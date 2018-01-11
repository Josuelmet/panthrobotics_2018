package main.src.org.usfirst.frc.team3337.drive;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;

public class TeleopGameDrive extends TeleopDrive
{
	
	public TeleopGameDrive(TalonSRX _leftFront, TalonSRX _leftBack, TalonSRX _rightFront, TalonSRX _rightBack,
			TalonSRX _swerveWheel, Joystick _driveStick) {
		super(_leftFront, _leftBack, _rightFront, _rightBack, _swerveWheel, _driveStick);
		// TODO Auto-generated constructor stub
	}

	double accelerationTrigger, brakeTrigger;
	
	accelerationTrigger = stick1.getRawAxis(3);
	brakeTrigger = stick1.getRawAxis(2);
	
}
