package gunsandrocket.com.projectalconaft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Äלטענמ on 01.07.2015.
 */
public class StartActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Here will animation with beer and astronaut
        Intent intent = new Intent(this, InputActivity.class);
        startActivity(intent);

    }
}
