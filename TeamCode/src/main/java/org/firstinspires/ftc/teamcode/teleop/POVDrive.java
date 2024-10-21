package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "POVDrive")
public class POVDrive extends LinearOpMode {
    //Motors and Servo initialization
    private DcMotor FrontRight;
    private DcMotor BackRight;
    private DcMotor FrontLeft;
    private DcMotor BackLeft;
    private DcMotor RightAscent;
    private DcMotor LeftAscent;
    private DcMotor ClawSlides;
    private Servo ClawGrab;
    private Servo ClawPivot;

    //opening and closing initialization
    boolean gamepad2_b_toggle = true;

    //Position for slides and claw for samples/specimens
    int SpecimenPlaceSlideTicks    = 100;
    int TopBucketSlideTicks        = 1000;
    int PickupSlideTicks           = 1;
    double ClawPositionPivotOpen   = .65;
    double ClawPositionPivotClosed = 0.4;
    double ClawPositionClosed      = 0.45;
    double ClawPositionOpen        = 0.75;

    //Position for ascent slides for hanging
    int RightAscent_1st_Pos = 2;
    int RightAscent_2nd_Pos = 9;
    int LeftAscent_1st_Pos  = 2;
    int LeftAscent_2nd_Pos  = 9;

    @Override
    public void runOpMode() {
        // Initialize motors
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");

        RightAscent = hardwareMap.get(DcMotor.class,"RightAscent");
        LeftAscent = hardwareMap.get(DcMotor.class,"LeftAscent");

        ClawSlides = hardwareMap.get(DcMotor.class, "ClawSlides");
        ClawGrab = hardwareMap.get(Servo.class,"ClawGrab");
        ClawPivot = hardwareMap.get(Servo.class,"ClawPivot");

        // Set motor directions
        FrontRight.setDirection(DcMotor.Direction.REVERSE);
        BackRight.setDirection(DcMotor.Direction.REVERSE);
        FrontLeft.setDirection(DcMotor.Direction.FORWARD);
        BackLeft.setDirection(DcMotor.Direction.FORWARD);

        //setting right ascent behaviors
        RightAscent.setDirection(DcMotorSimple.Direction.FORWARD);
        RightAscent.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RightAscent.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RightAscent.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //setting left ascent behaviors
        LeftAscent.setDirection(DcMotorSimple.Direction.FORWARD);
        LeftAscent.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LeftAscent.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LeftAscent.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //setting claw slides behavior
        ClawSlides.setDirection(DcMotorSimple.Direction.FORWARD);
        ClawSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ClawSlides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ClawSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        // Set zero power behavior
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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
                turnSpeed = 1;
            } else if (gamepad1.right_bumper) {
                turnSpeed = -1;
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



            //Moving Claw to grab and then pull up and to open and go down
            if (gamepad2_b_toggle) {
                ClawGrab.setPosition(ClawPositionClosed);
                sleep(100);
                ClawPivot.setPosition(ClawPositionPivotClosed);
            }
            if (!gamepad2_b_toggle) {
                ClawGrab.setPosition(ClawPositionOpen);
            }
            if (!gamepad2_b_toggle && ClawSlides.getTargetPosition() == 10 && !ClawSlides.isBusy()) {
                ClawPivot.setPosition(ClawPositionPivotOpen);
            }
            // Setting toggle for using Claw
            if (gamepad2.b) {
                gamepad2_b_toggle = !gamepad2_b_toggle;
                sleep(100);
            }

            //moving slides to the positions for top bucket and Specimen placing
            if (gamepad2.a) {
                ClawSlides.setTargetPosition(PickupSlideTicks);
                ClawSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                ClawSlides.setPower(1);
            } else if (gamepad2.x) {
                ClawSlides.setTargetPosition(SpecimenPlaceSlideTicks);
                ClawSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                ClawSlides.setPower(1);
            } else if (gamepad2.y) {
                ClawSlides.setTargetPosition(TopBucketSlideTicks);
                ClawSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                ClawSlides.setPower(1);
            }



            //Ascent program to bring the ascent down if we make a mistake
            if (gamepad2.dpad_down) {
                RightAscent.setTargetPosition(10);
                RightAscent.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RightAscent.setPower(1);
                LeftAscent.setTargetPosition(10);
                LeftAscent.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                LeftAscent.setPower(1);
            }
            //Ascent Program to extend the slides and pull us in automatically
            if (gamepad2.dpad_up) {
                RightAscent.setTargetPosition(RightAscent_1st_Pos);
                RightAscent.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RightAscent.setPower(1);
                LeftAscent.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                LeftAscent.setTargetPosition(LeftAscent_1st_Pos);
                LeftAscent.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                LeftAscent.setPower(1);
                sleep(3000);
                RightAscent.setTargetPosition(RightAscent_2nd_Pos);
                RightAscent.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RightAscent.setPower(1);
                LeftAscent.setTargetPosition(LeftAscent_2nd_Pos);
                LeftAscent.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                LeftAscent.setPower(1);
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
