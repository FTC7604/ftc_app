package org.firstinspires.ftc.ftccommon.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.internal.opmode.ClassFilter;

import java.util.LinkedList;
import java.util.List;

public class DisabledOpClassFilter implements ClassFilter
{
	public List<Class> classes = new LinkedList<>();

	private static DisabledOpClassFilter theInstance = new DisabledOpClassFilter();

	// Singleton
	public static DisabledOpClassFilter getInstance()
	{
		return theInstance;
	}

	private DisabledOpClassFilter()
	{
	}

	// Class filter
	@Override
	public void filterAllClassesStart()
	{
		classes.clear();
	}

	@Override
	public void filterOnBotJavaClassesStart()
	{
		// do nothing
	}

	@Override
	public void filterClass(Class clazz)
	{
		if (clazz.getAnnotation(Autonomous.class) != null
				|| clazz.getAnnotation(TeleOp.class) != null)
		{
			if (clazz.getAnnotation(Disabled.class) != null)
			{
				if (OpMode.class.isAssignableFrom(clazz))
				{
					classes.add(clazz);
				}
			}
		}
	}

	@Override
	public void filterOnBotJavaClass(Class clazz)
	{
		filterClass(clazz);
	}

	@Override
	public void filterAllClassesComplete()
	{
		// do nothing
	}

	@Override
	public void filterOnBotJavaClassesComplete()
	{
		filterAllClassesComplete();
	}
}