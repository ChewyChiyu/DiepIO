import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Tank extends GameObject{
	
	protected double angle = 0;
	protected TankType t;
	
	protected Tank(double x, double y, TankType t) {
		super(x, y, t.img.getWidth(), t.img.getHeight());
		//setting tank type
		this.t = t;
		
		
		Thread motion = new Thread(new Runnable(){
			public void run(){
				while(this!=null){
					if((angle+=Math.PI/300) > Math.PI*2){
						angle = 0;
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
		g2d.drawImage(t.img, t.centerDX ,t.centerDY, W, H, null);
		g2d.setTransform(a);
		//g.draw(bounds);
	}

}
