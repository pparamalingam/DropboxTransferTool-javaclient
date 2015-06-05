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
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.UUID;
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

        String uniqueID = UUID.randomUUID().toString();
        String dir = "tmp-" + uniqueID + "/";
        ArrayList<File> file = new ArrayList<File>();

        System.out.println("\nDropbox Photo Downloader \n");

        for (URL url : DbUrl){
            try {
                String baseName = FilenameUtils.getBaseName(url.toString());
                String extension = FilenameUtils.getExtension(url.toString());
                File f = new File(dir + baseName + "." + extension);

                FileUtils.copyURLToFile(url, f);
                file.add(f);
                System.out.println(" Dropbox Photo Downloaded: " + f.getName());
            }
            catch(Exception exception) {
                log.log(Level.SEVERE, exception.getMessage());
            }
        }

        System.out.println("\nGoogle Photo Uploader \n");

        createTokenResponse(AccessToken);
        PicasawebService service = new PicasawebService("DropboxTransferTool");
        HTTP_TRANSPORT =  new NetHttpTransport();
        JSON_FACTORY = new JacksonFactory();
        Credential credential = createCredentialWithAccessTokenOnly(HTTP_TRANSPORT, JSON_FACTORY, tokenResponse_);
        service.setOAuth2Credentials(credential);

        for (File photo : file){
            PhotoEntry myPhoto = new PhotoEntry();
            myPhoto.setTitle(new PlainTextConstruct(photo.getName()));
            myPhoto.setClient("DropboxTransferTool");

            try {
                MediaFileSource myMedia = new MediaFileSource(photo, "image/jpg");
                myPhoto.setMediaSource(myMedia);
            }
            catch (Exception exception){
                log.log(Level.SEVERE, exception.getMessage());
            }
            URL albumPostUrl = new URL("https://picasaweb.google.com/data/feed/api/user/default");
            service.insert(albumPostUrl, myPhoto);
            System.out.println(" Goolge Photo Uploaded: " + photo.getName());
            credential.refreshToken();
        }

        System.out.println("\nPurging Downloaded Assets \n");
        FileUtils.deleteDirectory(new File (dir));
        System.out.println("\nSuccessful Transfer. Thank You. \n");


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
