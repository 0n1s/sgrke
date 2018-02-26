package kenya.pl.com.sgr;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import static kenya.pl.com.sgr.URLs.id_num;

public class Payment extends AppCompatActivity
{
TextView pay;
    String date;
    EditText mpesacode;
    Button complete_transaction;
    String tickets;
    String amt;
    String action;
    String dest;
    String num_of_spaces;
    String type;
    String seat_number;

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
        setContentView(R.layout.activity_payment);

        Intent intent = getIntent();
        amt= intent.getStringExtra("amt");
        action= intent.getStringExtra("action");

        if(action.equals("personal"))
        {
             type= intent.getStringExtra("type");
             seat_number=intent.getStringExtra("seat_number");
             date= intent.getStringExtra("date");
             dest=intent.getStringExtra("destination");
        }
        else
        {
            num_of_spaces=intent.getStringExtra("num_of_spaces");
        }


        pay=(TextView)findViewById(R.id.textView3);
        pay.setText("Pay "+amt+" KES /=");
        mpesacode=(EditText)findViewById(R.id.editText7);
        complete_transaction=(Button) findViewById(R.id.button3);


        complete_transaction.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(action.equals("personal")) {
                    payment(dest,mpesacode.getText().toString(),id_num,amt,type,seat_number);

                }else {
                    payment_cargo(mpesacode.getText().toString(), id_num,amt,num_of_spaces);
                }


            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


    }


    public void payment(final String dest, final String phone, final String id, final String amt, final String _class, final String seat_number)
    {


        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog= new ProgressDialog(Payment.this);
                pDialog.setMessage("Processing your request...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params)
            {

                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("id", id);
                paramms.put("amt", amt);
                paramms.put("seat_number", seat_number);
                paramms.put("action", "0");
                paramms.put("phone", phone);
                paramms.put("date", date);
                paramms.put("dest", dest);
                paramms.put("class", _class);
                String s = rh.sendPostRequest(URLs.main+"book.php", paramms);
                return s;

            }



            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                pDialog.dismiss();
                new AlertDialog.Builder(Payment.this).setMessage(s.substring(0,50)).setPositiveButton("okay", null).show();
                //new AlertDialog.Builder()
                //Toast.makeText(Payment.this, s, Toast.LENGTH_SHORT).show();
                String message = "We have received your request for seat number "+seat_number + " on " +date+". Please keep time";
                sendSMS( phone,  message);
            }


        }
        GetJSON jj = new GetJSON();
        jj.execute();

    }


    public void payment_cargo(final String phone, final String id, final String amt, final String num_of_spaces)
    {


        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog= new ProgressDialog(Payment.this);
                pDialog.setMessage("Processing your request...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params)
            {

                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("id", id);
                paramms.put("amt", amt);
                paramms.put("action", "1");
                paramms.put("phone", phone);
                paramms.put("num_of_spaces", num_of_spaces);
                String s = rh.sendPostRequest(URLs.main+"book.php", paramms);
                return s;

            }



            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                pDialog.dismiss();
                new AlertDialog.Builder(Payment.this).setMessage(s.substring(0,50)).setPositiveButton("okay", null).show();
               // Toast.makeText(Payment.this, s.substring(0,50), Toast.LENGTH_SHORT).show();

                String message = "We have received your request for "+num_of_spaces + " spaces on " +date+". Please keep time";

                sendSMS( phone,  message);
            }


        }
        GetJSON jj = new GetJSON();
        jj.execute();

    }


    public void sendSMS(String phoneNo, String msg)
    {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}
