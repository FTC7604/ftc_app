package org.firstinspires.ftc.teamcode;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Locale;

public class CSVWriter
{
	private Writer writer;

	public CSVWriter(String fileName)
	{
		// String directoryPath = "/sdcard/FIRST/CSVWriter";
		String directoryPath = Environment.getExternalStorageDirectory().getPath() + "/FIRST/CSVWriter";
		String filePath = directoryPath + "/" + fileName + ".csv";

		// noinspection ResultOfMethodCallIgnored
		new File(directoryPath).mkdirs(); // Make sure that the directory exists

		try
		{
			writer = new FileWriter(filePath);
		}
		catch (IOException ignored)
		{}
	}

	public void writeLine(Object... toWrite)
    {
        for(int i = 0; i < toWrite.length; i++)
        {
            if(i == toWrite.length - 1)
            {
                try
                {
                    writer.write(toWrite[i] + "\n");
                } catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
            else
            {
                try
                {
                    writer.write(toWrite[i] + ",");
                } catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}