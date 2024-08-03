package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

/** A command that sleeps for a certain amount of time. */
public class Sleep extends Command {
    private Timer timer = new Timer();
    private double seconds;

    /**
     * @param seconds Amount of time to sleep for in seconds.
     */
    public Sleep(final double seconds) {
        this.seconds = seconds;
    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public void execute() {
        if (timer.hasElapsed(this.seconds)) this.end(false);
    }
}
