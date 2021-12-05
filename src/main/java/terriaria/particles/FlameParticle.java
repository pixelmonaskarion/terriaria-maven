package terriaria.particles;

import java.util.Random;

import terriaria.Game;

public class FlameParticle extends Particle {

	public FlameParticle(int x, int y) {
		super(x+new Random().nextInt(Game.blockSize)-Game.blockSize/2, y+new Random().nextInt(Game.blockSize)-Game.blockSize/2, 1000);
		this.image = Game.images.particles.get(0);
	}

}
