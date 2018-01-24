package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.LightSensor;

/**
 * Created by ssuri on 11/1/17.
 *
 */

@TeleOp(name = "Led Test")
public class LEDTestOp extends OpMode
{
    DcMotorController mc = null;

    long initTime;
    @Override
    public void init()
    {
        mc = hardwareMap.dcMotorController.get("motor");
        initTime = System.currentTimeMillis();

    }


    @Override
    public void loop()
    {
        long i = ((System.currentTimeMillis() - initTime) / 1000) % 3;
        if(i == 1 || i == 3)
        {
            mc.setMotorPower(1, 1);
        }
        if(i == 2 || i == 3)
        {
            mc.setMotorPower(2, 1);
        }
    }
}
