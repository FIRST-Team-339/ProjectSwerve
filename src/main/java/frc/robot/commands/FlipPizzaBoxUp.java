package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.PizzaBoxSubsystem;

public class FlipPizzaBoxUp extends Command {
    private PizzaBoxSubsystem pizzaBoxSubsystem;

    public FlipPizzaBoxUp(final PizzaBoxSubsystem pizzaBoxSubsystem) {
        addRequirements(pizzaBoxSubsystem);
    }

    @Override
    public void initialize() {
        this.pizzaBoxSubsystem.flipUp();

        this.cancel();
    }
}
