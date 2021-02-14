package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

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
		robot.cameraVision.end();

		RingPosition ringPosition = robot.cameraVision.getPosition();
		int analysis = robot.cameraVision.getAnalysis();
		logger.completeLog("RingPosition", ringPosition.toString());
		logger.completeLog("RingAnalysis", String.valueOf(analysis));
		robot.cameraVision.save();

		logger.statusLog(step++, "Stopping");
		robot.stopAllMotors();
	}

}