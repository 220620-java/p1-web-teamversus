package com.revature.versusapp.services.ersatz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.versusapp.models.rest.InventoryItem;
import com.revature.versusapp.models.rest.NewAlbum;
import com.revature.versusapp.utils.ConnectionUtil;

public class ErsatzInventoryService {
    private ConnectionUtil connUtil;
    
    public ErsatzInventoryService() {
        connUtil = ConnectionUtil.getConnectionUtil();
    }
    
    int getArtistId(String stageName) {
        int artistId = -1;
        
        try (Connection connection = connUtil.getConnection()) {

            String query = "select id from artist where stage_name=?;";
            
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, stageName);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                artistId = rs.getInt("id");
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
        
        return artistId;
    }
    
    
    
    int getAlbumId(String stageName, String title) {
        int albumId = -1;
        
        int artistId = getArtistId(stageName);
        
        if ( artistId == -1 ) {
            return albumId;
        }
        
        try (Connection connection = connUtil.getConnection()) {

            String query = "select id from album where artist_id = ? and title = ?;";
            
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, artistId);
            pstmt.setString(2, title);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                albumId = rs.getInt("id");
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
        
        return albumId;
    }
    
    public boolean deleteAlbumFromInventory(int userId, NewAlbum album) {
        boolean albumDeleted = false;
        
//        int albumId = getAlbumId(album.getArtist(), album.getTitle());
//        
//        if ( albumId == -1 ) {
//            return false;
//        }
//        
//        try (Connection connection = connUtil.getConnection()) {
//
//            String query = "delete from inventory where person_id=? and album_id =?;";
//            
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setInt(1, userId);
//            pstmt.setInt(2, albumId);
//            
//            pstmt.executeUpdate();
//            
//            pstmt.close();
//            albumDeleted = true;
//        } catch (SQLException e) {
//            System.err.println(e.getClass().getName()+": "+e.getMessage());
//        }
        
        return albumDeleted;
    }
    
    public boolean addAblumToInventory(int userId, NewAlbum album) {
        boolean albumAdded = false;
        
//        int albumId = getAlbumId(album.getArtist(), album.getTitle());
//        
//        if ( albumId == -1 ) {
//            return false;
//        }
//        
//        try (Connection connection = connUtil.getConnection()) {
//
//            String query = "insert into inventory(person_id,album_id) values (?,?);";
//            
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setInt(1, userId);
//            pstmt.setInt(2, albumId);
//            
//            pstmt.executeUpdate();
//            
//            pstmt.close();
//            albumAdded = true;
//        } catch (SQLException e) {
//            System.err.println(e.getClass().getName()+": "+e.getMessage());
//        }
        
        return albumAdded;
    }
    
    
    public List<InventoryItem> getInventory(){
        List<InventoryItem> inventory = new ArrayList<InventoryItem>();
        
//        try (Connection connection = connUtil.getConnection()) {
//
//            Statement selectStmt = connection.createStatement();
//            ResultSet rs = selectStmt
//                .executeQuery("select username, title, stage_name \r\n"
//                        + "from inventory \r\n"
//                        + "inner join person on inventory.person_id = person.id \r\n"
//                        + "inner join album on inventory.album_id = album.id \r\n"
//                        + "inner join artist on album.artist_id = artist.id \r\n"
//                        + "order by username,stage_name,title;");
//            
//            String username = null;
//            String title = null;
//            String stageName = null;
//            
//            InventoryItem item = new InventoryItem();
//            boolean hasItems = false;
//            
//            while(rs.next())
//            {
//                username = rs.getString("username");
//                title = rs.getString("title");
//                stageName = rs.getString("stage_name");
//                
//                NewAlbum album = new NewAlbum(stageName,title);
////                album.setArtist(stageName);
////                album.setTitle(title);
//                
//                // For the first time through the loop, item.getUsername()
//                // will be null, so set it to username here.
//                if ( item.getUsername() == null ) {
//                    item.setUsername(username);
//                }
//                
//                // If we've reached a new username, put the current item
//                // in the inventory and start a new one.
//                if (  !item.getUsername().equals(username) ) {
//                    inventory.add(item);
//                    item = new InventoryItem();
//                    item.setUsername(username);
//                }
//                
//                item.getAlbums().add(album);
//            }
//            
//            //Add the last item that was worked on to inventory.
//            inventory.add(item);
//            
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        
        return inventory;
    }
    
}
