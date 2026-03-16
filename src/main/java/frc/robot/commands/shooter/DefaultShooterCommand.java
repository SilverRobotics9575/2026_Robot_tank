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

        if (operatorInput.getShooterMode()) { // If the mode is the "toggle mode", where it goes to preset speeds, we check for
            // the preset speeds.

            // TODO: SET RANGES
            if (operatorInput.getShortRangeShooterSpeed()) {
                System.out.println("Short Range");
                shooterSubsystem.setShooterSpeed(Constants.ShooterConstants.LOW_RANGE_SHOOTER_SPEED);
            }
            else if (operatorInput.getMediumRangeShooterSpeed()) {
                System.out.println("Medium Range");
                shooterSubsystem.setShooterSpeed(Constants.ShooterConstants.MEDIUM_RANGE_SHOOTER_SPEED);
            }
            else if (operatorInput.getLongRangeShooterSpeed()) {
                System.out.println("Long Range");
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
