package frame.panel;

import constant.Constants;
import frame.MyFrame;
import frame.dialog.SearchDialog;
import frame.listener.ComponentListener;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * 定位文本面板
 *
 * @ClassName LocationPanel
 * @Author lrh
 * @Date 2020/4/2 16:30
 * @Version 1.0
 */
public class LocationPanel extends JPanel {
    /**获取选项卡面板对象**/
    private static JTabbedPane jTabbedPane = MyFrame.getJTabbedPane();
    /**提示信息**/
    private JLabel tips = new JLabel();
    /**对话框**/
    private SearchDialog searchDialog;

    public LocationPanel() {
    }
    public LocationPanel(SearchDialog searchDialog){
        this.add(tips);
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
        tips.setFont(Constants.FONT);
        tips.setOpaque(true); //设置背景不是透明的，否则设置背景颜色不起作用
        tips.setBorder(new MatteBorder(1,0,0,0,Color.decode("#e6e4e0")));
        BorderLayout bl=new BorderLayout();  //边界布局，设置控件垂直居中
        tips.setLayout(bl);
        tips.setBounds(0,300,680,30);
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

    public JLabel getTips() {
        return tips;
    }

    public void setTips(JLabel tips) {
        this.tips = tips;
    }
}
