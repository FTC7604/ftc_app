package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
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

    @Override
    public void runOpMode () {


        long timeInit;
        Robot7604 bot = new Robot7604(this);

        PIDAngleControl pid = new PIDAngleControl(this);

        waitForStart();


        int cameraMonitorViewId = hardwareMap.appContext.getResources()
                .getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "ASv2MNj/////AAAAGQukwPKRd0YcsSlpoJYzs9EdjNGpnGv0mY+oWYr923xV6ZP+Tm9A7ZjZvdw7KY3iqJ/2AXpNLeHZLylMumJd46ZYL4zpkdjPY6OwGwUmQBrgo6MXWgIM6bKgp/0M1SJnb8yYpFjzTAqAXtXqotY5KPiLkelgBeCuPYc+NUAlf6vSxjEr7+Zezid1O2zV3dRV/FlaBJN9MQsgWOvPQfsTiKqgpEr2b4pLG8PMqL/HU3RvuEexsWSv5eN6mWtx8Vt7m+GSBC6xo9vxR+gaTLsi19RAXTPCq4UhoQvrFYIORotVeVa5zIhZXlpMc09NZT25e6DcOPTv2eloL55O2/FK81AGay8e4urLNQ5wF3vknehR";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        VuforiaLocalizer vuforia = ClassFactory
                .createVuforiaLocalizer(parameters);


		VuforiaTrackables relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
		VuforiaTrackable relicTemplate = relicTrackables.get(0);
		relicTemplate.setName("relicVuMarkTemplate");

		relicTrackables.activate();

		timeInit = System.currentTimeMillis();

        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

		while(System.currentTimeMillis() - timeInit < 1000 && opModeIsActive()) {
            vuMark = RelicRecoveryVuMark.from(relicTemplate);
        }


		telemetry.addData("VuMark", vuMark);
        telemetry.update();


        bot.grip(true);

        sleep(500);


        bot.SvingerDvinger.setPower(1);
        sleep(500);
        bot.SvingerDvinger.setPower(0);
        sleep(500);

        /*
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
        */

        bot.drive(-0.2f, 0);
        sleep(400);
        bot.stop();
        sleep(500);


        //pid.startPID();

        timeInit = System.currentTimeMillis();
        while(System.currentTimeMillis() - timeInit < 4500 && opModeIsActive()){
            bot.drive(-0.2f, 0);
        }

        bot.stop();
        sleep(1000);
        bot.drive(0,-0.2f);
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
