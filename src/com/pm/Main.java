//Authors :Matthew Francis, Presh
package com.pm;

import com.dropbox.core.DbxException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, DbxException {

	    DropboxAuthenticator auth = new DropboxAuthenticator("io6bkt0n9uq2opz","4lgybl1aclmjcq8");
        DropboxDirectoryList list = new DropboxDirectoryList(auth);

    }
}
