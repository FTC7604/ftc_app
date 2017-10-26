package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.internal.opmode.ClassFilter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ssuri on 10/24/17.
 *
 */

@Autonomous(name="Sample Selection")
public class RunDisabledOp extends OpMode implements ClassFilter
{
    private List<Class> classes = new LinkedList<>();

    @Override
    public void init()
    {

    }

    @Override
    public void loop()
    {

    }

    //Class filter
    @Override
    public void filterAllClassesStart()
    {
        classes.clear();
    }

    @Override
    public void filterOnBotJavaClassesStart()
    {
    }

    @Override
    public void filterClass(Class clazz)
    {
        if(clazz.getAnnotation(Autonomous.class) != null || clazz.getAnnotation(TeleOp.class) != null)
        {
            if(clazz.getAnnotation(Disabled.class )!= null)
            {
                if(clazz.isAssignableFrom(OpMode.class))
                {
                    classes.add(clazz);
                }
            }
        }
    }

    @Override
    public void filterOnBotJavaClass(Class clazz)
    {

    }

    @Override
    public void filterAllClassesComplete()
    {

    }

    @Override
    public void filterOnBotJavaClassesComplete()
    {

    }
}