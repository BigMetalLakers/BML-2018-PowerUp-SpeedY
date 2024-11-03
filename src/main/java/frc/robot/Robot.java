/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GripWheels;
import frc.robot.subsystems.Sensors;

import com.analog.adis16448.frc.ADIS16448_IMU;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static final ADIS16448_IMU imu = new ADIS16448_IMU();
	public static final Drivetrain drivetrain = new Drivetrain();
	public static final GripWheels intake = new GripWheels();
	public static final Sensors sensors = new Sensors();
	public static final OI m_oi = new OI();
	
	public double closeToSwitch = 10;
	public boolean dropped = false;
	public static int arenaWidth = 324;
	public static int turnNum = 1;
	public static final double kP = 0.005;
	public static int gear = 2;
	public static double frontValueToInches = .1249;
	public static double InchesPerS = 36.65;
	
	public static boolean teleop;
	public static double turningValue;
	public static int switchSide;
	public static char ourSide = 'R';
	public static int angle;
  Timer timer = new Timer();
  
 Solenoid Solenoidin;
 Solenoid Solenoidout;
 Joystick driverstick = new Joystick(0);
  double LeftStickValue;
  double RightStickValue;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    imu.reset();
		teleop = false;
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture("Robot Camera", 0);
		camera.setResolution(320, 240);
		camera.setFPS(30);
		UsbCamera camera2 = CameraServer.getInstance().startAutomaticCapture("Robot Camera 2", 1);
		camera2.setResolution(320, 240);
    camera2.setFPS(30);
    Solenoidin = new Solenoid(0,2);
    Solenoidout = new Solenoid(0,4);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();
    imu.reset();
		Timer.delay(.25);
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		if (gameData.length() > 0) {
			timer.start();
			if (gameData.charAt(0) == ourSide) {
				while (timer.get() < 3.8) {
					turningValue = Math.abs(0 - imu.getAngleZ()) * kP;
					drivetrain.ArcadeDrive(.64, turningValue);
				}
				while (Math.abs(imu.getAngleZ()) < 90) {
					drivetrain.ArcadeDrive(0, -.4);
				}
				while (Robot.sensors.ultrasonic_Front.getValue() <= closeToSwitch && dropped == false) {
					Robot.drivetrain.ArcadeDrive(.2, 0);
					if (Robot.imu.getAccelY() >= 1) {
						Robot.drivetrain.ArcadeDrive(0, 0);
						Robot.drivetrain.conveyorSpeed(.15);
						Timer.delay(2);
					} else if (timer.get() >= 15) {
						Robot.drivetrain.ArcadeDrive(0, 0);
						Robot.drivetrain.conveyorSpeed(.2);
						Timer.delay(2);
					} else {
						Robot.drivetrain.ArcadeDrive(.2, 0);
					}
				}
				//autonomousCommand.start();
			} else {
				while (timer.get() < 4.74){
					turningValue = Math.abs(0 - imu.getAngleZ()) * kP;
					drivetrain.ArcadeDrive(.64, turningValue);
				}
				//autonomousCommand.start();
			}
		}
  
    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    teleop = true;
		drivetrain.initDefaultCommand();
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    if (driverstick.getRawButton(4))
      {Solenoidin.set(true);
        Solenoidout.set(false);}
    if (driverstick.getRawButton(1))
      {Solenoidout.set(true);
        Solenoidin.set(false);}
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
