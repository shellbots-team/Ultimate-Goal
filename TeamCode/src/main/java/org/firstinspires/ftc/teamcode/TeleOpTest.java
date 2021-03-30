package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Robot.Robot;


/**
 * Created by shell on 09/10/2019.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(group = "Manual", name = "Manual Mode Test")
public class TeleOpTest extends OpMode {

	private static final boolean TUNING = true;
	private Robot robot = new Robot();
	private Logger logger = null;
	private double speed = 0.5;
	private double shootSpeed = 1;
	private int count = 0;
	private boolean last_g2_x = false;
	private boolean last_g2_b = false;
	private boolean wobbleOpen = false;
	private boolean grabOpen = false;
	private boolean frontWheel = true;

	/**
	 * Run once after INIT is pushed
	 */
	@Override
	public void init() {
		robot.init(hardwareMap, telemetry, this, false);
		robot.drivetrain.runWithoutEncoders();
		logger = new Logger(telemetry);

		// Step 0 - Initialized
		logger.statusLog(0, "Initialized");

	}

	/**
	 * Runs constantly after INIT is pushed but before PLAY is pushed
	 */
	@Override
	public void init_loop() {

	}

	/**
	 * Runs once after PLAY is pushed
	 */
	@Override
	public void start() {
		// Step 1 - Playing
		logger.statusLog(0, "Playing");
	}

	@Override
	public void loop() {

		// Player 1

		// Move according to player 1's joysticks
		singleJoystickDrive();

		// Change driving speed
		if (this.gamepad1.right_trigger > 0.5) {
			speed = 1.0;
		} else if (this.gamepad1.left_trigger > 0.5) {
			speed = 0.5;
		}

		if(this.gamepad1.y) {
			frontWheel = true;
		} else if(this.gamepad1.a) {
			frontWheel = false;
		}

	}

	/**
	 * Runs once after STOP is pushed
	 */
	@Override
	public void stop() {
		robot.stopAllMotors();
		logger.completeLog("Status", "Stopped");
		logger.update();
	}

	private void singleJoystickDrive() {
		double leftX = this.gamepad1.left_stick_x;
		double leftY = this.gamepad1.left_stick_y;
		double rightX = this.gamepad1.right_stick_x;
		double rightY = this.gamepad1.right_stick_y;

		double[] motorPowers = new double[4];
		if(frontWheel) {
			motorPowers[0] = leftY;// -+
			motorPowers[1] = rightY; // (leftY+leftX+rightX);// +-
			motorPowers[2] = 0; // (leftY+leftX-rightX);// ++
			motorPowers[3] = 0; // (leftY-leftX+rightX);// --
		} else {
			motorPowers[0] = 0;// -+
			motorPowers[1] = 0; // (leftY+leftX+rightX);// +-
			motorPowers[2] = leftY; // (leftY+leftX-rightX);// ++
			motorPowers[3] = rightY; // (leftY-leftX+rightX);// --
		}

		double max = Math.abs(getLargestAbsVal(motorPowers));
		if(max < 1) { max = 1; }

		for(int i = 0; i < motorPowers.length; i++) {
			motorPowers[i] /= max;
		}

		for(int i = 0; i < motorPowers.length; i++) {
			if(motorPowers[i] < 0.05 && motorPowers[i] > -0.05) { motorPowers[i] = 0.0; }
			if(motorPowers[i] > 1.0) { motorPowers[i] = 1.0; }
			if(motorPowers[i] < -1.0) { motorPowers[i] = -1.0; }
			motorPowers[i] *= speed;
			logger.numberLog("Motor" + i, motorPowers[i]);
		}

		robot.drivetrain.setIndividualPowers(motorPowers);
	}

	private double getLargestAbsVal(double[] values) {
		double max = 0;
		for(double val : values) {
			if(Math.abs(val) > max) { max = Math.abs(val); }
		}
		return max;
	}

}