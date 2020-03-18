package frame;

import constant.Constants;
import util.FrameUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
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
    private static JLabel tips = new JLabel();
    /**计数**/
    private JButton searchCount = new JButton("计数");
    /**替换文本**/
    private JLabel replaceTarget = new JLabel("替换为： ");
    /**替换的字符串**/
    private JTextField replaceStr = new JTextField();
    /**替换按钮**/
    private JButton replaceBtn = new JButton("替换");
    /**替换全部按钮**/
    private JButton replaceAllBtn = new JButton("替换全部");
    /**目标文本索引位置**/
    private int indexOf = 0;
    /**文本替换标识**/
    private boolean replaceFlag = false;
    /**下一个文本替换标识**/
    private boolean nextReplaceFlag = false;
    private SearchDialog(){

    }
    private SearchDialog(JFrame jFrame){
        super(jFrame,"查找");
        Container conn = getContentPane();
//        conn.setLayout(new FlowLayout(FlowLayout.LEFT,10,5));
        conn.setLayout(null);
        conn.add(searchTarget);
        conn.add(inputStr);
        conn.add(searchUp);
        conn.add(searchNext);
        conn.add(tips);
        conn.add(searchCount);
        conn.add(replaceTarget);
        conn.add(replaceStr);
        conn.add(replaceBtn);
        conn.add(replaceAllBtn);

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
                tips.setText(""); //清空提示信息
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
        inputStr.setFont(Constants.FONT);
        searchUp.setFont(Constants.FONT);
        searchNext.setFont(Constants.FONT);
        searchTarget.setFont(Constants.FONT);
        searchCount.setFont(Constants.FONT);
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
        searchCount.setBounds(510,160,150,30);
        tips.setBounds(0,335,680,30);

        upActionListener();
        nextActionListener();
        searchCountListener();
        replaceListener();
        replaceAllListener();
    }
    /**
     * 设置红色的错误提示信息
     * @Author lrh
     * @Date 2020/3/15 14:33
     * @Param [msg]
     * @Return void
     */
    public static void setErrorTipsMsg(String msg){
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
    public static void setNormalTipsMsg(String msg){
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
                    FrameUtils.windowJitter(getInstance(),true);
                    return;
                }
                TabCard  tabCard = (TabCard)jTabbedPane.getSelectedComponent();
                JTextArea jTextArea = tabCard.getjTextArea();
                jTextArea.setSelectionColor(Color.green);
                //将鼠标定位到文件末尾
                if(indexOf < 0){
                    indexOf = jTextArea.getText().length();
                }
                int index = jTextArea.getText().lastIndexOf(searchStr,indexOf);
                if(index >= 0 ){
                    jTextArea.setSelectionStart(index); //设置文本开始选中的位置
                    jTextArea.setSelectionEnd(index+searchStr.length()); //文本结束选中的位置
                    indexOf = index-1;
                    replaceFlag = true;
                }else {  //找不到的情况下，再从文件开始位置找一下，防止点击下一个的时候中间出现一次不查询的情况
                    indexOf = jTextArea.getText().length();   //找不到时从尾部开始查找
                    index = jTextArea.getText().lastIndexOf(searchStr,indexOf);
                    if(index >=0 ){
                        jTextArea.setSelectionStart(index); //设置文本开始选中的位置
                        jTextArea.setSelectionEnd(index+searchStr.length()); //文本结束选中的位置
                        indexOf = index-1;
                        replaceFlag = true;
                    }else{
                        indexOf = jTextArea.getText().length();   //找不到时从尾部开始查找
                        jTextArea.setSelectionStart(0); //设置文本开始选中的位置
                        jTextArea.setSelectionEnd(0);
                        setErrorTipsMsg("查找： 无法找到文本\""+searchStr+"\"");
                        FrameUtils.windowJitter(getInstance(),true);
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
                    FrameUtils.windowJitter(getInstance(),true);
                    return;
                }
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
                    replaceFlag = true;
                }else {  //找不到的情况下，再从文件开始位置找一下，防止点击下一个的时候中间出现一次不查询的情况
                    indexOf = 0; //找不到时从头开始查找
                    index = jTextArea.getText().indexOf(searchStr,indexOf);
                    if(index >=0 ){
                        indexOf = index+searchStr.length();
                        jTextArea.setSelectionStart(index); //设置文本开始选中的位置
                        jTextArea.setSelectionEnd(indexOf); //文本结束选中的位置
                        replaceFlag = true;
                    }else{
                        indexOf = 0; //找不到时从头开始查找
                        jTextArea.setSelectionStart(0); //设置文本开始选中的位置
                        jTextArea.setSelectionEnd(0);
                        setErrorTipsMsg("查找： 无法找到文本\""+searchStr+"\"");
                        FrameUtils.windowJitter(getInstance(),true);
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
                           FrameUtils.windowJitter(getInstance(),true);
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
     * 替换字符串
     * @Author lrh
     * @Date 2020/3/15 17:18
     * @Param []
     * @Return void
     */
    private void replaceListener(){
        replaceBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tips.setText(""); //先清空原来的文本
                String sourceStr = inputStr.getText(); //要查询的字符串
                String targetStr = replaceStr.getText(); //要替换的字符串
                if(sourceStr.length() == 0){
                    setErrorTipsMsg("替换： 请输入要查找的字符串");
                    FrameUtils.windowJitter(getInstance(),true);
                    return;
                }
                if(targetStr.length() == 0){
                    setErrorTipsMsg("替换： 请输入要替换的字符串");
                    FrameUtils.windowJitter(getInstance(),true);
                    return;
                }
                TabCard  tabCard = (TabCard)jTabbedPane.getSelectedComponent();
                JTextArea jTextArea = tabCard.getjTextArea();
                jTextArea.setSelectionColor(Color.green);
                if(indexOf < 0){
                    indexOf = 0;
                }
                //判断是否已经找到要替换的字符串
                if(replaceFlag){
                    jTextArea.replaceSelection(targetStr);
                    indexOf = indexOf+targetStr.length();
                }
                int index = jTextArea.getText().indexOf(sourceStr,indexOf);
                if(index >= 0 ){
                    indexOf = index;
                    jTextArea.setSelectionStart(index); //设置文本开始选中的位置
                    jTextArea.setSelectionEnd(index+sourceStr.length()); //文本结束选中的位置
                    replaceFlag = true;
                    setNormalTipsMsg("替换： 已替换1个匹配项，并找到了下一个");
                }else {  //找不到的情况下，再从文件开始位置找一下，防止点击下一个的时候中间出现一次不查询的情况
                    indexOf = 0; //找不到时从头开始查找
                    index = jTextArea.getText().indexOf(sourceStr,indexOf);
                    if(index >=0 ){
                        indexOf = index;
                        jTextArea.setSelectionStart(index); //设置文本开始选中的位置
                        jTextArea.setSelectionEnd(index+sourceStr.length()); //文本结束选中的位置
                        replaceFlag = true;
                        setNormalTipsMsg("替换： 已替换1个匹配项，并找到了下一个");
                    }else{
                        indexOf = 0; //找不到时从头开始查找
                        jTextArea.setSelectionStart(0); //设置文本开始选中的位置
                        jTextArea.setSelectionEnd(0);
                        FrameUtils.windowJitter(getInstance(),true);
                        replaceFlag = false;
                        setErrorTipsMsg("替换： 没有找到匹配项\""+sourceStr+"\"");
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
                    FrameUtils.windowJitter(getInstance(),true);
                    return;
                }
                if(targetStr.length() == 0){
                    setErrorTipsMsg("全部替换： 请输入要替换的字符串");
                    FrameUtils.windowJitter(getInstance(),true);
                    return;
                }
                TabCard  tabCard = (TabCard)jTabbedPane.getSelectedComponent();
                final JTextArea jTextArea = tabCard.getjTextArea();
                jTextArea.setSelectionColor(Color.green);
                final String sourceStr = jTextArea.getText();
                new Thread(new Runnable() {
                    public void run() {
                        int count = matchCount(sourceStr,matchStr);
                        if(count == 0){
                            setErrorTipsMsg("全部替换： 已替换0个匹配项");
                            FrameUtils.windowJitter(getInstance(),true);
                        }else{
                            int index = 0;
                            while(true){
                                int idx = jTextArea.getText().indexOf(matchStr,index);
                                if(idx == -1){
                                    break;
                                }
                                jTextArea.replaceRange(targetStr,index,index + matchStr.length());
                                index = index + targetStr.length();
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
}
