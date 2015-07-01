package gunsandrocket.com.projectalconaft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Äלטענמ on 01.07.2015.
 */
public class InputActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //here will two editText and button with beautiful animation)

        //next code need add into button`s metod OnClick
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
