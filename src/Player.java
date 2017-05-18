
public class Player implements GameObject{

	private Rectangle playerRectangle;
	public Rectangle collisionCheckRectangle;
	
	private int speed = 8;

	//0 = Right, 1 = Left, 2 = Up, 3 = Down
	private int direction = 0;
	private int layer = 0;
	
	private Sprite sprite;
	private AnimatedSprite animatedSprite = null;
	private final int xCollisionOffset = 11;
	private final int yCollisionOffset = 64;
	private int[] mostRecentKey = new int[4];

	
	public Player(Sprite sprite, int xZoom, int yZoom){
		this.sprite = sprite;
		if(sprite != null && sprite instanceof AnimatedSprite){
			animatedSprite = (AnimatedSprite) sprite;
		}
		
		updateDirection();
		playerRectangle = new Rectangle(0, 0, 20, 26);
		playerRectangle.generateGraphics(3, 0xFF00FF90);
		collisionCheckRectangle = new Rectangle(0, 0, 12 * xZoom, 1 * yZoom);
	}
	
	
	private void updateDirection(){
		if(animatedSprite != null)
		{
			animatedSprite.setAnimationRange(direction * 8, direction * 8 + 7);
		}
			
	}

	@Override
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		// TODO Auto-generated method stub

		if(animatedSprite != null){
				renderer.renderSprite(animatedSprite, playerRectangle.x, playerRectangle.y, xZoom, yZoom, false);
		}
		else if (sprite != null){
			renderer.renderSprite(sprite, playerRectangle.x, playerRectangle.y, xZoom, yZoom, false);
		}
		else{
			renderer.renderRectangle(playerRectangle, xZoom, yZoom, false);
		}
	}

	@Override
	public void update(Game game) {
		// TODO Auto-generated method stub
		KeyboardListener keyListener = game.getKeyListener();
		mostRecentKey = keyListener.getRecentKey();
		boolean didMove = false;
		int newDirection = direction;
		
		collisionCheckRectangle.x = playerRectangle.x;
		collisionCheckRectangle.y = playerRectangle.y;
		
		if(keyListener.right() && mostRecentKey[0] == 1){
			collisionCheckRectangle.x += speed;
			newDirection = 0;
			didMove = true;	
		}
		
		if(keyListener.left() && mostRecentKey[1] == 1){
			collisionCheckRectangle.x -= speed;
			newDirection = 1;
			didMove = true;
		}

		if(keyListener.up() && mostRecentKey[2] == 1){
			collisionCheckRectangle.y -= speed;
			newDirection = 2;
			didMove = true;
		}
		
		if(keyListener.down() && mostRecentKey[3] == 1){
			collisionCheckRectangle.y += speed;
			newDirection = 3;
			didMove = true;
		}
		//System.out.println(mostRecentKey);
		
		if(newDirection != direction){
			direction = newDirection;
			updateDirection();
		}

		if(!didMove){
			animatedSprite.reset();
		}
		

		
		if(didMove){
			collisionCheckRectangle.x += xCollisionOffset;
			collisionCheckRectangle.y += yCollisionOffset;
			if(!game.getMap().checkCollision(collisionCheckRectangle, layer, game.getXZoom(), game.getYZoom()) && 
					!game.getMap().checkCollision(collisionCheckRectangle, layer + 1, game.getXZoom(), game.getYZoom())){
				
				playerRectangle.x = collisionCheckRectangle.x -  xCollisionOffset;
				playerRectangle.y = collisionCheckRectangle.y - yCollisionOffset;

			}
			animatedSprite.update(game);

		}
		updateCamera(game.getRenderer().getCamera());
		
	}
	
	public void updateCamera(Rectangle camera){
		camera.x = playerRectangle.x - (camera.w / 2);
		camera.y = playerRectangle.y - (camera.h / 2);
	}
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom){
		return false;
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return layer;
	}

	@Override
	public Rectangle getRectangle() {
		// TODO Auto-generated method stub
		return playerRectangle;
	}

}
