package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "servotest")
public class servotest extends LinearOpMode {

    //Intake
    private Servo IntakeClaw;
    //TODO: ADD POSITION VARIABLE, TEST CLAW SERVOS
    double IntakeClawPos = 0;

    @Override
    public void runOpMode() {

        //Initialize Intake
        IntakeClaw = hardwareMap.get(Servo.class, "IntakeClaw");

        //telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {

            if (gamepad2.dpad_up) {
                IntakeClawPos += .1;
                IntakeClaw.setPosition(IntakeClawPos);
            } else if (gamepad2.dpad_down) {
                IntakeClawPos -= .1;
                IntakeClaw.setPosition(IntakeClawPos);
            }


            // Update telemetry data
            telemetry.addData("Status", "Running");
            telemetry.addData("dpad_up: ", gamepad2.dpad_up);
            telemetry.addData("dpad_down: ", gamepad2.dpad_down);
            telemetry.addData("claw position: ", IntakeClawPos);


            telemetry.update();
        }
    }
}