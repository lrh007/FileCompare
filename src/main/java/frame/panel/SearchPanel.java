package frame.panel;

import constant.Constants;
import frame.listener.ComponentListener;
import frame.MyFrame;
import frame.dialog.SearchDialog;
import frame.TabCard;
import util.FrameUtils;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 查找对话框面板
 *
 * @ClassName SearchPanel
 * @Author lrh
 * @Date 2020/3/25 10:53
 * @Version 1.0
 */
public class SearchPanel extends JPanel{
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
    /**提示信息**/
    private JLabel tips = new JLabel();
    /**计数**/
    private JButton searchCount = new JButton("计数");
    /**目标文本索引位置**/
    private int indexOf = 0;
    /**对话框**/
    private SearchDialog searchDialog;
    private SearchPanel(){

    }
    public SearchPanel(SearchDialog searchDialog){
        this.add(searchTarget);
        this.add(inputStr);
        this.add(searchUp);
        this.add(searchNext);
        this.add(tips);
        this.add(searchCount);
        this.setLayout(null);  //布局管理，这里一定要设置，否则会不显示
        this.setBackground(Color.white);
        this.searchDialog = searchDialog;
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
        inputStr.setFont(Constants.FONT);
        searchUp.setFont(Constants.FONT);
        searchNext.setFont(Constants.FONT);
        searchTarget.setFont(Constants.FONT);
        searchCount.setFont(Constants.FONT);
        tips.setFont(Constants.FONT);
        tips.setOpaque(true); //设置背景不是透明的，否则设置背景颜色不起作用
        tips.setBorder(new MatteBorder(1,0,0,0,Color.decode("#e6e4e0")));
        BorderLayout bl=new BorderLayout();  //边界布局，设置控件垂直居中
        tips.setLayout(bl);


        searchTarget.setBounds(10,10,90,30);
        inputStr.setBounds(100,10,400,30);
        searchUp.setBounds(510,10,70,30);
        searchNext.setBounds(590,10,70,30);
        searchCount.setBounds(510,60,150,30);
        tips.setBounds(0,300,680,30);

        upActionListener();
        nextActionListener();
        searchCountListener();
        ComponentListener.inputSearchStrListener(inputStr);
    }
    /**
     * 设置红色的错误提示信息
     * @Author lrh
     * @Date 2020/3/15 14:33
     * @Param [msg]
     * @Return void
     */
    public void setErrorTipsMsg(String msg){
        tips.setForeground(Color.RED);
        tips.setText(msg);
    }
    /**
     * 设置绿色的正常提示信息
     * @Author lrh
     * @Date 2020/3/15 14:33
     * @Param [msg]
     * @Return void
     */
    public void setNormalTipsMsg(String msg){
        tips.setForeground(Color.decode("#42be3b"));
        tips.setText(msg);
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
                tips.setText(""); //先清空原来的文本
                String searchStr = inputStr.getText();
                if(searchStr.length() == 0){
                    setErrorTipsMsg("查找： 请输入要查找的字符串");
                    FrameUtils.windowJitter(searchDialog,true);
                    return;
                }
                TabCard tabCard = (TabCard)jTabbedPane.getSelectedComponent();
                JTextArea jTextArea = tabCard.getjTextArea();
                jTextArea.setSelectionColor(Color.green);
                //获取选中文本的开始索引位置
                indexOf = jTextArea.getSelectionStart()-1;
                int index = jTextArea.getText().lastIndexOf(searchStr,indexOf);
                if(index >= 0 ){
                    jTextArea.setSelectionStart(index); //设置文本开始选中的位置
                    jTextArea.setSelectionEnd(index+searchStr.length()); //文本结束选中的位置
                }else {  //找不到的情况下，再从文件开始位置找一下，防止点击下一个的时候中间出现一次不查询的情况
                    indexOf = jTextArea.getText().length();   //找不到时从尾部开始查找
                    index = jTextArea.getText().lastIndexOf(searchStr,indexOf);
                    if(index >=0 ){
                        jTextArea.setSelectionStart(index); //设置文本开始选中的位置
                        jTextArea.setSelectionEnd(index+searchStr.length()); //文本结束选中的位置
                    }else{
                        jTextArea.setSelectionStart(0); //设置文本开始选中的位置
                        jTextArea.setSelectionEnd(0);
                        setErrorTipsMsg("查找： 无法找到文本\""+searchStr+"\"");
                        FrameUtils.windowJitter(searchDialog,true);
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
                tips.setText(""); //先清空原来的文本
                String searchStr = inputStr.getText();
                if(searchStr.length() == 0){
                    setErrorTipsMsg("查找： 请输入要查找的字符串");
                    FrameUtils.windowJitter(searchDialog,true);
                    return;
                }
                TabCard  tabCard = (TabCard)jTabbedPane.getSelectedComponent();
                JTextArea jTextArea = tabCard.getjTextArea();
                jTextArea.setSelectionColor(Color.green);
                //获取选中文本的结束索引位置
                indexOf = jTextArea.getSelectionEnd();
                if(indexOf >= jTextArea.getText().length()){
                    indexOf = 0;
                }
                int index = jTextArea.getText().indexOf(searchStr,indexOf);
                if(index >= 0 ){
                    jTextArea.setSelectionStart(index); //设置文本开始选中的位置
                    jTextArea.setSelectionEnd(index+searchStr.length()); //文本结束选中的位置
                }else {  //找不到的情况下，再从文件开始位置找一下，防止点击下一个的时候中间出现一次不查询的情况
                    indexOf = 0; //找不到时从头开始查找
                    index = jTextArea.getText().indexOf(searchStr,indexOf);
                    if(index >=0 ){
                        jTextArea.setSelectionStart(index); //设置文本开始选中的位置
                        jTextArea.setSelectionEnd(index+searchStr.length()); //文本结束选中的位置
                    }else{
                        jTextArea.setSelectionStart(0); //设置文本开始选中的位置
                        jTextArea.setSelectionEnd(0);
                        setErrorTipsMsg("查找： 无法找到文本\""+searchStr+"\"");
                        FrameUtils.windowJitter(searchDialog,true);
                    }
                }
            }
        });
    }
    /**
     * 计数事件监听
     * @Author lrh
     * @Date 2020/3/15 15:22
     * @Param []
     * @Return void
     */
    private void searchCountListener(){
        searchCount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tips.setText(""); //先清空原来的文本
                final String matchStr = inputStr.getText();
                TabCard  tabCard = (TabCard)jTabbedPane.getSelectedComponent();
                JTextArea jTextArea = tabCard.getjTextArea();
                jTextArea.setSelectionColor(Color.green);
                final String sourceStr = jTextArea.getText();
                new Thread(new Runnable() {
                    public void run() {
                        int count = matchCount(sourceStr,matchStr);
                        if(count == 0){
                            setErrorTipsMsg("计数： 0次匹配");
                            FrameUtils.windowJitter(searchDialog,true);
                        }else{
                            setNormalTipsMsg("计数： "+count+"次匹配");
                        }
                        inputStr.requestFocus(); //输入框获取焦点
                        inputStr.selectAll();
                    }
                }).start();
            }
        });
    }
    /**
     * 查询文本匹配次数
     * @Author lrh
     * @Date 2020/3/15 15:39
     * @Param [str, index, count]
     * @Return int
     */
    private int matchCount(String sourceStr,String matchStr){
        if(matchStr.length() == 0){
            return 0;
        }
        if("\\".equals(matchStr)){
            matchStr = "\\\\";
        }
        Matcher matcher = Pattern.compile(matchStr).matcher(sourceStr);
        int count = 0;
        while(matcher.find()){
            count++;
        }
        return count;
    }

    public JTextField getInputStr() {
        return inputStr;
    }

    public void setInputStr(JTextField inputStr) {
        this.inputStr = inputStr;
    }

    public JButton getSearchUp() {
        return searchUp;
    }

    public void setSearchUp(JButton searchUp) {
        this.searchUp = searchUp;
    }

    public JButton getSearchNext() {
        return searchNext;
    }

    public void setSearchNext(JButton searchNext) {
        this.searchNext = searchNext;
    }

    public JLabel getSearchTarget() {
        return searchTarget;
    }

    public void setSearchTarget(JLabel searchTarget) {
        this.searchTarget = searchTarget;
    }

    public JLabel getTips() {
        return tips;
    }

    public void setTips(JLabel tips) {
        this.tips = tips;
    }

    public JButton getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(JButton searchCount) {
        this.searchCount = searchCount;
    }

    public int getIndexOf() {
        return indexOf;
    }

    public void setIndexOf(int indexOf) {
        this.indexOf = indexOf;
    }
}
