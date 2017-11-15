package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Declan on 11/13/2017.
 */

@Autonomous(name = "AutoRedLeft", group = "7604")
public class AutoRedLeft extends LinearOpMode {


    @Override
    public void runOpMode () {

        Robot7604 bot = new Robot7604(this);
        PIDAngleControl pid = new PIDAngleControl(this);

        pid.startPID();
        waitForStart();

        long timeInit = System.currentTimeMillis();

        while(System.currentTimeMillis() - timeInit < 6000 && opModeIsActive()){
            bot.drive(.2f, (Math.PI / 2) - 0.32f,pid.getValue());
        }
        /*
        bot.drive(0f,0.0f,0.2f);
        sleep(2000);
        */
        bot.stop();

        pid.stopPID();
    }
}
