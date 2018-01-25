package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "ServoTest")
public class ServoTest extends LinearOpMode {
	Servo servo;
	Servo contServo;
	
	@Override
	public void runOpMode() {
		servo = hardwareMap.servo.get("servo");
		contServo = hardwareMap.servo.get("contServo");
		waitForStart();
		servo.setPosition(1);
		sleep(500);
		servo.setPosition(0);
		sleep(500);
		servo.setPosition(.5);
		sleep(500);
		contServo.setPosition(1);
		sleep(500);
		contSrvo.setPosition(0);
		sleep(500);
		contServo.setPosition(.5);
		sleep(500);
	}
}
