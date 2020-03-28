package frame.panel;

import constant.Constants;
import frame.ComponentListener;
import frame.MyFrame;
import frame.SearchDialog;
import frame.TabCard;
import util.FrameUtils;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 替换对话框面板
 *
 * @ClassName ReplacePanel
 * @Author lrh
 * @Date 2020/3/25 11:11
 * @Version 1.0
 */
public class ReplacePanel extends JPanel {
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
    /**替换文本**/
    private JLabel replaceTarget = new JLabel("替换为： ");
    /**替换的字符串**/
    private JTextField replaceStr = new JTextField();
    /**替换按钮**/
    private JButton replaceBtn = new JButton("替换");
    /**替换全部按钮**/
    private JButton replaceAllBtn = new JButton("全部替换");
    /**目标文本索引位置**/
    private int indexOf = 0;
    /**对话框对象**/
    private SearchDialog searchDialog;
    private ReplacePanel(){

    }
    public ReplacePanel(SearchDialog searchDialog){
        this.add(searchTarget);
        this.add(inputStr);
        this.add(searchUp);
        this.add(searchNext);
        this.add(tips);
        this.add(replaceTarget);
        this.add(replaceStr);
        this.add(replaceBtn);
        this.add(replaceAllBtn);
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
        replaceTarget.setFont(Constants.FONT);
        replaceStr.setFont(Constants.FONT);
        replaceBtn.setFont(Constants.FONT);
        replaceAllBtn.setFont(Constants.FONT);
        tips.setFont(Constants.FONT);
        tips.setOpaque(true); //设置背景不是透明的，否则设置背景颜色不起作用
        tips.setBorder(new MatteBorder(1,0,0,0,Color.decode("#e6e4e0")));
        BorderLayout bl=new BorderLayout();  //边界布局，设置控件垂直居中
        tips.setLayout(bl);

        searchTarget.setBounds(10,10,90,30);
        inputStr.setBounds(100,10,400,30);
        searchUp.setBounds(510,10,70,30);
        searchNext.setBounds(590,10,70,30);
        replaceTarget.setBounds(10,60,90,30);
        replaceStr.setBounds(100,60,400,30);
        replaceBtn.setBounds(510,60,150,30);
        replaceAllBtn.setBounds(510,110,150,30);
        tips.setBounds(0,300,680,30);

        upActionListener();
        nextActionListener();
        replaceListener();
        replaceAllListener();
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
                indexOf = jTextArea.getSelectionEnd()+1;
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
     * 替换字符串，从上到下
     * @Author lrh
     * @Date 2020/3/15 17:18
     * @Param []
     * @Return void
     */
    private void replaceListener(){
        replaceBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tips.setText(""); //先清空原来的文本
                boolean replaceFlag = false; //替换标识
                String sourceStr = inputStr.getText(); //要查询的字符串
                String targetStr = replaceStr.getText(); //要替换的字符串
                if(sourceStr.length() == 0){
                    setErrorTipsMsg("替换： 请输入要查找的字符串");
                    FrameUtils.windowJitter(searchDialog,true);
                    return;
                }
                if(targetStr.length() == 0){
                    setErrorTipsMsg("替换： 请输入要替换的字符串");
                    FrameUtils.windowJitter(searchDialog,true);
                    return;
                }
                TabCard  tabCard = (TabCard)jTabbedPane.getSelectedComponent();
                JTextArea jTextArea = tabCard.getjTextArea();
                jTextArea.setSelectionColor(Color.green);
                //判断是否已经找到要替换的字符串
                if(jTextArea.getSelectionStart() == jTextArea.getSelectionEnd()){
                    //获取选中文本的结束索引位置
                    indexOf = jTextArea.getSelectionEnd()+1;
                    if(indexOf >= jTextArea.getText().length()){
                        indexOf = 0;
                    }
                }else{
                    jTextArea.replaceSelection(targetStr);
                    replaceFlag = true;
                }
                int index = jTextArea.getText().indexOf(sourceStr,indexOf);
                if(index >= 0 ){
                    jTextArea.setSelectionStart(index); //设置文本开始选中的位置
                    jTextArea.setSelectionEnd(index+sourceStr.length()); //文本结束选中的位置
                    if(replaceFlag){
                        setNormalTipsMsg("替换： 已替换1个匹配项，并找到了下一个");
                    }
                }else {  //找不到的情况下，再从文件开始位置找一下，防止点击下一个的时候中间出现一次不查询的情况
                    indexOf = 0; //找不到时从头开始查找
                    index = jTextArea.getText().indexOf(sourceStr,indexOf);
                    if(index >=0 ){
                        jTextArea.setSelectionStart(index); //设置文本开始选中的位置
                        jTextArea.setSelectionEnd(index+sourceStr.length()); //文本结束选中的位置
                        if(replaceFlag){
                            setNormalTipsMsg("替换： 已替换1个匹配项，并找到了下一个");
                        }
                    }else{
                        jTextArea.setSelectionStart(0); //设置文本开始选中的位置
                        jTextArea.setSelectionEnd(0);
                        if(replaceFlag){
                            setNormalTipsMsg("替换： 已替换1个匹配项，没有找到下一个");
                        }else{
                            FrameUtils.windowJitter(searchDialog,true);
                            setErrorTipsMsg("替换： 没有找到匹配项\""+sourceStr+"\"");
                        }
                    }
                }
            }
        });
    }
    /**
     * 替换全部事件监听
     * @Author lrh
     * @Date 2020/3/15 20:37
     * @Param []
     * @Return void
     */
    private void replaceAllListener(){
        replaceAllBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tips.setText(""); //先清空原来的文本
                final String matchStr = inputStr.getText(); //查找的文本
                final String targetStr = replaceStr.getText(); //要替换的文本
                if(matchStr.length() == 0){
                    setErrorTipsMsg("全部替换： 请输入要查找的字符串");
                    FrameUtils.windowJitter(searchDialog,true);
                    return;
                }
                if(targetStr.length() == 0){
                    setErrorTipsMsg("全部替换： 请输入要替换的字符串");
                    FrameUtils.windowJitter(searchDialog,true);
                    return;
                }
                TabCard  tabCard = (TabCard)jTabbedPane.getSelectedComponent();
                final JTextArea jTextArea = tabCard.getjTextArea();
                jTextArea.setSelectionColor(Color.green);
                jTextArea.setSelectionStart(0);
                jTextArea.setSelectionEnd(0);
                final String sourceStr = jTextArea.getText();
                new Thread(new Runnable() {
                    public void run() {
                        int count = matchCount(sourceStr,matchStr);
                        if(count == 0){
                            setErrorTipsMsg("全部替换： 已替换0个匹配项");
                            FrameUtils.windowJitter(searchDialog,true);
                        }else{
                            int index = 0;
                            while(true){
                                int idx = jTextArea.getText().indexOf(matchStr,index);
                                if(idx == -1){
                                    break;
                                }
                                jTextArea.replaceRange(targetStr,idx,idx + matchStr.length());
                                index = idx + targetStr.length();
                            }
                            setNormalTipsMsg("全部替换： 已替换"+count+"个匹配项");
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

    public JLabel getReplaceTarget() {
        return replaceTarget;
    }

    public void setReplaceTarget(JLabel replaceTarget) {
        this.replaceTarget = replaceTarget;
    }

    public JTextField getReplaceStr() {
        return replaceStr;
    }

    public void setReplaceStr(JTextField replaceStr) {
        this.replaceStr = replaceStr;
    }

    public JButton getReplaceBtn() {
        return replaceBtn;
    }

    public void setReplaceBtn(JButton replaceBtn) {
        this.replaceBtn = replaceBtn;
    }

    public JButton getReplaceAllBtn() {
        return replaceAllBtn;
    }

    public void setReplaceAllBtn(JButton replaceAllBtn) {
        this.replaceAllBtn = replaceAllBtn;
    }

    public int getIndexOf() {
        return indexOf;
    }

    public void setIndexOf(int indexOf) {
        this.indexOf = indexOf;
    }

}
