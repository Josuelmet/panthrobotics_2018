package main.src.org.usfirst.frc.team3337.drive;

//FIRST Library Packages
import org.usfirst.frc.team3337.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

//WPI Library packages
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//This is the
public abstract class TeleopDrive extends Drive
{

	Joystick stick1;
	double previousSpeed, previousTime, kAcceleration, kDeceleration, accelerationTrigger, brakeTrigger;	
	
	public TeleopDrive
		(TalonSRX _leftFront, TalonSRX _leftBack, TalonSRX _rightFront, TalonSRX _rightBack,
		 TalonSRX _swerveWheel, Joystick _driveStick)
	{
		super(_leftFront, _leftBack, _rightFront, _rightBack, _swerveWheel);
		stick1 = _driveStick;
	}
	
	void init()
	{
		zeroVelocities();
	}
	
	public void periodic()
	{
		updateVelocities();
		
		accelerationTrigger = stick1.getRawAxis(3);
		brakeTrigger = stick1.getRawAxis(3);
		
		if (accelerationTrigger > 0)
		{
			
		}
	}
	
}
