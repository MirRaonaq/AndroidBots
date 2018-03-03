package com.example.fatin.foodbasket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.Map;
import java.util.jar.JarEntry;

public class Main3Activity extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private Button login;
    private Button register;
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
        findViewById(R.id.home).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
                //HideKeyBoard hide = new HideKeyBoard(view,getSystemService(Activity.INPUT_METHOD_SERVICE));
                // hide.hideSoftKeyboard();
                return false;
            }
        });
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

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
                    Intent intent = new Intent(Main3Activity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        };
        //   Info.setText("No of attempts remaining: 5");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _user_email = name.getText().toString();
                String _pasword = password.getText().toString();
                if (fieldsNotEmpty(_user_email, _pasword)) {
                    firebaseAuth.signInWithEmailAndPassword(_user_email, _pasword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                inValidLogin();
                            } else {
                                //  Intent intent = new Intent(Main3Activity.this,MainActivity.class);
                                //  startActivity(intent);
                            }

                        }
                    });
                } else {
                    inValidLogin();
                }

                //    validate(_name,_pasword);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();

            }
        });

    }

    private void updateUseAccount(FirebaseUser user) {
        if (user != null){
            findViewById(R.id.etName).setVisibility(View.GONE);
            findViewById(R.id.etPassword).setVisibility(View.GONE);
            findViewById(R.id.btnLogin).setVisibility(View.GONE);
        }else {
            findViewById(R.id.username).setVisibility(View.VISIBLE);
            findViewById(R.id.password).setVisibility(View.VISIBLE);
            findViewById(R.id.btnLogin).setVisibility(View.VISIBLE);
        }
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

    private boolean fieldsNotEmpty(String user_email, String user_pasword) {
        if (TextUtils.isEmpty(user_email) || TextUtils.isEmpty(user_pasword)) {
            return false;
        }
        return true;
    }


    private void validate(String userName, String userPassword) {
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference user = firebaseDatabase.getReference("users_name");
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //  Map<String,String> user_data=dataSnapshot.getValue(Map.class);
                //Log.d(TAG, "validate: "+dataSnapshot.getValue());
                //  Log.d(TAG, "validate: "+user_data.get("kemokhan"));
                //Toast.makeText(Main3Activity.this,dataSnapshot.getValue().toString() , Toast.LENGTH_SHORT).show();
                //  Map<String,String> user_data=dataSnapshot.getValue(Map.class);
                Log.d(TAG, "validate: " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //String string =user.getKey();

        // String val = user.getDatabase().toString();

        /* DatabaseReference username_1 = user.child("kemokhan");
        DatabaseReference last_name =username_1.child("Last Name");
        last_name.setValue("Khan");*/

        //DatabaseReference password =username_1.child("Password");
        //first_name.setValue(userPassword);

        DatabaseReference username_2 = user.getRef().child("FatinNazat");

        //first name
        // DatabaseReference first_name =username_1.push().child("First Name");
        //first_name.setValue(user);
//        //last name
//        DatabaseReference last_name =username_1.child("Last Name").push();
//        last_name.setValue("Raymond");
//        //password
//        DatabaseReference password =username_1.child("Password").push();
//        password.setValue(userPassword);


        // user.child("Password").setValue(userPassword);
        if ((userName.equals("Mir")) && (userPassword.equals("1234"))) {
            Intent intent = new Intent(Main3Activity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Invalid Credentials.\nPlease, Enter Password Again. Attemps remain:" + String.valueOf(counter));
            alert.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            /**
             alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {

            }
            });**/
            alert.show();
            counter--;
            //     Info.setText("Invalid Credentials.\nPlease, Enter Password Again. Attemps remain: "+ String.valueOf(counter));
            if (counter == 0) {
                //          Info.setText("No of attemps has reached the limit: ");
            }
            if (counter == 0) {
                login.setEnabled(false);
            }
        }
    }

    public void signup() {
        Log.d(TAG, "signup: is called");
        Intent intent = new Intent(Main3Activity.this, Main5Activity.class);
        startActivity(intent);
    }

}
