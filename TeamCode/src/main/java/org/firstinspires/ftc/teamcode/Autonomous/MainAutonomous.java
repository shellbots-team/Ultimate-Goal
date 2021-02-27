package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.vision.SkystoneDeterminationPipeline.RingPosition;

/**
 * Created by shell on 12/5/2020.
 */

@Autonomous
public class MainAutonomous extends BaseAutonomous {

	private void fixAngle() {
		double angle = robot.imu.getHeading();
		if(angle < -100) { angle = -( 180 + angle ); }
		if(angle > 100) { angle = -( 180 - angle ); }
		if(Math.abs(angle) < 2) { return; }
		boolean clockwise = (angle < 0);
		robot.drivetrain.turnDegrees(angle * 6, clockwise, 1);
		logger.completeLog("IMU", String.valueOf(angle));

	}

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

		robot.bananaShooter.run(0.88);


		// Move off wall
		robot.drivetrain.runDistance(175, 1);
		//fixAngle();

		robot.ringStager.drop();
		sleep(250);
		robot.ringStager.grab();
		sleep(250);

		if(ringPosition == RingPosition.ONE) {
			moveTowardsRedAlliance(48, 99, 1);
		} else {
			moveTowardsRedAlliance(45, 99, 1);
		}
		fixAngle();

		robot.elevator.close();
//		robot.drivetrain.turnDegrees(60, false, 1);
		robot.elevator.open();
		sleep(500);

		robot.elevator.close();
		sleep(1000);
		robot.elevator.open();

		if(ringPosition != RingPosition.FOUR) {
			sleep(500);

			robot.ringStager.drop();
			sleep(500);
			robot.ringStager.grab();
			sleep(250);
			robot.elevator.close();
			sleep(1000);
			robot.elevator.open();
		}

		robot.bananaShooter.run(0);

		// Move to be aligned with chosen box
		if(ringPosition != RingPosition.ONE) {
			moveTowardsRedAlliance(85, 99, 1);
			moveTowardsBlueAlliance(5, 99, 1);
		}
		fixAngle();

		// Move to drop in chosen box
		if(ringPosition == RingPosition.NONE) {
			robot.drivetrain.runDistance(35, 1);
		} else if(ringPosition == RingPosition.ONE) {
			robot.drivetrain.runDistance(95, 1);
		} else if(ringPosition == RingPosition.FOUR) {
			robot.drivetrain.runDistance(170, 1);
		}

		// Release wobble goal
		robot.wobbleHand.release();

		// Move so not on wobble goal
		robot.drivetrain.runDistance(-50, 1);

		// Move to align wobble goal with grabber
		if(ringPosition == RingPosition.ONE) {
			 moveTowardsRedAlliance(68, 99, 1);
		} else if(ringPosition == RingPosition.FOUR) {
			moveTowardsBlueAlliance(14, 99, 1);
		} else {
			moveTowardsBlueAlliance(15, 99, 1);
		}

		// Move much closer to the wobble goal
		if(ringPosition == RingPosition.NONE) {
			robot.drivetrain.runDistance(-45, 1);
		} else if(ringPosition == RingPosition.ONE) {
			robot.drivetrain.runDistance(-108, 1);
		} else if(ringPosition == RingPosition.FOUR) {
			robot.drivetrain.runDistance(-186, 1);
		}

		// Grab the wobble goal
		robot.wobbleGoalArm.giveArmPower(-0.5);
		sleep(1000);
		robot.wobbleGoalArm.releaseWobbleGoal();
		sleep(1000);
		robot.drivetrain.runDistance(-20, 1);
		robot.wobbleGoalArm.giveArmPower(0);
		robot.wobbleGoalArm.grabWobbleGoal();
		sleep(250);
		robot.wobbleGoalArm.giveArmPower(0.4);

		// Turn back to look at the boxes
		robot.drivetrain.turnDegrees(1250, true, 1);
		fixAngle();

		robot.wobbleGoalArm.giveArmPower(0);

//		// Raise arm before moving
//		robot.wobbleGoalArm.giveArmPower(0.5);
//		sleep(500);
//		robot.wobbleGoalArm.giveArmPower(0);

		// Move to box
		if(ringPosition == RingPosition.NONE) {
			robot.drivetrain.runDistance(-50, 1);
		} else if(ringPosition == RingPosition.ONE) {
			moveTowardsBlueAlliance(50, 99, 1);
			moveTowardsRedAlliance(20, 99, 1);
			robot.drivetrain.runDistance(-134, 1);
		} else if(ringPosition == RingPosition.FOUR) {
			robot.drivetrain.runDistance(-176, 1);
		}

		// Move to be aligned with chosen box
		if(ringPosition == RingPosition.ONE) {
			moveTowardsRedAlliance(70, 99, 1); // Actually towards blue alliance
		} else {
			//moveTowardsBlueAlliance(29, 99, 1); // Actually red alliance
		}

		robot.wobbleGoalArm.giveArmPower(-0.35);
		sleep(750);

		// Drop wobble goal
		robot.wobbleGoalArm.releaseWobbleGoal();
		sleep(250);

		// Move onto line
		if(ringPosition == RingPosition.NONE) {
			robot.drivetrain.runDistance(40, 1);
			moveTowardsRedAlliance(40, 99, 1); // Actually blue alliance
			robot.drivetrain.runDistance(-80, 1);
		} else if(ringPosition == RingPosition.ONE) {
			robot.drivetrain.runDistance(35, 1);
		} else if(ringPosition == RingPosition.FOUR) {
			robot.drivetrain.runDistance(80, 1);
		}

		// Stop
		logger.statusLog(step++, "Stopping");
		robot.stopAllMotors();
	}

}