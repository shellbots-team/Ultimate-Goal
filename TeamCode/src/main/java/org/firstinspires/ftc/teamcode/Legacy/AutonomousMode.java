package org.firstinspires.ftc.teamcode.Legacy;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot.Robot;

/**
 * Created by shell on 09/10/2019.
 */

@Autonomous(group = "Autonomous", name = "Autonomous")
@Disabled
public class AutonomousMode extends LinearOpMode {

	// Declare motors/servos
	Robot robot = new Robot();

	@Override
	public void runOpMode() {

		// Initialize motors/servos
		robot.init(hardwareMap, telemetry, this);

		// Setting status to "Ready to run"
		telemetry.addData("Status", "Ready To Run");
		telemetry.update();

		// Waiting until user presses start
		waitForStart();

		// Step 1
		telemetry.addData("Status", "Step 1");
		telemetry.update();

		// Take Action
		sleep(1000); // Sleep for 1 second

		telemetry.addData("Status", "Finished");
		telemetry.update();

	}
}
