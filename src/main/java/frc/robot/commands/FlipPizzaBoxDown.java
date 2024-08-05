package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.PizzaBoxSubsystem;

public class FlipPizzaBoxDown extends Command {
    private PizzaBoxSubsystem pizzaBoxSubsystem;

    public FlipPizzaBoxDown(final PizzaBoxSubsystem pizzaBoxSubsystem) {
        addRequirements(pizzaBoxSubsystem);
    }

    @Override
    public void initialize() {
        this.pizzaBoxSubsystem.flipUp();

        this.cancel();
    }
}
