package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot.Robot;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Config
@Autonomous(group = "drive")
public class AutonomousMode extends LinearOpMode {

	public static double firstX = 15;
	public static double secondX = 91;
	public static double secondY = -17.5;
	public static double thirdX = 25;
	public static double thirdY = -32;
	public static double armPower = -0.25;
	public static int time1 = 1000;
	public static int time2 = 1000;

	@Override
	public void runOpMode() throws InterruptedException {
		SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
		Robot robot = new Robot();

		robot.init(hardwareMap, telemetry, this, true, true);
		robot.wobbleHand.grab();

		waitForStart();

		if (isStopRequested()) return;

		// TODO: Drop off in front left instead of front right

		Trajectory traj = drive.trajectoryBuilder(new Pose2d())
				.lineTo(new Vector2d(firstX, 0))
				.splineTo(new Vector2d(secondX, secondY), 0)
				.build();

		Trajectory traj2 = drive.trajectoryBuilder(traj.end())
				.lineToConstantHeading(new Vector2d(thirdX, thirdY))
				.build();

		drive.followTrajectory(traj);
		robot.wobbleHand.release();
		robot.wobbleGoalArm.giveArmPower(armPower);
		sleep(time1);
		robot.wobbleGoalArm.releaseWobbleGoal();
		sleep(time2);
		drive.followTrajectory(traj2);
		robot.wobbleGoalArm.grabWobbleGoal();
	}
}
