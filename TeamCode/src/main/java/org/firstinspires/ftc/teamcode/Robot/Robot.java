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

import android.util.Log;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorBNO055IMUCalibration;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Logger;

import java.util.Locale;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.INCH;

/**
 * This is NOT an opmode.
 * <p>
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 * <p>
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 * <p>
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Manipulator drive motor:  "left_arm"
 * Servo channel:  Servo to open left claw:  "left_hand"
 * Servo channel:  Servo to open right claw: "right_hand"
 */
public class Robot {

	private Logger logger = null;
	public Drivetrain drivetrain = null;
	public Arm arm = null;
	public ObjectDetection objectDetection = null;
	public Grabber grabber = null;
	public TouchSensor minTouch = null;
	public TouchSensor maxTouch = null;
	public CRServo capstoneServo = null;
	public DistanceSensor leftDistanceSensor = null;
	public DistanceSensor rightDistanceSensor = null;

	public CRServo leftGrip = null;
	public CRServo rightGrip = null;

	private HardwareMap hardwareMap = null;
	private Telemetry telemetry = null;
	private OpMode opmode = null;


	/* Constructor */
	public Robot() {

	}

	/* Initialize standard Hardware interfaces */
	public void init(HardwareMap hardwareMap, Telemetry telemetry, OpMode opmode) {
		this.hardwareMap = hardwareMap;
		this.telemetry = telemetry;
		this.opmode = opmode;

		logger = new Logger(telemetry);
		drivetrain = new Drivetrain(opmode);
		arm = new Arm(opmode);
		objectDetection = new ObjectDetection(hardwareMap, telemetry);
		grabber = new Grabber(opmode);

		drivetrain.init(
				telemetry,
				this.hardwareMap.get(DcMotor.class, "frontLeft"),
				this.hardwareMap.get(DcMotor.class, "frontRight"),
				this.hardwareMap.get(DcMotor.class, "backLeft"),
				this.hardwareMap.get(DcMotor.class, "backRight")
		);

		arm.init(
				telemetry,
				this.hardwareMap.get(DcMotor.class, "leftArm"),
				this.hardwareMap.get(DcMotor.class, "rightArm"),
				this.hardwareMap.get(DcMotor.class, "extendArm"),
				this.hardwareMap.get(CRServo.class, "rightHand"),
				this.hardwareMap.get(DcMotor.class, "elevateArm")
		);

		grabber.init(
				telemetry,
				this.hardwareMap.get(CRServo.class, "baseRightServo"),
				this.hardwareMap.get(CRServo.class, "altRightServo"),
				this.hardwareMap.get(CRServo.class, "baseLeftServo"),
				this.hardwareMap.get(CRServo.class, "altLeftServo")
		);

		// Define and initialize ALL installed servos.
		leftGrip = this.hardwareMap.get(CRServo.class, "leftGrip");
		rightGrip = this.hardwareMap.get(CRServo.class, "rightGrip");

		capstoneServo = this.hardwareMap.get(CRServo.class, "capstoneServo");

		leftDistanceSensor = this.hardwareMap.get(DistanceSensor.class, "leftDistance");
		rightDistanceSensor = this.hardwareMap.get(DistanceSensor.class, "rightDistance");

		minTouch = this.hardwareMap.get(TouchSensor.class, "minTouch");
		maxTouch = this.hardwareMap.get(TouchSensor.class, "maxTouch");

	}

	public void raiseCapstone() {
		capstoneServo.setPower(-1.0);
	}

	public void lowerCapstone() {
		capstoneServo.setPower(1.0);
	}

	public void stopCapstone() {
		capstoneServo.setPower(0.0);
	}

	public void releaseFoundation() {
		setServoPosition(leftGrip, 1);
		setServoPosition(rightGrip, 0);
	}

	public void grabFoundation() {
		setServoPosition(leftGrip, 0);
		setServoPosition(rightGrip, 0.9);
	}

	public static void setServoPosition(CRServo crservo, double position) {
		crservo.getController().setServoPosition(crservo.getPortNumber(), position);
	}

	/**
	 * Sets the power for all passed motors to a given power
	 *
	 * @param power  The power to set the motors to (-1.0 to 1.0)
	 * @param motors The motors to set to the given power
	 */
	protected void setSpecificPowers(double power, DcMotor... motors) {
		for (DcMotor motor : motors) {
			motor.setPower(power);
		}
	}

	protected void setRunMode(DcMotor.RunMode runMode, DcMotor... motors) {
		for (DcMotor motor : motors) {
			motor.setMode(runMode);
		}
	}

	private boolean opModeIsActive() {
		if (opmode instanceof LinearOpMode) {
			return ((LinearOpMode) opmode).opModeIsActive();
		}
		return true;
	}

	public void logTeleOpData() {
		drivetrain.logTeleOpData();
		arm.logTeleOpData();
	}

	public void driveUntilDistance(double speed, double distance) {
		driveUntilDistance(speed, distance, distance);
	}

	public void driveUntilDistance(double speed, double leftFinalDistance, double rightFinalDistance) {
		logger.completeLog("Status", "Began reading distance");
		while(opModeIsActive()) {
			double leftDistance = leftDistanceSensor.getDistance(INCH);
			double rightDistance = rightDistanceSensor.getDistance(INCH) - 1.6;

			double chance = 2;
			if(Math.abs(leftDistance - leftFinalDistance) < chance && Math.abs(rightDistance - rightFinalDistance) < chance) { break; }
			if(leftDistance < leftFinalDistance && rightDistance < rightFinalDistance) {
				drivetrain.setAllPowers(speed);
			} else if(leftDistance < leftFinalDistance) {
				drivetrain.setIndividualPowers(speed, -speed, speed, -speed);
			} else if(rightDistance < rightFinalDistance) {
				drivetrain.setIndividualPowers(-speed, speed, -speed, speed);
			} else {
				drivetrain.setAllPowers(-speed);
			}
			logger.numberLog("Left Distance", leftDistance);
			logger.numberLog("Right Distance", rightDistance);
			if(rightDistance > 200 || leftDistance > 200) { throw new RuntimeException("Distance sensor broken"); }
		}
		drivetrain.setAllPowers(0);
	}

	public void stopAllMotors() {
		drivetrain.stopAllMotors();
		arm.stopAllMotors();
	}

}