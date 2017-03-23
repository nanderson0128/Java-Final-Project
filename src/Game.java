import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.Runnable;
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
public class Game extends JFrame implements Runnable{
	
	public static int alpha = 0xFFFF00FF;
	private int FPS = 60;
	
	private Canvas canvas = new Canvas();
	private RenderHandler renderer;
	
	BufferedImage sheetImage = loadImage("spritesheet.png");
	BufferedImage sheetImage1 = loadImage("tiles16x16.png");
	
	private Tiles tile;
	private Map map;
	
	private SpriteSheet sheet;
	private Rectangle testRectangle = new Rectangle(30, 30, 100, 100);
	


	
	public Game(){
		//When you close window, it stops program
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Sets position
		setBounds(0, 0, 1280, 960);
		//Puts frame in the center of screen
		setLocationRelativeTo(null);
		//Adds graphics
		add(canvas);
		//Makes frame visible
		setVisible(true);
		//Creates object for buffering
		canvas.createBufferStrategy(3);
		renderer = new RenderHandler(getWidth(), getHeight());
		
		testRectangle.generateGraphics(15, 12234);
		sheet = new SpriteSheet(sheetImage);
		sheet.loadSprites(16, 16);
		

		
		//Load Tiles
		File txtFile = new File("bin/Tiles.txt");
		tile = new Tiles(txtFile, sheet);
		//Load Map 
		map = new Map(new File("bin/Map.txt"), tile);
	}
	
	public void update(){
	}
	
	
	private BufferedImage loadImage(String path){
		try {
			BufferedImage loadedImage = ImageIO.read(Game.class.getResource(path));
			BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);
			return formattedImage;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	

	
	
	public void render(){
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		super.paint(graphics);
		//renderer.renderRectangle(testRectangle, 1, 1);
		renderer.render(graphics);
		map.render(renderer, 3, 3);
		graphics.dispose();
		bufferStrategy.show();
	}
	
	@Override
	public void run() {
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		long lastTime = System.nanoTime();
		double nanoSecondConversion = 1000000000 / FPS;
		double 	changeInSeconds = 0;
		while(true){
			long now = System.nanoTime();
			changeInSeconds += (now - lastTime) / nanoSecondConversion;
			while(changeInSeconds >= 1){
				update();
				changeInSeconds = 0;
			}
			render();
			lastTime = now;
		}
	} 
	
	public static void main(String[] args) {
		Game game = new Game();
		Thread gameThread = new Thread(game);
		gameThread.start();
	}
}