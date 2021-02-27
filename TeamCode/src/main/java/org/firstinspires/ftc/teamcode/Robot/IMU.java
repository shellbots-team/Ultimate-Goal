package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import java.util.Locale;

public class IMU extends RobotComponent {

	private BNO055IMU imu = null;

	IMU(OpMode opMode) { super(opMode); }

	public void init(Telemetry telemetry, BNO055IMU imu) {
		BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
		parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
		parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
		parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
		parameters.loggingEnabled      = true;
		parameters.loggingTag          = "IMU";
		parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

		// Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
		// on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
		// and named "imu".
		this.imu = imu;
		this.imu.initialize(parameters);
	}

	public void start() {
		imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
	}

	public double getHeading() {
		Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
		return formatAngle(angles.angleUnit, angles.firstAngle);
	}

	double formatAngle(AngleUnit angleUnit, double angle) {
		return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
	}

	double formatDegrees(double degrees){
		return AngleUnit.DEGREES.normalize(degrees);
	}

	@Override
	public void stopAllMotors() {

	}

	@Override
	void logTeleOpData() {

	}
}
