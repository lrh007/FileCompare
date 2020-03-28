package game.eatbean;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import javax.swing.*;


public class EatBean extends JPanel implements KeyListener,Runnable{
	private static EatBean INSTANCE = null;
	private static final long serialVersionUID = 1L;
	private static final ImageIcon upImg=new ImageIcon(ClassLoader.getSystemResource("images/eatbean/up.png"));         //向上
	private static final ImageIcon downImg=new ImageIcon(ClassLoader.getSystemResource("images/eatbean/down.png"));     //向下
	private static final ImageIcon leftImg=new ImageIcon(ClassLoader.getSystemResource("images/eatbean/left.png"));     //向左
	private static final ImageIcon rightImg=new ImageIcon(ClassLoader.getSystemResource("images/eatbean/right.png"));   //向右
	private static final ImageIcon foodImg=new ImageIcon(ClassLoader.getSystemResource("images/eatbean/food.png"));     //食物
	private static final ImageIcon stoneImg=new ImageIcon(ClassLoader.getSystemResource("images/eatbean/stone.png"));   //石头
	//	private static final ImageIcon stoneImg=new ImageIcon(ClassLoader.getSystemResource("images/eatbean/grass.png"));   //草地
	private static final ImageIcon gwImg=new ImageIcon(ClassLoader.getSystemResource("images/eatbean/gw.png"));         //小怪物
	private String DIREC="U";                               												   //蛇的移动方向，默认向上，
	private int SX=0,SY=0;                                                                                     //蛇的位置
	private int S_WIDTH=25,S_HEIGHT=25;                                                                        //蛇的大小
	private int SPEED=30;                                                                                      //蛇移动的速度
	private int FX=0,FY=0;                                                                                     //食物的位置
	private int F_WIDTH=25,F_HEIGHT=25;                                                                        //食物的大小
	private int interval=30;                                                                                   //绘制图片的间隔，也是障碍物的大小
	private List<Monster> monsterList=new ArrayList<Monster>();                                                //存放的是小怪物类的集合
	private int monterLength=1;                                                                               //生成小怪物的数量
	private String[] M_DIRECS={"U","D","L","R"};                                                               //小怪物的移动方向，随机生成方向的时候从这里面获取
	private boolean flag=true;                                                                                 //小怪物是否移动,游戏是否结束，false 结束，true 未结束
	private int FRAME_WIDTH=570;                                                                               //jframe窗体的宽度
	private int FRAME_HEIGHT=485;                                                                              //jframe窗体的高度
	private long MILLIS=100;                                                                                   //线程的执行速度
	private int SCORE=0;                                                                                       //分数
	private final int SCORE_X=460,SCORE_Y=30;                                                                  //分数显示的位置
	private static final int mapLength=15;                                                                     //地图大小，15*15的

	private JFrame jf=new JFrame("EatingBeans");
	//将空白的位置保存下来
	private static List<Map<String,Object>> blankList=new ArrayList<Map<String,Object>>();
	//将障碍物的位置保存下来
	private static List<Map<String,Object>> obstacleList=new ArrayList<Map<String,Object>>();

	// 前四个是上下左右，后四个是斜角
	public final static int[] dx = { 0, -1, 0, 1, -1, -1, 1, 1 };
	public final static int[] dy = { -1, 0, 1, 0, 1, -1, -1, 1 };

	private static final int[][] map={{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,1,1,0,0,1,1,0,1,0,1,1,0,1},
			{1,0,0,0,1,0,1,0,0,1,0,0,0,0,1},
			{1,0,1,0,1,0,1,1,0,0,0,1,0,0,1},
			{1,0,1,0,0,0,1,0,0,1,0,0,1,0,1},
			{1,0,1,1,1,0,1,1,0,1,1,1,1,0,1},
			{1,0,1,0,0,0,0,0,0,0,0,0,1,0,1},
			{1,0,1,0,1,0,1,0,0,1,0,1,1,1,1},
			{1,0,0,0,1,0,0,0,0,0,1,0,0,0,1},
			{1,0,1,1,1,0,1,0,1,0,0,0,1,0,1},
			{1,0,1,0,0,0,1,0,0,0,1,0,0,1,1},
			{1,0,0,0,1,0,1,0,0,1,1,0,1,0,1},
			{1,0,1,1,1,0,1,0,1,1,0,0,0,0,1},
			{1,0,0,0,1,0,1,0,0,1,0,1,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
	};


	public EatBean() {
		jf.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		jf.add(this);
//		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getBlankXY();//获取空白位置的坐标
		generatorSnake(); //生成蛇的位置
		generatorFood();   //生成食物的位置
		initMonsterList(); //初始化小怪物的数量，一定要在生成小怪物的位置之前
		repaint();
		//开始移动小怪物
		new Thread(this).start();
		jf.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				INSTANCE = null;
			}
		});
	}
	//初始化小怪物集合
	public void initMonsterList(){
		for(int i=0;i<monterLength;i++){
			int length=M_DIRECS.length;
			int index=new Random().nextInt(length);
			Monster m=new Monster();
			m.setM_DIREC(M_DIRECS[index]);
			monsterList.add(m);
		}
		generatorMonster(); //生成小怪物的位置
	}
	//随机生成一个小怪物
	public void generatorOneMonster(){
		System.out.println("---------随机生成一个小怪物-------");
		monterLength++; //小怪物总数量+1
		int length=M_DIRECS.length;
		int index=new Random().nextInt(length);
		Monster m=new Monster();
		m.setM_DIREC(M_DIRECS[index]);
		monsterList.add(m);
		generatorMonster();
	}
	//获取空白位置的坐标
	public void getBlankXY(){
		//绘制障碍物
		int x=0,y=0;
		for(int i=0;i<map.length;i++){
			for(int j=0;j<map.length;j++){
				if(map[i][j]==0){
					//将空白位置的坐标保存下来，方便生成食物和蛇的位置
					Map<String,Object> temp=new HashMap<String,Object>();
					temp.put("X", x);
					temp.put("Y", y);
					blankList.add(temp);
				}else{
					//将障碍物位置的坐标保存下来，方便生成食物和蛇的位置
					Map<String,Object> temp=new HashMap<String,Object>();
					temp.put("X", x);
					temp.put("Y", y);
					obstacleList.add(temp);

				}
				x+=interval; //绘制每列的时候，将x坐标移动
			}
			x=0;//绘制行的时候将x重置，将y移动
			y+=interval;
		}
	}
	//生成蛇的位置
	private void generatorSnake(){
		//随机生成位置
		int length=blankList.size();
		int index=new Random().nextInt(length);
		Map<String,Object> temp=blankList.get(index);
		SX=Integer.parseInt(temp.get("X").toString());
		SY=Integer.parseInt(temp.get("Y").toString());
		//如果生成蛇的位置和食物的位置相同，和小怪物的位置相同，就重新生成蛇的位置
		if((SX+SY)-(FX+FY)==0){
			generatorSnake();//递归
		}
		for(Monster m:monsterList){
			if((SX+SY)-(m.getMX()+m.getMY())==0){
				generatorSnake();//递归
			}
		}
	}
	//生成食物的位置
	private void generatorFood(){
		//随机生成位置
		int length=blankList.size();
		int index=new Random().nextInt(length);
		Map<String,Object> temp=blankList.get(index);
		FX=Integer.parseInt(temp.get("X").toString());
		FY=Integer.parseInt(temp.get("Y").toString());
		//如果生成食物的位置和蛇的位置相同,就重新生成食物的位置
		if((FX+FY)-(SX+SY)==0){
			generatorFood();//递归
		}
	}
	//生成小怪物的位置
	private void generatorMonster(){
		//随机生成位置
		int length=blankList.size();

		//循环生成小怪物的坐标位置
		for(Monster m:monsterList){
			//重新生成小怪物位置的时候判断，如果位置XY不为0,就跳过，为0就生成，这样可以避免生成一个新的小怪物时，其他小怪物的位置发生改变
			if(m.getMX()==0 && m.getMY()==0){
				int index=new Random().nextInt(length);
				Map<String,Object> temp=blankList.get(index);
				int MX=Integer.parseInt(temp.get("X").toString());
				int MY=Integer.parseInt(temp.get("Y").toString());
				//如果生成小怪物的位置和蛇的位置相同，就重新生成小怪物的位置
				if((MX+MY)-(SX+SY)==0){
					generatorMonster();//递归
				}else{
					m.setMX(MX);
					m.setMY(MY);
				}
			}

		}
	}



	//检查蛇是否可以移动,true可以移动，false不可以移动
	private boolean checkCanMove(){
		boolean b=true;
		for(Map<String,Object>temp:obstacleList){
			//障碍物的位置
			int x=Integer.parseInt(temp.get("X").toString());
			int y=Integer.parseInt(temp.get("Y").toString());
			Rectangle  ob_rec=new Rectangle(x, y, interval, interval);
			//蛇的位置
			Rectangle  s_rec=new Rectangle(SX, SY, S_WIDTH, S_HEIGHT);
			if(ob_rec.intersects(s_rec)){ //只要碰到了一个障碍物，就直接推出循环
				b=false;
				break;
			}
		}
		return b;
	}
	//检查蛇和食物是否相交，即蛇吃了食物
	private void checkEat(){
		//蛇的矩形
		Rectangle s_rec=new Rectangle(SX, SY, S_WIDTH, S_HEIGHT);
		//食物的矩形
		Rectangle f_rec=new Rectangle(FX, FY, F_WIDTH, F_HEIGHT);
		//相交，重新生成食物的位置
		if(s_rec.intersects(f_rec)){
			SCORE++; //分数+1
			generatorFood();
			generatorOneMonster(); //每次吃掉一个食物，就新生成一个小怪物
		}
	}
	//检查小怪物和蛇是否发生碰撞
	public void checkGameOver(){
		for(Monster m:monsterList){
			//蛇的矩形
			Rectangle s_rec=new Rectangle(SX, SY, S_WIDTH, S_HEIGHT);
			//小怪物的矩形
			Rectangle m_rec=new Rectangle(m.getMX(), m.getMY(),m.getM_WIDTH(), m.getM_HEIGHT());
			//蛇和小怪物发生碰撞，游戏结束
			if(s_rec.intersects(m_rec)){
				flag=false; //停止移动小怪物，游戏结束
				break;
			}
		}
		//游戏没有结束，判断小怪物是否可以移动
		checkMonster();
	}
	//检查小怪物是否可以移动，true 可以，false不可以
	private boolean checkMonsterCanMove(Monster m){
		boolean b=true;
		//小怪物的矩形
		Rectangle m_rec=new Rectangle(m.getMX(), m.getMY(),m.getM_WIDTH(), m.getM_HEIGHT());
		//循环障碍物集合，判断小怪物是否碰到了障碍物
		for(Map<String,Object>temp:obstacleList){
			//障碍物的位置
			int x=Integer.parseInt(temp.get("X").toString());
			int y=Integer.parseInt(temp.get("Y").toString());
			Rectangle  ob_rec=new Rectangle(x, y, interval, interval);
			if(ob_rec.intersects(m_rec)){ //只要碰到了一个障碍物，生成一个随机的移动方向
				String direc=geratorDirec(m.getM_DIREC());
				m.setM_DIREC(direc);
				b=false; //不能移动小怪物
				break;
			}
		}
		return b;
	}
	//随机生成小怪物的方向,保证这次和上次生成的方向不一样
	private String geratorDirec(String oldDirec){
		int length=M_DIRECS.length;
		int index=new Random().nextInt(length);
		String newDirec=M_DIRECS[index]; //新生成的方向
		if(oldDirec.equals(newDirec)){
			geratorDirec(oldDirec); //递归重新生成方向
		}
		return newDirec;
	}
	//检查小怪物是否和障碍物发生碰撞（发生碰撞就改变移动方向，没有则继续移动）
	public void checkMonster(){
		for(Monster m:monsterList){
			boolean b=true;

			//true,可以移动，false 不做任何操作
			String M_DIREC=m.getM_DIREC();
			int MX=m.getMX();
			int MY=m.getMY();
			int M_SPEED=m.getM_SPEED();
			switch(M_DIREC){
				case "U": //向上
					MY-=M_SPEED;
					m.setMY(MY);   //这个坐标发生改变的时候，一定要将改变后的值赋给对象，否则会导致碰到障碍物之后不再移动
					b=checkMonsterCanMove(m); //检查是否能移动小怪物
					if(!b){
						MY+=M_SPEED;
					}
					break;
				case "D": //向下
					MY+=M_SPEED;
					m.setMY(MY);
					b=checkMonsterCanMove(m); //检查是否能移动小怪物
					if(!b){
						MY-=M_SPEED;
					}
					break;
				case "L": //向左
					MX-=M_SPEED;
					m.setMX(MX);
					b=checkMonsterCanMove(m); //检查是否能移动小怪物
					if(!b){
						MX+=M_SPEED;
					}
					break;
				case "R": //向右
					MX+=M_SPEED;
					m.setMX(MX);
					b=checkMonsterCanMove(m); //检查是否能移动小怪物
					if(!b){
						MX-=M_SPEED;
					}
					break;
			}
			m.setMX(MX);
			m.setMY(MY);
		}
	}

	public static EatBean getINSTANCE(){
		if(INSTANCE == null){
			INSTANCE = new EatBean();
		}
		return INSTANCE;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//绘制障碍物
		int x=0,y=0;
		for(int i=0;i<map.length;i++){
			for(int j=0;j<map.length;j++){
				if(map[i][j]==1){
					stoneImg.paintIcon(this, g, x, y);
//					grassImg.paintIcon(this, g, x, y);
				}
				x+=30; //绘制每列的时候，将x坐标移动
			}
			x=0;//绘制行的时候将x重置，将y移动
			y+=30;
		}
		//绘制蛇
		switch(DIREC){
			case "U":
				upImg.paintIcon(this, g, SX, SY);
				break;
			case "D":
				downImg.paintIcon(this, g, SX, SY);
				break;
			case "L":
				leftImg.paintIcon(this, g, SX, SY);
				break;
			case "R":
				rightImg.paintIcon(this, g, SX, SY);
				break;
		}
		//绘制食物
		foodImg.paintIcon(this, g, FX, FY);
		//绘制小怪物
		for(Monster m:monsterList){
			gwImg.paintIcon(this, g, m.getMX(), m.getMY());
		}
		//绘制分数
		g.setColor(Color.WHITE);
		Font font = new Font("宋体",Font.PLAIN,18);
		g.setFont(font);
		g.drawString("score: "+SCORE, SCORE_X, SCORE_Y);

	}




	//键盘按下事件
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode=e.getKeyCode();
		boolean b=false;
		//游戏结束
		if(flag){
			switch(keyCode){
				case KeyEvent.VK_UP:    //按下上键
					SY-=SPEED;
					//判断是否可以移动蛇
					b=checkCanMove();
					if(!b){
						SY+=SPEED;
					}
					DIREC="U"; //向上
					System.out.println("----上----");
					break;
				case KeyEvent.VK_DOWN:  //按下下键
					SY+=SPEED;
					//判断是否可以移动蛇
					b=checkCanMove();
					if(!b){
						SY-=SPEED;
					}
					DIREC="D"; //向下
					System.out.println("----下----");
					break;
				case KeyEvent.VK_LEFT:  //按下左键
					SX-=SPEED;
					//判断是否可以移动蛇
					b=checkCanMove();
					if(!b){
						SX+=SPEED;
					}
					DIREC="L"; //向左
					System.out.println("----左----");
					break;
				case KeyEvent.VK_RIGHT: //按下右键
					SX+=SPEED;
					//判断是否可以移动蛇
					b=checkCanMove();
					if(!b){
						SX-=SPEED;
					}
					DIREC="R"; //向右
					System.out.println("----右----");
					break;
			}
			checkEat(); //检查是否吃掉食物
			repaint();
		}
	}

	@Override
	public void run() {
		while(flag){
			try {
				checkGameOver();//检查游戏是否结束
				repaint();  //刷新页面
				Thread.sleep(MILLIS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		JOptionPane.showMessageDialog(null, "Game Over!", "INFORMATION", JOptionPane.ERROR_MESSAGE);

	}


	//小怪物寻路算法
	public static Stack<Point> printPath(Point start,Point end){

		//不用PriorityQueue是因为必须取出存在的元素
		List<Point> openTable=new ArrayList<Point>();    //表示为搜索（待检验）的集合点
		List<Point> closeTable=new ArrayList<Point>();   //表示已搜索（已检验）的集合点
		boolean flag=true;

		openTable.clear();
		closeTable.clear();
		Stack<Point> pathStack=new Stack<Point>();
		start.parent=null;
		//该点起到转换作用，就是当前扩展点
		Point currentPoint=new Point(start.x,start.y);
		while(flag){
			for(int i=0;i<8;i++){
				int fx=currentPoint.x+dx[i];
				int fy=currentPoint.y+dy[i];
				Point tempPoint=new Point(fx,fy);
				if(map[fx][fy]==1){ //节点是障碍物，不做处理
					//由于边界都是1中间障碍物也是1，，这样不必考虑越界和障碍点扩展问题
					//如果不设置边界那么fx >=map.length &&fy>=map[0].length判断越界问题
					continue;
				}else{ //节点是路，可以移动
					if(end.equals(tempPoint)){
						flag=false;
						//终点不是tempPoint,他俩都一样了此时
						end.parent=currentPoint;
						break;
					}
					if(i<4){ //此时是上下左右，代价+10
						tempPoint.G=currentPoint.G+10;
					}else{ //此时不是斜对角，代价+14
						tempPoint.G=currentPoint.G+14;
					}
					//当前节点到终点的预估代价
					tempPoint.H=Point.getDis(tempPoint, end);
					//当前节点到终点总的代价
					tempPoint.F=tempPoint.G+tempPoint.H;

					//因为重写了equals方法，所以这里包含只是按equals相等包含，这一点是使用java封装好类的关键

					if(openTable.contains(tempPoint)){ //判断当前节点是否是为检验的
						int index=openTable.indexOf(tempPoint); //在列表中获取当前节点的索引位置
						Point temp=openTable.get(index);        //获取当前节点对象
						//更新集合中的数据
						if(temp.F>tempPoint.F){
							openTable.remove(index);
							openTable.add(tempPoint);
							tempPoint.parent=currentPoint;
						}
					} else if (closeTable.contains(tempPoint)) { // 当前节已经检验
						int pos = closeTable.indexOf(tempPoint);
						Point temp = closeTable.get(pos);
						if (temp.F > tempPoint.F) {
							closeTable.remove(pos);
							openTable.add(tempPoint);
							tempPoint.parent = currentPoint;
						}

					}else{  //直接添加到待检验集合中
						openTable.add(tempPoint);
						tempPoint.parent = currentPoint;
					}
				}
			}

			if (openTable.isEmpty()) {
				return null;
			} // 无路径
			if (false == flag) {
				break;
			} // 找到路径
			openTable.remove(currentPoint);
			closeTable.add(currentPoint);
			Collections.sort(openTable);
			currentPoint = openTable.get(0);

		}

		Point node = end;
		while (node.parent != null) {
			pathStack.push(node);
			node = node.parent;
		}
		return pathStack;
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}


}

