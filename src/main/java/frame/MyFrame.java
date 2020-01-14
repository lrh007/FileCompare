package frame;

import constant.Constants;

import javax.swing.*;
import javax.swing.plaf.MenuBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 界面
 *
 * @ClassName Frame
 * @Author lrh
 * @Date 2020/1/14 10:47
 * @Version 1.0
 */
public class MyFrame extends JPanel {
    private static MyFrame INSTANCE = new MyFrame();
    private MyFrame() throws HeadlessException {
        JFrame jFrame = new JFrame("FileCompare");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize(); //获取系统窗口大小
        jFrame.setSize(screenSize);  //设置界面大小
        jFrame.setResizable(true);   //设置为可放大缩小
        jFrame.setExtendedState(jFrame.getExtendedState()|JFrame.MAXIMIZED_BOTH); //窗口最大化
        Image imageIcon = toolkit.getImage("src/main/resources/images/title.gif");
        jFrame.setIconImage(imageIcon);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //关闭窗口时退出进程
        jFrame.add(this);
        this.setBackground(Color.decode(Constants.BACKGROUD_COLOR));  //设置窗口背景色
        this.setForeground(Color.white);
        addMenu(jFrame);
        jFrame.setVisible(true);  //显示窗口
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
        JMenu fileMenu = new JMenu("文件");
        JMenu editMenu = new JMenu("编辑");
        JMenu viewMenu = new JMenu("视图");
        JMenu helpMenu = new JMenu("帮助");
        Font font = new Font("微软雅黑",Font.PLAIN,18);
        fileMenu.setFont(font);
        editMenu.setFont(font);
        viewMenu.setFont(font);
        helpMenu.setFont(font);
        jMenuBar.add(fileMenu);
        jMenuBar.add(editMenu);
        jMenuBar.add(viewMenu);
        jMenuBar.add(helpMenu);
        JMenuItem newFile = new JMenuItem("新建");
        JMenuItem openFile = new JMenuItem("打开");
        JMenuItem saveFile = new JMenuItem("保存");
        JMenuItem exit = new JMenuItem("退出");
        newFile.setFont(font);
        openFile.setFont(font);
        saveFile.setFont(font);
        exit.setFont(font);

        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(exit);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(jFrame, "确定退出？", "提示", JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
        jFrame.setJMenuBar(jMenuBar);
    }
    /**   
     * 退出，关闭窗口
     * @Author lrh
     * @Date 2020/1/14 13:24
     * @Param []
     * @Return void
     */
    private void exit(){

    }
    public static MyFrame getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new MyFrame();
        }
        return INSTANCE;
    }
}
