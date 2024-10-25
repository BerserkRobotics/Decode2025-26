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
    boolean gamepad2_y_toggle = true;
    //Position for slides and claw for samples/specimens
    int SpecimenPlaceSlideTicks    = 450;
    int TopBucketSlideTicks        = 1125; //actually low bucket -- need to test
    int PickupSlideTicks           = 10;
    double ClawPositionPivotOpen   = 0.65;
    double ClawPositionPivotClosed = 0.25;
    double ClawPositionClosed      = 0.45;
    double ClawPositionOpen        = 0.75;
    boolean gamepad2_y_Last_press  = false;
    boolean gamepad2_b_Last_press  = false;
    boolean gamepad2_a_Last_press  = false;
    int ClawslidesV                = 0;
    boolean y_toggle               = false;

    //Position for ascent slides for hanging
    int RightAscent_1st_Pos = 3000;
//    int RightAscent_2nd_Pos = 0;
    int LeftAscent_1st_Pos  = 3000;
//    int LeftAscent_2nd_Pos  = 0;

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
//        RightAscent.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        RightAscent.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //setting left ascent behaviors
        LeftAscent.setDirection(DcMotorSimple.Direction.FORWARD);
        LeftAscent.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        LeftAscent.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        LeftAscent.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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
            } else if (!gamepad2_b_toggle) {
                ClawGrab.setPosition(ClawPositionOpen);
            }
            if (gamepad2_y_toggle && !y_toggle
            ) {
                ClawPivot.setPosition(ClawPositionPivotClosed + .05);
                sleep(150);
                ClawPivot.setPosition(ClawPositionPivotClosed + .1);
                sleep(150);
                ClawPivot.setPosition(ClawPositionPivotClosed + .15);
                sleep(150);
                ClawPivot.setPosition(ClawPositionPivotClosed + .20);
                sleep(150);
                ClawPivot.setPosition(ClawPositionPivotClosed + .25);
                sleep(150);
                ClawPivot.setPosition(ClawPositionPivotClosed + .3);
                sleep(150);
                ClawPivot.setPosition(ClawPositionPivotClosed + .35);
                sleep(150);
                ClawPivot.setPosition(ClawPositionPivotClosed + .40);
                y_toggle = true;
            }
            if (!gamepad2_y_toggle) {
                ClawPivot.setPosition(ClawPositionPivotClosed);
                y_toggle = false;
            }
            // Setting toggle for using Claw
            if (gamepad2.b && !gamepad2_b_Last_press) {
                gamepad2_b_toggle = !gamepad2_b_toggle;
                sleep(100);
            }
            if (gamepad2.y && !gamepad2_y_Last_press)  {
                gamepad2_y_toggle = !gamepad2_y_toggle;
                sleep(100);
            }
            if (gamepad2.a && !gamepad2_a_Last_press) {
                ClawslidesV += 1;
            } else if (ClawslidesV == 3) {
                ClawslidesV = 0;
            }
            gamepad2_b_Last_press = gamepad2.b;
            gamepad2_y_Last_press = gamepad2.y;
            gamepad2_a_Last_press = gamepad2.a;

//            moving slides to the positions for top bucket and Specimen placing
            if (ClawslidesV == 0) {
                ClawSlides.setTargetPosition(PickupSlideTicks);
                ClawSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                ClawSlides.setPower(1);
            } else if (ClawslidesV == 2) {
                ClawSlides.setTargetPosition(SpecimenPlaceSlideTicks);
                ClawSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                ClawSlides.setPower(1);
            } else if (ClawslidesV == 1) {
                ClawSlides.setTargetPosition(TopBucketSlideTicks);
                ClawSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                ClawSlides.setPower(1);
            }



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
