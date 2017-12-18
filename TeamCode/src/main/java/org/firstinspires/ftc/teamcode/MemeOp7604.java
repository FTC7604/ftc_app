package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

@TeleOp(name = "A Quality Pull Request", group = "7604")
public class MemeOp7604 extends OpMode{

    DcMotor FrontLeft;
    DcMotor FrontRight;
    DcMotor BackLeft;
    DcMotor BackRight;
    DcMotor Lift;//High torque motor
    DcMotor Slide;
    DcMotor SvingerDvinger;

    Servo LeftHook, RightHook;
    Servo Twister;
    Servo Upercut;
    Servo LeftGrip, RightGrip;
    Servo LeftGripBottom, RightGripBottom;

    double power = 1;
    double powerLevels = 1;
    int liftPower = 0;

    double twistValue = 0;
    double uperValue = 0;

    boolean gripPressed = false;
    boolean uperPressed = false;

    double gripValue = 0.5;


    @Override
    public void init() {

        FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
        FrontRight = hardwareMap.dcMotor.get("FrontRight");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        BackRight = hardwareMap.dcMotor.get("BackRight");
        Slide = hardwareMap.dcMotor.get("Slide");
        SvingerDvinger = hardwareMap.dcMotor.get("SvingerDvinger");

        Lift = hardwareMap.dcMotor.get("Lift");

        LeftHook = hardwareMap.servo.get("LeftHook");
        RightHook = hardwareMap.servo.get("RightHook");
        Twister = hardwareMap.servo.get("Twister");
        Upercut = hardwareMap.servo.get("Upercut");
        LeftGrip = hardwareMap.servo.get("LeftGrip");
        RightGrip = hardwareMap.servo.get("RightGrip");
        LeftGripBottom = hardwareMap.servo.get("LeftGripBottom");
        RightGripBottom = hardwareMap.servo.get("RightGripBottom");

        FrontRight.setDirection(REVERSE);
        BackRight.setDirection(REVERSE);
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {

        double powerMult = power / powerLevels;
        double leftPower = powerMult * gamepad1.left_stick_y;
        double rightPower = powerMult * gamepad1.right_stick_y;

        FrontRight.setPower(rightPower);
        FrontLeft.setPower(leftPower);
        BackLeft.setPower(leftPower);
        BackRight.setPower(rightPower);

        telemetry.addData("rsy", "%f %f", gamepad1.right_stick_y, rightPower);
        telemetry.addData("lsy", "%f %f", gamepad1.left_stick_y, leftPower);

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

        SvingerDvinger.setPower(gamepad1.y ? 1 : gamepad1.x ? -1 : 0);

        if(gamepad1.left_bumper && !gripPressed){
            gripValue += (gripValue < 0.5 ? 0.25 : 0);
            gripPressed = true;
        }
        else if(gamepad1.right_bumper && !gripPressed){
            gripValue -= (gripValue > 0 ? 0.25 : 0);
            gripPressed = true;
        }
        else{
            gripPressed = false;
        }

        LeftGrip.setPosition(0.5 + gripValue);
        LeftGripBottom.setPosition(0.5 + gripValue);
        RightGrip.setPosition(0.5 - gripValue);
        RightGripBottom.setPosition(0.5 - gripValue);




        // --------------------------- Second controller

        Slide.setPower(gamepad2.right_trigger - gamepad2.left_trigger);

        if(gamepad2.dpad_left){
            twistValue = 0;
        }
        else if(gamepad2.dpad_right){
            twistValue = 1;
        }
        else{
            twistValue = 0.5;
        }

        Twister.setPosition(twistValue);

        if(gamepad2.right_bumper || gamepad2.left_bumper){
            LeftHook.setPosition(gamepad2.right_bumper ? 1 : 0);
            RightHook.setPosition(gamepad2.right_bumper ? 0 : 1);
        }

        if(gamepad2.x){
            if(!uperPressed) {
                uperValue += (uperValue >= 1 ? 0 : 0.5);
                uperPressed = true;
            }
        }
        else if(gamepad2.y){
            if(!uperPressed){
                uperValue -= (uperValue <= 0 ? 0 : 0.5);
                uperPressed = true;
            }
        }
        else{
            uperPressed = false;
        }

        Upercut.setPosition(uperValue);

        telemetry.addData("gripValue", gripValue);
        telemetry.update();

    }

}
