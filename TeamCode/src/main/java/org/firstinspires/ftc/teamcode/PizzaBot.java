package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by ssuri on 12/18/17.
 */

@TeleOp(name = "PizzaBot")
public class PizzaBot extends OpMode
{
    DcMotor rightDrive, leftDrive;
    @Override
    public void init()
    {
        rightDrive = hardwareMap.dcMotor.get("rightdrive");
        leftDrive = hardwareMap.dcMotor.get("leftdrive");
        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop()
    {
        double leftPower = gamepad1.left_stick_y;
        double rightPower = gamepad1.right_stick_y;

        rightDrive.setPower(rightPower);
        leftDrive.setPower(leftPower);


        telemetry.addData("Encoder Value", rightDrive.getCurrentPosition());
        telemetry.update();
    }
}
