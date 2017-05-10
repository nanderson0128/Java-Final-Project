
public interface GameObject {
	
	//Update as fast as possible
	public void render(RenderHandler renderer, int xZoom, int yZoom);
	
	
	//Update 60 FPS
	public void update(Game game);
	
	//Call whenever mouse is clicked on canvas.
	//Return true to stop checking clicks.
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom);


	public int getLayer();




}
