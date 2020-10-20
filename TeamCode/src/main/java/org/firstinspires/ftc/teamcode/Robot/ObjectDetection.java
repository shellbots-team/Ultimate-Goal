package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.Logger;

import java.util.List;

public class ObjectDetection {

	private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
	private static final String LABEL_FIRST_ELEMENT = "Quad";
	private static final String LABEL_SECOND_ELEMENT = "Single";

	private static final String VUFORIA_KEY = "AbvNjVH/////AAABmVkayA+Ah0Tct8evC0icZRZwcm2L+0efbPf1kZaN5sT94HCg5EGRv17PHjGEcUBgUh6/L5my6JfI+fjIwSf/FHAQiClUon4IwXoeT7rIKO1rjVrBKWmQggW2GfhvJ1rNiuFU1bfmWsuaHeo4APWeJ4nto7YvVMbVvRh8/N+11EpMOgfyMOYZFaQ/crgqP4yq231mSneK5EhUAsAU7myl4FFjoPg+K75z5BSklif09FlrKfvEc8PhIeAYg+XaOMOeZDWDO6As9tbXPNlAx/DecWiyNqiaSmX30rAdbp1o8nUkrLplTO2HJK4zoy6s7itGXDDQuJ2ZNfC8dIjPAOLEmX7zkF72J3Kq/cb7f5IG914M";

	private static final float MINIMUM_CONFIDENCE = 0.8f;

	private VuforiaLocalizer vuforia;
	private TFObjectDetector tfod;

	private HardwareMap hardwareMap;
	private Logger logger;

	ObjectDetection(HardwareMap hardwareMap, Telemetry telemetry) {
		this.hardwareMap = hardwareMap;
		logger = new Logger(telemetry);

	}

	/**
	 * Check what and how many things have been detected and print out their values
	 */
	public void checkDetections() {
		List<Recognition> updatedRecognitions;
		if (tfod != null) {
			// getUpdatedRecognitions() will return null if no new information is available since
			// the last time that call was made.
			updatedRecognitions = tfod.getUpdatedRecognitions();
			if (updatedRecognitions != null) {
				for (Recognition recognition : updatedRecognitions) {
					logger.completeLog("ObjectDetection", recognition.toString());
				}
			}
		}
	}
	/*
				logger.numberLog("# Object Detected", updatedRecognitions.size());
				updatedRecognitions.sort(new Comparator<Recognition>() {
					@Override
					public int compare(Recognition r1, Recognition r2) {
						if(r1.getConfidence() > r2.getConfidence()) {
							return -1;
						} else {
							return 1;
						}
					}
				});
				int i = 0;
				for (Recognition recognition : updatedRecognitions) {
					logger.completeLog("ObjectDetection",
							"\nDetection Number " + i +
									"\nLabel " + recognition.getLabel() +
									"\nConfidence: " + recognition.getConfidence() +
									"\nLeft: " + recognition.getLeft() +
									"\nRight: " + recognition.getRight() +
									"\nTop: " + recognition.getTop() +
									"\nBottom: " + recognition.getBottom());
					i++;
				}
				logger.update();
				for (Recognition recognition : updatedRecognitions) {
					if (recognition.getLabel().equals(LABEL_SECOND_ELEMENT)) {
						logger.completeLog("ObjectDetection", "Found detection");
						return recognition;
					}
				}
			}
		}
		logger.completeLog("Object Detection", "No objects detected");
		return new Recognition() {
			@Override
			public String getLabel() {
				return null;
			}

			@Override
			public float getConfidence() {
				return 0;
			}

			@Override
			public float getLeft() {
				return 0.0f;
			}

			@Override
			public float getRight() {
				return 0.0f;
			}

			@Override
			public float getTop() {
				return 12345f;
			}

			@Override
			public float getBottom() {
				return 0.5f;
			}

			@Override
			public float getWidth() {
				return 0;
			}

			@Override
			public float getHeight() {
				return 0;
			}

			@Override
			public int getImageWidth() {
				return 0;
			}

			@Override
			public int getImageHeight() {
				return 0;
			}

			@Override
			public double estimateAngleToObject(AngleUnit angleUnit) {
				return 0;
			}
		};
	}
	*/

	// Below is all the initialization/setup for object detection...

	/**
	 * Initialize the Vuforia localization engine.
	 */
	private void initVuforia() {
		/*
		 * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
		 */
		VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

		parameters.vuforiaLicenseKey = VUFORIA_KEY;
		parameters.cameraName = hardwareMap.get(WebcamName.class, "TensorFlowCamera");

		//  Instantiate the Vuforia engine
		vuforia = ClassFactory.getInstance().createVuforia(parameters);

		// Loading trackables is not necessary for the TensorFlow Object Detection engine.
	}

	/**
	 * Initialize the TensorFlow Object Detection engine.
	 */
	private void initTfod() {
		int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
				"tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
		TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
		tfodParameters.minResultConfidence = MINIMUM_CONFIDENCE;
		tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
		tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
	}

	/**
	 * Check if TFOD exists, and if it does activate it
	 */
	public void startTFOD() {
		if(tfod != null) {
			tfod.activate();
		}
	}

	/**
	 * Check if TFOD exists, and if it does stop deactivate it
	 */
	public void stopTFOD() {
		if(tfod != null) {
			tfod.deactivate();
		}
	}

	/**
	 * Initialize vuforia and then tensorflow object detection
	 */
	public void initializeObjectDetection() {
		initVuforia();
		initTfod();
	}
}
