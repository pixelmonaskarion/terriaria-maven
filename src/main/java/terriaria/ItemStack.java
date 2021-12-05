package terriaria;

import java.beans.ConstructorProperties;
import java.util.Random;

public class ItemStack {
	public String type;
	public int amount;
	public long UUID;
	@ConstructorProperties({"type", "amount"})
	public ItemStack(String type, int amount) {
		this.type = type;
		this.amount = amount;
		this.UUID = new Random().nextInt(99999999);
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemStack) {
			ItemStack item = (ItemStack)obj;
			if (this.type == item.type) {
				return true;
			} else {return false;}
		} else {
			return false;
		}
	}
	
	public ItemStack(ItemStack item) {
		this.type = item.type;
		this.amount = item.amount;
	}
}
