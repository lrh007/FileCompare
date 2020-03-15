package util;

import java.awt.*;

/**
 * 工具类
 *
 * @ClassName FrameUtils
 * @Author lrh
 * @Date 2020/3/15 15:09
 * @Version 1.0
 */
public class FrameUtils {

    /**
     * 窗口左右抖动
     * commponent 要进行抖动的窗口组件
     * beep true 发出系统提示音，false 没有提示音
     * @Author lrh
     * @Date 2020/3/15 14:49
     * @Param [component,beep]
     * @Return void
     */
    public static void windowJitter(Component component, boolean beep){
        //发出系统提示音
        if(beep){
            try {
                Toolkit.getDefaultToolkit().beep();
            }catch (Exception e){
                System.out.println("发出系统提示声音异常");
            }
        }
        Point oldLocation = component.getLocation(); //记录原始位置
        int x = (int)oldLocation.getX();
        try {
            //窗口左右抖动
            for (int i = 0; i < 20; i++) {
                Thread.sleep(10);
                if(i % 2 ==0){
                    x+=5;
                }else{
                    x-=5;
                }
                component.setLocation(x,(int)oldLocation.getY());
            }
            //恢复原来的位置
            component.setLocation(oldLocation);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
