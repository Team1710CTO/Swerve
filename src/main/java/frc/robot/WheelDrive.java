/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import java.lang.Math;
/**
 * Add your docs here.
 */
public class WheelDrive {
    private CANSparkMax angleMotor;
    private CANSparkMax speedMotor;
    private PIDController pidController;
    private final double MAX_VOLTS = 4.95;
    public WheelDrive (int angleMotor, int speedMotor, int encoder) {
        this.angleMotor = new CANSparkMax (angleMotor, MotorType.kBrushless);
        this.speedMotor = new CANSparkMax (speedMotor, MotorType.kBrushless);
        pidController = new PIDController (1, 0, 0, new AnalogInput (encoder), this.angleMotor);
    
        pidController.setOutputRange (-1, 1);
        pidController.setContinuous ();
        pidController.enable ();
    }
    public void drive (double speed, double angle) {
        speedMotor.set (speed);
    
        double setpoint = angle * (MAX_VOLTS * 0.5) + (MAX_VOLTS * 0.5); // Optimization offset can be calculated here.
        if (setpoint < 0) {
            setpoint = MAX_VOLTS + setpoint;
        }
        if (setpoint > MAX_VOLTS) {
            setpoint = setpoint - MAX_VOLTS;
        }
    
        pidController.setSetpoint (setpoint);
    }
}
