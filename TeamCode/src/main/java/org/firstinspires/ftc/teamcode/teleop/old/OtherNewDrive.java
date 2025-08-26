package org.firstinspires.ftc.teamcode.teleop.old;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "OtherNewDrive")
public class OtherNewDrive extends LinearOpMode {
    //Wheels
    private DcMotor FrontRight;
    private DcMotor BackRight;
    private DcMotor FrontLeft;
    private DcMotor BackLeft;
    //Hang
    private DcMotor RightAscent;
    private DcMotor LeftAscent;
    //Intake
    private Servo IntakeClaw;
    //private Servo IntakeRoller;
    private Servo IntakePivot;
    private DcMotor IntakeArm;
    //outtake
    private DcMotor OuttakeSlides;
    private Servo OuttakePivot;
    private Servo IntakeRoll;

    private TouchSensor Claw_Sensor;


    // Initializing drive variables
    double front_left_power = 0;
    double front_right_power = 0;
    double back_left_power = 0;
    double back_right_power = 0;
    double speedSetter = 1;
    double turnSpeed = 0;

    //initializing position values
    double OuttakePivotPosition = 0;
    double IntakeClawOpen = .8;
    double IntakeClawClose = .2;
    //double IntakeRollerPosition = 0.5;
    double IntakePivotPosition = 0.44;
    int OuttakeSlidesPosition = 0;
    int IntakeArmPosition = 0;
    boolean dpadup = false;
    boolean dpaddown = false;

    int IntakeToggle = 0;
    int OuttakeToggle = 0;
    boolean mode = true;
    boolean g2a = false;
    boolean g2y = false;
    boolean g2lb = false;
    boolean g2rb = false;
    boolean g2tleft_bumper = false;
    boolean g2tright_bumper = false;
    int variable = 0;


    @Override
    public void runOpMode() {
        // Initialize Wheels
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");

        //Initialize Hang
        RightAscent = hardwareMap.get(DcMotor.class, "RightAscent");
        LeftAscent = hardwareMap.get(DcMotor.class, "LeftAscent");

        //Initialize Intake
        IntakeArm = hardwareMap.get(DcMotor.class, "IntakeArm");
        IntakeClaw = hardwareMap.get(Servo.class, "IntakeClaw");
        //IntakeRoller = hardwareMap.get(Servo.class, "IntakeRoller");
        IntakePivot = hardwareMap.get(Servo.class, "IntakePivot");

        //Initialize Outtake
        OuttakeSlides = hardwareMap.get(DcMotor.class, "OuttakeSlides");
        OuttakePivot = hardwareMap.get(Servo.class, "OuttakePivot");


        Claw_Sensor = hardwareMap.get(TouchSensor.class, "Claw_Sensor");


        // Set wheel motor directions
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);

        //Set hang motor direction
        RightAscent.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftAscent.setDirection(DcMotorSimple.Direction.FORWARD);

        //set intake/Outtake motor direction
        IntakeArm.setDirection(DcMotorSimple.Direction.REVERSE);
        OuttakeSlides.setDirection(DcMotorSimple.Direction.FORWARD);

        // Set wheel zero power behavior
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set hang zero power behavior
        RightAscent.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LeftAscent.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set Intake/outtake zero power behavior
        IntakeArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        OuttakeSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //Setting Encoders for Intake/Outtake
        IntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        IntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        OuttakeSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        OuttakeSlides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Initialization
        OuttakeSlides.setTargetPosition(100);
        OuttakeSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        OuttakeSlides.setPower(1);

        OuttakePivot.setPosition(0.5);
        while (OuttakePivot.getPosition() != 0.5 && IntakePivot.getPosition() != 0.75) {

        }
        sleep(500);

        IntakeArm.setTargetPosition(1200);
        IntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        IntakeArm.setPower(1);



        //telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            double moveSpeed = -gamepad1.left_stick_y;
            double strafeSpeed = gamepad1.left_stick_x;
            if (gamepad1.right_trigger != 0) {
                speedSetter = 0.5;
            } else {
                speedSetter = 1;
            }
            //setting Turn
            if (gamepad1.left_bumper) {
                turnSpeed = -1;
            } else if (gamepad1.right_bumper) {
                turnSpeed = 1;
            } else {
                turnSpeed = 0;
            }

            //calculating how to move
            front_left_power = (moveSpeed + turnSpeed + strafeSpeed) * speedSetter;
            front_right_power = (moveSpeed - turnSpeed - strafeSpeed) * speedSetter;
            back_left_power = (moveSpeed + turnSpeed - strafeSpeed) * speedSetter;
            back_right_power = (moveSpeed - turnSpeed + strafeSpeed) * speedSetter;

            LeftAscent.setPower(-gamepad2.left_stick_y);
            RightAscent.setPower(-gamepad2.right_stick_y);


            if (gamepad2.dpad_right) {
                variable += 5;
            } else if (gamepad2.dpad_left) {
                variable -= 5;
            }
            if (gamepad2.dpad_up & !dpadup) {
                IntakeToggle += 1;
            } else if (gamepad2.dpad_down & !dpaddown) {
                IntakeToggle -= 1;
            }

            if (IntakeToggle > 2) {
                IntakeToggle = 2;
            } else if (IntakeToggle < 0) {
                IntakeToggle = 0;
            }


            if (IntakeToggle == 2) {
                IntakeArmPosition = 765 + variable;
                IntakePivotPosition = 0.2;
            } else if (IntakeToggle == 1) {
                IntakeArmPosition = 1200 + variable;
                IntakePivotPosition = 1;
            } else if (IntakeToggle == 0) {
                IntakeArmPosition = 1740 + variable;
                IntakePivotPosition = 1;
            }

            if (IntakeArm.getCurrentPosition() == 750 && IntakePivot.getPosition() == 0.55) {
                IntakeClaw.setPosition(IntakeClawOpen);
            }

            if (gamepad2.b) {
                OuttakePivotPosition = 0.5;
            }

            if (gamepad2.y & !g2y) {
                OuttakeSlidesPosition = 2300;
            } else if (gamepad2.a & !g2a) {
                OuttakeSlidesPosition = 0;
                OuttakePivotPosition = 0;
            }

            dpaddown = gamepad2.dpad_down;
            dpadup = gamepad2.dpad_up;
            g2a = gamepad2.a;
            g2y = gamepad2.y;


            if (gamepad2.x & !g2lb) {
                g2tleft_bumper = !g2tleft_bumper;
            }
            if (g2tleft_bumper) {
                IntakeRoll.setPosition(0.8);
            } else {
                IntakeRoll.setPosition(0.5);
            }

            if (gamepad2.right_bumper & !g2rb) {
                g2tright_bumper = !g2tright_bumper;
            }
            if (g2tright_bumper) {
                IntakeClaw.setPosition(IntakeClawClose);
            } else {
                IntakeClaw.setPosition(IntakeClawOpen);
            }

            g2rb = gamepad2.right_bumper;
            g2lb = gamepad2.x;

            // Set motor powers
            FrontRight.setPower(front_right_power);
            FrontLeft.setPower(front_left_power);
            BackRight.setPower(back_right_power);
            BackLeft.setPower(back_left_power);

            //setting position
            IntakeArm.setTargetPosition(IntakeArmPosition);
            OuttakeSlides.setTargetPosition(OuttakeSlidesPosition);
            IntakePivot.setPosition(IntakePivotPosition);
            OuttakePivot.setPosition(OuttakePivotPosition);

            //setting run to position
            OuttakeSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            IntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            OuttakeSlides.setPower(1);
            IntakeArm.setPower(0.75);
            // Update telemetry data
            telemetry.addData("Status", "Running");
            telemetry.addData("Front Left Power", front_left_power);
            telemetry.addData("Front Right Power", front_right_power);
            telemetry.addData("Pivot Position", IntakePivotPosition);
            telemetry.addData("Toggle", IntakeToggle);
            telemetry.addData("Target Position", IntakeArm.getTargetPosition());
            telemetry.addData("Position", IntakeArm.getCurrentPosition());
            telemetry.addData("dpad_up", gamepad2.dpad_up);
            telemetry.addData("dpad_down", gamepad2.dpad_down);
            telemetry.addData("Claw", IntakeClaw.getPosition());




            telemetry.update();
        }
    }
}
