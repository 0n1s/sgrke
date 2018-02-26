package kenya.pl.com.sgr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import static kenya.pl.com.sgr.URLs.id_num;

public class Login extends AppCompatActivity {
    EditText phone, passwprd;
    Button signup;
    TextView signup_;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        phone=(EditText)findViewById(R.id.editText);
        passwprd=(EditText)findViewById(R.id.editText3);
        signup=(Button)findViewById(R.id.button);

        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                signin(phone.getText().toString(), passwprd.getText().toString());
            }
        });
        signup_=(TextView)findViewById(R.id.textView);
        signup_.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });

    }
    public void signin(final String username, final String password)
    {

        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog= new ProgressDialog(Login.this);
                pDialog.setMessage("Signing you in...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params)
            {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("id", username);
                paramms.put("password", password);
                String s = rh.sendPostRequest(URLs.main+"login.php", paramms);
                return s;

            }



            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                pDialog.dismiss();

                if(s.equals("1"))
                {
                    id_num=username;
                    startActivity(new Intent(Login.this, Main2Activity.class));

                }
                else {
                    Toast.makeText(Login.this, "Login failed!", Toast.LENGTH_SHORT).show();
                }

            }


        }
        GetJSON jj = new GetJSON();
        jj.execute();


    }
}
