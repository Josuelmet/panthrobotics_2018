package main.src.org.usfirst.frc.team3337.drive;

//FIRST Library Packages
import org.usfirst.frc.team3337.robot.RobotMap;
//WPI Library packages
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class TeleopDrive
{

	Joystick stick1;
	double previousSpeed, previousTime, kAcceleration, kDeceleration, accelerationTrigger, brakeTrigger;	
	
	
	void init()
	{
		
	}
	
	public void periodic()
	{
		
		accelerationTrigger = stick1.getRawAxis(3);
		brakeTrigger = stick1.getRawAxis(2);
		
		if (accelerationTrigger > 0)
		{
			
		}
	}
	
}
