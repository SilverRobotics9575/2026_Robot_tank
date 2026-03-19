package frc.robot.commands.intake;

import frc.robot.OperatorInput;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class DefaultIntakeCommand extends LoggingCommand {

    private final OperatorInput   operatorInput;
    private final IntakeSubsystem intakeSubsystem;

    public DefaultIntakeCommand(OperatorInput operatorInput, IntakeSubsystem intakeSubsystem) {
        this.operatorInput   = operatorInput;
        this.intakeSubsystem = intakeSubsystem;

        this.addRequirements(intakeSubsystem);
    }

    public void init() {
        logCommandStart();
        intakeSubsystem.stop();
    }

    @Override
    public void execute() {

        // Set the intake speed
        intakeSubsystem.setIntakeSpeed(operatorInput.getIntakeRollerSpeed());

        intakeSubsystem.setRetractorSpeed(operatorInput.getIntakeRetractorSpeed());

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);

        intakeSubsystem.stop();
    }
}
