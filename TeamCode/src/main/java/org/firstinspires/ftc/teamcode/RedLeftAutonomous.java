package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by ssuri on 1/23/18.
 *
 */

@Autonomous(name = "RedLeftAutonomous")
public class RedLeftAutonomous extends OpMode
{
    private OpMode mainOp = new AutonomousCode(AutonomousCode.Position.RedLeft);

    @Override public void init()
    {
        mainOp.hardwareMap = hardwareMap;
        mainOp.gamepad1 = gamepad1;
        mainOp.gamepad2 = gamepad2;
        mainOp.telemetry = telemetry;
        mainOp.init();
    }
    @Override public void init_loop() { mainOp.init_loop(); }
    @Override public void start() { mainOp.start(); }
    @Override public void loop() { mainOp.loop(); }
    @Override public void stop() { /*mainOp.stop();*/ }
}
