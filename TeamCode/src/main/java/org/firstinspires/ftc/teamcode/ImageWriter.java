package org.firstinspires.ftc.teamcode;

import android.util.Log;

import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.Image;

/**
 * Created by ssuri on 1/30/18.
 *
 */

@Autonomous(name = "CameraJewelOp")
public class ImageWriter
{
	private Set<Listener> listeners = new HashSet<>();
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.US);

	public interface Listener
	{
		void processed(String filePath);
	}

	public void addListener(Listener listener)
	{
		listeners.add(listener);
	}

	public void removeListener(Listener listener)
	{
		listeners.remove(listener);
	}

	public void process(final Image img)
	{
		Thread t = new Thread()
		{
			@Override
			public void run()
			{
				doWork(img);
			}
		};
		t.start();
	}

	private byte[][][] imgArr;
	private void doWork(Image img)
	{
		Log.d("MemeMachine", "Thread Launched");

		if (img != null)
		{
			Log.d("MemeMachine", "Img Not Null");

			// reference: array[x][y]
			ByteBuffer buf = img.getPixels();
			Log.d("MemeMachine", "Allocating Array" );
			imgArr = new byte[img.getWidth()][img.getHeight()][3];
			Log.d("MemeMachine", "Width: " + img.getWidth());
			Log.d("MemeMachine", "Height: " + img.getHeight());
			Log.d("MemeMachine", "Total: " + (img.getWidth() * img.getHeight()));
//			for (int i = 0; i < img.getWidth() * img.getHeight(); i++)
//			{
//				int x = i % img.getWidth();
//				int y = i / img.getWidth();
//
//				Log.d("MemeCap", ""+buf.capacity());
//				Log.d("MemeExp", ""+img.getWidth() * img.getHeight() * 3);
//CSV
////				imgArr[x][y][0] = buf.get();
////				imgArr[x][y][1] = buf.get();
////				imgArr[x][y][2] = buf.get();
////				Log.d("MemeMachine", String.format("Buffer iter %d=(%d, %d)", i, x, y));
////				Log.d("MemeMachine1", x + " " + y);
////				Log.d("MemeMachine2", (img.getWidth() * img.getHeight() * 3) + " " + buf.capacity());
//
//			}

			CSVWriter writer = new CSVWriter("JewelTest-" + dateFormat.format(new Date()));

			Log.d("MemeMachine", "CSV Writer created");

			Object[] red = new Object[imgArr.length + 1], green = new Object[imgArr.length + 1],
					blue = new Object[imgArr.length + 1];
			for (int x = 0; x < imgArr.length; x++)
			{
				int redSum = 0;
				int greenSum = 0;
				int blueSum = 0;
				for (int y = 0; y < imgArr[x].length; y++)
				{
					redSum += imgArr[x][y][0];
					greenSum += imgArr[x][y][1];
					blueSum += imgArr[x][y][2];
				}
				double height = imgArr[x].length;
				red[x + 1] = redSum / height;
				green[x + 1] = greenSum / height;
				blue[x + 1] = blueSum / height;
			}

			Log.d("MemeMachine", "Array generated");

			red[0] = "Red";
			green[0] = "Green";
			blue[0] = "Blue";

			writer.writeLine(red);
			writer.writeLine(green);
			writer.writeLine(blue);

			Log.d("MemeMachine", "Lines written");

			String filePath = writer.getFilePath();
			for (Listener listener : listeners)
			{
				listener.processed(filePath);
			}

			Log.d("MemeMachine", "Listeners Triggered");
		}
		else
		{
			Log.d("MemeMachine", "Img Null");
		}
	}
}
