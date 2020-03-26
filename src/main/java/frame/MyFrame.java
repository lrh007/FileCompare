package frame;

import constant.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * 界面
 *
 * @ClassName Frame
 * @Author lrh
 * @Date 2020/1/14 10:47
 * @Version 1.0
 */
public class MyFrame extends JFrame {
    private static MyFrame INSTANCE;
    //创建选项卡面板,标题位置在上面，放不下所有选项卡时使用滚动条
    private static JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);

    private MyFrame() throws HeadlessException {
        this.setTitle("FileCompare");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize(); //获取系统窗口大小
        this.setSize(screenSize);  //设置界面大小
        this.setResizable(true);   //设置为可放大缩小
        this.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH); //窗口最大化
        Image imageIcon = toolkit.getImage("src/main/resources/images/title.gif");
        this.setIconImage(imageIcon);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //关闭窗口时退出进程
        this.setSize(this.getWidth(),this.getHeight());
        TabCard tabCard = new TabCard();
        String fileName = "new 1";
        tabCard.setFileName(fileName); //初始化文件名称

        jTabbedPane.addTab(fileName,Constants.ICON,tabCard,fileName); //初始化一个空白选项卡
        jTabbedPane.setFont(Constants.FONT);
        ComponentListener.jTextAreaListener(tabCard.getjTextArea(),tabCard.getUndoManager()); //添加事件监听
        this.add(jTabbedPane);
        FrameMenu.addMenu(this); //设置菜单栏
        this.setVisible(true);  //显示窗口
    }

    /**
     * 动态添加组件后刷新组件
     * @Author lrh
     * @Date 2020/1/14 19:28
     * @Param [component]
     * @Return void
     */
    public static void updateUI(Component component){
        SwingUtilities.updateComponentTreeUI(component);
    }
    
    /**   
     * 获取选项卡面板对象
     * @Author lrh
     * @Date 2020/1/16 15:34
     * @Param []
     * @Return javax.swing.JTabbedPane
     */
    public static JTabbedPane getJTabbedPane(){
        return jTabbedPane;
    }

    /**   
     * 实例化对象，单例模式
     * @Author lrh
     * @Date 2020/1/16 11:18
     * @Param []
     * @Return frame.MyFrame
     */
    public static MyFrame getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new MyFrame();
        }
        return INSTANCE;
    }
}
