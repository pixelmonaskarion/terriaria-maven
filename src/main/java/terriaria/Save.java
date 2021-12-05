package terriaria;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Save {
	@SuppressWarnings("deprecation")
	public byte[] saveWorld(Game game) {
		String string = "";
		game.saving = true;
		ObjectMapper mapper = new ObjectMapper();
		mapper.enableDefaultTyping();
		try {
			string = string+mapper.writeValueAsString(game.world);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			System.exit(0);
		}
		game.saving = false;
		return string.getBytes();
	}
	public byte[] savePlayer(Game game) {
		String string = "";
		game.saving = true;
		ObjectMapper mapper = new ObjectMapper();
		try {
			string = string+mapper.writeValueAsString(Game.p);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			System.exit(0);
		}
		game.saving = false;
		return string.getBytes();
	}
}