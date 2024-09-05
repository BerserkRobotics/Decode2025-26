/*
 * Copyright (c) 2021 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode.auto.CenterStageAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.auto.AprilTagDetectionPipeline;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@Autonomous(name = "LeftAuto2")
public class LeftAuto2 extends LinearOpMode
{
    //INTRODUCE VARIABLES HERE
    private DcMotor BackLeft;
    private DcMotor BackRight;
    private DcMotor FrontLeft;
    private DcMotor FrontRight;
    private DcMotor bottomMotor;
    private DcMotor topMotor;
    private Servo Rservoram;
    private Servo Lservoarm;
    private Servo LClaw;



    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.166;

    // Tag ID 1,2,3 from the 36h11 family
    /*EDIT IF NEEDED!!!*/

    int LEFT = 1;
    int MIDDLE = 2;
    int RIGHT = 3;

    AprilTagDetection tagOfInterest = null;

    @Override
    public void runOpMode()
    {

        BackLeft = hardwareMap.get(DcMotor.class, "Back Left");
        BackRight = hardwareMap.get(DcMotor.class, "Back Right");
        FrontLeft = hardwareMap.get(DcMotor.class, "Front Left");
        FrontRight = hardwareMap.get(DcMotor.class, "Front Right");
        bottomMotor = hardwareMap.get(DcMotor.class, "bottomMotor");
        topMotor = hardwareMap.get(DcMotor.class, "topMotor");

        Rservoram = hardwareMap.get(Servo.class, "Rservoram");
        Lservoarm = hardwareMap.get(Servo.class, "Lservoarm");
        LClaw = hardwareMap.get(Servo.class, "LClaw");


        LClaw.setDirection(Servo.Direction.FORWARD);
        Lservoarm.setDirection(Servo.Direction.FORWARD);
        Rservoram.setDirection(Servo.Direction.REVERSE);
        bottomMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        topMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        bottomMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        topMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setPower(0);
        BackRight.setPower(0);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });

        telemetry.setMsTransmissionInterval(50);


        //HARDWARE MAPPING HERE etc.


        /*
         * The INIT-loop:
         * This REPLACES waitForStart!
         */
        while (!isStarted() && !isStopRequested())
        {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            if(currentDetections.size() != 0)
            {
                boolean tagFound = false;

                for(AprilTagDetection tag : currentDetections)
                {
                    if(tag.id == LEFT || tag.id == MIDDLE || tag.id == RIGHT)
                    {
                        tagOfInterest = tag;
                        tagFound = true;
                        break;
                    }
                }

                if(tagFound)
                {
                    telemetry.addLine("Tag of interest is in sight! :)\n\nLocation data:");
                    tagToTelemetry(tagOfInterest);
                }
                else
                {
                    telemetry.addLine("Don't see tag of interest :(");

                    if(tagOfInterest == null)
                    {
                        telemetry.addLine("(The tag has never been seen)");
                    }
                    else
                    {
                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest);
                    }
                }

            }
            else
            {
                telemetry.addLine("Don't see tag of interest :(");

                if(tagOfInterest == null)
                {
                    telemetry.addLine("(The tag has never been seen)");
                }
                else
                {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest);
                }

            }

            telemetry.update();
            sleep(20);
        }





        if(tagOfInterest != null)
        {
            telemetry.addLine("Tag snapshot:\n");
            tagToTelemetry(tagOfInterest);
            telemetry.update();
        }
        else
        {
            telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
            telemetry.update();
        }

        //PUT AUTO CODE HERE (DRIVER PRESSED THE PLAY BUTTON!)
        if (tagOfInterest.id == 1) {

            //Grab preload
            telemetry.addData("Object Detected", "Circle");
            LClaw.setPosition(0.01);
            sleep(200);
            Lservoarm.setPosition(0.25);
            Rservoram.setPosition(0.25);
            sleep(100);

            //Forward
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(50);
            BackRight.setTargetPosition(50);
            FrontLeft.setTargetPosition(50);
            FrontRight.setTargetPosition(50);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(0.5);
            BackRight.setPower(0.5);
            FrontLeft.setPower(0.5);
            FrontRight.setPower(0.5);
            sleep(500);

            //Strafe left
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(2100);
            BackRight.setTargetPosition(-2100);
            FrontLeft.setTargetPosition(-2100);
            FrontRight.setTargetPosition(2100);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(3000);

            //Forward to medium junction
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(300);
            BackRight.setTargetPosition(300);
            FrontLeft.setTargetPosition(300);
            FrontRight.setTargetPosition(300);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);

            //Drop cone and reset arm FIX HEIGHTS!!!
            bottomMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            topMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            bottomMotor.setTargetPosition(1250);
            topMotor.setTargetPosition(1250);
            bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bottomMotor.setPower(1);
            topMotor.setPower(1);
            sleep(500);
            Lservoarm.setPosition(0.4);
            Rservoram.setPosition(0.4);
            sleep(500);
            LClaw.setPosition(0.06);
            sleep(500);

            //Diagonal strafe to cone stack
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(0);
            BackRight.setTargetPosition(-1300);
            FrontLeft.setTargetPosition(-1300);
            FrontRight.setTargetPosition(0);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);

            //Raise arm
            bottomMotor.setTargetPosition(850);
            topMotor.setTargetPosition(850);
            bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bottomMotor.setPower(1);
            topMotor.setPower(1);
            Lservoarm.setPosition(0);
            Rservoram.setPosition(0);
            LClaw.setPosition(0.11);
            sleep(1000);

            //Back to cone stack
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(-900);
            BackRight.setTargetPosition(-900);
            FrontLeft.setTargetPosition(-900);
            FrontRight.setTargetPosition(-900);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(0.8);
            BackRight.setPower(0.8);
            FrontLeft.setPower(0.8);
            FrontRight.setPower(0.8);
            sleep(2000);

            //Pick up cone 1
            LClaw.setPosition(0.01);
            sleep(500);
            Lservoarm.setPosition(0.25);
            Rservoram.setPosition(0.25);
            sleep(1000);

            //Forward
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(800);
            BackRight.setTargetPosition(800);
            FrontLeft.setTargetPosition(800);
            FrontRight.setTargetPosition(800);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Diagonal to medium
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(0);
            BackRight.setTargetPosition(1300);
            FrontLeft.setTargetPosition(1300);
            FrontRight.setTargetPosition(0);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Drop cone on junction and reset
            bottomMotor.setTargetPosition(850);
            topMotor.setTargetPosition(850);
            bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bottomMotor.setPower(1);
            topMotor.setPower(1);
            sleep(1500);
            Lservoarm.setPosition(0.4);
            Rservoram.setPosition(0.4);
            sleep(500);
            LClaw.setPosition(0.08);
            sleep(500);
            Lservoarm.setPosition(0);
            Rservoram.setPosition(0);
            sleep(500);

            //Diagonal to cone stack
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(0);
            BackRight.setTargetPosition(-1300);
            FrontLeft.setTargetPosition(-1300);
            FrontRight.setTargetPosition(0);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            bottomMotor.setTargetPosition(-850);
            topMotor.setTargetPosition(-850);
            bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bottomMotor.setPower(1);
            topMotor.setPower(1);
            Lservoarm.setPosition(0);
            Rservoram.setPosition(0);
            LClaw.setPosition(0.11);
            sleep(1000);

            //Back up to stack
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(-1000);
            BackRight.setTargetPosition(-1000);
            FrontLeft.setTargetPosition(-1000);
            FrontRight.setTargetPosition(-1000);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(2000);
            LClaw.setPosition(0.01);
            sleep(500);
            Lservoarm.setPosition(0.25);
            Rservoram.setPosition(0.25);
            sleep(1000);

            //Forward
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(800);
            BackRight.setTargetPosition(800);
            FrontLeft.setTargetPosition(800);
            FrontRight.setTargetPosition(800);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Diagonal to medium
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(0);
            BackRight.setTargetPosition(1300);
            FrontLeft.setTargetPosition(1300);
            FrontRight.setTargetPosition(0);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Drop and reset
            bottomMotor.setTargetPosition(750);
            topMotor.setTargetPosition(750);
            bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bottomMotor.setPower(1);
            topMotor.setPower(1);
            sleep(1500);
            Lservoarm.setPosition(0.4);
            Rservoram.setPosition(0.4);
            sleep(500);
            LClaw.setPosition(0.08);
            sleep(500);
            Lservoarm.setPosition(0);
            Rservoram.setPosition(0);
            sleep(500);

            //Diagonal to zone
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setTargetPosition(0);
            FrontRight.setTargetPosition(-1300);
            BackLeft.setTargetPosition(-1300);
            BackRight.setTargetPosition(0);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            bottomMotor.setTargetPosition(-1250);
            topMotor.setTargetPosition(-1250);
            bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bottomMotor.setPower(0.6);
            topMotor.setPower(0.6);
            sleep(1000);

            //Park in zone 1
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
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);

        } else if (tagOfInterest.id == 3) {

            //Diagonal to medium
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(0);
            BackRight.setTargetPosition(1300);
            FrontLeft.setTargetPosition(1300);
            FrontRight.setTargetPosition(0);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Away from junction
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setTargetPosition(-300);
            FrontRight.setTargetPosition(-300);
            BackLeft.setTargetPosition(-300);
            BackRight.setTargetPosition(-300);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            bottomMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            topMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            bottomMotor.setTargetPosition(-1250);
            topMotor.setTargetPosition(-1250);
            bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bottomMotor.setPower(0.6);
            topMotor.setPower(0.6);
            sleep(2000);

            //Park in zone
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(-700);
            BackRight.setTargetPosition(700);
            FrontLeft.setTargetPosition(700);
            FrontRight.setTargetPosition(-700);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(0.8);
            BackRight.setPower(0.8);
            FrontLeft.setPower(0.8);
            FrontRight.setPower(0.8);
            sleep(2000);

            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(1000);
            BackRight.setTargetPosition(1000);
            FrontLeft.setTargetPosition(1000);
            FrontRight.setTargetPosition(1000);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(5000);

        } else if (tagOfInterest.id == 2) {

            //Grab preload
            telemetry.addData("Object Detected", "Circle");
            LClaw.setPosition(0.01);
            sleep(200);
            Lservoarm.setPosition(0.25);
            Rservoram.setPosition(0.25);
            sleep(100);

            //Forward
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(50);
            BackRight.setTargetPosition(50);
            FrontLeft.setTargetPosition(50);
            FrontRight.setTargetPosition(50);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(0.5);
            BackRight.setPower(0.5);
            FrontLeft.setPower(0.5);
            FrontRight.setPower(0.5);
            sleep(500);

            //Strafe left
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(2100);
            BackRight.setTargetPosition(-2100);
            FrontLeft.setTargetPosition(-2100);
            FrontRight.setTargetPosition(2100);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(0.8);
            BackRight.setPower(0.8);
            FrontLeft.setPower(0.8);
            FrontRight.setPower(0.8);
            sleep(3000);

            //Forward to medium junction
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(300);
            BackRight.setTargetPosition(300);
            FrontLeft.setTargetPosition(300);
            FrontRight.setTargetPosition(300);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);

            //Drop cone and reset arm
            bottomMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            topMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            bottomMotor.setTargetPosition(1250);
            topMotor.setTargetPosition(1250);
            bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bottomMotor.setPower(1);
            topMotor.setPower(1);
            sleep(500);
            Lservoarm.setPosition(0.4);
            Rservoram.setPosition(0.4);
            sleep(500);
            LClaw.setPosition(0.06);
            sleep(500);

            //Diagonal strafe to cone stack
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(0);
            BackRight.setTargetPosition(-1300);
            FrontLeft.setTargetPosition(-1300);
            FrontRight.setTargetPosition(0);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);

            //Raise arm
            bottomMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            topMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            bottomMotor.setTargetPosition(-850);
            topMotor.setTargetPosition(-850);
            bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bottomMotor.setPower(1);
            topMotor.setPower(1);
            Lservoarm.setPosition(0);
            Rservoram.setPosition(0);
            LClaw.setPosition(0.11);
            sleep(1000);

            //Back to cone stack
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(-1000);
            BackRight.setTargetPosition(-1000);
            FrontLeft.setTargetPosition(-1000);
            FrontRight.setTargetPosition(-1000);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(0.8);
            BackRight.setPower(0.8);
            FrontLeft.setPower(0.8);
            FrontRight.setPower(0.8);
            sleep(2000);

            //Pick up cone
            LClaw.setPosition(0.01);
            sleep(500);
            Lservoarm.setPosition(0.25);
            Rservoram.setPosition(0.25);
            sleep(1000);

            //Forward
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(800);
            BackRight.setTargetPosition(800);
            FrontLeft.setTargetPosition(800);
            FrontRight.setTargetPosition(800);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Diagonal to medium
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(0);
            BackRight.setTargetPosition(1300);
            FrontLeft.setTargetPosition(1300);
            FrontRight.setTargetPosition(0);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Drop cone on junction and reset
            bottomMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            topMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            bottomMotor.setTargetPosition(850);
            topMotor.setTargetPosition(850);
            bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bottomMotor.setPower(1);
            topMotor.setPower(1);
            sleep(1500);
            Lservoarm.setPosition(0.4);
            Rservoram.setPosition(0.4);
            sleep(500);
            LClaw.setPosition(0.08);
            sleep(500);
            Lservoarm.setPosition(0);
            Rservoram.setPosition(0);
            sleep(500);

            //Diagonal to cone stack
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(0);
            BackRight.setTargetPosition(-1300);
            FrontLeft.setTargetPosition(-1300);
            FrontRight.setTargetPosition(0);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            bottomMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            topMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            bottomMotor.setTargetPosition(-750);
            topMotor.setTargetPosition(-750);
            bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bottomMotor.setPower(1);
            topMotor.setPower(1);
            Lservoarm.setPosition(0);
            Rservoram.setPosition(0);
            LClaw.setPosition(0.11);
            sleep(1000);

            //Back up to stack
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(-1000);
            BackRight.setTargetPosition(-1000);
            FrontLeft.setTargetPosition(-1000);
            FrontRight.setTargetPosition(-1000);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(0.8);
            BackRight.setPower(0.8);
            FrontLeft.setPower(0.8);
            FrontRight.setPower(0.8);
            sleep(2000);
            LClaw.setPosition(0.01);
            sleep(500);
            Lservoarm.setPosition(0.25);
            Rservoram.setPosition(0.25);
            sleep(1000);

            //Forward
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(800);
            BackRight.setTargetPosition(800);
            FrontLeft.setTargetPosition(800);
            FrontRight.setTargetPosition(800);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Diagonal to medium
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(0);
            BackRight.setTargetPosition(1300);
            FrontLeft.setTargetPosition(1300);
            FrontRight.setTargetPosition(0);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Drop and reset
            bottomMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            topMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            bottomMotor.setTargetPosition(750);
            topMotor.setTargetPosition(750);
            bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bottomMotor.setPower(1);
            topMotor.setPower(1);
            sleep(1500);
            Lservoarm.setPosition(0.4);
            Rservoram.setPosition(0.4);
            sleep(500);
            LClaw.setPosition(0.08);
            sleep(500);
            Lservoarm.setPosition(0);
            Rservoram.setPosition(0);
            sleep(500);

            //Back to zone
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackLeft.setTargetPosition(-100);
            BackRight.setTargetPosition(-100);
            FrontLeft.setTargetPosition(-100);
            FrontRight.setTargetPosition(-100);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

        } else {
            BackLeft.setPower(0);
            BackRight.setPower(0);
            FrontLeft.setPower(0);
            FrontRight.setPower(0);
        }
    }

    void tagToTelemetry(AprilTagDetection detection)
    {
        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
        // telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
       // telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
        // telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
    }
}

