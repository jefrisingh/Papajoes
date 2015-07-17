package www.thetunagroup.com.papajoes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import www.thetunagroup.com.papajoes.util.IncomingOrdersDetailsCustomAdapter;
import www.thetunagroup.com.papajoes.util.Interaction;
import www.thetunagroup.com.papajoes.util.Progress;
import www.thetunagroup.com.papajoes.util.SwipeDetector;

/**
 * Created by DELL-PC on 7/6/2015.
 */
public class CompletedOrders {

    Activity _activity;
    ListView _listView;
    TextView _notify;
    ArrayList<String> _list;
    ArrayList<String> _order_id;
    String user_id = "";

    SharedPreferences sharedPreferences;
    public CompletedOrders(Activity activity) {
        this._activity = activity;
        this._listView= new ListView(_activity);
        this._notify = new TextView(_activity);
        this._list = new ArrayList<String>();
        this._order_id = new ArrayList<String>();

        sharedPreferences= _activity.getSharedPreferences("Papajoe", Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id","");

        final SwipeDetector swipeDetector =new SwipeDetector();
        _listView.setOnTouchListener(swipeDetector);
       // _listView.setOnItemClickListener(listener);
        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(swipeDetector.swipeDetected()) {
                    if(swipeDetector.getAction() == SwipeDetector.Action.RL) {

                        Toast.makeText(_activity,"Right to left",Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(_activity,"other then Right to left",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(_activity,"Click",Toast.LENGTH_LONG).show();
                }
            }
        });

            /*
        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new orderDetails().execute(_order_id.get(position));
            }
        });
        */
    }


    public View getCompletedOrders() {
        if(!user_id.equals("")) {
            try {
                _list.clear();
                _order_id.clear();

                MultipartEntity entity = new MultipartEntity();
                entity.addPart("user_id", new StringBody(user_id));

                String response = Interaction.postGetData(AppUrls.completedOrders, entity);
                if (response.contains("success")) {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("orders");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        /*
                            {
                                "order_id": "1",
                                "bill_id": "1",
                                "user_device_id": "1",
                                "type": "1",
                                "source": "1",
                                "first_name": "Jophi",
                                "last_name": "Joe",
                                "address1": "addddd",
                                "address2": "addd",
                                "city": "Birmingham",
                                "state": "MI",
                                "zip": "48009",
                                "mobile": "999999999"
                            }
                         */

                        JSONObject current = jsonArray.getJSONObject(i);
                        String details = current.getString("first_name") + "\n" +current.getString("mobile");
                        _list.add(details);
                        _order_id.add(current.getString("order_id"));
                    }
                }


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayAdapter<String> simpleAdapter = new ArrayAdapter<String>(_activity, android.R.layout.simple_list_item_1, _list);
            _listView.setAdapter(simpleAdapter);
            return _listView;
        }
        else
        {
            _notify.setText("Problem with loading , please logout then login");
            return _notify;
        }
    }

    class orderDetails extends AsyncTask<String,String,String>
    {
        Progress progress;
        IncomingOrdersDetailsCustomAdapter incomingOrdersCustomAdapter = null;
        @Override
        protected String doInBackground(String... params) {
            String orderId = params[0];

            if(!user_id.equals("") && !orderId.equals("")) {
                try {
                    //user_id
                    //order_id

                    MultipartEntity entity = new MultipartEntity();
                    entity.addPart("user_id", new StringBody(user_id));
                    entity.addPart("order_id", new StringBody(orderId));

                    String response = Interaction.postGetData(AppUrls.getOrdersItems, entity);
                    JSONObject jsonObject = new JSONObject(response);
                    String serverResponce = jsonObject.getString("response");
                    if(serverResponce.contains("success"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("order_items");
                        int jsonArraySize = jsonArray.length();
                        if(jsonArraySize>0)
                        {
                            ArrayList<JSONObject> resultSets = new ArrayList<JSONObject>();
                            ArrayList<String> objects = new ArrayList<String>();
                            for(int i=0;i<jsonArraySize;i++)
                            {
                                resultSets.add(jsonArray.getJSONObject(i));
                                objects.add(""+i);
                            }

                            incomingOrdersCustomAdapter = new IncomingOrdersDetailsCustomAdapter(_activity,objects,resultSets);

                        }

                    }
                    else
                    {
                        //error or failed
                    }

                } catch (Exception e) {

                }
            }

            return "some";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new Progress(_activity);
            progress.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(incomingOrdersCustomAdapter != null)
            {
                ListView itemList = new ListView(_activity);
                itemList.setAdapter(incomingOrdersCustomAdapter);

                AlertDialog.Builder builder = new AlertDialog.Builder(_activity);
                builder.setTitle("Order Details");
                builder.setView(itemList);
                builder.setPositiveButton("CLOSE",null);
                builder.create().show();
            }
            else
            {
                //something went wrong
                Toast.makeText(_activity,"Something went wrong",Toast.LENGTH_LONG).show();
            }
            progress.dismiss();
        }
    }
}
