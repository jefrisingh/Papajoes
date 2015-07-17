package www.thetunagroup.com.papajoes.util;

import android.webkit.URLUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by DELL-PC on 7/6/2015.
 */
public class Interaction {

    public static String postGetData(String url,MultipartEntity postData)
    {
        if(!URLUtil.isValidUrl(url))
        {
            return "NotValidUrl";
        }
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(postData);
            HttpResponse response = httpclient.execute(httppost);
            return  EntityUtils.toString(response.getEntity());
        }catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}
