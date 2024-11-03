/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.General;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class Intake extends Command {
  public double wheelDirection;
  public Intake(double i) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.intake);
		wheelDirection = i;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.intake.intake_M.set(.3 * wheelDirection);

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    boolean Continue = OI.LB_button.get();
		if (Continue == true) {
			return false;
		} else {
			return true;
		}
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.intake.intake_M.set(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
