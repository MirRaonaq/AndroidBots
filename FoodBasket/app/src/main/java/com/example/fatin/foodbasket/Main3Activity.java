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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class Main3Activity extends AppCompatActivity {
    private static class User {

        public String email;
        //public String user_name;

        public User(String email) {
        }

    }

    private EditText name;
    private EditText password;
    private Button login;
    private Button register;
    //  private TextView Info;
    private int counter = 5;
    FirebaseDatabase fBase;
    DatabaseReference dataRef;

    String _user_email ="";
    String _pasword="";
    String val;

    //authentication variable
    FirebaseAuth firebaseAuth = null;
    FirebaseAuth.AuthStateListener authStateListener;

    String TAG = "MAIN_TEST";
    User u;

   // static ArrayList<String> email_ =new ArrayList<>();

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
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _user_email = name.getText().toString().trim();
                _pasword = password.getText().toString();
                String ret;
                if (_user_email.contains("@")){
                    loginUserWithEmail(_user_email,_pasword);

                }else {
                    loginUserWithUserName(_user_email, _pasword);
                   // Log.d(TAG, "onClick: email return "+ u.email);
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

    private void loginUserWithEmail(String uemail, String pword) {
        if (ValidateFieldInput.fieldsNotEmpty(uemail, pword)) {
            firebaseAuth.signInWithEmailAndPassword(uemail, pword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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

    private void loginUserWithUserName(final String user_email, final String pword) {
        final DatabaseReference user = FirebaseDatabase.getInstance().getReference("users").child(user_email);
        //String val;
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String retVal=dataSnapshot.child("email").getValue().toString();
                    Log.d(TAG, "onDataChange: "+retVal);
                    if (ValidateFieldInput.fieldsNotEmpty(retVal, pword)) {
                        firebaseAuth.signInWithEmailAndPassword(retVal, pword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
                }catch (Exception ex){
                    Toast.makeText(Main3Activity.this, "The entered user name doesn't exist", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

}
