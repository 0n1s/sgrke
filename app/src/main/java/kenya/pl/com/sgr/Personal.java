package kenya.pl.com.sgr;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class Personal extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener {
    EditText date;
    EditText num_of_tickets;
    Spinner spinner2, spinner, from, to;
    Button book_now;
    TextView textView3;
    String amt;
String destination_start;
String destination_to;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        num_of_tickets=(EditText)findViewById(R.id.editText7) ;
        book_now=(Button)findViewById(R.id.button3);
        textView3=(TextView)findViewById(R.id.textView3);
        spinner2=(Spinner)findViewById(R.id.spinner2);
        spinner=(Spinner)findViewById(R.id.spinner);
        from=(Spinner)findViewById(R.id.spinner3);
        to=(Spinner)findViewById(R.id.spinner4);




          from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
          {
              @Override
              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
              {
                  destination_start=from.getSelectedItem().toString();
                  destination_to=to.getSelectedItem().toString();
                  if(destination_to.equals(destination_start))
                  {
                      Toast.makeText(Personal.this, "Start point is similar to endpoint!", Toast.LENGTH_LONG).show();
                  }
                  else
                  {
                      amt= getAmt(from.getSelectedItem().toString(), to.getSelectedItem().toString(),spinner.getSelectedItem().toString());
                      textView3.setText(amt);
                      textView3.setText(amt+" KES /=");
                  }

              }

              @Override
              public void onNothingSelected(AdapterView<?> adapterView)
              {

              }
          });
          to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
          {
              @Override
              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
              {
                  destination_start=from.getSelectedItem().toString();
                  destination_to=to.getSelectedItem().toString();
                  if(destination_to.equals(destination_start))
                  {
                      Toast.makeText(Personal.this, "Start point is similar to endpoint!", Toast.LENGTH_SHORT).show();
                  }
else {
                      amt = getAmt(from.getSelectedItem().toString(), to.getSelectedItem().toString(), spinner.getSelectedItem().toString());
                      textView3.setText(amt);
                      textView3.setText(amt + " KES /=");
                  }
              }

              @Override
              public void onNothingSelected(AdapterView<?> adapterView) {

              }
          });

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String currentDateandTime = sdf.format(new Date());

        textView3.setText("0 KES /=");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                String selected=spinner.getSelectedItem().toString().toLowerCase();

                destination_start=from.getSelectedItem().toString();
                destination_to=to.getSelectedItem().toString();
                String dest =destination_start+","+destination_to;

                populate_spinner2(selected,dest, date.getText().toString());

                if(selected.equals("business"))
                {


                    amt= getAmt(from.getSelectedItem().toString(), to.getSelectedItem().toString(),spinner.getSelectedItem().toString());textView3.setText(amt);
                    textView3.setText(amt+" KES /=");
                }
                else
                {
                    amt= getAmt(from.getSelectedItem().toString(), to.getSelectedItem().toString(),spinner.getSelectedItem().toString());textView3.setText(amt);
                    textView3.setText(amt+" KES /=");

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        date=(EditText)findViewById(R.id.editText8);
        date.setText(currentDateandTime);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog=new DatePickerDialog( Personal.this, Personal.this,  2017,7,4 );
                dialog.show();
            }
        });

        book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

if(destination_start.equals(destination_to))
{
    Toast.makeText(Personal.this, "Start point is similar to endpoint!", Toast.LENGTH_SHORT).show();

}
else
{
    final AlertDialog.Builder builder = new AlertDialog.Builder(Personal.this);
    builder.setMessage("Are you sure you want to book?");
    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {


            //       Toast.makeText(Personal.this, spinner2.getSelectedItemPosition(), Toast.LENGTH_SHORT).show();

            String dest=destination_start+","+destination_to;
           // Toast.makeText(Personal.this, dest, Toast.LENGTH_SHORT).show();
            if(spinner2.getSelectedItemPosition()!=0)
            {
                startActivity( new Intent(Personal.this, Payment.class)
                        .putExtra("seat_number",spinner2.getSelectedItem().toString())
                        .putExtra("amt",amt)
                        .putExtra("destination",dest)
                        .putExtra("type",spinner.getSelectedItem().toString().toLowerCase())
                        .putExtra("date", date.getText().toString())
                        .putExtra("action","personal"));

            }
            else
            {
                Toast.makeText(Personal.this, "Select seat number.", Toast.LENGTH_SHORT).show();
            }


        }
    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
    });

    builder.show();

}








            }
        });

       amt= getAmt(from.getSelectedItem().toString(), to.getSelectedItem().toString(),spinner.getSelectedItem().toString());textView3.setText(amt);


    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int DayOfMonth)
    {


        Date datez = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);// for 6 hour
        calendar.set(Calendar.MONTH, monthOfYear);// for 0 min
        calendar.set(Calendar.DAY_OF_MONTH, DayOfMonth);//
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String currentDateandTime = sdf.format(calendar.getTime());
        date.setText(currentDateandTime);

        //Toast.makeText(this, "date set", Toast.LENGTH_SHORT).show();


    }

    public  void populate_spinner2(final String category, final String dest, final String date)
    {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

             //   Toast.makeText(Personal.this, category+ dest+ date, Toast.LENGTH_SHORT).show();

                pDialog= new ProgressDialog(Personal.this);
                pDialog.setMessage("fetching seats...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params)
            {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("class", category);
                paramms.put("dest", dest);
                paramms.put("date", date);
                String s = rh.sendPostRequest(URLs.main+"seats.php", paramms);
                return s;

            }



            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                pDialog.dismiss();

              //  new AlertDialog.Builder(Personal.this).setMessage(s).show();

               //Toast.makeText(Personal.this, s, Toast.LENGTH_SHORT).show();

                try {
                    ArrayList<String> options=new ArrayList<String>();
                            JSONObject jsonObj = new JSONObject(s);
                            JSONArray contacts = jsonObj.getJSONArray("result");
                            options.add("Choose seat number");
                            for (int i=0; i<contacts.length();i++)
                            {

                                JSONObject c = contacts.getJSONObject(i);
                                String sucess = c.getString("seat");
                                options.add(sucess);
                            }


// use default spinner item to show options in spinner
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Personal.this,android.R.layout.simple_spinner_item,options);
                    spinner2.setAdapter(adapter);












                } catch (JSONException e) {
                   Toast.makeText(Personal.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }

            }


        }
        GetJSON jj = new GetJSON();
        jj.execute();




    }

    public String getAmt (String from, String to, String _class)
    {
       String price="0";


       if(_class.toLowerCase().equals("business"))
       {

           if(from.equals("Mtito Andei") && to.equals("Mombasa") ||from.equals("Mombasa") && to.equals("Mtito Andei"))
           {
               price="100";
           }
           if(from.equals("Mtito Andei") && to.equals("Nairobi") || from.equals("Nairobi") && to.equals("Mtito Andei"))
           {
               price="400";
           }
           if(from.equals("Mombasa") && to.equals("Nairobi")||from.equals("Nairobi") && to.equals("Mombasa"))
           {
               price="700";
           }
       }
       else
       {
           if(from.equals("Mtito Andei") && to.equals("Mombasa") ||from.equals("Mombasa") && to.equals("Mtito Andei"))
           {
               price="200";
           }
           if(from.equals("Mtito Andei") && to.equals("Nairobi") || from.equals("Nairobi") && to.equals("Mtito Andei"))
           {
               price="600";
           }
           if(from.equals("Mombasa") && to.equals("Nairobi")||from.equals("Nairobi") && to.equals("Mombasa"))
           {
               price="1200";
           }
       }



        return price;
    }
}
