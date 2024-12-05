package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "testtest")
public class testtest extends LinearOpMode {

    //Intake
    private Servo IntakeRoller;
    private Servo IntakePivot;
    private DcMotor IntakeArm;
    //outtake
    private DcMotor OuttakeSlides;
    private Servo OuttakePivot;

    //initializing position values
    double OuttakePivotPosition = 0;
    double IntakeRollerPosition = 0.5;
    double IntakePivotPosition = 0.44;
    int OuttakeSlidesPosition = 0;
    int IntakeArmPosition = 0;
    boolean dpadup = false;
    boolean dpaddown = false;

    int IntakeToggle = 1;
    int OuttakeToggle = 0;
    boolean mode = true;
    boolean g2a = false;
    boolean g2y = false;



    @Override
    public void runOpMode() {

        //Initialize Intake
        IntakeArm = hardwareMap.get(DcMotor.class, "IntakeArm");
        IntakeRoller = hardwareMap.get(Servo.class, "IntakeRoller");
        IntakePivot = hardwareMap.get(Servo.class, "IntakePivot");

        //Initialize Outtake
        OuttakeSlides = hardwareMap.get(DcMotor.class, "OuttakeSlides");
        OuttakePivot = hardwareMap.get(Servo.class, "OuttakePivot");

        //set intake/Outake motor direction
        IntakeArm.setDirection(DcMotorSimple.Direction.REVERSE);
        OuttakeSlides.setDirection(DcMotorSimple.Direction.FORWARD);

        // Set Intake/outtake zero power behavior
        IntakeArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        OuttakeSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Setting Encoders for Intake/Outtake
        IntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        IntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        OuttakeSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        OuttakeSlides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        //telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            double moveSpeed = -gamepad1.left_stick_y;
            double strafeSpeed = gamepad1.left_stick_x;
            if (!gamepad2.right_stick_button) {
                mode = true;
            } else if (!gamepad2.left_stick_button) {
                mode = false;
            }

                if (gamepad2.dpad_up & !dpadup) {
                    IntakeToggle += 1;
                } else if (gamepad2.dpad_down & !dpaddown) {
                    IntakeToggle -= 1;
                }

                if (IntakeToggle > 2) {
                    IntakeToggle = 2;
                } else if (IntakeToggle < 0) {
                    IntakeToggle = 0;
                }

                if (IntakeToggle == 2) {
                    IntakeArmPosition = 500;
                    IntakePivotPosition = 0.4;
                } else if (IntakeToggle == 1) {
                    IntakeArmPosition = 1000;
                } else if (IntakeToggle == 0) {
                    IntakeArmPosition = 2050;
                    IntakePivotPosition = 0.45;
                }

                if (gamepad2.b) {
                    OuttakePivotPosition = 0;
                } else if (gamepad2.x) {
                    OuttakePivotPosition = 0.5;
                }

                if (gamepad2.y & !g2y) {
                    OuttakeToggle += 1;
                } else if (gamepad2.a & !g2a) {
                    OuttakeToggle -= 1;
                }

                if (OuttakeToggle > 1) {
                    OuttakeToggle = 1;
                } else if (IntakeToggle < 0) {
                    OuttakeToggle = 0;
                }

                if (OuttakeToggle == 0) {
                    OuttakeSlidesPosition = 250;
                } else if (OuttakeToggle == 1) {
                    OuttakeSlidesPosition = 2300;
                }
                dpaddown = gamepad2.dpad_down;
                dpadup = gamepad2.dpad_up;
                g2a = gamepad2.a;
                g2y = gamepad2.y;

                if (gamepad2.right_bumper) {
                    IntakeRollerPosition = 1;
                } else if (gamepad2.left_bumper) {
                    IntakeRollerPosition = 0;
                } else {
                    IntakeRollerPosition = 0.5;
                }
                IntakeRoller.setPosition(IntakeRollerPosition);
                //manual


                if (gamepad2.right_bumper) {
                    IntakeRollerPosition = 1;
                } else if (gamepad2.left_bumper) {
                    IntakeRollerPosition = 0;
                } else {
                    IntakeRollerPosition = 0.5;
                }

                if (IntakePivotPosition < 0) {
                    IntakePivotPosition = 0;
                } else if (IntakePivotPosition > 1) {
                    IntakePivotPosition = 1;
                } else if (gamepad2.right_trigger != 0) {
                    IntakePivotPosition += gamepad2.right_trigger * (0.005);
                } else if (gamepad2.left_trigger != 0) {
                    IntakePivotPosition -= gamepad2.left_trigger * (0.005);
                }

                if (gamepad2.dpad_up) {
                    IntakeArmPosition += 1;
                } else if (gamepad2.dpad_down) {
                    IntakeArmPosition -= 1;
                }

                if (OuttakePivotPosition < 0) {
                    OuttakePivotPosition = 0;
                } else if (OuttakePivotPosition > 1) {
                    OuttakePivotPosition = 1;
                    // up
                } else if (gamepad2.b) {
                    OuttakePivotPosition += 0.01;
                    // down
                } else if (gamepad2.x) {
                    OuttakePivotPosition -= 0.01;
                }

                if (gamepad2.y) {
                    OuttakeSlidesPosition += 10;
                } else if (gamepad2.a) {
                    OuttakeSlidesPosition -= 10;
                }
                IntakeRoller.setPosition(IntakeRollerPosition);

            }


            //setting position
            IntakeArm.setTargetPosition(IntakeArmPosition);
            OuttakeSlides.setTargetPosition(OuttakeSlidesPosition);
            IntakePivot.setPosition(IntakePivotPosition);
            OuttakePivot.setPosition(OuttakePivotPosition);

            //setting run to position
            OuttakeSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            IntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            OuttakeSlides.setPower(1);
            IntakeArm.setPower(0.75);
            // Update telemetry data
            telemetry.addData("Status", "Running");
            telemetry.addData("Pivot Position", IntakePivotPosition);
            telemetry.addData("Toggle", IntakeToggle);
            telemetry.addData("Target Position", IntakeArm.getTargetPosition());
            telemetry.addData("Position", IntakeArm.getCurrentPosition());
            telemetry.addData("dpad_up", gamepad2.dpad_up);
            telemetry.addData("dpad_down", gamepad2.dpad_down);


            telemetry.update();
        }
    }