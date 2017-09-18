
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

@TeleOp(name = "A Quality Pull Request", group = "7604")
public class MemeOp7604 extends OpMode {
    DcMotor FrontLeft;
    DcMotor FrontRight;
    DcMotor BackLeft;
    DcMotor BackRight;

    double power = 1;
    double powerLevels = 2;
    @Override
    public void init() {
        FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
        FrontRight = hardwareMap.dcMotor.get("FrontRight");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        BackRight = hardwareMap.dcMotor.get("BackRight");

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
    }

}
