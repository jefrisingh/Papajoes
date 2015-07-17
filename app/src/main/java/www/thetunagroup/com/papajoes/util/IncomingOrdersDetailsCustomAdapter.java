package www.thetunagroup.com.papajoes.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import www.thetunagroup.com.papajoes.R;

/**
 * Created by DELL-PC on 7/11/2015.
 */
public class IncomingOrdersDetailsCustomAdapter extends ArrayAdapter<String> {

    Activity _activity;
    ArrayList<String>  _objects;
    ArrayList<JSONObject> _jsonValues;
    public ImageLoader imageLoader;




    public IncomingOrdersDetailsCustomAdapter(Activity activity,ArrayList<String> objects, ArrayList<JSONObject> jsonValues) {
        super(activity, R.layout.incoming_order_item_list_item_details, objects);
        this._activity = activity;
        this._objects = objects;
        this._jsonValues = jsonValues;
        imageLoader=new ImageLoader(_activity.getApplicationContext());


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            convertView = _activity.getLayoutInflater().inflate(R.layout.incoming_order_item_list_item_details,null);
        }

        ImageView preview = (ImageView) convertView.findViewById(R.id.incoming_order_items_list_item_imageView);
        TextView details = (TextView) convertView.findViewById(R.id.incoming_order_items_list_item_details_textView);

        JSONObject jsonObject = _jsonValues.get(position);
        if(jsonObject != null)
        {
            try {
                String item_details = jsonObject.getString("title")+"\n"+jsonObject.getString("thickness")+" "+jsonObject.getString("pack") + "L/B\nQuantity: "+jsonObject.getString("quantity")+"\n$"+jsonObject.getString("price");
                details.setText(item_details);
                String imageUrl = jsonObject.getString("image").trim();
                if(URLUtil.isValidUrl(imageUrl))
                {
                    imageLoader.DisplayImage(imageUrl, preview);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return convertView;
    }
}
