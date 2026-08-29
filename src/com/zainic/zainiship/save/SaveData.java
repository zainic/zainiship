package com.zainic.zainiship.save;

/** The persistent state stored in one save slot. */
public final class SaveData {

	private final String playerName;
	private final long money;
	private final int highestUnlockedLevel;
	private final long playtimeSeconds;
	private final long lastSavedEpochMillis;

	public SaveData(String playerName, long money, int highestUnlockedLevel) {
		this(playerName, money, highestUnlockedLevel, 0L, 0L);
	}

	public SaveData(String playerName, long money, int highestUnlockedLevel,
			long playtimeSeconds, long lastSavedEpochMillis) {
		if (playerName == null || playerName.trim().isEmpty()) {
			throw new IllegalArgumentException("Player name cannot be empty");
		}
		this.playerName = playerName.trim();
		this.money = Math.max(0L, money);
		this.highestUnlockedLevel = Math.max(1, highestUnlockedLevel);
		this.playtimeSeconds = Math.max(0L, playtimeSeconds);
		this.lastSavedEpochMillis = Math.max(0L, lastSavedEpochMillis);
	}

	public static SaveData newGame(String playerName) {
		return new SaveData(playerName, 0L, 1);
	}

	public String getPlayerName() {
		return playerName;
	}

	public long getMoney() {
		return money;
	}

	public int getHighestUnlockedLevel() {
		return highestUnlockedLevel;
	}

	public long getPlaytimeSeconds() {
		return playtimeSeconds;
	}

	public long getLastSavedEpochMillis() {
		return lastSavedEpochMillis;
	}

	public SaveData withLastSavedAt(long epochMillis) {
		return new SaveData(playerName, money, highestUnlockedLevel, playtimeSeconds, epochMillis);
	}
}
