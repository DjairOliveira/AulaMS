package frc.robot;

import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;

public class Intake {

    private SparkMax pivo = new SparkMax(6, MotorType.kBrushless);

    private final SparkMaxConfig pivoConfig = new SparkMaxConfig();
    private final SparkClosedLoopController pivoPID = pivo.getClosedLoopController();

    private SparkMax coletor = new SparkMax(7, MotorType.kBrushless);

    private final SparkMaxConfig coletorConfig = new SparkMaxConfig();
    private final SparkClosedLoopController coletorPID = coletor.getClosedLoopController();

    public Intake() {
        pivoConfig.inverted(true).idleMode(IdleMode.kBrake).smartCurrentLimit(40);
        coletorConfig.inverted(false).idleMode(IdleMode.kBrake).smartCurrentLimit(40);

        pivoConfig.closedLoop.pid(0.01, 0.0, 0.0);
        pivoConfig.closedLoop.feedForward.kV(0.0021);
        pivoConfig.closedLoop.maxMotion
                .cruiseVelocity(3000)
                .maxAcceleration(1000)
                .allowedProfileError(1);

        pivo.configure(pivoConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        coletorConfig.closedLoop.pid(0.0001, 0.0, 0.0);
        coletorConfig.closedLoop.feedForward.kV(0.0021);

        coletor.configure(coletorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        
        pivo.getEncoder().setPosition(0);
        coletor.getEncoder().setPosition(0);
        
    }

    public void setMaxMotion(double position){
        pivoPID.setSetpoint(position, ControlType.kMAXMotionPositionControl);
    }

    public void setRPM(double RPM){
        coletorPID.setSetpoint(RPM, ControlType.kVelocity);
    }

    public double getPosition() {
        return pivo.getEncoder().getPosition();
    }

    public double getVelocity() {
        return coletor.getEncoder().getVelocity();
    }
}
