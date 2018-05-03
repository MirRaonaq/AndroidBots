package com.example.fatin.foodbasket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class Register extends AppCompatActivity implements View.OnClickListener{

    private ProgressDialog progressDialog;

    private static class User {

        public String email;
        public String user_name;

        public User(String use_name, String email) {
        }

    }
    private Button register_btn;
    private EditText user_name;
    private EditText user_password;
    private EditText user_pass_reEntered;
    private EditText user_email;

    FirebaseAuth firebaseAuth =null;
    static String TAG ="HELL0";
    //boolean userIScreated =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        HideKeyBoard.hideKeyPad(findViewById(R.id.home_register), Register.this);
        progressDialog= new ProgressDialog(this);


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
        checkIfAcountAlreadyExist(userName, userEmail, userPassword);
    }

    private void checkIfAcountAlreadyExist(final String userName, final String userEmail, final String userPassword) {
        //boolean created =false;
        final DatabaseReference user = FirebaseDatabase.getInstance().getReference("users").child(userName);
        user.addListenerForSingleValueEvent(new ValueEventListener() {
           //FirebaseAuth firebaseAuth =null;

           @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (ValidateFieldInput.fieldsNotEmpty(userEmail, userPassword, userName)) {
                        if (dataSnapshot.getValue() == null) {
                            Log.d(TAG, "onDataChange: yes is null "+ dataSnapshot.getValue());

                            firebaseAuth = FirebaseAuth.getInstance();
                            progressDialog.setTitle("Registration in progress...");
                            progressDialog.show();
                            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        final FirebaseUser user = firebaseAuth.getCurrentUser();
                                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(Register.this,
                                                            "Verification email sent to " + user.getEmail(),
                                                            Toast.LENGTH_SHORT).show();

                                                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                    final DatabaseReference ref = database.getReference("users");
                                                    DatabaseReference usersRef = ref.child(userName + "/email");
                                                    usersRef.setValue(userEmail);

                                                    comfirmEnteredData(firebaseAuth.getCurrentUser(), userName);
                                                    firebaseAuth.signOut();

                                                    Intent intent = new Intent(Register.this, Main3Activity.class);
                                                    startActivity(intent);

                                                    //finish();
                                                } else {
                                                    progressDialog.dismiss();
                                                    Log.e(TAG, "sendEmailVerification", task.getException());
                                                    Toast.makeText(Register.this,
                                                            "Failed to send verification email.",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                        });

                                    } else {
                                        progressDialog.dismiss();
                                        user_email.setText("");
                                        user_password.setText("");
                                        Toast.makeText(Register.this, "There was an error in creating your account.", Toast.LENGTH_LONG).show();

                                    }

                                }
                            });
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(Register.this,"User name is taken.", Toast.LENGTH_LONG).show();

                        }
                    }else {

                        progressDialog.dismiss();
                        Toast.makeText(Register.this,"Input field cannot be empty.", Toast.LENGTH_LONG).show();

                    }

                }catch (Exception e){
                    //DatabaseReference username_1 = user.child(userName);
                    //DatabaseReference ename =username_1.child("email");
                    //ename.setValue(userEmail);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void comfirmEnteredData(FirebaseUser currentUser, String userName) {

        //String userName = currentUser.getDisplayName();
        String userEmail =currentUser.getEmail();

        findViewById(R.id.password).setVisibility(View.GONE);
        findViewById(R.id.username).setVisibility(View.GONE);
        findViewById(R.id.registerBtn).setVisibility(View.GONE);
        findViewById(R.id.email).setVisibility(View.GONE);
        currentUser.reload();





        Toast.makeText(this,"Your account was created successfully",Toast.LENGTH_LONG).show();



    }
}
