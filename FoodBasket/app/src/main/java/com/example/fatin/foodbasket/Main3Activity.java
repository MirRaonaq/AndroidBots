package com.example.fatin.foodbasket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

public class Main3Activity extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private Button login;
    private Button register;
    private Button forgot;
    //  private TextView Info;
    private int counter = 5;
    FirebaseDatabase fBase;
    DatabaseReference dataRef;

    //authentication variable
    FirebaseAuth firebaseAuth = null;
    FirebaseAuth.AuthStateListener authStateListener;

    String TAG = "MAIN_TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        HideKeyBoard.hideKeyPad(findViewById(R.id.home), Main3Activity.this);

        name = (EditText) findViewById(R.id.etName);
        password = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.btnLogin);
        register = (Button) findViewById(R.id.btnReg);

        //instantiate firebaseauth
        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebase_auth) {
                if (firebase_auth.getCurrentUser() != null) {
                    //open when sign in is successful.
                     Intent intent = new Intent(Main3Activity.this, MainActivity.class);
                     startActivity(intent);
                }

            }
        };

        forgot= (Button)findViewById(R.id.btnForgot);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main6Activity();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _user_email = name.getText().toString();
                String _pasword = password.getText().toString();
                if (ValidateFieldInput.fieldsNotEmpty(_user_email, _pasword)) {
                    firebaseAuth.signInWithEmailAndPassword(_user_email, _pasword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                password.setText("");
                                inValidLogin();
                            } else {
                                //  Intent intent = new Intent(Main3Activity.this,MainActivity.class);
                                //  startActivity(intent);
                            }

                        }
                    });
                } else {
                    password.setText("");
                    inValidLogin();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.signOut();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    private void inValidLogin() {
        Toast.makeText(Main3Activity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
    }


    public void signup() {
        Intent intent = new Intent(Main3Activity.this, Main5Activity.class);
        startActivity(intent);
    }
    public void main6Activity(){
        Intent intent = new Intent(Main3Activity.this, Main6Activity.class);
        startActivity(intent);
    }

}
