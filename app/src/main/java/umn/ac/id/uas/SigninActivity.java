package umn.ac.id.uas;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SigninActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnSignIn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);



        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                if (email.length() > 0){
                    if(password.length() > 0){
                        Toast.makeText(SigninActivity.this, "Logging in...",
                                Toast.LENGTH_SHORT).show();
                        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

                        auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(SigninActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()) {
                                            findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                                            // Sign in success, update UI with the signed-in user's information
                                            FirebaseUser user = auth.getCurrentUser();
                                            Toast.makeText(SigninActivity.this, "Authentication success.",
                                                    Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SigninActivity.this, MainActivity.class));
                                        } else {
                                            findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                                            // If sign in fails, display a message to the user.
                                            Toast.makeText(SigninActivity.this, "User / password is incorrect",
                                                    Toast.LENGTH_SHORT).show();
                                            //updateUI(null);
                                            // ...
                                        }

                                        // ...
                                    }
                                });
                    }
                }


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser user) {

    }
}
