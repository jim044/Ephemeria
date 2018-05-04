package com.example.jvillossel.ephemeria.IHM;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jvillossel.ephemeria.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import bolts.Task;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private Button button_connexion_google;
    private final int RC_SIGN_IN = 123;
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;
    private LoginButton button_connexion_facebook;
    private Button inscription;
    private FirebaseAuth mAuth;
    private Button connexion_button_email;
    private static final String TAG = "MyActivity";
    private EditText connexion_email;
    private EditText connexion_mdp;
    private String email;
    private String password;
    private String utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //button_connexion_google = findViewById(R.id.button_connexion_google);
        //button_connexion_facebook = (LoginButton) findViewById(R.id.login_button);
        inscription = findViewById(R.id.button_inscription);
        connexion_button_email = findViewById(R.id.button_connexion_simple);
        connexion_email = findViewById(R.id.editText_email);
        connexion_mdp = findViewById(R.id.editText_mot_de_passe);

        mAuth = FirebaseAuth.getInstance();


        connexion_button_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = connexion_email.getText().toString().trim();
                password = connexion_mdp.getText().toString().trim();

                connexion_par_mail(email, password);

            }
        });



        //button_connexion_facebook.setReadPermissions("email");

        // Connexion Google
        // GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        //         .requestEmail()
        //         .build();

        // mGoogleApiClient = new GoogleApiClient.Builder(this)
        //        .enableAutoManage(this ,  this)
        //       .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        //       .build();

        //callbackManager = CallbackManager.Factory.create();

        // button_connexion_google.setOnClickListener(new View.OnClickListener() {
        //    public void onClick(View v) {
//
        //        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        //       startActivityForResult(signInIntent, RC_SIGN_IN);
        //    }
        // });

        // Connexion FaceBook
        //button_connexion_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
         //   @Override
         //   public void onSuccess(LoginResult loginResult) {
          //      // App code
           // }

         //   @Override
          //  public void onCancel() {
          //      // App code
          //  }

          //  @Override
           // public void onError(FacebookException exception) {
                // App code
          //  }
        //});

        //LoginManager.getInstance().registerCallback(callbackManager,
         //       new FacebookCallback<LoginResult>() {
        //            @Override
         //           public void onSuccess(LoginResult loginResult) {
         //               // App code
          //          }

         //           @Override
          //          public void onCancel() {
                        // App code
          //          }

           //         @Override
            //        public void onError(FacebookException exception) {
             //           // App code
              //      }
               // });

        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInscription = new Intent(MainActivity.this, Inscription.class);
                startActivity(intentInscription);
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

    public void connexion_par_mail(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user.isEmailVerified())
                            {
                                utilisateur = "Vérifié";
                            }
                            else
                            {
                                utilisateur = "Non vérifié";
                            }


                            Toast.makeText(MainActivity.this, utilisateur, Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // callbackManager.onActivityResult(requestCode, resultCode, data);
        // super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateUI(FirebaseUser user) {

    }
}
