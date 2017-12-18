package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by ssuri on 10/3/17.
 *
 */

public class MiniRobot7604 extends Robot7604
{
    public MiniRobot7604(OpMode op)
    {
        this(op.hardwareMap);
    }

    public MiniRobot7604(HardwareMap hardwareMap)
    {
        super(hardwareMap);

        FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
        FrontRight = hardwareMap.dcMotor.get("FrontRight");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        BackRight = hardwareMap.dcMotor.get("BackRight");

        FrontRight.setDirection(REVERSE);
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
    }
}
