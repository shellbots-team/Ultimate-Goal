package org.firstinspires.ftc.teamcode;

import android.util.Log;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Locale;

public class Logger {

	private Telemetry telemetry;
	private static final int HOW_OFTEN_LOG = 3;
	private int i = 1;

	public Logger(Telemetry telemetry) {
		this.telemetry = telemetry;
	}

	public void statusLog(int step, String description) {
		completeLog("Status", String.format(Locale.US, "Step: %d, Description: %s", step, description));
		update();
	}

	public void numberLog(String description, double value) {

		completeLog(description, String.valueOf(value));
	}

	public void occasionalLog(String caption, String value) {
		i++;
		if (i % HOW_OFTEN_LOG == 0) {
			completeLog(caption, value);
			i = 1;
		} else {
			telemetry.addData(caption, value);
		}
	}

	public void completeLog(String caption, String value) {
		Log.d("14736:" + caption, value);
		telemetry.addData(caption, value);
	}

	public void addData(String caption, String value) {
		telemetry.addData(caption, value);
	}

	public void update() {
		telemetry.update();
	}

}
