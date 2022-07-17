package com.revature.versusapp.services.ersatz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.versusapp.models.Album;
import com.revature.versusapp.models.rest.NewAlbum;
import com.revature.versusapp.utils.ConnectionUtil;

public class ErsatzAlbumService {
    private ConnectionUtil connUtil;
    
    public ErsatzAlbumService() {
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
    
    public boolean addAlbum(NewAlbum album) {
        boolean albumAdded = false;
        
        int artistId = getArtistId(album.getArtist());
        
        if ( artistId == -1 ) {
            return false;
        }
        
        try (Connection connection = connUtil.getConnection()) {

            String query = "insert into album (title, artist_id) values (?, ?);";
            
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, album.getTitle());
            pstmt.setInt(2, artistId);
            
            pstmt.executeUpdate();
            
            pstmt.close();
            albumAdded = true;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
        
        return albumAdded;
    }
    
    
    public List<Album> getAlbums() {
        List<Album> albums = new ArrayList<>();
        
        try (Connection connection = connUtil.getConnection()) {

            Statement selectStmt = connection.createStatement();
            ResultSet rs = selectStmt
                .executeQuery("select id, title,artist_id from album;");
            
            while(rs.next())
            {
               int id = rs.getInt("id");
               String title = rs.getString("title");
               int artistId = rs.getInt("artist_id");
               
               Album album = new Album(id,title,artistId);
               albums.add(album);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return albums;
    }
}
