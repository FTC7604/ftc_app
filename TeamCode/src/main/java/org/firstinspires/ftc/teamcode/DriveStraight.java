package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by ssuri on 10/9/17.
 *
 */

@Autonomous(name = "Mini Drive Straight")
public class DriveStraight extends OpMode
{
    Robot7604 robot = null;

    @Override
    public void init()
    {
        robot = new MiniRobot7604(this);
    }

    @Override
    public void start()
    {
    }

    @Override
    public void loop()
    {
        robot.drive(.2f, 0);
    }

    @Override
    public void stop()
    {
        robot.stop();
    }
}
