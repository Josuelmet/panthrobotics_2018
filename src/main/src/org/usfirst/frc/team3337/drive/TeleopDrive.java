//Our package
package main.src.org.usfirst.frc.team3337.drive;

import org.usfirst.frc.team3337.robot.Robot;
//Our other classes
import org.usfirst.frc.team3337.robot.RobotMap;
import main.src.org.usfirst.frc.team3337.drive.ToggleButton;

import com.ctre.phoenix.motorcontrol.ControlMode;
//Cross the Road Electronics packages
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.buttons.Button;
//WPI Library packages
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Solenoid;

//This is the class TeleopDrive. It is a child of Drive. It is abstract.
public abstract class TeleopDrive extends Drive
{

	//Class variables
	Joystick driveController, auxController;
	ToggleButton driveSwitchButton, speedDecrease;
	//make a RobotMap value for bumpers for auto buttons and triggers for manual buttons
	Button autoRaiseElevator, autoLowerElevator, switchButton, manualRaiseElevator, manualLowerElevator;
	Timer tempTimer;
	Solenoid extendPistons, retractPistons, supportPiston;
	
	double previousVelocity, reverse, velocity, previousAngle;	
	double joyLY, joyLX, joyRY, joyRX, gtaForwardTrigger, gtaBackwardTrigger;
	double originalForwardsAngle, originalBackwardsAngle;
	boolean forwardsPressed, backwardsPressed;
	
	public static final double SLOW_SPEED = 0.5;
	
	//Constructor
	public TeleopDrive
		(TalonSRX _leftFront, TalonSRX _leftBack, TalonSRX _rightFront, TalonSRX _rightBack, Joystick _driveStick, Joystick _auxStick)
	{
		//Calling Drive's constructor
		super(_leftFront, _leftBack, _rightFront, _rightBack);
		driveController = _driveStick;
		auxController = _auxStick;
		tempTimer = new Timer();
		
		driveSwitchButton = new ToggleButton(new JoystickButton(driveController, RobotMap.DRIVE_SWITCH_TOGGLE));
		speedDecrease = new ToggleButton(new JoystickButton(driveController, RobotMap.SPEED_DECREASE));
		autoRaiseElevator = new JoystickButton(auxController, RobotMap.RAISE_ELEVATOR_AUTO);
		autoLowerElevator = new JoystickButton(auxController, RobotMap.LOWER_ELEVATOR_AUTO);
		manualRaiseElevator = new JoystickButton(auxController, RobotMap.RAISE_ELEVATOR_MANUAL);//unsure atm how to key in triggers, just basis stuff
		manualLowerElevator = new JoystickButton(auxController, RobotMap.LOWER_ELEVATOR_MANUAL);//unsure atm how to key in triggers, just basis stuff
		extendPistons = new Solenoid(RobotMap.EXTEND_PISTON);
		retractPistons = new Solenoid(RobotMap.RETRACT_PISTON);
		supportPiston = new Solenoid(RobotMap.SUPPORT_PISTON);
		
		//Put up acceleration input to dashboard
		SmartDashboard.putNumber("a->", 0.1);
		SmartDashboard.putNumber("a<-", 0.1);
		backwardsPressed = false;
		forwardsPressed = false;
	}
	
	//Arcade Drive
	private void arcadeDrive()
	{
		vL = joyLY + joyLX/2;
		vR = joyLY - joyLX/2;
		driveLeft(vL * speedLimit);
		driveRight(vR * speedLimit);
	}
	
	//Tank Drive
	private void tankDrive()
	{
		driveLeft(speedLimit * joyLY);
		driveRight(speedLimit * joyRY);
	}
	
	//GTA Forwards Drive
	private void driveBackwards()
	{
		if (!backwardsPressed) //if backwards GTA has not been pressed
		{
			originalBackwardsAngle = Robot.getRawYaw();
			System.out.println("angle = " + originalBackwardsAngle);
			backwardsPressed = true;
		}
        double scaledAngleDifference = (Robot.getRawYaw() - originalBackwardsAngle) * GYRO_COEFFICIENT;
		/* The right side overpowers the left. This fixes that.
		 * Since the right side is stronger than the left, the robot will turn left when going forwards.
		 * As such, the expected change in angle since the robot started will be negative.
		 * To decrease the power of the right side and increase that of the left,
		 * we will subtract the angle difference to the left side and add it to the right side.
		 */
		driveLeft((-gtaBackwardTrigger + scaledAngleDifference) *speedLimit);
		driveRight((-gtaBackwardTrigger - scaledAngleDifference) * speedLimit);
		/*driveLeft((gtaForwardTrigger * speedLimit) + scaledAngleDifference);
		driveRight((gtaForwardTrigger * speedLimit) - scaledAngleDifference);*/
	}
	
	//GTA sdrawkcaB Drive
	private void driveForwards()
	{
		if (!forwardsPressed) //if backwards GTA has not been pressed
		{
			originalForwardsAngle = Robot.getRawYaw();
			System.out.println("angle = " + originalForwardsAngle);
			forwardsPressed = true;
		}
        double gyroCoefficient = 0.01;
        double scaledAngleDifference = (Robot.getRawYaw() - originalForwardsAngle) * GYRO_COEFFICIENT;
		/* The right side overpowers the left. This fixes that.
		 * Since the right side is stronger than the left, the robot will turn left when going forwards.
		 * As such, the expected change in angle since the robot started will be positive.
		 * To decrease the power of the right side and increase that of the left,
		 * we will add the angle difference to the left side and subtract it from the right side.
		 */
		driveLeft((gtaForwardTrigger + scaledAngleDifference) *speedLimit);
		driveRight((gtaForwardTrigger - scaledAngleDifference) * speedLimit);
		/*driveLeft(gtaBackwardTrigger * speedLimit * -1);
		driveRight(gtaBackwardTrigger * speedLimit * -1);*/
	}
	
	//This is a function that must be implemented by the child class.
	abstract void updateControls();
	
	//Making function to be called during teleopInit().
	public void init()
	{
		//zeroSpeed();
		//previousVelocity = velocity; //Give previousVelocity a value for the first loop in periodic().
		//tempTimer.start();
	}
	
	//Making function to be called during teleopPeriodic().
	public void periodic()
	{
		ToggleButton.updateToggleButtons();
		updateControls(); //This gets the values for joystick inputs from the child class.
		
        //TODO: add turning (after autonomous)
        double scaledAngleDifference = (Robot.getRawYaw() - previousAngle) * GYRO_COEFFICIENT;
		/*
		 * Priority of drive modes:
		 * 1) GTA Forward
		 * 2) GTA Backward
		 * 3) Arcade and Tank
		 */
		if (gtaForwardTrigger > 0) //TODO: add turning (after autonomous)
		{
			driveForwards();
			SmartDashboard.putString("status", "gtaForward");
			backwardsPressed = false;
		}
		else if (gtaBackwardTrigger > 0) //TODO: add turning (after autonomous)
		{
			driveBackwards();
			SmartDashboard.putString("status", "gtaBackward");
			forwardsPressed = false;
		}
		else if (driveSwitchButton.get())
		{
			arcadeDrive();
			backwardsPressed = false;
			forwardsPressed = false;
		}
		else
		{
			tankDrive();
			backwardsPressed = false;
			forwardsPressed = false;
		}
		if (speedDecrease.get())
    	{
    		vL *= SLOW_SPEED;
    		vR *= SLOW_SPEED;
    	}
		
		previousAngle = Robot.getRawYaw();
	}
	
	double deadZone(double value)
	{
		if (Math.abs(value) < 0.01)
			return 0;
		return value;
	}
	
}
