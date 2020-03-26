package frame;

import constant.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        this.setIconImage(Constants.IMAGE);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE); //关闭窗口时退出进程
        this.setSize(this.getWidth(),this.getHeight());
        TabCard tabCard = new TabCard();
        String fileName = "new 1";
        tabCard.setFileName(fileName); //初始化文件名称

        jTabbedPane.addTab(fileName,Constants.ICON,tabCard,fileName); //初始化一个空白选项卡
        jTabbedPane.setFont(Constants.FONT);
        ComponentListener.jTextAreaListener(tabCard.getjTextArea(),tabCard.getUndoManager()); //添加事件监听
        this.add(jTabbedPane);
        FrameMenu.addMenu(this); //设置菜单栏
        addSystemTray(this);//设置系统托盘
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
    /***   
     * 添加系统托盘
     * @Author lrh
     * @Date 2020/3/26 16:56
     * @Param []
     * @Return void
     */
    private void addSystemTray(final MyFrame frame){
        /*
         * 添加系统托盘
         */
        if (SystemTray.isSupported()) {
            // 获取当前平台的系统托盘
            SystemTray tray = SystemTray.getSystemTray();
            // 加载一个图片用于托盘图标的显示
            // 创建点击图标时的弹出菜单
            PopupMenu popupMenu = new PopupMenu();
            MenuItem openItem = new MenuItem("Open");
            openItem.setFont(Constants.FONT);
            MenuItem exitItem = new MenuItem("Exit");
            exitItem.setFont(Constants.FONT);
            openItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 点击打开菜单时显示窗口
                    if (!frame.isShowing()) {
                        frame.setVisible(true);
                    }
                }
            });
            exitItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 点击退出菜单时退出程序
                    System.exit(0);
                }
            });

            popupMenu.add(openItem);
            popupMenu.add(exitItem);
            // 创建一个托盘图标
            TrayIcon trayIcon = new TrayIcon(Constants.IMAGE, "文本编辑器", popupMenu);
            // 托盘图标自适应尺寸
            trayIcon.setImageAutoSize(true);
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    switch (e.getButton()) {
                        case MouseEvent.BUTTON1: {
                            frame.setVisible(true);
                            break;
                        }
                        case MouseEvent.BUTTON2: {
                            System.out.println("托盘图标被鼠标中键被点击");
                            break;
                        }
                        case MouseEvent.BUTTON3: {
                            System.out.println("托盘图标被鼠标右键被点击");
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                }
            });
            // 添加托盘图标到系统托盘
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("当前系统不支持系统托盘");
        }
        frame.setVisible(true);
    }


}
