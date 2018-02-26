package kenya.pl.com.sgr;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {
    EditText phone, password,id_number,name;
    Button signup;
    TextView signin;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle("Sign up here!");
        phone=(EditText)findViewById(R.id.editText6);
        password=(EditText)findViewById(R.id.editText2);
        id_number=(EditText)findViewById(R.id.editText5);
        name=(EditText)findViewById(R.id.editText4);
        signup=(Button)findViewById(R.id.button2);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                Signupfunction(phone.getText().toString(),
                        password.getText().toString(),
                        id_number.getText().toString(),
                        name.getText().toString()
                );
            }
        });

        signin=(TextView)findViewById(R.id.textView2) ;
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
startActivity(new Intent(SignUp.this, Login.class));
            }
        });
    }

    public void Signupfunction(final String phone, final String password, final String id_number, final String name)
    {

        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog= new ProgressDialog(SignUp.this);
                pDialog.setMessage("Signing you in...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params)
            {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("phone_number", phone);
                paramms.put("id", id_number);
                paramms.put("password", password);
                paramms.put("name", name);
                String s = rh.sendPostRequest(URLs.main+"signup.php", paramms);
                return s;

            }



            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                pDialog.dismiss();

                if(s.equals("1"))
                {
//                   new SweetAlertDialog(SignUp.this, SweetAlertDialog.SUCCESS_TYPE).setContentText("Registration success. Please sign in to continue.")
//                           .setTitleText("Attention.").show();

                    new AlertDialog.Builder(SignUp.this).setTitle("Attention!").setMessage("Registration success. Please sign in to continue.").show();

                }
                else
                    {
//                        new SweetAlertDialog(SignUp.this, SweetAlertDialog.ERROR_TYPE).setContentText("Registration Failed.")
//                                .setTitleText("Attention.").show();
                        new AlertDialog.Builder(SignUp.this).setTitle("Attention!").setMessage("Registration Failed.").show();
                }

            }


        }
        GetJSON jj = new GetJSON();
        jj.execute();


    }
}
