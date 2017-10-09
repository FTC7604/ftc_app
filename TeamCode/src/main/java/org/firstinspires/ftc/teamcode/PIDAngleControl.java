
package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class PIDAngleControl implements SensorEventListener
{
    private static final float kP = 1, kI = 1, kD = 1;
    private float error, integral, derivative, bias;
    private Context context;
    private SensorManager sensorManager;
    private Sensor sensor;

    private float startingValue = -1;
    private float lastError = -1;
    private long lastTime = -1;


	public PIDAngleControl(HardwareMap hardwareMap)
	{
        context = hardwareMap.appContext;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
    }

    public PIDAngleControl(OpMode op)
    {
        this(op.hardwareMap);
    }


    public void startPID()
    {
        startingValue = -1;
        error = integral = derivative = bias = 0;
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopPID()
    {
        sensorManager.unregisterListener(this);
    }

    public float getValue()
    {
        return kP * error + kI * integral + kD * derivative + bias;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        float[] rotation = new float[3], orientation = new float[3];
        SensorManager.getRotationMatrixFromVector(rotation, sensorEvent.values);
        SensorManager.getOrientation(rotation, orientation);

        float azimuth = orientation[0];

        if (startingValue == -1)
        {
            lastError = startingValue = azimuth;
            lastTime = System.currentTimeMillis();
        }
        else
        {
            float modStartingValue = startingValue;
            while(modStartingValue < azimuth)
            {
                modStartingValue += 6.2831854820251465f; // Max precision float value for 2PI
            }

            long currentTime = System.currentTimeMillis(), elapsedTime = currentTime - lastTime;

            error = modStartingValue - azimuth;
            integral = integral + (error * elapsedTime);
            derivative = (error - lastError) / elapsedTime;

            lastTime = currentTime;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {
        // who cares dude
    }
}
