package game.eatbean;
/**
 *
 * ClassName: Point
 * @Description: A*寻路算法中表示节点
 * @author lrh
 * @date 2018年12月6日
 */
public class Point implements Comparable<Point>{
	public int x;           //x坐标
	public int y;           //y坐标
	public Point parent;    //父节点的引用
	public int F;           //从开始节点到终点的总代价，F=G+H
	public int G;           //开始节点到下一个节点的代价
	public int H;           //从下一个节点到终点的预估代价

	public Point() {
		// TODO Auto-generated constructor stub
	}

	public Point(int x,int y){
		this.x=x;
		this.y=y;
		this.F=0;
		this.G=0;
		this.H=0;
	}

	public static int getDis(Point p1, Point p2) {
		int dis = Math.abs(p1.x - p2.x) * 10 + Math.abs(p1.y - p2.y) * 10;
		return dis;
	}
	//比较总代价的大小
	@Override
	public int compareTo(Point o) {
		return this.F-o.F;
	}
	//比较是否是同一个节点
	@Override
	public boolean equals(Object obj) {
		Point point=(Point) obj;
		if(point.x==this.x && point.y==this.y){
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", F=" + F + ", G=" + G + ", H=" + H + "]";
	}


}
