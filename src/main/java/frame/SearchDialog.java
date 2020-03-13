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
    /**查询上一个按钮**/
    private JButton searchUp = new JButton("<<");
    /**查询下一个按钮**/
    private JButton searchNext = new JButton(">>");
    /**查找目标文本**/
    private JLabel searchTarget = new JLabel("查找目标: ");
    /**目标文本索引位置**/
    private int indexOf = 0;
    private SearchDialog(){

    }
    private SearchDialog(JFrame jFrame){
        super(jFrame,"查找");
        Container conn = getContentPane();
//        conn.setLayout(new FlowLayout(FlowLayout.LEFT,10,5));
        conn.setLayout(new GridLayout(5,1,10,10));
        JLabel search = new JLabel(); //放置搜索组件
        search.setLayout(new FlowLayout(FlowLayout.LEFT,10,5));
        search.add(searchTarget);
        search.add(inputStr);
        search.add(searchUp);
        search.add(searchNext);

        conn.add(search);
        conn.setBackground(Color.white);
        this.setSize(680,400);
        this.setResizable(false);
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
     * 初始化参数
     * @Author lrh
     * @Date 2020/3/10 20:53
     * @Param []
     * @Return void
     */
    private void init(){
        inputStr.setPreferredSize(new Dimension(400,30));
        inputStr.setFont(Constants.FONT);
        searchUp.setFont(Constants.FONT);
        searchNext.setFont(Constants.FONT);
        searchTarget.setFont(Constants.FONT);
        upActionListener();
        nextActionListener();
    }
    /**
     * 查找上一个事件监听
     * @Author lrh
     * @Date 2020/3/10 20:53
     * @Param []
     * @Return void
     */
    private void upActionListener(){
        //查找上一个
        searchUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchStr = inputStr.getText();
                TabCard  tabCard = (TabCard)jTabbedPane.getSelectedComponent();
                JTextArea jTextArea = tabCard.getjTextArea();
                jTextArea.setSelectionColor(Color.green);
                //将鼠标定位到文件末尾
                if(indexOf <= 0){
                    indexOf = jTextArea.getText().length();
                }
                int index = jTextArea.getText().lastIndexOf(searchStr,indexOf);
                if(index >= 0 ){
                    jTextArea.setSelectionStart(index); //设置文本开始选中的位置
                    jTextArea.setSelectionEnd(index+searchStr.length()); //文本结束选中的位置
                    indexOf = index-1;
                }else {  //找不到的情况下，再从文件开始位置找一下，防止点击下一个的时候中间出现一次不查询的情况
                    indexOf = jTextArea.getText().length();   //找不到时从尾部开始查找
                    index = jTextArea.getText().lastIndexOf(searchStr,indexOf);
                    if(index >=0 ){
                        jTextArea.setSelectionStart(index); //设置文本开始选中的位置
                        jTextArea.setSelectionEnd(index+searchStr.length()); //文本结束选中的位置
                        indexOf = index-1;
                    }else{
                        indexOf = jTextArea.getText().length();   //找不到时从尾部开始查找
                        jTextArea.setSelectionStart(0); //设置文本开始选中的位置
                        jTextArea.setSelectionEnd(0);
                    }
                }
            }
        });
    }



    /**   
     * 查找下一个事件监听
     * @Author lrh
     * @Date 2020/3/10 20:53
     * @Param []
     * @Return void
     */
    private void nextActionListener(){
        //查找下一个
        searchNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchStr = inputStr.getText();
                TabCard  tabCard = (TabCard)jTabbedPane.getSelectedComponent();
                JTextArea jTextArea = tabCard.getjTextArea();
                jTextArea.setSelectionColor(Color.green);
                if(indexOf < 0){
                    indexOf = 0;
                }
                int index = jTextArea.getText().indexOf(searchStr,indexOf);
                if(index >= 0 ){
                    indexOf = index+searchStr.length();
                    jTextArea.setSelectionStart(index); //设置文本开始选中的位置
                    jTextArea.setSelectionEnd(indexOf); //文本结束选中的位置
                }else {  //找不到的情况下，再从文件开始位置找一下，防止点击下一个的时候中间出现一次不查询的情况
                    indexOf = 0; //找不到时从头开始查找
                    index = jTextArea.getText().indexOf(searchStr,indexOf);
                    if(index >=0 ){
                        indexOf = index+searchStr.length();
                        jTextArea.setSelectionStart(index); //设置文本开始选中的位置
                        jTextArea.setSelectionEnd(indexOf); //文本结束选中的位置
                    }else{
                        indexOf = 0; //找不到时从头开始查找
                        jTextArea.setSelectionStart(0); //设置文本开始选中的位置
                        jTextArea.setSelectionEnd(0);
                    }
                }
            }
        });
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
