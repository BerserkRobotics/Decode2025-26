package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "BasicAutoBlueRight", group = ".Main Auto")
public class BasicAutoBlueRight extends LinearOpMode {

    private DcMotor BackLeft;
    private DcMotor BackRight;
    private DcMotor FrontLeft;
    private DcMotor FrontRight;
    private Servo ClawServo;
    private DcMotor ArmMotor;

    /**
     * This function is executed when this OpMode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        ClawServo = hardwareMap.get(Servo.class, "ClawServo");
        ArmMotor = hardwareMap.get(DcMotor.class, "ArmMotor");

        // Put initialization blocks here.
        BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.REVERSE);
        FrontLeft.setDirection(DcMotor.Direction.FORWARD);
        FrontRight.setDirection(DcMotor.Direction.REVERSE);
        ClawServo.setPosition(0);
        waitForStart();
        if (opModeIsActive()) {

            //fwd
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(1300);
            BackRight.setTargetPosition(1300);
            FrontLeft.setTargetPosition(1300);
            FrontRight.setTargetPosition(1300);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(0.6);
            BackRight.setPower(0.6);
            FrontLeft.setPower(0.6);
            FrontRight.setPower(0.6);
            sleep(1500);

            //bk
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(-515);
            BackRight.setTargetPosition(-515);
            FrontLeft.setTargetPosition(-515);
            FrontRight.setTargetPosition(-515);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(0.6);
            BackRight.setPower(0.6);
            FrontLeft.setPower(0.6);
            FrontRight.setPower(0.6);
            sleep(1000);

            //strafe rt
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(-940);
            BackRight.setTargetPosition(940);
            FrontLeft.setTargetPosition(940);
            FrontRight.setTargetPosition(-940);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(0.6);
            BackRight.setPower(0.6);
            FrontLeft.setPower(0.6);
            FrontRight.setPower(0.6);
            sleep(1000);

            //fwd
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(2000);
            BackRight.setTargetPosition(2000);
            FrontLeft.setTargetPosition(2000);
            FrontRight.setTargetPosition(2000);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(0.6);
            BackRight.setPower(0.6);
            FrontLeft.setPower(0.6);
            FrontRight.setPower(0.6);
            sleep(2000);

            //strafe lft
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(5400);
            BackRight.setTargetPosition(-5400);
            FrontLeft.setTargetPosition(-5400);
            FrontRight.setTargetPosition(5400);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(0.6);
            BackRight.setPower(0.6);
            FrontLeft.setPower(0.6);
            FrontRight.setPower(0.6);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sleep(7000);

            //bk
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(-1450);
            BackRight.setTargetPosition(-1450);
            FrontLeft.setTargetPosition(-1450);
            FrontRight.setTargetPosition(-1450);
            BackLeft.setPower(0.6);
            BackRight.setPower(0.6);
            FrontLeft.setPower(0.6);
            FrontRight.setPower(0.6);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sleep(4000);

            //trn to face bkboard
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(-1000);
            BackRight.setTargetPosition(1000);
            FrontLeft.setTargetPosition(-1000);
            FrontRight.setTargetPosition(1000);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(0.6);
            BackRight.setPower(0.6);
            FrontLeft.setPower(0.6);
            FrontRight.setPower(0.6);
            sleep(1000);

            //make sure clw is clsd
            ClawServo.setPosition(0);
            sleep(1000);

            //arm up
            ArmMotor.setTargetPosition(800);
            ArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ArmMotor.setPower(0.5);
            sleep(3000);

            //drop pxl
            ClawServo.setPosition(0.12);


            while (opModeIsActive()) {
                // Put loop blocks here.
            }
            telemetry.update();
        }
    }
}