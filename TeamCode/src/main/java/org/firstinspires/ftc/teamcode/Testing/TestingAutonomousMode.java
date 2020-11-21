package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by shell on 11/14/2020.
 */

@Autonomous(group = "Autonomous", name = "TestingAutonomous")
public class TestingAutonomousMode extends LinearOpMode {

	// Declare motors/servos
	//Robot robot = new Robot();
	@Override
	public void runOpMode() {

		DcMotor left = this.hardwareMap.get(DcMotor.class, "left");
		DcMotor right = this.hardwareMap.get(DcMotor.class, "right");

		// Initialize motors/servos
		// robot.init(hardwareMap, telemetry, this);

		// Setting status to "Ready to run"
		telemetry.addData("Status", "Ready To Run");
		telemetry.update();

		// Waiting until user presses start
		waitForStart();

		// Step 1
		telemetry.addData("Status", "Step 1");
		telemetry.update();

		float power = 0.6f;

		left.setPower(power);
		right.setPower(power);

		// Take Action
		sleep(1000); // Sleep for 1 second

		left.setPower(0);
		right.setPower(0);

		sleep(500); // Sleep for 1 second

		left.setPower(power);
		right.setPower(-power);

		sleep(1000); // Sleep for 1 second

		left.setPower(0);
		right.setPower(0);

		telemetry.addData("Status", "Finished");
		telemetry.update();

	}
}
