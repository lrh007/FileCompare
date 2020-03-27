package frame;


import constant.Constants;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.*;
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

    private static JTabbedPane jTabbedPane = MyFrame.getJTabbedPane();   //获取选项卡面板对象

    private ComponentListener() {
    }

    /**
     * jTextArea事件监听
     * @Author lrh
     * @Date 2020/1/15 19:20
     * @Param []
     * @Return void
     */
    public static void jTextAreaListener(JTextArea jTextArea, final UndoManager undoManager){
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
        //添加撤销/恢复事件监听
        jTextArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
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
    public static void newFileListener(final JFrame jFrame,final JMenuItem jMenuItem) {
        jMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TabCard tabCard = new TabCard(); //创建选项卡对象
                int tabCount = jTabbedPane.getTabCount(); //选项卡总个数
                tabCount++; //总个数+1 等于名称
                String fileName = "new "+tabCount; //新文件名称
                tabCard.setFileName(fileName); //保存文件名称
                jTabbedPane.addTab(fileName, Constants.ICON,tabCard,fileName);
                jTabbedPane.setSelectedComponent(tabCard);  //选中当前选项卡
                jTextAreaListener(tabCard.getjTextArea(),tabCard.getUndoManager()); //添加事件监听
            }
        });
    }
    /**   
     * 关闭当前选项卡事件监听
     * @Author lrh
     * @Date 2020/1/16 18:34
     * @Param []
     * @Return void
     */
    public static void closeTabListener(final JFrame jFrame,final JMenuItem jMenuItem){
        jMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Component selectedComponent = jTabbedPane.getSelectedComponent(); //获取当前选中的选项卡
                jTabbedPane.remove(selectedComponent);
                int tabCount = jTabbedPane.getTabCount(); //选项卡总个数
                //选项卡全部关闭之后退出
                if(tabCount == 0){
                    System.exit(0);
                }

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
    public static void openFileListener(final JFrame jFrame, final JMenuItem jMenuItem){
        jMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                if (jFileChooser.showOpenDialog(jMenuItem)==JFileChooser.APPROVE_OPTION) {
                    final File file = jFileChooser.getSelectedFile();
                    final TabCard tabCard = new TabCard(); //创建选项卡对象
                    tabCard.setAbsoluteFilePath(file.getAbsolutePath()); //保存文件绝对路径
                    tabCard.setFileName(file.getName());   //保存文件名称（短名称）
                    jTabbedPane.addTab(file.getName(),Constants.ICON,tabCard,file.getAbsolutePath());
                    jTabbedPane.setSelectedComponent(tabCard);  //选中当前选项卡
                    //将文件的读取放到一个单独的线程中，防止界面发生卡顿
                    new Thread(new Runnable() {
                        public void run() {
                            readFile(file,tabCard.getjTextArea());
                            jTextAreaListener(tabCard.getjTextArea(),tabCard.getUndoManager()); //添加事件监听
                        }
                    }).start();

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
    
    /**   
     * 保存文件事件监听
     * @Author lrh
     * @Date 2020/1/19 20:17
     * @Param [jFrame, jMenuItem]
     * @Return void
     */
    public static void saveFileListener(final JFrame jFrame,JMenuItem jMenuItem){
        jMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TabCard tabCard = (TabCard) jTabbedPane.getSelectedComponent();//获取当前选中的tab卡对象
                JTextArea jTextArea = tabCard.getjTextArea();
                String text = jTextArea.getText(); //获取当前所有的文件内容
                int selectedIndex = jTabbedPane.getSelectedIndex(); //获取当前选中的tab卡的索引
                try{

                    if(tabCard.getAbsoluteFilePath() == null){ //当前文件是新建的文件
                        //打开文件选择框
                        JFileChooser jFileChooser = new JFileChooser();
                        jFileChooser.setSelectedFile(new File(tabCard.getFileName()));
                        int result = jFileChooser.showSaveDialog(jFrame);
                        if(result == JFileChooser.APPROVE_OPTION){
                            File file = jFileChooser.getSelectedFile();
                            jTabbedPane.setTitleAt(selectedIndex,file.getName());  //重新设置标题
                            jTabbedPane.setToolTipTextAt(selectedIndex,file.getAbsolutePath()); //重新设置提示信息
                            tabCard.setFileName(file.getName());
                            tabCard.setAbsoluteFilePath(file.getAbsolutePath());
                            writeFile(file,text);
                        }
                    }else{ //当前文件是打开的文件
                        String fileName = tabCard.getFileName();   //获取文件名称
                        String filePath = tabCard.getAbsoluteFilePath(); //获取文件绝对路径
                        File file = new File(filePath);
                        writeFile(file,text);
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null,"保存失败，异常信息="+ex.getMessage());
                    System.out.println("文件保存失败，异常信息="+ex);
                    throw new RuntimeException(ex);
                }

            }
        });
    }
    /**
     * 写入文件
     * @Author lrh
     * @Date 2020/1/19 22:27
     * @Param [file]
     * @Return void
     */
    private static void writeFile(final File file, final String text) throws Exception {
        //将文件保存放到单独的线程中，防止界面卡顿
        new Thread(new Runnable() {
            public void run() {
                try{
                    FileChannel fileChannel = new FileOutputStream(file).getChannel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(text.getBytes().length);
                    byteBuffer.put(text.getBytes());
                    byteBuffer.flip(); //切换成读取模式
                    while(byteBuffer.hasRemaining()){
                        fileChannel.write(byteBuffer);//写入文件
                    }
                    fileChannel.close();
                    byteBuffer.clear();
                    JOptionPane.showMessageDialog(null,"保存成功");

                }catch (Exception ex){
                    throw new RuntimeException("保存文件异常",ex);
                }
            }
        }).start();
    }
    /**   
     * 复制文件事件监听器
     * @Author lrh
     * @Date 2020/1/29 9:05
     * @Param [jMenuItem]
     * @Return void
     */
    public static void copyFileListener(JMenuItem jMenuItem){
        jMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TabCard tabCard = (TabCard) jTabbedPane.getSelectedComponent();//获取当前选中的tab卡对象
                JTextArea jTextArea = tabCard.getjTextArea();
                jTextArea.copy();
            }
        });
    }
    /**   
     * 剪切文件事件监听器
     * @Author lrh
     * @Date 2020/1/29 9:06
     * @Param [jMenuItem]
     * @Return void
     */
    public static void cutFileListener(JMenuItem jMenuItem){
        jMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TabCard tabCard = (TabCard) jTabbedPane.getSelectedComponent();//获取当前选中的tab卡对象
                JTextArea jTextArea = tabCard.getjTextArea();
                jTextArea.cut();
            }
        });
    }
    /**   
     * 粘贴文件事件监听器
     * @Author lrh
     * @Date 2020/1/29 9:07
     * @Param [jMenuItem]
     * @Return void
     */
    public static void pasteFileListener(JMenuItem jMenuItem){
        jMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TabCard tabCard = (TabCard) jTabbedPane.getSelectedComponent();//获取当前选中的tab卡对象
                JTextArea jTextArea = tabCard.getjTextArea();
                jTextArea.paste();
            }
        });
    }
    /**
     * 撤销文本事件监听
     * @Author lrh
     * @Date 2020/1/29 9:32
     * @Param [jMenuItem]
     * @Return void
     */
    public static void cancelOptionListener(JMenuItem jMenuItem){
        jMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TabCard tabCard = (TabCard) jTabbedPane.getSelectedComponent();//获取当前选中的tab卡对象
                if(tabCard.getUndoManager().canUndo()){
                    tabCard.getUndoManager().undo();
                }
            }
        });
    }
    /**
     * 恢复文本事件监听
     * @Author lrh
     * @Date 2020/1/29 9:32
     * @Param [jMenuItem]
     * @Return void
     */
    public static void resetOptionListener(JMenuItem jMenuItem){
        jMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TabCard tabCard = (TabCard) jTabbedPane.getSelectedComponent();//获取当前选中的tab卡对象
                if(tabCard.getUndoManager().canRedo()){
                    tabCard.getUndoManager().redo();
                }
            }
        });
    }
    /**
     * 查找文件事件监听
     * @Author lrh
     * @Date 2020/2/1 9:31
     * @Param [jMenuItem]
     * @Return void
     */
    public static void searchFileListener(final JFrame jFrame, JMenuItem jMenuItem){
        jMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("查找文件");
                JTextField inputStr = SearchDialog.getInstance(jFrame).getSearchPanel().getInputStr();
                inputStr.requestFocus(); //输入框获取焦点
                inputStr.selectAll(); //选中输入框中所有的文本
            }
        });
    }
    /**   
     * 添加拖拽事件监听
     * @Author lrh
     * @Date 2020/3/27 16:38
     * @Param [jTextArea]
     * @Return void
     */
    public static void dropListener(final JTextArea jTextArea){
        // 在 textArea 上注册拖拽目标监听器
        DropTarget dropTarget = new DropTarget(jTextArea, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                boolean isAccept = false;
                try{
                    // 1. 文件: 判断拖拽目标是否支持文件列表数据（即拖拽的是否是文件或文件夹, 支持同时拖拽多个）
                    if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        // 接收拖拽目标数据
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                        isAccept = true;
                        // 以文件集合的形式获取数据
                        java.util.List<File> files = (java.util.List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                        // 把文件路径输出到文本区域
                        if (files != null && files.size() > 0) {
                            for (final File file : files) {
                                final TabCard tabCard = new TabCard(); //创建选项卡对象
                                tabCard.setAbsoluteFilePath(file.getAbsolutePath()); //保存文件绝对路径
                                tabCard.setFileName(file.getName());   //保存文件名称（短名称）
                                jTabbedPane.addTab(file.getName(),Constants.ICON,tabCard,file.getAbsolutePath());
                                jTabbedPane.setSelectedComponent(tabCard);  //选中当前选项卡
                                //是文件夹就直接显示路径
                                if(file.isDirectory()){
                                    tabCard.getjTextArea().append(file.getAbsolutePath());
                                }else{//是文件就直接读取
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            readFile(file,tabCard.getjTextArea());
                                        }
                                    }).start();
                                }
                            }
                        }
                    }
                    //2. 文本: 判断拖拽目标是否支持文本数据（即拖拽的是否是文本内容, 或者是否支持以文本的形式获取）
                    if (dtde.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                        // 接收拖拽目标数据
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                        isAccept = true;
                        // 以文本的形式获取数据
                        String text = dtde.getTransferable().getTransferData(DataFlavor.stringFlavor).toString();
                        // 输出到文本区域
                        jTextArea.append("\r\n" + text);
                    }
                    /*
                     * 3. 图片: 判断拖拽目标是否支持图片数据。注意: 拖拽图片不是指以文件的形式拖拽图片文件,
                     *          而是指拖拽一个正在屏幕上显示的并且支持拖拽的图片（例如网页上显示的图片）。
                     */
                   /* if (dtde.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                        // 接收拖拽目标数据
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                        isAccept = true;
                        // 以图片的形式获取数据
                        Image image = (Image) dtde.getTransferable().getTransferData(DataFlavor.imageFlavor);
                        // 获取到 image 对象后, 可以对该图片进行相应的操作（例如: 用组件显示、图形变换、保存到本地等）,
                        // 这里只把图片的宽高输出到文本区域
                        textArea.append("图片: " + image.getWidth(null) + " * " + image.getHeight(null) + "\n");

                        final TabCard tabCard = new TabCard(); //创建选项卡对象
                        tabCard.setAbsoluteFilePath(image.get.getAbsolutePath()); //保存文件绝对路径
                        tabCard.setFileName(file.getName());   //保存文件名称（短名称）
                        jTabbedPane.addTab(file.getName(),Constants.ICON,tabCard,file.getAbsolutePath());
                        jTabbedPane.setSelectedComponent(tabCard);  //选中当前选项卡
                    }*/
                }catch (Exception e){
                    System.out.println("文件拖拽异常："+e.getMessage());
                }
                // 如果此次拖拽的数据是被接受的, 则必须设置拖拽完成（否则可能会看到拖拽目标返回原位置, 造成视觉上以为是不支持拖拽的错误效果）
                if (isAccept) {
                    dtde.dropComplete(true);
                }
            }
        }, true);
        // 如果要移除监听器, 可以调用下面代码
        // dropTarget.removeDropTargetListener(listener);
    }
}
