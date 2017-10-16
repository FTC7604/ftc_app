package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by ssuri on 10/9/17.
 *
 */

@Autonomous(name = "PID Test")
public class PIDTestOp extends OpMode
{
    PIDAngleControl pid = null;
    Robot7604 robot = null;

    @Override
    public void init()
    {
        pid = new PIDAngleControl(this);
        robot = new Robot7604(this);
    }

    @Override
    public void start()
    {
        pid.startPID();
    }

    @Override
    public void loop()
    {
        double value = pid.getValue();
        robot.drive(.2f, (Math.PI / 2) - 0.32f, value);
    }




    @Override
    public void stop()
    {
        robot.stop();
        pid.stopPID();
    }
}
