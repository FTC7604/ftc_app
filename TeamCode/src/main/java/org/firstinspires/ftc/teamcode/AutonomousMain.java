package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import android.support.annotation.ColorInt;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.vuforia.CameraDevice;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.NavUtil;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static java.lang.Math.abs;

/**
 * Created by Declan on 11/13/2017.
 * Mostly deleted by Sameer on 02/07/2018
 */

public class AutonomousMain extends AbstractLinearOpMode {
    private Location location = null;
    private Robot7604 robot;
    @SuppressWarnings("FieldCanBeLocal")
    private VuforiaTrackable relicTemplate;
    private VuforiaLocalizer vuforia;

    public enum Location
    {
        RedLeft(true, true), RedRight(true, false), BlueLeft(false, true), BlueRight(false, false);
        private boolean red;
        private boolean left;
        Location(boolean red, boolean left)
        {
            this.red = red;
            this.left = left;
        }
    }

    @SuppressWarnings("WeakerAccess")
    public AutonomousMain(Location location)
    {
        this.location = location;
    }

    @Override
    public void initLinear()
    {
        robot = new Robot7604(this.callingOpMode);

        print("Robot Initialized");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        //parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new MemeMachineAccelerationIntegrator();
        parameters.mode = BNO055IMU.SensorMode.IMU;

        robot.imu.initialize(parameters);

        print("IMU Initialized");

        ////////////////////////////
        // VUFORIA INITIALIZATION //
        ////////////////////////////
        int cameraMonitorViewId = hardwareMap.appContext.getResources()
                .getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        VuforiaLocalizer.Parameters vuforiaParameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        vuforiaParameters.vuforiaLicenseKey = "ASv2MNj/////AAAAGQukwPKRd0YcsSlpoJYzs9EdjNGpnGv0mY+oWYr923xV6ZP+Tm9A7ZjZvdw7KY3iqJ/2AXpNLeHZLylMumJd46ZYL4zpkdjPY6OwGwUmQBrgo6MXWgIM6bKgp/0M1SJnb8yYpFjzTAqAXtXqotY5KPiLkelgBeCuPYc+NUAlf6vSxjEr7+Zezid1O2zV3dRV/FlaBJN9MQsgWOvPQfsTiKqgpEr2b4pLG8PMqL/HU3RvuEexsWSv5eN6mWtx8Vt7m+GSBC6xo9vxR+gaTLsi19RAXTPCq4UhoQvrFYIORotVeVa5zIhZXlpMc09NZT25e6DcOPTv2eloL55O2/FK81AGay8e4urLNQ5wF3vknehR";

        vuforiaParameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforia = ClassFactory.createVuforiaLocalizer(vuforiaParameters);

        vuforia.setFrameQueueCapacity(1);
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);

        CameraDevice cam = CameraDevice.getInstance();
        cam.setField("auto-whitebalance-lock-supported", "false");

        VuforiaTrackables relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        relicTrackables.activate();

        print("Vuforia Settings Initialized");
    }

    @Override
    public void runLinear()
    {
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

        print(vuMark);

        boolean red = location.red;
        boolean left = location.left;

        ///////////////////////////////
        // IMU INITIALIZATION PART 2 //
        ///////////////////////////////
        robot.imu.startAccelerationIntegration(new org.firstinspires.ftc.robotcore.external.navigation.Position(), new Velocity(), 100);

        // noinspection unused
        long timeInit = System.currentTimeMillis();

//        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
//
//        while (opModeIsActive() && System.currentTimeMillis() - timeInit < 2000)
//        {
//            vuMark = RelicRecoveryVuMark.from(relicTemplate);
//        }

        ///////////////////
        // JEWEL SCORING //
        ///////////////////
        long startTime = System.currentTimeMillis();
        double[] expectedValues = getExpectedValues(getFrame(vuforia));
        long endTime = System.currentTimeMillis();
        boolean blueLeft = (expectedValues[0] < expectedValues[2]) ^ red;

        print("red: %b, blueLeft: %b, execTime=%d", red, blueLeft, endTime - startTime);

        sleep(5000);

        // Setting Servos to their starting position, fixes random servo movement
        robot.ColorSpinLeft.setPosition(0.7);
        robot.ColorSpinRight.setPosition(0.4);

        robot.ColorLeverLeft.setPosition(1);
        robot.ColorLeverRight.setPosition(0);

        if(red)
        {
            robot.ColorLeverLeft.setPosition(0.25);
            sleep(1000);
            robot.ColorSpinLeft.setPosition(blueLeft ? 0.5 : 1);
            sleep(1000);
            robot.ColorLeverLeft.setPosition(1);
            sleep(1000);
            robot.ColorSpinLeft.setPosition(0.7);
        }
        else
        {
            robot.ColorLeverRight.setPosition(0.75);
            sleep(1000);
            robot.ColorSpinRight.setPosition(blueLeft ? 0.75 : 0.205);
            sleep(1000);
            robot.ColorLeverRight.setPosition(0);
            sleep(1000);
            robot.ColorSpinRight.setPosition(0.4);
        }
        /*
        while(opModeIsActive()){
            print("FrontLeft: " + robot.FrontLeft.getCurrentPosition() +
                    " FrontRight: " + robot.FrontRight.getCurrentPosition() +
                    " BackLeft: " + robot.BackLeft.getCurrentPosition() +
                    " BackRight: " + robot.BackRight.getCurrentPosition());
        }*/


        double first_move_th = 0;

        double fl = robot.FrontLeft.getCurrentPosition();
        double fr = robot.FrontRight.getCurrentPosition();

        double bl = robot.BackLeft.getCurrentPosition();
        double br = robot.BackRight.getCurrentPosition();



        while(first_move_th < (left ? 11000 : 9000)) {
            robot.FrontLeft.setPower(0.5);
            robot.FrontRight.setPower(0.5);
            robot.BackLeft.setPower(0.5);
            robot.BackRight.setPower(0.5);

            first_move_th = Math.max(abs(robot.FrontLeft.getCurrentPosition() - fl) + abs(robot.BackLeft.getCurrentPosition() - bl),
                    abs(robot.FrontRight.getCurrentPosition() - fr) + abs(robot.BackRight.getCurrentPosition() - br)
            );

        }
        robot.stop();
        sleep(1000);

        double turnVal = 0;
        if(red){
            if(left){
                switch(vuMark){
                    case LEFT:
                        turnVal = 60;
                        break;
                    case RIGHT:
                        turnVal = 100;
                        break;
                    default:
                        turnVal = 80;
                        break;
                }
            }
            else{
                switch(vuMark){
                    case LEFT:
                        turnVal = 55;
                        break;
                    case RIGHT:
                        turnVal = 25;
                        break;
                    default:
                        turnVal = 40;
                        break;
                }
            }
        }
        else{
            if(left){
                switch(vuMark){
                    case LEFT:
                        turnVal = 100;
                        break;
                    case RIGHT:
                        turnVal = 60;
                        break;
                    default:
                        turnVal = 80;
                        break;
                }
            }
            else{
                switch(vuMark){
                    case LEFT:
                        turnVal = 25;
                        break;
                    case RIGHT:
                        turnVal = 55;
                        break;
                    default:
                        turnVal = 40;
                        break;
                }
            }
        }

        double angularFirst = robot.imu.getAngularOrientation().firstAngle;

        /*
        while(opModeIsActive()) {
            print(robot.imu.getAngularOrientation().firstAngle - angularFirst);
        }*/

        while(abs(robot.imu.getAngularOrientation().firstAngle - angularFirst) < turnVal){

            if(red ^ left){
                robot.FrontLeft.setPower(0.5);
                robot.BackLeft.setPower(0.5);
            }
            else{
                robot.FrontRight.setPower(0.5);
                robot.BackRight.setPower(0.5);
            }

        }
        robot.stop();

        robot.drive(0.5,0);
        sleep(2000);
        robot.stop();
        robot.Flipper.setPower(-0.5);
        sleep(1000);
        robot.drive(-0.5, 0);
        sleep(500);
        robot.stop();
        robot.Flipper.setPower(0.5);
        sleep(1500);
        robot.stop();
        robot.drive(-0.5,0);
        sleep(750);
        robot.stop();
        sleep(500);
        robot.drive(0.5,0);
        sleep(2000);
        robot.drive(-0.5,0);
        sleep(1000);
        robot.stop();

        // Wait for IMU calibration
        // noinspection StatementWithEmptyBody
        while(opModeIsActive() && !robot.imu.isGyroCalibrated());
        // while (opModeIsActive() && !imu.isSystemCalibrated());
        // it might be this second one but the Internet says to use the first one for IMU mode

        print("IMU Calibrated");

    }

    private void print(Object... o)
    {
        Object[] args = new Object[o.length - 1];
        System.arraycopy(o, 1, args, 0, o.length - 1);
        telemetry.addData("", String.format(o[0].toString(), args));
        telemetry.update();
    }

    private double distance(Position pos1, Position pos2)
    {
        Position dPos = NavUtil.minus(pos1, pos2);
        return Math.sqrt(sq(dPos.x) + sq(dPos.y) + sq(dPos.z));
    }

    private double sq(double d)
    {
        return d * d;
    }

    private Bitmap getFrame(VuforiaLocalizer vuforia)
    {
        try
        {
            VuforiaLocalizer.CloseableFrame frame = vuforia.getFrameQueue().take();

            Image img = null;
            for (int i = 0; i < frame.getNumImages(); i++)
            {
                Image possImg = frame.getImage(i);
                if (possImg.getFormat() == PIXEL_FORMAT.RGB565)
                {
                    img = possImg;
                }
            }

            if (img != null && img.getPixels() != null)
            {
                Bitmap bm = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.RGB_565);
                bm.copyPixelsFromBuffer(img.getPixels());

                return bm;
            }
        }
        catch(InterruptedException ignored)
        {
            // InterruptedException:
            // Thread was interrupted while waiting or the blocking queue to return a value
            // This means (most likely) that the OpMode has already ended
            // Otherwise, it means something has gone horribly wrong
            // Thus, we don't need to do anything
            // ssuri 01/30/18
        }

        return null;
    }

    private double[] getExpectedValues(Bitmap bm)
    {
        double numR = 0, numG = 0, numB = 0, denomR = 0, denomG = 0, denomB = 0;
        int width = bm.getWidth();
        double height = bm.getHeight(); // made double to avoid explicit casting later

        for (int x = 0; x < width; x += 2)
        {
            double redSum = 0;
            double greenSum = 0;
            double blueSum = 0;
            for (int y = 0; y < bm.getHeight(); y += 2)
            {
                @ColorInt int color = bm.getPixel(x, y);
                redSum += (color >> 16) & 0xff;
                greenSum += (color >> 8) & 0xff;
                blueSum += color & 0xff;
            }

            numR += x * (redSum / height);
            denomR += (redSum / height);
            numG += x * (greenSum / height);
            denomG += (greenSum / height);
            numB += x * (blueSum / height);
            denomB += (blueSum / height);
        }

        return new double[] { numR / denomR, numG / denomG, numB / denomB };
    }
}