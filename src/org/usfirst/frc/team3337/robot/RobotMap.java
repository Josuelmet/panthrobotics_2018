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
	
	//Motors
	public final static int LEFT_FRONT_TALON_SRX_CAN_DEVICE_ID = 1;
	public final static int lEFT_BACK_TALON_SRX_CAN_DEVICE_ID = 2;
	public final static int RIGHT_FRONT_TALON_SRX_CAN_DEVICE_ID = 3;
	public final static int RIGHT_BACK_TALON_SRX_CAN_DEVICE_ID = 4;
	
	public final static int LIFT_MOTOR_1 = 5;//temp number for lift mechanism
	public final static int LIFT_MOTOR_2 = 6;
	
	public final static int LEFT_ARM = 1;
	public final static int RIGHT_ARM = 2;
	
	public final static int INTAKE_MOTOR_1 = 7;
	public final static int INTAKE_MOTOR_2 = 8;
	
	public final static int CLIMB_MOTOR_1 = 9;
	public final static int CLIMB_MOTOR_2 = 10;
	
	//Pneumatics Components
	public final static int CLIMBER_SOLENOID = 0;
	public final static int INTAKE_SOLENOID = 1;
	
	public final static int DRIVE_STICK_PORT = 0;
	public final static int AUX_STICK_PORT = 1;
	
	//Buttons on Drive Controller
	public final static int DRIVE_SWITCH_TOGGLE = 4; //Y Button
	public final static int SPEED_DECREASE = 6; //Right Button
	
	//Buttons on Aux Controller
	//autoRaiseElevator, autoLowerElevator, switchButton, manualRaiseElevator, manualLowerElevator
	public final static int RAISE_ELEVATOR_MANUAL = 3; //Right Trigger
	public final static int LOWER_ELEVATOR_MANUAL = 2; //Left Trigger
	public final static int RAISE_ELEVATOR_AUTO = 6; //Right Bumper
	public final static int LOWER_ELEVATOR_AUTO = 5; //Left Bumper
	public final static int SWITCH_BUTTON = 4; //Y Button
	
	//Axes on Controller
	public final static int GTA_FORWARD = 2; //Left Trigger
	public final static int GTA_BACKWARD = 3; //Right Trigger
	
}
