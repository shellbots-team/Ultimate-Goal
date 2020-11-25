package org.firstinspires.ftc.teamcode.Autonomous.Red.Line;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.BaseAutonomous;
import org.firstinspires.ftc.teamcode.Logger;
import org.firstinspires.ftc.teamcode.Robot.Robot;

/**
 * Created by shell on 9/28/2020.
 */

@Autonomous
public class RedOuterLineCenter extends BaseAutonomous {

    @Override
    public void runOpMode() {

        super.runOpMode();

        logger.statusLog(step++, "Ready to run");

        waitForStart();

        logger.statusLog(step++, "Running forward");
        robot.drivetrain.runDistance(4, 4, 8, 0.2);

        logger.statusLog(step++, "Finished");
        robot.stopAllMotors();

    }

}

