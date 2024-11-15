package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "NewDrive")
public class NewDrive extends LinearOpMode {
    //Motors and Servo initialization
    private DcMotor FrontRight;
    private DcMotor BackRight;
    private DcMotor FrontLeft;
    private DcMotor BackLeft;
    private DcMotor RightAscent;
    private DcMotor LeftAscent;
    private CRServo IntakeRoller;
    private Servo IntakePivot;
    private DcMotor IntakeArm;
    private DcMotor OuttakeSlides;
    private Servo OuttakePivot;

    // INTAKE values
    int IntakeArmTicks = 0;
    int OuttakeArmTicks = 0;
    float pivot = 0;
    @Override
    public void runOpMode() {
        // Initialize motors
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");

        RightAscent = hardwareMap.get(DcMotor.class,"RightAscent");
        LeftAscent = hardwareMap.get(DcMotor.class,"LeftAscent");

        OuttakeSlides = hardwareMap.get(DcMotor.class, "OuttakeSlides");
        OuttakePivot = hardwareMap.get(Servo.class,"OuttakePivot");
        IntakeArm = hardwareMap.get(DcMotor.class,"IntakeArm");
        IntakeRoller = hardwareMap.get(CRServo.class,"IntakeRoller");
        IntakePivot = hardwareMap.get(Servo.class,"IntakePivot");
        // Set motor directions
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);

        // Set zero power behavior
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //setting right ascent behaviors
        RightAscent.setDirection(DcMotorSimple.Direction.FORWARD);
        RightAscent.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        RightAscent.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        RightAscent.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //setting left ascent behaviors
        LeftAscent.setDirection(DcMotorSimple.Direction.FORWARD);
        LeftAscent.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        LeftAscent.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        LeftAscent.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //setting intake arm
        IntakeArm.setDirection(DcMotorSimple.Direction.REVERSE);
        IntakeArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        IntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        IntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        OuttakeSlides.setDirection(DcMotorSimple.Direction.REVERSE);
        OuttakeSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        OuttakeSlides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        OuttakeSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);



        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // Initialize motor power variables
            double front_left_power  = 0;
            double front_right_power = 0;
            double back_left_power   = 0;
            double back_right_power  = 0;

            //setting movement amounts
            double turnSpeed;
            if (gamepad1.left_bumper) {
                turnSpeed = -1;
            } else if (gamepad1.right_bumper) {
                turnSpeed = 1;
            } else {
                turnSpeed = 0;
            }
            double moveSpeed   = -gamepad1.left_stick_y;
            double strafeSpeed = gamepad1.left_stick_x;
            double speedSetter = 1;

            //calculating how to move
            front_left_power  = (moveSpeed + turnSpeed + strafeSpeed) * speedSetter;
            front_right_power = (moveSpeed - turnSpeed - strafeSpeed) * speedSetter;
            back_left_power   = (moveSpeed + turnSpeed - strafeSpeed) * speedSetter;
            back_right_power  = (moveSpeed - turnSpeed + strafeSpeed) * speedSetter;

            // Set motor powers
            FrontRight.setPower(front_right_power);
            FrontLeft.setPower(front_left_power);
            BackRight.setPower(back_right_power);
            BackLeft.setPower(back_left_power);
//
//            // intake arm
//
//            if (gamepad2.dpad_up) {
//                IntakeArmTicks += 10;
//                IntakeArm.setPower(1);
//
//
//            } else if (gamepad2.dpad_down) {
//                IntakeArmTicks -= 10;
//                IntakeArm.setPower(-1);
//            }
//
//            if (gamepad2.y) {
//                OuttakeArmTicks += 10;
//
//            } else if (gamepad2.a) {
//                OuttakeArmTicks -= 10;
//            }
//
//            if (gamepad2.left_bumper) {
//                IntakeRoller.setPower(1);
//
//            } else if (gamepad2.left_trigger != 0) {
//                IntakeRoller.setPower(-1);
//            } else {
//                IntakeRoller.setPower(0);
//            }
//
//            if (gamepad2.right_bumper) {
//                pivot += .1;
//
//            } else if (gamepad2.right_trigger != 0) {
//                pivot -= 1;
//            }


            //Ascent program to bring the ascent down if we make a mistake
            if (gamepad2.right_stick_y != 0) {
                RightAscent.setPower(gamepad2.right_stick_y);
            } else {
                RightAscent.setPower(0);
            }
            if (gamepad2.left_stick_y != 0) {
                LeftAscent.setPower(-gamepad2.left_stick_y);
            } else {
                LeftAscent.setPower(0);
            }

//            OuttakeSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            IntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//
//            IntakeArm.setTargetPosition(IntakeArmTicks);
//            OuttakeSlides.setTargetPosition(OuttakeArmTicks);
//            IntakePivot.setPosition(pivot);


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
