package org.firstinspires.ftc.teamcode.Legacy.RoverRuckus;

import android.os.Environment;
import android.util.JsonReader;
import android.util.Log;

import com.google.gson.Gson;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Logger;
import org.firstinspires.ftc.teamcode.Robot.Robot;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

@Autonomous(name="Rover Ruckus Auto", group="a")
public class RoverRuckusAuto extends LinearOpMode {

	@Override
	public void runOpMode() {

		RoverRuckusBot robot = new RoverRuckusBot();
		robot.init(hardwareMap);

		waitForStart();

		Logger logger = new Logger(telemetry);
		String filename = "/storage/emulated/0/custom/gamepad.properties";

		if(!(new File(filename).exists())) {
			logger.completeLog("File", "File doesn't exist; stopping");
			return;
		}

		while (opModeIsActive() && !isStopRequested()) {
			try (InputStream input = new FileInputStream(filename)){

				Properties prop = new Properties();
				prop.load(input);

				if("1".equals(prop.getProperty("w"))) {
					telemetry.addData("File", "w is 1");
					robot.setPower(0.4);
				} else if("1".equals(prop.getProperty("s"))) {
					robot.setPower(-0.4);
					telemetry.addData("File", "s is 1");
				} else {
					telemetry.addData("File", "s is 0");
					robot.setPower(0);
				}

				telemetry.update();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
