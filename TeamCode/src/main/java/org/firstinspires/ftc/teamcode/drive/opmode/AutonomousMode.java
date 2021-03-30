package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot.Robot;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.vision.SkystoneDeterminationPipeline;

import java.lang.annotation.ElementType;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Config
@Autonomous(group = "drive")
public class AutonomousMode extends LinearOpMode {

	public static double aFirstPositionX = 54;
	public static double aFirstPositionY = 7;

	public static double armLowerPower = -0.4;
	public static double armLowerPowerTwo = -0.4;
	public static double armRaisePower = 0.4;
	public static double armRaisePowerTwo = 0.4;

	public static double bSecondPositionX = 62;
	public static double bSecondPositionY = -2;

	public static double cThirdPositionT = 0;
	public static double cThirdPositionX = 30.5;
	public static double cThirdPositionY = 18;

	public static double dFourthPositionH = 135;
	public static double dFourthPositionX = 64;
	public static double dFourthPositionY = 22;

	public static double eFifthPositionH = -180;
	public static double eFifthPositionX = 65;
	public static double eFifthPositionY = 30;

	public static double launcherSpeed = 0.835;
	public static int timeBetweenLaunch = 1250;

	@Override
	public void runOpMode() throws InterruptedException {
		SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
		Robot robot = new Robot();
		robot.init(hardwareMap, telemetry, this, true);

		robot.cameraVision.start();
		robot.wobbleHand.grab();

		ElapsedTime t = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
		double currentTime = t.time();
		//while(t.time() - currentTime < 5) { idle(); }

		Trajectory traj = drive.trajectoryBuilder(new Pose2d())
				.splineToConstantHeading(new Vector2d(aFirstPositionX, aFirstPositionY), 0)
				.build();

		Trajectory traj2 = drive.trajectoryBuilder(traj.end())
				.lineToConstantHeading(new Vector2d(bSecondPositionX, bSecondPositionY))
				.build();

		Trajectory traj3 = drive.trajectoryBuilder(traj2.end())
				.back(10)
				.splineToConstantHeading(new Vector2d(cThirdPositionX, cThirdPositionY), Math.toRadians(cThirdPositionT))
				.build();

		Trajectory traj4 = drive.trajectoryBuilder(traj3.end())
				.lineToLinearHeading(new Pose2d(dFourthPositionX, dFourthPositionY, Math.toRadians(dFourthPositionH)))
				.build();

		Trajectory traj5 = drive.trajectoryBuilder(traj4.end())
				.lineToLinearHeading(new Pose2d(eFifthPositionX, eFifthPositionY, Math.toRadians(eFifthPositionH)))
				.build();

		telemetry.addData("Status", "Ready to run");
		telemetry.update();

		waitForStart();

		if (isStopRequested()) return;

		SkystoneDeterminationPipeline.RingPosition x = robot.cameraVision.getPosition();
		int ringAnalysis = robot.cameraVision.getAnalysis();
		telemetry.addData("Position", x.toString() + " " + String.valueOf(ringAnalysis));
		telemetry.update();

		robot.launcher.run(launcherSpeed + 0.03);

		drive.followTrajectory(traj);

		for(int i = 0; i < 3; i++) {
			robot.launcher.push();
			sleep(timeBetweenLaunch / 2);
			robot.launcher.reset();
			robot.launcher.run(launcherSpeed);
			sleep(timeBetweenLaunch / 2);
		}

		robot.launcher.run(0);

		drive.followTrajectory(traj2);

		robot.wobbleHand.release();
		robot.wobbleGoalArm.giveArmPower(armLowerPower);
		robot.wobbleGoalArm.releaseWobbleGoal();
		sleep(1000);

		drive.followTrajectory(traj3);
		robot.wobbleGoalArm.grabWobbleGoal();
		sleep(1000);
		robot.wobbleGoalArm.giveArmPower(armRaisePower);

		drive.followTrajectory(traj4);

		sleep(1000);

		robot.wobbleGoalArm.giveArmPower(armLowerPowerTwo);

		sleep(1000);

		robot.wobbleGoalArm.releaseWobbleGoal();
		sleep(1000);
		robot.wobbleGoalArm.giveArmPower(armRaisePowerTwo);
		sleep(500);
		robot.wobbleGoalArm.giveArmPower(0);

		drive.followTrajectory(traj5);

		sleep(1000);
	}
}
