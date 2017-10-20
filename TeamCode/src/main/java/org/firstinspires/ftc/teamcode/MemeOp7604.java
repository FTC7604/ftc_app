
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

@TeleOp(name = "A Quality Pull Request", group = "7604")
public class MemeOp7604 extends OpMode {
    DcMotor FrontLeft;
    DcMotor FrontRight;
    DcMotor BackLeft;
    DcMotor BackRight;
    DcMotor Lift;//High torque motor
    DcMotor Slide;

    Servo LeftHook, RightHook;
    Servo Twister;
    Servo Upercut;

    double power = 1;
    double powerLevels = 2;
    int liftPower = 0;

    double twistValue = 0;
    double uperValue = 0;


    @Override
    public void init() {
        FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
        FrontRight = hardwareMap.dcMotor.get("FrontRight");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        BackRight = hardwareMap.dcMotor.get("BackRight");
        Slide = hardwareMap.dcMotor.get("Slide");

        Lift = hardwareMap.dcMotor.get("Lift");

        LeftHook = hardwareMap.servo.get("LeftHook");
        RightHook = hardwareMap.servo.get("RightHook");
        Twister = hardwareMap.servo.get("Twister");
        Upercut = hardwareMap.servo.get("Upercut");

        FrontLeft.setDirection(REVERSE);
        FrontRight.setDirection(REVERSE);
        BackLeft.setDirection(REVERSE);
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

        FrontRight.setPower((power / powerLevels) * (-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x));
        FrontLeft.setPower((power / powerLevels) * (gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x));
        BackLeft.setPower((power / powerLevels) * (gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x));
        BackRight.setPower((power / powerLevels) * (-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x));

        Lift.setPower(liftPower);

        liftPower = ((gamepad1.dpad_up ? 1 : 0) + (gamepad1.dpad_down ? -1 : 0));

        if(gamepad1.x)
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
        }

        Slide.setPower(gamepad2.right_trigger - gamepad2.left_trigger);

        if(gamepad2.dpad_left){
            twistValue += (twistValue == 1 ? 0 : 0.1);
        }
        if(gamepad2.dpad_right){
            twistValue -= (twistValue == 0 ? 0 : 0.1);
        }

        Twister.setPosition(twistValue);


        if(gamepad2.right_bumper || gamepad2.left_bumper){
            LeftHook.setPosition(gamepad2.right_bumper ? 1 : 0);
            RightHook.setPosition(gamepad2.right_bumper ? 0 : 1);
        }

        if(gamepad2.x){
            uperValue += (uperValue == 1 ? 0 : 0.1);
        }
        if(gamepad2.b){
            uperValue -= (uperValue == 0 ? 0 : 0.1);
        }

        Upercut.setPosition(uperValue);

    }

}
