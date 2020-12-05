package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.vision.SkystoneDeterminationPipeline;
import org.firstinspires.ftc.teamcode.vision.SkystoneDeterminationPipeline.RingPosition;

/**
 * Created by shell on 12/5/2020.
 */

@Autonomous
public class MainAutonomous extends BaseAutonomous {

	@Override
	public void runOpMode() {

		super.runOpMode();

		logger.statusLog(step++, "Ready to run");

		waitForStart();

		logger.statusLog(step++, "Scanning ring position");
		RingPosition ringPosition = robot.cameraVision.getPosition();
		logger.completeLog("RingPosition", ringPosition.toString());
		robot.cameraVision.end();

		logger.statusLog(step++, "Moving off of wall");
		robot.drivetrain.runDistance(16, 16, 99, 0.3);

		logger.statusLog(step++, "Moving to be aligned with boxes");
		moveTowardsBlueAlliance(20, 99, 0.05);

		logger.statusLog(step++, "Moving to be aligned with correct box");
		if(ringPosition == RingPosition.NONE) {
			robot.drivetrain.runDistance(62,62, 99, 0.4);
		} else if(ringPosition == RingPosition.ONE) {
			robot.drivetrain.runDistance(94,94, 99, 0.4);
		} else if(ringPosition == RingPosition.FOUR) {
			robot.drivetrain.runDistance(116,116, 99, 0.4);
		}

		logger.statusLog(step++, "Turning towards box");
		robot.drivetrain.turnDegrees(180, true, 0.2);

		if(ringPosition != RingPosition.ONE) {
			moveTowardsBlueAlliance(30, 99, 0.3);
		}

		// Drop the stone
		robot.arm.grabHand();
		sleep(1500);
		// Pick the hand back up
		robot.arm.releaseHand();
		sleep(1500);

		if(ringPosition == RingPosition.ONE) {
			robot.drivetrain.runDistance(24,24, 99, 0.3);
		} else if(ringPosition == RingPosition.FOUR){
			robot.drivetrain.runDistance(46,46, 99, 0.3);
		}

		logger.statusLog(step++, "Stopping");
		robot.stopAllMotors();

	}

}

