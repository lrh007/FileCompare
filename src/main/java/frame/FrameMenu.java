package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * 窗口菜单
 *
 * @ClassName FrameMenu
 * @Author lrh
 * @Date 2020/1/16 13:00
 * @Version 1.0
 */
public class FrameMenu {

    private FrameMenu() {
    }

    /**
     * 添加菜单栏
     * @Author lrh
     * @Date 2020/1/14 12:49
     * @Param []
     * @Return void
     */
    public static void addMenu(final JFrame jFrame){
        JMenuBar jMenuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("文件(F)");
        JMenu editMenu = new JMenu("编辑(E)");
        JMenu searchMenu = new JMenu("搜索(S)");
        JMenu viewMenu = new JMenu("视图(V)");
        JMenu helpMenu = new JMenu("帮助(H)");
        Font font = new Font("微软雅黑",Font.PLAIN,18);
        fileMenu.setFont(font);
        fileMenu.setMnemonic(KeyEvent.VK_F);
        editMenu.setFont(font);
        editMenu.setMnemonic(KeyEvent.VK_E);
        searchMenu.setFont(font);
        searchMenu.setMnemonic(KeyEvent.VK_S);
        viewMenu.setFont(font);
        viewMenu.setMnemonic(KeyEvent.VK_V);
        helpMenu.setFont(font);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        jMenuBar.add(fileMenu);
        jMenuBar.add(editMenu);
        jMenuBar.add(searchMenu);
        jMenuBar.add(viewMenu);
        jMenuBar.add(helpMenu);
        JMenuItem newFile = new JMenuItem("新建(N)    ");
        JMenuItem openFile = new JMenuItem("打开(O)   ");
        JMenuItem saveFile = new JMenuItem("保存(S)   ");
        JMenuItem closeTab = new JMenuItem("关闭(W)   ");
        JMenuItem exitSystem = new JMenuItem("退出(Q) ");
        JMenuItem cancelOption = new JMenuItem("撤销(Z)   ");
        JMenuItem resetOption = new JMenuItem("恢复(Y)    ");
        JMenuItem cutFile = new JMenuItem("剪切(X)    ");
        JMenuItem copyFile = new JMenuItem("复制(C)   ");
        JMenuItem pasteFile = new JMenuItem("粘贴(V)  ");
        newFile.setFont(font);
        newFile.setMnemonic(KeyEvent.VK_N);
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        openFile.setFont(font);
        openFile.setMnemonic(KeyEvent.VK_O);
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
        saveFile.setFont(font);
        saveFile.setMnemonic(KeyEvent.VK_S);
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
        closeTab.setFont(font);
        closeTab.setMnemonic(KeyEvent.VK_W);
        closeTab.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,ActionEvent.CTRL_MASK));
        exitSystem.setFont(font);
        exitSystem.setMnemonic(KeyEvent.VK_Q);
        exitSystem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,ActionEvent.CTRL_MASK));

        cancelOption.setFont(font);
        cancelOption.setMnemonic(KeyEvent.VK_Z);
        cancelOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        resetOption.setFont(font);
        resetOption.setMnemonic(KeyEvent.VK_Y);
        resetOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));

        copyFile.setFont(font);
        copyFile.setMnemonic(KeyEvent.VK_C);
        copyFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        cutFile.setFont(font);
        cutFile.setMnemonic(KeyEvent.VK_X);
        cutFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        pasteFile.setFont(font);
        pasteFile.setMnemonic(KeyEvent.VK_V);
        pasteFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));

        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(closeTab);
        fileMenu.addSeparator(); //添加分割线
        fileMenu.add(exitSystem);

        editMenu.add(cancelOption);
        editMenu.add(resetOption);
        editMenu.addSeparator();
        editMenu.add(cutFile);
        editMenu.add(copyFile);
        editMenu.add(pasteFile);
        jFrame.setJMenuBar(jMenuBar);

        ComponentListener.exitSystemListener(jFrame,exitSystem);  //退出系统
        ComponentListener.openFileListener(jFrame,openFile);      //打开文件
        ComponentListener.newFileListener(jFrame,newFile);        //新建文件
        ComponentListener.closeTabListener(jFrame,closeTab);      //关闭当前选项卡
        ComponentListener.saveFileListener(jFrame,saveFile);      //保存文件

        ComponentListener.cancelOptionListener(cancelOption);     //撤销
        ComponentListener.resetOptionListener(resetOption);       //恢复
        ComponentListener.copyFileListener(copyFile);             //复制
        ComponentListener.cutFileListener(cutFile);               //剪切
        ComponentListener.pasteFileListener(pasteFile);           //粘贴

    }
}
