package com.pm;

import com.dropbox.core.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Preshoth on 2015-06-03.
 */
public class DropboxDirectoryList {

    private static final Logger log = Logger.getLogger( DropboxDirectoryList.class.getName() );
    private ArrayList<String> directory_;

    public DropboxDirectoryList(DropboxAuthenticator AUTH){

        directory_ = new ArrayList<String>();
        int count = 0;
        try{
            DbxEntry.WithChildren listing = AUTH.getClient_().getMetadataWithChildren("/Camera Uploads");
            for (DbxEntry child: listing.children){
                System.out.println("	" + child.path );
                directory_.add(child.path);
                count++;
            }
            System.out.println(count );
        }
        catch (DbxException exception){
            log.log(Level.SEVERE, exception.getMessage());
        }


    }

}
