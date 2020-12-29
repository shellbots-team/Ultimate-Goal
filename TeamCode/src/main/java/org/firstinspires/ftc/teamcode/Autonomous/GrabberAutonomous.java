package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.SavedData;
import org.firstinspires.ftc.teamcode.vision.SkystoneDeterminationPipeline;
import org.firstinspires.ftc.teamcode.vision.SkystoneDeterminationPipeline.RingPosition;

/**
 * Created by shell on 12/5/2020.
 */

@Autonomous
public class GrabberAutonomous extends BaseAutonomous {

	@Override
	public void runOpMode() {

		super.runOpMode();

		logger.statusLog(step++, "Ready to run");

		waitForStart();

		logger.statusLog(step++, "Scanning ring position");
		robot.cameraVision.end();

		RingPosition ringPosition = robot.cameraVision.getPosition();
		int analysis = robot.cameraVision.getAnalysis();
		logger.completeLog("RingPosition", ringPosition.toString());
		logger.completeLog("RingAnalysis", String.valueOf(analysis));
		robot.cameraVision.save();

		logger.statusLog(step++, "Moving off of wall");
		robot.drivetrain.runDistance(16, 16, 99, 0.4);

		logger.statusLog(step++, "Moving to be aligned with boxes");
		moveTowardsBlueAlliance(20, 99, 0.4);

		logger.statusLog(step++, "Front of Boxes");
		robot.drivetrain.runDistance(47,62, 99, 0.4);

		moveTowardsRedAlliance(20, 99, 0.25);
/*
		// Drop the goal
		robot.arm.grabHand();
		sleep(2000);
		// Pick the hand back up
		robot.arm.releaseHand();
		sleep(2000);
		// Drop the goal
		robot.arm.grabHand();
		sleep(2000);
		// Pick the hand back up
		robot.arm.releaseHand();
		sleep(2000);

		if(ringPosition == RingPosition.ONE) {
			robot.drivetrain.runDistance(30,30, 99, 0.3);
		} else if(ringPosition == RingPosition.FOUR){
			robot.drivetrain.runDistance(54,54, 99, 0.3);
		}

 */
		SavedData.save(robot.drivetrain.frontLeft.getCurrentPosition(),
				robot.drivetrain.frontRight.getCurrentPosition());
		logger.statusLog(step++, "Stopping");
		robot.stopAllMotors();

	}

}

