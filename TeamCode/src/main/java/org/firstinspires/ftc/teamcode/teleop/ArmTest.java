package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ArmTest")
public class ArmTest extends LinearOpMode {
    private DcMotor IntakeArm;
    @Override
    public void runOpMode() {
        IntakeArm = hardwareMap.get(DcMotor.class,"IntakeArm");
        IntakeArm.setDirection(DcMotorSimple.Direction.REVERSE);
        IntakeArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        IntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        IntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        int IntakeArmPosition = 0;

        waitForStart();
        while (opModeIsActive()) {
            IntakeArmPosition += -gamepad2.left_stick_y * 1;
            IntakeArm.setTargetPosition(IntakeArmPosition);
            IntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            IntakeArm.setPower(1);


            telemetry.addData("Target Position",IntakeArm.getTargetPosition() );
            telemetry.addData("Position", IntakeArm.getCurrentPosition());

            telemetry.update();
        }
    }
}
