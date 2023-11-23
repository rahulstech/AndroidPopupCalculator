package rahulstech.android.androidcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout mContainer;
    private EditText mEditText;

    private CalculatorKeyboard mKeyboard = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContainer = findViewById(R.id.container_edittext);
        mEditText = findViewById(R.id.calculator_edittext);
        mKeyboard = new CalculatorKeyboard(this,CalculatorKeyboard.calculateWindowSize(getWindow()));

        mContainer.setEndIconOnClickListener(v -> onClickCalculatorButton());
    }

    private void onClickCalculatorButton() {
        if (!mKeyboard.isShowing()) {
            // show keyboard
            mKeyboard.registerEditText(mEditText);
            mKeyboard.show(getWindow());
        }
        else {
            // hide keyboard
            mKeyboard.hide();
            mKeyboard.unregisterEditText();
        }
    }
}