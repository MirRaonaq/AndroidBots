package buffalo.cse.foodbasket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class login extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private Button Login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Login = (Button)findViewById(R.id.btnLogin);
    }

    private void validate(String userName, String userPassword){
        if ((userName == "Mir") && (userPassword == "1234")){
            Intent intent = new Intent(login.this, SecondActivity.class);
            startActivity(intent);
        }else{
            System.out.print("Incorrect Password or User");
        }
    }
}
