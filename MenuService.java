package restaurant_jdbc;

import java.sql.SQLException;

public interface MenuService {

	int searchItem(String item_name) throws SQLException;
	
	int insert() throws SQLException;

	void fetch(String category_item) throws SQLException;

	int delete(String category_name) throws SQLException;

	void fetchSpecificCol(String name) throws SQLException;

	int update(String item_name) throws SQLException;
	
	void showAll() throws SQLException;

}
