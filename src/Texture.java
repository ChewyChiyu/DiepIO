import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Texture {
	static BufferedImage basicBlue;
	protected Texture(){
		try{
		BufferedImage spriteSheet = ImageIO.read(getClass().getResource("DiepIOSpriteSheet.png"));
		basicBlue = spriteSheet.getSubimage(110,31,93,66);
		}catch(Exception e) { e.printStackTrace();}
	}
}
