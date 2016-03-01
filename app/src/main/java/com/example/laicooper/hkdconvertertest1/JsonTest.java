package com.example.laicooper.hkdconvertertest1;

import android.app.ListActivity;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JsonTest extends ListActivity {
    private Bundle bunData=new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        new HttpGetTask().execute();
    }



    private class HttpGetTask extends AsyncTask<Void, Void, List<String>> {

        // Get the HKD Exchange Rate
        private static final String URL_ADDRESS =
                "http://api.fixer.io/latest?base=HKD";

        AndroidHttpClient mClient = AndroidHttpClient.newInstance("");

        @Override
        protected List<String> doInBackground(Void... params) {
            HttpGet request = new HttpGet(URL_ADDRESS);
            JSONResponseHandler responseHandler = new JSONResponseHandler();
            try {
                return mClient.execute(request, responseHandler);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(List<String> result) {
            if (null != mClient)
                mClient.close();
            Intent ii=new Intent();
            ii.putExtras(bunData);
            setResult(RESULT_OK, ii);
            setListAdapter(new ArrayAdapter<String>(
                    JsonTest.this,
                    R.layout.list_item, result));


        }
    }
    private class JSONResponseHandler implements ResponseHandler<List<String>> {

        @Override
        public List<String> handleResponse(HttpResponse response)
                throws ClientProtocolException, IOException {
            List<String> result = new ArrayList<String>();
            String JSONResponse = new BasicResponseHandler()
                    .handleResponse(response);

            try {
                // Get top-level JSON Object - a JSONObject
                JSONObject responseObject = (JSONObject) new JSONTokener(
                        JSONResponse).nextValue();

                // Get the base of the Exchange Rate
                String  baseString = responseObject.getString("base");
                Log.i("EXCHANGE RATE", "Base : " + baseString);
                result.add("Exchange Rates for " + baseString);

                // Get the date of the Exchange Rate
                String  dateString = responseObject.getString("date");
                Log.i("EXCHANGE RATE", "DATE : " + dateString);
                result.add("Date : " + dateString);

                // Get rates Object and then extract the selected rates
                JSONObject ratesObject = responseObject.getJSONObject("rates");

                Double cnyRate = ratesObject.getDouble("CNY");
                Double usdRate = ratesObject.getDouble("USD");
                Double gbpRate = ratesObject.getDouble("GBP");
                Double eurRate = ratesObject.getDouble("EUR");
                Double jpyRate = ratesObject.getDouble("JPY");
                bunData.putDouble("CNY",cnyRate);
                bunData.putDouble("USD",usdRate);
                bunData.putDouble("GBP",gbpRate);
                bunData.putDouble("EUR",eurRate);
                bunData.putDouble("JPY",jpyRate);


                result.add("CNY" + " : " + cnyRate.toString());
                result.add("USD" + " : " + usdRate.toString());
                result.add("EUR" + " : " + eurRate.toString());
                result.add("GBP" + " : " + gbpRate.toString());
                result.add("JPY" + " : " + jpyRate.toString());

                Log.i("EXCHANGE RATE", "CNY: " + cnyRate);
                Log.i("EXCHANGE RATE", "USD: " + usdRate);
                Log.i("EXCHANGE RATE", "GBP: " + gbpRate);
                Log.i("EXCHANGE RATE", "EUR: " + eurRate);
                Log.i("EXCHANGE RATE", "JPY: " + jpyRate);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

}
