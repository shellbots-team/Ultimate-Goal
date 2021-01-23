/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logger;

import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.INCH;

public class Robot {

	private Logger logger = null;
	public Drivetrain drivetrain = null;

	private HardwareMap hardwareMap = null;
	private Telemetry telemetry = null;
	private OpMode opmode = null;


	/* Constructor */
	public Robot() { }

	/**
	 * An initialization method to allocate everything needed for the robot to be setup
	 *
	 * @param hardwareMap The robots hardware map (for physical locations of hardware)
	 * @param telemetry For outputting to the phones console
	 * @param opmode To allow the robot to know if it is running or not
	 */
	public void init(HardwareMap hardwareMap, Telemetry telemetry, OpMode opmode) {
		// Setup basic overall variables
		this.hardwareMap = hardwareMap;
		this.telemetry = telemetry;
		this.opmode = opmode;

		// Initialize specific robot parts
		logger = new Logger(telemetry);
		drivetrain = new Drivetrain(opmode);

		drivetrain.init(
				telemetry,
				this.hardwareMap.get(DcMotor.class, "leftFront"),
				this.hardwareMap.get(DcMotor.class, "rightFront"),
				this.hardwareMap.get(DcMotor.class, "leftRear"),
				this.hardwareMap.get(DcMotor.class, "rightRear")
		);

	}

	// TODO: Bad system to have setServoPosition here and in robot component instead extend CRServo class and implement it
	public static void setServoPosition(CRServo crservo, double position) {
		crservo.getController().setServoPosition(crservo.getPortNumber(), position);
	}

	private boolean opModeIsActive() {
		if (opmode instanceof LinearOpMode) {
			return ((LinearOpMode) opmode).opModeIsActive();
		}
		return true;
	}

	public void logTeleOpData() {
		drivetrain.logTeleOpData();
	}

	public void stopAllMotors() {
		drivetrain.stopAllMotors();
	}

}