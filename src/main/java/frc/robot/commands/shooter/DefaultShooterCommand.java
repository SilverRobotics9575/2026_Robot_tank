package frc.robot.commands.shooter;

import frc.robot.Constants;
import frc.robot.OperatorInput;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.ShooterSubsystem;

public class DefaultShooterCommand extends LoggingCommand {

    private final OperatorInput    operatorInput;
    private final ShooterSubsystem shooterSubsystem;

    public DefaultShooterCommand(OperatorInput operatorInput, ShooterSubsystem shooterSubsystem) {
        this.operatorInput    = operatorInput;
        this.shooterSubsystem = shooterSubsystem;

        this.addRequirements(shooterSubsystem);
    }

    public void init() {
        logCommandStart();
        shooterSubsystem.stop();
    }

    @Override
    public void execute() {

        // Set the shooter speed based on the left trigger
        boolean toggleMode = false; // False is variable shooter mode, true is toggle

        if (operatorInput.getShooterMode()) { // If the inital press of the button is detected, invert the state of toggle mode
                                              // (switch from Variable to Toggle or vice versa)
            toggleMode = !toggleMode; // Flip value
        }
        if (toggleMode) { // If the mode is the "toggle mode", where it goes to preset speeds, we check for the preset speeds.

            // TODO: SET RANGES
            if (operatorInput.getShortRangeShooterSpeed()) {
                shooterSubsystem.setShooterSpeed(Constants.ShooterConstants.LOW_RANGE_SHOOTER_SPEED);
            }
            else if (operatorInput.getMediumRangeShooterSpeed()) {
                shooterSubsystem.setShooterSpeed(Constants.ShooterConstants.MEDIUM_RANGE_SHOOTER_SPEED);
            }
            else if (operatorInput.getLongRangeShooterSpeed()) {
                shooterSubsystem.setShooterSpeed(Constants.ShooterConstants.LONG_RANGE_SHOOTER_SPEED);
            }
        }
        else { // If the mode is the "variable mode", we check the trigger inputs for the variable speed.
            if (operatorInput.getCBSpeedReverse()) {
                shooterSubsystem.setCBSpeed(-operatorInput.getCBSpeed());
            }
            else {
                shooterSubsystem.setCBSpeed(operatorInput.getCBSpeed());
            }
            shooterSubsystem.setShooterSpeed(operatorInput.getShooterSpeed());
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);

        shooterSubsystem.stop();
    }
}
