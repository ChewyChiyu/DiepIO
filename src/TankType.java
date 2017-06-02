import java.awt.image.BufferedImage;

public enum TankType {
	BASIC(
			-32, //translate x center
			-32,	 //translate y center
			Texture.basicBlue // basic tank image
			);
	
	
	
	
	
	protected int centerDX;
	protected int centerDY;
	protected BufferedImage img;
	private TankType(int x, int y, BufferedImage img){
		centerDX = x;
		centerDY = y;
		this.img = img;
	}
	
}
