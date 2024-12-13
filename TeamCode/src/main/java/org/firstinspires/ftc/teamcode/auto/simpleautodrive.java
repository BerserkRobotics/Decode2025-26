package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "simpleautodrive")
public class simpleautodrive extends LinearOpMode {

  private DcMotor BackLeft;
  private DcMotor BackRight;
  private DcMotor FrontLeft;
  private DcMotor FrontRight;

  @Override
  public void runOpMode() {
    BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
    BackRight = hardwareMap.get(DcMotor.class, "BackRight");
    FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
    FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");

    BackLeft.setDirection(DcMotor.Direction.REVERSE);
    BackRight.setDirection(DcMotor.Direction.FORWARD);
    FrontLeft.setDirection(DcMotor.Direction.REVERSE);
    FrontRight.setDirection(DcMotor.Direction.FORWARD);
    BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    BackLeft.setPower(0);
    BackRight.setPower(0);
    FrontLeft.setPower(0);
    FrontRight.setPower(0);
    waitForStart();
    if (opModeIsActive()) {
      // Put run blocks here.
      while (opModeIsActive()) {
        // Put loop blocks here.
        move_right(1350);
      }
    }
  }

  private void move_right(int distance) {
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
