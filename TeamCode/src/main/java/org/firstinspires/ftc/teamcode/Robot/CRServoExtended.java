package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.CRServo;

public class CRServoExtended {

	private CRServo servo = null;
	private double closedPosition = 0;
	private double openPosition = 0;

	public CRServoExtended(CRServo servo) {
		this.servo = servo;
	}

	public void definePositions(double closedPosition, double openPosition) {
		this.closedPosition = closedPosition;
		this.openPosition = openPosition;
	}

	public void close() {
		setServoPosition(closedPosition);
	}

	public void open() {
		setServoPosition(openPosition);
	}

	public void setServoPosition(double position) {
		this.servo.getController().setServoPosition(this.servo.getPortNumber(), position);
	}

	public double getPosition() {
		return this.servo.getController().getServoPosition(this.servo.getPortNumber());
	}
}