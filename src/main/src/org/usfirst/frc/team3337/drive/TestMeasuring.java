package main.src.org.usfirst.frc.team3337.drive;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.Timer;

import org.usfirst.frc.team3337.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestMeasuring extends Robot
{
	Timer resetTimer;
	TestMeasuring testMeasuring;
	
	
	public TestMeasuring()
	{
		super();
	}
	
	public void driveLift(double velocity)
	{
		Robot.elevatorMotorOne.set(ControlMode.PercentOutput, velocity);
		Robot.elevatorMotorTwo.set(ControlMode.PercentOutput, velocity);
	}
	
	public void periodic()
	{
		int elevatorPosition = -Robot.elevatorMotorOne.getSensorCollection().getQuadraturePosition();
		SmartDashboard.putNumber("Test Rotations", elevatorPosition);		
		resetTimer = new Timer();
		
		resetTimer.start();
		
		SmartDashboard.putNumber("Reset Timer", resetTimer.get());
		if (resetTimer.get() < .5)
		{
			//elevatorPosition = 0;
		}
		else if (resetTimer.get() > .5)
		{
			
		}
		
		//Make motors run until encoders read a certain number, and then output that number into both SmartDashboard and for other areas of the code, specifically AutoDrive.
		if (elevatorPosition <= -20)
		{
			driveLift(.1);
		}
		else
		{
			driveLift(0);
		}
	}
}