//The package this file is in.
package main.src.org.usfirst.frc.team3337.drive;

//Importing the classes below to use them.
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;


//This class is AutoDrive and is a child of Drive.
public class AutoDrive extends Drive
{
	//Constructor for AutoDrive.
	public AutoDrive(TalonSRX _leftFront, TalonSRX _leftBack, TalonSRX _rightFront, TalonSRX _rightBack, TalonSRX _swerveWheel)
	{
		super(_leftFront, _leftBack, _rightFront, _rightBack, _swerveWheel);
	}
	
	//Method to be run in autonomousInit() in Robot.java
	public void init()
	{
		
	}
	
	//Method to be run in autonomousPeriodic() in Robot.java
	public void periodic()
	{
		
		String gameData;
		
		/*The method below returns a string of three characters decided by the competition management system.
		 *The three characters represent where our alliance's switch/scale ports are.
		 *The characters are organized by distance away from alliance players:
		 *	First character = our switch
		 *	Second character = the scale
		 *	Third character = other alliance's switch
		 *If a character is 'L', the component referenced is to the left; if 'R', to the right.
		 */
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if(gameData.charAt(0) == 'L')
		{
			System.out.println("---Our switch is on the left---");
		} else
		{
			System.out.println("---Our switch is on the right---");
		}
		
		if (gameData.charAt(1) == 'L')
		{
			System.out.println("---Our scale is on the left---");
		} else
		{
			System.out.println("---Our scale is on the right---");
		}
		
		if (gameData.charAt(2) == 'L')
		{
			System.out.println("---Our side of the other switch is on the left---");
		} else
		{
			System.out.println("---Our side of the other switch is on the right---");
		}
		
	}
	
}
