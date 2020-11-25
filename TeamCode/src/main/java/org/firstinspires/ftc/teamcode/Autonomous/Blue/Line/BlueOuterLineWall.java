package org.firstinspires.ftc.teamcode.Autonomous.Blue.Line;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.BaseAutonomous;

/**
 * Created by shell on 9/28/2020.
 */

@Autonomous
public class BlueOuterLineWall extends BaseAutonomous {

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

