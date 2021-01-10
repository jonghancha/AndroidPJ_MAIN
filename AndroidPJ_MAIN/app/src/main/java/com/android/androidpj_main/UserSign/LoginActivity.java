package com.android.androidpj_main.UserSign;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.androidpj_main.Main.MainActivity;
import com.android.androidpj_main.R;

import java.security.MessageDigest;


public class LoginActivity extends Activity {

    final static String TAG = "LoginActivity";

    Button gomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        gomain = findViewById(R.id.gomain);

        gomain.setOnClickListener(gogo);


    //--------------------------------------------
        getAppKeyHash(); //해시 키.
    //--------------------------------------------

    }

    View.OnClickListener gogo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };


        //-------- 해시 키 찾는 코드 -----------------------------------------------------------------------
        private void getAppKeyHash() {
            try {
                PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
                for (Signature signature : info.signatures) {
                    MessageDigest md;
                    md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    String something = new String(Base64.encode(md.digest(), 0));

                    Log.e(TAG,"Hash key : " + something);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.e(TAG,"name not found" + e.toString());
            }
        }
        //----------------------------------------------------------------------------------------------

}//-------------------