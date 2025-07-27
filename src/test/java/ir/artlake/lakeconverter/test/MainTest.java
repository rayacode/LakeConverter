package codes.rayacode.lakeconverter.test;

import codes.rayacode.lakeconverter.Main;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class MainTest {

    @Start
    public void start(Stage stage) throws Exception {
        
        new Main().start(stage);
    }

    @Test
    public void testMainIsRunning(FxRobot robot) {
        
        assertTrue(robot.window(0).isShowing());
    }
}