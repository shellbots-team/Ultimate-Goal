package org.firstinspires.ftc.teamcode.Legacy;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by shell on 09/10/2019.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Basic Linear Opmode", group = "Manual")
@Disabled
public class ManualMode extends LinearOpMode {

	// Declare motors/servos/variables
	private DcMotor myMotor;


	@Override
	public void runOpMode() {

		// Initialize motors/servos
		myMotor = hardwareMap.get(DcMotor.class, "myMotor");

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

			// Display information about the motors
			telemetry.addData("MyMotor power is ", myMotor.getPower());
			telemetry.addData("Status", "Running");
			telemetry.update();
		}
	}
}
