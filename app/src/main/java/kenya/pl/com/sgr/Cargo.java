package kenya.pl.com.sgr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Cargo extends AppCompatActivity {
    EditText num_of_spaces;
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
        setContentView(R.layout.activity_cargo);

        Button button3= (Button)findViewById(R.id.button3);
         num_of_spaces=(EditText)findViewById(R.id.editText7);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               // Toast.makeText(Cargo.this, "dd", Toast.LENGTH_SHORT).show();

                String num_of_space=num_of_spaces.getText().toString();
                int amt=400*Integer.parseInt(num_of_space);

                startActivity(new Intent(Cargo.this, Payment.class)
                .putExtra("num_of_spaces", num_of_space)
                .putExtra("amt", String.valueOf(amt))
                .putExtra("action", "cargo"));
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
}
