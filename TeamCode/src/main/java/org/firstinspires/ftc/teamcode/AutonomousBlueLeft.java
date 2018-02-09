package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by ssuri on 1/23/18.
 *
 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "AutonomousBlueLeft")
public class AutonomousBlueLeft extends LinearOpMode
{
    @Override
    public void runOpMode()
    {
        AbstractLinearOpMode mainOp = new AutonomousMain(AutonomousMain.Location.BlueLeft);
        mainOp.callingOpMode = this;
        mainOp.initVars();

        mainOp.initLinear();
        waitForStart();
        mainOp.runLinear();
    }
}
