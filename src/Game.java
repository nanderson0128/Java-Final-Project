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

public class Game extends JFrame implements Runnable{
	
	public static int alpha = 0xFFFF00DC;
	private int FPS = 60;
	
	
	private Canvas canvas = new Canvas();
	private RenderHandler renderer;
	BufferedImage treeLeaves = loadImage("grassTile.png");
	BufferedImage sheetImage = loadImage("spritesheet.png");
	private SpriteSheet sheet;
	private Rectangle testRectangle = new Rectangle(30, 30, 100, 100);
	
	//SPRITE SHEET SPRITES
		//Roof
	private Sprite leftRoof1;
	private Sprite leftRoof2;
	private Sprite leftRoof3;
	private Sprite leftRoof4;
	private Sprite centerRoofLeft;
	private Sprite centerRoofRight;
	private Sprite rightRoof4;
	private Sprite rightRoof3;
	private Sprite rightRoof2;
	private Sprite rightRoof1;
		//Ground
	private Sprite grass;
	private Sprite detailedGrass;

	
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
		
		//Roof
		leftRoof1 = sheet.getSprite(0, 0);
		leftRoof2 = sheet.getSprite(1, 0);
		leftRoof3 = sheet.getSprite(2, 0);
		leftRoof4 = sheet.getSprite(3, 0);
		centerRoofLeft = sheet.getSprite(4, 0);
		centerRoofRight = sheet.getSprite(5, 0);
		rightRoof4 = sheet.getSprite(6, 0);
		rightRoof3 = sheet.getSprite(7, 0);
		rightRoof2 = sheet.getSprite(8, 0);
		rightRoof1 = sheet.getSprite(9, 0);
		//Ground
		grass = sheet.getSprite(0, 1);
		detailedGrass = sheet.getSprite(0, 2);
		

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
	
	
	public void renderRoof(){
		renderer.renderSprite(leftRoof1, 0, 0, 5, 5);
		renderer.renderSprite(leftRoof2, 80, 0, 5, 5);
		renderer.renderSprite(leftRoof3, 160, 0, 5, 5);
		renderer.renderSprite(leftRoof4, 240, 0, 5, 5);
		renderer.renderSprite(centerRoofLeft, 320, 0, 5, 5);
		renderer.renderSprite(centerRoofRight, 400, 0, 5, 5);
		renderer.renderSprite(rightRoof4, 480, 0, 5, 5);
		renderer.renderSprite(rightRoof3, 560, 0, 5, 5);
		renderer.renderSprite(rightRoof2, 640, 0, 5, 5);
		renderer.renderSprite(rightRoof1, 720, 0, 5, 5);
	}
	
	
	public void render(){
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		super.paint(graphics);
		renderRoof();
		//renderer.renderSprite(leftRoof2, 0, 0, 5, 5);
		//renderer.renderRectangle(testRectangle, 1, 1);
		renderer.render(graphics);
		
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