package frame;

import constant.Constants;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.MenuBarUI;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

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
    // 创建文本区域组件
    private  JTextArea jTextArea = new JTextArea();
    // 创建滚动面板, 指定滚动显示的视图组件(textArea), 垂直滚动条一直显示, 水平滚动条一直显示
    private  JScrollPane jScrollPane;

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
        addMenu(this);
        jScrollPane = new JScrollPane(jTextArea);
        this.add(jScrollPane);
        init();
        this.setVisible(true);  //显示窗口
    }
    /**
     * 初始化
     * @Author lrh
     * @Date 2020/1/15 13:08
     * @Param []
     * @Return void
     */
    private void init(){
        jTextArea.setFont(new Font(Constants.FONT_NAME, Font.PLAIN, Constants.FONT_SIZE));   // 设置字体
        jScrollPane.setRowHeaderView(new LineNumberHeaderView());
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    }
    /**
     * 添加菜单栏
     * @Author lrh
     * @Date 2020/1/14 12:49
     * @Param []
     * @Return void
     */
    private void addMenu(final JFrame jFrame){
        JMenuBar jMenuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("文件(F)");
        JMenu editMenu = new JMenu("编辑(E)");
        JMenu searchMenu = new JMenu("搜索(S)");
        JMenu viewMenu = new JMenu("视图(V)");
        JMenu helpMenu = new JMenu("帮助(H)");
        Font font = new Font("微软雅黑",Font.PLAIN,18);
        fileMenu.setFont(font);
        fileMenu.setMnemonic(KeyEvent.VK_F);
        editMenu.setFont(font);
        editMenu.setMnemonic(KeyEvent.VK_E);
        searchMenu.setFont(font);
        searchMenu.setMnemonic(KeyEvent.VK_S);
        viewMenu.setFont(font);
        viewMenu.setMnemonic(KeyEvent.VK_V);
        helpMenu.setFont(font);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        jMenuBar.add(fileMenu);
        jMenuBar.add(editMenu);
        jMenuBar.add(searchMenu);
        jMenuBar.add(viewMenu);
        jMenuBar.add(helpMenu);
        JMenuItem newFile = new JMenuItem("新建(N)    ");
        JMenuItem openFile = new JMenuItem("打开(O)   ");
        JMenuItem saveFile = new JMenuItem("保存(S)   ");
        JMenuItem exitSystem = new JMenuItem("退出(Q) ");
        newFile.setFont(font);
        newFile.setMnemonic(KeyEvent.VK_N);
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
        openFile.setFont(font);
        openFile.setMnemonic(KeyEvent.VK_O);
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
        saveFile.setFont(font);
        saveFile.setMnemonic(KeyEvent.VK_S);
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
        exitSystem.setFont(font);
        exitSystem.setMnemonic(KeyEvent.VK_Q);
        exitSystem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,ActionEvent.CTRL_MASK));

        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.addSeparator(); //添加分割线
        fileMenu.add(exitSystem);
        jFrame.setJMenuBar(jMenuBar);
        ComponentListener.exitSystemListener(jFrame,exitSystem);            //退出系统
        ComponentListener.openFileListener(jFrame,openFile,jTextArea);      //打开文件
        ComponentListener.jTextAreaListener(jTextArea);                     //输入数据
        ComponentListener.newFileListener(jFrame,newFile,jTextArea);        //新建文件
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
