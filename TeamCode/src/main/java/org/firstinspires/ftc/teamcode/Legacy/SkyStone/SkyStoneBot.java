package org.firstinspires.ftc.teamcode.Legacy.SkyStone;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SkyStoneBot {
	// fR fL bL bR in h3

	private DcMotor frontLeft;
	private DcMotor frontRight;
	private DcMotor backLeft;
	private DcMotor backRight;

	public SkyStoneBot() { }

	public void init(HardwareMap hardwareMap) {
		frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
		frontRight = hardwareMap.get(DcMotor.class, "frontRight");
		backLeft = hardwareMap.get(DcMotor.class, "backLeft");
		backRight = hardwareMap.get(DcMotor.class, "backRight");

		frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

		frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

		frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
		backRight.setDirection(DcMotorSimple.Direction.REVERSE);
	}

	public void setPower(double power) { setIndividualPower(power, power); }

	public void setIndividualPower(double leftPower, double rightPower) {
		frontLeft.setPower(leftPower);
		frontRight.setPower(rightPower);
		backLeft.setPower(leftPower);
		backRight.setPower(rightPower);
	}

	public void setIndividualPowers(double[] motorPowers) {
		if (motorPowers.length != 4) {
			return;
		}
		frontLeft.setPower(motorPowers[0]);
		frontRight.setPower(motorPowers[1]);
		backLeft.setPower(motorPowers[2]);
		backRight.setPower(motorPowers[3]);
	}

}
