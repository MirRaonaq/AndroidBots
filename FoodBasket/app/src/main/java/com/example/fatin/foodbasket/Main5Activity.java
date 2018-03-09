package com.example.fatin.foodbasket;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main5Activity extends AppCompatActivity implements View.OnClickListener{
    private Button register_btn;
    private EditText user_name;
    private EditText user_password;
    private EditText user_email;

    FirebaseAuth firebaseAuth =null;
    static String TAG ="HELL0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        HideKeyBoard.hideKeyPad(findViewById(R.id.home_register), Main5Activity.this);

        register_btn =(Button) findViewById(R.id.registerBtn);
        user_email =(EditText)findViewById(R.id.email);
        user_password=(EditText)findViewById(R.id.password);
        firebaseAuth=FirebaseAuth.getInstance();
        register_btn.setOnClickListener(this);
        user_name =(EditText)findViewById(R.id.username);
    }

    @Override
    public void onClick(View view) {
        String _user_email = user_email.getText().toString();
        String _password = user_password.getText().toString();
        String _username = user_name.getText().toString();

        registerUser(_user_email,_password,_username);
    }

    private void registerUser(final String userEmail,final String userPassword, final String userName) {
       if(false/*checkIfAcountAlreadyExist(userEmail,userName)*/){
           Toast.makeText(Main5Activity.this,user_name+", there was an error in your account creation.", Toast.LENGTH_LONG).show();
           return;
       }

       if(ValidateFieldInput.fieldsNotEmpty(userEmail,userPassword,userName)){
            firebaseAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Main5Activity.this,userName +", Your account was created successfully", Toast.LENGTH_LONG).show();
                        comfirmEnteredData(firebaseAuth.getCurrentUser());
                    }else {
                        user_email.setText("");
                        user_password.setText("");
                        Toast.makeText(Main5Activity.this,user_name+", there was an error in your account creation.", Toast.LENGTH_LONG).show();

                    }
                }
            });
        }else {
           Toast.makeText(Main5Activity.this,"Input field cannot be empty.", Toast.LENGTH_LONG).show();

       }
    }

    private boolean checkIfAcountAlreadyExist(final String userEmail, final String userName) {
        boolean crated =false;
        final DatabaseReference user = FirebaseDatabase.getInstance().getReference("users").child(userEmail);
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                   // crated=true;

                }catch (Exception e){
                    DatabaseReference username_1 = user.child(userName);
                    DatabaseReference ename =username_1.child("email");
                    ename.setValue(userEmail);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return true;
    }

    private void comfirmEnteredData(FirebaseUser currentUser) {
        findViewById(R.id.password).setVisibility(View.GONE);
        findViewById(R.id.username).setVisibility(View.GONE);
        findViewById(R.id.registerBtn).setVisibility(View.GONE);
        findViewById(R.id.email).setVisibility(View.GONE);

        Toast.makeText(this,currentUser.getDisplayName(),Toast.LENGTH_LONG).show();

    }
}
