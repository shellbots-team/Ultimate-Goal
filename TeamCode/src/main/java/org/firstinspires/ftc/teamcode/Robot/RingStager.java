package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class RingStager extends RobotComponent {

	private Servo servo = null;

	RingStager(OpMode opMode) { super(opMode); }

	public void init(Servo servo) {
		this.servo = servo;
	}

	public void drop() {
		servo.setPosition(0.4);
	}

	public void grab() {
		servo.setPosition(0.6);
	}

	@Override
	public void stopAllMotors() {

	}

	@Override
	void logTeleOpData() {

	}
}
