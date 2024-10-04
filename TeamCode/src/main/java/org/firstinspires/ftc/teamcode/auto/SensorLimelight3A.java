//package org.firstinspires.ftc.teamcode.auto;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//public class Teleop extends LinearOpMode {
//
//    private Limelight3A limelight;
//
//    @Override
//    public void runOpMode() throws InterruptedException
//    {
//        limelight = hardwareMap.get(Limelight3A.class, "limelight");
//
//        telemetry.setMsTransmissionInterval(11);
//
//        limelight.pipelineSwitch(0);
//
//        limelight.start();
//
//        while (opModeIsActive()) {
//            LLResult result = limelight.getLatestResult();
//            if (result != null) {
//                if (result.isValid()) {
//                    Pose3D botpose = result.getBotpose();
//                    telemetry.addData("tx", result.getTx());
//                    telemetry.addData("ty", result.getTy());
//                    telemetry.addData("Botpose", botpose.toString());