//Our package
package main.src.org.usfirst.frc.team3337.drive;

//Our other classes
import org.usfirst.frc.team3337.robot.RobotMap;

//Cross the Road Electronics packages
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

//WPI Library packages
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//This is the
public abstract class TeleopDrive extends Drive
{

	//Class variables
	Joystick stick1;
	double previousVelocity, previousTime, fowardAcceleration, reverseAcceleration, forwardTrigger, reverseTrigger;	
	
	//Constructor
	public TeleopDrive
		(TalonSRX _leftFront, TalonSRX _leftBack, TalonSRX _rightFront, TalonSRX _rightBack,
		 TalonSRX _swerveWheel, Joystick _driveStick)
	{
		//Calling Drive's constructor
		super(_leftFront, _leftBack, _rightFront, _rightBack, _swerveWheel);
		stick1 = _driveStick;
	}
	
	//Making function to be called during teleopInit().
	public void init()
	{
		zeroVelocities();
	}
	
	//Making function to be called during teleopPeriodic().
	public void periodic()
	{
		
		//TODO: deadzone these values MASON
		forwardTrigger = stick1.getRawAxis(3);
		reverseTrigger = stick1.getRawAxis(3);
		
		if (forwardTrigger > 0 & reverseTrigger == 0)
		{
			
		}
		
		
		//The end of periodic()
		updateVelocities();
	}
	
}
