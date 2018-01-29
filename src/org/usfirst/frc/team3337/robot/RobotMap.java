//We're in this package.
package org.usfirst.frc.team3337.robot;

//This class is RobotMap.
public class RobotMap
{
	/*
	 * These numbers are all public (accessible to all), final (unchangeable),
	 * and static (accessible without a RobotMap instance.
	 */
	
	public final static int PIGEON_IMU_CAN_DEVICE_ID = 2; //Pigeon Gyro CAN ID.
	
	public final static int LEFT_FRONT_TALON_SRX_CAN_DEVICE_ID = 5;
	public final static int lEFT_BACK_TALON_SRX_CAN_DEVICE_ID = 4;
	public final static int RIGHT_FRONT_TALON_SRX_CAN_DEVICE_ID = 2;
	public final static int RIGHT_BACK_TALON_SRX_CAN_DEVICE_ID = 7;
	
	public final static int DRIVE_STICK_PORT = 0;
	public final static int AUX_STICK_PORT = 1;
	
	//Buttons on Controller
	public final static int DRIVE_SWITCH_TOGGLE = 4; //Y Button
	public final static int SPEED_DECREASE = 6; //Right Button
	
	//Axes on Controller
	public final static int GTA_FORWARD = 2; //Left Trigger
	public final static int GTA_BACKWARD = 3; //Right Trigger
	
}
