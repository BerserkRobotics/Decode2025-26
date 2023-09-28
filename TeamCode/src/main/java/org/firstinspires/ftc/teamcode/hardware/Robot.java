package org.firstinspires.ftc.teamcode.hardware;

// import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.AnalogInput;
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

        // TODO: Edit these!
        DcMotorEx motorDriveRightFront = hardwareMap.get(DcMotorEx.class, "rf");
        motorDriveRightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        DcMotorEx motorDriveRightRear = hardwareMap.get(DcMotorEx.class, "rr");
        motorDriveRightRear.setDirection(DcMotorSimple.Direction.REVERSE);
        mecanum = new MecanumSubSystem(motorDriveLeftFront, motorDriveLeftRear, motorDriveRightFront, motorDriveRightRear);

        // Gyro/IMU Sub System
        IMU imu = hardwareMap.get(IMU.class, "imu");
        gyroIMU = new IMU(imu);

        // HEX Linear Extension
        DcMotorEx motorHEX = hardwareMap.get(DcMotorEx.class, "HEX");
        motorHEX.setDirection(DcMotorSimple.Direction.REVERSE);
        DigitalChannel limitSwitch = hardwareMap.get(DigitalChannel.class, "limit");
        HEXtension = new ExtensionLinearLimitSwitch(motorHEX, limitSwitch, 2900);

        // HEX Pivot
        // HEX Pivot Left Sub System
        Servo HEXPivotLeftServo = hardwareMap.servo.get("HEXleftrotator");
        Map<String, Double> HEXPivotLeftPositions = new HashMap<>();
        HEXPivotLeftPositions.put("HandOff", 0.30);
        HEXPivotLeftPositions.put("FitIn18", 0.82);
        HEXPivotLeftPositions.put("OneCone", 0.82);
        HEXPivotLeftPositions.put("TwoCones", 0.79);
        HEXPivotLeftPositions.put("ThreeCones", 0.78);
        HEXPivotLeftPositions.put("FourCones", 0.75);
        HEXPivotLeftPositions.put("FiveCones", 0.73);
        HEXPivotLeftPositions.put("RaisePivot", 0.41);

        HEXPivotLeft = new ServoMappedPositions(HEXPivotLeftServo, HEXPivotLeftPositions);

        // HEX Pivot Right Sub System
        Servo HEXPivotRightServo = hardwareMap.servo.get("HEXrightrotator");
        Map<String, Double> HEXPivotRightPositions = new HashMap<>();
        HEXPivotRightPositions.put("HandOff", 0.68);
        HEXPivotRightPositions.put("FitIn18", 0.17);
        HEXPivotRightPositions.put("OneCone", 0.17);
        HEXPivotRightPositions.put("TwoCones", 0.20);
        HEXPivotRightPositions.put("ThreeCones", 0.21);
        HEXPivotRightPositions.put("FourCones", 0.23);
        HEXPivotRightPositions.put("FiveCones", 0.25);
        HEXPivotRightPositions.put("RaisePivot", 0.57);
        HEXPivotRight = new ServoMappedPositions(HEXPivotRightServo, HEXPivotRightPositions);

        // Combined HEX Pivot Sub System
        HEXPivot = new HEXPivot(HEXPivotLeft, HEXPivotRight);

        // HEX Claw
        Servo HEXClawServo = hardwareMap.servo.get("HEXclaw");
        HEXClaw = new ServoOpenClosePositions(HEXClawServo, 0.49, 0.18);

        // Complete HEX Sub System
        HEX = new HEXSubSystem(HEXtension, HEXPivot, HEXClaw);
        HEX.setPivotTimes(400, 650);
        HEX.setClawTimes(250, 300);

        // VEX Sub System
        //VEX Lift Left Sub System
        DcMotorEx VEXLeftMotor = hardwareMap.get(DcMotorEx.class, "leftVEX");
        VEXLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        AnalogInput leftLimit = hardwareMap.analogInput.get("leftlimit");
        VEXLeft = new ExtensionDR4BAbsoluteEncoder(VEXLeftMotor, leftLimit, true);
        VEXLeft.setLiftMotorProperties(0.35, 18.0, 0.03);
        VEXLeft.setLiftHeights(2.40, 1.57, 0.95, 1.236, 0.46);
        VEXLeft.setLiftTickHeights(860, 460, 260, 400, 0);
        VEXLeft.setMinMaxLiftHeights(3.01, 0.46);

        //VEX Lift Right Sub System
        DcMotorEx VEXRightMotor = hardwareMap.get(DcMotorEx.class, "rightVEX");
        VEXRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        AnalogInput rightLimit = hardwareMap.analogInput.get("rightlimit");
        VEXRight = new ExtensionDR4BAbsoluteEncoder(VEXRightMotor, rightLimit);
        VEXRight.setLiftMotorProperties(0.35, 18.0, 0.03);
        VEXRight.setLiftHeights(2.34, 1.51 /*NICE*/, 0.96, 1.196, 0.41);
        VEXRight.setLiftTickHeights(860, 460, 260, 400, 0);
        VEXRight.setMinMaxLiftHeights(2.96, 0.41);

        // VEX Pivot Left Sub System
        Servo VEXPivotLeftServo = hardwareMap.servo.get("leftpivot");
        Map<String, Double> VEXPivotLeftPositions = new HashMap<>();
        VEXPivotLeftPositions.put("FitIn18", 0.10);
        VEXPivotLeftPositions.put("HandOff", 0.28);
        VEXPivotLeftPositions.put("Score", 0.83);
        VEXPivotLeft = new ServoMappedPositions(VEXPivotLeftServo, VEXPivotLeftPositions);

        // VEX Pivot Right Sub System
        Servo VEXPivotRightServo = hardwareMap.servo.get("rightpivot");
        Map<String, Double> VEXPivotRightPositions = new HashMap<>();
        VEXPivotRightPositions.put("FitIn18", 0.85);
        VEXPivotRightPositions.put("HandOff", 0.67);
        VEXPivotRightPositions.put("Score", 0.12);
        VEXPivotRight = new ServoMappedPositions(VEXPivotRightServo, VEXPivotRightPositions);
        // Combined VEX Pivot Sub System
        VEXPivot = new VEXPivot(VEXPivotLeft, VEXPivotRight);

        // VEX Rotator Sub System
        Servo VEXRotatorServo = hardwareMap.servo.get("rotator");
        Map<String, Double> VEXRotatePositions = new HashMap<>();
        VEXRotatePositions.put("Mid", 0.49);
        VEXRotatePositions.put("Left", 0.83);
        VEXRotatePositions.put("Right", 0.15);
        VEXRotator = new ServoMappedPositions(VEXRotatorServo, VEXRotatePositions);

        // VEX Claw Sub System
        Servo VEXClawServo = hardwareMap.servo.get("VEXclaw");
        VEXClaw = new ServoOpenClosePositions(VEXClawServo, 0.33, 0.65);

        // Complete VEX Sub System
        VEX = new VEXSubSystem(VEXLeft, VEXRight, VEXPivot, VEXRotator, VEXClaw);
        VEX.setPivotTimes(450, 450);
        VEX.setRotatorTimes(400, 400);
        VEX.setClawTimes(250, 250);
    }

    public void initTeleOpServos() {
        VEXClaw.open();
        HEXClaw.open();
        VEXPivot.fitIn18();
        VEXRotator.goToPosition("Mid");
        HEXPivot.grab();
        VEXLeft.setGoToTickPosition(400);
        VEXRight.setGoToTickPosition(400);
        while (VEX.liftIsBusy()) {
            VEX.setLiftPower(0.5);
        }
        VEX.stop();
        VEXPivot.handOff();
    }

    public void initAutoServos() {
        VEXClaw.close();
        HEXClaw.open();
        VEXPivot.fitIn18();
        VEXRotator.goToPosition(0.5);
        HEXPivot.oneCone();
    }
}
