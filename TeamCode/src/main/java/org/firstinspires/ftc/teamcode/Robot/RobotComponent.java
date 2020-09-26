package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

abstract class RobotComponent {

	static final double COUNTS_PER_MOTOR_REV = 400;    // eg: 1440 if TETRIX Motor Encoder
	static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
	static final double WHEEL_DIAMETER_INCHES = 3.75;     // For figuring circumference
	static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
			(WHEEL_DIAMETER_INCHES * 3.1415);

	static final double DEFAULT_DRIVE_SPEED = 1.0;
	static final double DEFAULT_TURN_SPEED = 0.5;

	private OpMode opmode;

	RobotComponent(OpMode opmode) {
		this.opmode = opmode;
	}

	public abstract void stopAllMotors();

	abstract void logTeleOpData();

	void setServoPosition(CRServo crservo, double position) {
		crservo.getController().setServoPosition(crservo.getPortNumber(), position);
	}

	/**
	 * Sets the power for all passed motors to a given power
	 *
	 * @param power  The power to set the motors to (-1.0 to 1.0)
	 * @param motors The motors to set to the given power
	 */
	void setSpecificPowers(double power, DcMotor... motors) {
		for (DcMotor motor : motors) {
			motor.setPower(power);
		}
	}

void setRunMode(DcMotor.RunMode runMode, DcMotor... motors) {
		for (DcMotor motor : motors) {
			motor.setMode(runMode);
		}
	}

	boolean opModeIsActive() {
		if (opmode instanceof LinearOpMode) {
			return ((LinearOpMode) opmode).opModeIsActive();
		}
		return true;
	}

	void sleep(long milliseconds) {
		if (opmode instanceof LinearOpMode) {
			((LinearOpMode) opmode).sleep(milliseconds);
		}
	}

}
