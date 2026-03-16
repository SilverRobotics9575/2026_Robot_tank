package frc.robot.commands.shooter;

import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterAutoCommand extends LoggingCommand {

    private final double           rotSpeed, timeout, CBeltSpeed;
    private final boolean          stopAtEnd;
    private final ShooterSubsystem shooterSubsystem;

    public ShooterAutoCommand(double rotSpeed, double timeout, double CBeltSpeed,
        ShooterSubsystem shooterSubsystem) {
        this(rotSpeed, timeout, CBeltSpeed, true, shooterSubsystem);
    }

    public ShooterAutoCommand(double rotSpeed, double timeout, double CBeltSpeed, boolean stopAtEnd,
        ShooterSubsystem shooterSubsystem) {

        this.rotSpeed         = rotSpeed;
        this.stopAtEnd        = stopAtEnd;
        this.timeout          = timeout;
        this.CBeltSpeed       = CBeltSpeed;
        this.shooterSubsystem = shooterSubsystem;

        addRequirements(shooterSubsystem);

    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        // Set the speeds on the motors
        shooterSubsystem.setCBSpeed(ShooterConstants.CONVEYOR_BELT_AUTO_SPEED);
        shooterSubsystem.setShooterSpeed(ShooterConstants.SHOOTER_AUTO_SPEED);
    }

    @Override
    public boolean isFinished() {

        // Check the timeout
        if (hasElapsed(timeout)) {
            setFinishReason("Timed out");
            return true;
        }

        return false;
    }

}
