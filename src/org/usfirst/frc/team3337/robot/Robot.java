//We're in the package below
package org.usfirst.frc.team3337.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

//We're using these other program files below for their functions.

//Cross the Road Electronics packages
import com.ctre.phoenix.motorcontrol.can.TalonSRX; //CANTalon class
import com.ctre.phoenix.sensors.PigeonIMU; //Pigeon gyro class

//WPI Library packages
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//3337 packages
import main.src.org.usfirst.frc.team3337.drive.TeleopGameDrive;

//This is our class name. It is a child of IterativeRobot.
public class Robot extends IterativeRobot {
	
	//Declaring Variables
	Joystick driveController, auxController;
	PigeonIMU pigeonGyro;
	TalonSRX leftFront, leftBack, rightFront, rightBack;
	TeleopGameDrive teleopDrive;
	
	
	
    //IterativeRobot has functions like the one below, hence the @Override.
	@Override
	public void robotInit()
	{
		//Initialize motors
		leftFront = new TalonSRX(RobotMap.LEFT_FRONT_TALON_SRX_CAN_DEVICE_ID);
		leftBack = new TalonSRX(RobotMap.lEFT_BACK_TALON_SRX_CAN_DEVICE_ID);
		rightFront = new TalonSRX(RobotMap.RIGHT_FRONT_TALON_SRX_CAN_DEVICE_ID);
		rightBack = new TalonSRX(RobotMap.RIGHT_BACK_TALON_SRX_CAN_DEVICE_ID);
		
		//Initializing joystick
		driveController = new Joystick(RobotMap.DRIVE_STICK_PORT);
		
		//Give pigeonGyro value.
		pigeonGyro = new PigeonIMU(RobotMap.PIGEON_IMU_CAN_DEVICE_ID);
		teleopDrive = new TeleopGameDrive(leftFront, leftBack, rightFront, rightBack, driveController);
	}


	@Override
	public void autonomousInit()
	{
		
	}


	@Override
	public void autonomousPeriodic()
	{
		/*
		 * How to read from the Pigeon Gyro:
		 * double [] yawPitchRoll = new double[3];
		 * pigeonGyro.getYawPitchRoll(yawPitchRoll);
		 * double yaw = yawPitchRoll[0];
		 */
	}

	@Override
	public void teleopInit()
	{
		teleopDrive.init();
	}
	@Override
	public void teleopPeriodic()
	{
		
		teleopDrive.periodic();

	}

	@Override
	public void testPeriodic()
	{
		
	}
	
}
