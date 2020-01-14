import frame.MyFrame;

import javax.swing.*;
import java.awt.*;

/**
 * 主程序
 *
 * @ClassName Run
 * @Author lrh
 * @Date 2020/1/14 10:45
 * @Version 1.0
 */
public class Run {

    public static void main(String[] args) {
        MyFrame frame = MyFrame.getINSTANCE();
        JLabel jLabel = new JLabel("测试");
        jLabel.setSize(100,200);
        jLabel.setForeground(Color.white);
        frame.add(jLabel);
    }
}
