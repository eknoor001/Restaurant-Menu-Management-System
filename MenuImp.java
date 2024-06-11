package restaurant_jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

class MenuImp implements MenuService {
	static Scanner sc = new Scanner(System.in);
	static Connection conn = null;

	public MenuImp() throws ClassNotFoundException, SQLException {
		conn = GetConnection.getinstance().getConnection();
	}

	static int getID() {
		System.out.println("Enter Item ID::");
		return sc.nextInt();
	}

	static Category getCategory() {
		Category cat = new Category();
		System.out.println("Enter the Category");
		cat.setCategoryname(sc.next());

		return cat;

	}

	static Item getinput() {
		Item product = new Item();
		System.out.println("Enter product details::");

		System.out.println("Enter name:");
		product.setItemname(sc.next());

		System.out.println("Enter price");
		product.setItemprice(sc.nextInt());

		return product;
	}

	private int getIncrementID(String table_seq) throws SQLException {
		String q_temp = "select " + table_seq + ".nextval from dual";
		Statement st = conn.createStatement();
		ResultSet rr = st.executeQuery(q_temp);
		Integer id = -1;
		while (rr.next()) {
			id = rr.getInt(1);
		}
		return id;
	}

	@Override
	public int insert() throws SQLException {
		Category cat = getCategory();
		Item item = getinput();
		int item_id = getIncrementID("seq_item");

		String query1 = "select category_id,category_name from category";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query1);
		while (rs.next()) {

			if (rs.getString(2).equalsIgnoreCase(cat.getCategoryname())) {
				String query3 = "insert all into item values (?,?,?)" + " into category_item values(?,?) "
						+ "select * from dual";

				PreparedStatement pstmt = conn.prepareStatement(query3);
				pstmt.setInt(1, item_id);
				pstmt.setString(2, item.getItemname());
				pstmt.setFloat(3, item.getItemprice());
				pstmt.setInt(4, rs.getInt(1));
				pstmt.setInt(5, item_id);
				return pstmt.executeUpdate();

			}
		}

		int cat_id = getIncrementID("seq_category");

		String query2 = "insert all" + " into category values (?,?)" + " into item values (?,?,?)"
				+ " into category_item values(?,?) " + "select * from dual";

		PreparedStatement pstmt = conn.prepareStatement(query2);
		pstmt.setInt(1, cat_id);
		pstmt.setString(2, cat.getCategoryname());
		pstmt.setInt(3, item_id);
		pstmt.setString(4, item.getItemname());
		pstmt.setFloat(5, item.getItemprice());
		pstmt.setInt(6, cat_id);
		pstmt.setInt(7, item_id);

		return pstmt.executeUpdate();

	}

	@Override
	public void fetch(String category_name) throws SQLException {

		String query = "select item_name,item_price from item where item_id in "
				+ "(select item_id from category "
				+ "INNER JOIN category_item ON category.category_id = category_item.category_id "
				+ "where category.category_name =?)";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, category_name);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(1) + " " + rs.getInt(2));
			System.out.println();
		}

	}

	@Override
	public int delete(String category_name) throws SQLException {
		// TODO Auto-generated method stub
		String query = "select category_item.item_id,category_item.category_id from category INNER JOIN category_item ON category.category_id = category_item.category_id where category.category_name =?";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, category_name);
		ResultSet rs = pstmt.executeQuery();

		query = "delete from category_item where category_item.category_id in (select category_item.category_id from category INNER JOIN category_item ON category.category_id = category_item.category_id where category.category_name =?)";
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, category_name);
		pstmt.executeUpdate();

		while (rs.next()) {
			String query1 = "delete from category where category_id = ?";
			String query2 = "delete from item where item_id = ?";
			PreparedStatement pstmt1 = conn.prepareStatement(query1);
			PreparedStatement pstmt2 = conn.prepareStatement(query2);
			pstmt1.setInt(1, rs.getInt(2));
			pstmt2.setInt(1, rs.getInt(1));

			pstmt1.executeUpdate();
			pstmt2.executeUpdate();
		}

		return 0;
	}

	@Override
	public int update(String item_name) throws SQLException {
		// TODO Auto-generated method stub

		int present = searchItem(item_name);

		if (present == 0) {
			return 0;

		}
		Item item = getinput();
		String query = "update item set item_name = ?, item_price = ? where item_name = ?";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, item.getItemname());
		pstmt.setFloat(2, item.getItemprice());
		pstmt.setString(3, item_name);
		return pstmt.executeUpdate();

	}

	@Override
	public void fetchSpecificCol(String name) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public int searchItem(String item_name) throws SQLException {
		String query = "select * from item where item_name = ?";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, item_name);
		ResultSet rs = pstmt.executeQuery();
		boolean flag = false;
		while (rs.next()) {

			flag = true;
			System.out.println(rs.getInt(1) + "/" + rs.getString(2) + "/" + rs.getInt(3));
		}

		if (flag == false)
			System.out.println("Sorry! Item named " + item_name + " not available.");
		return (flag == true ? 1 : 0);
	}

	@Override
	public void showAll() throws SQLException {
		// TODO Auto-generated method stub
		String query = "select category_name from category";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			System.out.println(rs.getString(1));
		}

	}

	public void close() {
		try {
			conn.close();
			System.out.println("Connection closed...");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

}