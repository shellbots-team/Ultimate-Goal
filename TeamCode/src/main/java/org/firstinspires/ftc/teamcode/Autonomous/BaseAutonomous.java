package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Logger;
import org.firstinspires.ftc.teamcode.Robot.Robot;
import org.firstinspires.ftc.teamcode.vision.SkystoneDeterminationPipeline.RingPosition;

/**
 * Created by shell on 9/26/2020.
 */

@Disabled
@Autonomous
public abstract class BaseAutonomous extends LinearOpMode  {

	public Robot robot = new Robot();
	public Logger logger = null;
	public int step = 0;

	void moveTowardsBlueAlliance(double distance, double maxSeconds, double speed) {
		robot.drivetrain.runDistance(-distance, distance, distance, -distance, maxSeconds, speed);
	}

	void moveTowardsRedAlliance(double distance, double maxSeconds, double speed) {
		robot.drivetrain.runDistance(distance, -distance, -distance, distance, maxSeconds, speed);
	}

	void moveTowardsDropZone(double distance, double maxSeconds, double speed) {
		robot.drivetrain.runDistance(distance, distance, -distance, -distance, maxSeconds, speed);
		//TODO: not sure which direction this drives
	}

	void moveTowardsTower(double distance, double maxSeconds, double speed) {
		robot.drivetrain.runDistance(-distance, -distance, distance, distance, maxSeconds, speed);
		//TODO: not sure which direction this drives
	}

	@Override
	public void runOpMode() {
		this.msStuckDetectStop = 60000;
		robot.init(hardwareMap, telemetry, this);
		logger = new Logger(telemetry);
		robot.cameraVision.start();
		sleep(1000);
	}
}