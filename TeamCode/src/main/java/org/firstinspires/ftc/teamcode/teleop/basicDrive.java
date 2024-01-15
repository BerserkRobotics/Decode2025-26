package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "basicDrive")
public class basicDrive extends LinearOpMode {

    private DcMotor FrontRight;
    private DcMotor BackRight;
    private DcMotor FrontLeft;
    private DcMotor BackLeft;
    private DcMotor IntakeMotor;
    private DcMotor LSlideMotor;
    private DcMotor RSlideMotor;
    private Servo RArmServo;
    private Servo TClawServo;
    private Servo BClawServo;
    private Servo LArmServo;
    private CRServo IntakeRoller;
    private Servo PlaneLaunch;
    private Servo PlaneLift;

    /**
     * This function is executed when this OpMode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        //motors
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        LSlideMotor = hardwareMap.get(DcMotor.class, "LSlideMotor");
        RSlideMotor = hardwareMap.get(DcMotor.class, "RSlideMotor");
        IntakeMotor = hardwareMap.get(DcMotor.class, "IntakeMotor");

        //servos
        RArmServo = hardwareMap.get(Servo.class, "RArmServo");
        LArmServo = hardwareMap.get(Servo.class, "LArmServo");
        TClawServo = hardwareMap.get(Servo.class, "TClawServo");
        BClawServo = hardwareMap.get(Servo.class, "BClawServo");
        PlaneLaunch = hardwareMap.get(Servo.class, "PlaneLaunch");
        PlaneLift = hardwareMap.get(Servo.class, "PlaneLift");
        IntakeRoller = hardwareMap.get(CRServo.class, "IntakeRoller");

        //drive motors
        FrontRight.setDirection(DcMotor.Direction.REVERSE);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        FrontLeft.setDirection(DcMotor.Direction.FORWARD);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);

        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FrontRight.setPower(0);
        BackRight.setPower(0);
        FrontLeft.setPower(0);
        BackLeft.setPower(0);

        //intake
        IntakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        IntakeRoller.setDirection(CRServo.Direction.FORWARD);

        //slide motors
        LSlideMotor.setDirection(DcMotor.Direction.FORWARD);
        RSlideMotor.setDirection(DcMotor.Direction.REVERSE);
        LSlideMotor.setPower(0);
        RSlideMotor.setPower(0);

        LSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        LSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //arm + claw servos
        RArmServo.setDirection(Servo.Direction.REVERSE);
        TClawServo.setPosition(0);
        BClawServo.setPosition(0);
        LArmServo.setPosition(0.04);
        RArmServo.setPosition(0.04);


        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                FrontRight.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x) / 1);
                BackRight.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x) / 1);
                FrontLeft.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x) / 1);
                BackLeft.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x) / 1);

                while (gamepad1.right_bumper) {
                    FrontRight.setPower(0.5);
                    BackRight.setPower(0.5);
                    FrontLeft.setPower(-0.5);
                    BackLeft.setPower(-0.5);
                }
                while (gamepad1.left_bumper) {
                    FrontRight.setPower(-0.5);
                    BackRight.setPower(-0.5);
                    FrontLeft.setPower(0.5);
                    BackLeft.setPower(0.5);
                }
                // Launch Plane
                if (gamepad2.dpad_right) {
                    PlaneLaunch.setPosition(0);
                }
                // lift plane arm to launch
                if (gamepad2.dpad_up) {
                    PlaneLift.setPosition(0.15);
                }
                // lower plane arm to rest
                if (gamepad2.dpad_down) {
                    PlaneLift.setPosition(0);
                }
                // TopOpen Claw
                if (gamepad2.a) {
                    TClawServo.setPosition(0);
                }
                // BottomOpen Claw
                if (gamepad2.y) {
                    BClawServo.setPosition(0);
                }
                // Slides to first tape
                if (gamepad1.dpad_left) {
                    BClawServo.setPosition(0.05);
                    TClawServo.setPosition(0.05);
                    sleep(100);
                    LSlideMotor.setTargetPosition(700);
                    RSlideMotor.setTargetPosition(700);
                    LSlideMotor.setPower(1);
                    RSlideMotor.setPower(1);
                    LSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    RSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    sleep(500);
                    LArmServo.setPosition(0.35);
                    RArmServo.setPosition(0.35);
                }
                // Slides to second tape
                if (gamepad1.dpad_up) {
                    BClawServo.setPosition(0.05);
                    TClawServo.setPosition(0.05);
                    sleep(100);
                    LSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    RSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    LSlideMotor.setTargetPosition(1100);
                    RSlideMotor.setTargetPosition(1100);
                    LSlideMotor.setPower(1);
                    RSlideMotor.setPower(1);
                    LSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    RSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    sleep(500);
                    LArmServo.setPosition(0.35);
                    RArmServo.setPosition(0.35);
                }
                // Slides to third tape
                if (gamepad1.dpad_right) {
                    BClawServo.setPosition(0.05);
                    TClawServo.setPosition(0.05);
                    sleep(100);
                    LSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    RSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    LSlideMotor.setTargetPosition(1500);
                    RSlideMotor.setTargetPosition(1500);
                    LSlideMotor.setPower(1);
                    RSlideMotor.setPower(1);
                    LSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    RSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    sleep(500);
                    LArmServo.setPosition(0.35);
                    RArmServo.setPosition(0.35);
                }
                // Slides down
                if (gamepad1.dpad_down) {
                    LArmServo.setPosition(0);
                    RArmServo.setPosition(0);
                    TClawServo.setPosition(0);
                    BClawServo.setPosition(0);
                    sleep(500);
                    LSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    RSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    LSlideMotor.setTargetPosition(0);
                    RSlideMotor.setTargetPosition(0);
                    LSlideMotor.setPower(1);
                    RSlideMotor.setPower(1);
                    LSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    RSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                if (gamepad2.left_bumper) {
                    IntakeMotor.setPower(0.9);
                    IntakeRoller.setPower(0.9);
                } else if (gamepad2.right_bumper) {
                    IntakeMotor.setPower(-0.9);
                    IntakeRoller.setPower(-0.9);
                } else {
                    IntakeMotor.setPower(0);
                    IntakeRoller.setPower(0);
                }
            }
            telemetry.update();
        }
    }
}