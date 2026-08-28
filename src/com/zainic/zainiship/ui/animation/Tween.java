package com.zainic.zainiship.ui.animation;

/** Animates one double value between two endpoints, optionally after a delay. */
public final class Tween implements Animation {
	private final double from;
	private final double to;
	private final int durationTicks;
	private final int delayTicks;
	private int elapsedTicks;

	public enum Function {
		LINEAR("linear", 0, 0, 1, 1),
		EASE("ease", 0.25, 0.1, 0.25, 1),
		EASEIN("ease-in", 0.42, 0, 1, 1),
		EASEOUT("ease-out", 0, 0, 0.58, 1),
		EASEINOUT("ease-in-out", 0.42, 0, 0.58, 1),
		CUBICBEZIER("cubic-bezier", 0, 0, 1, 1);

		final String nameFunction;
		double x1, y1, x2, y2;

		private Function(String nameFunction, double x1, double y1, double x2, double y2) {
			this.nameFunction = nameFunction;
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}

		public void setCubicBezier(double x1, double y1, double x2, double y2) {
			if (!Double.isFinite(x1) || !Double.isFinite(y1) || !Double.isFinite(x2) || !Double.isFinite(y2)
					|| x1 < 0 || x1 > 1 || x2 < 0 || x2 > 1) {
				throw new IllegalArgumentException("Bezier x control points must be finite values in [0, 1]");
			}
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}

	}

	private Function function;
	private double x1, y1, x2, y2;

	public Tween(double from, double to, int durationTicks) {
		this(from, to, durationTicks, 0, "linear");
	}

	public Tween(double from, double to, int durationTicks, int delayTicks) {
		this(from, to, durationTicks, delayTicks, "linear");
	}

	public Tween(double from, double to, int durationTicks, int delayTicks, String nameFunction ) {
		if (durationTicks <= 0 || delayTicks < 0) {
			throw new IllegalArgumentException("Duration must be positive and delay cannot be negative");
		}
		this.from = from;
		this.to = to;
		this.durationTicks = durationTicks;
		this.delayTicks = delayTicks;
		for (Function func : Function.values()) {
			if (func.nameFunction.equals(nameFunction)) {
				this.function = func;
				break;
			}
		}
		if (this.function == null) throw new IllegalArgumentException("Unknown timing function: " + nameFunction);
		this.x1 = function.x1;
		this.y1 = function.y1;
		this.x2 = function.x2;
		this.y2 = function.y2;
	}

	/** Creates a tween with per-instance cubic Bezier control points. */
	public Tween(double from, double to, int durationTicks, int delayTicks,
			double x1, double y1, double x2, double y2) {
		if (durationTicks <= 0 || delayTicks < 0) {
			throw new IllegalArgumentException("Duration must be positive and delay cannot be negative");
		}
		if (!Double.isFinite(x1) || !Double.isFinite(y1) || !Double.isFinite(x2) || !Double.isFinite(y2)
				|| x1 < 0 || x1 > 1 || x2 < 0 || x2 > 1) {
			throw new IllegalArgumentException("Bezier x control points must be finite values in [0, 1]");
		}
		this.from = from;
		this.to = to;
		this.durationTicks = durationTicks;
		this.delayTicks = delayTicks;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	@Override 
	public void reset() { 
		elapsedTicks = 0; 
	}

	@Override public void update() {
		if (!isFinished()) elapsedTicks++; 
	}

	@Override 
	public boolean isFinished() {
		return elapsedTicks >= delayTicks + durationTicks;
	}

	public double value() {
		if (elapsedTicks <= delayTicks) return from;
		double progress = Math.min(1.0f, Math.max(0, (double) (elapsedTicks - delayTicks) / durationTicks));
		double state = stateFunction(progress);
		return from + (to - from) * state;
	}

	private double timeFunction(double t) {
		return 3*t*((1-t)*(1-t)) * x1 + 3*(t*t)*(1-t) * x2 + t*t*t;
	}

	private double stateFunction(double progress) {
		// Progress is the Bezier curve's x coordinate. Find its curve parameter,
		// then use the corresponding y coordinate as the eased value.
		double low = 0;
		double high = 1;
		for (int i = 0; i < 24; i++) {
			double t = (low + high) / 2;
			if (timeFunction(t) < progress) low = t;
			else high = t;
		}
		double t = (low + high) / 2;
		return 3*t*((1-t)*(1-t)) * y1 + 3*(t*t)*(1-t) * y2 + t*t*t;
	}
}
