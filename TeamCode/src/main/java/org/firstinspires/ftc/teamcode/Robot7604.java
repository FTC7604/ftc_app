package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

/**
 * Created by ssuri on 10/3/17.
 *
 */

public class Robot7604
{
    DcMotor FrontLeft;
    DcMotor FrontRight;
    DcMotor BackLeft;
    DcMotor BackRight;
    DcMotor Lift; // High torque motor
    DcMotor SpinnerLeft;
    DcMotor SpinnerRight;
    DcMotor Flipper;

    Servo ColorSpinLeft, ColorSpinRight;
    Servo ColorLeverLeft, ColorLeverRight;

    LightSensor SensorL;
    LightSensor SensorR;

    BNO055IMU imu;

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
        SpinnerLeft = hardwareMap.dcMotor.get("SpinnerLeft");
        SpinnerRight = hardwareMap.dcMotor.get("SpinnerRight");
        Flipper = hardwareMap.dcMotor.get("Flipper");

        FrontRight.setDirection(REVERSE);
        BackRight.setDirection(REVERSE);

        ColorSpinLeft = hardwareMap.servo.get("ColorSpinLeft");
        ColorSpinRight = hardwareMap.servo.get("ColorSpinRight");
        ColorLeverLeft = hardwareMap.servo.get("ColorLeverLeft");
        ColorLeverRight = hardwareMap.servo.get("ColorLeverRight");

        SensorL = hardwareMap.get(LightSensor.class, "SensorL");
        SensorR = hardwareMap.get(LightSensor.class, "SensorR");

        imu = hardwareMap.get(BNO055IMU.class, "imu");
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
        Flipper.setPower(0);
        SpinnerLeft.setPower(0);
        SpinnerRight.setPower(0);
    }
}

