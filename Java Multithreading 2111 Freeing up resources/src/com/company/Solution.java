package com.company;

/*

2111 Freeing up resources
Implement the finalize method, having previously thought over what exactly should be in it.
Refactor the getUsers method according to java7 try-with-resources.

Requirements:
1. The finalize method in the Solution class must contain a call to super.finalize ().
2. The finalize method in the Solution class should complete correctly if the value of the connection field is null.
3. The finalize method in the Solution class should close the current connection if the value of the connection field is not null.
4. The getUsers method should correctly use try-with-resources.


 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;



public class Solution {
    private Connection connection;

    public Solution(Connection connection) {
        this.connection = connection;
    }

    public List<User> getUsers() {
        String query = "select ID, DISPLAYED_NAME, LEVEL, LESSON from USER";

        List<User> result = new LinkedList();

        try {
            try (
                    Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
            ) {
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String name = rs.getString("DISPLAYED_NAME");
                    int level = rs.getInt("LEVEL");
                    int lesson = rs.getInt("LESSON");

                    result.add(new User(id, name, level, lesson));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = null;
        }

        return result;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if(connection !=null)
            connection.close();
    }

    public static class User {
        private int id;
        private String name;
        private int level;
        private int lesson;

        public User(int id, String name, int level, int lesson) {
            this.id = id;
            this.name = name;
            this.level = level;
            this.lesson = lesson;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", level=" + level +
                    ", lesson=" + lesson +
                    '}';
        }
    }

    public static void main(String[] args) {

    }
}


