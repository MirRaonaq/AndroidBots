package com.example.fatin.foodbasket;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main5Activity extends AppCompatActivity implements View.OnClickListener{
    private Button register_btn;
    private EditText user_name;
    private EditText user_password;
    private EditText user_email;

    FirebaseAuth firebaseAuth =null;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        register_btn =(Button) findViewById(R.id.registerBtn);
        user_email =(EditText)findViewById(R.id.email);
        user_password=(EditText)findViewById(R.id.password);
        firebaseAuth=FirebaseAuth.getInstance();
        register_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        registerUser(user_email,user_password);
    }

    private void registerUser(EditText user_email, EditText user_password) {
        firebaseAuth.createUserWithEmailAndPassword(user_email.toString(),user_password.toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Main5Activity.this,"Account Created Sucessfully", Toast.LENGTH_LONG).show();
                   // FirebaseUser user = firebaseAuth.getCurrentUser();
                  //  updateUI(user);
                }
            }
        });
    }
}
