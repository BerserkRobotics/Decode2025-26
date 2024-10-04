package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "mainDrive")
public class mainDrive extends LinearOpMode {

    private DcMotor FrontRight;
    private DcMotor BackRight;
    private DcMotor FrontLeft;
    private DcMotor BackLeft;
    private DcMotor RightAscent;
    private DcMotor LeftAscent;
    private Servo ClawGrab;
    private Servo ClawPivot;

    @Override
    public void runOpMode() {
        // Initialize motors
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");

        RightAscent = hardwareMap.get(DcMotor.class,"RightAscent");
        LeftAscent = hardwareMap.get(DcMotor.class,"LeftAscent");

        ClawGrab = hardwareMap.get(Servo.class,"ClawGrab");
        ClawPivot = hardwareMap.get(Servo.class,"ClawPivot");

        // Set motor directions
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);

        RightAscent.setDirection(DcMotor.Direction.REVERSE);
        LeftAscent.setDirection(DcMotor.Direction.REVERSE);

        // Set zero power behavior
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        RightAscent.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LeftAscent.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // Initialize motor power variables
            double front_left_power = 0;
            double front_right_power = 0;
            double back_left_power = 0;
            double back_right_power = 0;

            double turnSpeed;
            if (gamepad1.left_bumper) {
                turnSpeed = -1;
            } else if (gamepad1.right_bumper) {
                turnSpeed = 1;
            } else {
                turnSpeed = 0;
            }
            double moveSpeed = -gamepad1.left_stick_y;
            double strafeSpeed = gamepad1.left_stick_x;
            double speedSetter = 0.6;

            front_left_power  = (moveSpeed + turnSpeed + strafeSpeed) * speedSetter;
            front_right_power = (moveSpeed - turnSpeed - strafeSpeed) * speedSetter;
            back_left_power   = (moveSpeed + turnSpeed - strafeSpeed) * speedSetter;
            back_right_power  = (moveSpeed - turnSpeed + strafeSpeed) * speedSetter;


            // Set motor powers
            FrontRight.setPower(front_right_power);
            FrontLeft.setPower(front_left_power);
            BackRight.setPower(back_right_power);
            BackLeft.setPower(back_left_power);

            // Ascent! free control with joysticks
            RightAscent.setPower((gamepad2.right_stick_y) / 1);
            LeftAscent.setPower((gamepad2.left_stick_y) / 1);

            // Open and close claw
            if (gamepad2.a) {
                ClawPivot.setPosition(.75); //down
                sleep(100);
                ClawGrab.setPosition(1); //open
            } else if (gamepad2.b) {
                ClawGrab.setPosition(.75); //close
                sleep(100);
                ClawPivot.setPosition(0); //up
            }


            // Update telemetry data
            telemetry.addData("Status", "Running");
            telemetry.addData("Front Left Power", front_left_power);
            telemetry.addData("Front Right Power", front_right_power);
            telemetry.addData("Back Left Power", back_left_power);
            telemetry.addData("Back Right Power", back_right_power);
            telemetry.update();
        }
    }
}
