package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logger;

public class BananaShooter extends RobotComponent {

	private Logger logger = null;

	private DcMotor leftMotor = null;
	private DcMotor rightMotor = null;

	BananaShooter(OpMode opMode) { super(opMode); }

	public void init(Telemetry telemetry, DcMotor leftMotor, DcMotor rightMotor) {
		this.logger = new Logger(telemetry);

		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;

		this.leftMotor.setDirection(DcMotor.Direction.REVERSE);
	}

	public void setPower(double power) {
		leftMotor.setPower(power);
		rightMotor.setPower(power);
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
