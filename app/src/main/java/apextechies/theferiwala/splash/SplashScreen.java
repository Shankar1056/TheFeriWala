package apextechies.theferiwala.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.firebase.digitsmigrationhelpers.AuthMigrator;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import apextechies.theferiwala.R;
import apextechies.theferiwala.activity.MainActivity;
import io.fabric.sdk.android.Fabric;

/**
 * Created by shankar on 16/11/17.
 */

public class SplashScreen extends AppCompatActivity {

    public static final int RC_SIGN_IN = 1;
    private static final String TAG = "AplashActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                checksession();

            }
        }, 3000);
    }

    private void checksession() {
        AuthMigrator.getInstance().migrate(true).addOnSuccessListener(this,
                new OnSuccessListener() {

                    @Override
                    public void onSuccess(Object o) {
                        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                        if (u != null) {
                            // Either a user was already logged in or token exchange succeeded
                            Log.d("MyApp", "Digits id preserved:" + u.getUid());
                            Log.d("MyApp", "Digits phone number preserved: " + u.getPhoneNumber());
                            //  login(u.getPhoneNumber().toString(), "0");

                                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                                finish();
                        } else {
                            // No tokens were found to exchange and no Firebase user logged in.
                            startActivityForResult(
                                    AuthUI.getInstance()
                                            .createSignInIntentBuilder()
                                            .setProviders(Arrays.asList(
                                                    new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()

                                            ))
                                            .build(),
                                    RC_SIGN_IN);
                        }
                    }
                }).addOnFailureListener(this,
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        // Error migrating Digits token
                    }
                });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
               startActivity(new Intent(SplashScreen.this,MainActivity.class));
               finish();
//				return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Log.e("Login", "Login canceled by User");
//					return;
                }
                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Log.e("Login", "No Internet Connection");
//					return;
                }
                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Log.e("Login", "Unknown Error");
//					return;
                }
            }
            Log.e("Login", "Unknown sign in response");

        }
    }

}
