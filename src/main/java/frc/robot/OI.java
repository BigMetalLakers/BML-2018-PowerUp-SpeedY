/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.General.Gear;
import frc.robot.commands.General.HoldCube;
import frc.robot.commands.General.Intake;
import frc.robot.commands.General.Turn;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  public static XboxController controller = new XboxController(0);
  public static Button A_button = new JoystickButton(controller, 1),
	  B_button = new JoystickButton(controller, 2),
		X_button = new JoystickButton(controller, 3),
		Y_button = new JoystickButton(controller, 4),
    LB_button = new JoystickButton(controller, 5),
    RB_button = new JoystickButton(controller, 6),
    start_button = new JoystickButton(controller, 7),
    back_button = new JoystickButton(controller, 8),
    LS_button = new JoystickButton(controller, 9),
    RS_button = new JoystickButton(controller, 10);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
  public OI() {
		start_button.whenPressed(new Gear(1));
		back_button.whenPressed(new Gear(0));
		LB_button.whileHeld(new Intake(-1));
		RB_button.whileHeld(new Intake(1));
		Y_button.toggleWhenPressed(new HoldCube());
		B_button.toggleWhenPressed(new Turn(1));
		X_button.toggleWhenPressed(new Turn(-1));
	}
}
