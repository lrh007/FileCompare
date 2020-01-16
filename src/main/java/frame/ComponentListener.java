package frame;

import javafx.scene.control.Labeled;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 组件事件监听器
 *
 * @ClassName ComponentListener
 * @Author lrh
 * @Date 2020/1/16 11:26
 * @Version 1.0
 */
public class ComponentListener {
    /**
     * jTextArea事件监听
     * @Author lrh
     * @Date 2020/1/15 19:20
     * @Param []
     * @Return void
     */
    public static void jTextAreaListener(JTextArea jTextArea){
        jTextArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                System.out.println("输入数据");
            }

            public void removeUpdate(DocumentEvent e) {
                System.out.println("删除数据");
            }

            public void changedUpdate(DocumentEvent e) {
                System.out.println("更新数据");
            }
        });
    }
    /**
     * 新建文件事件监听
     * @Author lrh
     * @Date 2020/1/16 11:20
     * @Param []
     * @Return void
     */
    public static void newFileListener(final JFrame jFrame,final JMenuItem jMenuItem,final JTextArea jTextArea) {
        jMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jTextArea.setText(null); //清空内容
            }
        });
    }
    /**
     * 打开文件事件监听
     * @Author lrh
     * @Date 2020/1/14 15:34
     * @Param [jFrame, jMenuItem]
     * @Return void
     */
    public static void openFileListener(final JFrame jFrame, final JMenuItem jMenuItem,final JTextArea jTextArea){
        jMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                if (jFileChooser.showOpenDialog(jMenuItem)==JFileChooser.APPROVE_OPTION) {
                    File file = jFileChooser.getSelectedFile();
                    readFile(file,jTextArea);
                };
            }
        });
    }
    /**
     * 读取文件
     * @Author lrh
     * @Date 2020/1/14 19:33
     * @Param [file]
     * @Return void
     */
    private static void readFile(File file,JTextArea jTextArea){
        try {
            long startTime = System.currentTimeMillis();
            jTextArea.setText(null);
            FileChannel fileChannel = new FileInputStream(file).getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1048576);  //每次读取1M
            int len = 0;
            while((len = fileChannel.read(byteBuffer))!=-1){
                jTextArea.append(new String(byteBuffer.array(),0,len,"UTF-8"));
                byteBuffer.clear();
            }
            fileChannel.close();
            System.out.println("读取数据一共："+jTextArea.getLineCount()+" 条，耗时："+(System.currentTimeMillis()-startTime)+" ms");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,"未找到文件");
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"打开文件失败");
            e.printStackTrace();
        }
    }
    /**
     * 退出，关闭窗口事件监听
     * @Author lrh
     * @Date 2020/1/14 13:24
     * @Param []
     * @Return void
     */
    public static void exitSystemListener(final JFrame jFrame, JMenuItem jMenuItem){
        jMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(jFrame, "确定退出？", "提示", JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
    }
}
