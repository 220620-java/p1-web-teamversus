package com.revature.versusapp.services.ersatz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.versusapp.models.Artist;
import com.revature.versusapp.models.rest.InventoryItem;
import com.revature.versusapp.models.rest.NewAlbum;
import com.revature.versusapp.utils.ConnectionUtil;

public class ErsatzInventoryService {
    private ConnectionUtil connUtil;
    
    public ErsatzInventoryService() {
        connUtil = ConnectionUtil.getConnectionUtil();
    }
    
    public List<InventoryItem> getInventory(){
        List<InventoryItem> inventory = new ArrayList<InventoryItem>();
        
        try (Connection connection = connUtil.getConnection()) {

            Statement selectStmt = connection.createStatement();
            ResultSet rs = selectStmt
                .executeQuery("select username, title, stage_name \r\n"
                        + "from inventory \r\n"
                        + "inner join person on inventory.person_id = person.id \r\n"
                        + "inner join album on inventory.album_id = album.id \r\n"
                        + "inner join artist on album.artist_id = artist.id \r\n"
                        + "order by username,stage_name,title;");
            
            String username = null;
            String title = null;
            String stageName = null;
            
            InventoryItem item = new InventoryItem();
            boolean hasItems = false;
            
            while(rs.next())
            {
                username = rs.getString("username");
                title = rs.getString("title");
                stageName = rs.getString("stage_name");
                
                NewAlbum album = new NewAlbum();
                album.setArtist(stageName);
                album.setTitle(title);
                
                // For the first time through the loop, item.getUsername()
                // will be null, so set it to username here.
                if ( item.getUsername() == null ) {
                    item.setUsername(username);
                }
                
                // If we've reached a new username, put the current item
                // in the inventory and start a new one.
                if ( item.getUsername()!= null && item.getUsername().equals(username) ) {
                    inventory.add(item);
                    item = new InventoryItem();
                    item.setUsername(username);
                }
                
                item.getAlbums().add(album);
            }
            
            //Add the last item that was worked on to inventory.
            inventory.add(item);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return inventory;
    }
    
}
