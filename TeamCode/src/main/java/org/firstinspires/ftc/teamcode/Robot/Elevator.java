package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logger;

public class Elevator extends RobotComponent {

	private Logger logger;
	private CRServoExtended leftServo = null;
	private CRServoExtended rightServo = null;

	Elevator(OpMode opMode) { super(opMode); }

	public void init(Telemetry telemetry, CRServo leftServo, CRServo rightServo) {
		logger = new Logger(telemetry);

		this.leftServo = new CRServoExtended(leftServo);
		this.rightServo = new CRServoExtended(rightServo);

		this.leftServo.definePositions(0, 1);
		this.rightServo.definePositions(0, 1);
	}

	public void close() {
		leftServo.close();
		rightServo.close();
	}

	public void open() {
		leftServo.open();
		leftServo.open();
	}

	@Override
	public void stopAllMotors() {

	}

	@Override
	void logTeleOpData() {

	}
}
