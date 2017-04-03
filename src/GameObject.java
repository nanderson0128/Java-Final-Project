
public interface GameObject {
	
	//Update as fast as possible
	public void render(RenderHandler renderer, int xZoom, int yZoom);
	
	
	//Update 60 FPS
	public void update(Game game);
}
