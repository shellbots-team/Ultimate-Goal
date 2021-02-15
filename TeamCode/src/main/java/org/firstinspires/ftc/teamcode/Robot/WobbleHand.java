package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logger;

public class WobbleHand extends RobotComponent {

	private Logger logger = null;
	private Servo servo = null;

	WobbleHand(OpMode opMode) { super(opMode); }

	public void init(Telemetry telemetry, Servo servo) {
		logger = new Logger(telemetry);

		this.servo = servo;
	}

	public void grab() {
		servo.setPosition(0);
	}

	public void release() {
		this.servo.setPosition(0.2);
	}

	@Override
	public void stopAllMotors() {

	}

	@Override
	void logTeleOpData() {

	}
}
