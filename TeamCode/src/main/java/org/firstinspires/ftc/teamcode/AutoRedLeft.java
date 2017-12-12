package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Declan on 11/13/2017.
 */

@Autonomous(name = "AutoRedLeft", group = "7604")
public class AutoRedLeft extends LinearOpMode {

    @Override
    public void runOpMode () {


        long timeInit;
        Robot7604 bot = new Robot7604(this);

        PIDAngleControl pid = new PIDAngleControl(this);

        waitForStart();

        //bot.grip(true);

        sleep(500);

        /*
        bot.SvingerDvinger.setPower(1);
        sleep(500);
        bot.SvingerDvinger.setPower(0);
        sleep(500);

        //bot.CSensor.getRawLightDetected();

        /*bot.ColorStick.setPosition(0);
        sleep(500);
        bot.ColorStick.setPosition(0.3);
        sleep(100);
        */



        bot.ColorStick.setPosition(0.5);
        bot.CSensor.enableLed(true);

        sleep(500);

        timeInit = System.currentTimeMillis();

        while(System.currentTimeMillis() - timeInit < 5000 && opModeIsActive()) {
            telemetry.addData("RawCS", bot.CSensor.getRawLightDetected());

            telemetry.update();
        }

        if(bot.CSensor.getRawLightDetected() > 1.25){
            //bot.drive(0.1,1.571f,0);
            telemetry.addData("Choice", "Forwards");
        }
        else{
            //bot.drive(-0.1,1.571f,0);
            telemetry.addData("Choice", "Backwards");
        }
        telemetry.update();
        sleep(600);
        bot.stop();

        bot.CSensor.enableLed(false);
        bot.ColorStick.setPosition(0);
        sleep(500);

        /*
        bot.drive(-0.2f,1.571f,0);
        sleep(400);
        bot.stop();
        sleep(500);


        pid.startPID();

        long timeInit = System.currentTimeMillis();
        while(System.currentTimeMillis() - timeInit < 4500 && opModeIsActive()){
            bot.drive(-0.2f, 1.571f /*(Math.PI / 2) - .5f,0.5 * pid.getValue());
        }

        bot.stop();
        sleep(1000);
        bot.drive(0,0,-0.5f);
        sleep(900);
        bot.stop();
        pid.stopPID();
        /*
            Shift over laterally based on pictogram

        pid.startPID();
        timeInit = System.currentTimeMillis();
        while(System.currentTimeMillis() - timeInit < 2000 && opModeIsActive()){
            bot.drive(0.4f,1.571f, 0.5 * pid.getValue());
        }
        bot.stop();
        bot.grip(false);
        bot.drive(-0.2f,1.571f,0);
        bot.stop();

        pid.stopPID();
        */
    }
}
