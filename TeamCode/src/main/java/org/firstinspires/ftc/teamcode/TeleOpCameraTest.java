package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Robot.CameraVision;
import org.firstinspires.ftc.teamcode.Robot.Robot;
import org.firstinspires.ftc.teamcode.vision.SkystoneDeterminationPipeline;


/**
 * Created by shell on 9/26/2020.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(group = "Manual", name = "Camera Test Manual")
public class TeleOpCameraTest extends OpMode {

	private Logger logger = null;
	private CameraVision cameraVision = null;

	@Override
	public void init() {
		cameraVision = new CameraVision(hardwareMap);
		logger = new Logger(telemetry);
		this.msStuckDetectStop = 60000;

		// Step 0 - Initialized
		logger.statusLog(0, "Initialized");
		cameraVision.start();
	}

	@Override
	public void loop() {
		SkystoneDeterminationPipeline.RingPosition rp = cameraVision.getPosition();
		int analysis = cameraVision.getAnalysis();
		logger.completeLog("14736: Ring Position", rp.toString() + " " + String.valueOf(analysis));
		logger.update();
	}

	/**
	 * Runs once after STOP is pushed
	 */
	@Override
	public void stop() {
		cameraVision.end();
		logger.completeLog("Status", "Stopped");
		logger.update();
	}
}