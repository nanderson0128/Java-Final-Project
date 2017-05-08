import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
				if(splitString.length >= 3){
					MappedTile mappedTile = new MappedTile(Integer.parseInt(splitString[0]), Integer.parseInt(splitString[1]), Integer.parseInt(splitString[2]));
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
					
					mappedTiles.add(mappedTile);
					
				}
			}
			else{
				comments.put(currentLine, line);
			}
			currentLine++;
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
			
			blocks[blockX][blockY].mappedTiles.add(mappedTile);
		}
		
	}
	catch(FileNotFoundException e){
		e.printStackTrace();
	}
}
	
	
	
	
	
	
	

	
	public void setTile(int tileX, int tileY, int tileID){
		

		
		for (int i = 0; i < mappedTiles.size(); i++) {
			MappedTile mappedTile = mappedTiles.get(i);
			if(mappedTile.x == tileX && mappedTile.y == tileY){
				mappedTile.id = tileID;
				return;
			}
		}
		

			MappedTile mappedTile = new MappedTile(tileID, tileX, tileY);
			mappedTiles.add(mappedTile);
			
			//Add to blocks
			int blockX = (tileX - blockStartX) / blockWidth;
			int blockY = (tileY - blockStartY) / blockHeight;
			
			if(blockX >= 0 && blockY >= 0 && blockX < blocks.length && blockY < blocks[0].length){
 
			}
			else{
				int newMinX = blockStartX;
				int newMinY = blockStartY;
				int newLengthX = blocks.length;
				int newLengthY = blocks[0].length;
				
				if(blockX < 0){
					newMinX = blockStartX - blockWidth*((int) Math.ceil(blockX * -1.0/blockWidth));
					newLengthX = newLengthX + (blockStartX - newMinX);
				} else if(blockX >= blocks.length){
					newLengthX = blocks.length + ((int) Math.ceil(blockX * 1.0/blockWidth));
				}
				
				if(blockY < 0){
					newMinY = blockStartY - blockHeight*((int) Math.ceil(blockY * -1.0/blockHeight));
					newLengthY = newLengthY + (blockStartY - newMinY);
				} else if(blockY >= blocks[0].length){
					newLengthY = blocks[0].length + ((int) Math.ceil(blockY * 1.0/blockHeight));
				}
				
				
				Block[][] newBlocks = new Block[newLengthX][newLengthY];
				
				for (int x = 0; x < blocks.length; x++) {
					for (int y = 0; y < blocks[0].length; y++) {
						if(blocks[x][y] != null){
							newBlocks[x + blockStartX - newMinX][y + blockStartY - newMinY] = blocks[x][y];
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
				blocks[blockX][blockY].mappedTiles.add(mappedTile);
			}

		


	}
	
	public void removeTile(int tileX, int tileY){
		for (int i = 0; i < mappedTiles.size(); i++) {
			MappedTile mappedTile = mappedTiles.get(i);
			if(mappedTile.x == tileX && mappedTile.y == tileY){
				mappedTiles.remove(i);
				//Remove from block
				int blockX = (tileX - blockStartX) / blockWidth;
				int blockY = (tileY - blockStartY) / blockHeight;
			
				assert(blockX >= 0 && blockY >= 0 && blockX < blocks.length && blockY < blocks[0].length);
				blocks[blockX][blockY].mappedTiles.remove(mappedTile);
					
				
				
				
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
				printWriter.println(tile.id + "," + tile.x + "," + tile.y);
				currentLine++;
			}
			
			printWriter.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	
	public void render(RenderHandler renderer, int xZoom, int yZoom){
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
					blocks[blockX][blockY].render(renderer, tileWidth, tileHeight, xZoom, yZoom);
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
		
//		for (int tileIndex = 0; tileIndex < mappedTiles.size(); tileIndex++) {
//			MappedTile mappedTile = mappedTiles.get(tileIndex);
//			tileSet.renderTile(mappedTile.id, renderer, mappedTile.x * tileWidth, mappedTile.y * tileHeight, xZoom, yZoom);
//			
//		}
	}
	
	
//	public void render(RenderHandler renderer, int xZoom, int yZoom){
//		int tileWidth = 16 * xZoom;
//		int tileHeight = 16 * yZoom;
//		
//		if(fillTileID >= 0){
//			
//			Rectangle camera = renderer.getCamera();
//			
//			for (int y = camera.y - tileHeight - (camera.y % tileHeight); y < camera.y + camera.h; y+= tileHeight) {
//				for (int x = camera.x - tileWidth - (camera.x % tileWidth); x < camera.x + camera.w; x+= tileWidth) {
//					tileSet.renderTile(fillTileID, renderer, x, y, xZoom, yZoom);
//				}
//			}
//		}
//		
//		for (int tileIndex = 0; tileIndex < mappedTiles.size(); tileIndex++) {
//			MappedTile mappedTile = mappedTiles.get(tileIndex);
//			tileSet.renderTile(mappedTile.id, renderer, mappedTile.x * tileWidth, mappedTile.y * tileHeight, xZoom, yZoom);
//			
//		}
//	}
	
	//Block represents a 6x6 block of tiles
	private class Block {
		public ArrayList<MappedTile> mappedTiles = new ArrayList<MappedTile>();
		
		public void render(RenderHandler renderer, int tileWidth, int tileHeight, int xZoom, int yZoom){
			for (int tileIndex = 0; tileIndex < mappedTiles.size(); tileIndex++) {
			MappedTile mappedTile = mappedTiles.get(tileIndex);
			tileSet.renderTile(mappedTile.id, renderer, mappedTile.x * tileWidth, mappedTile.y * tileHeight, xZoom, yZoom);
			
			}
		}
		
		
	}
	
	
	
	//TileID in the tileSet and the position of the tile in the map
	private class MappedTile{
		public int id, x, y;
		
		public MappedTile(int id, int x, int y){
			this.id = id;
			this.x = x;
			this.y = y;
		}
	}
	
}
