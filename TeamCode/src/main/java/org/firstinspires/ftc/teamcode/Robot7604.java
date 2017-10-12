package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

/**
 * Created by ssuri on 10/3/17.
 */

public class Robot7604
{
    DcMotor FrontLeft;
    DcMotor FrontRight;
    DcMotor BackLeft;
    DcMotor BackRight;
    DcMotor Lift;// High torque motor
    DcMotor Slide;

    public Robot7604(OpMode op)
    {
        this(op.hardwareMap);
    }

    public Robot7604(HardwareMap hardwareMap)
    {
        FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
        FrontRight = hardwareMap.dcMotor.get("FrontRight");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        BackRight = hardwareMap.dcMotor.get("BackRight");

        Lift = hardwareMap.dcMotor.get("Lift");

        Slide = hardwareMap.dcMotor.get("Slide");

        //FrontLeft.setDirection(REVERSE);
        FrontRight.setDirection(REVERSE);
        //BackLeft.setDirection(REVERSE);
        BackRight.setDirection(REVERSE);
    }

    public void drive(double power, double angle, double rotation){
        FrontRight.setPower(power * (Math.sin(angle) - Math.cos(angle)) - rotation);
        FrontLeft.setPower(power * (Math.sin(angle) + Math.cos(angle)) + rotation);
        BackLeft.setPower(power * (Math.sin(angle) - Math.cos(angle)) + rotation);
        BackRight.setPower(power * (Math.sin(angle) + Math.cos(angle)) - rotation);
    }

    public void stop()
    {
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
        Lift.setPower(0);
        Slide.setPower(0);
    }
}
