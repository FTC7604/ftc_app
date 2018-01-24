package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by ssuri on 1/23/18.
 *
 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "AutonomousRedLeft")
public class AutonomousRedLeft extends LinearOpMode
{
    @Override
    public void runOpMode()
    {
        AbstractLinearOpMode mainOp = new AutonomousMain(AutonomousMain.Position.RedLeft);
        mainOp.callingOpMode = this;
        mainOp.initVars();

        mainOp.startLinear();
        waitForStart();
        mainOp.runLinear();
    }
}
