package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

/**
 * Created by ssuri on 10/3/17.
 */

public class Robot7604
{

    LightSensor LSensor;
    LightSensor CSensor;

    DcMotor FrontLeft;
    DcMotor FrontRight;
    DcMotor BackLeft;
    DcMotor BackRight;
    DcMotor Lift;//High torque motor
    DcMotor Slide;
    DcMotor SvingerDvinger;
    DcMotor Rudder;

    Servo ColorStick;
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

        LSensor = hardwareMap.get(LightSensor.class, "LightSensor");
        CSensor = hardwareMap.get(LightSensor.class, "ColorSensor");

        FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
        FrontRight = hardwareMap.dcMotor.get("FrontRight");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        BackRight = hardwareMap.dcMotor.get("BackRight");
        Slide = hardwareMap.dcMotor.get("Slide");
        Lift = hardwareMap.dcMotor.get("Lift");
        Rudder = hardwareMap.dcMotor.get("Rudder");

        LeftHook = hardwareMap.servo.get("LeftHook");
        RightHook = hardwareMap.servo.get("RightHook");
        Twister = hardwareMap.servo.get("Twister");
        Upercut = hardwareMap.servo.get("Upercut");
        LeftGrip = hardwareMap.servo.get("LeftGrip");
        RightGrip = hardwareMap.servo.get("RightGrip");
        LeftGripBottom = hardwareMap.servo.get("LeftGripBottom");
        RightGripBottom = hardwareMap.servo.get("RightGripBottom");
        SvingerDvinger = hardwareMap.dcMotor.get("SvingerDvinger");
        ColorStick = hardwareMap.servo.get("ColorStick");

        FrontLeft.setDirection(REVERSE);
        //FrontRight.setDirection(REVERSE);
        BackLeft.setDirection(REVERSE);
        BackRight.setDirection(REVERSE);
    }

    public void drive(double power, double rotation){
        FrontRight.setPower(power - rotation);
        FrontLeft.setPower(power + rotation);
        BackLeft.setPower(power + rotation);
        BackRight.setPower(power - rotation);
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

