import java.awt.*;

public class MyRobot {

    public volatile static Robot robot;

    public static Robot getRobot() {
        if (robot == null) {
            synchronized (MyRobot.class) {
                if (robot == null) {
                    try {
                        robot = new Robot();
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return robot;
    }
}
