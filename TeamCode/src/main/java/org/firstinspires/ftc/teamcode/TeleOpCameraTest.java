package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot.Robot;
import org.firstinspires.ftc.teamcode.Robot.VisionBot;
import org.firstinspires.ftc.teamcode.vision.SkystoneDeterminationPipeline;


/**
 * Created by shell on 9/26/2020.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(group = "Manual", name = "Camera Test Manual")
public class TeleOpCameraTest extends OpMode {

	private VisionBot robot = new VisionBot();
	private Logger logger = null;

	/**
	 * Run once after INIT is pushed
	 */
	@Override
	public void init() {
		robot.init(hardwareMap, telemetry, this);
		logger = new Logger(telemetry);
		this.msStuckDetectStop = 60000;

		// Step 0 - Initialized
		logger.statusLog(0, "Initialized");
		robot.cameraVision.start();

	}

	@Override
	public void loop() {
		SkystoneDeterminationPipeline.RingPosition rp = robot.cameraVision.getPosition();
		int analysis = robot.cameraVision.getAnalysis();
		logger.completeLog("14736: Ring Position", rp.toString() + " " + String.valueOf(analysis));
		logger.update();
	}

	@Override
	public void stop() {
		robot.cameraVision.end();
		logger.completeLog("Status", "Stopped");
		logger.update();
	}

}
