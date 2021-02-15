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

		robot.wobbleHand.grab();
		robot.ringStager.grab();
		robot.elevator.open();

		logger.statusLog(step++, "Ready to run");

		waitForStart();

		logger.statusLog(step++, "Scanning ring position");
		robot.cameraVision.end();

		// Scan for the ring and find position
		RingPosition ringPosition = robot.cameraVision.getPosition();
		int analysis = robot.cameraVision.getAnalysis();
		logger.completeLog("RingPosition", ringPosition.toString());
		logger.completeLog("RingAnalysis", String.valueOf(analysis));
		robot.cameraVision.save();

		// Move off wall
		robot.drivetrain.runDistance(70, 1);

		// Move away from rings
		moveTowardsBlueAlliance(60, 99, 0.9);

		robot.bananaShooter.run(0.95);

		robot.ringStager.drop();
		sleep(500);
		robot.ringStager.grab();

		// Move past rings
		robot.drivetrain.runDistance(105, 1);

		robot.elevator.close();

		moveTowardsRedAlliance(55, 99, 0.9);
//		robot.bananaShooter.run(0.97);

		robot.elevator.open();
		sleep(500);
		robot.elevator.close();
		sleep(1000);
		robot.elevator.open();
		sleep(500);

		robot.ringStager.drop();
		sleep(500);
		robot.ringStager.grab();
		sleep(250);
		robot.elevator.close();
		sleep(1000);
		robot.elevator.open();

		robot.bananaShooter.run(0);

		// Move to be aligned with chosen box
		if(ringPosition != RingPosition.ONE) {
			moveTowardsRedAlliance(85, 99, 1);
		}

		// Move to drop in chosen box
		if(ringPosition == RingPosition.NONE) {
			robot.drivetrain.runDistance(40, 1);
		} else if(ringPosition == RingPosition.ONE) {
			robot.drivetrain.runDistance(95, 1);
		} else if(ringPosition == RingPosition.FOUR) {
			robot.drivetrain.runDistance(130, 1);
		}

		// Release wobble goal
		robot.wobbleHand.release();

		// Move so not on wobble goal
		robot.drivetrain.runDistance(-50, 1);

		// Move to align wobble goal with grabber
		if(ringPosition == RingPosition.ONE) {
			 moveTowardsRedAlliance(55, 99, 1);
		} else {
			moveTowardsBlueAlliance(30, 99, 1);
		}

		// Move much closer to the wobble goal
		if(ringPosition == RingPosition.NONE) {
			robot.drivetrain.runDistance(-50, 1);
		} else if(ringPosition == RingPosition.ONE) {
			robot.drivetrain.runDistance(-90, 1);
		} else if(ringPosition == RingPosition.FOUR) {
			robot.drivetrain.runDistance(-130, 1);
		}

		// Grab the wobble goal
		robot.wobbleGoalArm.giveArmPower(-0.5);
		sleep(2000);
		robot.wobbleGoalArm.releaseWobbleGoal();
		robot.drivetrain.runDistance(-20, 1);
		robot.wobbleGoalArm.giveArmPower(0);
		robot.wobbleGoalArm.grabWobbleGoal();
		sleep(250);
		robot.wobbleGoalArm.giveArmPower(0.4);

		// Turn back to look at the boxes
		robot.drivetrain.turnDegrees(1275, true, 1);

		robot.wobbleGoalArm.giveArmPower(0);

		// Move to be aligned with chosen box
		if(ringPosition == RingPosition.ONE) {
			moveTowardsRedAlliance(70, 99, 1); // Actually towards blue alliance
		} else {
			moveTowardsBlueAlliance(30, 99, 1); // Actually red alliance
		}

//		// Raise arm before moving
//		robot.wobbleGoalArm.giveArmPower(0.5);
//		sleep(500);
//		robot.wobbleGoalArm.giveArmPower(0);

		// Move to box
		if(ringPosition == RingPosition.NONE) {
			robot.drivetrain.runDistance(-50, 1);
		} else if(ringPosition == RingPosition.ONE) {
			robot.drivetrain.runDistance(-130, 1);
		} else if(ringPosition == RingPosition.FOUR) {
			robot.drivetrain.runDistance(-150, 1);
		}

		// Drop wobble goal
		robot.wobbleGoalArm.releaseWobbleGoal();
		sleep(250);

		// Move onto line
		if(ringPosition == RingPosition.NONE) {
			robot.drivetrain.runDistance(40, 1);
			moveTowardsRedAlliance(40, 99, 1); // Actually blue alliance
			robot.drivetrain.runDistance(-40, 1);
		} else if(ringPosition == RingPosition.ONE) {
			robot.drivetrain.runDistance(40, 1);
		} else if(ringPosition == RingPosition.FOUR) {
			robot.drivetrain.runDistance(80, 1);
		}

		// Stop
		logger.statusLog(step++, "Stopping");
		robot.stopAllMotors();
	}

}