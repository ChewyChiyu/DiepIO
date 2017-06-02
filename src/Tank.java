import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Tank extends GameObject{
	
	protected double angle = 0;
	protected TankType t;
	
	
	protected boolean A,W,S,D;
	
	protected Tank(double x, double y, TankType t) {
		super(x, y, t.img.getWidth(), t.img.getHeight());
		//setting tank type
		this.t = t;
		
		
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
