package frc.team339.hardware;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import java.util.ArrayList;

/** A group of DoubleSolenoids that can be controlled as one in parallel. */
public class DoubleSolenoidGroup implements Sendable, AutoCloseable {
    private ArrayList<DoubleSolenoid> doubleSolenoids = new ArrayList<DoubleSolenoid>();

    /**
     * Create a new DoubleSolenoidGroup with the given solenoids.
     *
     * @param doubleSolenoids
     * @throws IllegalArgumentException
     */
    public DoubleSolenoidGroup(final DoubleSolenoid... doubleSolenoids)
            throws IllegalArgumentException {
        if (doubleSolenoids.length < 1) {
            throw new IllegalArgumentException(
                    "doubleSolenoids must have at least one solenoid in it");
        }

        for (DoubleSolenoid doubleSolenoid : doubleSolenoids) {
            this.doubleSolenoids.add(doubleSolenoid);
        }
    }

    /** Get the solenoids in the group. */
    public ArrayList<DoubleSolenoid> getDoubleSolenoids() {
        return doubleSolenoids;
    }

    @Override
    public synchronized void close() {
        for (DoubleSolenoid doubleSolenoid : doubleSolenoids) {
            doubleSolenoid.close();
        }
    }

    /**
     * Set the value of the solenoids.
     *
     * @param value The value to set (Off, Forward, Reverse)
     */
    public void set(final DoubleSolenoid.Value value) {
        for (DoubleSolenoid doubleSolenoid : doubleSolenoids) {
            doubleSolenoid.set(value);
        }
    }

    /** Set the value of the solenoids to {@link DoubleSolenoid.Value#kForward forward}. */
    public void setForward() {
        set(DoubleSolenoid.Value.kForward);
    }

    /** Set the value of the solenoids to {@link DoubleSolenoid.Value#kReverse reverse}. */
    public void setReverse() {
        set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * Read the current value of the solenoids.
     *
     * @return The current value of the solenoids.
     */
    public DoubleSolenoid.Value get() {
        return doubleSolenoids.get(0).get();
    }

    /**
     * Toggle the value of the solenoids.
     *
     * <p>If the solenoids are set to forward, it'll be set to reverse. If the solenoids are set to
     * reverse, it'll be set to forward. If the solenoids are set to off, nothing happens.
     */
    public void toggle() {
        for (DoubleSolenoid doubleSolenoid : doubleSolenoids) {
            doubleSolenoid.toggle();
        }
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        for (DoubleSolenoid doubleSolenoid : doubleSolenoids) {
            doubleSolenoid.initSendable(builder);
        }
    }
}
