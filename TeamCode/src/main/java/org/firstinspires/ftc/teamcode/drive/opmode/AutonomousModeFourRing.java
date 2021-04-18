package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot.Robot;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.vision.SkystoneDeterminationPipeline;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Config
@Autonomous(group = "drive")
public class AutonomousModeFourRing extends LinearOpMode {

	public static double aFirstPositionX = 54;
	public static double aFirstPositionY = 7;

	public static double armLowerPower = -0.4;
	public static double armLowerPowerTwo = -0.4;
	public static double armRaisePower = 0.4;
	public static double armRaisePowerTwo = 0.4;

	public static double bSecondPositionX = 107;
	public static double bSecondPositionY = 2;

	public static double cThirdPositionX = 35;
	public static double cThirdPositionY = 4;

	public static double dFourthPositionH = -55;
	public static double dFourthPositionX = 27;
	public static double dFourthPositionY = 15;

	public static double eFifthPositionH = 180;
	public static double eFifthPositionX = 30;
	public static double eFifthPositionY = -2;

	public static double fSixthPositionX = 100;
	public static double fSixthPositionY = 0;

	public static double gSeventhPositionX = 65;
	public static double gSeventhPositionY = 7;

	public static double launcherSpeed = 0.82;
	public static double powerIncr = 0.008;
	public static int timeBetweenLaunch = 1250;

	public static void run(SampleMecanumDrive drive, Robot robot, Telemetry telemetry, LinearOpMode opMode) {

		Trajectory traj = drive.trajectoryBuilder(new Pose2d())
				.splineToConstantHeading(new Vector2d(aFirstPositionX, aFirstPositionY), 0)
				.build();

		Trajectory traj2 = drive.trajectoryBuilder(traj.end())
				.lineToConstantHeading(new Vector2d(bSecondPositionX, bSecondPositionY))
				.build();

		Trajectory traj3 = drive.trajectoryBuilder(traj2.end())
				.lineToConstantHeading(new Vector2d(cThirdPositionX, cThirdPositionY))
				.build();

		Trajectory traj4 = drive.trajectoryBuilder(traj3.end())
				.lineToLinearHeading(new Pose2d(dFourthPositionX, dFourthPositionY, Math.toRadians(dFourthPositionH)))
				.build();

		Trajectory traj5 = drive.trajectoryBuilder(traj4.end())
				.lineToLinearHeading(new Pose2d(eFifthPositionX, eFifthPositionY, Math.toRadians(eFifthPositionH)))
				.build();

		Trajectory traj6 = drive.trajectoryBuilder(traj5.end())
				.lineToConstantHeading(new Vector2d(fSixthPositionX, fSixthPositionY))
				.build();

		Trajectory traj7 = drive.trajectoryBuilder(traj6.end())
				.lineToConstantHeading(new Vector2d(gSeventhPositionX, gSeventhPositionY))
				.build();

		double decreaseInPower = 0;

		robot.launcher.run(launcherSpeed - decreaseInPower);

		drive.followTrajectory(traj);

		for(int i = 0; i < 3; i++) {
			robot.launcher.push();
			opMode.sleep(timeBetweenLaunch / 2);
			robot.launcher.reset();
			decreaseInPower += powerIncr;
			robot.launcher.run(launcherSpeed - decreaseInPower);
			opMode.sleep(timeBetweenLaunch / 2);
		}

		robot.launcher.run(0);

		drive.followTrajectory(traj2);

		robot.wobbleHand.release();
		robot.wobbleGoalArm.giveArmPower(armLowerPower);
		robot.wobbleGoalArm.releaseWobbleGoal();
		opMode.sleep(1000);

		drive.followTrajectory(traj3);
		drive.followTrajectory(traj4);

		robot.wobbleGoalArm.grabWobbleGoal();
		opMode.sleep(1000);
		robot.wobbleGoalArm.giveArmPower(armRaisePower);

		drive.followTrajectory(traj5);
		drive.followTrajectory(traj6);

		opMode.sleep(200);

		robot.wobbleGoalArm.giveArmPower(armLowerPowerTwo);

		opMode.sleep(1000);

		robot.wobbleGoalArm.releaseWobbleGoal();
		opMode.sleep(1000);
		robot.wobbleGoalArm.giveArmPower(armRaisePowerTwo);
		opMode.sleep(550);
		robot.wobbleGoalArm.giveArmPower(0);

		drive.followTrajectory(traj7);

		robot.intake.releaseHold();

		opMode.sleep(50);
	}

	@Override
	public void runOpMode() throws InterruptedException {
		SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
		Robot robot = new Robot();
		robot.init(hardwareMap, telemetry, this, true);

		robot.cameraVision.start();
		robot.wobbleHand.grab();

		telemetry.addData("Status", "WRONG OPMODE");
		telemetry.update();

		waitForStart();

		if (isStopRequested()) return;

		AutonomousModeFourRing.run(drive, robot, telemetry, this);

	}
}
