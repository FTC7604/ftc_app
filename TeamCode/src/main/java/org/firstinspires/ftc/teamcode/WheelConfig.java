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

        
        DcMotor Led1; //Led stream
        DcMotor Led2; //Led stream

        Led1 = hardwareMap.dcMotor.get("Led1");
        Led2 = hardwareMap.dcMotor.get("Led2");

        Robot7604 bot = new Robot7604(this);

        waitForStart();

        telemetry.addData("Encoder 1", Led1.getCurrentPosition());
        telemetry.addData("Encoder 2", Led2.getCurrentPosition());
        telemetry.addData("Encoder 3", bot.Lift.getCurrentPosition());
        telemetry.addData("Encoder 4", bot.Slide.getCurrentPosition());
        telemetry.update();

        bot.FrontRight.setPower(1);
        sleep(1000);
        bot.FrontRight.setPower(0);

        telemetry.addData("Encoder 1", Led1.getCurrentPosition());
        telemetry.addData("Encoder 2", Led2.getCurrentPosition());
        telemetry.addData("Encoder 3", bot.Lift.getCurrentPosition());
        telemetry.addData("Encoder 4",bot.Slide.getCurrentPosition()); //front right
        telemetry.update();

        bot.FrontLeft.setPower(1);
        sleep(1000);
        bot.FrontLeft.setPower(0);

        telemetry.addData("Encoder 1", Led1.getCurrentPosition());
        telemetry.addData("Encoder 2", Led2.getCurrentPosition()); //front left
        telemetry.addData("Encoder 3", bot.Lift.getCurrentPosition());
        telemetry.addData("Encoder 4", bot.Slide.getCurrentPosition());
        telemetry.update();

        bot.BackRight.setPower(1);
        sleep(1000);
        bot.BackRight.setPower(0);

        telemetry.addData("Encoder 1", Led1.getCurrentPosition());
        telemetry.addData("Encoder 2", Led2.getCurrentPosition());
        telemetry.addData("Encoder 3", bot.Lift.getCurrentPosition()); //back right
        telemetry.addData("Encoder 4",bot.Slide.getCurrentPosition());
        telemetry.update();

        bot.BackLeft.setPower(1);
        sleep(1000);
        bot.BackLeft.setPower(0);

        telemetry.addData("Encoder 1", Led1.getCurrentPosition()); //back left
        telemetry.addData("Encoder 2", Led2.getCurrentPosition());
        telemetry.addData("Encoder 3", bot.Lift.getCurrentPosition());
        telemetry.addData("Encoder 4",bot.Slide.getCurrentPosition());
        telemetry.update();

    }
}
