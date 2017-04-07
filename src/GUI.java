
public class GUI implements GameObject{

	private Sprite backgroundSprite;
	private GUIButton[] buttons;
	private Rectangle rect;
	
	public GUI(Sprite backgroundSprite, GUIButton[] buttons, int x, int y, boolean fixed){
		this.backgroundSprite = backgroundSprite;
		this.buttons = buttons;
		
		rect.x = x;
		rect.y = y;
		
		if(backgroundSprite != null){
			rect.w = backgroundSprite.getWidth();
			rect.h = backgroundSprite.getHeight();
		}
		
	}
	
	public GUI(GUIButton[] buttons, int x, int y, boolean fixed){
		
		this(null, buttons, x, y, fixed);
	}
	@Override
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMouseClick(Rectangle mouseRectangle, int xZoom, int yZoom) {
		// TODO Auto-generated method stub
		
	}

}
