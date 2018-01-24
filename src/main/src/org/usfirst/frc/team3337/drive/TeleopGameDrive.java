package main.src.org.usfirst.frc.team3337.drive;

import org.usfirst.frc.team3337.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;

public class TeleopGameDrive extends TeleopDrive
{
	
	public TeleopGameDrive(TalonSRX _leftFront, TalonSRX _leftBack, TalonSRX _rightFront, TalonSRX _rightBack, TalonSRX _swerveWheel, Joystick _driveStick)
	{
		super(_leftFront, _leftBack, _rightFront, _rightBack, _swerveWheel, _driveStick);
	}
	
	void updateControls()
	{
		
		joyLY = -deadZone(driveController.getRawAxis(1));
		joyRY = -deadZone(driveController.getRawAxis(5));
		joyLX = deadZone(driveController.getRawAxis(0));
		joyRX = deadZone(driveController.getRawAxis(4));
	}
		
	
}
