package kenya.pl.com.sgr;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static kenya.pl.com.sgr.URLs.id_num;

public class MyTickets extends AppCompatActivity {
ListView list_item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);
        list_item =(ListView)findViewById(R.id.list_item);
        getJSON(id_num);
    }



    public void getJSON(final String id)
    {
        class GetJSON extends AsyncTask<Void, Void, String> {

            // SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            ProgressDialog pDialog = new ProgressDialog(MyTickets.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog.setMessage("Fetching shares for "+id);
                pDialog.show();
            }
            @Override
            protected String doInBackground(Void... params)
            {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(URLs.fetcher2+"?id="+id);
                return s;
            }
            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                pDialog.dismiss();
                showthem(s);

            }


        }
        GetJSON jj =new GetJSON();
        jj.execute();
    }

    private void showthem(String s)
    {


        //new AlertDialog.Builder(MyTickets.this).setMessage(s).show();
        //Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(s);
            JSONArray result = jsonObject.getJSONArray("result");

            String itemID="";
            for (int i = 0; i < result.length(); i++)
            {  JSONObject jo = result.getJSONObject(i);


                String id=jo.getString("id");

                String destination=jo.getString("destination");

                String seat_num=jo.getString("seat_num");

                String status=jo.getString("status");


                HashMap<String, String> employees = new HashMap<>();
                Date date=  new Date();
                employees.put("id", id);
                employees.put("destination", destination);
                employees.put("seat_num",seat_num);
                employees.put("status", status);
                list.add(employees);
            }




        } catch (JSONException e) {


            Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }

        ListAdapter adapter = new SimpleAdapter(MyTickets.this, list, R.layout.myticketslist,
                new String[]{
                        "id",
                        "destination",
                        "seat_num",
                        "status"


                }
                , new int[]{

                R.id.textView8,
                R.id.textView10,
                R.id.textView13,
                R.id.textView18,


        });
        list_item.setAdapter(adapter);
    }



}
