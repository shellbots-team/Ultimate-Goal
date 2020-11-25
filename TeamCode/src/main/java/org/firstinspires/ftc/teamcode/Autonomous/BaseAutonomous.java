package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Logger;
import org.firstinspires.ftc.teamcode.Robot.Robot;

/**
 * Created by shell on 9/26/2020.
 */

@Disabled
@Autonomous
public abstract class BaseAutonomous extends LinearOpMode  {

    public Robot robot = new Robot();
    public Logger logger = null;
    public int step = 0;

    void moveTowardsBlueAlliance(double distance, double maxSeconds, double speed) {
        robot.drivetrain.runDistance(distance, -distance, -distance, distance, maxSeconds, speed);
        //TODO: not sure which direction this drives
    }

    void moveTowardsRedAlliance(double distance, double maxSeconds, double speed) {
        robot.drivetrain.runDistance(-distance, distance, distance, -distance, maxSeconds, speed);
        //TODO: not sure which direction this drives
    }

    void moveTowardsDropZone(double distance, double maxSeconds, double speed) {
        robot.drivetrain.runDistance(distance, distance, -distance, -distance, maxSeconds, speed);
        //TODO: not sure which direction this drives
    }

    void moveTowardsTower(double distance, double maxSeconds, double speed) {
        robot.drivetrain.runDistance(-distance, -distance, distance, distance, maxSeconds, speed);
        //TODO: not sure which direction this drives
    }
    @Override
    public void runOpMode() {
        robot.init(hardwareMap, telemetry, this);
        logger = new Logger(telemetry);
    }
}