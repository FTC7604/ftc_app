package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@TeleOp(name = "ServoTester", group = "7604")
public class ServoTest extends OpMode {
	Servo servo;
	Servo contServo;
	
	@Override
	public void init() {
		servo = hardwareMap.servo.get("servo");
		contServo = hardwareMap.servo.get("contServo");
	}
	
	@Override
	public void loop() {
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