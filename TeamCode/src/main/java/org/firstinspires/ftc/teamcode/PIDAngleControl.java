
package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PIDAngleControl implements SensorEventListener
{
    private static final float mult = .1f;
    private static final float kP = 2, kI = 2f, kD = 0.5f;
    private float error, integral, derivative;
    private SensorManager sensorManager;
    private Sensor sensor;
    private Telemetry telemetry = null;

    private float startingValue = -1;
    private float lastError = -1;
    private long lastTime = -1;


	public PIDAngleControl(HardwareMap hardwareMap, Telemetry telemetry)
	{
        Context context = hardwareMap.appContext;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        this.telemetry = telemetry;
    }

    public PIDAngleControl(OpMode op)
    {
        this(op.hardwareMap, op.telemetry);
    }


    public void startPID()
    {
        startingValue = -1;
        error = integral = derivative = 0;
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopPID()
    {
        sensorManager.unregisterListener(this);
    }

    public float getValue()
    {
        float value = (mult * kP * error) + (mult * kI * integral) + (mult * kD * derivative);

        telemetry.addData("error", error);
        telemetry.addData("integral", integral);
        telemetry.addData("differential", derivative);
        telemetry.addData("value", value);
        telemetry.update();

        return value;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        float[] rotation = new float[9], orientation = new float[3];
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
            /*
            while(modStartingValue < azimuth)
            {
                modStartingValue += 6.2831854820251465f; // Max precision float value for 2PI
            }
            */
            long currentTime = System.currentTimeMillis();
            float elapsedTime = 0.001f * (currentTime - lastTime);

            error = modStartingValue -                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   azimuth;

            telemetry.addData("starting value", startingValue);
            telemetry.addData("starting value (mod)", modStartingValue);
            telemetry.addData("azimuth", azimuth);
            telemetry.update();

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
