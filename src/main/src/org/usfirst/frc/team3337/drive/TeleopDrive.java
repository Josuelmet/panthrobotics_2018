//Our package
package main.src.org.usfirst.frc.team3337.drive;

//Our other classes
import org.usfirst.frc.team3337.robot.RobotMap;

//Cross the Road Electronics packages
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

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
	Joystick stick1;
	Timer changeTimer;
	double previousVelocity, forwardAcceleration, reverseAcceleration, reverse;	
	double forwardTrigger, reverseTrigger, joyLY, joyLX, joyRY, joyRX;
	DriveMode driveMode;
	
	//Constructor
	public TeleopDrive
		(TalonSRX _leftFront, TalonSRX _leftBack, TalonSRX _rightFront, TalonSRX _rightBack,
		 TalonSRX _swerveWheel, Joystick _driveStick)
	{
		//Calling Drive's constructor
		super(_leftFront, _leftBack, _rightFront, _rightBack, _swerveWheel);
		stick1 = _driveStick;
		changeTimer = new Timer();
		
		//Put up acceleration input to dashboard
		SmartDashboard.putNumber("a->", 0.1);
		SmartDashboard.putNumber("a<-", 0.1);
	}
	
	//Arcade Drive
	//TODO: fix variables to be current (joyLX and joyLY)
	private void arcadeDrive()
	{
		vL = joyLY - joyLX/2;
		vR = joyLY + joyLX/2;
	}
	
	//Tank Drive
	private void tankDrive()
	{
		vL = joyLY;
		vR = joyRY;
	}
	
	//This is a function that must be implemented by the child class.
	abstract void updateControls();
	
	//Making function to be called during teleopInit().
	public void init()
	{
		zeroVelocities();
		previousVelocity = velocity; //Give previousVelocity a value for the first loop in periodic().
		
		//Get values from the SmartDashboard.
		forwardAcceleration = SmartDashboard.getNumber("a->", 0.1);
		reverseAcceleration = Math.abs(SmartDashboard.getNumber("a<-", 0.1)); //Make sure value is positive.
	}
	
	//Making function to be called during teleopPeriodic().
	public void periodic()
	{
		double deltaT = changeTimer.get(); //deltaT is the change in time since this function was called.
		updateControls(); //This gets the values for joystick inputs from the child class.
		
		//The end of periodic()
		updateVelocities(); //Set vL and vR equal to velocity.
		previousVelocity = velocity; //Record current velocity for next iteration of this loop.
		changeTimer.reset(); //Reset timer so that deltaT in next iteration of this loop is accurate.
	}
	
	double deadZone(double value)
	{
		if (value < 0.01)
			return 0;
		return value;
	}
	
}
