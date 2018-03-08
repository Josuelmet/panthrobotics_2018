//The package this file is in.
package main.src.org.usfirst.frc.team3337.drive;

//Importing the classes below to use them.
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


//This class is AutoDrive and is a child of Drive.
public class AutoDrive extends Drive
{
	
	AutoChoice autoChoice;
	AutoInitialPosition autoInitialPosition;
	SendableChooser<AutoChoice> autoChooser;
	SendableChooser<AutoInitialPosition> positionChooser;
	
	boolean ourSwitchIsLeft, scaleIsLeft, otherSwitchIsLeft;
	
	//Constructor for AutoDrive.
	public AutoDrive()
	{
		super();
		
        autoChooser = new SendableChooser<AutoChoice>();
        autoChooser.addDefault(AutoChoice.GO_STRAIGHT.toString(), AutoChoice.GO_STRAIGHT);
        for (AutoChoice d: AutoChoice.possibleOptions())
            autoChooser.addObject(d.toString(), d);
        SmartDashboard.putData("Autonomous Mode autoChoice", autoChooser);	
        
        positionChooser = new SendableChooser<AutoInitialPosition>();
        positionChooser.addDefault(AutoInitialPosition.MIDDLE.toString(), AutoInitialPosition.MIDDLE);
        for (AutoInitialPosition d: AutoInitialPosition.possibleOptions())
            positionChooser.addObject(d.toString(), d);
        SmartDashboard.putData("Autonomous Position autoInitialPosition", positionChooser);
        
        forwardsStarted = false;
        backwardsStarted = false;
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
		
		ourSwitchIsLeft = gameData.charAt(0) == 'L';
		scaleIsLeft = gameData.charAt(1) == 'L';
		otherSwitchIsLeft = gameData.charAt(2) == 'L';
		
		//TODO: figure out why left encoder isn't working
		//TODO: figure out why left side won't follow
		//TODO: configure PID with driving
		switch (autoChoice)
		{
		case GO_STRAIGHT:
		default:
			break;
		case OUR_SWITCH:
			switch (autoInitialPosition)
			{
			case LEFT:
				break;
			case MIDDLE:
				break;
			case RIGHT:
				break;
			}
			break;
		case SCALE:
			switch (autoInitialPosition)
			{
			case LEFT:
				break;
			case MIDDLE:
				break;
			case RIGHT:
				break;
			}
			break;
		case OTHER_SWITCH:
			switch (autoInitialPosition)
			{
			case LEFT:
				break;
			case MIDDLE:
				break;
			case RIGHT:
				break;
			}
			break;
		}
		
	}
	
}
