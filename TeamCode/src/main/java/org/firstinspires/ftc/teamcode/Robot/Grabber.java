package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logger;

public class Grabber extends RobotComponent {
	public Grabber(OpMode opmode) {
		super(opmode);
	}

	Logger logger = null;
	CRGrabber baseRightGrabber = new CRGrabber();
	CRGrabber altRightGrabber = new CRGrabber();
	CRGrabber baseLeftGrabber = new CRGrabber();
	CRGrabber altLeftGrabber = new CRGrabber();
	Side side = null;

	void init(Telemetry telemetry, CRServo baseRightServo, CRServo altRightServo, CRServo baseLeftServo, CRServo altLeftServo) {
		logger = new Logger(telemetry);
		this.baseRightGrabber.set(baseRightServo);
		baseRightGrabber.definePositions(0, 0.45, 0);
		this.altRightGrabber.set(altRightServo);
		altRightGrabber.definePositions(0.25, 0.9, 0.8);
		this.baseLeftGrabber.set(baseLeftServo);
		baseLeftGrabber.definePositions(1, 0.4, 1);
		this.altLeftGrabber.set(altLeftServo);
		altLeftGrabber.definePositions(1, 0, 0.1);
	}

	public static enum Side {
		LEFT, RIGHT
	}

	public static enum Level {
		BASE, ALT
	}

	public void flip(Level level, Side side) {
		if(level == Level.BASE) {
			if(side == Side.RIGHT) {
				baseRightGrabber.flip();
			} else {
				baseLeftGrabber.flip();
			}
		} else {
			if(side == Side.RIGHT) {
				altRightGrabber.flip();
			} else {
				altLeftGrabber.flip();
			}
		}
	}

	public void raise() {
		baseRightGrabber.raise();
		altRightGrabber.raise();
		baseLeftGrabber.raise();
		altLeftGrabber.raise();
	}

	public void setSide(Side side) {
		this.side = side;
	}

	public void prepareForStone() {
		if(side == Side.RIGHT) {
			baseRightGrabber.lower();
			altRightGrabber.raise();
		} else {
			baseLeftGrabber.lower();
			altLeftGrabber.raise();
		}
	}

	public void grabStone() {
		if(side == Side.RIGHT) {
			altRightGrabber.lower();
		} else {
			altLeftGrabber.lower();
		}
	}

	public void raiseStone() {
		if(side == Side.RIGHT) {
			baseRightGrabber.raise();
		} else {
			baseLeftGrabber.raise();
		}
	}

	public void dropBase() {
		if(side == Side.RIGHT) {
			baseRightGrabber.lower();
		} else {
			baseLeftGrabber.lower();
		}
	}

	public void dropAlt() {
		if(side == Side.RIGHT) {
			altRightGrabber.raise();
		} else {
			altLeftGrabber.raise();
		}
	}

	public void home() {
		baseRightGrabber.home();
		altRightGrabber.home();
		baseLeftGrabber.home();
		altLeftGrabber.home();

	}

	@Override
	public void stopAllMotors() {

	}

	@Override
	void logTeleOpData() {
		logger.completeLog("Base Right Grabber", String.valueOf(baseRightGrabber.getPosition()));
		logger.completeLog("Alt Right Grabber", String.valueOf(altRightGrabber.getPosition()));
		logger.completeLog("Base Left Grabber", String.valueOf(baseLeftGrabber.getPosition()));
		logger.completeLog("Alt Left Grabber", String.valueOf(altLeftGrabber.getPosition()));
	}
}