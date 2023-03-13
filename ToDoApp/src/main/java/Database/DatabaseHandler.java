package Database;

import model.Tasks;
import model.User;

import java.sql.*;

public class DatabaseHandler extends Configs{
     Connection dbConnection;

     public Connection getDbConnection() throws ClassNotFoundException, SQLException {
          Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
          String connectionString = "jdbc:sqlserver://localhost;DatabaseName=ToDo;user=todoapp;password=todoapp123;encrypt=true;trustServerCertificate=true";


          Connection dbConnection = DriverManager.getConnection(connectionString);
          return dbConnection;
     }

     public void deleteTask(int userId, int taskId) throws SQLException, ClassNotFoundException {
          String query = "DELETE FROM " + Const.TASKS_TABLE
                  + " WHERE " + Const.USERS_ID
                  + "=?" + " AND " + Const.TASKS_ID + "=?";

          PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
          preparedStatement.setInt(1, userId);
          preparedStatement.setInt(2, taskId);
          preparedStatement.execute();
          preparedStatement.close();
     }

     public void signUpUser(User user) {
          String insert = "INSERT INTO " + Const.USERS_TABLE
                  + "(" + Const.USERS_NAME
                  + "," + Const.USERS_SURNAME
                  + "," + Const.USERS_USERNAME
                  + "," + Const.USERS_PASSWORD
                  + "," + Const.USERS_GENDER
                  + ")" + "VALUES(?, ?, ?, ?, ?)";

          try {
               PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

               preparedStatement.setString(1, user.getName());
               preparedStatement.setString(2, user.getSurname());
               preparedStatement.setString(3, user.getUsername());
               preparedStatement.setString(4, user.getPassword());
               preparedStatement.setString(5, user.getGender());

               preparedStatement.executeUpdate();
          }
          catch (SQLException e) {
               e.printStackTrace();
          }
          catch (ClassNotFoundException e) {
               e.printStackTrace();
          }
     }

     public  ResultSet getTasksByUser(int userId) {
          ResultSet resultTasks = null;

          String query = "SELECT * FROM " + Const.TASKS_TABLE + " WHERE "
                  +Const.USERS_ID + "=?";

          try {
               PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

               preparedStatement.setInt(1, userId);

               resultTasks = preparedStatement.executeQuery();

          } catch (SQLException e) {
               e.printStackTrace();
          } catch (ClassNotFoundException e) {
               e.printStackTrace();
          }

          return resultTasks;
     }



     public ResultSet getUser(User user) {

          ResultSet resultSet = null;

          if (!user.getUsername().equals("") || !user.getPassword().equals("")) {
               String query = "SELECT * FROM " + Const.USERS_TABLE + " WHERE "
                       +Const.USERS_USERNAME + "=?" + " AND " + Const.USERS_PASSWORD
                       + "=?";

               try {
                    PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

                    preparedStatement.setString(1, user.getUsername());
                    preparedStatement.setString(2, user.getPassword());

                    resultSet = preparedStatement.executeQuery();

               } catch (SQLException e) {
                    e.printStackTrace();
               } catch (ClassNotFoundException e) {
                    e.printStackTrace();
               }
          }
          else {

          }
          return resultSet;
     }

     public int getAllTasks(int userId) throws SQLException, ClassNotFoundException {

          String query = "SELECT COUNT(*) FROM " + Const.TASKS_TABLE + " WHERE " + Const.USERS_ID + "=?";


               PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
               preparedStatement.setInt(1, userId);

               ResultSet resultSet = preparedStatement.executeQuery();

               while (resultSet.next()) {
                    return resultSet.getInt(1);
               }
               return resultSet.getInt(1);
     }

     public void insertTask(Tasks tasks) {
          String insert = "INSERT INTO " + Const.TASKS_TABLE
                  + "(" + Const.USERS_ID
                  + "," + Const.TASKS_TASK
                  + "," + Const.TASKS_DESCRIPTION
                  + "," + Const.TASKS_DATE
                  + "," + Const.TASKS_STATUS
                  + ")" + "VALUES(?,?,?,?,?)";

          try {
               PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

               preparedStatement.setInt(1, tasks.getUserId());
               preparedStatement.setString(2,tasks.getTask());
               preparedStatement.setString(3,tasks.getDescription());
               preparedStatement.setTimestamp(4, tasks.getDateCreated());
               preparedStatement.setString(5, tasks.getStatus());

               preparedStatement.executeUpdate();

          }
          catch (SQLException e){
               e.printStackTrace();
          }
          catch (ClassNotFoundException e) {
               e.printStackTrace();
          }


     }

     public void updateStatus(String taskStatus, int userId, int taskId) throws SQLException, ClassNotFoundException {
          String update = "UPDATE " + Const.TASKS_TABLE + " SET " + Const.TASKS_STATUS + "=?"
                  + " WHERE " + Const.TASKS_ID + "=?" + " AND " + Const.USERS_ID + "=?";

          PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
          preparedStatement.setString(1, taskStatus);
          preparedStatement.setInt(2, taskId);
          preparedStatement.setInt(3, userId);
          preparedStatement.execute();
          preparedStatement.close();
     }





}
