package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by ssuri on 1/23/18.
 *
 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "AutonomousBlueRight")
public class AutonomousBlueRight extends LinearOpMode
{
    @Override
    public void runOpMode()
    {
        AbstractLinearOpMode mainOp = new AutonomousMain(AutonomousMain.Location.BlueRight);
        mainOp.callingOpMode = this;
        mainOp.initVars();

        mainOp.startLinear();
        waitForStart();
        mainOp.runLinear();
    }
}
