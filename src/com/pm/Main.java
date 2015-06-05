//Authors :Matthew Francis, Presh
package com.pm;

import com.dropbox.core.DbxException;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, DbxException, ServiceException, GeneralSecurityException {

        ArrayList<URL> url = new ArrayList<URL>();
        URL x = new URL("https://www.dropbox.com/s/7uki3b1sayel74q/Boston%20City%20Flow.jpg");
        url.add(x);
	    //DropboxAuthenticator auth = new DropboxAuthenticator("io6bkt0n9uq2opz","4lgybl1aclmjcq8");
        //DropboxDirectoryList list = new DropboxDirectoryList(auth);
        GooglePhotoImporter test = new GooglePhotoImporter("TOKEN", url);
    }
}
