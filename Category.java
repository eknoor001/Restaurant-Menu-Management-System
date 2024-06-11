package restaurant_jdbc;

public class Category {

	private String categoryname;

	public Category() {
		super();
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	@Override
	public String toString() {
		return "Category [categoryname=" + categoryname + "]";
	}

}
