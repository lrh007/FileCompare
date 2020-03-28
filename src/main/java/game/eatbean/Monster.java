package game.eatbean;
/**
 *
 * ClassName: Monster
 * @Description: 小怪物类
 * @author lrh
 * @date 2018年12月5日
 */
public class Monster {
	private int MX=0,MY=0;                                      //小怪物的坐标
	private int M_WIDTH=25,M_HEIGHT=25;                         //小怪物的大小
	private int M_SPEED=5;                                      //小怪物的移动速度
	private String M_DIREC="U";                                 //小怪物的移动方向,默认向上

	public Monster(){

	}

	public int getMX() {
		return MX;
	}

	public void setMX(int mX) {
		MX = mX;
	}

	public int getMY() {
		return MY;
	}

	public void setMY(int mY) {
		MY = mY;
	}

	public int getM_WIDTH() {
		return M_WIDTH;
	}

	public void setM_WIDTH(int m_WIDTH) {
		M_WIDTH = m_WIDTH;
	}

	public int getM_HEIGHT() {
		return M_HEIGHT;
	}

	public void setM_HEIGHT(int m_HEIGHT) {
		M_HEIGHT = m_HEIGHT;
	}

	public int getM_SPEED() {
		return M_SPEED;
	}

	public void setM_SPEED(int m_SPEED) {
		M_SPEED = m_SPEED;
	}

	public String getM_DIREC() {
		return M_DIREC;
	}

	public void setM_DIREC(String m_DIREC) {
		M_DIREC = m_DIREC;
	}



	@Override
	public String toString() {
		return "Monster [MX=" + MX + ", MY=" + MY + ", M_WIDTH=" + M_WIDTH + ", M_HEIGHT=" + M_HEIGHT + ", M_SPEED="
				+ M_SPEED + ", M_DIREC=" + M_DIREC + "]";
	}

}
