package frame;

import constant.Constants;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.MenuBarUI;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
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
        JMenuItem exitSystem = new JMenuItem("退出");
        newFile.setFont(font);
        openFile.setFont(font);
        saveFile.setFont(font);
        exitSystem.setFont(font);

        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(exitSystem);
        exitSystemListener(jFrame,exitSystem); //退出系统
        openFileListener(jFrame,openFile);     //打开文件
        jFrame.setJMenuBar(jMenuBar);
        jTextAreaListener(); //输入数据
    }
    /**
     * jTextArea事件监听
     * @Author lrh
     * @Date 2020/1/15 19:20
     * @Param []
     * @Return void
     */
    private void jTextAreaListener(){
        jTextArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                System.out.println("输入数据");
            }

            public void removeUpdate(DocumentEvent e) {
                System.out.println("删除数据");
            }

            public void changedUpdate(DocumentEvent e) {
                System.out.println("更新数据");
            }
        });
    }

    /**
     * 打开文件
     * @Author lrh
     * @Date 2020/1/14 15:34
     * @Param [jFrame, jMenuItem]
     * @Return void
     */
    private void openFileListener(final JFrame jFrame, final JMenuItem jMenuItem){
        jMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                if (jFileChooser.showOpenDialog(jMenuItem)==JFileChooser.APPROVE_OPTION) {
                    File file = jFileChooser.getSelectedFile();
                    readFile(file);
                };
            }
        });
    }
    /**   
     * 读取文件
     * @Author lrh
     * @Date 2020/1/14 19:33
     * @Param [file]
     * @Return void
     */
    private void readFile(File file){
        try {

           /* Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                jTextArea.append(scanner.nextLine());
            }
            scanner.close();*/

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            byte[] bytes = new byte[4096];
            int len = 0;
            while((len = in.read(bytes))!=-1){
                jTextArea.append(new String(bytes,0,len,"UTF-8"));
                jTextArea.paintImmediately(jTextArea.getBounds());
            }
            in.close();
            System.out.println("一共："+jTextArea.getLineCount());
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,"未找到文件");
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"打开文件失败");
            e.printStackTrace();
        }
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
     * 退出，关闭窗口
     * @Author lrh
     * @Date 2020/1/14 13:24
     * @Param []
     * @Return void
     */
    private void exitSystemListener(final JFrame jFrame, JMenuItem jMenuItem){
        jMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(jFrame, "确定退出？", "提示", JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
    }
    public static MyFrame getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new MyFrame();
        }
        return INSTANCE;
    }
}
