package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorController;
/**
 * Created by ssuri on 11/1/17.
 *
 */

@TeleOp(name = "Led Test")
public class LEDTestOp extends OpMode
{
    DcMotorController mc = null;
    @Override
    public void init()
    {
        mc = hardwareMap.dcMotorController.get("motor");
    }

    @Override
    public void loop()
    {
        mc.setMotorPower(1, 1);
        mc.setMotorPower(2, 1);
    }
}
