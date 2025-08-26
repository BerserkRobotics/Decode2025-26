package org.firstinspires.ftc.teamcode.teleop.old;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

//TODO: TEST TEST TEST!!! edit intakearm, intakepivot, EDIT OUTTAKE PIVOT, etc???

@TeleOp(name = "POVDrive")
public class POVDrive extends LinearOpMode {
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

    //TODO: edit uses of these
    //opening and closing initialization
    boolean gamepad2_b_toggle = true;
    boolean gamepad2_y_toggle = true;
    boolean gamepad2_x_toggle = true;


    //TODO: edit these values
    //OUTTAKE
    //Position for slides and claw for samples/specimens
    int SpecimenPlaceSlideTicks    = 450;
    int TopBucketSlideTicks        = 1125; //actually low bucket -- need to test
    int PickupSlideTicks           = 10;
    double OuttakePivotUp          = .5;
    double OuttakePivotDown        = 1;

    //TODO: edit these values
    //TODO: also intake pos in and out might be swapped...
    //INTAKE
    double IntakePositionPivotIn = 0.25;
    double IntakePositionPivotOut = 0.65;
    int IntakeArmTicksUp = 100;
    int IntakeArmTicksDown = -100;

    boolean gamepad2_y_Last_press  = false;
    boolean gamepad2_b_Last_press  = false;
    boolean gamepad2_a_Last_press  = false;
    boolean gamepad2_x_Last_press  = false;
    int OuttakeSlidesV = 0;
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
        OuttakeSlides.setDirection(DcMotorSimple.Direction.FORWARD);
        OuttakeSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //OuttakeSlides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //OuttakeSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //TODO: edit direction
        //setting intake arm
        IntakeArm.setDirection(DcMotorSimple.Direction.FORWARD);
        IntakeArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //IntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //IntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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

            // Setting toggle for using
            // intake roller
            if (gamepad2.b && !gamepad2_b_Last_press) {
                gamepad2_b_toggle = !gamepad2_b_toggle;
                sleep(100);
            }
            // intake pivot
            if (gamepad2.y && !gamepad2_y_Last_press)  {
                gamepad2_y_toggle = !gamepad2_y_toggle;
                sleep(100);
            }
            // intake arm
            if (gamepad2.x && !gamepad2_x_Last_press) {
                gamepad2_x_toggle = !gamepad2_x_Last_press;
                sleep(100);
            }
            // outtake slides
            if (gamepad2.a && !gamepad2_a_Last_press) {
                OuttakeSlidesV += 1;
            } else if (OuttakeSlidesV == 3) {
                OuttakeSlidesV = 0;
            }

            gamepad2_b_Last_press = gamepad2.b;
            gamepad2_y_Last_press = gamepad2.y;
            gamepad2_a_Last_press = gamepad2.a;
            gamepad2_x_Last_press = gamepad2.x;

            // TODO: edit power
            // Intake arm up and down
            if (gamepad2_x_toggle) {
                IntakeArm.setTargetPosition(IntakeArmTicksDown);
                IntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                IntakeArm.setPower(.6);
                IntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            } else if (!gamepad2_x_toggle) {
                IntakeArm.setTargetPosition(IntakeArmTicksUp);
                IntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                IntakeArm.setPower(.6);
                IntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            } else {
                IntakeArm.setPower(0);
            }

            //TODO: edit this because need to test with active intake
            //Moving intake to roll in and out

            if (gamepad2_b_toggle) { //in
                IntakeRoller.setPower(1);
            } else if (!gamepad2_b_toggle) { //out
                IntakeRoller.setPower(-1);
            } else {
                IntakeRoller.setPower(0);
            }


            if (gamepad2_y_toggle && !y_toggle) {
                IntakePivot.setPosition(IntakePositionPivotIn);
                y_toggle = true;
            }
            if (!gamepad2_y_toggle) {
                IntakePivot.setPosition(IntakePositionPivotOut);
                y_toggle = false;
            }



            //moving slides to the positions for top bucket and Specimen placing
            if (OuttakeSlidesV == 0) {
                OuttakeSlides.setTargetPosition(PickupSlideTicks);
                OuttakeSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                OuttakeSlides.setPower(1);
                sleep(2000);
                OuttakePivot.setPosition(OuttakePivotDown);
                sleep(2000);
                OuttakePivot.setPosition(OuttakePivotUp);
            } else if (OuttakeSlidesV == 2) {
                OuttakeSlides.setTargetPosition(SpecimenPlaceSlideTicks);
                OuttakeSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                OuttakeSlides.setPower(1);
                sleep(2000);
                OuttakePivot.setPosition(OuttakePivotDown);
                sleep(2000);
                OuttakePivot.setPosition(OuttakePivotUp);
            } else if (OuttakeSlidesV == 1) {
                OuttakeSlides.setTargetPosition(TopBucketSlideTicks);
                OuttakeSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                OuttakeSlides.setPower(1);
                sleep(2000);
                OuttakePivot.setPosition(OuttakePivotDown);
                sleep(2000);
                OuttakePivot.setPosition(OuttakePivotUp);
            }

            // lifts/drops outtake bucket in case
            if (gamepad2.dpad_up) {
                OuttakePivot.setPosition(OuttakePivotUp);
            } else if (gamepad2.dpad_down) {
                OuttakePivot.setPosition(OuttakePivotDown);
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
