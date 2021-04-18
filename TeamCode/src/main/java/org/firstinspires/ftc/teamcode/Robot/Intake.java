package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Intake extends RobotComponent {

	private DcMotor outerIntake = null;
	private DcMotor innerIntake = null;
	private Servo dropServo = null;
	private Servo holdServo = null;

	Intake(OpMode opMode) { super(opMode); }

	public void init(Telemetry telemetry, DcMotor outerIntake, DcMotor innerIntake, Servo dropServo, Servo holdServo) {
		this.outerIntake = outerIntake;
		this.dropServo = dropServo;
		this.innerIntake = innerIntake;
		this.holdServo = holdServo;
	}

	public void outerIntake(double power) {
		outerIntake.setPower(-power);
	}

	public void innerIntake(double power) {
		innerIntake.setPower(-power);
	}

	public void run(double power) {
		outerIntake(power);
		innerIntake(power);
	}

	public void setHold() { holdServo.setPosition(0); }

	public void releaseHold() { holdServo.setPosition(0.2); }

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
