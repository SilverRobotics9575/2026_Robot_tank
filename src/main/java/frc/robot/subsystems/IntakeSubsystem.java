package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {

    // The motor pulling the intake belt.
    private final SparkMax        intakeMotor      = new SparkMax(IntakeConstants.INTAKE_CAN_ID, MotorType.kBrushless);

    // Encoders
    private final RelativeEncoder intakeEncoder    = intakeMotor.getEncoder();

    // The motor pulling the intake belt.
    private final SparkMax        retractorMotor   = new SparkMax(IntakeConstants.RETRACTOR_CAN_ID, MotorType.kBrushless);

    // Encoders
    private final RelativeEncoder retractorEncoder = retractorMotor.getEncoder();

    /** Creates a new DriveSubsystem. */
    public IntakeSubsystem() {

        /*
         * Configure Motors
         */
        SparkMaxConfig config = new SparkMaxConfig();
        config.inverted(true)
            .idleMode(IdleMode.kBrake)
            .disableFollowerMode();
        intakeMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    }

    /*
     * intake Motor routines
     */
    public void setIntakeSpeed(double intakeSpeed) {
        intakeMotor.set(intakeSpeed * 0.9); // 5676 rpm at 12v and triggeraxis 1

    }

    public double getIntakeSpeed() {
        return Math.round(intakeEncoder.getVelocity());
    }


    /*
     * Retractor motor routines
     */
    public void setRetractorSpeed(double retractorSpeed) {
        retractorMotor.set(retractorSpeed);

    }

    public boolean isIntakeDeployed() {
    	return getRetractorPosition() < 0;
    }

    /** 
     * Get retractor position in degrees from the ground
     * fully retracted, the retractor position is 90 deg,
     * fully deployed, the retractor position is less than zero
     * 
     * @return angle relative to the ground where parallel to the 
     * ground is zero and fully deployed is less than zero.
     */
    public double getRetractorPosition() {
    	return retractorEncoder.getPosition();
    }

    @Override
    public void periodic() {

        SmartDashboard.putNumber("Intake Motor Speed", getIntakeSpeed());
        SmartDashboard.putNumber("Retractor Position", getRetractorPosition());
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append(this.getClass().getSimpleName()).append(" : ")
            .append("Intake Speed ").append(getIntakeSpeed())
            .append(" Retractor Position ").append(getRetractorPosition());

        return sb.toString();
    }

    public void stop() {
        setIntakeSpeed(0);
        setRetractorSpeed(0);
    }

}
