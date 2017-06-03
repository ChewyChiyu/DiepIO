import java.awt.Graphics2D;

public class Projectile extends GameObject{
	protected float id;
	protected Projectile(double x, double y, double dx, double dy, int radius, float id) {
		super(x, y, radius, radius);
		this.id = id;
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	void draw(Graphics2D g) {
		g.drawImage(Texture.basicProjBlue,(int)x, (int)y, W*2, H*2, null);
		//g.draw(bounds);
	}

}
