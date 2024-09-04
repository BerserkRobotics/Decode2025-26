package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);

        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FrontRight.setPower(0);
        BackRight.setPower(0);
        FrontLeft.setPower(0);
        BackLeft.setPower(0);

        double front_left_power;
        double front_right_power;
        double back_left_power;
        double back_right_power;

        String run_type = "pov";


        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            if(gamepad1.dpad_left) {
                run_type = "pov";
            } else if(gamepad1.dpad_right) {
                run_type = "tank";
            }

            front_left_power = 0;
            front_right_power = 0;
            back_left_power = 0;
            back_right_power = 0;

            switch(run_type) {

                case "pov": {
                    int strafeDirection = 1; // negative or positive
                    int turnDirection = 1; // neg or pos
                    if (gamepad1.left_bumper) {
                        turnDirection = 1;
                    } else if (gamepad1.right_bumper) {
                        turnDirection = -1;
                    }
                    double moveSpeed = -gamepad1.left_stick_y;
                    double turnSpeed = turnDirection * 0.6;
                    double strafeSpeed = strafeDirection * gamepad1.left_stick_x;

                    front_left_power  = moveSpeed + turnSpeed + strafeSpeed;
                    front_right_power = moveSpeed - turnSpeed - strafeSpeed;
                    back_left_power   = moveSpeed + turnSpeed - strafeSpeed;
                    back_right_power  = moveSpeed - turnSpeed + strafeSpeed;
                    break;
                }

                case "tank": {
                    front_left_power = -gamepad1.left_stick_y;
                    front_right_power = -gamepad1.right_stick_y;
                    back_left_power = -gamepad1.left_stick_y;
                    back_right_power = -gamepad1.right_stick_y;
                    break;
                }
                default: {
                    telemetry.addData("ERROR", "Run type not found");
                }
            }
            double speedChange1;
            if (gamepad1.left_bumper) {
                speedChange1 = 0.2;
            } else if (gamepad1.right_bumper) {
                speedChange1 = 1;
            } else {
                speedChange1 = 0.6;
            }

            front_left_power *= speedChange1;
            front_right_power *= speedChange1;
            back_left_power *= speedChange1;
            back_right_power *= speedChange1;


            FrontRight.setPower(front_right_power);
            FrontLeft.setPower(front_left_power);
            BackRight.setPower(back_right_power);
            BackLeft.setPower(back_left_power);


            telemetry.addData("Status", "Running");
            telemetry.addData("Front Left", front_left_power);
            telemetry.addData("Front Right", front_right_power);
            telemetry.addData("Back Left", back_left_power);
            telemetry.addData("Back Right", back_right_power);
            telemetry.addData("Run Type", run_type);
            telemetry.update();


        }
    }
}