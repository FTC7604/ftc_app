
package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

@TeleOp(name = "AngleSensorTest")
public class AngleSensorTest extends OpMode implements SensorEventListener
{
    private SensorManager sensorManager;
    private Sensor sensor;


	public void init()
	{
        Context context = hardwareMap.appContext;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop()
    {
        sensorManager.unregisterListener(this);
    }

    float[] orientation;
    public void loop()
    {
        for(int i = 0; i < orientation.length; i++)
        {
            telemetry.addData(String.valueOf(i), orientation[i]);
        }
        telemetry.update();
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        float[] rotation = new float[9], orientation = new float[3];
        SensorManager.getRotationMatrixFromVector(rotation, sensorEvent.values);
        SensorManager.getOrientation(rotation, orientation);

        this.orientation = orientation;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {
        // who cares dude
    }
}
