package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake extends RobotComponent {

	private DcMotor outerIntake = null;
	private DcMotor innerIntake = null;
	private Servo dropServo = null;

	Intake(OpMode opMode) { super(opMode); }

	public void init(Telemetry telemetry, DcMotor outerIntake, DcMotor innerIntake, Servo dropServo) {
		this.outerIntake = outerIntake;
		this.dropServo = dropServo;
		this.innerIntake = innerIntake;
	}

	public void outerIntake(double power) {
		outerIntake.setPower(-power);
	}

	public void innerIntake(double power) {
		innerIntake.setPower(power);
	}

	public void drop() {
		dropServo.setPosition(1);
	}

	@Override
	public void stopAllMotors() {

	}

	@Override
	void logTeleOpData() {

	}
}
