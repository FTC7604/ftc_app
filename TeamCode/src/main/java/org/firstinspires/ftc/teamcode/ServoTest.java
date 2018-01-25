package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "ServoTest")
public class ServoTest extends LinearOpMode {
	Servo servo;
	CRServo contServo;

	@Override
	public void runOpMode() {
		servo = hardwareMap.servo.get("servo");
		contServo = hardwareMap.crservo.get("contServo");
		waitForStart();
		servo.setPosition(1);
		sleep(3000);
		contServo.setPower(1);
		sleep(3000);
	}
}
