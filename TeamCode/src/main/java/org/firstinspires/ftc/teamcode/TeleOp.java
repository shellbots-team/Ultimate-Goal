package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot.Grabber;
import org.firstinspires.ftc.teamcode.Robot.Robot;


/**
 * Created by shell on 09/10/2019.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(group = "Manual", name = "Manual Mode")
public class TeleOp extends OpMode {

	private Robot robot = new Robot();
	private Logger logger = null;
	private String elevatorLimit = "";
	private int switchCount = 0;

	private double speed = 0.5;
	private double armSpeed = 1.0;

	private boolean last_x = false;
	private boolean last_a = false;
	private boolean last_y = false;
	private boolean last_b = false;

	private boolean manualOverride = false;

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

	}

	/**
	 * Runs constantly after INIT is pushed but before PLAY is pushed
	 */
	@Override
	public void init_loop() {

	}

	ElapsedTime timer = new ElapsedTime();

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

		timer.reset();

		/* Controller Layouts
		 *
		 * Controller 1 - "Body Controller"
		 *      Left Trigger     - Set full speed
		 *      Right Trigger    - Set half speed
		 *
		 *      Dpad Up          - Release Foundation / Move lower servos up
		 *      Dpad Down        - Grab Foundation / Move lower servos down
		 *
		 *      Left Joystick Y  - Move the robot forward/backward
		 *      Left Joystick X  - Move the robot left/right
		 *
		 *      Right Joystick X - Turn the robot
		 *
		 *      A + Y = Single joystick drive
		 *      X + B = Two joystick drive
		 *
		 * Controller 2 - "Arm Controller"
		 *      Right Trigger    - Extends arm
		 *      Left Trigger     - Detracts arm
		 *
		 *      Dpad Up          - Raise the arm
		 *      Dpad Down        - Lower the arm
		 *
		 *      Y Button         - Grab blocks with the hand
		 *      A Button         - Release blocks with the hand
		 */

		/*
		 * Controller 1 settings
		 */

		singleJoystickDrive();

		if (this.gamepad1.right_trigger > 0.5) {
			if(speed == 0.5) { switchCount += 1; }
			speed = 1.0;
		} else if (this.gamepad1.left_trigger > 0.5) {
			if(speed == 1.0) { switchCount += 1; }
			speed = 0.5;
		} else if (this.gamepad1.right_bumper) {
			speed = 0.25;
		}

		if (this.gamepad1.left_bumper) {
			robot.grabber.home();
		}

		if (this.gamepad1.dpad_up) {
			robot.releaseFoundation();
		} else if (this.gamepad1.dpad_down) {
			robot.grabFoundation();
		}

		if (this.gamepad1.dpad_left) {
			robot.lowerCapstone();
		} else if (this.gamepad1.dpad_right) {
			robot.raiseCapstone();
		} else if(this.gamepad2.x) {
			robot.lowerCapstone();
		} else if(this.gamepad2.b) {
			robot.raiseCapstone();
		} else {
			robot.stopCapstone();
		}

		if (this.gamepad1.x && this.gamepad1.x != last_x) { // Left Big Arm
			robot.grabber.flip(Grabber.Level.BASE, Grabber.Side.LEFT);
		}
		if (this.gamepad1.a && this.gamepad1.a != last_a) { // Left Little Arm
			robot.grabber.flip(Grabber.Level.ALT, Grabber.Side.LEFT);
		}
		if (this.gamepad1.y && this.gamepad1.y != last_y) { // Right Big Arm
			robot.grabber.flip(Grabber.Level.BASE, Grabber.Side.RIGHT);
		}
		if (this.gamepad1.b && this.gamepad1.b != last_b) { // Right Little Arm
			robot.grabber.flip(Grabber.Level.ALT, Grabber.Side.RIGHT);
		}

		last_x = this.gamepad1.x;
		last_a = this.gamepad1.a;
		last_y = this.gamepad1.y;
		last_b = this.gamepad1.b;

		/*
		 * Controller 2 settings
		 */

		if(this.gamepad2.right_stick_button && this.gamepad2.x) {
			manualOverride = true;
		}

		if (this.gamepad2.right_trigger > 0.5) {
			robot.arm.extendWithPower(0.55);
		} else if (this.gamepad2.left_trigger > 0.5) {
			robot.arm.extendWithPower(-0.55);
		} else {
			robot.arm.extendWithPower(0);
		}

		if (this.gamepad2.right_bumper) {
			armSpeed = 1.0;
		}
		if (this.gamepad2.left_bumper) {
			armSpeed = 0.5;
		}

		if (this.gamepad2.dpad_up) {
			robot.arm.raiseWithPower(1 * armSpeed);
		} else if (this.gamepad2.dpad_down) {
			robot.arm.lowerWithPower(0.20);
		} else {
			robot.arm.raiseWithPower(0);
		}

		if (this.gamepad2.dpad_left) {
		}
		if (this.gamepad2.dpad_right) {
		}

		if(this.gamepad2.y) {
			robot.arm.grabHand();
		} else if (this.gamepad2.a) {
			robot.arm.releaseHand();
		}

		if (this.gamepad2.x) {
		}
		if (this.gamepad2.b) {
		}

		if (this.gamepad2.right_stick_button && (manualOverride || !robot.maxTouch.isPressed())) { // Up
			robot.arm.elevateWithPower(-1.0);
		} else if (this.gamepad2.left_stick_button && (manualOverride || !robot.minTouch.isPressed())) { // Down
			robot.arm.elevateWithPower(1.0);
		} else {
			robot.arm.elevateWithPower(0);
		}

		elevatorLimit = "None";
		if(robot.minTouch.isPressed() && robot.maxTouch.isPressed()) {
			elevatorLimit = "Error";
		} else if(robot.minTouch.isPressed()) {
			elevatorLimit = "Minimum";
		} else if(robot.maxTouch.isPressed()) {
			elevatorLimit = "Maximum";
		}

//		logger.numberLog("Speed", speed);
//		logger.completeLog("Elevator Limit?", elevatorLimit);
//		logger.completeLog("Elevator Manually Overridden?", manualOverride ? "True" : "False");
//		logger.numberLog("Switches full-half speed", switchCount);
//		robot.logTeleOpData();
		logger.numberLog("LeftDistance", robot.leftDistanceSensor.getDistance(DistanceUnit.INCH));
		logger.numberLog("RightDistance", robot.rightDistanceSensor.getDistance(DistanceUnit.INCH) - 1.6);
		logger.update();

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

		double[] motorPowers = new double[4];
		motorPowers[0] = (leftY-leftX-rightX);// -+
		motorPowers[1] = (leftY+leftX+rightX);// +-
		motorPowers[2] = (leftY+leftX-rightX);// ++
		motorPowers[3] = (leftY-leftX+rightX);// --

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