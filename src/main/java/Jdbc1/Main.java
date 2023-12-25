package Jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/kwkdjds?serverTimezone=Europe/Kiev";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "Helloworld2";

    public static void main(String[] args) {
        try {
            createTables();
            insertRandomData("Клиенты", 10);
            insertRandomData("Заказы", 10);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTables() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            // Создание таблицы "Клиенты"
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Клиенты (id INT PRIMARY KEY, имя VARCHAR(255), email VARCHAR(255))");

            // Создание таблицы "Заказы"
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Заказы (id INT PRIMARY KEY, продукт VARCHAR(255), количество INT, клиент_id INT, FOREIGN KEY (клиент_id) REFERENCES Клиенты(id))");
        }
    }

    private static void insertRandomData(String tableName, int count) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            Scanner scanner = new Scanner(System.in);
            Random random = new Random();
            for (int i = 1; i <= count; i++) {
                System.out.println("Введите данные для записи в таблицу " + tableName + " (запись " + i + "):");
                System.out.print("Имя: ");
                String name = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();

                if (tableName.equals("Клиенты")) {
                    statement.executeUpdate("INSERT INTO " + tableName + " VALUES (" + i + ", '" + name + "', '" + email + "')");
                } else if (tableName.equals("Заказы")) {
                    System.out.print("Продукт: ");
                    String product = scanner.nextLine();
                    int quantity = random.nextInt(10) + 1;
                    statement.executeUpdate("INSERT INTO " + tableName + " VALUES (" + i + ", '" + product + "', " + quantity + ", " + i + ")");
                }
            }
        }
    }
}


