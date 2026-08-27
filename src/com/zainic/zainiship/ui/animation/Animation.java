package com.zainic.zainiship.ui.animation;

/** A reusable, tick-based animation for the game's fixed 60 UPS update loop. */
public interface Animation {
	void reset();
	void update();
	boolean isFinished();
}
