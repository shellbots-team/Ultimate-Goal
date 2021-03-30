package org.firstinspires.ftc.teamcode.Legacy.RoverRuckus;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RoverRuckusBot {

	private DcMotor left;
	private DcMotor right;

	public RoverRuckusBot() { }

	public void init(HardwareMap hardwareMap) {
		left = hardwareMap.get(DcMotor.class, "left");
		right = hardwareMap.get(DcMotor.class, "right");

		left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

		left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
	}

	public void setIndividualPower(double leftPower, double rightPower) {
		left.setPower(leftPower);
		right.setPower(rightPower);
	}

	public void setPower(double power) {
		left.setPower(power);
		right.setPower(power);
	}
}
