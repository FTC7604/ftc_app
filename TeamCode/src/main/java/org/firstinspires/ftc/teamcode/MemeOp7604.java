
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

        Twister.setPosition(gamepad2.dpad_left ? 1 : gamepad2.dpad_right ? -1 : 0); // FIXME

        LeftHook.setPosition(gamepad2.right_bumper ? 1 : gamepad2.left_bumper ? -1 : 0); //FIXME
        RightHook.setPosition(gamepad2.right_bumper ? -1 : gamepad2.left_bumper ? 1 : 0); //FIXME

        Upercut.setPosition(gamepad2.x ? 1 : gamepad2.b ? -1 : 0); //FIXME

    }

}
