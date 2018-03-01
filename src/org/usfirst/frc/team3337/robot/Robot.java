//We're in the package below
package org.usfirst.frc.team3337.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

//We're using these other program files below for their functions.

//Cross the Road Electronics packages
import com.ctre.phoenix.motorcontrol.can.TalonSRX; //CANTalon class
import com.ctre.phoenix.sensors.PigeonIMU; //Pigeon gyro class

import edu.wpi.cscore.UsbCamera;
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
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//3337 packages
import main.src.org.usfirst.frc.team3337.drive.TeleopGameDrive;

//This is our class name. It is a child of IterativeRobot.
public class Robot extends IterativeRobot {
	
	//Declaring Variables
	//public static AHRS gyro; //Example code for the gyro is at C:\Users\Panthrobotics\navx-mxp\java\examples
	public static Joystick driveController, auxController;
	public static JoystickButton gyroButton, aButton, bButton, xButton, yButton;
	public static PigeonIMU gyro;
	public static TalonSRX leftFront, leftBack, rightFront, rightBack, elevatorMotorOne, elevatorMotorTwo;
	public static Spark rightArm, leftArm;
	public static Timer time;
	public static Encoder leftEncoder, rightEncoder;
	TeleopGameDrive teleopDrive;
	
	StringBuilder rbSB, lbSB, lfSB;
	
	int timeoutMs = 100;
	int slotIdx = 0;
	int pidIdx = 1;
	int loops = 0;
	
    //IterativeRobot has functions like the one below, hence the @Override.
	@Override
	public void robotInit()
	{
		//Initialize motors
		leftFront = new TalonSRX(RobotMap.LEFT_FRONT_TALON_SRX_CAN_DEVICE_ID);
		leftBack = new TalonSRX(RobotMap.lEFT_BACK_TALON_SRX_CAN_DEVICE_ID);
		//leftFront.follow(leftBack);
		//leftFront.set(ControlMode.Follower, leftBack.getDeviceID());
		
		rightFront = new TalonSRX(RobotMap.RIGHT_FRONT_TALON_SRX_CAN_DEVICE_ID);
		rightBack = new TalonSRX(RobotMap.RIGHT_BACK_TALON_SRX_CAN_DEVICE_ID);
		rightFront.follow(rightBack); //rightFront will do what rightBack does, since rightBack has an encoder.
		
		elevatorMotorOne = new TalonSRX(RobotMap.LIFT_MOTOR_1);
		elevatorMotorTwo = new TalonSRX(RobotMap.LIFT_MOTOR_2);
		
		rightArm = new Spark(RobotMap.RIGHT_ARM);
		leftArm = new Spark(RobotMap.LEFT_ARM);
		
		//Initializing joystick
		driveController = new Joystick(RobotMap.DRIVE_STICK_PORT);
		auxController = new Joystick(RobotMap.AUX_STICK_PORT);
		
		//Give pigeonGyro value.
		gyro = new PigeonIMU(rightFront); //the gyro is plugged into the rightFront motor controller.
		teleopDrive = new TeleopGameDrive(leftFront, leftBack, rightFront, rightBack, driveController, auxController);
		
		//Initializing NavX gyro. MAKE SURE IT'S ON!!
		//gyro = new AHRS(SPI.Port.kMXP); // It must be SPI or I2C instead of SerialPort because of communication issues.
		
		time = new Timer();
		time.reset();
		time.start();
		
		UsbCamera frontCamera = CameraServer.getInstance().startAutomaticCapture(0);
		UsbCamera backCamera = CameraServer.getInstance().startAutomaticCapture(1);
		
		gyroButton = new JoystickButton(driveController, 1);
		bButton = new JoystickButton(driveController, 2);
		xButton = new JoystickButton(driveController, 3);
		yButton = new JoystickButton(driveController, 4);

		rbSB = new StringBuilder();
		lbSB = new StringBuilder();
		lfSB = new StringBuilder();
		
		/* first choose the sensor */
		rightBack.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, pidIdx, timeoutMs); //TODO: MAYBE CHANGE LAST TWO VALUES?
		rightBack.setSelectedSensorPosition(0, pidIdx, timeoutMs);
		rightBack.setInverted(false); //TODO: CHANGE?
		/* set the peak and nominal outputs, 12V means full */
		rightBack.configNominalOutputForward(0, timeoutMs);
		rightBack.configNominalOutputReverse(0, timeoutMs);
		rightBack.configPeakOutputForward(1, timeoutMs);
		rightBack.configPeakOutputReverse(-1, timeoutMs);
		/* set closed loop gains in slot0 - see documentation */
		rightBack.selectProfileSlot(slotIdx, pidIdx);
		rightBack.config_kF(slotIdx, 0, timeoutMs);
		rightBack.config_kP(slotIdx, 0, timeoutMs);
		rightBack.config_kI(slotIdx, 0, timeoutMs);
		rightBack.config_kD(slotIdx, 0, timeoutMs);
		rightBack.configMotionCruiseVelocity(0, timeoutMs);
		rightBack.configMotionAcceleration(0, timeoutMs);
		
		/* first choose the sensor */
		leftBack.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, pidIdx, timeoutMs); //TODO: MAYBE CHANGE LAST TWO VALUES?
		leftBack.setSelectedSensorPosition(0, pidIdx, timeoutMs);
		leftBack.setInverted(false); //TODO: CHANGE?
		/* set the peak and nominal outputs, 12V means full */
		leftBack.configNominalOutputForward(0, timeoutMs);
		leftBack.configNominalOutputReverse(0, timeoutMs);
		leftBack.configPeakOutputForward(1, timeoutMs);
		leftBack.configPeakOutputReverse(-1, timeoutMs);
		/* set closed loop gains in slot0 - see documentation */
		leftBack.selectProfileSlot(0, 0);
		leftBack.config_kF(slotIdx, 0, timeoutMs);
		leftBack.config_kP(slotIdx, 0, timeoutMs);
		leftBack.config_kI(slotIdx, 0, timeoutMs);
		leftBack.config_kD(slotIdx, 0, timeoutMs);
		leftBack.configMotionCruiseVelocity(0, timeoutMs);
		leftBack.configMotionAcceleration(0, timeoutMs);
		
		/* first choose the sensor */
		/*leftFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, pidIdx, timeoutMs); //TODO: MAYBE CHANGE LAST TWO VALUES?
		leftFront.setInverted(false); //TODO: CHANGE?
		/* set the peak and nominal outputs, 12V means full */
		/*leftFront.configNominalOutputForward(0, timeoutMs);
		leftFront.configNominalOutputReverse(0, timeoutMs);
		leftFront.configPeakOutputForward(1, timeoutMs);
		leftFront.configPeakOutputReverse(-1, timeoutMs);
		/* set closed loop gains in slot0 - see documentation */
		/*leftFront.selectProfileSlot(0, 0);
		leftFront.config_kF(slotIdx, 0, timeoutMs);
		leftFront.config_kP(slotIdx, 0, timeoutMs);
		leftFront.config_kI(slotIdx, 0, timeoutMs);
		leftFront.config_kD(slotIdx, 0, timeoutMs);
		leftFront.configMotionCruiseVelocity(0, timeoutMs);
		leftFront.configMotionAcceleration(0, timeoutMs);*/
	}

	@Override
	public void autonomousInit()
	{
		
	}

	@Override
	public void autonomousPeriodic()
	{
		//leftFront.set(ControlMode.PercentOutput, -0.2);
		//leftFront.set(ControlMode.PercentOutput, -0.2);
		//rightFront.set(ControlMode.PercentOutput, -0.2);
		//System.out.println("Yaw" + getYaw() % 360);
		//Timer.delay(1);
		if (getYaw() < 100 && getYaw() > 80)
		{
			leftFront.set(ControlMode.PercentOutput, 0);
			leftBack.set(ControlMode.PercentOutput, 0);
			rightFront.set(ControlMode.PercentOutput, 0);
		}
		else
		{
			leftFront.set(ControlMode.PercentOutput, 0.15);
			leftBack.set(ControlMode.PercentOutput, 0.15);
			rightFront.set(ControlMode.PercentOutput, 0.15);
		}
	}

	@Override
	public void teleopInit()
	{
		teleopDrive.init();
	}
	
	@Override
	public void teleopPeriodic()
	{
		//teleopDrive.periodic();
		
		double leftYStick = -1.0 * driveController.getRawAxis(1);
		
		
		double rbMotorOutput = rightBack.getMotorOutputVoltage() / rightBack.getBusVoltage();
		rbSB.append("\tRB_out:");
		rbSB.append(rbMotorOutput);
		rbSB.append("\tRB_spd:");
		rbSB.append(rightBack.getSelectedSensorVelocity(pidIdx));

		double lbMotorOutput = leftBack.getMotorOutputVoltage() / leftBack.getBusVoltage();
		lbSB.append("\tLB_out:");
		lbSB.append(lbMotorOutput);
		lbSB.append("\tLB_spd:");
		lbSB.append(leftBack.getSelectedSensorVelocity(pidIdx));
		
		/*double lfMotorOutput = leftFront.getMotorOutputVoltage() / leftFront.getBusVoltage();
		lfSB.append("\tLF_out:");
		lfSB.append(lfMotorOutput);
		lfSB.append("\tLF_spd:");
		lfSB.append(leftFront.getSelectedSensorVelocity(pidIdx));*/
		
		if (driveController.getRawButton(1))
		{
			/* Motion Magic */
			double targetPos = leftYStick * 10.0; /* 10 Rotations in either direction */
			
			rightBack.set(ControlMode.MotionMagic, targetPos);
			/* append more signals to print when in speed mode. */
			rbSB.append("\tRB_err:");
			rbSB.append(rightBack.getClosedLoopError(pidIdx));
			rbSB.append("\tRB_trg:");
			rbSB.append(targetPos);

			leftBack.set(ControlMode.MotionMagic, targetPos);
			/* append more signals to print when in speed mode. */
			lbSB.append("\tLB_err:");
			lbSB.append(leftBack.getClosedLoopError(pidIdx));
			lbSB.append("\tLB_trg:");
			lbSB.append(targetPos);
			
			/*leftFront.set(ControlMode.MotionMagic, targetPos);
			/* append more signals to print when in speed mode. */
			/*lfSB.append("\tLF_err:");
			lfSB.append(leftFront.getClosedLoopError(pidIdx));
			lfSB.append("\tLF_trg:");
			lfSB.append(targetPos);*/
		}
		else
		{
			/* Percent voltage/output mode (normal mode) */
			rightBack.set(ControlMode.PercentOutput, leftYStick);
			leftBack.set(ControlMode.PercentOutput, leftYStick);
			//leftFront.set(ControlMode.PercentOutput, leftYStick);
		}
		
		/* PROCESSING DATA */
		/* smart dash plots */
	    SmartDashboard.putNumber("RB_RPM", rightBack.getSelectedSensorVelocity(pidIdx));
	    SmartDashboard.putNumber("RB_Pos",  rightBack.getSelectedSensorPosition(pidIdx));
	    SmartDashboard.putNumber("RB_AppliedThrottle",  (rightBack.getMotorOutputVoltage()/rightBack.getBusVoltage())*1023);
	    SmartDashboard.putNumber("RB_ClosedLoopError", rightBack.getClosedLoopError(pidIdx));
	    
	    SmartDashboard.putNumber("LB_RPM", leftBack.getSelectedSensorVelocity(pidIdx));
	    SmartDashboard.putNumber("LB_Pos",  leftBack.getSelectedSensorPosition(pidIdx));
	    SmartDashboard.putNumber("LB_AppliedThrottle",  (leftBack.getMotorOutputVoltage()/leftBack.getBusVoltage())*1023);
	    SmartDashboard.putNumber("LB_ClosedLoopError", leftBack.getClosedLoopError(pidIdx));
	    
	    /*SmartDashboard.putNumber("LF_RPM", leftFront.getSelectedSensorVelocity(pidIdx));
	    SmartDashboard.putNumber("LF_Pos",  leftFront.getSelectedSensorPosition(pidIdx));
	    SmartDashboard.putNumber("LF_AppliedThrottle",  (leftFront.getMotorOutputVoltage()/leftFront.getBusVoltage())*1023);
	    SmartDashboard.putNumber("LF_ClosedLoopError", leftFront.getClosedLoopError(pidIdx));*/
	    
	    /*if (rightBack.getControlMode() == ControlMode.MotionMagic) {
			//These API calls will be added in our next release.
	    	//SmartDashboard.putNumber("ActTrajVelocity", tal.getMotionMagicActTrajVelocity());
	    	//SmartDashboard.putNumber("ActTrajPosition", tal.getMotionMagicActTrajPosition());
	    }*/
	    
	    
	    /* periodically print to console */
	    if(++loops >= 10)
	    {
	        loops = 0;
	        System.out.println(rbSB.toString());
	        System.out.println(lbSB.toString());
	        //System.out.println(lfSB.toString());
	    }
	    /* clear line cache */
	    rbSB.setLength(0);
	    lbSB.setLength(0);
	    //lfSB.setLength(0);
	        
	        
		SmartDashboard.putNumber("Yaw", getYaw());
		//if (gyroButton.get())
			//System.out.println("Yaw:::::" + getYaw());
		
		if (bButton.get())
		{
			rightArm.set(1);
			leftArm.set(1);
		}
		else if (xButton.get())
		{
			rightArm.set(-1);
			leftArm.set(-1);
		}
		else
		{
			rightArm.set(0);
			leftArm.set(0);
		}
		
		
	}
	
	public static double getYaw()
	{
		/*
		 * The yaw returned by the gyro goes negative when turning clockwise (right),
		 * positive when turning counterclockwise (left).
		 * However, the yaw returned does not return a value from -180 to +180.
		 * Rather, the yaw returned continues to build past 180 and -180.
		 * For example, if the robot turns 2 perfect revolutions,
		 * the gyro does not return 0, but rather returns -720.
		 * Not terribly useful.
		 * As such, this function processes the yaw input to return a value
		 * from -180 to +180, with negative being clockwise, and positive being counterclockwise.
		 */
		double rawYaw = getRawYaw();
		rawYaw %= 360;
		if (Math.abs(rawYaw) <= 180)
			return rawYaw;
		else
		{
			if (rawYaw < 0)
				return 360 + rawYaw;
			else
				return rawYaw - 360;
		}
		
		
			
	}
	
	public static double getRawYaw()
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
