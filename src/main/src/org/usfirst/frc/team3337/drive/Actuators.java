package main.src.org.usfirst.frc.team3337.drive;

import org.usfirst.frc.team3337.robot.Robot;
import org.usfirst.frc.team3337.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.Solenoid;



public class Actuators
{
	
	JoystickButton aButton, bButton, xButton, yButton, leftBumper, rightBumper;
	//Solenoid extendPistons, retractPistons, supportPiston;
	
	public static final double ROTATIONS_TO_REACH_SWITCH = 7000;
	
	public Actuators()
	{
		aButton = new JoystickButton(Robot.auxController, 1);
		bButton = new JoystickButton(Robot.auxController, 2);
		xButton = new JoystickButton(Robot.auxController, 3);
		yButton = new JoystickButton(Robot.auxController, 4);
		leftBumper = new JoystickButton(Robot.auxController, 5);
		rightBumper = new JoystickButton(Robot.auxController, 6);
		
		//Conversion Stuff
		
		double inchConversion = SmartDashboard.getNumber("Inches Measured", 0);
		 
	}
	
	public void setIntake(double velocity)
	{
		Robot.rightArm.set(velocity);
		Robot.leftArm.set(velocity);
	}
	
	public void driveLift(double velocity)
	{
		Robot.elevatorMotorOne.set(ControlMode.PercentOutput, velocity);
		Robot.elevatorMotorTwo.set(ControlMode.PercentOutput, velocity);
	}
	
	public void periodic()
	{
		//We multiply by -1 because the encoder gives negative values as the elevator goes up.
		int elevatorPosition = -Robot.elevatorMotorOne.getSensorCollection().getQuadraturePosition();
		
		if (bButton.get())
			setIntake(1);
		else if (xButton.get())
			setIntake(-1);
		else
			setIntake(0);
		
		Robot.intakeAngleMotor.set(Math.max(0, -Robot.auxController.getRawAxis(1)));
		
		if (rightBumper.get())
		{
			if (elevatorPosition < ROTATIONS_TO_REACH_SWITCH)
				driveLift(0.7);
			else
				driveLift(0);
		}
		else if (leftBumper.get())
		{
			if (elevatorPosition > ROTATIONS_TO_REACH_SWITCH - 3000)
				driveLift(-0.6);
			else
				driveLift(0);
		}
		else if (Robot.auxController.getRawAxis(RobotMap.ELEVATOR_UP) > 0)
			driveLift(Robot.auxController.getRawAxis(RobotMap.ELEVATOR_UP));
		else 
			driveLift(-Robot.auxController.getRawAxis(RobotMap.ELEVATOR_DOWN));
		
		//extendPistons = new Solenoid(RobotMap.EXTEND_PISTON);
		//retractPistons = new Solenoid(RobotMap.RETRACT_PISTON);
		//supportPiston = new Solenoid(RobotMap.SUPPORT_PISTON);
	}
}
