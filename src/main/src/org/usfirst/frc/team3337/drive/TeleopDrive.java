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
	double previousVelocity, forwardAcceleration, reverseAcceleration, forwardTrigger, reverseTrigger;	
	
	//Constructor
	public TeleopDrive
		(TalonSRX _leftFront, TalonSRX _leftBack, TalonSRX _rightFront, TalonSRX _rightBack,
		 TalonSRX _swerveWheel, Joystick _driveStick)
	{
		//Calling Drive's constructor
		super(_leftFront, _leftBack, _rightFront, _rightBack, _swerveWheel);
		stick1 = _driveStick;
		changeTimer = new Timer();
		//TODO: put up acceleration input to dashboard
	}
	
	//Making function to be called during teleopInit().
	public void init()
	{
		zeroVelocities();
		previousVelocity = velocity;
		//TODO: get acceleration values from dashboard
		reverseAcceleration = Math.abs(reverseAcceleration); //This makes sure the value is positive.
	}
	
	//Making function to be called during teleopPeriodic().
	public void periodic()
	{
		double deltaT = changeTimer.get(); //deltaT is the change in time since this function was called.
		
		//TODO: THIS SHOUDL GO IN TELEOPGAMEDRIVE MASON
		forwardTrigger = deadZone(stick1.getRawAxis(3));
		reverseTrigger = deadZone(stick1.getRawAxis(3));
		
		if (forwardTrigger > 0 & reverseTrigger == 0) //acceleration
		{
			velocity = previousVelocity + forwardTrigger * forwardAcceleration * deltaT;
		}
		
		else if (reverseTrigger > 0 & forwardTrigger == 0) //deceleration
		{
			velocity = previousVelocity - reverseTrigger * reverseAcceleration * deltaT;
		}
		
		else //same velocity
		{
			velocity = previousVelocity;
		}
		
		
		//The end of periodic()
		updateVelocities(); //Set vL and vR equal to velocity.
		previousVelocity = velocity; //Record current velocity for next iteration of this loop.
		changeTimer.reset(); //Reset timer so that deltaT in next iteration of this loop is accurate.
	}
	
	private double deadZone(double value)
	{
		if (value < 0.01)
			return 0;
		return value;
	}
	
}
