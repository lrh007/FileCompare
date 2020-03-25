package frame;

import constant.Constants;
import frame.panel.ReplacePanel;
import frame.panel.SearchPanel;
import util.FrameUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 搜索文件弹出对话框
 *
 * @ClassName SearchDialog
 * @Author lrh
 * @Date 2020/3/10 17:24
 * @Version 1.0
 */
public class SearchDialog extends JDialog {
    private static SearchDialog INSTANCE = null;
    /**查找面板对象**/
    private SearchPanel searchPanel = new SearchPanel(this);
    /**替换面板对象**/
    private ReplacePanel replacePanel = new ReplacePanel(this);
    /**选项卡面板放置组件**/
    private JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
    private SearchDialog(){

    }
    private SearchDialog(JFrame jFrame){
        super(jFrame,"查找");
        Container conn = getContentPane();
        jTabbedPane.addTab("查找",null,searchPanel,null);
        jTabbedPane.addTab("替换",null,replacePanel,null);

        conn.add(jTabbedPane);
        this.setSize(680,400);
        this.setResizable(false);
        int x = jFrame.getWidth()/2-this.getWidth()/2;
        int y = jFrame.getHeight()/2-this.getHeight()/2;
        this.setLocation(x,y);
        setVisible(true);
        init();
        listener();
    }
    /**   
     * 初始化参数
     * @Author lrh
     * @Date 2020/3/10 20:53
     * @Param []
     * @Return void
     */
    private void init(){
        jTabbedPane.setFont(Constants.FONT);
    }
    /**
     * 事件监听
     * @Author lrh
     * @Date 2020/3/25 11:40
     * @Param []
     * @Return void
     */
    private void listener(){
        //窗口关闭事件
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                searchPanel.getTips().setText("");
                replacePanel.getTips().setText("");
                INSTANCE = null;
            }
        });
        //选项卡切换事件
        jTabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = jTabbedPane.getSelectedIndex();//获得被选中选项卡的索引
                String title = jTabbedPane.getTitleAt(selectedIndex);//获得指定索引的选项卡标签
                setTitleName(title);
            }
        });

    }
    /**
     * 获取单例对象
     * @Author lrh
     * @Date 2020/3/10 17:28
     * @Param []
     * @Return frame.SearchDialog
     */
    public static SearchDialog getInstance(JFrame jFrame){
        if(INSTANCE == null){
            INSTANCE = new SearchDialog(jFrame);
        }
        return INSTANCE;
    }
    /**   
     * 获取单例对象
     * @Author lrh
     * @Date 2020/3/15 14:56
     * @Param []
     * @Return frame.SearchDialog
     */
    public static SearchDialog getInstance(){
        if(INSTANCE == null){
            INSTANCE = new SearchDialog();
        }
        return INSTANCE;
    }
    /**   
     * 设置窗体的显示标题
     * @Author lrh
     * @Date 2020/3/25 11:43
     * @Param [name]
     * @Return void
     */
    private void setTitleName(String name){
        this.setTitle(name);
    }

    public SearchPanel getSearchPanel() {
        return searchPanel;
    }

    public void setSearchPanel(SearchPanel searchPanel) {
        this.searchPanel = searchPanel;
    }

    public ReplacePanel getReplacePanel() {
        return replacePanel;
    }

    public void setReplacePanel(ReplacePanel replacePanel) {
        this.replacePanel = replacePanel;
    }

    public JTabbedPane getjTabbedPane() {
        return jTabbedPane;
    }

    public void setjTabbedPane(JTabbedPane jTabbedPane) {
        this.jTabbedPane = jTabbedPane;
    }
}
