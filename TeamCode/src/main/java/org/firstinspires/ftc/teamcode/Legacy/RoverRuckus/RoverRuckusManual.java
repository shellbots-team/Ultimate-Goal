package org.firstinspires.ftc.teamcode.Legacy.RoverRuckus;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by shell on 09/10/2019.
 */

@TeleOp
public class RoverRuckusManual extends LinearOpMode {

	// Declare motors/servos/variables
	private DcMotor left;
	private DcMotor right;


	@Override
	public void runOpMode() {

		// Initialize motors/servos
		left = hardwareMap.get(DcMotor.class, "left");
		right = hardwareMap.get(DcMotor.class, "right");

		left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

		left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

		// Set status
		telemetry.addData("Status", "Initialized");
		telemetry.update();

		waitForStart();

		// Do this while program is running
		while (opModeIsActive()) {

			if (this.gamepad1.x) {
			} else if (this.gamepad1.b) {
			} else if (this.gamepad1.y) {
			} else if (this.gamepad1.a) {
			}

			if (this.gamepad1.dpad_left) {
			} else if (this.gamepad1.dpad_right) {
			}

			if (this.gamepad1.right_trigger > 0.5) { // When right trigger is clicked
			} else if (this.gamepad1.left_trigger > 0.5) { // When left trigger is clicked
			}

			if (this.gamepad1.dpad_up == this.gamepad1.dpad_down) {
			} else if (this.gamepad1.dpad_up) {
			} else if (this.gamepad1.dpad_down) {
			}

			double leftPower = gamepad1.left_stick_y;
			double rightPower = gamepad1.right_stick_y;
			left.setPower(leftPower);
			right.setPower(rightPower);

			// Display information about the motors
			telemetry.addData("Left power should be ", leftPower);
			telemetry.addData("Right power should be ", rightPower);
			telemetry.addData("Left power is ", left.getPower());
			telemetry.addData("Right power is ", right.getPower());
			telemetry.addData("Status", "Running");
			telemetry.update();
		}
	}
}
