package frc.robot.commands.shooter;

import frc.robot.OperatorInput;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootCommand extends LoggingCommand {

    private final OperatorInput    operatorInput;
    private final ShooterSubsystem shooterSubsystem;

    public ShootCommand(OperatorInput operatorInput, ShooterSubsystem shooterSubsystem) {
        this.operatorInput    = operatorInput;
        this.shooterSubsystem = shooterSubsystem;

        this.addRequirements(shooterSubsystem);
    }

    public void init() {
        logCommandStart();
    }

    @Override
    public void execute() {

        shooterSubsystem.setShooterSpeed(-.4);
    }

    @Override
    public boolean isFinished() {

        return operatorInput.stopShooter();
    }

    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);

        shooterSubsystem.stop();
    }
}
