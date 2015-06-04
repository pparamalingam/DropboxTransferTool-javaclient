package com.pm;

import com.google.api.client.auth.oauth.AbstractOAuthGetToken;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import org.apache.commons.io.FileUtils;

import com.google.gdata.client.photos.*;
import com.google.gdata.data.*;
import com.google.gdata.data.media.*;
import com.google.gdata.data.photos.*;
import com.google.api.client.auth.*;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Preshoth on 2015-06-03.
 */
public class GooglePhotoImporter {

    private static final Logger log = Logger.getLogger( GooglePhotoImporter.class.getName() );

    private TokenResponse tokenResponse_;

    public GooglePhotoImporter(String AccessToken, ArrayList<URL> DbUrl){

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
    }

    public void createTokenResponse (String AccessToken){
        tokenResponse_ = new TokenResponse();
        tokenResponse_.setAccessToken(AccessToken);
    }

    public static GoogleCredential createCredentialWithAccessTokenOnly(
            HttpTransport transport, JsonFactory jsonFactory, TokenResponse tokenResponse) {
        return new GoogleCredential().setFromTokenResponse(tokenResponse);
    }

}
