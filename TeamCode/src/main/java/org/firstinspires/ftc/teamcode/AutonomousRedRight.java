package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by ssuri on 1/23/18.
 *
 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "AutonomousRedRight")
public class AutonomousRedRight extends LinearOpMode
{
    @Override
    public void runOpMode()
    {
        AbstractLinearOpMode mainOp = new AutonomousMain(AutonomousMain.Location.RedRight);
        mainOp.callingOpMode = this;
        mainOp.initVars();

        mainOp.startLinear();
        waitForStart();
        mainOp.runLinear();
    }
}
