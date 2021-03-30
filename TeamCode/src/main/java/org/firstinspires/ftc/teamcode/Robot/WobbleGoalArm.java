package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logger;

public class WobbleGoalArm extends RobotComponent {
	private DcMotor base;
	private Servo claw;

	private Logger logger = null;

	WobbleGoalArm(OpMode opmode) { super(opmode); }

	void init(Telemetry telemetry, DcMotor base, Servo claw) {
		logger = new Logger(telemetry);

		this.base = base;
		this.claw = claw;

		base.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
	}

	public void giveArmPower(double power) {
		base.setPower(power);
	}

	public void grabWobbleGoal() { claw.setPosition(0); }

	public void releaseWobbleGoal() { claw.setPosition(0.6); }

	@Override
	public void stopAllMotors() {
		base.setPower(0);
	}

	@Override
	void logTeleOpData() {
		logger.numberLog("Base Speed", base.getPower());
	}
}