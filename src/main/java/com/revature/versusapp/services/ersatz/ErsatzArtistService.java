package com.revature.versusapp.services.ersatz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.versusapp.models.Artist;
import com.revature.versusapp.models.rest.NewArtist;
import com.revature.versusapp.utils.ConnectionUtil;

public class ErsatzArtistService {
//    private ConnectionUtil connUtil;
//    
//    public ErsatzArtistService() {
//        connUtil = ConnectionUtil.getConnectionUtil();
//    }
//    
//    boolean hasArtist(String stageName ) {
//        int existingCount = 0;
//        
//        try (Connection connection = connUtil.getConnection()) {
//
//            String query = "select count(*) as total from artist where stage_name=?;";
//            
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, stageName);
//            
//            ResultSet rs = pstmt.executeQuery();
//            
//            if (rs.next()) {
//                existingCount = rs.getInt("total");
//            }
//            
//            rs.close();
//            pstmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.err.println(e.getClass().getName()+": "+e.getMessage());
//        }
//        
//        return (existingCount == 1);
//    }
//    
//    
//    public boolean addArtist(NewArtist artist) {
//        boolean artistAdded = false;
//        
//        if ( hasArtist(artist.getStageName()) ) {
//            return false;
//        }
//        
//        try (Connection connection = connUtil.getConnection()) {
//
//            String query = "insert into artist(stage_name) values (?);";
//            
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, artist.getStageName());
//            
//            pstmt.executeUpdate();
//            
//            pstmt.close();
//            artistAdded = true;
//        } catch (SQLException e) {
//            System.err.println(e.getClass().getName()+": "+e.getMessage());
//        }
//        
//        return artistAdded;
//    }
//    
//    public List<Artist> getArtists() {
//        List<Artist> artists = new ArrayList<>();
//        
//        try (Connection connection = connUtil.getConnection()) {
//
//            Statement selectStmt = connection.createStatement();
//            ResultSet rs = selectStmt
//                .executeQuery("select id, stage_name from artist;");
//            
//            while(rs.next())
//            {
//               int id = rs.getInt("id");
//               String stageName = rs.getString("stage_name");
//               
//               Artist artist = new Artist(id,stageName);
//               artists.add(artist);
//            }
//            
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        
//        return artists;
//    }
}
