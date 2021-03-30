package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.roadrunner.profile.MotionProfile;
import com.acmerobotics.roadrunner.profile.MotionProfileGenerator;
import com.acmerobotics.roadrunner.profile.MotionState;
import com.acmerobotics.roadrunner.util.NanoClock;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logger;

import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_ACCEL;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_RPM;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_VEL;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MOTOR_VELO_PID;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.TICKS_PER_REV;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.getMotorVelocityF;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.kV;

public class Launcher extends RobotComponent {

	private Logger logger = null;

	private DcMotor bananaShooter = null;
	private Servo pinballArm = null;

	private static double RUN_SPEED = 0.70;

	Launcher(OpMode opMode) { super(opMode); }

	public void init(Telemetry telemetry, DcMotor motor, Servo pinballArm) {
		this.logger = new Logger(telemetry);
		this.bananaShooter = motor;
		this.pinballArm = pinballArm;
		this.bananaShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
	}

	public void run(double power) {
		bananaShooter.setPower(RUN_SPEED * power);
	}

	public void push() {
		pinballArm.setPosition(1);
	}

	public void reset() {
		pinballArm.setPosition(0);
	}

	@Override
	public void stopAllMotors() {
		bananaShooter.setPower(0);
	}

	@Override
	void logTeleOpData() {
	}
}
