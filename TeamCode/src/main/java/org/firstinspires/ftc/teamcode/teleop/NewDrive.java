package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "NewDrive")
public class NewDrive extends LinearOpMode {
    //Wheels
    private DcMotor FrontRight;
    private DcMotor BackRight;
    private DcMotor FrontLeft;
    private DcMotor BackLeft;

    //Hang
    private DcMotor RightAscent;
    private DcMotor LeftAscent;

    //Intake
    private CRServo IntakeRoller;
    private Servo IntakePivot;
    private DcMotor IntakeArm;

    //outtake
    private DcMotor OuttakeSlides;
    private Servo OuttakePivot;


    // INTAKE values
    int IntakeArmTicks = 0;
    int OuttakeArmTicks = 0;

    // Initializing drive variables
    double front_left_power  = 0;
    double front_right_power = 0;
    double back_left_power   = 0;
    double back_right_power  = 0;
    double speedSetter = 1;
    double turnSpeed = 0;
    double intakepivot = 0;

    @Override
    public void runOpMode() {
        // Initialize Wheels
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");

        //Initialize Hang
        RightAscent = hardwareMap.get(DcMotor.class,"RightAscent");
        LeftAscent = hardwareMap.get(DcMotor.class,"LeftAscent");

        //Initialize Intake
        IntakeArm = hardwareMap.get(DcMotor.class,"IntakeArm");
        IntakeRoller = hardwareMap.get(CRServo.class,"IntakeRoller");
        IntakePivot = hardwareMap.get(Servo.class,"IntakePivot");

        //Initialize Outtake
        OuttakeSlides = hardwareMap.get(DcMotor.class, "OuttakeSlides");
        OuttakePivot = hardwareMap.get(Servo.class,"OuttakePivot");


        // Set wheel motor directions
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);

        //Set hang motor direction
        RightAscent.setDirection(DcMotorSimple.Direction.FORWARD);
        LeftAscent.setDirection(DcMotorSimple.Direction.FORWARD);

        //set intake/Outake motor direction
        IntakeArm.setDirection(DcMotorSimple.Direction.REVERSE);
        OuttakeSlides.setDirection(DcMotorSimple.Direction.REVERSE);







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
        IntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        IntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        OuttakeSlides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        OuttakeSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);



        //setting telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            double moveSpeed   = -gamepad1.left_stick_y;
            double strafeSpeed = gamepad1.left_stick_x;

            //setting Turn
            if (gamepad1.left_bumper) {
                turnSpeed = -1;
            } else if (gamepad1.right_bumper) {
                turnSpeed = 1;
            } else {
                turnSpeed = 0;
            }

            //calculating how to move
            front_left_power  = (moveSpeed + turnSpeed + strafeSpeed) * speedSetter;
            front_right_power = (moveSpeed - turnSpeed - strafeSpeed) * speedSetter;
            back_left_power   = (moveSpeed + turnSpeed - strafeSpeed) * speedSetter;
            back_right_power  = (moveSpeed - turnSpeed + strafeSpeed) * speedSetter;

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

            // Set motor powers
            FrontRight.setPower(front_right_power);
            FrontLeft.setPower(front_left_power);
            BackRight.setPower(back_right_power);
            BackLeft.setPower(back_left_power);

            //setting position
            IntakeArm.setTargetPosition(IntakeArmTicks);
            OuttakeSlides.setTargetPosition(OuttakeArmTicks);
            IntakePivot.setPosition(intakepivot);

            //setting run to position
            OuttakeSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            IntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);


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
