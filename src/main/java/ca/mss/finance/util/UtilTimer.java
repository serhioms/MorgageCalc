package ca.mss.finance.util;

public class UtilTimer {

	private long lastTime, startTime, duration = 0;

	public UtilTimer() {
		start();
	}

	final public void start() {

		startTime = lastTime = System.currentTimeMillis();

	}

	// Calculate time from the last invocation

	final public String duration() {

		duration = System.currentTimeMillis() - lastTime;

		lastTime += duration;

		return formatMls(duration);

	}

	final public String formatMls(long mls) {

		long sec = mls / 1000;

		if (sec / 3600 > 0)

			return ((double )Math.round((mls / 1000.0) / 3600 * 100)) / 100 + " h";

		if (sec / 60 > 0)

			return ((double )Math.round((mls / 1000.0) / 60 * 100)) / 100 + " min";

		if (sec > 0)

			return ((double )Math.round((mls / 1000.0) * 100)) / 100 + " sec";

		return mls + " mls";

	}

	final public String formatBytes(int bytes) {

		int temp = bytes / 1024 / 1024 / 1024;

		if (temp > 0)

			return temp + " Gb";

		temp = bytes / 1024 / 1024;

		if (temp > 0)

			return temp + " Mb";

		temp = bytes / 1024;

		if (temp > 0)

			return temp + " Kb";

		return bytes + " b";

	}

	// Get total time since process get started

	final public String getTotalTime() {

		return formatMls((System.currentTimeMillis() - startTime));

	}

}
