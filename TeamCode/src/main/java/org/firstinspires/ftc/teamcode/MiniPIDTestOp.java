package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by ssuri on 10/9/17.
 *
 */

@Autonomous(name = "Mini PID Test")
public class MiniPIDTestOp extends PIDTestOp
{
    @Override
    public void init()
    {
        pid = new PIDAngleControl(this);
        robot = new MiniRobot7604(this);
    }
}
