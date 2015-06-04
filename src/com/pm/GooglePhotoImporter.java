package com.pm;

import com.google.api.client.auth.oauth.AbstractOAuthGetToken;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.media.MediaFileSource;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.ServiceException;
import org.apache.commons.io.FileUtils;

import com.google.gdata.client.photos.PicasawebService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Preshoth on 2015-06-03.
 */
public class GooglePhotoImporter {

    private static final Logger log = Logger.getLogger( GooglePhotoImporter.class.getName() );

    private TokenResponse tokenResponse_;

    public GooglePhotoImporter(String AccessToken, ArrayList<URL> DbUrl) throws MalformedURLException, IOException, ServiceException{

        File f = new File("tmp");
        ArrayList<File> file = new ArrayList<File>();
        for (URL url : DbUrl){
            try {
                FileUtils.copyURLToFile(url, f);
                file.add(f);
                f.delete();
            }
            catch(Exception exception) {
                log.log(Level.SEVERE, exception.getMessage());
            }
        }
        createTokenResponse(AccessToken);

        //This needs to replaced with a contextual URL
        URL albumPostUrl = new URL("https://picasaweb.google.com/data/feed/api/user/username/albumid/albumid");

        PicasawebService service = new PicasawebService("DropboxTransferTool");
        service.setOAuth2Credentials(createCredentialWithAccessTokenOnly(tokenResponse_));

        PhotoEntry myPhoto = new PhotoEntry();
        myPhoto.setTitle(new PlainTextConstruct(file.get(1).getName()));
        myPhoto.setClient("DropboxTransferTool");

        try {
            MediaFileSource myMedia = new MediaFileSource(file.get(1), Files.probeContentType(file.get(1).toPath()));
            myPhoto.setMediaSource(myMedia);
        }
        catch (Exception exception){
            log.log(Level.SEVERE, exception.getMessage());
        }

        PhotoEntry returnedPhoto = service.insert(albumPostUrl, myPhoto);


    }

    public void createTokenResponse (String AccessToken){
        tokenResponse_ = new TokenResponse();
        tokenResponse_.setAccessToken(AccessToken);
    }

    public static GoogleCredential createCredentialWithAccessTokenOnly(TokenResponse tokenResponse) {
        return new GoogleCredential().setFromTokenResponse(tokenResponse);
    }

}
