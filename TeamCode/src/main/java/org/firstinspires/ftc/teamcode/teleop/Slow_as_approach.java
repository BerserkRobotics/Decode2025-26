package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Slow_as_approach")
public class Slow_as_approach extends LinearOpMode {

    private DcMotor FrontRight;
    private DcMotor BackRight;
    private DcMotor FrontLeft;
    private DcMotor BackLeft;

    private final ElapsedTime intakeTimer = new ElapsedTime();
    //intakeState = IntakeState.RESTING;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() throws InterruptedException {
        double Launch_Angle;

        FrontRight = hardwareMap.get(DcMotor.class, "Front Right");
        BackRight = hardwareMap.get(DcMotor.class, "Back Right");
        FrontLeft = hardwareMap.get(DcMotor.class, "Front Left");
        BackLeft = hardwareMap.get(DcMotor.class, "Back Left");
        //rangefinder

        // Put initialization blocks here.
        waitForStart();
        FrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        BackRight.setDirection(DcMotorSimple.Direction.FORWARD);
        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

    }
}
