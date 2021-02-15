package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logger;

public class BananaShooter extends RobotComponent {

	private Logger logger = null;

	private DcMotor leftMotor = null;
	private DcMotor rightMotor = null;

	private static double RUN_SPEED = 0.45;

	BananaShooter(OpMode opMode) { super(opMode); }

	public void init(Telemetry telemetry, DcMotor leftMotor, DcMotor rightMotor) {
		this.logger = new Logger(telemetry);

		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;

		this.rightMotor.setDirection(DcMotor.Direction.REVERSE);
	}

	public void run(double power) {
		leftMotor.setPower(RUN_SPEED * power);
		rightMotor.setPower(RUN_SPEED * power);
	}

	@Override
	public void stopAllMotors() {
		leftMotor.setPower(0);
		rightMotor.setPower(0);
	}

	@Override
	void logTeleOpData() {
	}
}
