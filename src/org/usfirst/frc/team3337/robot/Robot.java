//We're in the package below
package org.usfirst.frc.team3337.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

//We're using these other program files below for their functions.

//Cross the Road Electronics packages
import com.ctre.phoenix.motorcontrol.can.TalonSRX; //CANTalon class
import com.ctre.phoenix.sensors.PigeonIMU; //Pigeon gyro class
//import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.AnalogGyro;

//WPI Library packages
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//3337 packages
import main.src.org.usfirst.frc.team3337.drive.TeleopGameDrive;

//This is our class name. It is a child of IterativeRobot.
public class Robot extends IterativeRobot {
	
	//Declaring Variables
	//public static AHRS gyro; //Example code for the gyro is at C:\Users\Panthrobotics\navx-mxp\java\examples
	public static Joystick driveController, auxController;
	public static PigeonIMU gyro;
	public static TalonSRX leftFront, leftBack, rightFront, rightBack, elevatorMotor, rightArm, leftArm;
	public static Encoder leftEncoder, rightEncoder;
	TeleopGameDrive teleopDrive;
	
    //IterativeRobot has functions like the one below, hence the @Override.
	@Override
	public void robotInit()
	{
		//Initialize motors
		leftFront = new TalonSRX(RobotMap.LEFT_FRONT_TALON_SRX_CAN_DEVICE_ID);
		leftBack = new TalonSRX(RobotMap.lEFT_BACK_TALON_SRX_CAN_DEVICE_ID);
		leftBack.follow(leftFront); //leftBack will do what leftFront does.
		
		rightFront = new TalonSRX(RobotMap.RIGHT_FRONT_TALON_SRX_CAN_DEVICE_ID);
		rightBack = new TalonSRX(RobotMap.RIGHT_BACK_TALON_SRX_CAN_DEVICE_ID);
		rightBack.follow(rightFront); //rightBack will do what rightFront does.
		
		elevatorMotor = new TalonSRX(RobotMap.LIFT_MOTOR_1);
		rightArm = new TalonSRX(RobotMap.RIGHT_ARM);
		leftArm = new TalonSRX(RobotMap.LEFT_ARM);
		
		//Initializing joystick
		driveController = new Joystick(RobotMap.DRIVE_STICK_PORT);
		auxController = new Joystick(RobotMap.AUX_STICK_PORT);
		
		//Give pigeonGyro value.
		gyro = new PigeonIMU(rightFront); //the gyro is plugged into the rightFront motor controller.
		teleopDrive = new TeleopGameDrive(leftFront, leftBack, rightFront, rightBack, driveController, auxController);
		
		//Initializing NavX gyro. MAKE SURE IT'S ON!!
		//gyro = new AHRS(SPI.Port.kMXP); // It must be SPI or I2C instead of SerialPort because of communication issues.
		
		
		UsbCamera frontCamera = CameraServer.getInstance().startAutomaticCapture(0);
		UsbCamera backCamera = CameraServer.getInstance().startAutomaticCapture(1);

		//Intializing Encoders
		//leftEncoder = new Encoder(RobotMap.LEFT_ENCODER);
		//rightEncoder= new Encoder(RobotMap.RIGHT_ENCODER);
	}

	@Override
	public void autonomousInit()
	{
		
	}

	@Override
	public void autonomousPeriodic()
	{
		/*
		 * How to read from the Pigeon Gyro:
		 * double [] yawPitchRoll = new double[3];
		 * pigeonGyro.getYawPitchRoll(yawPitchRoll);
		 * double yaw = yawPitchRoll[0];
		 */
	}

	@Override
	public void teleopInit()
	{
		teleopDrive.init();
	}
	
	@Override
	public void teleopPeriodic()
	{
		teleopDrive.periodic();
		SmartDashboard.putNumber("Yaw", getYaw());
	}
	
	public static double getYaw()
	{
		double [] ypr = new double[3];
		gyro.getYawPitchRoll(ypr);
		return ypr[0];
	}

	@Override
	public void testPeriodic()
	{
		
	}
	
}
