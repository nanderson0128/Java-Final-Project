
public abstract class GUIButton implements GameObject{

	protected Sprite sprite;
	protected Rectangle rect;
	protected boolean fixed;
	
	public GUIButton(Sprite sprite, Rectangle rect){
		this.sprite = sprite;
		this.rect = rect;
		this.fixed = fixed;
	}

	@Override
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		// TODO Auto-generated method stub
		renderer.renderSprite(sprite, rect.x, rect.y, xZoom, yZoom, fixed);
	}

	@Override
	public void update(Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
		// TODO Auto-generated method stub
		if(mouseRectangle.intersects(rect)){
			activate();
		}
	}
	
	public abstract void activate();

}
