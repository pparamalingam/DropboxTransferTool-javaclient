package com.pm;

import org.apache.commons.io.FileUtils;

import com.google.gdata.client.photos.*;
import com.google.gdata.data.*;
import com.google.gdata.data.media.*;
import com.google.gdata.data.photos.*;

/**
 * Created by Preshoth on 2015-06-03.
 */
public class GooglePhotoImporter {

    String nextUrl = "http://www.example.com/RetrieveToken.jsp";
    String scope = "http://www.google.com/calendar/feeds/";
    boolean secure = false;  // set secure=true to request secure AuthSub tokens
    boolean session = true;
    //String authSubUrl = AuthSubUtil.getRequestUrl(nextUrl, scope, secure, session);

}
