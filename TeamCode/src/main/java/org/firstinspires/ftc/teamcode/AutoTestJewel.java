package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by ssuri on 1/28/18.
 */

@Autonomous(name="AutoTestJewel")
public class AutoTestJewel extends LinearOpMode
{
    Robot7604 bot;
    @Override
    public void runOpMode() throws InterruptedException
    {
        bot = new Robot7604(this);
        waitForStart();

        bot.ColorStick.setPosition(0.75);
        bot.CSensor.enableLed(true);

        while(opModeIsActive())
        {
            double raw = bot.CSensor.getRawLightDetected();
            telemetry.addData("raw", raw);
            telemetry.addData("color", (bot.CSensor.getRawLightDetected() < 1.55) ? "blue" : "red");
            telemetry.update();
        }
    }
}
