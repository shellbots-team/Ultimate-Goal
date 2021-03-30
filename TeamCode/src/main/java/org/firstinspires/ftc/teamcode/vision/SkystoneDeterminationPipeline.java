/*
 * Copyright (c) 2020 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode.vision;

import android.os.Environment;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.io.File;
import java.time.LocalDate;

public class SkystoneDeterminationPipeline extends OpenCvPipeline {
	/*
	 * An enum to define the skystone position
	 */
	public enum RingPosition
	{
		FOUR,
		ONE,
		NONE
	}

	/*
	 * Some color constants
	 */

	static final Scalar BLUE = new Scalar(0, 0, 255);

	static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(310, 40);

	static final int REGION_WIDTH = 120;
	static final int REGION_HEIGHT = 150;

	final int FOUR_RING_THRESHOLD = 140;
	final int ONE_RING_THRESHOLD = 132;

	Point region1_pointA = new Point(
			REGION1_TOPLEFT_ANCHOR_POINT.x,
			REGION1_TOPLEFT_ANCHOR_POINT.y);
	Point region1_pointB = new Point(
			REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
			REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);

	/*
	 * Working variables
	 */
	Mat region1_Cb;
	Mat YCrCb = new Mat();
	Mat Cb = new Mat();
	int avg1;

	// Volatile since accessed by OpMode thread w/o synchronization
	public volatile RingPosition position = RingPosition.FOUR;

	/*
	 * This function takes the RGB frame, converts to YCrCb,
	 * and extracts the Cb channel to the 'Cb' variable
	 */

	void inputToCb(Mat input)
	{
		Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
		Core.extractChannel(YCrCb, Cb, 1);
	}

	@Override
	public void init(Mat firstFrame)
	{
		inputToCb(firstFrame);

		region1_Cb = Cb.submat(new Rect(region1_pointA, region1_pointB));
	}

	Mat frame = null;

	@Override
	public Mat processFrame(Mat input)
	{
		inputToCb(input);

		avg1 = (int) Core.mean(region1_Cb).val[0];

		Imgproc.rectangle(
				input, // Buffer to draw on
				region1_pointA, // First point which defines the rectangle
				region1_pointB, // Second point which defines the rectangle
				BLUE, // The color the rectangle is drawn in
				2); // Thickness of the rectangle lines

		position = getRingPosition();

		// TODO: Initialize color scalars as static and use one of them
		Scalar color = new Scalar(255, 255, 255);
		if(position == RingPosition.FOUR) {
			color = new Scalar(0, 0, 255); // Blue
		} else if(position == RingPosition.ONE) {
			color = new Scalar(0, 255, 0); // Green
		} else if(position == RingPosition.NONE) {
			color = new Scalar(255, 0, 0); // Red
		}

		/*
		 * Four:  Blue
		 * One:   Green
		 * None:  Red
		 * Error: Black/White
		 */

		Imgproc.rectangle(
				input, // Buffer to draw on
				region1_pointA, // First point which defines the rectangle
				region1_pointB, // Second point which defines the rectangle
				color, // The color the rectangle is drawn in
				// -1); // Negative thickness means solid fill
				4); // Negative thickness means solid fill

		frame = input;
		return input;
	}

	public int getAnalysis() {
		return avg1;
	}

	public RingPosition getRingPosition() {
		if(avg1 > FOUR_RING_THRESHOLD){
			return RingPosition.FOUR;
		}else if (avg1 > ONE_RING_THRESHOLD){
			return RingPosition.ONE;
		}else{
			return RingPosition.NONE;
		}
	}

	public void saveLastFrame() {
		File path = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DCIM);
		Imgcodecs.imwrite(path + "/1.jpg", frame);
	}
}