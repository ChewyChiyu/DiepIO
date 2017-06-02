import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class GameObject {
	protected double x,y,dx,dy;
	protected final int W,H;
	
	//changes
	protected int speed;	
	protected Rectangle bounds;
	
	protected GameObject(double x, double y,int W , int H){
		this.H = H;
		this.W = W;
		this.x = x;
		this.y = y;
		speed = 5;
		bounds = new Rectangle((int)x-W/2,(int)y-H/2,W,H);
	}
	
	
	abstract void draw(Graphics2D g);
	Rectangle getBounds(){
		bounds.setLocation((int)x-W/2, (int)y-W/2);
		return bounds;
	}
}
