package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;

import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CloseableFrame;

/**
 * Created by ssuri on 1/30/18.
 *
 */

@Autonomous(name="CameraJewelOp")
public class CameraJewelOp extends OpMode
{
    VuforiaLocalizer vuforia;

    @Override
    public void init()
    {
        int cameraMonitorViewId = hardwareMap.appContext.getResources()
                .getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "ASv2MNj/////AAAAGQukwPKRd0YcsSlpoJYzs9EdjNGpnGv0mY+oWYr923xV6ZP+Tm9A7ZjZvdw7KY3iqJ/2AXpNLeHZLylMumJd46ZYL4zpkdjPY6OwGwUmQBrgo6MXWgIM6bKgp/0M1SJnb8yYpFjzTAqAXtXqotY5KPiLkelgBeCuPYc+NUAlf6vSxjEr7+Zezid1O2zV3dRV/FlaBJN9MQsgWOvPQfsTiKqgpEr2b4pLG8PMqL/HU3RvuEexsWSv5eN6mWtx8Vt7m+GSBC6xo9vxR+gaTLsi19RAXTPCq4UhoQvrFYIORotVeVa5zIhZXlpMc09NZT25e6DcOPTv2eloL55O2/FK81AGay8e4urLNQ5wF3vknehR";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        vuforia.setFrameQueueCapacity(1);
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB888, true);
    }

    @Override
    public void loop()
    {
        try
        {
            CloseableFrame frame = vuforia.getFrameQueue().take();

            Image img = null;
            for(int i = 0; i < frame.getNumImages(); i++)
            {
                Image possImg = frame.getImage(i);
                if(img.getFormat() == PIXEL_FORMAT.RGB888)
                {
                    img = possImg;
                }
            }
            if(img != null)
            {
                // reference: array[x][y]
                ByteBuffer buf = img.getPixels();
                byte[][][] imgArr = new byte[img.getWidth()][img.getHeight()][3];
                for(int i = 0; i < img.getWidth() * img.getHeight(); i++)
                {
                    int x = i % img.getWidth();
                    int y = i / img.getWidth();

                    imgArr[x][y][0] = buf.get(i);
                    imgArr[x][y][1] = buf.get(i+1);
                    imgArr[x][y][2] = buf.get(i+2);
                }

                CSVWriter writer = new CSVWriter("JewelTest.csv");
            }
        }
        catch(InterruptedException e)
        {
            // InterruptedException:
            // Thread was interrupted while watiing for the blocking queue to return a value
            // This means (most likely) that the OpMode has already ended
            // Thus, we don't need to do anything
            // ssuri 01/30/18
        }

    }

    private String decode(int format)
    {
        switch(format)
        {
            case PIXEL_FORMAT.GRAYSCALE:
                return "GRAYSCALE";
            case PIXEL_FORMAT.INDEXED:
                return "INDEXED";
            case PIXEL_FORMAT.RGB565:
                return "RGB565";
            case PIXEL_FORMAT.RGB888:
                return "RGB888";
            case PIXEL_FORMAT.RGBA8888:
                return "RGBA8888";
            case PIXEL_FORMAT.UNKNOWN_FORMAT:
                return "UNKNOWN_FORMAT";
            case PIXEL_FORMAT.YUV:
                return "YUV";
        }

        return "lol wut";
    }
}
