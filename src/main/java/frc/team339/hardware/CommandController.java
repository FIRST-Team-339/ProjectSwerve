package frc.team339.hardware;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class CommandController extends CommandXboxController {
    public CommandController(int port) {
        super(port);
    }

    /**
     * Set the rumble output for the HID. The DS currently supports 2 rumble values, left rumble and
     * right rumble.
     *
     * @param type Which rumble value to set
     * @param value The normalized value (0 to 1) to set the rumble to
     */
    public void setRumble(RumbleType type, double value) {
        this.getHID().setRumble(type, value);
    }
}
