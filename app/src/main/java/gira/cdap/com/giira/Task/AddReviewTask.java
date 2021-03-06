package gira.cdap.com.giira.Task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import gira.cdap.com.giira.Service.JSONParser;
import gira.cdap.com.giira.Service.ServiceHandler;
import gira.cdap.com.giira.one_place;

/**
 * Created by Perceptor on 6/13/2016.
 */
public class AddReviewTask extends AsyncTask<String,Void,String> {

    ServiceHandler serviceHandler ;
    InputStream inputStream;
    JSONParser parsing;
    String comment,placeid;
    one_place one_place;

    Context context;


    public AddReviewTask(Context context, String comment, String placeid, one_place one_place)
    {
        this.context=context;
        this.comment=comment;
        this.placeid=placeid;
        this.one_place=one_place;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = null;

        List<NameValuePair> value = new ArrayList<NameValuePair>();
        value.add(new BasicNameValuePair("comment", comment));
        value.add(new BasicNameValuePair("place_id", placeid));
        android.util.Log.d("Task","AddReviewTask");
        serviceHandler = new ServiceHandler();
        inputStream = serviceHandler.makeServiceCall(
                serverURL.local_host_url+"giira/index.php/places/addreviews",2,
                value);
        parsing = new JSONParser();

        try {
            JSONObject json = parsing.getJSONFromResponse(inputStream);
            result = String.valueOf(json.getBoolean("response"));

//            if (json.getString("response").matches("true")) {
//                result = "Successful Added";
//            } else {
//                result = "Failed";
//            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if(result.matches("true"))

        {
            if(one_place!=null)
            {
                Toast.makeText(context, "successfully added", Toast.LENGTH_SHORT).show();
                //insertPlace.showLogin();
            }

//			if(availableSeats!=null)
//			{
//				availableSeats.setPassengerUserNameU(uname);
//				availableSeats.seatTaskUpdateCall();
//			}
        }
        else
        {
//			if(availableSeats!=null)
//			{
//				Toast.makeText(availableSeats.getApplicationContext(), "Booking failed", Toast.LENGTH_SHORT).show();
//			}
            if(one_place!=null)
            {
                Toast.makeText(context, "Insertion Failed", Toast.LENGTH_SHORT).show();

            }

        }


    }
}
