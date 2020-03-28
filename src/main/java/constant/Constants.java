package constant;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * 常量类
 *
 * @ClassName Constants
 * @Author lrh
 * @Date 2020/1/14 12:23
 * @Version 1.0
 */
public class Constants {

    /**字体设置**/
    public static final String BACKGROUD_COLOR = "#333333";  //窗口背景色
    public static final String FONT_NAME = "微软雅黑"; //字体名称
    public static final int FONT_SIZE = 18; //字体大小
    public static final Font FONT = new Font(FONT_NAME, Font.PLAIN, FONT_SIZE); //字体
    /**文件状态**/
    public static final String FILE_STATE_SAVE = "已保存";
    public static final String FILE_STATE_EDIT = "编辑";
    public static final String FILE_STATE_UNSAVE = "未保存";
    /**图标设置**/
    public static final ImageIcon ICON = new ImageIcon(ClassLoader.getSystemResource("images/tabCard.png"));
    public static final Image IMAGE = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("images/title.gif"));
    /**查找输入框的字符串，共享给替换等其他组件**/
    public static String SEARCH_STR = null;

}
