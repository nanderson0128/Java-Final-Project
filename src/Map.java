import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

 
public class Map {
	private Tiles tileSet;
	private int fillTileID = -1;
	
	private ArrayList<MappedTile> mappedTiles = new ArrayList<MappedTile>();
	private Block [][] blocks;
	private int blockStartX, blockStartY = 0;
	private int blockWidth = 6;
	private int blockHeight = 6;
	private int blockPixelWidth = blockWidth * 16;
	private int blockPixelHeight = blockHeight * 16;
	private HashMap<Integer, String> comments = new HashMap<Integer, String>();
	
	private File mapFile;
	
	private int numLayers;
	
	private Player player;
	
	
	public Map(File mapFile, Tiles tileSet){
	this.tileSet = tileSet;
	this.mapFile = mapFile;
	int minX = Integer.MAX_VALUE;
	int minY = Integer.MAX_VALUE;
	int maxX = Integer.MIN_VALUE;
	int maxY = Integer.MIN_VALUE;
	try{
		Scanner scanner = new Scanner(mapFile);
		int currentLine = 0;
		while(scanner.hasNextLine()) 
		{
			String line = scanner.nextLine();
			if(!line.startsWith("//"))
			{
				if(line.contains(":")){
					String[] splitString = line.split(":");
					if(splitString[0].equalsIgnoreCase("Fill")){
						fillTileID = Integer.parseInt(splitString[1]);
						continue;
					}
				}
				
				String[] splitString = line.split(",");
				if(splitString.length >= 4){
					MappedTile mappedTile = new MappedTile( Integer.parseInt(splitString[0]), 
															Integer.parseInt(splitString[1]), 
															Integer.parseInt(splitString[2]),
															Integer.parseInt(splitString[3]));
					if(minX > mappedTile.x){
						minX = mappedTile.x;
					}
					if(minY > mappedTile.y){
						minY = mappedTile.y;
					}
					if(maxX < mappedTile.x){
						maxX = mappedTile.x;
					}
					if(maxY < mappedTile.y){
						maxY = mappedTile.y;
					}
					
					if(numLayers <= mappedTile.layer){
						numLayers = mappedTile.layer + 1;
					}
					
					mappedTiles.add(mappedTile);
					
				}
			}
			else{
				comments.put(currentLine, line);
			}
			currentLine++;
		}
		
		if(mappedTiles.size() == 0){
			minX = -blockWidth;
			minY = -blockHeight;
			maxX = blockWidth;
			maxY = blockHeight;
		}
		
		blockStartX = minX;
		blockStartY = minY;
		
		int blockSizeX = (maxX + blockWidth) - minX;
		int blockSizeY = (maxY + blockHeight) - minY;
		blocks = new Block [blockSizeX][blockSizeY];
		//Loop through all mapped tiles in entire level and save and add them to a block.
		for (int i = 0; i < mappedTiles.size(); i++) {
			MappedTile mappedTile = mappedTiles.get(i);
			
			int blockX = (mappedTile.x - minX) / blockWidth;
			int blockY = (mappedTile.y - minY) / blockHeight;
			
			assert(blockX >= 0 && blockX < blocks.length && blockY >= 0 && blockY < blocks[0].length);
			if(blocks[blockX][blockY] == null){
				blocks[blockX][blockY] = new Block();
			}
			
			blocks[blockX][blockY].addTile(mappedTile);
		}
		
	}
	catch(FileNotFoundException e){
		e.printStackTrace();
	}
}
	
	
	
	public MappedTile getTile(int layer, int tileX, int tileY){
		
		int blockX = (tileX - blockStartX) / blockWidth;
		int blockY = (tileY - blockStartY) / blockHeight;
		
		if(blockX < 0 || blockX >= blocks.length || blockY < 0 || blockY >= blocks[0].length){
			return null;
		}
		
		
		
		Block block = blocks[blockX][blockY];
		
		if(block == null){		
			return null;
		}
			return block.getTile(layer, tileX, tileY);
	
		
	}
	
	public boolean checkSurroundingTiles(Rectangle rect, int layer, int xZoom, int yZoom){
		int tileWidth = 16 * xZoom;
		int tileHeight = 16 * yZoom;
		
		//Coordinates to check all tiles in a radius on 4 around the player.
		int topLeftX = (rect.x - 64)/tileWidth;
		int topLeftY = (rect.y - 64)/tileHeight;
		int bottomRightX = (rect.x + rect.w + 64)/tileWidth;
		int bottomRightY = (rect.y + rect.h + 64)/tileHeight;
		

		//Starting at the top left tile and going to the bottom right.
		for (int x = topLeftX; x < bottomRightX; x++) {
			for (int y = topLeftY; y < bottomRightY; y++) {
				MappedTile tile = getTile(layer, x, y);
				if(tile != null){
					int collisionType = tileSet.collisionType(tile.id);
					if(collisionType == 0){
						Rectangle tileRectangle = new Rectangle(tile.x * tileWidth, tile.y * tileHeight, tileWidth, tileHeight);
						if(tileRectangle.intersects(rect)){
							return true;
						}
					}
					else if(collisionType == 1){
						return false;
					}
					else if(collisionType == 2){
						return false;
					}
					else if(collisionType == 3){
						Rectangle tileRectangle = new Rectangle(tile.x * tileWidth, tile.y * tileHeight + tileHeight - 16, tileWidth, 1);
						if(tileRectangle.intersects(rect)){
							return true;
						}
					}
					else if(collisionType == 4){
						return false;
					}
				}
			}
		}
		return false;
	}
	   
	
	public void setTile(int layer, int tileX, int tileY, int tileID){
		
		
		if(layer >= numLayers){
			numLayers = layer + 1;
		}

		
		for (int i = 0; i < mappedTiles.size(); i++) {
			MappedTile mappedTile = mappedTiles.get(i);
			if(mappedTile.x == tileX && mappedTile.y == tileY){
				mappedTile.id = tileID;
				return;
			}
		}
		

			MappedTile mappedTile = new MappedTile(layer, tileID, tileX, tileY);
			mappedTiles.add(mappedTile);
			
			//Add to blocks
			int blockX = (tileX - blockStartX) / blockWidth;
			int blockY = (tileY - blockStartY) / blockHeight;
			
			if(blockX >= 0 && blockY >= 0 && blockX < blocks.length && blockY < blocks[0].length){
				if(blocks[blockX][blockY] == null){
					blocks[blockX][blockY] = new Block();
				}
				blocks[blockX][blockY].addTile(mappedTile);
					
			}
			else{
				int newMinX = blockStartX;
				int newMinY = blockStartY;
				int newLengthX = blocks.length;
				int newLengthY = blocks[0].length;
				
				if(blockX < 0){
					int increaseAmount = blockX * -1;
					newMinX = blockStartX - blockWidth * increaseAmount;
					newLengthX = newLengthX + increaseAmount;
				} else if(blockX >= blocks.length){
					newLengthX = blocks.length + ((int) Math.ceil(blockX * 1.0/blockWidth));
				}
				
				if(blockY < 0){
					int increaseAmount = blockY * -1;
					newMinY = blockStartY - blockHeight * increaseAmount;
					newLengthY = newLengthY + increaseAmount;
				} else if(blockY >= blocks[0].length){
					newLengthY = blocks[0].length + ((int) Math.ceil(blockY * 1.0/blockHeight));
				}
				
				
				Block[][] newBlocks = new Block[newLengthX][newLengthY];
				
				for (int x = 0; x < blocks.length; x++) {
					for (int y = 0; y < blocks[0].length; y++) {
						if(blocks[x][y] != null){
							newBlocks[x + (blockStartX- newMinX)/blockWidth][y + (blockStartY - newMinY)/blockHeight] = blocks[x][y];
						}
					}
				}
				
				blocks = newBlocks;
				blockStartX = newMinX;
				blockStartY = newMinY;
				blockX = (tileX - blockStartX) / blockWidth;
				blockY = (tileY - blockStartY) / blockHeight;
				if(blocks[blockX][blockY] == null){
					blocks[blockX][blockY] = new Block();
				}
				blocks[blockX][blockY].addTile(mappedTile);
			}

		


	}
	
	public void removeTile(int layer, int tileX, int tileY){
		for (int i = 0; i < mappedTiles.size(); i++) {
			MappedTile mappedTile = mappedTiles.get(i);
			if( mappedTile.layer == layer && mappedTile.x == tileX && mappedTile.y == tileY){
				mappedTiles.remove(i);
				//Remove from block
				int blockX = (tileX - blockStartX) / blockWidth;
				int blockY = (tileY - blockStartY) / blockHeight;
			
				assert(blockX >= 0 && blockY >= 0 && blockX < blocks.length && blockY < blocks[0].length);
				blocks[blockX][blockY].removeTile(mappedTile);
					
				
				
				
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public void saveMap(){
		try {
			int currentLine = 0;
			if(mapFile.exists()){
				mapFile.delete();
			}
			mapFile.createNewFile();
		
			PrintWriter printWriter = new PrintWriter(mapFile);


			
			if(fillTileID >= 0){
				if(comments.containsKey(currentLine)){
					printWriter.println(comments.get(currentLine));
					currentLine++;
				}
				printWriter.println("fill:" + fillTileID);
			}
			for (int i = 0; i < mappedTiles.size(); i++) {
				if(comments.containsKey(currentLine)){
					printWriter.println(comments.get(currentLine));
				}
				MappedTile tile = mappedTiles.get(i);
				printWriter.println(tile.layer + "," + tile.id + "," + tile.x + "," + tile.y);
				currentLine++;
			}
			
			printWriter.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	
	public void render(RenderHandler renderer, GameObject[] objects, int xZoom, int yZoom){
		int tileWidth = 16 * xZoom;
		int tileHeight = 16 * yZoom;
		
		if(fillTileID >= 0){
			
			Rectangle camera = renderer.getCamera();
			
			for (int y = camera.y - tileHeight - (camera.y % tileHeight); y < camera.y + camera.h; y+= tileHeight) {
				for (int x = camera.x - tileWidth - (camera.x % tileWidth); x < camera.x + camera.w; x+= tileWidth) {
					tileSet.renderTile(fillTileID, renderer, x, y, xZoom, yZoom);
				}
			}
		}
		
		
		
;
		
		

		
		

		for (int layer = 0; layer < numLayers; layer++) {
		
			int topLeftX = renderer.getCamera().x;
			int topLeftY = renderer.getCamera().y;
			int bottomRightX = renderer.getCamera().x + renderer.getCamera().w;
			int bottomRightY = renderer.getCamera().y + renderer.getCamera().w;
					
			int leftBlockX = (topLeftX/tileWidth - blockStartX - 16)/blockWidth;
					
			int blockX = leftBlockX;
			int blockY = (topLeftY/tileHeight - blockStartY - 16)/blockHeight;
			
			int	pixelX = topLeftX;
			int pixelY = topLeftY;
			
			while(pixelX < bottomRightX && pixelY < bottomRightY){
				if(blockX >= 0 && blockY >= 0 && blockX < blocks.length && blockY < blocks[0].length){
					if(blocks[blockX][blockY] != null){
						blocks[blockX][blockY].render(renderer, layer, tileWidth, tileHeight, xZoom, yZoom);
					}
				}
				
				blockX++;
				pixelX += blockPixelWidth;
				
				
				if(pixelX > bottomRightX){
					pixelX = topLeftX;
					blockX = leftBlockX;
					blockY++;
					pixelY += blockPixelHeight;
					if(pixelY > bottomRightY){
						break;
					}
				}
			}
			for (int i = 0; i < objects.length; i++) {
				if(objects[i].getLayer() == layer){
					objects[i].render(renderer, xZoom, yZoom);
				}
				else if(objects[i].getLayer() + 1 == layer){
					Rectangle rect = objects[i].getRectangle();
					
					int tileBelowX = rect.x / tileWidth;
					int tileBelowX2 = (int) Math.floor((rect.x + rect.w/2 * xZoom * 1.0) / tileWidth);
					int tileBelowX3 = (int) Math.floor((rect.x + rect.w * xZoom * 1.0) / tileWidth);


					int tileBelowY = (int) Math.floor ((rect.y + rect.h * yZoom * 1.0) / tileHeight);					
					

					if(getTile(layer, tileBelowX, tileBelowY) == null && getTile(layer, tileBelowX2, tileBelowY) == null && getTile(layer, tileBelowX3, tileBelowY) == null){
						objects[i].render(renderer, xZoom, yZoom);
					}
					
				}
			}
			
		}
		
		for (int i = 0; i < objects.length; i++) {
			if(objects[i].getLayer() == Integer.MAX_VALUE){
				objects[i].render(renderer, xZoom, yZoom);
			}
		}	
	}
	
	//Block represents a 6x6 block of tiles
	@SuppressWarnings("unchecked")
	private class Block {
		public ArrayList<MappedTile>[] mappedTilesByLayer;
		
		
		public Block(){
			 mappedTilesByLayer = new ArrayList[numLayers];
			 for (int i = 0; i < mappedTilesByLayer.length; i++) {
				mappedTilesByLayer[i] = new ArrayList<MappedTile>();
			}
		}
		
		
		public void render(RenderHandler renderer, int layer, int tileWidth, int tileHeight, int xZoom, int yZoom){
			if(mappedTilesByLayer.length > layer){
				ArrayList<MappedTile> mappedTiles = mappedTilesByLayer[layer];
				
				for (int tileIndex = 0; tileIndex < mappedTiles.size(); tileIndex++) {
				MappedTile mappedTile = mappedTiles.get(tileIndex);
				tileSet.renderTile(mappedTile.id, renderer, mappedTile.x * tileWidth, mappedTile.y * tileHeight, xZoom, yZoom);
				
				}
			}
		}
		public void addTile(MappedTile tile){
			if(mappedTilesByLayer.length <= tile.layer){
				ArrayList<MappedTile>[] newTilesByLayer = new ArrayList[tile.layer + 1];
				
				
				
				int i = 0;
				for (i = 0; i < mappedTilesByLayer.length; i++) {
					newTilesByLayer[i] = mappedTilesByLayer[i];
				}
				for (; i < newTilesByLayer.length; i++) {
					newTilesByLayer[i] = new ArrayList<MappedTile>();
				}
				
				
				mappedTilesByLayer = newTilesByLayer;
			}
			mappedTilesByLayer[tile.layer].add(tile);
		}
		
		public void removeTile(MappedTile tile){
			mappedTilesByLayer[tile.layer].remove(tile);
		}
		
		public MappedTile getTile(int layer, int tileX, int tileY){
			for (MappedTile tile : mappedTilesByLayer[layer]) {
				if(tile.x == tileX && tile.y == tileY){
					return tile;
				}
				
			}
			return null;
		}
		
	}
	public Tiles getTileSet(){
		return tileSet;
	}
	
	
	
	//TileID in the tileSet and the position of the tile in the map
	public class MappedTile{
		public int layer, id, x, y;
		
		public MappedTile(int layer, int id, int x, int y){
			this.layer = layer;
			this.id = id;
			this.x = x;
			this.y = y;
		}
	}
	
}