package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "CenterStageDrive")
public class CenterStageDrive extends LinearOpMode {

    private DcMotor FrontRight;
    private DcMotor BackRight;
    private DcMotor FrontLeft;
    private DcMotor BackLeft;
    private DcMotor bottomMotor;
    private DcMotor topMotor;
    private Servo Lservoarm;
    private Servo Rservoram;
    private Servo LClaw;

    private final ElapsedTime intakeTimer = new ElapsedTime();
    //intakeState = IntakeState.RESTING;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() throws InterruptedException {
        double Launch_Angle;

        FrontRight = hardwareMap.get(DcMotor.class, "Front Right");
        BackRight = hardwareMap.get(DcMotor.class, "Back Right");
        FrontLeft = hardwareMap.get(DcMotor.class, "Front Left");
        BackLeft = hardwareMap.get(DcMotor.class, "Back Left");
        bottomMotor = hardwareMap.get(DcMotor.class, "bottomMotor");
        topMotor = hardwareMap.get(DcMotor.class, "topMotor");
        Lservoarm = hardwareMap.get(Servo.class, "Lservoarm");
        Rservoram = hardwareMap.get(Servo.class, "Rservoram");
        LClaw = hardwareMap.get(Servo.class, "LClaw");



        // Put initialization blocks here.
        waitForStart();
        FrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        BackRight.setDirection(DcMotorSimple.Direction.FORWARD);
        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        bottomMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        topMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        Lservoarm.setDirection(Servo.Direction.FORWARD);
        Rservoram.setDirection(Servo.Direction.REVERSE);
        LClaw.setDirection(Servo.Direction.FORWARD);
        BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bottomMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        topMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        if (opModeIsActive()) {
            FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            bottomMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            topMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            FrontRight.setPower(0);
            BackRight.setPower(0);
            FrontLeft.setPower(0);
            BackLeft.setPower(0);
            // Put run blocks here.


            while (opModeIsActive()&&!isStopRequested()) {

                //switch (intakeState){
                    //case RESTING:
                        if (gamepad2.left_bumper) {
                            LClaw.setPosition(0.01);
                            intakeTimer.reset();

                        }
                        break;

                       // if (intakeTimer.milliseconds()>200) {
                            //Rservoram.setPosition(0.25);
                             //Lservoarm.setPosition(0.25);
                            //intakeTimer.reset();


                       // }
                }

                if (gamepad2.left_bumper) {
                    LClaw.setPosition(0.01);
                    sleep(200);
                    Rservoram.setPosition(0.25);
                    Lservoarm.setPosition(0.25);

                }


                if (gamepad2.right_bumper) {
                    LClaw.setPosition(0.06);
                    sleep(200);
                    Rservoram.setPosition(0);
                    Lservoarm.setPosition(0);
                    bottomMotor.setTargetPosition(0);
                    topMotor.setTargetPosition(0);
                    bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    bottomMotor.setPower(1);
                    topMotor.setPower(1);
                    LClaw.setPosition(0.1);
                }
                if (gamepad2.dpad_left) {
                    // low junction
                    bottomMotor.setTargetPosition(400);
                    topMotor.setTargetPosition(400);
                    bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    bottomMotor.setPower(1);
                    topMotor.setPower(1);
                    Rservoram.setPosition(0.4);
                    Lservoarm.setPosition(0.4);
                }
                if (gamepad2.dpad_up) {
                    // medium junction
                    bottomMotor.setTargetPosition(1300);
                    topMotor.setTargetPosition(1300);
                    bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    bottomMotor.setPower(1);
                    topMotor.setPower(1);
                    sleep(600);
                    Rservoram.setPosition(0.4);
                    Lservoarm.setPosition(0.4);
                }
                if (gamepad2.dpad_right) {
                    // high junction
                    bottomMotor.setTargetPosition(2150);
                    topMotor.setTargetPosition(2150);
                    bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    bottomMotor.setPower(1);
                    topMotor.setPower(1);
                    sleep(1000);
                    Rservoram.setPosition(0.4);
                    Lservoarm.setPosition(0.4);
                }
                if (gamepad2.x) {
                    // five cone
                    LClaw.setPosition(0.1);
                    Rservoram.setPosition(0);
                    Lservoarm.setPosition(0);
                    bottomMotor.setTargetPosition(400);
                    topMotor.setTargetPosition(400);
                    bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    bottomMotor.setPower(1);
                    topMotor.setPower(1);
                }
                if (gamepad2.y) {
                    // four cone
                    LClaw.setPosition(0.1);
                    Rservoram.setPosition(0);
                    Lservoarm.setPosition(0);
                    bottomMotor.setTargetPosition(300);
                    topMotor.setTargetPosition(300);
                    bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    bottomMotor.setPower(1);
                    topMotor.setPower(1);
                }
                if (gamepad2.b) {
                    // three cone
                    LClaw.setPosition(0.1);
                    Rservoram.setPosition(0);
                    Lservoarm.setPosition(0);
                    bottomMotor.setTargetPosition(200);
                    topMotor.setTargetPosition(200);
                    bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    bottomMotor.setPower(1);
                    topMotor.setPower(1);
                }
                if (gamepad2.a) {
                    LClaw.setPosition(0.1);
                    Rservoram.setPosition(0);
                    Lservoarm.setPosition(0);
                    bottomMotor.setTargetPosition(100);
                    topMotor.setTargetPosition(100);
                    bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    bottomMotor.setPower(1);
                    topMotor.setPower(1);
                }

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
                telemetry.update();
            }
        }
    }



