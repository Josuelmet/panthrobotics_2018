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
	Joystick driveController;
	ToggleButton driveSwitchButton, speedDecrease;
	Timer changeTimer;
	double previousVelocity, reverse;	
	double joyLY, joyLX, joyRY, joyRX;
	
	public static final double SLOW_SPEED = 0.5;
	
	//Constructor
	public TeleopDrive
		(TalonSRX _leftFront, TalonSRX _leftBack, TalonSRX _rightFront, TalonSRX _rightBack,
		 TalonSRX _swerveWheel, Joystick _driveStick)
	{
		//Calling Drive's constructor
		super(_leftFront, _leftBack, _rightFront, _rightBack);
		driveController = _driveStick;
		changeTimer = new Timer();
		driveSwitchButton = new ToggleButton(new JoystickButton(driveController, RobotMap.DRIVE_SWITCH_TOGGLE));
		speedDecrease = new ToggleButton(new JoystickButton(driveController, RobotMap.SPEED_DECREASE));
		
		//Put up acceleration input to dashboard
		SmartDashboard.putNumber("a->", 0.1);
		SmartDashboard.putNumber("a<-", 0.1);
	}
	
	//Arcade Drive
	private void arcadeDrive()
	{
		vL = joyLY - joyLX/2;
		vR = joyLY + joyLX/2;
	}
	
	//Tank Drive
	private void tankDrive()
	{
		driveLeft(driveController.getRawAxis(1)*speedLimit*-1);
		driveRight(driveController.getRawAxis(5)*speedLimit*-1);
	}
	
	private void checkDrive()
	{
		if (driveSwitchButton.get())
			arcadeDrive();
		else
			tankDrive();
	}
	
	private void decreaseSpeed()
	{
		if (speedDecrease.get())
		{
			vL = vL/2;
			vR = vR/2;
		}
		else
		{
			checkDrive();
		}	
	}
	
	//This is a function that must be implemented by the child class.
	abstract void updateControls();
	
	//Making function to be called during teleopInit().
	public void init()
	{
		zeroSpeed();
		previousVelocity = velocity; //Give previousVelocity a value for the first loop in periodic().
		
	}
	
	//Making function to be called during teleopPeriodic().
	public void periodic()
	{
		double deltaT = changeTimer.get(); //deltaT is the change in time since this function was called.
		updateControls(); //This gets the values for joystick inputs from the child class.
		
		if (speedDecrease.get())
    	{
    		vL *= SLOW_SPEED;
    		vR *= SLOW_SPEED;
    	}
		
		decreaseSpeed();
		
		//The end of periodic()
		//updateVelocities(); //Set vL and vR equal to velocity.
		//previousVelocity = velocity; //Record current velocity for next iteration of this loop.
		changeTimer.reset(); //Reset timer so that deltaT in next iteration of this loop is accurate.
		
		//System.out.println("HELP");
	}
	
	double deadZone(double value)
	{
		if (value < 0.01)
			return 0;
		return value;
	}
	
}
