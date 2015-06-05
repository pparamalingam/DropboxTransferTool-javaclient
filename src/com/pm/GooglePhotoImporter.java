package com.pm;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
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
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Preshoth on 2015-06-03.
 */
public class GooglePhotoImporter {

    private static final Logger log = Logger.getLogger( GooglePhotoImporter.class.getName() );

    private TokenResponse tokenResponse_;
    private String userId_;
    private HttpTransport HTTP_TRANSPORT;
    private JsonFactory JSON_FACTORY;

    public GooglePhotoImporter(String AccessToken, ArrayList<URL> DbUrl) throws MalformedURLException, IOException, ServiceException, GeneralSecurityException{

        ArrayList<File> file = new ArrayList<File>();
        int count = 0;
        for (URL url : DbUrl){
            try {
                File f = new File("test.jpg");
                FileUtils.copyURLToFile(url, f);
                file.add(f);
                //f.delete();
            }
            catch(Exception exception) {
                log.log(Level.SEVERE, exception.getMessage());
            }
        }
        createTokenResponse(AccessToken);

        PicasawebService service = new PicasawebService("DropboxTransferTool");

        HTTP_TRANSPORT =  new NetHttpTransport();
        JSON_FACTORY = new JacksonFactory();
        Credential credential = createCredentialWithAccessTokenOnly(HTTP_TRANSPORT, JSON_FACTORY, tokenResponse_);
        service.setOAuth2Credentials(credential);

        PhotoEntry myPhoto = new PhotoEntry();
        myPhoto.setTitle(new PlainTextConstruct(file.get(0).getName()));
        myPhoto.setClient("DropboxTransferTool");

        try {
            MediaFileSource myMedia = new MediaFileSource(/*file.get(0)*/ new File("a.jpg"), "image/jpg");
            myPhoto.setMediaSource(myMedia);
        }
        catch (Exception exception){
            log.log(Level.SEVERE, exception.getMessage());
        }
        URL albumPostUrl = new URL("https://picasaweb.google.com/data/feed/api/user/default");
        PhotoEntry returnedPhoto = service.insert(albumPostUrl, myPhoto);
        credential.refreshToken();


    }

    public void createTokenResponse (String AccessToken){
        tokenResponse_ = new GoogleTokenResponse();
        tokenResponse_.setAccessToken(AccessToken);
        tokenResponse_.setTokenType("Bearer");
        tokenResponse_.setScope("https://picasaweb.google.com/data/");
    }

    public static Credential createCredentialWithAccessTokenOnly(
            HttpTransport transport, JsonFactory jsonFactory, TokenResponse tokenResponse) {
        return new Credential(BearerToken.authorizationHeaderAccessMethod()).setFromTokenResponse(
                tokenResponse);
    }

}
