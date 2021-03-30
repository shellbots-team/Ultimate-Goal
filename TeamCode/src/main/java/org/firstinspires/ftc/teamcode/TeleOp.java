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
	private double shootSpeed = 1.00;
	private int count = 0;
	private boolean last_g2_x = false;
	private boolean last_g2_b = false;
	private boolean wobbleOpen = false;
	private boolean grabOpen = false;

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

	@Override
	public void loop() {

		// Player 1

		// TODO: Figure out all the controls

		// Move according to player 1's joysticks
		singleJoystickDrive();

		// Change driving speed
		if (this.gamepad1.right_trigger > 0.5) {
			speed = 1.0;
		} else if (this.gamepad1.left_trigger > 0.5) {
			speed = 0.5;
		}

		// Player 2

		if(TUNING) {
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
		}

		logger.completeLog("Shoot Speed", String.valueOf(shootSpeed));

		if(gamepad2.left_trigger > 0.5) {
			robot.launcher.run(shootSpeed);
		} else {
			robot.launcher.run(0);
		}

		if(gamepad1.dpad_down) { robot.intake.drop(); }

		if(gamepad2.dpad_up) {
			robot.wobbleHand.release();
		} else if(gamepad2.dpad_down) {
			robot.wobbleHand.grab();
		}


		if(!last_g2_x && gamepad2.x) {
			if(wobbleOpen) {
				robot.wobbleGoalArm.releaseWobbleGoal();
			} else {
				robot.wobbleGoalArm.grabWobbleGoal();
			}
			wobbleOpen = !wobbleOpen;
		}
		last_g2_x = gamepad2.x;

		if(!last_g2_b && gamepad2.b) {
			if(grabOpen) {
				robot.launcher.push();
			} else {
				robot.launcher.reset();
			}
			grabOpen = !grabOpen;
		}
		last_g2_b = gamepad2.b;

		if(Math.abs(gamepad2.left_stick_y) > 0.05) {
			robot.wobbleGoalArm.giveArmPower(gamepad2.left_stick_y / 0.8);
		} else {
			robot.wobbleGoalArm.giveArmPower(0);
		}

		if(gamepad2.right_trigger > 0.5) {
			robot.intake.outerIntake(1);
			robot.intake.innerIntake(1);
		} else {
			robot.intake.outerIntake(0);
			robot.intake.innerIntake(0);
		}

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