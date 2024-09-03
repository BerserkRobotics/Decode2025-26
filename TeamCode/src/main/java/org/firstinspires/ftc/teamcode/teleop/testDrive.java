package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "testDrive")
public class testDrive extends LinearOpMode {

    private DcMotor FrontRight;
    private DcMotor BackRight;
    private DcMotor FrontLeft;
    private DcMotor BackLeft;


    /**
     * This function is executed when this OpMode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        //motors
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");

        //servos


        //drive motors
        FrontRight.setDirection(DcMotor.Direction.REVERSE);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        FrontLeft.setDirection(DcMotor.Direction.FORWARD);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);

        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FrontRight.setPower(0);
        BackRight.setPower(0);
        FrontLeft.setPower(0);
        BackLeft.setPower(0);

        //intake


        //slide motors

        //arm + claw servos


        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                FrontRight.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x) / 1);
                BackRight.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x) / 1);
                FrontLeft.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x) / 1);
                BackLeft.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x) / 1);

                while (gamepad1.right_bumper) {
                    FrontRight.setPower(0.5);
                    BackRight.setPower(0.5);
                    FrontLeft.setPower(-0.5);
                    BackLeft.setPower(-0.5);
                }
                while (gamepad1.left_bumper) {
                    FrontRight.setPower(-0.5);
                    BackRight.setPower(-0.5);
                    FrontLeft.setPower(0.5);
                    BackLeft.setPower(0.5);
                }

            }

            telemetry.update();
        }
    }
}