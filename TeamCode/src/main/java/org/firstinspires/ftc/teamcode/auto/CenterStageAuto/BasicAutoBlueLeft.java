package org.firstinspires.ftc.teamcode.auto.CenterStageAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "BasicAutoBlueLeft")
//, group = ".Main Blue Auto")
public class BasicAutoBlueLeft extends LinearOpMode {

    private DcMotor ArmMotor;
    private DcMotor BackLeft;
    private DcMotor BackRight;
    private DcMotor FrontLeft;
    private DcMotor FrontRight;
    private Servo ClawServo;

    /**
     * This function is executed when this OpMode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        ArmMotor = hardwareMap.get(DcMotor.class, "ArmMotor");
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        ClawServo = hardwareMap.get(Servo.class, "ClawServo");

        // Put initialization blocks here.
        ArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ArmMotor.setDirection(DcMotor.Direction.FORWARD);
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
            BackLeft.setPower(0.4);
            BackRight.setPower(0.4);
            FrontLeft.setPower(0.4);
            FrontRight.setPower(0.4);
            sleep(1500);

            //bk
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(-700);
            BackRight.setTargetPosition(-700);
            FrontLeft.setTargetPosition(-700);
            FrontRight.setTargetPosition(-700);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(0.6);
            BackRight.setPower(0.6);
            FrontLeft.setPower(0.6);
            FrontRight.setPower(0.6);
            sleep(1000);

            //strafe lft
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(1550);
            BackRight.setTargetPosition(-1550);
            FrontLeft.setTargetPosition(-1550);
            FrontRight.setTargetPosition(1550);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(0.6);
            BackRight.setPower(0.6);
            FrontLeft.setPower(0.6);
            FrontRight.setPower(0.6);
            sleep(2000);

            //fwd
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(500);
            BackRight.setTargetPosition(500);
            FrontLeft.setTargetPosition(500);
            FrontRight.setTargetPosition(500);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(0.6);
            BackRight.setPower(0.6);
            FrontLeft.setPower(0.6);
            FrontRight.setPower(0.6);
            sleep(1000);

            //turn to face bkboard
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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

            //cls claw
            ClawServo.setPosition(0);
            sleep(1000);

            //arm up
            ArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ArmMotor.setTargetPosition(800);
            ArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ArmMotor.setPower(0.5);
            sleep(1000);

            //open clw
            ClawServo.setPosition(0.12);
            sleep(1000);

            //arm dwn
            ArmMotor.setTargetPosition(0);
            ArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ArmMotor.setPower(0.3);


            while (opModeIsActive()) {
                // Put loop blocks here.
                telemetry.update();
            }
        }
    }
}