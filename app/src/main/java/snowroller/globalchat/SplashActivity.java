package snowroller.globalchat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        new Handler().postDelayed( ()-> startActivity(intent),2000 );
        //finish();  //Replaced by  android:noHistory="true" in manifest
    }
}
