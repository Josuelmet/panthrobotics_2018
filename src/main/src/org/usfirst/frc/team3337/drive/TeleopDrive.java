//Our package
package main.src.org.usfirst.frc.team3337.drive;

import org.usfirst.frc.team3337.robot.Robot;
//Our other classes
import org.usfirst.frc.team3337.robot.RobotMap;
import main.src.org.usfirst.frc.team3337.drive.ToggleButton;

import com.ctre.phoenix.motorcontrol.ControlMode;
//Cross the Road Electronics packages
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.buttons.Button;
//WPI Library packages
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

//This is the class TeleopDrive. It is a child of Drive. It is abstract.
public abstract class TeleopDrive extends Drive
{

	//Class variables
	ToggleButton driveSwitchButton, speedDecrease;
	//make a RobotMap value for bumpers for auto buttons and triggers for manual buttons
	Button autoRaiseElevator, autoLowerElevator, switchButton, manualRaiseElevator, manualLowerElevator, elevatorMotorOneButton, elevatorMotorTwoButton;
	Timer tempTimer;
	
	double previousVelocity, reverse, velocity, previousAngle;	
	double joyLY, joyLX, joyRY, joyRX, gtaForwardTrigger, gtaBackwardTrigger, elevatorUp, elevatorDown;
	
	public static final double SLOW_SPEED = 0.5;
	
	//Constructor
	public TeleopDrive()
	{
		//Calling Drive's constructor
		super();
		
		driveSwitchButton = new ToggleButton(new JoystickButton(Robot.driveController, RobotMap.DRIVE_SWITCH_TOGGLE));
		speedDecrease = new ToggleButton(new JoystickButton(Robot.driveController, RobotMap.SPEED_DECREASE));
		
		elevatorMotorOneButton = new JoystickButton(Robot.auxController, RobotMap.LIFT_MOTOR_1);
		elevatorMotorTwoButton = new JoystickButton(Robot.auxController, RobotMap.LIFT_MOTOR_2);
		
		backwardsStarted = false;
		forwardsStarted = false;
	}
	
	//Arcade Drive
	private void arcadeDrive()
	{
		vL = joyLY + joyLX/2;
		vR = joyLY - joyLX/2;
		driveLeft(vL * speedLimit);
		driveRight(vR * speedLimit);
	}
	
	//Tank Drive
	private void tankDrive()
	{
		driveLeft(speedLimit * joyLY);
		driveRight(speedLimit * joyRY);
	}
	
	//This is a function that must be implemented by the child class.
	abstract void updateControls();
	
	//Making function to be called during teleopInit().
	public void init()
	{
	}
	
	//Making function to be called during teleopPeriodic().
	public void periodic()
	{
		ToggleButton.updateToggleButtons();
		updateControls(); //This gets the values for joystick inputs from the child class.
		
		/*
		 * Priority of drive modes:
		 * 1) GTA Forward
		 * 2) GTA Backward
		 * 3) Arcade and Tank
		 */
		if (gtaForwardTrigger > 0) //TODO: add turning (after autonomous)
		{
			driveForwards(gtaForwardTrigger);
			SmartDashboard.putString("status", "gtaForward");
			backwardsStarted = false;
		}
		else if (gtaBackwardTrigger > 0) //TODO: add turning (after autonomous)
		{
			driveBackwards(gtaBackwardTrigger);
			SmartDashboard.putString("status", "gtaBackward");
			forwardsStarted = false;
		}
		else if (driveSwitchButton.get())
		{
			arcadeDrive();
			backwardsStarted = false;
			forwardsStarted = false;
		}
		else
		{
			tankDrive();
			backwardsStarted = false;
			forwardsStarted = false;
		}
		if (speedDecrease.get())
    	{
    		vL *= SLOW_SPEED;
    		vR *= SLOW_SPEED;
    	}
		
		previousAngle = Robot.getRawYaw();
	}
	
	double deadZone(double value)
	{
		if (Math.abs(value) < 0.05)
			return 0;
		return value;
	}
	
}
