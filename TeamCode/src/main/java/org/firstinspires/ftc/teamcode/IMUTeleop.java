package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.NavUtil;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

@TeleOp(name = "Gracious Professionalism (now with IMU!)", group = "7604")
public class IMUTeleop extends OpMode
{
    private Robot7604 robot;

    @Override
	public void init()
	{
	    robot = new Robot7604(this);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        //parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new MemeMachineAccelerationIntegrator();
        parameters.mode = BNO055IMU.SensorMode.IMU;

        robot.imu.initialize(parameters);
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

        robot.imu.startAccelerationIntegration(new Position(), new Velocity(), 100);
    }

    Position startPos = null;
	@Override
	public void loop()
	{
		robot.SensorL.enableLed(true);
        robot.SensorR.enableLed(true);

		double leftPower1 = (gamepad2.left_stick_y - gamepad2.right_stick_x);
		double rightPower1 = (gamepad2.left_stick_y + gamepad2.right_stick_x);
		double rightPower2 = -1 * (gamepad1.left_stick_y - gamepad1.right_stick_x);
		double leftPower2 = -1 * (gamepad1.left_stick_y + gamepad1.right_stick_x);

        robot.FrontRight.setPower(rightPower1 + rightPower2);
        robot.FrontLeft.setPower(leftPower1 + leftPower2);
        robot.BackLeft.setPower(leftPower1 + leftPower2);
        robot.BackRight.setPower(rightPower1 + rightPower2);

        if(gamepad1.y)
        {
            startPos = robot.imu.getPosition();
        }

        if(robot.imu.isGyroCalibrated())
        {
            if(startPos == null)
            {
                telemetry.addData("IMU Distance", "Waiting to set startPos (press Y)");
            }
            else
            {
                Position dPos = NavUtil.minus(startPos, robot.imu.getPosition());


                double dist = Math.sqrt(sq(dPos.x) + sq(dPos.y) + sq(dPos.z));
                Acceleration a = robot.imu.getLinearAcceleration();
//                robot.imu.
                telemetry.addData("IMU Accel", "acc [%f, %f, %f]", a.xAccel, a.yAccel, a.zAccel);
                //telemetry.addData("IMU Distance", "%f = [%f, %f, %f]", dist, dPos.x, dPos.y, dPos.z);
                telemetry.addData("IMU Distance", "%f = [%f, %f, %f]", dist, robot.imu.getPosition().x, robot.imu.getPosition().y, robot.imu.getPosition().z);
            }
        }
        else
        {
            telemetry.addData("IMU Distance", "Waiting for Calibration");
        }
    }

    private double sq(double d)
    {
        return d * d;
    }
}
