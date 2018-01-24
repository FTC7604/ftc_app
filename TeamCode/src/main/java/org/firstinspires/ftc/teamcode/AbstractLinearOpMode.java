package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwareK9bot;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by ssuri on 1/24/18.
 *
 */

public abstract class AbstractLinearOpMode
{
    public HardwareMap hardwareMap;
    public Gamepad gamepad1, gamepad2;
    public Telemetry telemetry;
    public LinearOpMode callingOpMode = null;

    public void initVars()
    {
        hardwareMap = callingOpMode.hardwareMap;
        gamepad1 = callingOpMode.gamepad1;
        gamepad2 = callingOpMode.gamepad2;
        telemetry = callingOpMode.telemetry;
    }

    public final boolean opModeIsActive()
    {
        return callingOpMode.opModeIsActive();
    }

    public final void sleep(long milliseconds)
    {
        callingOpMode.sleep(milliseconds);
    }

    public abstract void startLinear();
    public abstract void runLinear();
}
