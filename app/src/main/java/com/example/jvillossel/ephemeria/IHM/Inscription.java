package com.example.jvillossel.ephemeria.IHM;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jvillossel.ephemeria.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Inscription extends AppCompatActivity {

    private Button annuler_inscription;
    private Button confirmer_inscription;
    private FirebaseAuth mAuth;
    private static final String TAG = "MyActivity";
    private EditText inscription_email;
    private EditText inscription_mdp;
    private String email;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_bis);

        mAuth = FirebaseAuth.getInstance();
        annuler_inscription = findViewById(R.id.button_inscription_annulation);
        confirmer_inscription = findViewById(R.id.button_inscription_confirmation);
        inscription_email = findViewById(R.id.input_inscription_email);
        inscription_mdp = findViewById(R.id.input_inscription_mdp);

        annuler_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAnnulerInscription = new Intent(Inscription.this, MainActivity.class);
                startActivity(intentAnnulerInscription);
            }
        });

        confirmer_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = inscription_email.getText().toString().trim();
                password = inscription_mdp.getText().toString().trim();
                inscription_par_mail(email, password);
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }


    public void inscription_par_mail(String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Inscription.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }
}
