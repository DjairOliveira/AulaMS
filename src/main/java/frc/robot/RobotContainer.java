// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {

  private CommandXboxController controller = new CommandXboxController(0);

  private Intake m_Intake = new Intake();

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {

    // controller.a().onTrue(Commands.runOnce(() -> m_Intake.setRPM(2000)));
    // controller.povDown().onTrue(Commands.runOnce(() -> m_Intake.setMaxMotion(7)));

    // controller.b().onTrue(Commands.runOnce(() -> m_Intake.setRPM(0)));
    // controller.povUp().onTrue(Commands.runOnce(() -> m_Intake.setMaxMotion(1)));

    controller.rightBumper().whileTrue(Commands.sequence(
      Commands.runOnce(() -> m_Intake.setMaxMotion(7)),
      Commands.waitUntil(() -> m_Intake.getPosition() > 4),
      Commands.runOnce(() -> m_Intake.setRPM(2000))));

    controller.rightBumper().whileFalse(Commands.sequence(
      Commands.runOnce(() -> m_Intake.setMaxMotion(1)),
      Commands.waitUntil(() -> m_Intake.getPosition() < 4),
      Commands.runOnce(() -> m_Intake.setRPM(0))));
      
  }


  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }

  public double getVelocityIntake(){
    return m_Intake.getVelocity();
  }

  public double getPositionIntake(){
    return m_Intake.getPosition();
  }
}
