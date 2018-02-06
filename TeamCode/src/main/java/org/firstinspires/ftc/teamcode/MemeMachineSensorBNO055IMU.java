/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import java.util.Locale;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous(name = "Sensor: BNO055 IMU", group = "Sensor")
public class MemeMachineSensorBNO055IMU extends LinearOpMode
{
	// The IMU sensor object
	BNO055IMU imu;

	@Override
	public void runOpMode()
	{
		BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
		parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
		parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
		//parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
		parameters.loggingEnabled = true;
		parameters.loggingTag = "IMU";
		parameters.accelerationIntegrationAlgorithm = new MemeMachineAccelerationIntegrator();
		parameters.mode = BNO055IMU.SensorMode.IMU;

		imu = hardwareMap.get(BNO055IMU.class, "imu");
		imu.initialize(parameters);

		// Wait until we're told to go
		waitForStart();

		// Start the logging of measured acceleration
		imu.startAccelerationIntegration(new Position(), new Velocity(), 100);

		// Loop and update the dashboard
		// noinspection StatementWithEmptyBody
		while (opModeIsActive() && !imu.isGyroCalibrated());
		// noinspection StatementWithEmptyBody
		//while (opModeIsActive() && !imu.isSystemCalibrated());

		while (opModeIsActive())
		{
			// State used for updating telemetry
			Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
			Acceleration gravity = imu.getGravity();

			telemetry.update();
		}
	}
}
