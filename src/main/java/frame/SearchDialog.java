package frame;

import javax.swing.*;
import java.awt.*;
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
    private SearchDialog(){
        
    }
    private SearchDialog(JFrame jFrame){
        super(jFrame,"查找");
        Container conn = getContentPane();
        conn.add(new JLabel("test lable"));
        setSize(300,200);
        int x = jFrame.getWidth()/2-this.getWidth()/2;
        int y = jFrame.getHeight()/2-this.getHeight()/2;
        setLocation(x,y);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                INSTANCE = null;
            }
        });
        setVisible(true);

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
    
}
