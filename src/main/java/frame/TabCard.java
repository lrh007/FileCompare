package frame;

import constant.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * 选项卡类
 *
 * @ClassName TableCard
 * @Author lrh
 * @Date 2020/1/16 13:33
 * @Version 1.0
 */
public class TabCard extends JPanel {
    // 创建文本区域组件
    private JTextArea jTextArea;
    // 创建滚动面板, 指定滚动显示的视图组件(textArea), 垂直滚动条一直显示, 水平滚动条一直显示
    private  JScrollPane jScrollPane;
    //文件绝对路径
    private String absoluteFilePath;
    //文件名称（短名称）
    private String fileName;
    public TabCard() {
        super();
        jTextArea = new JTextArea(20,20);
        jScrollPane = new JScrollPane(jTextArea);
        jTextArea.setFont(new Font(Constants.FONT_NAME, Font.PLAIN, Constants.FONT_SIZE));   // 设置字体
        jScrollPane.setRowHeaderView(new LineNumberHeaderView());
        this.setLayout(new BorderLayout(1,1));  //布局管理，这里一定要设置，否则会不显示
        this.add(jScrollPane,BorderLayout.CENTER); //设置据中显示
    }

    /**
     * 获取JTextArea对象
     * @Author lrh
     * @Date 2020/1/16 13:37
     * @Param []
     * @Return javax.swing.JTextArea
     */
    public JTextArea getjTextArea() {
        return jTextArea;
    }
    /**   
     * 获取JScrollPane对象
     * @Author lrh
     * @Date 2020/1/16 13:38
     * @Param []
     * @Return javax.swing.JScrollPane
     */
    public JScrollPane getjScrollPane() {
        return jScrollPane;
    }

    public String getAbsoluteFilePath() {
        return absoluteFilePath;
    }

    public void setAbsoluteFilePath(String absoluteFilePath) {
        this.absoluteFilePath = absoluteFilePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
