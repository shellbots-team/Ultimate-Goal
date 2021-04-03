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

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Config
@Autonomous(group = "drive")
public class AutonomousMode extends LinearOpMode {

	@Override
	public void runOpMode() throws InterruptedException {
		this.msStuckDetectStop = 60000;
		SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
		Robot robot = new Robot();
		robot.init(hardwareMap, telemetry, this, true);

		robot.cameraVision.start();
		robot.wobbleHand.grab();

		ElapsedTime t = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
		double currentTime = t.time();
		while(t.time() - currentTime < 5) { idle(); }

		telemetry.addData("Status", "Ready to run");
		telemetry.update();

		waitForStart();

		if (isStopRequested()) return;

		SkystoneDeterminationPipeline.RingPosition ringPosition = robot.cameraVision.getPosition();
		telemetry.addData("Ring", ringPosition.toString());
		telemetry.update();

		if(ringPosition == SkystoneDeterminationPipeline.RingPosition.FOUR) {
			AutonomousModeFourRing.run(drive, robot, telemetry, this);
		} else if(ringPosition == SkystoneDeterminationPipeline.RingPosition.ONE) {
			AutonomousModeSingleRing.run(drive, robot, telemetry, this);
		} else {
			AutonomousModeNoRing.run(drive, robot, telemetry, this);
		}
	}
}
