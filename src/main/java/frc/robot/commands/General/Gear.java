/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.General;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Gear extends Command {
  int gearChange;
  public Gear(int i) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    gearChange = i;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if (gearChange == 1 && Robot.gear <= 3) {
      Robot.gear += 1;
    }else if (gearChange == 0 && Robot.gear >= 1){
      Robot.gear -= 1;
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
