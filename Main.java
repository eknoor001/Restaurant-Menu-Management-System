package restaurant_jdbc;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		Scanner sc = new Scanner(System.in);
		MenuImp obj = new MenuImp();
		do {
			System.out.println("======Menu======");
			System.out.println("\n1. Add Item." + "\n2. Search for an Item." + "\n3. Update Item details."
					+ "\n4. Display all Items of the specific Category." + "\n5. Delete item from category"
					+ "\n6. Show all available categories" + "\n0. Exit");
			int key = 0;
			try {

				key = sc.nextInt();

			} catch (Exception e) {
				System.out.println("Please enter integer.");
			}

			switch (key) {
			case 1:
				int inserted = obj.insert();
				System.out.println(inserted+" rows inserted");
				break;
			case 2:
				System.out.println("Enter the item_name ");
				obj.searchItem(sc.next());
				break;
			case 3:
				System.out.println("Enter the Item name ");
				int updated = obj.update(sc.next());
				System.out.println("\n"+updated+" rows updated\n");

				break;
			case 4:
				System.out.println("Enter the Category ");
				obj.fetch(sc.next());
				break;
			case 5:
				System.out.println("Enter the category name to be deleted.");
				obj.delete(sc.next());
				break;
			case 6:

				obj.showAll();

				break;
			case 0:

				System.exit(0);
				break;
			default:
				System.out.println("Enter the valid choice!s");
			}

		} while (true);
	}
}
