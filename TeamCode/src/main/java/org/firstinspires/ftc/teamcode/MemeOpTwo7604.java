package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

@TeleOp(name = "Gracious Professionalism", group = "7604")
public class MemeOpTwo7604 extends OpMode{

    DcMotor FrontLeft;
    DcMotor FrontRight;
    DcMotor BackLeft;
    DcMotor BackRight;
    DcMotor Lift;//High torque motor
    DcMotor SpinnerLeft;
    DcMotor SpinnerRight;

    Servo ColorSpinLeft, ColorSpinRight;
    Servo ColorLeverLeft, ColorLeverRight;
    Servo FlipperLeft, FlipperRight;

    LightSensor SensorL;
    LightSensor SensorR;

    double liftPower;


    @Override
    public void init() {

        FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
        FrontRight = hardwareMap.dcMotor.get("FrontRight");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        BackRight = hardwareMap.dcMotor.get("BackRight");
        Lift = hardwareMap.dcMotor.get("Lift");
        SpinnerLeft = hardwareMap.dcMotor.get("SpinnerLeft");
        SpinnerRight = hardwareMap.dcMotor.get("SpinnerRight");

        FrontRight.setDirection(REVERSE);
        BackRight.setDirection(REVERSE);

        ColorSpinLeft = hardwareMap.servo.get("ColorSpinLeft");
        ColorSpinRight = hardwareMap.servo.get("ColorSpinRight");
        ColorLeverLeft = hardwareMap.servo.get("ColorLeverLeft");
        ColorLeverRight = hardwareMap.servo.get("ColorLeverRight");
        FlipperLeft = hardwareMap.servo.get("FlipperLeft");
        FlipperRight = hardwareMap.servo.get("FlipperRight");

        SensorL = hardwareMap.get(LightSensor.class, "SensorL");
        SensorR = hardwareMap.get(LightSensor.class, "SensorR");
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {

        SensorL.enableLed(true);
        SensorR.enableLed(true);

        telemetry.addData("SensorL", SensorL.getRawLightDetected());
        telemetry.addData("SensorR", SensorR.getRawLightDetected());
        telemetry.addData("Encoder 1", FrontLeft.getCurrentPosition());
        telemetry.addData("Encoder 2", BackLeft.getCurrentPosition());
        telemetry.addData("Encoder 3", FrontRight.getCurrentPosition());
        telemetry.addData("Encoder 4", BackRight.getCurrentPosition());

        double leftPower1 = (gamepad2.left_stick_y - gamepad2.right_stick_x);
        double rightPower1 = (gamepad2.left_stick_y + gamepad2.right_stick_x);
        double rightPower2 = -1 * (gamepad1.left_stick_y - gamepad1.right_stick_x);
        double leftPower2 =  -1 * (gamepad1.left_stick_y + gamepad1.right_stick_x);

        FrontRight.setPower(rightPower1 + rightPower2);
        FrontLeft.setPower(leftPower1 + leftPower2);
        BackLeft.setPower(leftPower1 + leftPower2);
        BackRight.setPower(rightPower1 + rightPower2);

        telemetry.addData("rsy1", "%f %f", gamepad1.right_stick_y, rightPower1);
        telemetry.addData("lsy1", "%f %f", gamepad1.left_stick_y, leftPower1);
        telemetry.addData("rsy1", "%f %f", -gamepad1.left_stick_y, rightPower1);
        telemetry.addData("lsy1", "%f %f", -gamepad1.right_stick_y, leftPower1);

        liftPower = ((gamepad1.dpad_up ? -1 : 0) + (gamepad1.dpad_down ? 1 : 0));

        Lift.setPower(liftPower);

        /*if(gamepad1.x)
        {
            if(power == powerLevels)
            {
                power = 1;
            }
            else
            {
                power++;
            }
        }
        else if(gamepad1.y)
        {
            if(power == 1)
            {
                power = powerLevels;
            }
            else
            {
                power--;
            }
        }*/

        if(gamepad1.dpad_left){
            ColorLeverLeft.setPosition(0.5);
            ColorLeverRight.setPosition(0.5);
        }
        else if(gamepad1.dpad_right){
            ColorLeverLeft.setPosition(1);
            ColorLeverRight.setPosition(0);
        }

        if(gamepad1.right_trigger > 0.5){
            FlipperLeft.setPosition(0.6);
            FlipperRight.setPosition(0.6);
        }
        else if(gamepad1.left_trigger > 0.5){
            FlipperLeft.setPosition(1);
            FlipperRight.setPosition(0.2);
        }

        // --------------------------- Second controller

        SpinnerLeft.setPower(gamepad2.right_trigger - gamepad2.left_trigger);
        SpinnerRight.setPower(gamepad2.left_trigger - gamepad2.right_trigger);

    }

}
