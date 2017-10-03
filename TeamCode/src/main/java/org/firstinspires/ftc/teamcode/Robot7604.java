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

        FrontLeft.setDirection(REVERSE);
        //FrontRight.setDirection(REVERSE);
        BackLeft.setDirection(REVERSE);
        //BackRight.setDirection(REVERSE);
    }

    public void stop()
    {
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
    }
}
