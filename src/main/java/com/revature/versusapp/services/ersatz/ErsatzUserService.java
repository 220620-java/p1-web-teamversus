package com.revature.versusapp.services.ersatz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.versusapp.models.Artist;
import com.revature.versusapp.models.rest.Credentials;
import com.revature.versusapp.models.rest.Login;
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
    
    
    
    public Credentials tryToLogin(Login login) {
        Credentials credentials = new Credentials(-1,-1);
        
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
                
                if ( password.equals(login.getPassword())) {
                    credentials.setId(rs.getInt("id"));
                    credentials.setToken(3);
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
