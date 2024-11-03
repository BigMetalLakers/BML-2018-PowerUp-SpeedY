/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Sensors extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public AnalogInput ultrasonic_Front = new AnalogInput(RobotMap.Ultrasonic_Front);
	public Encoder encL = new Encoder(RobotMap.Encoder_Right, RobotMap.Encoder_Right2, false, Encoder.EncodingType.k4X);
	public Encoder encR = new Encoder(RobotMap.Encoder_Left, RobotMap.Encoder_Left2, false, Encoder.EncodingType.k4X);
	public Sensors() {
		encL.setMaxPeriod(.1);
		encL.setMinRate(2);
		encL.setDistancePerPulse(0.05235987755);
		encL.setSamplesToAverage(5);
		encR.setMaxPeriod(.1);
		encR.setMinRate(2);
		encR.setDistancePerPulse(0.05235987755);
		encR.setSamplesToAverage(5);
	}

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
