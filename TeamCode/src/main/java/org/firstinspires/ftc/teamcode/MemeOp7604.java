package org.firstinspires.ftc.teamcode;

import com.qualcomm.ftccommon.configuration.EditLegacyServoControllerActivity;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Gracious Professionalism", group = "7604")
public class MemeOp7604 extends OpMode
{
    private Robot7604 robot;

    @Override
	public void init()
	{
	    robot = new Robot7604(this);
	}

	@Override
	public void init_loop()
	{}

	@Override
	public void start()
	{
		robot.ColorSpinLeft.setPosition(0.7);
		robot.ColorSpinRight.setPosition(0.4);

		robot.ColorLeverLeft.setPosition(1);
		robot.ColorLeverRight.setPosition(0);
	}

	@Override
	public void loop()
	{
		robot.SensorL.enableLed(true);
        robot.SensorR.enableLed(true);

		telemetry.addData("SensorL", robot.SensorL.getRawLightDetected());
		telemetry.addData("SensorR", robot.SensorR.getRawLightDetected());
		telemetry.addData("Encoder 1", robot.FrontLeft.getCurrentPosition());
		telemetry.addData("Encoder 2", robot.BackLeft.getCurrentPosition());
		telemetry.addData("Encoder 3", robot.FrontRight.getCurrentPosition());
		telemetry.addData("Encoder 4", robot.BackRight.getCurrentPosition());

		double leftPower1 = -1 * (gamepad2.left_stick_y + gamepad2.right_stick_x);
		double rightPower1 = -1 * (gamepad2.left_stick_y - gamepad2.right_stick_x);
		double rightPower2 = (gamepad1.left_stick_y + gamepad1.right_stick_x);
		double leftPower2 = (gamepad1.left_stick_y - gamepad1.right_stick_x);

        robot.FrontRight.setPower(rightPower1 + rightPower2);
        robot.FrontLeft.setPower(leftPower1 + leftPower2);
        robot.BackLeft.setPower(leftPower1 + leftPower2);
        robot.BackRight.setPower(rightPower1 + rightPower2);

		telemetry.addData("rsy1", "%f %f", gamepad1.right_stick_y, rightPower1);
		telemetry.addData("lsy1", "%f %f", gamepad1.left_stick_y, leftPower1);
		telemetry.addData("rsy1", "%f %f", -gamepad1.left_stick_y, rightPower1);
		telemetry.addData("lsy1", "%f %f", -gamepad1.right_stick_y, leftPower1);

        double liftPower = ((gamepad1.dpad_up ? -1 : 0) + (gamepad1.dpad_down ? 1 : 0));

        robot.Lift.setPower(liftPower);

		/*
		 * if(gamepad1.x) { if(power == powerLevels) { power = 1; } else { power++; } }
		 * else if(gamepad1.y) { if(power == 1) { power = powerLevels; } else { power--;
		 * } }
		 */


		if (gamepad1.dpad_left)
		{
            robot.ColorLeverLeft.setPosition(0.5);
            robot.ColorLeverRight.setPosition(0.5);
		}
		else if (gamepad1.dpad_right)
		{
            robot.ColorLeverLeft.setPosition(1);
            robot.ColorLeverRight.setPosition(0);
		}

		if (gamepad1.right_trigger > 0.5)
		{
			robot.Flipper.setPower(0.2);
		}
		else if (gamepad1.left_trigger > 0.5)
		{
			robot.Flipper.setPower(-0.2);
		}
		else
		{
			robot.Flipper.setPower(0);
		}

		// --------------------------- Second controller

        robot.SpinnerLeft.setPower(gamepad2.right_trigger - gamepad2.left_trigger);
        robot.SpinnerRight.setPower(gamepad2.left_trigger - gamepad2.right_trigger);

	}

}
