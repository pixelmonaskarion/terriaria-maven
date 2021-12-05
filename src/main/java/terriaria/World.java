package terriaria;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;

import terriaria.blocks.AirBlock;
import terriaria.blocks.BlackCrystalOreBlock;
import terriaria.blocks.Block;
import terriaria.blocks.CaveAirBlock;
import terriaria.blocks.DeepStoneBlock;
import terriaria.blocks.DiamondOreBlock;
import terriaria.blocks.DirtBlock;
import terriaria.blocks.GoldOreBlock;
import terriaria.blocks.GrassBlock;
import terriaria.blocks.IronOreBlock;
import terriaria.blocks.LeafBlock;
import terriaria.blocks.MagmaOreBlock;
import terriaria.blocks.SandBlock;
import terriaria.blocks.StoneBlock;
import terriaria.blocks.WaterBlock;
import terriaria.blocks.WhiteCrystalOreBlock;
import terriaria.blocks.WoodBlock;
import terriaria.entities.Entity;
import terriaria.particles.Particle;

public class World {
	public static int worldx = 500;
	public static int worldy = 200;
	public Block[] world = new Block[worldx*worldy];
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Entity> deleteentities = new ArrayList<Entity>();
	private ArrayList<Entity> spawnEntities = new ArrayList<Entity>();
	@JsonIgnore
	public ArrayList<Particle> particles = new ArrayList<Particle>();
	@JsonIgnore
	private ArrayList<Particle> deleteParticles = new ArrayList<Particle>();
	@JsonIgnore
	private ArrayList<Particle> spawnParticles = new ArrayList<Particle>();
	public boolean generating;
	public World() {
		generate();
	}
	public void generate() {
		generating = true;
		NoiseGenerator r = new NoiseGenerator();
		for (int x = 0; x < worldx; x++) {
			for (int y = 0; y < worldy; y++) {
				double height = (((r.noise(x)*100)+100)/10)+50;
				if (y == (int)height) {
					if (y != 0) {
						if (world[x * worldy + y - 1] instanceof WaterBlock) {
							world[x * worldy + y] = new SandBlock(x, y, this);
						} else {
							world[x * worldy + y] = new GrassBlock(x, y, this);
						}
					} else {
						world[x * worldy + y] = new GrassBlock(x, y, this);
					}
				} else {
					if (y < height){
						if (y > 20) {
							world[x * worldy + y] = new WaterBlock(x, y, this);
						} else {
							System.out.println("x:" + x + "y: " + y);
							world[x * worldy + y] = new AirBlock(x, y, this);
						}
					}
					if (y > height) {
						if (y-5 > height) {
							world[x * worldy + y] = generateStone(x, y, true);
						} else {
							world[x * worldy + y] = new SandBlock(x, y, this);
						}
					}
				}
			}
		}
		NoiseGenerator islands = new NoiseGenerator();
		if (true) {
			int x = 0;
			int islandSize = new Random().nextInt(200)+50;
			boolean done = false;
			while (x < worldx+1) {
				if (done) {
					x += new Random().nextInt(50)+100;
					islandSize = new Random().nextInt(200)+50+x;
					done = false;
					int tempx = x-1;
					while (!done) {
						boolean noneWater = true;
						double height = (((islands.noise(tempx)*100)+100)/3)-15+(x-tempx);
						for (int y = 0; y < worldy+1; y++) {
							if (getBlock(tempx, y) instanceof WaterBlock || getBlock(tempx, y) instanceof AirBlock) {
								if (y >= height) {
									if (getBlock(tempx, y) instanceof WaterBlock) {
										noneWater = false;
									}
									if (y-5 > height) {
										setBlock(tempx, y, generateStone(tempx, y, false));
									} else {
										setBlock(tempx, y, new SandBlock(tempx, y, this));
									}
								}
							}
						}
						if (noneWater) {
							done = true;
						}
						tempx--;
					}
					done = false;
				}
				if (x <= islandSize) {
					for (int y = 0; y < worldy+1; y++) {
						double height = (((islands.noise(x)*100)+100)/3)-15;
						if (getBlock(x, y) instanceof WaterBlock || getBlock(x, y) instanceof AirBlock) {
							if (y == (int)height) {
								if (y != 0) {
									if (getBlock(x, y-1) instanceof WaterBlock) {
										setBlock(x, y, new SandBlock(x, y, this));
									} else {
										setBlock(x, y, new GrassBlock(x, y, this));
									}
								} else {
									setBlock(x, y, new GrassBlock(x, y, this));
								}
							} else {
								if (y < height){
									if (y > 20) {
										setBlock(x, y, new WaterBlock(x, y, this));
									} else {
										setBlock(x, y, new AirBlock(x, y, this));
									}
								}
								if (y > height) {
									if (y-5 > height) {
										setBlock(x, y, generateStone(x, y, false));
									} else {
										setBlock(x, y, new DirtBlock(x, y, this));
									}
								}
							}
						}
					}
				} else {
					boolean noneWater = true;
					double height = (((islands.noise(x)*100)+100)/3)-15+(x-islandSize);
					for (int y = 0; y < worldy+1; y++) {
						if (getBlock(x, y) instanceof WaterBlock || getBlock(x, y) instanceof AirBlock) {
							if (y >= height) {
								if (getBlock(x, y) instanceof WaterBlock) {
									noneWater = false;
								}
								if (y-5 > height) {
									setBlock(x, y, generateStone(x, y, false));
								} else {
									setBlock(x, y, new SandBlock(x, y, this));
								}
							}
						}
					}
					if (noneWater) {
						done = true;
					}
				}
				x++;
			}
		}
		for (int x = 0; x < worldx+1; x++) {
				if (new Random().nextInt(10) == 1) {
					if (getTopBlock(x) instanceof GrassBlock) {
						int topY = getTopBlock(x).y-new Random().nextInt(4)-4;
						for (int y = getTopBlock(x).y-1; y > topY; y--) {
							setBlock(x, y, new WoodBlock(x, y, this));
						}
						setBlock(x, topY-1, new LeafBlock(x, topY-1, this));
						setBlock(x, topY, new LeafBlock(x, topY, this));
						setBlock(x-1, topY+1, new LeafBlock(x-1, topY+1, this));
						setBlock(x+1, topY+1, new LeafBlock(x+1, topY+1, this));
						setBlock(x-1, topY, new LeafBlock(x-1, topY, this));
						setBlock(x+1, topY, new LeafBlock(x+1, topY, this));
					}
				}
			}
		NoiseGenerator caves = new NoiseGenerator();
		ArrayList<ArrayList<Float>> grid = new ArrayList<ArrayList<Float>>();
		for (int x = 0; x < worldx+1; x++) {
			ArrayList<Float> column = new ArrayList<Float>();
			for (int y = 0; y < worldy+1; y++) {
				float noise = (float) caves.noise(x, y);
				if (noise > 0) {
					noise = 1f;
				} else {
					noise = 0f;
				}
				column.add(noise);
			}
			grid.add(column);
		}
		for (int x = 0; x < worldx+1; x++) {
			for (int y = 0; y < worldy+1; y++) {
				float i = (float) caves.noise(x, y);
				if (i > 0) {
					i = 1f;
				} else {
					i = 0f;
				}
				boolean isSide = false;
				if (i == 0) {
					if (getAround(x, y, grid).contains(1f)) {
						isSide = true;
					}
				}
				if (i == 1) {
					if (getAround(x, y, grid).contains(0f)) {
						isSide = true;
					}
				}
				if (isSide) {
					if (getBlock(x, y) instanceof StoneBlock || getBlock(x, y) instanceof GoldOreBlock || getBlock(x, y) instanceof DiamondOreBlock || getBlock(x, y) instanceof IronOreBlock) {
						setBlock(x, y, new CaveAirBlock(x, y, this));
					}
				}
			}
		}
		generating = false;
	}
	public Block generateStone(int x, int y, boolean deep) {
		Random random = new Random();
		if (!deep) {
			if (random.nextInt(worldy+1-y) == 4) {
				if (random.nextInt(50) == 4) {
					if (y > 60) {
						return new DiamondOreBlock(x, y, this);
					} else {
						return new GoldOreBlock(x, y, this);
					}
				} else {
					return new IronOreBlock(x, y, this);
				}
			} else {
				return new StoneBlock(x, y, this);
			}
		} else {
			if (random.nextInt(worldy+1-y) == 4) {
				if (random.nextInt(50) == 4) {
					if (y > 60) {
						return new BlackCrystalOreBlock(x, y, this);
					} else {
						return new WhiteCrystalOreBlock(x, y, this);
					}
				} else {
					return new MagmaOreBlock(x, y, this);
				}
			} else {
				return new DeepStoneBlock(x, y, this);
			}
		}
	}
	public void updateAll(Graphics g, ImageObserver ob) {
		if (generating == false) {
			for (int x = 0; x < worldx; x++) {
				for (int y = 0; y < worldy; y++) {
					world[x * worldy + y].update(g, ob);
				}
			}
			for (Entity en : entities) {
				en.update(g, ob);
				en.moveandstuff();
			}
			for (Entity en : deleteentities) {
				entities.remove(en);
			}
			deleteentities.clear();
			for (Entity en : spawnEntities) {
				entities.add(en);
			}
			spawnEntities.clear();
			for (Particle en : deleteParticles) {
				particles.remove(en);
			}
			deleteParticles.clear();
			for (Particle en : particles) {
				en.update(g, ob);
			}
			for (Particle en : spawnParticles) {
				particles.add(en);
			}
			spawnParticles.clear();
			Game.p.update(g, ob);
			for (int x = 0; x < worldx; x++) {
				for (int y = 0; y < worldy; y++) {
					world[x * worldy + y].drawLight(g);
				}
			}
		} else {
			System.out.println("not ready");
		}
	}
	public Block getBlock(int x, int y) {
		try {
			return world[x * worldy + y];
		} catch (IndexOutOfBoundsException e) {
			return new AirBlock(x, y, this);
		}
		
	}
	public Block getTopBlock(int x) {
		for (int y = 0; y < worldy; y++) {
			if (world[x * worldy + y].hasHitbox()) {
				return world[x * worldy + y];
			}
		}
		return null;
	}
	public Block getTopLightingBlock(int x) {
//		return world.get(x).get(world.get(x).size()-1);
		for (int y = 0; y < worldy; y++) {
			if (world[x * worldy + y].letsLightThrough()) {
				return world[x * worldy + y];
			}
		}
		return null;
	}
	public void setBlock(int x, int y, Block b) {
		try {
			world[x * worldy + y] = b;
		} catch (IndexOutOfBoundsException e) {
			
		}
		
	}
	public void deleteEntity(Entity en) {
		deleteentities.add(en);
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
		for (int x = 0; x < worldx; x++) {
			for (int y = 0; y < worldy; y++) {
				world[x * worldy + y].loadTexture(world[x * worldy + y].type);
			}
		}
	}
	public void spawnEntity(Entity e) {
		spawnEntities.add(e);
	}
	public void addParticle(Particle p) {
		spawnParticles.add(p);
	}
	public void removeParticle(Particle p) {
		deleteParticles.add(p);
	}
	public void setWorlds() {
		for (int x = 0; x < worldx; x++) {
			for (int y = 0; y < worldy; y++) {
				world[x * worldy + y].world = this;
			}
		}
		for (Entity e : entities) {
			if (e.world == null) {
				e.world = this;
			}
		}
	}
}
