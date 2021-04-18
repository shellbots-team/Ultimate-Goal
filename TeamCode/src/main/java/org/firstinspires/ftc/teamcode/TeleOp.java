package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Robot.Robot;


/**
 * Created by shell on 09/10/2019.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(group = "Manual", name = "Manual Mode")
public class TeleOp extends OpMode {

	private static final boolean TUNING = true;
	private Robot robot = new Robot();
	private Logger logger = null;
	private double speed = 0.5;
	private double shootSpeed = 0.835;
	private int count = 0;
	private boolean last_g2_x = false;
	private boolean last_g2_b = false;
	private boolean wobbleOpen = false;
	private boolean grabOpen = false;
	private double outerIntake = 0;
	private double interIntake = 0;
	private boolean dropServo = false;

	/**
	 * Run once after INIT is pushed
	 */
	@Override
	public void init() {
		robot.init(hardwareMap, telemetry, this, false);
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


		/*
		// |----------------------------------------------------------------------------------------
		Gamepad 1: (driver 1)
		joysticks = drivetrain
		right bumper = outer intake and inner intake
	  	left bumper = outer intake and inner intake reversed
	  	right trigger = full speed
	  	left trigger = half speed
		y = drop outer intake

		// |----------------------------------------------------------------------------------------
		Gamepad 2: (driver 2)
		up dpad = wobble arm up
		down dpad = wobble arm down
		x = grab wobble goal
		b = release wobble goal
		left trigger = charge shooter
		right trigger = launch ring
		*/


	@Override
	public void loop() {

		// |----------------------------------------------------------------------------------------
		// Gamepad 1: (driver 1)


		// Move according to player 1's joysticks
		singleJoystickDrive();

		if (this.gamepad1.y == true) {
			robot.intake.drop();
		}

		if (this.gamepad1.right_bumper == true) {
			robot.intake.outerIntake(1);
			robot.intake.innerIntake(1);
		}
		else if (this.gamepad1.right_bumper == false) {
			robot.intake.outerIntake(0);
			robot.intake.innerIntake(0);
		}

		if (this.gamepad1.left_bumper == true) {
			robot.intake.outerIntake(-1);
			robot.intake.innerIntake(-1);
		}
		else if (this.gamepad1.left_bumper == false) {
			robot.intake.outerIntake(0);
			robot.intake.innerIntake(0);
		}

		// Change driving speed
		if (this.gamepad1.right_trigger > 0.5) {
			speed = 1.0;
		}
		else if (this.gamepad1.left_trigger > 0.5) {
			speed = 0.5;
		}


		// |----------------------------------------------------------------------------------------
		// Gamepad 2: (driver 2)


		if (gamepad2.a) {
			if (count % 100 == 0) {
				shootSpeed -= 0.01;
				if (shootSpeed < 0) {
					shootSpeed = 0;
				}
			}
			count++;
		} else if (gamepad2.y) {
			if (count % 100 == 0) {
				shootSpeed += 0.01;
			}
			if (shootSpeed > 1) {
				shootSpeed = 1;
			}
			count++;
		} else {
			count = 0;
		}

		if(gamepad2.right_trigger > 0.5) {
			robot.launcher.push();
		} else {
			robot.launcher.reset();
		}

		if(gamepad2.left_trigger > 0.5) {
			robot.launcher.run(shootSpeed);
		} else {
			robot.launcher.run(0);
		}

		if(gamepad2.dpad_up) {
			robot.wobbleGoalArm.giveArmPower(-0.6);
		} else if(gamepad2.dpad_down) {
			robot.wobbleGoalArm.giveArmPower(0.6);
		} else {
			robot.wobbleGoalArm.giveArmPower(0);
		}

		if(gamepad2.x) {
			robot.wobbleGoalArm.grabWobbleGoal();
		} else if(gamepad2.b) {
			robot.wobbleGoalArm.releaseWobbleGoal();
		}

		telemetry.addData("Launch Speed", shootSpeed);
		telemetry.update();
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