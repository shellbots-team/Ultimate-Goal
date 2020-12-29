package org.firstinspires.ftc.teamcode;

public class SavedData {

	public static int SavedX = 0;
	public static int SavedY = 0;

	public static void save(int x, int y) {
		SavedData.SavedX = x;
		SavedData.SavedY = y;
	}
}
