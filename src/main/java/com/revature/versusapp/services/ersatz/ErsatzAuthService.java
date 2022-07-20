package com.revature.versusapp.services.ersatz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.versusapp.utils.ConnectionUtil;

public class ErsatzAuthService {

    private ConnectionUtil connUtil;
    
    public ErsatzAuthService() {
        connUtil = ConnectionUtil.getConnectionUtil();
    }
    
    /**
     * Add a new row to the API table.  Ideally delete any entries for
     * username that are already there, but that is optional.
     * 
     * @param username
     * @param key
     */
    public void addToApiKeyTable(String username, String key) {
        
        try (Connection connection = connUtil.getConnection()) {

            String query = "insert into apikey(apikey_id,username) values (?,?);";
            
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, key );
            pstmt.setString(2, username);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
    }


    /**
     * Check if the key paramether matches the entry in the apikey table
     * for username.
     * 
     * @param username
     * @param key
     * @return true if the pair is in the apikey table and false otherwise.
     */
    public boolean keysMatch(String username, String key) {
        boolean match = false;
        
        try (Connection connection = connUtil.getConnection()) {

            String query = "select apikey_id from apikey where username=?;";
            
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, username);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                if ( rs.getString("apikey_id").equals(key) ) {
                    match = true;
                    break;
                }
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
        
        return match;
    }
}
