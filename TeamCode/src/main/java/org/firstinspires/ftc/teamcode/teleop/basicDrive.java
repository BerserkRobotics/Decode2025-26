package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "BasicDrive")
public class basicDrive extends LinearOpMode {

    private DcMotor FrontLeft;
    private DcMotor BackLeft;
    private DcMotor FrontRight;
    private DcMotor BackRight;
    private DcMotor RSlideMotor; //right
    private DcMotor LSlideMotor; //left
    private DcMotor IntakeMotor;
    private CRServo IntakeRoller;
    private Servo PlaneLift;
    private Servo PlaneLaunch;
    private Servo TClawServo; //top
    private Servo BClawServo; //bottom
    private Servo RArmServo; //right
    private Servo LArmServo; //left


    /**
     * This function is executed when this OpMode is selected from the Driver Station
     */
    @Override
    public void runOpMode() {
        // motors
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        RSlideMotor = hardwareMap.get(DcMotor.class, "RSlideMotor");
        LSlideMotor = hardwareMap.get(DcMotor.class, "LSlideMotor");
        IntakeMotor = hardwareMap.get(DcMotor.class, "IntakeMotor");

        // servos
        IntakeRoller = hardwareMap.get(CRServo.class, "IntakeRoller");
        PlaneLift = hardwareMap.get(Servo.class, "PlaneLift");
        PlaneLaunch = hardwareMap.get(Servo.class, "PlaneLaunch");
        TClawServo = hardwareMap.get(Servo.class, "TClawServo");
        BClawServo = hardwareMap.get(Servo.class, "BClawServo");
        RArmServo = hardwareMap.get(Servo.class, "RArmServo");
        LArmServo = hardwareMap.get(Servo.class, "LArmServo");

        // Put initialization blocks here.
        // TODO: edit motor directions and find positions for new motors + IntakeRoller (crservo)

        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        RSlideMotor.setDirection(DcMotor.Direction.FORWARD);
        LSlideMotor.setDirection(DcMotor.Direction.REVERSE);
        IntakeMotor.setDirection(DcMotor.Direction.FORWARD);
        RArmServo.setDirection(Servo.Direction.REVERSE);
        IntakeRoller.setDirection(CRServo.Direction.FORWARD);

        // resting positions
        PlaneLift.setPosition(0);
        PlaneLaunch.setPosition(.65);

        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        IntakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FrontRight.setPower(0);
        BackRight.setPower(0);
        FrontLeft.setPower(0);
        BackLeft.setPower(0);
        LSlideMotor.setPower(0);
        RSlideMotor.setPower(0);

        TClawServo.setPosition(0);
        BClawServo.setPosition(0);
        LArmServo.setPosition(0.04);
        RArmServo.setPosition(0.04);

        LSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {

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

                // TODO: update buttons to driver preference!

                if (gamepad1.dpad_right) {
                    PlaneLaunch.setPosition(0); //launches plane
                }
                if (gamepad1.dpad_down) {
                    PlaneLift.setPosition(0); //resting position
                }
                if (gamepad1.dpad_up) {
                    PlaneLift.setPosition(0.15); //launch position //TODO: test this value more
                }

                //TODO: check position (power) of CRServo IntakeRoller

                if (gamepad2.a) {
                    TClawServo.setPosition(0); //open top claw
                    BClawServo.setPosition(0); //open bottom claw
                }
                if (gamepad2.b) {
                    TClawServo.setPosition(.05); //close top claw
                    BClawServo.setPosition(.05); //close bottom claw
                }
                if (gamepad2.x) { //move claw arm to intake
                    RArmServo.setPosition(0);
                    LArmServo.setPosition(0);
                }
                if (gamepad2.y) { //move claw arm to outtake
                    RArmServo.setPosition(.5);
                    LArmServo.setPosition(.5);
                }

                /* TODO: find target positions for slide and intake motors!!
                if (gamepad2.dpad_up) { //raise slides
                    RSlideMotor.setTargetPosition(800);
                    RSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    RSlideMotor.setPower(0.5);

                    LSlideMotor.setTargetPosition(800);
                    LSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    LSlideMotor.setPower(0.5);
                }
                if (gamepad2.dpad_down) { //lower slides
                    RSlideMotor.setTargetPosition(0);
                    RSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    RSlideMotor.setPower(0.3);

                    LSlideMotor.setTargetPosition(0);
                    LSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    LSlideMotor.setPower(0.3);
                }

                if (gamepad1.dpad_left) {
                    IntakeMotor.setTargetPosition(0);
                    IntakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    IntakeMotor.setPower(0.5);
                }
                */

                telemetry.update();
            }
        }
    }
}




