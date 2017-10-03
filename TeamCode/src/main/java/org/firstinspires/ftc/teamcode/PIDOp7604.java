
package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "PID", group = "7604")
public class PIDOp7604 extends LinearOpMode
{
	DcMotor FrontLeft;
	DcMotor FrontRight;
	DcMotor BackLeft;
	DcMotor BackRight;
	DcMotor Lift;// High torque motor

    private static final double kP = 1, kI = 1, kD = 1;
	@Override
	public void runOpMode()
	{


        waitForStart();

        while(time < 3)
        {

        }

        go(0);
	}
	
	private void go(double power)
    {
        FrontLeft.setPower(power);
        FrontRight.setPower(power);
        BackLeft.setPower(power);
        BackRight.setPower(power);
    }
}
