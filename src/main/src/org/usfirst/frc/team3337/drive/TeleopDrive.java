//Our package
package main.src.org.usfirst.frc.team3337.drive;

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
	Joystick driveController, auxController;
	ToggleButton driveSwitchButton, speedDecrease;
	//make a RobotMap value for bumpers for auto buttons and triggers for manual buttons
	Button autoRaiseElevator, autoLowerElevator, switchButton, manualRaiseElevator, manualLowerElevator;
	Timer tempTimer;
	
	double previousVelocity, reverse;	
	double joyLY, joyLX, joyRY, joyRX, gtaForwardTrigger, gtaBackwardTrigger;
	
	public static final double SLOW_SPEED = 0.5;
	
	//Constructor
	public TeleopDrive
		(TalonSRX _leftFront, TalonSRX _leftBack, TalonSRX _rightFront, TalonSRX _rightBack, Joystick _driveStick, Joystick _auxStick)
	{
		//Calling Drive's constructor
		super(_leftFront, _leftBack, _rightFront, _rightBack);
		driveController = _driveStick;
		auxController = _auxStick;
		tempTimer = new Timer();
		
		driveSwitchButton = new ToggleButton(new JoystickButton(driveController, RobotMap.DRIVE_SWITCH_TOGGLE));
		speedDecrease = new ToggleButton(new JoystickButton(driveController, RobotMap.SPEED_DECREASE));
		autoRaiseElevator = new JoystickButton(auxController, RobotMap.RAISE_ELEVATOR_AUTO);
		autoLowerElevator = new JoystickButton(auxController, RobotMap.LOWER_ELEVATOR_AUTO);
		manualRaiseElevator = new JoystickButton(auxController, RobotMap.RAISE_ELEVATOR_MANUAL);//unsure atm how to key in triggers, just basis stuff
		manualLowerElevator = new JoystickButton(auxController, RobotMap.LOWER_ELEVATOR_MANUAL);//unsure atm how to key in triggers, just basis stuff
		
		//Put up acceleration input to dashboard
		SmartDashboard.putNumber("a->", 0.1);
		SmartDashboard.putNumber("a<-", 0.1);
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
	
	//GTA Forwards Drive
	private void gtaDriveForwards()
	{
		driveLeft(gtaForwardTrigger * speedLimit * -1);
		driveRight(gtaForwardTrigger * speedLimit * -1);
	}
	
	//GTA sdrawkcaB Drive
	private void gtaDriveBackwards()
	{
		driveLeft(gtaBackwardTrigger * speedLimit);
		driveRight(gtaBackwardTrigger * speedLimit);
	}
	
	//This is a function that must be implemented by the child class.
	abstract void updateControls();
	
	//Making function to be called during teleopInit().
	public void init()
	{
		zeroSpeed();
		previousVelocity = velocity; //Give previousVelocity a value for the first loop in periodic().
		tempTimer.start();
	}
	
	//Making function to be called during teleopPeriodic().
	public void periodic()
	{
		ToggleButton.updateToggleButtons();
		
		//double deltaT = changeTimer.get(); //deltaT is the change in time since this function was called.
		updateControls(); //This gets the values for joystick inputs from the child class.
		
		/*
		 * Priority of drive modes:
		 * 1) GTA Forward
		 * 2) GTA Backward
		 * 3) Arcade and Tank
		 */
		if (gtaForwardTrigger > 0) 
			gtaDriveForwards();
		else if (gtaBackwardTrigger > 0)
			gtaDriveBackwards();
		else if (driveSwitchButton.get())
			arcadeDrive();
		else
			tankDrive();
		if (speedDecrease.get())
    	{
    		vL *= SLOW_SPEED;
    		vR *= SLOW_SPEED;
    	}
	}
	
	double deadZone(double value)
	{
		if (Math.abs(value) < 0.01)
			return 0;
		return value;
	}
	
}
