package frc.robot;

public final class Constants {
    public static final double DRIVETRAIN_TRACKWIDTH_METERS = 0.5969; //Measured on robot
    public static final double DRIVETRAIN_WHEELBASE_METERS = 0.5969;

    public static final double DEFAULT_RADIANS_PER_SECOND = 3.0;
    public static final double ACCEPTABLE_AUTOROTATE_ERROR_DEGREES = 3.0;

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
    public static final double BACK_LEFT_MODULE_STEER_OFFSET = -Math.toRadians(304.17);

    public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR = 5; 
    public static final int BACK_RIGHT_MODULE_STEER_MOTOR = 6; 
    public static final int BACK_RIGHT_MODULE_STEER_ENCODER = 9; 
    public static final double BACK_RIGHT_MODULE_STEER_OFFSET = -Math.toRadians(314.47);

    //controller constants
    public static final int DRIVER_CONTROLLER = 0;
    public static final int OPERATOR_CONTROLLER = 1;

    public static final int LEFT_X_AXIS = 0;
    public static final int LEFT_Y_AXIS= 1;
    public static final int RIGHT_X_AXIS = 4;
    public static final int RIGHT_Y_AXIS = 5;

    public static final int A_BUTTON = 1;
    public static final int B_BUTTON = 2;
    public static final int X_BUTTON = 3;
    public static final int Y_BUTTON = 4;

    public static final int LEFT_BUMPER = 5;
    public static final int RIGHT_BUMPER = 6;
    public static final int BACK_BUTTON = 7;
    public static final int START_BUTTON = 8;
    public static final int LEFT_JOYSTICK_BUTTON = 9;
    public static final int RIGHT_JOYSTICK_BUTTON = 10;
}
