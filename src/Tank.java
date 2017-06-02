import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

public class Tank extends GameObject{
	static final double scale = 1;
	static int W = (int) (Texture.basicBlue.getWidth()*scale);
	static int H = (int) (Texture.basicBlue.getHeight()*scale);
	protected double angle = 0;
	protected Tank(double x, double y) {
		super(x, y, W, H);
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
		g2d.drawImage(Texture.basicBlue, -W/2 ,-H/2, W, H, null);
		g2d.setTransform(a);
		g.draw(bounds);
	}

}
