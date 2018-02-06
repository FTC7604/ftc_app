package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.ColorInt;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.CameraDevice;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CloseableFrame;

/**
 * Created by ssuri on 1/30/18.
 *
 */

@Autonomous(name="CameraJewelOp")
public class CameraJewelOp extends LinearOpMode
{
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.US);

    @Override
    public void runOpMode()
    {
        int cameraMonitorViewId = hardwareMap.appContext.getResources()
                .getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "ASv2MNj/////AAAAGQukwPKRd0YcsSlpoJYzs9EdjNGpnGv0mY+oWYr923xV6ZP+Tm9A7ZjZvdw7KY3iqJ/2AXpNLeHZLylMumJd46ZYL4zpkdjPY6OwGwUmQBrgo6MXWgIM6bKgp/0M1SJnb8yYpFjzTAqAXtXqotY5KPiLkelgBeCuPYc+NUAlf6vSxjEr7+Zezid1O2zV3dRV/FlaBJN9MQsgWOvPQfsTiKqgpEr2b4pLG8PMqL/HU3RvuEexsWSv5eN6mWtx8Vt7m+GSBC6xo9vxR+gaTLsi19RAXTPCq4UhoQvrFYIORotVeVa5zIhZXlpMc09NZT25e6DcOPTv2eloL55O2/FK81AGay8e4urLNQ5wF3vknehR";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        vuforia.setFrameQueueCapacity(1);
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);

        CameraDevice cam = CameraDevice.getInstance();
        cam.setField("auto-whitebalance-lock-supported", "false");

        waitForStart();


        try
        {
            CloseableFrame frame = vuforia.getFrameQueue().take();

            Image img = null;
            for (int i = 0; i < frame.getNumImages(); i++)
            {
                Image possImg = frame.getImage(i);
                if (possImg.getFormat() == PIXEL_FORMAT.RGB565)
                {
                    img = possImg;
                }
            }
            Log.d("MemeMachine", "Instantiating ImageWriter");

            if (img != null && img.getPixels() != null)
            {
                Bitmap bm = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.RGB_565);
                bm.copyPixelsFromBuffer(img.getPixels());


                String directoryPath = Environment.getExternalStorageDirectory().getPath() + "/FIRST/CSVWriter";
                String filePath = directoryPath + "/bitmap.png";

                // noinspection ResultOfMethodCallIgnored
                new File(directoryPath).mkdirs(); // Make sure that the directory exists
                try
                {
                    FileOutputStream fOut = new FileOutputStream(new File(filePath));

                    bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (IOException ignored)
                {
                    // lol no
                }

//                double[][] vals = new double[3][bm.getWidth()];
                double numR = 0, numG = 0, numB = 0, denomR = 0, denomG = 0, denomB = 0;
                for (int x = 0; x < bm.getWidth(); x++)
                {
                    double redSum = 0;
                    double greenSum = 0;
                    double blueSum = 0;
                    for (int y = 0; y < bm.getHeight(); y++)
                    {
                        @ColorInt int color = bm.getPixel(x, y);
                        redSum += (color >> 16) & 0xff;
                        greenSum += (color >> 8) & 0xff;
                        blueSum += color & 0xff;
                    }
                    double height = bm.getHeight();
//                    vals[0][x] = redSum / height;
//                    vals[1][x] = greenSum / height;
//                    vals[2][x] = blueSum / height;


                    numR += x * (redSum / height);
                    denomR += (redSum / height);
                    numG += x * (greenSum / height);
                    denomG += (greenSum / height);
                    numB += x * (blueSum / height);
                    denomB += (blueSum / height);
                }

                final double[] expectedValues = new double[] { numR / denomR, numG / denomG, numB / denomB };
//                final double[] expectedValues = new double[] { expectedValue(vals[0]), expectedValue(vals[1]), expectedValue(vals[2]) };
                boolean redLeft = expectedValues[0] < expectedValues[2];

                while(opModeIsActive())
                {
                    telemetry.addData("Ball Position", redLeft ? "Red - Blue" : "Blue - Red");
                    telemetry.update();
                }
//                final double[] means = new double[] { mean(vals[0]), mean(vals[1]), mean(vals[2]) };
//                final double[] stdDevs = new double[] { stdDev(vals[0], means[0]), stdDev(vals[1], means[1]), stdDev(vals[2], means[2]) };
//                final double[] thresholds = new double[] { means[0] + stdDevs[0], means[1] + stdDevs[1], means[2] + stdDevs[2] };
//
//                List<>

            }
        } catch (InterruptedException e)
        {
            // InterruptedException:
            // Thread was interrupted while waiting for the blocking queue to return a value
            // This means (most likely) that the OpMode has already ended
            // Otherwise, it means something has gone horribly wrong
            // Thus, we don't need to do anything
            // ssuri 01/30/18
        }
    }

    private static class Range
    {
        int start, end;
    }

    private double expectedValue(double[] arr)
    {
        double num = 0;
        double denom = 0;
        for(int i = 0; i < arr.length; i++)
        {
            num += i * arr[i];
            denom += arr[i];
        }

        return num / denom;
    }

    private double mean(double[] arr)
    {
        double sum = 0;
        for(double d : arr)
        {
            sum += d;
        }
        return sum / arr.length;
    }

    private double stdDev(double[] arr, double mean)
    {
        double variance = 0;
        for (double d : arr)
        {
            variance += Math.pow(d - mean, 2) / arr.length;
        }
        return Math.sqrt(variance);
    }
}
