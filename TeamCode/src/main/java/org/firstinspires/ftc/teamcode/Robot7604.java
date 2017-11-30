package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

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
    DcMotor Lift;//High torque motor
    DcMotor Slide;
    DcMotor SvingerDvinger;
    DcMotor ColorStick;

    Servo LeftHook, RightHook;
    Servo Twister;
    Servo Upercut;
    Servo LeftGrip, RightGrip;
    Servo LeftGripBottom, RightGripBottom;


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
        Slide = hardwareMap.dcMotor.get("Slide");
        SvingerDvinger = hardwareMap.dcMotor.get("SvingerDvinger");
        ColorStick = hardwareMap.dcMotor.get("ColorStick");
        Lift = hardwareMap.dcMotor.get("Lift");

        LeftHook = hardwareMap.servo.get("LeftHook");
        RightHook = hardwareMap.servo.get("RightHook");
        Twister = hardwareMap.servo.get("Twister");
        Upercut = hardwareMap.servo.get("Upercut");
        LeftGrip = hardwareMap.servo.get("LeftGrip");
        RightGrip = hardwareMap.servo.get("RightGrip");
        LeftGripBottom = hardwareMap.servo.get("LeftGripBottom");
        RightGripBottom = hardwareMap.servo.get("RightGripBottom");

        //FrontLeft.setDirection(REVERSE);
        FrontRight.setDirection(REVERSE);
        //BackLeft.setDirection(REVERSE);
        BackRight.setDirection(REVERSE);
    }

    public void drive(double power, double angle, double rotation){
        FrontRight.setPower(power * (Math.sin(angle) + Math.cos(angle)) - rotation);
        FrontLeft.setPower(power * (Math.sin(angle) + Math.cos(angle)) + rotation);
        BackLeft.setPower(power * (Math.sin(angle) - Math.cos(angle)) + rotation);
        BackRight.setPower(power * (Math.sin(angle) - Math.cos(angle)) - rotation);
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

    public void grip(boolean state){
        if(state){
            LeftGrip.setPosition(0.5);
            LeftGripBottom.setPosition(0.5);
            RightGrip.setPosition(0.5);
            RightGripBottom.setPosition(0.5);
        }
        else{
            LeftGrip.setPosition(1);
            LeftGripBottom.setPosition(1);
            RightGrip.setPosition(0);
            RightGripBottom.setPosition(0);
        }
    }
}
