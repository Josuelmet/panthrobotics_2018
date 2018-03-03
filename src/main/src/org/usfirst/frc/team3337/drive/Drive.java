//We're in the package below
package main.src.org.usfirst.frc.team3337.drive;

import org.usfirst.frc.team3337.robot.Robot;
//We're using these other program files below for their functions.
//WPILib imports
import org.usfirst.frc.team3337.robot.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;

//CANTalon imports
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

//This is our class name. Abstract means that it cannot be instantiated.
public abstract class Drive
{

	//Declaring Variables
	double vL, vR, velocity;
	public static final double GYRO_COEFFICIENT = 0.01;
	
	//Constructor
	public Drive()
	{
		zeroVelocities();
		SmartDashboard.putNumber("Gyro Number", 0.0001);
	}
	
	double speedLimit = 0.75;
	//oneEncoder = new Encoder(int channelA, int channelB, boolean reverseDirection);
	//oneEncoder = new Encoder(int channelA, int channelB, boolean reverseDirection);
	
	void driveLeft(double leftInput) 
	{
		Robot.leftFront.set(ControlMode.PercentOutput, leftInput*speedLimit);
		Robot.leftBack.set(ControlMode.PercentOutput, leftInput*speedLimit);
	}
	
	//reference temp
	//driveLeft((-gtaBackwardTrigger + scaledAngleDifference) *speedLimit);
	
	void driveRight(double rightInput) 
	{
		//rightFront.set(ControlMode.PercentOutput, -rightInput*speedLimit);
		Robot.rightBack.set(ControlMode.PercentOutput, -rightInput*speedLimit);
	}
	
	void zeroVelocities()
	{
		driveLeft(0);
		driveRight(0);
	}
	
	void driveLift(double velocity)
	{
		Robot.elevatorMotorOne.set(ControlMode.PercentOutput, velocity);
		Robot.elevatorMotorTwo.set(ControlMode.PercentOutput, velocity);
	}
}