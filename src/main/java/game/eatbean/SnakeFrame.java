package game.eatbean;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * ClassName: SnakeFrame
 * @Description: 直接继承JPanel,因为它自带缓冲技术，可以有效的避免重绘图像时闪烁的问题
 * @author lrh
 * @date 2018年12月3日
 */
public class SnakeFrame extends JPanel implements KeyListener,Runnable{

	private static final long serialVersionUID = 1L;
	// 定义七个图片变量，代表七张图片
	private static final ImageIcon upImg=new ImageIcon(ClassLoader.getSystemResource("image/up.png"));         //向上
	private static final ImageIcon downImg=new ImageIcon(ClassLoader.getSystemResource("image/down.png"));     //向下
	private static final ImageIcon leftImg=new ImageIcon(ClassLoader.getSystemResource("image/left.png"));     //向左
	private static final ImageIcon rightImg=new ImageIcon(ClassLoader.getSystemResource("image/right.png"));   //向右
	private static final ImageIcon bodyImg=new ImageIcon(ClassLoader.getSystemResource("image/body.png"));     //身体
	private static final ImageIcon foodImg=new ImageIcon(ClassLoader.getSystemResource("image/food.png"));     //食物
	private JFrame jf=new JFrame();

	private static final int WIDTH=500;     //窗体宽度
	private static final int HEIGHT=500;    //窗体高度
	private static final int SPEED=25;      //移动速度
	private static int SX=100,SY=100;       //位置
	private static final int S_WIDTH=30;    //宽度
	private static final int S_HEIGHT=30;   //高度
	private static final int F_WIDTH=foodImg.getIconWidth();            //食物宽度
	private static final int F_HEIGHT=foodImg.getIconHeight();          //食物高度
	private static int FX=new Random().nextInt(420)+30;  //食物x位置
	private static int FY=new Random().nextInt(420)+30; //食物y位置

	// 蛇的每一部分
	int[] snakex = new int [750];
	int[] snakey = new int [750];
	int len=1;
	boolean flag=true; //false结束


	private static String DREC="U";    //默认向上

	public SnakeFrame() {
		jf.setTitle("snake");
		jf.setSize(WIDTH,HEIGHT);

		jf.setAlwaysOnTop(true);
		jf.setResizable(false);
		jf.setVisible(true);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		//添加组件jpanel
		jf.add(this);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		repaint();

		new Thread(this).start();
	}



	@Override
	public void paint(Graphics g) {
		super.paint(g);
		switch(DREC){
			case "U": //向上
				upImg.paintIcon(this, g, snakex[0], snakey[0]);
				break;
			case "D": //向下
				downImg.paintIcon(this, g, snakex[0], snakey[0]);
				break;
			case "L": //向左
				leftImg.paintIcon(this, g, snakex[0], snakey[0]);
				break;
			case "R": //向右
				rightImg.paintIcon(this, g, snakex[0], snakey[0]);
				break;
		}
		// 画蛇的身体
		for(int i = 1; i < len; i ++){
			bodyImg.paintIcon(this, g, snakex[i], snakey[i]);
		}

		//画出食物
		foodImg.paintIcon(this, g, FX, FY);

	}



	public static void main(String[] args) {
		new SnakeFrame();
	}


	//碰撞检测，蛇和食物是否发生碰撞
	public void check(){
		//蛇头的矩形
		Rectangle s_rec=new Rectangle(SX, SY, S_WIDTH, S_HEIGHT);
		//食物的矩形
		Rectangle f_rec=new Rectangle(FX, FY, F_WIDTH, F_HEIGHT);

		boolean b=s_rec.intersects(f_rec);
		if(b){ //发生碰撞
			//重新绘制食物发位置
			FX=new Random().nextInt(420)+30;
			FY=new Random().nextInt(420)+30;
			System.out.println("----生成食物的坐标为：x="+FX+",y="+FY);
			len++; //蛇的长度+1
			System.out.println("----蛇和食物相交----");
		}

	}

	//检查是否还活着
	private void checkAlive(){
		for(int i=1;i<len;i++){
			if(SX==snakex[i] && SY==snakey[i]){
				flag=false;
				System.out.println("-----------游戏结束------------");
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode=e.getKeyCode();
		switch(keyCode){
			case KeyEvent.VK_UP:         //上
				DREC="U"; //头向上
				System.out.println("上");
				break;
			case KeyEvent.VK_DOWN:       //下
				DREC="D"; //头向下
				System.out.println("下");
				break;
			case KeyEvent.VK_LEFT:       //左
				DREC="L"; //头向左
				System.out.println("左");
				break;
			case KeyEvent.VK_RIGHT:      //右
				DREC="R"; //头向右
				System.out.println("右");
				break;
			case KeyEvent.VK_SPACE:      //空格，暂停
				if(flag){
					flag=false;
				}else{
					flag=true;
					new Thread(this).start();
				}
				System.out.println("空格："+flag);
				break;
		}
		repaint();

	}

	//开启一个线程移动
	@Override
	public void run() {

		while(flag){

			//这里是将上一节身体的坐标传递到下一节身体，是移动的关键
			for (int i = len; i>0; i--){
				snakex[i] = snakex[i-1];
				snakey[i] = snakey[i-1];
			}
			switch(DREC){
				case "U": //向上
					SY-=SPEED;
					if(SY<S_HEIGHT){
						SY=HEIGHT-S_HEIGHT;
					}
					check(); //检查是否碰撞
					checkAlive(); //检查是否游戏结束
					break;
				case "D": //向下
					SY+=SPEED;
					if(SY>HEIGHT-S_HEIGHT){
						SY=S_HEIGHT;
					}
					check(); //检查是否碰撞
					checkAlive(); //检查是否游戏结束
					break;
				case "L": //向左
					SX-=SPEED;
					if(SX<0){
						SX=WIDTH-S_WIDTH;
					}
					check(); //检查是否碰撞
					checkAlive(); //检查是否游戏结束
					break;
				case "R": //向右
					SX+=SPEED;
					if(SX>WIDTH-S_WIDTH){
						SX=0;
					}
					check(); //检查是否碰撞
					checkAlive(); //检查是否游戏结束
					break;
			}
			snakex[0]=SX;
			snakey[0]=SY;

			repaint();
			try {
				Thread.sleep(200); //每200毫秒移动一次
			} catch (InterruptedException e) {
				e.printStackTrace();
			}


		}


	}


	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}







}
