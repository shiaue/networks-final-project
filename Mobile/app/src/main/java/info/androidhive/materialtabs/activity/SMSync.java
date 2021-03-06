package info.androidhive.materialtabs.activity;

import android.os.AsyncTask;
import android.telephony.SmsManager;
import android.util.Log;

import com.owlike.genson.Genson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;

/**
 * Created by e on 2/25/16.
 */
public class SMSync extends AsyncTask<List<AddressBookContact>, Void, Boolean> {
    // not allowed to make a network call over a UI thread
    // Forces you to use a concurrent task (Async)



    @Override
    protected Boolean doInBackground(List<AddressBookContact>... list) {
        String phoneNumber;
        String textMessage;
        try{
            String ipAddress = "10.67.134.134",
                    port = "8080";

            URL url = new URL("http://"+ ipAddress+":"+port+"/contacts");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            // TODO sending contacts
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());

            JSONArray jsonContact = new JSONArray();
            JSONObject jsonPhone = new JSONObject();
            JSONObject jsonName = new JSONObject();
            Map<String, Object> config = new HashMap<String, Object>();
            //if you need pretty printing
            config.put("javax.json.stream.JsonGenerator.prettyPrinting", true);
            JsonBuilderFactory factory = Json.createBuilderFactory(config);

            JsonArrayBuilder jarr = Json.createArrayBuilder();



            int i = 1;

            for (AddressBookContact contact : list[0]) {
//                Log.w("Sending Contact", "AddressBookContact #" + i++ + ": " + contact.print());
                String mPhone = contact.getPhone();
                String mName = contact.getName();
//                int count = 0;
//                JSONObject name;
//                JSONObject phone;

//                if(mPhone.length() == 10 && mPhone.substring(0,3).equals("615"))    {
                if(mPhone.length() == 10)    {
//                    name = jsonName.put("name", mName);
//                    phone = jsonPhone.put("phone", mPhone);
                    jarr.add(Json.createObjectBuilder()
                            .add("name", mName)
                            .add("number", mPhone));

//                    jsonContact.put(count, name);
//                    jsonContact.put(count,phone);
//                    count++;

                    Log.w("Sending Contacts", "#" + i++ + ": "+mName + ", "+ mPhone +";");
                }
            }
            JsonArray arr = jarr.build();
            Log.w("JSON Contacts", arr.toString());

            wr.write(arr.toString());
            wr.flush();
            wr.close();

            //display what returns the POST request

//            StringBuilder sb = new StringBuilder();
//            int HttpResult = connection.getResponseCode();
//            if (HttpResult == HttpURLConnection.HTTP_OK) {
//                BufferedReader br = new BufferedReader(
//                        new InputStreamReader(connection.getInputStream(), "utf-8"));
//                String line = null;
//                while ((line = br.readLine()) != null) {
//                    sb.append(line + "\n");
//                }
//                br.close();
//                System.out.println("" + sb.toString());
//            } else {
//                System.out.println(connection.getResponseMessage());
//            }


            // TODO receiving number and message in separate Webtask, different url connection
//            try{
//                String ipAddress = "10.67.123.120",
//                        port = "8080";
//
//                URL url = new URL("http://"+ ipAddress+":"+port+"/contacts");
//                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            // Use POST if you are sending data from phone to webserver
            connection.setRequestMethod("GET"); // This doesn't seem to change the connection type
            int responseCode = connection.getResponseCode(); //200, 400, 404 Code
            Log.w("Connection", "Response Code: " + responseCode + " Method: "+ connection.getRequestMethod());

            InputStream input = connection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            String message;
            StringBuffer response = new StringBuffer();

            // create a public class and
            Genson genson = new Genson();
//            AddressBookContact contact = genson.deserialize("{some json in here}", AddressBookContact.class);
//            String json = genson.serialize(contact);
            // This allows going back from JSON to java class

            while ((message = in.readLine()) != null) {
                response.append(message);
            }

            in.close();
//            AddressBookContact messageRecipient = genson.deserialize(, AddressBookContact.class);

            String serialResponse = genson.serialize(response);

            Log.w("Response (Unserialized)", response.toString());
            Log.w("Response (Serialized)", serialResponse);
            Log.w("Response (phone)", response.substring(10,20));
            Log.w("Response (message)", response.substring(33,response.length()-2));
            phoneNumber = response.substring(10,20);
            textMessage =  response.substring(33, response.length()-2);

        } catch (Exception e) {
            Log.w("E", "Exception: " + e);
            phoneNumber = "8479222186";
            textMessage = "ERROR: message failed to load from node.js server";

        }

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, textMessage, null, null);
            Log.w("Response", "Web: SMS sent to "+ phoneNumber+"\nMessage: "+ textMessage);


        } catch (Exception e) {

            Log.w("Response", "Web: SMS failed to send. (number:" + phoneNumber + " message: " + textMessage + ")");
            e.printStackTrace();
        }


        return null;
    }

//    @Override
//    protected void onPostExecute(Void aVoid) {
//        if(aVoid.equals("Exception Caught"))  {
//                        Toast.makeText(getApplicationContext(),
//                    "Web: Sending SMS failed.",
//                    Toast.LENGTH_LONG).show();
//        }
//        else    {
//                        Toast.makeText(getActivity(), "Web: SMS sent.",
//                    Toast.LENGTH_LONG).show();
//        }
//
//    }

//    public static JSONObject mergeJSONObjects(JSONObject json1, JSONObject json2) {
//        JSONObject mergedJSON = new JSONObject();
//        try {
//            mergedJSON = new JSONObject(json1, JSONObject.getNames(json1));
//
//            for (String key : JSONObject.getNames(json1)) {
//                mergedJSON.put(key, json2.get(key));
//            }
//
//        } catch (JSONException e) {
//            throw new RuntimeException("JSON Exception" + e);
//        }
//        return mergedJSON;
//    }
}
