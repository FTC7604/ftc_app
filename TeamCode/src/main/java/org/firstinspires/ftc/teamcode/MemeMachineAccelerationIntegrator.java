package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.navigation.NavUtil.meanIntegrate;
import static org.firstinspires.ftc.robotcore.external.navigation.NavUtil.plus;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import java.util.ArrayDeque;
import java.util.Deque;

public class MemeMachineAccelerationIntegrator implements BNO055IMU.AccelerationIntegrator
{
	BNO055IMU.Parameters parameters;
	Position position;
	Velocity velocity;
	Acceleration acceleration;

	int capacity = 10;
	double thresh = 100000; // todo: assign real values
    LowPassFilter xFilter = new LowPassFilter(capacity, thresh),
                  yFilter = new LowPassFilter(capacity, thresh),
                  zFilter = new LowPassFilter(capacity, thresh);

    private Acceleration filter(Acceleration acceleration)
    {
        xFilter.add(acceleration.xAccel);
        yFilter.add(acceleration.yAccel);
        zFilter.add(acceleration.zAccel);
        acceleration.xAccel = xFilter.getCurrentValue();
        acceleration.yAccel = yFilter.getCurrentValue();
        acceleration.zAccel = zFilter.getCurrentValue();
        return acceleration;

    }

	public Position getPosition()
	{
		return this.position;
	}

	public Velocity getVelocity()
	{
		return this.velocity;
	}

	public Acceleration getAcceleration()
	{
		return this.acceleration;
	}

	MemeMachineAccelerationIntegrator()
	{
		this.parameters = null;
		this.position = new Position();
		this.velocity = new Velocity();
		this.acceleration = null;
	}

	@Override
	public void initialize(@NonNull BNO055IMU.Parameters parameters, @Nullable Position initialPosition,
			@Nullable Velocity initialVelocity)
	{
		this.parameters = parameters;
		this.position = initialPosition != null ? initialPosition : this.position;
		this.velocity = initialVelocity != null ? initialVelocity : this.velocity;
		this.acceleration = null;
	}

	@Override
	public void update(Acceleration linearAcceleration)
	{
		// We should always be given a timestamp here
		if (linearAcceleration.acquisitionTime != 0)
		{
            boolean accWasNull = acceleration == null;
            Acceleration accelPrev = acceleration;
            Velocity velocityPrev = velocity;

            acceleration = filter(linearAcceleration);

			// We can only integrate if we have a previous acceleration to baseline from
			if (!accWasNull)
			{
				if (accelPrev.acquisitionTime != 0)
				{
					Velocity deltaVelocity = meanIntegrate(acceleration, accelPrev);
					velocity = plus(velocity, deltaVelocity);
				}

				if (velocityPrev.acquisitionTime != 0)
				{
					Position deltaPosition = meanIntegrate(velocity, velocityPrev);
					position = plus(position, deltaPosition);
				}

				if (parameters != null && parameters.loggingEnabled)
				{
					RobotLog.vv(parameters.loggingTag, "dt=%.3fs accel=%s vel=%s pos=%s",
							(acceleration.acquisitionTime - accelPrev.acquisitionTime) * 1e-9, acceleration, velocity,
							position);
				}
			}
		}
	}

	private class LowPassFilter
	{
		private Deque<Double> values;
		private int capacity;
		private double thresh;

		public LowPassFilter(int capacity, double thresh)
		{
			this.capacity = capacity;
			values = new ArrayDeque<>();
			this.thresh = thresh;
		}

		public void add(double value)
		{
		    if(value < thresh)
            {
                values.addFirst(value);
            }
		}

		public double getCurrentValue()
		{
		    while(values.size() > capacity)
            {
                values.removeLast();
            }

            double sum = 0;
            for (double value : values)
            {
                sum += value;
            }

            return sum / values.size();
		}
	}
}