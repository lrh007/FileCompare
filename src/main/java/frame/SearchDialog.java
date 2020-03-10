package frame;

import constant.Constants;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
    /**获取选项卡面板对象**/
    private static JTabbedPane jTabbedPane = MyFrame.getJTabbedPane();
    /**输入的数据**/
    private JTextField inputStr = new JTextField();
    /**查询下一个按钮**/
    private JButton searchNext = new JButton("查找下一个");
    /**查找目标文本**/
    private JLabel searchTarget = new JLabel("查找目标: ");
    private SearchDialog(){
        
    }
    /**   
     * 初始化参数
     * @Author lrh
     * @Date 2020/3/10 20:53
     * @Param []
     * @Return void
     */
    private void init(){
        inputStr.setPreferredSize(new Dimension(300,30));
        inputStr.setFont(Constants.FONT);
        searchNext.setFont(Constants.FONT);
        searchTarget.setFont(Constants.FONT);
        actionListener();
    }
    /**   
     * 事件监听
     * @Author lrh
     * @Date 2020/3/10 20:53
     * @Param []
     * @Return void
     */
    private void actionListener(){
        //查找下一个
        searchNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TabCard  tabCard = (TabCard)jTabbedPane.getSelectedComponent();
                JTextArea jTextArea = tabCard.getjTextArea();
                String text = jTextArea.getText();
                System.out.println(text);
            }
        });
    }
    
    private SearchDialog(JFrame jFrame){
        super(jFrame,"查找");
        Container conn = getContentPane();
        conn.setLayout(new FlowLayout(FlowLayout.LEFT,10,5));
        conn.add(searchTarget);
        conn.add(inputStr);
        conn.add(searchNext);
        conn.setBackground(Color.white);
        this.setSize(600,400);
        int x = jFrame.getWidth()/2-this.getWidth()/2;
        int y = jFrame.getHeight()/2-this.getHeight()/2;
        this.setLocation(x,y);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                INSTANCE = null;
            }
        });
        setVisible(true);
        init();
    }
    /**   
     * 单例模式
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

    public JTextField getInputStr() {
        return inputStr;
    }

    public void setInputStr(JTextField inputStr) {
        this.inputStr = inputStr;
    }

    public JButton getSearchNext() {
        return searchNext;
    }

    public void setSearchNext(JButton searchNext) {
        this.searchNext = searchNext;
    }
}
