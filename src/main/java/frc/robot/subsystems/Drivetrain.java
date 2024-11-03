/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.General.DriveWithController;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Drivetrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public VictorSP conveyor_M = new VictorSP(RobotMap.VictorSP_Conveyor);
	public Spark Left_M = new Spark(RobotMap.Spark_Left);
	public Spark Right_M = new Spark(RobotMap.Spark_Right);
	public DifferentialDrive drive = new DifferentialDrive(Left_M, Right_M);
	
	public Drivetrain() {
		drive.setSafetyEnabled(false);
	}

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    if (Robot.teleop == true) {
      setDefaultCommand(new DriveWithController());
    }
  }
  public void ArcadeDrive(double x,double z) {
		drive.arcadeDrive(x, z);
    }

	public void conveyorSpeed(double d) {
		if (d < -.1) {
			d = -.3;
		}
		if (d > .1) {
			d = .2;
		}
		conveyor_M.set(d);
	}
}
