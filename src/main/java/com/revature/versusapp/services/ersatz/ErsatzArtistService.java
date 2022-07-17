package com.revature.versusapp.services.ersatz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.versusapp.models.Artist;
import com.revature.versusapp.utils.ConnectionUtil;

public class ErsatzArtistService {
    private ConnectionUtil connUtil;
    
    public ErsatzArtistService() {
        connUtil = ConnectionUtil.getConnectionUtil();
    }
    
    public List<Artist> getArtists() {
        List<Artist> artists = new ArrayList<>();
        
        try (Connection connection = connUtil.getConnection()) {

            Statement selectStmt = connection.createStatement();
            ResultSet rs = selectStmt
                .executeQuery("select id, stage_name from artist;");
            
            while(rs.next())
            {
               int id = rs.getInt("id");
               String stageName = rs.getString("stage_name");
               
               Artist artist = new Artist(id,stageName);
               artists.add(artist);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return artists;
    }
}
