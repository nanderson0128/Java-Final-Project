
public class Player implements GameObject{

	Rectangle playerRectangle;
	int speed = 10;
	
	public Player(){
		playerRectangle = new Rectangle(32, 16, 16, 32);
		playerRectangle.generateGraphics(3, 0xFF00FF90);
	}

	@Override
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		// TODO Auto-generated method stub
		renderer.renderRectangle(playerRectangle, xZoom, yZoom);
	}

	@Override
	public void update(Game game) {
		// TODO Auto-generated method stub
		KeyboardListener keyListener = game.getKeyListener();
		
		if(keyListener.up()){
			playerRectangle.y -= speed;
		}
		if(keyListener.down()){
			playerRectangle.y += speed;
		}
		if(keyListener.left()){
			playerRectangle.x -= speed;
		}
		if(keyListener.right()){
			playerRectangle.x += speed;
		}
		updateCamera(game.getRenderer().getCamera());
	}
	
	public void updateCamera(Rectangle camera){
		camera.x = playerRectangle.x - (camera.w / 2);
		camera.y = playerRectangle.y - (camera.h/2);
	}
}
