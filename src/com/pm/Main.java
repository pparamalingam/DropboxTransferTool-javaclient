//Authors :Matthew Francis, Presh
package com.pm;

import com.dropbox.core.DbxException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, DbxException {

	DropboxAuthenticator auth = new DropboxAuthenticator("YOUR_KEY","YOUR_SECRET");

    }
}
