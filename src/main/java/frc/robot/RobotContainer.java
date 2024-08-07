// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants.*;
import frc.robot.commands.FlipPizzaBoxUp;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.PizzaBoxSubsystem;
import frc.team339.hardware.CommandController;

public class RobotContainer {
    private double MaxSpeed =
            TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
    private double MaxAngularRate =
            1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final CommandController driverController =
            new CommandController(ControllerConstants.kDriverControllerId); // My joystick
    private final CommandController operatorController =
            new CommandController(ControllerConstants.kOperatorControllerId); // My joystick
    private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain

    private final SwerveRequest.FieldCentric drive =
            new SwerveRequest.FieldCentric()
                    .withDeadband(MaxSpeed * 0.1)
                    .withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
                    .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
    // driving in open loop
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    /* Pizza Box Subsystem */
    private final PizzaBoxSubsystem pizzaBoxSubsystem = new PizzaBoxSubsystem();

    private void configureBindings() {
        drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
                drivetrain.applyRequest(
                        () ->
                                drive.withVelocityX(
                                                -driverController.getLeftY()
                                                        * MaxSpeed) // Drive forward with
                                        // negative Y (forward)
                                        .withVelocityY(
                                                -driverController.getLeftX()
                                                        * MaxSpeed) // Drive left with negative X
                                        // (left)
                                        .withRotationalRate(
                                                -driverController.getRightX()
                                                        * MaxAngularRate) // Drive counterclockwise
                        // with negative
                        // X (left)
                        ));

        driverController
                .leftBumper()
                .or(driverController.rightTrigger())
                .whileTrue(drivetrain.applyRequest(() -> brake));
        driverController
                .b()
                .whileTrue(
                        drivetrain.applyRequest(
                                () ->
                                        point.withModuleDirection(
                                                new Rotation2d(
                                                        -driverController.getLeftY(),
                                                        -driverController.getLeftX()))));

        // reset the field-centric heading on left bumper press
        driverController
                .leftBumper()
                .onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

        if (Utils.isSimulation()) {
            drivetrain.seedFieldRelative(
                    new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
        }
        drivetrain.registerTelemetry(logger::telemeterize);
    }

    private void configureNamedCommands() {
        NamedCommands.registerCommand(
                "Flip Pizza Box Up", new FlipPizzaBoxUp(this.pizzaBoxSubsystem));
        NamedCommands.registerCommand(
                "Flip Pizza Box Down", new FlipPizzaBoxUp(this.pizzaBoxSubsystem));
    }

    public RobotContainer() {
        configureNamedCommands();

        configureBindings();
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }

    public void updateOdometry() {
        this.drivetrain.updateOdometry();
    }
}
