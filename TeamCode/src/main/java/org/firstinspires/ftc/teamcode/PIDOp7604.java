
package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "PID", group = "7604")
public class PIDOp7604 extends LinearOpMode
{
    private Robot7604 robot;
    private static final double kP = 1, kI = 1, kD = 1;
    private Context context;
    private SensorManager sensorManager;
    private Sensor sensor;

	@Override
	public void runOpMode()
	{
        robot = new Robot7604(this);
        context = hardwareMap.appContext;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        waitForStart();

        while(time < 3)
        {

        }

        robot.stop();
	}
}
