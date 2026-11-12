package frc.robot;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkMax;

public class TankSubsystem extends SubsystemBase {

    private XboxController ControlDriver = new XboxController(0);

    private SparkMax M1_Left = new SparkMax(2, MotorType.kBrushless);
    private SparkMax M2_Left = new SparkMax(4, MotorType.kBrushless);

    private SparkMax M1_Right = new SparkMax(5, MotorType.kBrushless);
    private SparkMax M2_Right = new SparkMax(3, MotorType.kBrushless);

    public TankSubsystem() {

    }

    @Override
    public void periodic(){

        double GatilhoDireito = MathUtil.applyDeadband(ControlDriver.getRightTriggerAxis(), 0.08);
        double GatilhoEsquerdo = MathUtil.applyDeadband(ControlDriver.getLeftTriggerAxis(), 0.08);
        double Analogico = MathUtil.applyDeadband(ControlDriver.getLeftX(), 0.1);

        drive(GatilhoDireito, GatilhoEsquerdo, (-Analogico)  * 0.3);

    }


    private void drive(double Frente, double Re, double Direcao){

        double conjuntoLeft = (Frente - Re) - Direcao;
        double conjuntoRight = (Frente - Re) + Direcao;

        M1_Left.set(MathUtil.clamp(conjuntoLeft, -1.0, 1.0));
        M2_Left.set(MathUtil.clamp(conjuntoLeft, -1.0, 1.0));

        M1_Right.set(MathUtil.clamp(conjuntoRight, -1.0, 1.0));
        M2_Right.set(MathUtil.clamp(conjuntoRight, -1.0, 1.0));

    }


    public double getVelocity(){
        return M1_Left.getEncoder().getVelocity();
    }
}
