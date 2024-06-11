package restaurant_jdbc;

public class Item {

	private String itemname;
	private float itemprice;

	public Item() {
		super();
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public float getItemprice() {
		return itemprice;
	}

	public void setItemprice(float itemprice) {
		this.itemprice = itemprice;
	}

	@Override
	public String toString() {
		return "Item [itemname=" + itemname + ", itemprice=" + itemprice + "]";
	}

}
