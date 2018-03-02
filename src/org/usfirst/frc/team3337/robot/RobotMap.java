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
	
	//CAN Motors
	public final static int LEFT_FRONT_TALON_SRX_CAN_DEVICE_ID = 1; //Yellow
	public final static int lEFT_BACK_TALON_SRX_CAN_DEVICE_ID = 2; //Purple; has encoder
	public final static int RIGHT_FRONT_TALON_SRX_CAN_DEVICE_ID = 3; //Orange
	public final static int RIGHT_BACK_TALON_SRX_CAN_DEVICE_ID = 4; //Blue; has encoder
	
	public final static int LIFT_MOTOR_1 = 5;//temp number for lift mechanism
	public final static int LIFT_MOTOR_2 = 6;
	
	//public final static int CLIMB_MOTOR_1 = 7;
	//public final static int CLIMB_MOTOR_2 = 8;
	
	//PWM Motors
	public final static int LEFT_ARM = 0; //PWM
	public final static int RIGHT_ARM = 1; //PWM
	
	//public final static int INTAKE_MOTOR_1 = 3;
	//public final static int INTAKE_MOTOR_2 = 4;
	
	//Pneumatics Components
	public final static int EXTEND_PISTON = 0;
	public final static int RETRACT_PISTON = 1;
	public final static int SUPPORT_PISTON = 2;
	
	public final static int DRIVE_STICK_PORT = 0;
	public final static int AUX_STICK_PORT = 1;
	
	//Buttons on Drive Controller
	public final static int DRIVE_SWITCH_TOGGLE = 4; //Y Button
	public final static int SPEED_DECREASE = 6; //Right Button
	
	//Buttons on Aux Controller
	//autoRaiseElevator, autoLowerElevator, switchButton, manualRaiseElevator, manualLowerElevator
	/*public final static int RAISE_ELEVATOR_MANUAL = 3; //Right Trigger
	public final static int LOWER_ELEVATOR_MANUAL = 2; //Left Trigger
	public final static int RAISE_ELEVATOR_AUTO = 6; //Right Bumper
	public final static int LOWER_ELEVATOR_AUTO = 5; //Left Bumper*/
	public final static int SWITCH_BUTTON = 4; //Y Button
	
	//Axes on Drive Controller
	public final static int GTA_FORWARD = 3; //Left Trigger
	public final static int GTA_BACKWARD = 2; //Right Trigger
	
	//Axes on Auxillary Controller
	public final static int ELEVATOR_UP = 2;//Right Trigger
	public final static int ELEVATOR_DOWN = 3;//Left Trigger
	
	//Encoder 
	public final static int LEFT_ENCODER = 1;
	public final static int RIGHT_ENCODER = 2; 
}
