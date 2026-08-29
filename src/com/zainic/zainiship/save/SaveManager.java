package com.zainic.zainiship.save;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

/** Reads and writes the game's fixed save slots. */
public final class SaveManager {

	public static final int SLOT_COUNT = 4;
	private static final String SAVE_VERSION = "2";
	private final Path saveDirectory;

	public SaveManager() {
		this(defaultSaveDirectory());
	}

	public SaveManager(Path saveDirectory) {
		if (saveDirectory == null) throw new IllegalArgumentException("Save directory cannot be null");
		this.saveDirectory = saveDirectory;
	}

	public static Path defaultSaveDirectory() {
		return Paths.get(System.getProperty("user.home"), ".zainiship", "saves");
	}

	public Path getSaveDirectory() {
		return saveDirectory;
	}

	public boolean exists(int slotIndex) {
		validateSlot(slotIndex);
		return Files.isRegularFile(slotPath(slotIndex));
	}

	public SaveData load(int slotIndex) throws IOException {
		validateSlot(slotIndex);
		Path file = slotPath(slotIndex);
		if (!Files.isRegularFile(file)) throw new IOException("Save slot " + (slotIndex + 1) + " is empty");

		Properties values = new Properties();
		try (InputStream input = new BufferedInputStream(Files.newInputStream(file))) {
			values.load(input);
		}

		String playerName = values.getProperty("playerName", "").trim();
		if (playerName.isEmpty()) throw new IOException("Save slot " + (slotIndex + 1) + " has no player name");
		try {
			long money = Long.parseLong(values.getProperty("money", "0"));
			int highestUnlockedLevel = Integer.parseInt(values.getProperty("highestUnlockedLevel", "1"));
			long playtimeSeconds = Long.parseLong(values.getProperty("playtimeSeconds", "0"));
			long fallbackLastSaved = Files.getLastModifiedTime(file).toMillis();
			long lastSavedEpochMillis = Long.parseLong(
					values.getProperty("lastSavedEpochMillis", Long.toString(fallbackLastSaved)));
			return new SaveData(playerName, money, highestUnlockedLevel,
					playtimeSeconds, lastSavedEpochMillis);
		} catch (NumberFormatException exception) {
			throw new IOException("Save slot " + (slotIndex + 1) + " contains invalid numbers", exception);
		}
	}

	public SaveData save(int slotIndex, SaveData data) throws IOException {
		validateSlot(slotIndex);
		if (data == null) throw new IllegalArgumentException("Save data cannot be null");
		Files.createDirectories(saveDirectory);
		SaveData savedData = data.withLastSavedAt(System.currentTimeMillis());

		Properties values = new Properties();
		values.setProperty("version", SAVE_VERSION);
		values.setProperty("playerName", savedData.getPlayerName());
		values.setProperty("money", Long.toString(savedData.getMoney()));
		values.setProperty("highestUnlockedLevel", Integer.toString(savedData.getHighestUnlockedLevel()));
		values.setProperty("playtimeSeconds", Long.toString(savedData.getPlaytimeSeconds()));
		values.setProperty("lastSavedEpochMillis", Long.toString(savedData.getLastSavedEpochMillis()));

		Path target = slotPath(slotIndex);
		Path temporary = saveDirectory.resolve("slot" + (slotIndex + 1) + ".tmp");
		try (OutputStream output = new BufferedOutputStream(Files.newOutputStream(temporary))) {
			values.store(output, "Zainiship save data");
		}
		try {
			Files.move(temporary, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
		} catch (AtomicMoveNotSupportedException exception) {
			Files.move(temporary, target, StandardCopyOption.REPLACE_EXISTING);
		}
		return savedData;
	}

	private Path slotPath(int slotIndex) {
		return saveDirectory.resolve("slot" + (slotIndex + 1) + ".properties");
	}

	private static void validateSlot(int slotIndex) {
		if (slotIndex < 0 || slotIndex >= SLOT_COUNT) {
			throw new IllegalArgumentException("Save slot must be between 0 and " + (SLOT_COUNT - 1));
		}
	}
}
