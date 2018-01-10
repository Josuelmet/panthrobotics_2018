package main.src.org.usfirst.frc.team3337.robot.drive;

import org.usfirst.frc.team3337.robot.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public abstract class Drive {

	//Initializing Variables
	double speedL, speedR;
	
	public Drive(TalonSRX leftFront, TalonSRX leftMiddle, TalonSRX leftBack. TalonSRX rightFront, TalonSRX rightMiddle, TalonSRX rightBack, double robotLimit);
	
}
