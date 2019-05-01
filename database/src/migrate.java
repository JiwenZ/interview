import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class migrate {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/info";
    static final String USER = "root";
    static final String PASSWORD = "654321";
    public static void main(String[] args) {

        Connection mysqlConnection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connection to database...");
            mysqlConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            String query = " insert into reviewer (firstname, id, name, cell, work, email, addr, basic, adv, project) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            System.out.println("Creating sql statement...");
            preparedStatement = mysqlConnection.prepareStatement(query);
            mysqlConnection.setAutoCommit(false);
            getJsonStrings(mysqlConnection, preparedStatement);
            preparedStatement.close();
            mysqlConnection.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void getJsonStrings(Connection mySqlConnection, PreparedStatement preparedStatement) {
        LinkedHashSet<String> jsonLineSet = new LinkedHashSet<>();
        String reviewer = null;
        try {
            FileInputStream inputStream = new FileInputStream("DB.json");
            Scanner sc = new Scanner(inputStream, "UTF-8");
            while(sc.hasNextLine()) {
                for (int i = 0; i < 10; i++) {
                    if (sc.hasNextLine()) {
                        reviewer = sc.nextLine();
                        jsonLineSet.add(reviewer);
                    } else {
                        System.out.println("Done!");
                        insertData(mySqlConnection, preparedStatement, jsonLineSet);
                        break;
                    }
                }
                insertData(mySqlConnection, preparedStatement, jsonLineSet);
            }
            sc.close();
        }catch(IOException ie) {
            ie.printStackTrace();
        }
    }

    public static void insertData(Connection mySqlConnection, PreparedStatement preparedStatement, LinkedHashSet<String> jsonLineSet) {

        for(String jsonLine : jsonLineSet) {
            try {
                System.out.println("Executing query...");
                JsonElement jsonElement = new JsonParser().parse(jsonLine);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                String firstname = jsonObject.get("firstName").getAsString();
                int id = jsonObject.get("Record ID").getAsInt();
                String name = jsonObject.get("Name").getAsString();
                String cell = jsonObject.get("Cell Phone").getAsString();
                String work = jsonObject.get("Work Phone").getAsString();
                String email = jsonObject.get("Email").getAsString();
                String addr = jsonObject.get("Address").getAsString();
                int basic = jsonObject.get("Basic Widget Order").getAsInt();
                int adv = jsonObject.get("Advanced Widget Order").getAsInt();
                Boolean project = jsonObject.get("Protection Plan").getAsBoolean();
                int project_int = 1
                if(project == Boolean.FALSE){
                    project_int = 0
                }

                System.out.println(firstname);
                System.out.println(id);
                System.out.println(name);
                System.out.println(cell);
                System.out.println(work);
                System.out.println(email);
                System.out.println(addr);
                System.out.println(basic);
                System.out.println(adv);
                System.out.println(project);
                // Insert statement.
                preparedStatement.setString(1, firstname);
                preparedStatement.setInt(2, id);
                preparedStatement.setString(3, name);
                preparedStatement.setString(4, cell);
                preparedStatement.setString(5, work);
                preparedStatement.setString(6, email);
                preparedStatement.setString(7, addr);
                preparedStatement.setInt(8, basic);
                preparedStatement.setInt(9, adv);
                preparedStatement.setInt(10, project_int);
                preparedStatement.addBatch();
            }catch(SQLException se) {
                se.printStackTrace();
            }
        }
        try {
            preparedStatement.executeBatch();
            mySqlConnection.commit();
            preparedStatement.clearBatch();
        }catch(SQLException se) {
            se.printStackTrace();
        }
    }

}