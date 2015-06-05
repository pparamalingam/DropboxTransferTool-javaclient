//Authors :Matthew Francis, Presh
package com.pm;

import com.dropbox.core.DbxException;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, DbxException, ServiceException, GeneralSecurityException, Exception {

        ArrayList<URL> url = new ArrayList<URL>();
        URL x = new URL("https://dl.dropboxusercontent.com/1/view/qanugni706m5xlj/Photos/Sample%20Album/2013-09-22-14.41.50-1.jpg");
        URL y = new URL("https://dl.dropboxusercontent.com/1/view/1ihoh7jed2f7qcp/Photos/Sample%20Album/Pensive%20Parakeet.jpg");
        URL z = new URL("https://dl.dropboxusercontent.com/1/view/h6ieah7r2k07fdi/Photos/Sample%20Album/Sony-Xperia-Z3-Compact-Review-074-manual-mode-20MP-samples.JPG.jpg");
        url.add(x);
        url.add(y);
        url.add(z);
        DbJsonConsumer urls = new DbJsonConsumer("https://api.myjson.com/bins/1mz8s");
        GooglePhotoImporter test = new GooglePhotoImporter("ya29.iQGVtMcvXRxpT8VOEERW7loeBVgES4B2BZQwZUOGO4RN-CmLv9dqnmljKP1ynX1NdlfwwkM0TP3dCw", urls.getDbUrls_()); //test
    }
}
