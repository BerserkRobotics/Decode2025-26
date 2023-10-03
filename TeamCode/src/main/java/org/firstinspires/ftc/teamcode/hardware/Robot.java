package org.firstinspires.ftc.teamcode.hardware;

// import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.HashMap;
import java.util.Map;


public class Robot {
    private ElapsedTime sleepTimer = new ElapsedTime();

    public HardwareMap hardwareMap;
    public IMU gyroIMU = null;
    public Robot(){

    }

    public void init(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        // Drive
        DcMotorEx FrontRight = hardwareMap.get(DcMotorEx.class, "Front Right");
        FrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        DcMotorEx BackRight = hardwareMap.get(DcMotorEx.class, "Back Right");
        BackRight.setDirection(DcMotorSimple.Direction.FORWARD);
        DcMotorEx FrontLeft = hardwareMap.get(DcMotorEx.class, "Front Left");
        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        DcMotorEx BackLeft = hardwareMap.get(DcMotorEx.class, "Back Left");
        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Gyro/IMU Sub System
        IMU imu = hardwareMap.get(IMU.class, "imu");
        gyroIMU = new IMU(imu);

        // random hardware from last year for reference
        DcMotor bottomMotor = hardwareMap.get(DcMotor.class, "bottomMotor")
    }
}
