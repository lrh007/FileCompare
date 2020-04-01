package frame;

import constant.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import static java.awt.Font.*;

/**
 * 显示行号
 *
 * @ClassName LineNumberHeaderView
 * @Author lrh
 * @Date 2020/1/15 10:33
 * @Version 1.0
 */
public class LineNumberHeaderView extends javax.swing.JComponent {

    private static final long serialVersionUID = 1L;
    private Font font = new Font("微软雅黑",Font.PLAIN,18);
    private static AffineTransform atf = new AffineTransform();
    private static FontRenderContext frc = new FontRenderContext(atf, true,true);
    private LineNumberHeaderView instance = this;
    private JTextArea jTextArea;

    public LineNumberHeaderView(){

    }
    public LineNumberHeaderView(JTextArea jTextArea) {
        this.setFont(font);
        this.setForeground(Color.gray);
        this.jTextArea = jTextArea;
        setPreferredSize((int) font.getStringBounds(String.valueOf(jTextArea.getLineCount()),frc).getWidth(),30);
    }
    /**
     * 设置组件的大小
     * @Author lrh
     * @Date 2020/3/31 10:13
     * @Param [width, height]
     * @Return void
     */
    public void setPreferredSize(int width,int height){
        this.setPreferredSize(new Dimension(width+40,height));
    }
    /**   
     * 绘制组件行数
     * @Author lrh
     * @Date 2020/3/31 10:15
     * @Param [g]
     * @Return void
     */
    @Override
    protected void paintComponent(Graphics g) {
        int lineCount = jTextArea.getLineCount();
        //初始化字符串位置
        int x = 20;
        int y = 0;
        for(int i=1;i <= lineCount;i++){
            y+=font.getStringBounds(String.valueOf(i),frc).getHeight()+1;
            g.drawString(String.valueOf(i),x,y);
        }
        //设置组件宽度和高度
        setPreferredSize((int) font.getStringBounds(String.valueOf(lineCount),frc).getWidth(),lineCount*60);
        System.out.println("重新绘制组件");
    }
    /***
     * 异步刷新页面
     * @Author lrh
     * @Date 2020/3/31 15:11
     * @Param []
     * @Return void
     */
    public void asynRepaint(){
        instance.repaint();
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                instance.repaint();
            }
        }).start();*/
    }
}