//We're in the package below
package main.src.org.usfirst.frc.team3337.drive;

//We're using these other program files below for their functions.
import org.usfirst.frc.team3337.robot.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

//This is our class name. Abstract means that it cannot be instantiated.
public abstract class Drive
{

	//Declaring Variables
	double vL, vR, velocity;
	TalonSRX leftFront, leftBack, rightFront, rightBack, swerveWheel;
	
	//Constructor
	public Drive(TalonSRX _leftFront, TalonSRX _leftBack, TalonSRX _rightFront, TalonSRX _rightBack, TalonSRX _swerveWheel)
	{
		
		leftFront = _leftFront;
		leftBack = _leftBack;
		rightFront = _rightFront;
		rightBack = _rightBack;
		swerveWheel = _swerveWheel;
		zeroVelocities();
		
	}
	
	void zeroVelocities()
	{
		velocity = 0;
		updateVelocities();
	}
	
	void updateVelocities()
	{
		vL = velocity;
		vR = velocity;
		
		leftFront.set(ControlMode.PercentOutput, vL);
		leftBack.set(ControlMode.PercentOutput, vL);
		rightFront.set(ControlMode.PercentOutput, vR);
		rightBack.set(ControlMode.PercentOutput, vR);
	}
	
}