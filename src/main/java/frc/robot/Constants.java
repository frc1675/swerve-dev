package frc.robot;

public final class Constants {
    public static final int DRIVER_CONTROLLER = 0;
    public static final double DRIVETRAIN_TRACKWIDTH_METERS = 0.599; //Measured in cad
    public static final double DRIVETRAIN_WHEELBASE_METERS = 0.601; //Mesasured in cad

    public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR = 3;
    public static final int FRONT_LEFT_MODULE_STEER_MOTOR = 4;
    public static final int FRONT_LEFT_MODULE_STEER_ENCODER = 12; 
    public static final double FRONT_LEFT_MODULE_STEER_OFFSET = -Math.toRadians(141.59);

    public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR = 1;
    public static final int FRONT_RIGHT_MODULE_STEER_MOTOR = 2; 
    public static final int FRONT_RIGHT_MODULE_STEER_ENCODER = 10; 
    public static final double FRONT_RIGHT_MODULE_STEER_OFFSET = -Math.toRadians(286.52);

    public static final int BACK_LEFT_MODULE_DRIVE_MOTOR = 7; 
    public static final int BACK_LEFT_MODULE_STEER_MOTOR = 8; 
    public static final int BACK_LEFT_MODULE_STEER_ENCODER = 11; 
    public static final double BACK_LEFT_MODULE_STEER_OFFSET = -Math.toRadians(302.17);

    public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR = 5; 
    public static final int BACK_RIGHT_MODULE_STEER_MOTOR = 6; 
    public static final int BACK_RIGHT_MODULE_STEER_ENCODER = 9; 
    public static final double BACK_RIGHT_MODULE_STEER_OFFSET = -Math.toRadians(314.47);
}
