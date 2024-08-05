package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;
import frc.team339.hardware.DoubleSolenoidGroup;

public class PizzaBoxSubsystem extends SubsystemBase {
    private DoubleSolenoidGroup doubleSolenoidGroup;

    public PizzaBoxSubsystem() {
        if (!PizzaBoxConstants.kFeatureEnabled) return;
        this.doubleSolenoidGroup =
                new DoubleSolenoidGroup(
                        new DoubleSolenoid(
                                PneumaticsModuleType.CTREPCM,
                                PizzaBoxConstants.kLeftPistonForwardPort,
                                PizzaBoxConstants.kLeftPistonReversePort),
                        new DoubleSolenoid(
                                PneumaticsModuleType.CTREPCM,
                                PizzaBoxConstants.kRightPistonForwardPort,
                                PizzaBoxConstants.kRightPistonReversePort));

        if (PizzaBoxConstants.kUpByDefault) {
            this.flipUp();
        } else {
            this.flipDown();
        }
    }

    /** Flip the pizza box up */
    public void flipUp() {
        if (!PizzaBoxConstants.kFeatureEnabled) return;
        this.doubleSolenoidGroup.setReverse();
    }

    /** Flip the pizza box down */
    public void flipDown() {
        if (!PizzaBoxConstants.kFeatureEnabled) return;
        this.doubleSolenoidGroup.setForward();
    }
}
