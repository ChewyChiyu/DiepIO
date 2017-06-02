import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class DiepPanel extends JPanel implements Runnable{
	int currentFPS = 0;
	int currentDraw = 0;
	boolean gameRun;
	Thread game;
	ArrayList<GameObject> sprites = new ArrayList<GameObject>();
	Tank player = new Tank(Constants.screenW/2,Constants.screenH/2,TankType.BASIC);

	double renderX, renderY;
	Rectangle currentScreen = new Rectangle((int)renderX,(int)renderY,Constants.screenW,Constants.screenH);



	protected DiepPanel(){

		sprites.add(player);


		keys();
		panel();
		start();




	}
	void keys(){
		getInputMap().put(KeyStroke.getKeyStroke("A"), "A");
		getInputMap().put(KeyStroke.getKeyStroke("S"), "S");
		getInputMap().put(KeyStroke.getKeyStroke("D"), "D");
		getInputMap().put(KeyStroke.getKeyStroke("W"), "W");

		getInputMap().put(KeyStroke.getKeyStroke("released A"), "rA");
		getInputMap().put(KeyStroke.getKeyStroke("released S"), "rS");
		getInputMap().put(KeyStroke.getKeyStroke("released D"), "rD");
		getInputMap().put(KeyStroke.getKeyStroke("released W"), "rW");

		getActionMap().put("A", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
					player.dx=-player.speed;
			}

		});
		getActionMap().put("D", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
					player.dx=player.speed;
			}

		});
		getActionMap().put("W", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
					player.dy=-player.speed;
			}

		});
		getActionMap().put("S", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
					player.dy=player.speed;
			}

		});
		getActionMap().put("rA", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				player.dx=0;
			}

		});
		getActionMap().put("rD", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				player.dx=0;
			}

		});
		getActionMap().put("rW", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				player.dy=0;
			}

		});
		getActionMap().put("rS", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				player.dy=0;
			}

		});

	}
	void panel(){
		JFrame frame = new JFrame();
		frame.add(this);
		frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}
	synchronized void start(){
		game = new Thread(this);
		gameRun = true;
		game.start();
	}
	public void run(){
		long previousTime = System.currentTimeMillis();
		long currentTime = previousTime;
		long elapsedTime;
		long totalElapsedTime = 0;
		int frameCount = 0;

		while(gameRun)
		{
			currentTime = System.currentTimeMillis();
			elapsedTime = (currentTime - previousTime); 
			totalElapsedTime += elapsedTime;

			if (totalElapsedTime > 1000)
			{
				currentFPS = frameCount;
				frameCount = 0;
				totalElapsedTime = 0;
			}

			update();

			try
			{
				Thread.sleep(getFpsDelay(Constants.maxFPS));
			} catch (Exception e) {
				e.printStackTrace();
			}

			previousTime = currentTime;
			frameCount++;

		}

	}
	int getFpsDelay(int desiredFps)
	{
		return 1000 / desiredFps;
	}

	void update(){
		move();
		boundCheck();
		repaint();
	}
	void boundCheck(){
		synchronized(sprites){
			for(int index = 0; index < sprites.size(); index++){
				GameObject o = sprites.get(index);
				//bound check via map
				
					if(o.x-o.W/2 < Constants.mapX1){
						o.x += o.speed;
					}
					if(o.x+o.W/2 > Constants.mapX2){
						o.x -= o.speed;
					}
					if(o.y-o.H/2 < Constants.mapY1){
						o.y += o.speed;
					}
					if(o.y+o.H/2 > Constants.mapY2){
						o.y -= o.speed;
					}
				}
			
		}
	}
	void move(){
		synchronized(sprites){
			for(int index = 0; index < sprites.size(); index++){
				//map edge check
				GameObject o = sprites.get(index);
					o.x+=sprites.get(index).dx;
					o.y+=sprites.get(index).dy;
			}
		}
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		
		Graphics2D g2d = (Graphics2D) g;
		renderX = player.x-Constants.screenW/2;
		renderY = player.y-Constants.screenH/2;
		if(renderX<Constants.mapX1){
			renderX = Constants.mapX1;
		}
		if(renderY<Constants.mapY1){
			renderY = Constants.mapY1;
		}
		if(renderX+Constants.screenW>Constants.mapX2){
			renderX = Constants.mapX2-Constants.screenW;
		}
		if(renderY+Constants.screenH>Constants.mapY2){
			renderY = Constants.mapY2-Constants.screenH;
		}
		
		
		
		//sets focus on player
	
		//makes sure player screen renders only the map
		
		g2d.translate(-renderX, -renderY);
		currentDraw = 0;
		currentScreen.setLocation((int)renderX, (int)renderY);
		(g2d).draw(Constants.map);
		drawMap(g2d);
		drawSprites(g2d);
		drawInfo(g2d);

	}
	void drawMap(Graphics2D g2d){
		int scale = 250;
		Rectangle r = new Rectangle();
		r.setSize(scale, scale);
		for(int index = Constants.mapX1; index <= Constants.mapX2; index+=scale){
			for(int index2 = Constants.mapY1; index2 <= Constants.mapY2; index2+=scale){
				r.setLocation(index, index2);
				if(currentScreen.contains(r)||currentScreen.intersects(r)){
					g2d.draw(r);
					currentDraw++;
				}
			}
		}
		
		
		

	}
	void drawSprites(Graphics2D g2d){
		
		synchronized(sprites){
			for(int index = 0; index < sprites.size(); index++){
				//partial render
				GameObject o = sprites.get(index);
				if(currentScreen.contains(o.getBounds())||currentScreen.intersects(o.getBounds())){
					o.draw(g2d);
					currentDraw++;
				}
			}
		}
	}
	void drawInfo(Graphics g){
		g.setFont(Constants.font);
		g.drawString("FPS: "+currentFPS, (int)renderX, (int)renderY+25);
		g.drawString("Drawn Currently : " + currentDraw, (int)renderX, (int)renderY+50);
		g.drawString("renderX " + (int)renderX + " renderY"+  (int)renderY  , (int)renderX, (int)renderY+75) ;
	}
}
