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
    /**目标文本索引位置**/
    private int indexOf = 0;
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
                String searchStr = inputStr.getText();
                TabCard  tabCard = (TabCard)jTabbedPane.getSelectedComponent();
                JTextArea jTextArea = tabCard.getjTextArea();
                jTextArea.setSelectionColor(Color.green);
                int index = jTextArea.getText().indexOf(searchStr,indexOf);
                if(index >= 0 ){
                    jTextArea.setSelectionStart(index); //设置文本开始选中的位置
                    jTextArea.setSelectionEnd(index+searchStr.length()); //文本结束选中的位置
                    indexOf = index+searchStr.length();
                }else {  //找不到的情况下，再从文件开始位置找一下，防止点击下一个的时候中间出现一次不查询的情况
                    indexOf = 0; //找不到时从头开始查找
                    index = jTextArea.getText().indexOf(searchStr,indexOf);
                    if(index >=0 ){
                        jTextArea.setSelectionStart(index); //设置文本开始选中的位置
                        jTextArea.setSelectionEnd(index+searchStr.length()); //文本结束选中的位置
                        indexOf = index+searchStr.length();
                    }else{
                        indexOf = 0; //找不到时从头开始查找
                        jTextArea.setSelectionStart(0); //设置文本开始选中的位置
                        jTextArea.setSelectionEnd(0);
                    }
                }
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