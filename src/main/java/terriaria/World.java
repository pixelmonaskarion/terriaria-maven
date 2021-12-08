package terriaria;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.HashMap;

import terriaria.blocks.AirBlock;
import terriaria.blocks.Block;
import terriaria.entities.Entity;
import terriaria.particles.Particle;

public class World {
	public ArrayList<Entity> entities = new ArrayList<>();
	public HashMap<String, Chunk> chunks = new HashMap<>();
	public World() {
		//generate();
		for (int x = -2; x < 3; x++) {
			for (int y = -2; y <3; y++) {
				chunks.put(x+"|"+y, new Chunk(x, y, this));
				System.out.println("generated chunk x: " + x + " y: " + y);
			}
		}
		for (String chunk : chunks.keySet()) {
			System.out.println(chunk);
		}
	}

	public void updateAll(Graphics g, ImageObserver ob) {
		for (Chunk chunk : chunks.values()) {
			chunk.updateAll(g, ob);
//			System.out.println("drew chunk x: " + chunk.chunkx + " y: " + chunk.chunky);
		}
		for (Entity e : entities) {
			chunks.get(e.x/Chunk.worldx/Game.blockSize + "|" + e.y/Chunk.worldy/Game.blockSize).spawnEntity(e);
		}
		entities.clear();
	}
	public Block getBlock(int x, int y) {
		//try {
			System.out.println(chunks.get(x/Chunk.worldx + "|" + y/Chunk.worldy).getBlock(x%Chunk.worldx, y%Chunk.worldy));
			return chunks.get(x/Chunk.worldx + "|" + y/Chunk.worldy).getBlock(x%Chunk.worldx, y%Chunk.worldy);
		//} catch (NullPointerException e) {
			//return new AirBlock(x, y, this);
		//}
		
	}
	public Block getTopBlock(int x) {
//		for (int y = 0; y < Chunk.worldy; y++) {
//			if (world[blockIndex(x, y)].hasHitbox()) {
//				return world[blockIndex(x, y)];
//			}
//		}
//		return null;
		return getBlock(0,0);
	}
	public Block getTopLightingBlock(int x) {
////		return world.get(x).get(world.get(x).size()-1);
//		for (int y = 0; y < worldy; y++) {
//			if (!world[blockIndex(x, y)].letsLightThrough()) {
////				System.out.println(world[blockIndex(x, y)]);
//				return world[blockIndex(x, y)];
//			}
//		}
//		return null;
		return getBlock(0,0);
	}
	public void setBlock(int x, int y, Block b) {
		try {
			chunks.get(x/Chunk.worldx + "|" + y/Chunk.worldy).setBlock(x%Chunk.worldx, y%Chunk.worldy, b);
		} catch (IndexOutOfBoundsException e) {
			
		}
		
	}
	public void deleteEntity(Entity en) {
//		deleteentities.add(en);
	}
	public ArrayList<Float> getAround(int x, int y, ArrayList<ArrayList<Float>> grid) {
		ArrayList<Float> floats = new ArrayList<Float>();
		try {
		floats.add(grid.get(x-1).get(y));
		} catch (IndexOutOfBoundsException e) {}
		try {
		floats.add(grid.get(x+1).get(y));
		} catch (IndexOutOfBoundsException e) {}
		try {
		floats.add(grid.get(x).get(y-1));
		} catch (IndexOutOfBoundsException e) {}
		try {
		floats.add(grid.get(x).get(y+1));
		} catch (IndexOutOfBoundsException e) {}
		try {
		floats.add(grid.get(x-1).get(y-1));
		} catch (IndexOutOfBoundsException e) {}
		try {
		floats.add(grid.get(x-1).get(y+1));
		} catch (IndexOutOfBoundsException e) {}
		try {
		floats.add(grid.get(x+1).get(y-1));
		} catch (IndexOutOfBoundsException e) {}
		try {
		floats.add(grid.get(x+1).get(y+1));
		} catch (IndexOutOfBoundsException e) {}
		return floats;
	}
	public void reloadTextures() {
		for (Chunk chunk : chunks.values()) {
			chunk.reloadTextures();
		}
	}
	public void spawnEntity(Entity e) {
		//spawnEntities.add(e);
	}
	public void addParticle(Particle p) {
		//spawnParticles.add(p);
	}
	public void removeParticle(Particle p) {
		//deleteParticles.add(p);
	}
	public void setWorlds() {
		for (Chunk chunk : chunks.values()) {
			chunk.world = this;
			chunk.setWorlds();
		}
	}
	
//	public int blockIndex(int x, int y) {
//		return x + (y * worldx);
//	}
}
