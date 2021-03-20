package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logger;

public class BananaShooter extends RobotComponent {

	private Logger logger = null;

	private DcMotor motor = null;

	private static double RUN_SPEED = 0.8;

	BananaShooter(OpMode opMode) { super(opMode); }

	public void init(Telemetry telemetry, DcMotor motor) {
		this.logger = new Logger(telemetry);

		this.motor = motor;

		this.motor.setDirection(DcMotor.Direction.REVERSE);
	}

	public void run(double power) {
		motor.setPower(RUN_SPEED * power);
	}

	@Override
	public void stopAllMotors() {
		motor.setPower(0);
	}

	@Override
	void logTeleOpData() {
	}
}
