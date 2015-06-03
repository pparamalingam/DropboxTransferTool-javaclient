package com.pm;

import com.dropbox.core.*;
import java.io.*;
import java.util.Locale;

/**
 * Created by Preshoth on 2015-06-03.
 * Dropbox authentication system using keys.
 */
public class DropboxAuthenticator {

    private DbxClient client_;

    public DropboxAuthenticator(final String APP_KEY, final String APP_SECRET) throws IOException, DbxException {

        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        DbxRequestConfig config = new DbxRequestConfig(
                "JavaTutorial/1.0", Locale.getDefault().toString());
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);

        String authorizeUrl = webAuth.start();
        System.out.println("1. Go to: " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first)");
        System.out.println("3. Copy the authorization code.");
        String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();

        DbxAuthFinish authFinish = webAuth.finish(code);
        String accessToken = authFinish.accessToken;

        client_ = new DbxClient(config, accessToken);
        System.out.println("Linked account: " + client_.getAccountInfo().displayName);
    }

    public DbxClient getClient() {
        return client_;
    }


}
