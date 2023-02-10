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

package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@Autonomous(name = "LeftPlaceConeAndPark", group = ".Main Auto")
public class LeftPlaceConeAndPark extends LinearOpMode
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

    OpenCvCamera LCamera;
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
        LCamera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        LCamera.setPipeline(aprilTagDetectionPipeline);
        LCamera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                LCamera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
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

        //PUT AUTON CODE HERE (DRIVER PRESSED THE PLAY BUTTON!)
        if (tagOfInterest.id == 1) {

            telemetry.addData("Object Detected", "1");

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
            sleep(1000);


            /*
            LClaw.setPosition(0.01);
            Lservoarm.setPosition(0.25);
            Rservoram.setPosition(0.25);
            sleep(1000);

            // Strafe right

            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(-300);
            BackRight.setTargetPosition(300);
            FrontLeft.setTargetPosition(300);
            FrontRight.setTargetPosition(-300);

            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            // Forward

            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(200);
            BackRight.setTargetPosition(200);
            FrontLeft.setTargetPosition(200);
            FrontRight.setTargetPosition(200);

            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            // Turn Clockwise

            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(750);
            BackRight.setTargetPosition(-750);
            FrontLeft.setTargetPosition(750);
            FrontRight.setTargetPosition(-750);

            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            // Left to meduim junction

            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(1900);
            BackRight.setTargetPosition(-1900);
            FrontLeft.setTargetPosition(-1900);
            FrontRight.setTargetPosition(1900);

            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);

            sleep(1000);
            bottomMotor.setTargetPosition(1280);
            topMotor.setTargetPosition(1280);
            bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bottomMotor.setPower(1);
            topMotor.setPower(1);

            sleep(500);
            Rservoram.setPosition(0.4);
            Lservoarm.setPosition(0.4);

            // Forward

            sleep(1000);
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(230);
            BackRight.setTargetPosition(230);
            FrontLeft.setTargetPosition(230);
            FrontRight.setTargetPosition(230);

            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Raise arm

            LClaw.setPosition(0.1);
            sleep(500);

            Lservoarm.setPosition(0);
            Rservoram.setPosition(0);
            sleep(500);

            bottomMotor.setTargetPosition(0);
            topMotor.setTargetPosition(0);
            bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bottomMotor.setPower(1);
            topMotor.setPower(1);

            //Backwards

            sleep(1000);
            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(-230);
            BackRight.setTargetPosition(-230);
            FrontLeft.setTargetPosition(-230);
            FrontRight.setTargetPosition(-230);

            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Strafe left

            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(550);
            BackRight.setTargetPosition(-550);
            FrontLeft.setTargetPosition(-550);
            FrontRight.setTargetPosition(550);

            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Backwards

            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(-1080);
            BackRight.setTargetPosition(-1050);
            FrontLeft.setTargetPosition(-1080);
            FrontRight.setTargetPosition(-1050);

            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(3000000);

            */

        } else if (tagOfInterest.id == 3) {


            telemetry.addData("Object Detected", "3");

            LClaw.setPosition(0.01);
            Lservoarm.setPosition(0.25);
            Rservoram.setPosition(0.25);
            sleep(1000);

            //Strafe right

            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(-300);
            BackRight.setTargetPosition(300);
            FrontLeft.setTargetPosition(300);
            FrontRight.setTargetPosition(-300);

            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Forward

            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(230);
            BackRight.setTargetPosition(230);
            FrontLeft.setTargetPosition(230);
            FrontRight.setTargetPosition(230);

            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Turn clockwise

            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(750);
            BackRight.setTargetPosition(-750);
            FrontLeft.setTargetPosition(750);
            FrontRight.setTargetPosition(-750);

            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Strafe left

            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(1900);
            BackRight.setTargetPosition(-1900);
            FrontLeft.setTargetPosition(-1900);
            FrontRight.setTargetPosition(1900);

            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Arm up

            bottomMotor.setTargetPosition(1280);
            topMotor.setTargetPosition(1280);
            bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bottomMotor.setPower(1);
            topMotor.setPower(1);
            sleep(500);

            Rservoram.setPosition(0.4);
            Lservoarm.setPosition(0.4);
            sleep(1000);

            //Forward

            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(230);
            BackRight.setTargetPosition(230);
            FrontLeft.setTargetPosition(230);
            FrontRight.setTargetPosition(230);

            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Drop and lower arm

            LClaw.setPosition(0.1);
            sleep(500);

            Lservoarm.setPosition(0);
            Rservoram.setPosition(0);
            sleep(500);

            bottomMotor.setTargetPosition(0);
            topMotor.setTargetPosition(0);
            bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bottomMotor.setPower(1);
            topMotor.setPower(1);
            sleep(1000);

            //Backwards

            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(-230);
            BackRight.setTargetPosition(-230);
            FrontLeft.setTargetPosition(-230);
            FrontRight.setTargetPosition(-230);

            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Strafe left

            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(700);
            BackRight.setTargetPosition(-700);
            FrontLeft.setTargetPosition(-700);
            FrontRight.setTargetPosition(700);

            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Forward

            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(900);
            BackRight.setTargetPosition(900);
            FrontLeft.setTargetPosition(900);
            FrontRight.setTargetPosition(900);

            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(3000000);

        } else if (tagOfInterest.id == 2) {

            telemetry.addData("Object Detected", "2");

            LClaw.setPosition(0.01);
            Lservoarm.setPosition(0.25);
            Rservoram.setPosition(0.25);
            sleep(1000);

            //Strafe right

            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(-300);
            BackRight.setTargetPosition(300);
            FrontLeft.setTargetPosition(300);
            FrontRight.setTargetPosition(-300);

            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Forward

            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(200);
            BackRight.setTargetPosition(200);
            FrontLeft.setTargetPosition(200);
            FrontRight.setTargetPosition(200);

            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Turn clockwise

            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(750);
            BackRight.setTargetPosition(-750);
            FrontLeft.setTargetPosition(750);
            FrontRight.setTargetPosition(-750);

            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Strafe left

            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(1900);
            BackRight.setTargetPosition(-1900);
            FrontLeft.setTargetPosition(-1900);
            FrontRight.setTargetPosition(1900);

            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Raise arm

            bottomMotor.setTargetPosition(1280);
            topMotor.setTargetPosition(1280);
            bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bottomMotor.setPower(1);
            topMotor.setPower(1);
            sleep(500);

            Rservoram.setPosition(0.4);
            Lservoarm.setPosition(0.4);
            sleep(1000);

            //Forward

            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(200);
            BackRight.setTargetPosition(200);
            FrontLeft.setTargetPosition(200);
            FrontRight.setTargetPosition(200);

            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            BackLeft.setPower(1);
            BackRight.setPower(1);
            FrontLeft.setPower(1);
            FrontRight.setPower(1);
            sleep(1000);

            //Drop and lower arm

            LClaw.setPosition(0.1);
            sleep(500);

            Lservoarm.setPosition(0);
            Rservoram.setPosition(0);
            sleep(500);

            bottomMotor.setTargetPosition(0);
            topMotor.setTargetPosition(0);
            bottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            topMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bottomMotor.setPower(1);
            topMotor.setPower(1);
            sleep(1000);

            //Backwards

            BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            BackLeft.setTargetPosition(-200);
            BackRight.setTargetPosition(-200);
            FrontLeft.setTargetPosition(-200);
            FrontRight.setTargetPosition(-200);

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
        telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
        telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
        telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
    }
}
