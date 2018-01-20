package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Declan on 11/13/2017.
 */

@Autonomous(name = "AutoRedLeft", group = "7604")
public class AutoRedLeft extends LinearOpMode {
    String position = "None";

    public void AutoRedLeft(String position){
        position = this.position;
    }

    @Override
    public void runOpMode () {



        long timeInit;
        Robot7604 bot = new Robot7604(this);

        //PIDAngleControl pid = new PIDAngleControl(this);

        waitForStart();



        int cameraMonitorViewId = hardwareMap.appContext.getResources()
                .getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "ASv2MNj/////AAAAGQukwPKRd0YcsSlpoJYzs9EdjNGpnGv0mY+oWYr923xV6ZP+Tm9A7ZjZvdw7KY3iqJ/2AXpNLeHZLylMumJd46ZYL4zpkdjPY6OwGwUmQBrgo6MXWgIM6bKgp/0M1SJnb8yYpFjzTAqAXtXqotY5KPiLkelgBeCuPYc+NUAlf6vSxjEr7+Zezid1O2zV3dRV/FlaBJN9MQsgWOvPQfsTiKqgpEr2b4pLG8PMqL/HU3RvuEexsWSv5eN6mWtx8Vt7m+GSBC6xo9vxR+gaTLsi19RAXTPCq4UhoQvrFYIORotVeVa5zIhZXlpMc09NZT25e6DcOPTv2eloL55O2/FK81AGay8e4urLNQ5wF3vknehR";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        VuforiaLocalizer vuforia = ClassFactory
                .createVuforiaLocalizer(parameters);


        VuforiaTrackables relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        relicTrackables.activate();

        timeInit = System.currentTimeMillis();

        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

        while(System.currentTimeMillis() - timeInit < 2000 && opModeIsActive()) {
            vuMark = RelicRecoveryVuMark.from(relicTemplate);
        }


        telemetry.addData("VuMark", vuMark);
        telemetry.update();
        sleep(500);





        bot.grip(true);

        sleep(500);


        bot.SvingerDvinger.setPower(1);
        sleep(500);
        bot.SvingerDvinger.setPower(0);
        sleep(500);


        bot.ColorStick.setPosition(0.6);
        bot.CSensor.enableLed(true);

        sleep(500);

        timeInit = System.currentTimeMillis();

        while(System.currentTimeMillis() - timeInit < 1000 && opModeIsActive()) {
            telemetry.addData("RawCS", bot.CSensor.getRawLightDetected());

            telemetry.update();
        }

        if(bot.CSensor.getRawLightDetected() < 1.88 ^ (position == "blueleft" || position == "blueright")){
            bot.drive(0.2,0);
            telemetry.addData("Choice", "Forwards");
            telemetry.update();
            sleep(600);
            bot.stop();


            bot.CSensor.enableLed(false);
            bot.ColorStick.setPosition(0);
            sleep(500);

            sleep(500);
            bot.drive(-0.2,0);
            sleep(2000);
            bot.stop();
        }
        else{
            bot.drive(-0.2,0);
            telemetry.addData("Choice", "Backwards");
            telemetry.update();
            sleep(1400);
            bot.stop();


            bot.CSensor.enableLed(false);
            bot.ColorStick.setPosition(0);
            sleep(500);
        }









        /*
        bot.drive(-0.2f, 0);
        sleep(400);
        bot.stop();
        sleep(500);
        */


        bot.LSensor.enableLed(true);
        timeInit = System.currentTimeMillis();
        while(System.currentTimeMillis() - timeInit < 300 && opModeIsActive()){
            bot.drive(-0.3f, 0);
        }
        while(bot.LSensor.getRawLightDetected() < 1.325 && opModeIsActive()){
            bot.drive(-0.3f, 0);
            telemetry.addData("LValue", bot.LSensor.getRawLightDetected());
            telemetry.update();
        }
        bot.LSensor.enableLed(false);



        bot.stop();
        sleep(1000);

        long rot = 0;

        switch(vuMark){
            case LEFT:
                rot = 2000;
                break;
            case CENTER:
                rot = 1500;
                break;
            case RIGHT:
                rot = 1000;
                break;
        }


        bot.drive(0,-0.5f);
        sleep(rot);
        bot.stop();
        bot.drive(0.4f,0);
        sleep(600);
        bot.stop();
        bot.grip(false);
        sleep(500);
        bot.drive(-0.2f,0);
        bot.stop();

    }
}
