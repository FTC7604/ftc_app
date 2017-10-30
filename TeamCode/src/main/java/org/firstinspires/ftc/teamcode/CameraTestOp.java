package org.firstinspires.ftc.teamcode;

import android.hardware.Camera;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by ssuri on 10/20/17.
 *
 */

@TeleOp(name = "Camera Test")
public class CameraTestOp extends OpMode
{

	@Override
    public void init()
    {
        Camera c = getCameraInstance();

    }

	@Override
	public void loop()
	{
        //605 475 6968

	}

	public static Camera getCameraInstance()
	{
		Camera c = null;
		try
		{
			c = Camera.open();
		}
		catch (Exception e)
		{
			// Camera is not available (in use or does not exist)
		}
		return c;
	}
}
