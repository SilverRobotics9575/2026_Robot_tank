package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {

    // The motors on the left side of the drive.
    private final SparkMax        leftShooter         = new SparkMax(ShooterConstants.LEFT_SHOOTER_CAN_ID,
        MotorType.kBrushless);
    private final SparkMax        rightShooter        = new SparkMax(ShooterConstants.RIGHT_SHOOTER_CAN_ID,
        MotorType.kBrushless);
    private final SparkFlex       conveyorBelt        = new SparkFlex(ShooterConstants.CONVEYOR_BELT_CAN_ID,
        MotorType.kBrushless);

    // Encoders
    private final RelativeEncoder leftShooterEncoder  = leftShooter.getEncoder();
    private final RelativeEncoder rightShooterEncoder = rightShooter.getEncoder();
    private final RelativeEncoder CBEncoder           = conveyorBelt.getEncoder();

    /** Creates a new DriveSubsystem. */
    public ShooterSubsystem() {

        /*
         * Configure Motors
         */
        SparkMaxConfig config = new SparkMaxConfig();
        config.inverted(true)
            .idleMode(IdleMode.kBrake)
            .disableFollowerMode();
        leftShooter.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        /*
         * Configure Right Side Motors
         */

        config.follow(rightShooter);
        rightShooter.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        config = new SparkMaxConfig();
        config.inverted(true)
            .idleMode(IdleMode.kBrake);
        rightShooter.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        SparkFlexConfig fconfig = new SparkFlexConfig();
        fconfig.inverted(false)
            .idleMode(IdleMode.kBrake)
            .disableFollowerMode();
        conveyorBelt.configure(fconfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    /*
     * Shooter Motor routines
     */
    public void setShooterSpeed(double shooterSpeed) {
        leftShooter.set(shooterSpeed * 0.8); // 5676 rpm at 12v and triggeraxis 1
        rightShooter.set(-shooterSpeed * 0.8);
    }

    public double getShooterSpeed() {
        return Math.round(leftShooterEncoder.getVelocity());
    }

    public void setCBSpeed(double CBSpeed) {
        conveyorBelt.set(CBSpeed);
    }

    public double getCBSpeed() {
        return Math.round(leftShooterEncoder.getVelocity());
    }


    /*
     * Kicker Motor routines
     */
    /*
     * public void setKickerSpeed(double kickerSpeed) {
     * rightShooter.set(kickerSpeed);
     * }
     * 
     * public double getKickerSpeed() {
     * return Math.round(kickerEncoder.getVelocity());
     * }
     */


    @Override
    public void periodic() {

        SmartDashboard.putNumber("Left Shooter Speed", getShooterSpeed());
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append(this.getClass().getSimpleName()).append(" : ")
            .append("Shooter Speed ").append(getShooterSpeed());

        return sb.toString();
    }

    public void stop() {
        leftShooter.set(0);
        rightShooter.set(0);
    }

}
