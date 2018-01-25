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

public class AutonomousMain extends AbstractLinearOpMode {
    Position position = null;

    public enum Position {
        RedLeft(-1), RedRight(-1), BlueLeft(-1), BlueRight(-1);
        private int direction;
        Position(int direction) {
            this.direction = direction;
        }
    }

    public AutonomousMain(Position position){
        this.position = position;
    }

    Robot7604 bot;
    @Override
    public void startLinear ()
    {
        bot = new Robot7604(this.callingOpMode);
        //PIDAngleControl pid = new PIDAngleControl(this);
    }

    @Override
    public void runLinear()
    {
        long timeInit;
        int direction = position.direction;

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


        bot.ColorStick.setPosition(0.75);
        bot.CSensor.enableLed(true);

        sleep(500);

        timeInit = System.currentTimeMillis();

        while(System.currentTimeMillis() - timeInit < 1000 && opModeIsActive()) {
            telemetry.addData("RawCS", bot.CSensor.getRawLightDetected());

            telemetry.update();
        }

        if(bot.CSensor.getRawLightDetected() < 1.75 ^ (direction == 1)){
            bot.drive(0.2,0);
            telemetry.addData("Choice", "Forwards");
            telemetry.update();
            sleep(600);
            bot.stop();


            bot.CSensor.enableLed(false);
            bot.ColorStick.setPosition(0);
            sleep(500);

            if(direction == -1) {
                bot.drive(-0.2, 0);
                sleep(2000);
                bot.stop();
            }
        }
        else{
            bot.drive(-0.2,0);
            telemetry.addData("Choice", "Backwards");
            telemetry.update();
            sleep(600);
            bot.stop();


            bot.CSensor.enableLed(false);
            bot.ColorStick.setPosition(0);
            sleep(500);


            if(direction == 1) {
                bot.drive(0.2, 0);
                sleep(3000);
                bot.stop();
            }
        }

        /*
        bot.drive(-0.2f, 0);
        sleep(400);
        bot.stop();
        sleep(500);
        */
        double redGray = 1.325;
        double blueGray = 1.05;

        double LThreshold = (redGray + blueGray) / 2 + (direction * ((blueGray - redGray)/2));
        bot.LSensor.enableLed(true);
        timeInit = System.currentTimeMillis();
        while(System.currentTimeMillis() - timeInit < 1000 && opModeIsActive()){
            bot.drive(direction * 0.3f, 0);
        }
        while(bot.LSensor.getRawLightDetected() < LThreshold && opModeIsActive()){
            bot.drive(direction * 0.3f, 0);
            telemetry.addData("LValue", bot.LSensor.getRawLightDetected());
            telemetry.update();
        }
        bot.stop();
        sleep(500);


        bot.LSensor.enableLed(false);



        bot.stop();
        sleep(1000);

        long rot = 0;

        switch(vuMark){
            case LEFT:
                rot = 7000;
                break;
            case CENTER:
                rot = 5000;
                break;
            default: //RIGHT
                rot = 3250;
                break;
        }


        /*bot.drive(-0.3,-0.5);
        sleep(rot);*/

        int leftwheelthresh = bot.Led1.getCurrentPosition();
        int rightwheelthresh = bot.Lift.getCurrentPosition();

        while(bot.Led1.getCurrentPosition() - leftwheelthresh + bot.Lift.getCurrentPosition() - rightwheelthresh < rot){
            bot.drive(-0.3,-0.5);
        }

        bot.stop();
        sleep(500);
        bot.drive(0.4f,0);
        sleep(600);
        bot.stop();
        bot.grip(false);
        sleep(500);
        bot.drive(-0.2f,0);
        sleep(500);
        bot.drive(0.2f, 0);
        sleep(500);
        bot.drive(-0.2f,0);
        sleep(500);
        bot.stop();

    }
}