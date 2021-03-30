package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.profile.MotionProfile;
import com.acmerobotics.roadrunner.profile.MotionProfileGenerator;
import com.acmerobotics.roadrunner.profile.MotionState;
import com.acmerobotics.roadrunner.util.NanoClock;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_ACCEL;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_VEL;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MOTOR_VELO_PID;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.kV;

/*
 * This routine is designed to tune the PID coefficients used by the REV Expansion Hubs for closed-
 * loop velocity control. Although it may seem unnecessary, tuning these coefficients is just as
 * important as the positional parameters. Like the other manual tuning routines, this op mode
 * relies heavily upon the dashboard. To access the dashboard, connect your computer to the RC's
 * WiFi network. In your browser, navigate to https://192.168.49.1:8080/dash if you're using the RC
 * phone or https://192.168.43.1:8080/dash if you are using the Control Hub. Once you've successfully
 * connected, start the program, and your robot will begin moving forward and backward according to
 * a motion profile. Your job is to graph the velocity errors over time and adjust the PID
 * coefficients (note: the tuning variable will not appear until the op mode finishes initializing).
 * Once you've found a satisfactory set of gains, add them to the DriveConstants.java file under the
 * MOTOR_VELO_PID field.
 *
 * Recommended tuning process:
 *
 * 1. Increase kP until any phase lag is eliminated. Concurrently increase kD as necessary to
 *    mitigate oscillations.
 * 2. Add kI (or adjust kF) until the steady state/constant velocity plateaus are reached.
 * 3. Back off kP and kD a little until the response is less oscillatory (but without lag).
 *
 * Pressing Y/Δ (Xbox/PS4) will pause the tuning process and enter driver override, allowing the
 * user to reset the position of the bot in the event that it drifts off the path.
 * Pressing B/O (Xbox/PS4) will cede control back to the tuning process.
 */
@Config
@Autonomous(group = "drive")
public class LauncherTuning extends LinearOpMode {
	public static int DISTANCE = 9999;

	private static MotionProfile generateProfile(boolean movingForward) {
		MotionState start = new MotionState(movingForward ? 0 : DISTANCE, 0, 0, 0);
		MotionState goal = new MotionState(movingForward ? DISTANCE : 0, 0, 0, 0);
		return MotionProfileGenerator.generateSimpleMotionProfile(start, goal, MAX_VEL, MAX_ACCEL);
	}

	@Override
	public void runOpMode() {

		DcMotorEx motor = this.hardwareMap.get(DcMotorEx.class, "bananaShooter");
		motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		VoltageSensor batteryVoltageSensor = hardwareMap.voltageSensor.iterator().next();

		telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

		double lastKp = MOTOR_VELO_PID.p;
		double lastKi = MOTOR_VELO_PID.i;
		double lastKd = MOTOR_VELO_PID.d;
		double lastKf = MOTOR_VELO_PID.f;

		PIDFCoefficients compensatedCoefficients = new PIDFCoefficients(
				MOTOR_VELO_PID.p, MOTOR_VELO_PID.i, MOTOR_VELO_PID.d,
				MOTOR_VELO_PID.f * 12 / batteryVoltageSensor.getVoltage()
		);
		motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, compensatedCoefficients);

		NanoClock clock = NanoClock.system();

		telemetry.addLine("Ready!");
		telemetry.update();
		telemetry.clearAll();

		waitForStart();

		if (isStopRequested()) return;

		boolean movingForwards = true;
		MotionProfile activeProfile = generateProfile(true);
		double profileStart = clock.seconds();


		while (!isStopRequested()) {

			// calculate and set the motor power
			double profileTime = clock.seconds() - profileStart;

			if (profileTime > activeProfile.duration()) {
				// generate a new profile
				movingForwards = !movingForwards;
				activeProfile = generateProfile(movingForwards);
				profileStart = clock.seconds();
			}

			MotionState motionState = activeProfile.get(profileTime);
			double targetPower = kV * motionState.getV();
			motor.setPower(targetPower);

			double velo = 2 * 2 * Math.PI * 1 * motor.getVelocity() / 537.6;

			// update telemetry
			telemetry.addData("targetVelocity", motionState.getV());
			telemetry.addData("measuredVelocity", velo);
			telemetry.addData(
					"error",
					motionState.getV() - velo
			);
			telemetry.update();
		}

		if (lastKp != MOTOR_VELO_PID.p || lastKd != MOTOR_VELO_PID.d
				|| lastKi != MOTOR_VELO_PID.i || lastKf != MOTOR_VELO_PID.f) {
			PIDFCoefficients compensatedCoefficientstwo = new PIDFCoefficients(
					MOTOR_VELO_PID.p, MOTOR_VELO_PID.i, MOTOR_VELO_PID.d,
					MOTOR_VELO_PID.f * 12 / batteryVoltageSensor.getVoltage()
			);
			motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, compensatedCoefficientstwo);

			lastKp = MOTOR_VELO_PID.p;
			lastKi = MOTOR_VELO_PID.i;
			lastKd = MOTOR_VELO_PID.d;
			lastKf = MOTOR_VELO_PID.f;
		}

		telemetry.update();
	}
}
