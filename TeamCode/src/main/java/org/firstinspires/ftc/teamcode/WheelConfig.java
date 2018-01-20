package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Declan on 11/13/2017. // Copied in, actual date unknown
 */

@Autonomous(name = "WheelConfig", group = "7604")
public class     WheelConfig extends LinearOpMode {

    @Override
    public void runOpMode () {

        Robot7604 bot = new Robot7604(this);

        waitForStart();

        bot.FrontRight.setPower(1);
        sleep(1000);
        bot.FrontRight.setPower(0);

        bot.FrontLeft.setPower(1);
        sleep(1000);
        bot.FrontLeft.setPower(0);

        bot.BackRight.setPower(1);
        sleep(1000);
        bot.BackRight.setPower(0);

        bot.BackLeft.setPower(1);
        sleep(1000);
        bot.BackLeft.setPower(0);

    }
}
