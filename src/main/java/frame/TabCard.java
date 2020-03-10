package frame;

import constant.Constants;

import javax.swing.*;
import javax.swing.undo.UndoManager;
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
    //JTextArea撤销管理类
    private UndoManager undoManager;
    // 创建文本区域组件
    private JTextArea jTextArea;
    // 创建滚动面板, 指定滚动显示的视图组件(textArea), 垂直滚动条一直显示, 水平滚动条一直显示
    private  JScrollPane jScrollPane;
    //创建工具栏组件
    private JToolBar jToolBar;
    //文件状态
    private JLabel fileState;
    //文件属性
    private JLabel fileAttribute;

    //文件绝对路径
    private String absoluteFilePath;
    //文件名称（短名称）
    private String fileName;
    public TabCard() {
        super();
        createObject();
    }
    /**
     * 创建对象
     * @Author lrh
     * @Date 2020/1/19 23:05
     * @Param []
     * @Return void
     */
    private void createObject(){
        jTextArea = new JTextArea(20,20);
        jScrollPane = new JScrollPane(jTextArea);
        jToolBar = new JToolBar();
        fileState = new JLabel("文件状态："+Constants.FILE_STATE_UNSAVE);
        fileAttribute = new JLabel("长度：0    行数：1");
        undoManager = new UndoManager();
        init();
    }
    /**
     * 初始化对象属性
     * @Author lrh
     * @Date 2020/1/19 23:06
     * @Param []
     * @Return void
     */
    private void init(){
        jTextArea.setFont(Constants.FONT);   // 设置字体
        jScrollPane.setRowHeaderView(new LineNumberHeaderView());
        this.setLayout(new BorderLayout(1,2));  //布局管理，这里一定要设置，否则会不显示
        jToolBar.setFloatable(false);  //设置为不可移动
        jToolBar.add(fileState);
        jToolBar.add(fileAttribute);
        fileState.setFont(Constants.FONT);
        fileAttribute.setFont(Constants.FONT);
        this.add(jScrollPane,BorderLayout.CENTER); //设置据中显示
        this.add(jToolBar,BorderLayout.PAGE_END);  //设置显示在底部
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

    public JToolBar getjToolBar() {
        return jToolBar;
    }

    public void setjToolBar(JToolBar jToolBar) {
        this.jToolBar = jToolBar;
    }

    public JLabel getFileState() {
        return fileState;
    }

    public void setFileState(JLabel fileState) {
        this.fileState = fileState;
    }

    public JLabel getFileAttribute() {
        return fileAttribute;
    }

    public void setFileAttribute(JLabel fileAttribute) {
        this.fileAttribute = fileAttribute;
    }

    public UndoManager getUndoManager() {
        return undoManager;
    }

    public void setUndoManager(UndoManager undoManager) {
        this.undoManager = undoManager;
    }
}
