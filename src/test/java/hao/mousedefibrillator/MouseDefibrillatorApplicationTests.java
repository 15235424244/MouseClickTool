package hao.mousedefibrillator;

import hao.mousedefibrillator.tools.ClickTool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.event.InputEvent;

@SpringBootTest
class MouseDefibrillatorApplicationTests {

    @Autowired
    public ClickTool clickTool;

    @Test
    void contextLoads() {
    }

}
