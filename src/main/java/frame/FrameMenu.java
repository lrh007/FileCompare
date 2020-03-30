package frame;

import constant.Constants;

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
    private static JMenuBar jMenuBar = new JMenuBar();
    private static JMenu fileMenu = new JMenu("文件(F)");
    private static JMenu editMenu = new JMenu("编辑(E)");
    private static JMenu searchMenu = new JMenu("搜索(S)");
    private static JMenu viewMenu = new JMenu("视图(V)");
    private static JMenu helpMenu = new JMenu("帮助(H)");
    private static JMenu gameMenu = new JMenu("游戏(U)");
    private static JMenuItem newFile = new JMenuItem("新建(N)    ");
    private static JMenuItem openFile = new JMenuItem("打开(O)    ");
    private static JMenuItem saveFile = new JMenuItem("保存(S)    ");
    private static JMenuItem closeTab = new JMenuItem("关闭(W)    ");
    private static JMenuItem exitSystem = new JMenuItem("退出(Q)    ");
    private static JMenuItem cancelOption = new JMenuItem("撤销(Z)    ");
    private static JMenuItem resetOption = new JMenuItem("恢复(Y)    ");
    private static JMenuItem cutFile = new JMenuItem("剪切(X)    ");
    private static JMenuItem copyFile = new JMenuItem("复制(C)    ");
    private static JMenuItem pasteFile = new JMenuItem("粘贴(V)    ");
    private static JMenuItem searchFile = new JMenuItem("查找(F)    ");
    private static JMenuItem replaceFile = new JMenuItem("替换(R)    ");
    private static JMenuItem eatBeanMenu = new JMenuItem("吃人豆(E)    ");
    private static Font font = Constants.FONT;

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
        addOneMenu(jFrame);
        jFrame.setJMenuBar(jMenuBar);
    }
    /**
     * 添加一级菜单
     * @Author lrh
     * @Date 2020/1/29 10:09
     * @Param []
     * @Return void
     */
    private static void addOneMenu(JFrame jFrame){
        jMenuBar.add(fileMenu);
        jMenuBar.add(editMenu);
        jMenuBar.add(searchMenu);
        jMenuBar.add(viewMenu);
        jMenuBar.add(helpMenu);
        jMenuBar.add(gameMenu);
        /**添加二级菜单**/
        addTwoMenu(jFrame);
    }
    /**
     * 添加二级菜单
     * @Author lrh
     * @Date 2020/1/29 10:10
     * @Param []
     * @Return void
     */
    private static void addTwoMenu(JFrame jFrame){
        /**添加文件按钮的二级菜单**/
        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(closeTab);
        fileMenu.addSeparator(); //添加分割线
        fileMenu.add(exitSystem);
        /**添加编辑按钮的二级菜单**/
        editMenu.add(cancelOption);
        editMenu.add(resetOption);
        editMenu.addSeparator();
        editMenu.add(cutFile);
        editMenu.add(copyFile);
        editMenu.add(pasteFile);
        /**添加搜索按钮的二级菜单**/
        searchMenu.add(searchFile);
        searchMenu.add(replaceFile);
        /**添加游戏按钮的二级菜单**/
        gameMenu.add(eatBeanMenu);
        /**添加菜单的快捷键**/
        addHotKey(jFrame);
    }
    /**
     * 添加菜单的快捷键
     * @Author lrh
     * @Date 2020/1/29 10:13
     * @Param []
     * @Return void
     */
    private static void addHotKey(JFrame jFrame){
        /**添加所有一级菜单的快捷键**/
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
        gameMenu.setFont(font);
        gameMenu.setMnemonic(KeyEvent.VK_U);
        /**添加文件按钮下面所有子菜单的快捷键**/
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
        /**添加编辑按钮下面所有子菜单的快捷键**/
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
        /**添加搜索按钮下面所有子菜单的快捷键**/
        searchFile.setFont(font);
        searchFile.setMnemonic(KeyEvent.VK_F);
        searchFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        replaceFile.setFont(font);
        replaceFile.setMnemonic(KeyEvent.VK_R);
        replaceFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        /**添加游戏-吃人豆按钮的快捷键**/
        eatBeanMenu.setFont(font);
        eatBeanMenu.setMnemonic(KeyEvent.VK_E);
        eatBeanMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        /**添加事件监听**/
        addListener(jFrame);
    }

    /**
     * 添加事件监听
     * @Author lrh
     * @Date 2020/1/29 10:07
     * @Param []
     * @Return void
     */
    private static void addListener(JFrame jFrame){
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
        ComponentListener.searchFileListener(jFrame,searchFile);  //查找文件
        ComponentListener.replaceFileListener(jFrame,replaceFile);//替换文件
        ComponentListener.gameEatBeanListener(eatBeanMenu);       //吃人豆游戏
    }
}
