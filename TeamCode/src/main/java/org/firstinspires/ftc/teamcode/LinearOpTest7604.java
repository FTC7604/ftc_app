
package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Linear Op Test", group = "7604")
public class LinearOpTest7604 extends LinearOpMode
{
    private Robot7604 robot;

	@Override
	public void runOpMode()
	{
        robot = new Robot7604(this);

        waitForStart();

        while(time < 2){

            robot.drive(0.5,0);

        }
        while(time < 4){

            robot.drive(0,1);

        }



        robot.stop();
	}
}
