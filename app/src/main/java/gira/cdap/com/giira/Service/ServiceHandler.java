package gira.cdap.com.giira.Service;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 *
 *
 */
public class ServiceHandler {	// to connect the local server

    String response = null;
    InputStream is=null;
    public final static int GET = 1;
    public final static int POST = 2;

    public ServiceHandler() {

    }

//    public InputStream makeServiceCall1(String url, int method) {
//        return this.makeServiceCall1(url, method);
//    }

    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     * */
    public InputStream makeServiceCall(String url, int method,
                                       List<NameValuePair> params) {
        try {
            // Http client
            HttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                    Log.d("HTTP", EntityUtils.toString(new UrlEncodedFormEntity(params)));
                    Log.d("HTTP POST", httpPost.toString());
                }

                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) {
                // appending params to url
                if (params != null) {
                    String paramString = URLEncodedUtils.format(params, "utf-8");
                    url += "&" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);
                Log.d("HTTP", EntityUtils.toString(new UrlEncodedFormEntity(params)));
                Log.d("HTTP POST", httpGet.toString());
                httpResponse = httpClient.execute(httpGet);
                Log.d("HTTP POST", httpGet.toString());
            }
            httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }
}
