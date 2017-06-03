import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Tank extends GameObject{
	
	protected double angle = 0;
	protected float id;
	protected TankType t;
	
	
	protected boolean A,W,S,D;
	
	protected Tank(double x, double y, TankType t, float id) {
		super(x, y, t.img.getWidth(), t.img.getHeight());
		//setting tank type
		this.t = t;
		this.id = id;
		
		Thread motion = new Thread(new Runnable(){
			public void run(){
				while(this!=null){
					//moving
					if(A){
						dx = (-speed);
					}
					if(W){
						dy = (-speed);
					}
					if(S){
						dy = (speed);
					}
					if(D){
						dx = (speed);
					}
					
					if(!A||!D){
						if(dx!=0){
							if(dx>0)
								dx-=Constants.friction;
							else
								dx+=Constants.friction;
						}
					}
					if(!W||!S){
						if(dy!=0){
							if(dy>0)
								dy-=Constants.friction;
							else
								dy+=Constants.friction;
						}
					}
					
					
					try{
						Thread.sleep(1);
					}catch(Exception e) { }
				}
			}
		});
		motion.start();
	}
	void change(TankType t){
		this.t = t;
	}
	void shoot(){
		//shoots from center of tank
		DiepPanel.sprites.add(new Projectile(x+Math.cos(angle)*super.W, y+Math.sin(angle)*super.H, Math.cos(angle)*10, Math.sin(angle)*10, 15, id));
	}
	@Override
	void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform a = g2d.getTransform();
		g2d.translate(x, y);
		g2d.rotate(angle);
		g2d.drawImage(t.img, t.centerDX ,t.centerDY, super.W, super.H, null);
		g2d.setTransform(a);
		//g.draw(bounds);
	}

}
