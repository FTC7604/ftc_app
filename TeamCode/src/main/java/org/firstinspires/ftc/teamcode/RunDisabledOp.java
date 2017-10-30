package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.RelativeLayout;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.ftccommon.teamcode.DisabledOpClassFilter;

import java.util.List;

/**
 * Created by ssuri on 10/24/17.
 *
 */

@TeleOp(name="Disabled Selection")
public class RunDisabledOp extends OpMode
{
    private OpMode selectedOp = null;
    private boolean runInit = false;

    @Override
    public void init()
    {
        final List<Class> classes = DisabledOpClassFilter.getInstance().classes;
        String[] classNames = new String[classes.size()];

        for (int i = 0; i < classes.size(); i++)
        {
            //classNames[i] = classes.get(i).getName();
            classNames[i] = classes.get(i).getSimpleName();
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(hardwareMap.appContext);
        builder.setTitle("Select a Disabled OpMode to run")
                .setItems(classNames, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try
                        {
                            selectedOp = (OpMode) classes.get(which).newInstance();
                            runInit = true;

                            selectedOp.hardwareMap = RunDisabledOp.this.hardwareMap;
                            selectedOp.gamepad1 = RunDisabledOp.this.gamepad1;
                            selectedOp.gamepad2 = RunDisabledOp.this.gamepad2;
                            selectedOp.telemetry= RunDisabledOp.this.telemetry;
                        }
                        catch(Exception e)
                        {
                            telemetry.addData("Error", "Unable to instantiate OpMode due to: " + e.getClass().getName());
                            telemetry.update();
                        }
                    }
                });


        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        relativeLayout.post(new Runnable()
        {
            @Override
            public void run()
            {
                builder.create().show();
            }
        });
    }

    @Override
    public void init_loop()
    {
        if(runInit)
        {
            runInit = false;
            selectedOp.init();
        }
        else if(selectedOp != null)
        {
            selectedOp.init_loop();
        }
    }

    @Override
    public void start()
    {
        selectedOp.start();
    }

    @Override
    public void loop()
    {
        //selectedOp.time = time;
        //selectedOp.loop();
    }

    @Override
    public void stop()
    {
        //selectedOp.stop();
    }
}