package com.revature.versusapp.services.ersatz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.versusapp.models.rest.Credentials;
import com.revature.versusapp.models.rest.Login;
import com.revature.versusapp.utils.ApiKeyUtil;
import com.revature.versusapp.utils.ConnectionUtil;

public class ErsatzUserService {
    private ConnectionUtil connUtil;
    
    public ErsatzUserService() {
        connUtil = ConnectionUtil.getConnectionUtil();
    }
    
    boolean hasUser(String username ) {
        int existingCount = 0;
        
        try (Connection connection = connUtil.getConnection()) {

            String query = "select count(*) as total from person where username=?;";
            
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, username);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                existingCount = rs.getInt("total");
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
        
        return (existingCount == 1);
    }
    
    public int getIdForApiKey(String apiKey) {
        int userId = -1;
        
        try (Connection connection = connUtil.getConnection()) {

            String query = "select id from apikey where apikey=?;";
            
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, apiKey);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                userId = rs.getInt("id");
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
        
        return userId;
    }
    
    public String setApiKey(int userId) {
        
        String apiKey = ApiKeyUtil.generateApiKey();
        
        try (Connection connection = connUtil.getConnection()) {

            String query = "delete from apikey where id = ?;";
            
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            pstmt.close();
            
            String query2 = "insert into apikey(id,apikey) values (?,?);";
            
            PreparedStatement pstmt2 = connection.prepareStatement(query2);
            pstmt2.setInt(1, userId);
            pstmt2.setString(2, apiKey);
            pstmt2.executeUpdate();
            pstmt2.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            apiKey = "";
        }
        
        return apiKey;
    }
    
    
    public Credentials tryToLogin(Login login) {
        Credentials credentials = null;
        
        if ( !hasUser(login.getUsername()) ) {
            return credentials;
        }
        
        try (Connection connection = connUtil.getConnection()) {

            String query = "select id,passwrd from person where username=?;";
            
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, login.getUsername());
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                
                String password = rs.getString("passwrd");
                int id = rs.getInt("id");
                
                if ( password.equals(login.getPassword())) {
                    String apiKey = setApiKey(id);
                    credentials = new Credentials();
                    credentials.setVersusApiKey(apiKey);
                }
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
        
        return credentials;
    }
    

}
