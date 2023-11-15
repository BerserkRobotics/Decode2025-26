package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "basicDrive")
public class basicDrive extends LinearOpMode {

    private DcMotor FrontLeft;
    private DcMotor BackLeft;
    private DcMotor FrontRight;
    private DcMotor BackRight;
    private Servo ClawServo;
    private DcMotor ArmMotor;
    private Servo planeLaunch;
    private Servo planeArm;

    /**
     * This function is executed when this OpMode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        ClawServo = hardwareMap.get(Servo.class, "ClawServo");
        ArmMotor = hardwareMap.get(DcMotor.class, "ArmMotor");
        planeLaunch = hardwareMap.get(Servo.class, "planeLaunch");
        planeArm = hardwareMap.get(Servo.class, "planeArn");

        // Put initialization blocks here.
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        ClawServo.setPosition(0.1);
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ArmMotor.setPower(0);
        ArmMotor.setDirection(DcMotor.Direction.FORWARD);
        ArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        planeLaunch.setPosition(0.6);
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                FrontRight.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x);
                BackRight.setPower(gamepad1.left_stick_y - gamepad1.left_stick_x);
                FrontLeft.setPower(gamepad1.left_stick_y - gamepad1.left_stick_x);
                BackLeft.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x);
                while (gamepad1.right_bumper) {
                    FrontRight.setPower(0.6);
                    BackRight.setPower(0.6);
                    FrontLeft.setPower(-0.6);
                    BackLeft.setPower(-0.6);
                }
                while (gamepad1.left_bumper) {
                    FrontRight.setPower(-0.6);
                    BackRight.setPower(-0.6);
                    FrontLeft.setPower(0.6);
                    BackLeft.setPower(0.6);
                }
                if (gamepad2.dpad_left) {
                    planeLaunch.setPosition(0);
                }
                if (gamepad1.dpad_down) {
                    planeArm.setPosition(0);
                }
                if (gamepad1.dpad_up) {
                    planeArm.setPosition(0.4);
                }
                if (gamepad2.a) {
                    ClawServo.setPosition(0);
                }
                if (gamepad2.b) {
                    ClawServo.setPosition(0.10);
                }
                if (gamepad2.dpad_up) {
                    ArmMotor.setTargetPosition(800);
                    ArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    ArmMotor.setPower(0.5);
                }
                if (gamepad2.dpad_down) {
                    ArmMotor.setTargetPosition(0);
                    ArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    ArmMotor.setPower(0.3);
                }
                telemetry.update();
            }
        }
    }
}