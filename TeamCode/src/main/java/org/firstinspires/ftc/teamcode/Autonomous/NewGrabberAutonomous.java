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
public class NewGrabberAutonomous extends BaseAutonomous {

	@Override
	public void runOpMode() {

		super.runOpMode();

		logger.statusLog(step++, "Ready to run");

		waitForStart();

		//RingPosition ringPosition = RingPosition.NONE;

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
		robot.drivetrain.runDistance(52,52, 99, 0.4);

		if(ringPosition == RingPosition.ONE) {
			moveTowardsRedAlliance(20, 99, 0.25);
		} else if(ringPosition == RingPosition.NONE) {
			moveTowardsRedAlliance(49, 99, 0.25);
		} else if(ringPosition == RingPosition.FOUR) {
			moveTowardsRedAlliance(51, 99, 0.25);
		}

		if(ringPosition == RingPosition.ONE) {
			robot.drivetrain.runDistance(25,25, 99, 0.4);
		} else if(ringPosition == RingPosition.FOUR) {
			robot.drivetrain.runDistance(53,53, 99, 0.4);
		}

		robot.dropWobble();

		sleep(2000);

		//robot.drivetrain.runDistance(-5, -5, 99, 0.4);

		if(ringPosition == RingPosition.ONE) {
			moveTowardsRedAlliance(20, 99, 0.4);
		} else if(ringPosition == RingPosition.NONE) {
			moveTowardsBlueAlliance(5, 99, 0.4);
		} else if(ringPosition == RingPosition.FOUR) {
			//moveTowardsBlueAlliance(1, 99, 0.4);
		}

		if(ringPosition == RingPosition.NONE) {
			robot.drivetrain.runDistance(-28, -28, 99, 0.4);
		} else if(ringPosition == RingPosition.ONE) {
			robot.drivetrain.runDistance(-58, -58, 99, 0.4);
		}else if(ringPosition == RingPosition.FOUR) {
			robot.drivetrain.runDistance(-83, -83, 99, 0.5);
		}

		robot.wobbleGoalArm.giveArmPower(-0.55);
		sleep(200);
		robot.wobbleGoalArm.releaseWobbleGoal();
		sleep(785);
		robot.drivetrain.runDistance(-8, -8, 99, 0.4);

		robot.wobbleGoalArm.giveArmPower(0);
		sleep(200);
		robot.wobbleGoalArm.grabWobbleGoal();
		sleep(250);

		//if(ringPosition == RingPosition.FOUR) {
		if(true) {
			sleep(450);
			robot.wobbleGoalArm.giveArmPower(1.00);
			sleep(525);
			robot.wobbleGoalArm.giveArmPower(0);
		}

		robot.drivetrain.turnDegrees(180, true, 0.4);

		// Moving different because of 180 turn
		if(ringPosition == RingPosition.ONE) {
			moveTowardsRedAlliance(20, 99, 0.4); // Now towards blue alliance
		} else {
			moveTowardsBlueAlliance(5, 99, 0.4); // Now towards red alliance
		}

		if(ringPosition == RingPosition.NONE) {
			robot.drivetrain.runDistance(-24, -24, 99, 0.4);
		} else if(ringPosition == RingPosition.ONE) {
			robot.drivetrain.runDistance(-47, -47, 99, 0.4);
		} else if(ringPosition == RingPosition.FOUR) {
			robot.drivetrain.runDistance(-83, -83, 99, 0.4);
		}

		robot.wobbleGoalArm.releaseWobbleGoal();
		sleep(250);
		if(ringPosition != RingPosition.FOUR) {
			robot.wobbleGoalArm.raiseArm();
		}

		if(ringPosition == RingPosition.NONE) {
			moveTowardsRedAlliance(25, 99, 0.4);
			robot.drivetrain.runDistance(-20, -20, 99, 0.4);
		} else if(ringPosition == RingPosition.ONE) {
			robot.drivetrain.runDistance(8, 8, 99, 0.4);
		} else if(ringPosition == RingPosition.FOUR) {
			robot.drivetrain.runDistance(35, 35, 99, 0.4);
		}

		robot.resetWobble();

		logger.statusLog(step++, "Stopping");
		robot.stopAllMotors();
	}

}
