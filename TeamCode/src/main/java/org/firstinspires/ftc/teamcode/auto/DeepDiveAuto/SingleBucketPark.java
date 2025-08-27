package org.firstinspires.ftc.teamcode.auto.DeepDiveAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "SingleBucketPark", preselectTeleOp = "NewDrive")
public class SingleBucketPark extends LinearOpMode {

    private DcMotor BackLeft;
    private DcMotor BackRight;
    private DcMotor FrontLeft;
    private DcMotor FrontRight;
    private DcMotor OuttakeSlides;
    private DcMotor IntakeArm;
    private Servo OuttakePivot;


    @Override
    public void runOpMode() {
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        OuttakeSlides = hardwareMap.get(DcMotor.class, "OuttakeSlides");
        IntakeArm = hardwareMap.get(DcMotor.class, "IntakeArm");
        OuttakePivot = hardwareMap.get(Servo.class, "OuttakePivot");

        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        OuttakeSlides.setDirection(DcMotor.Direction.FORWARD);
        IntakeArm.setDirection(DcMotor.Direction.REVERSE);
        IntakeArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        OuttakeSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        OuttakeSlides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        IntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        OuttakeSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        IntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        IntakeArm.setPower(0);
        OuttakeSlides.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);


        waitForStart();
        if (opModeIsActive()) {

            // strafe right slightly
            BackLeft.setTargetPosition(450);
            BackRight.setTargetPosition(-450);
            FrontLeft.setTargetPosition(-450);
            FrontRight.setTargetPosition(450);
            BackLeft.setPower(0.5);
            BackRight.setPower(0.5);
            FrontLeft.setPower(0.5);
            FrontRight.setPower(0.5);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // move outtake mechanism
            OuttakePivot.setPosition(0);
            IntakeArm.setTargetPosition(0);
            IntakeArm.setPower(1);
            IntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            OuttakeSlides.setTargetPosition(2300);
            OuttakeSlides.setPower(1);
            OuttakeSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sleep(1000);

            // drive fwd to basket
            move_fwd(-1350);
            sleep(2500);

            // pivot to the right
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(-1000);
            BackRight.setTargetPosition(0);
            FrontLeft.setTargetPosition(-1000);
            FrontRight.setTargetPosition(0);
            BackLeft.setPower(0.5);
            BackRight.setPower(0.5);
            FrontLeft.setPower(0.5);
            FrontRight.setPower(0.5);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sleep(1000);

            // drop sample in top basket
            OuttakePivot.setPosition(0.5);
            sleep(1000);
            OuttakePivot.setPosition(0);
            sleep(1000);

            // pivot to left
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(950);
            BackRight.setTargetPosition(0);
            FrontLeft.setTargetPosition(950);
            FrontRight.setTargetPosition(0);
            BackLeft.setPower(0.5);
            BackRight.setPower(0.5);
            FrontLeft.setPower(0.5);
            FrontRight.setPower(0.5);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sleep(2000);

            // lower outtake mechanism
            OuttakeSlides.setTargetPosition(0);
            OuttakeSlides.setPower(1);
            OuttakeSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // drive back to park
            move_fwd(4000);
            sleep(4000);

            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(-450);
            BackRight.setTargetPosition(450);
            FrontLeft.setTargetPosition(450);
            FrontRight.setTargetPosition(-450);
            BackLeft.setPower(0.5);
            BackRight.setPower(0.5);
            FrontLeft.setPower(0.5);
            FrontRight.setPower(0.5);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);


            while (opModeIsActive()) {
            }
        }
    }

    private void move_fwd(int distance) {
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setTargetPosition(distance);
        BackRight.setTargetPosition(distance);
        FrontLeft.setTargetPosition(distance);
        FrontRight.setTargetPosition(distance);
        BackLeft.setPower(0.5);
        BackRight.setPower(0.5);
        FrontLeft.setPower(0.5);
        FrontRight.setPower(0.5);
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}