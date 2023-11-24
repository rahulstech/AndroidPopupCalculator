package rahulstech.android.androidcalculator;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.util.Size;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.MathContext;

import androidx.annotation.NonNull;

@SuppressWarnings({"unused","FieldMayBeFinal"})
public class CalculatorKeyboard extends PopupWindow implements View.OnClickListener {

    private static final String TAG = "CalculatorKeyboard";

    private static final String ADDITION = "+";

    private static final String SUBTRACTION = "−";

    private static final String DIVISION = "÷";

    private static final String MULTIPLICATION = "×";

    private static final String PERCENTAGE = "%";

    private static final String EQUAL = "=";

    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    @SuppressWarnings("FieldCanBeLocal")
    private TextView mEqnPreview;
    private Button btnClear;
    private Button btnToggleSign;
    private Button btnPercent;
    private Button btnDel;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnDivision;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btnMultiplication;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btnSubtraction;
    private Button btnPeriod;
    private Button btn0;
    private Button btnEqual;
    private Button btnAddition;

    private EditText mEditText;
    private int mCachedInputType;
    private PreviewText mPreviewText;
    private BigDecimal number1 = null;
    private String operation = null;

    @NonNull
    public static Size calculateWindowSize(@NonNull Window window) {
        int height = window.getWindowManager().getDefaultDisplay().getHeight();
        int width = window.getWindowManager().getDefaultDisplay().getWidth();
        return new Size(width,height);
    }

    public CalculatorKeyboard(@NonNull Context context, @NonNull Size maxDimension) {
        super(context, null, R.style.Calculator);

        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.layout_calculator_keyboard, null);
        contentView.measure(View.MeasureSpec.makeMeasureSpec(maxDimension.getWidth(), View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec((int) (maxDimension.getHeight()*.45f), View.MeasureSpec.UNSPECIFIED));
        setContentView(contentView);

        int windowWidth = maxDimension.getWidth();
        int windowHeight = contentView.getMeasuredHeight();

        setWidth(windowWidth);
        setHeight(windowHeight);
        setAnimationStyle(android.R.style.Animation_InputMethod);

        mEqnPreview = contentView.findViewById(R.id.equation_preview);
        btnClear = contentView.findViewById(R.id.btnClear);
        btnToggleSign = contentView.findViewById(R.id.btnToggleSign);
        btnPercent = contentView.findViewById(R.id.btnPercentage);
        btnDel = contentView.findViewById(R.id.btnDel);
        btn7 = contentView.findViewById(R.id.btn7);
        btn8 = contentView.findViewById(R.id.btn8);
        btn9 = contentView.findViewById(R.id.btn9);
        btnDivision = contentView.findViewById(R.id.btnDivision);
        btn4 = contentView.findViewById(R.id.btn4);
        btn5 = contentView.findViewById(R.id.btn5);
        btn6 = contentView.findViewById(R.id.btn6);
        btnMultiplication = contentView.findViewById(R.id.btnMultiplication);
        btn1 = contentView.findViewById(R.id.btn1);
        btn2 = contentView.findViewById(R.id.btn2);
        btn3 = contentView.findViewById(R.id.btn3);
        btnSubtraction = contentView.findViewById(R.id.btnSubtraction);
        btnPeriod = contentView.findViewById(R.id.btnPeriod);
        btn0 = contentView.findViewById(R.id.btn0);
        btnEqual = contentView.findViewById(R.id.btnEqual);
        btnAddition = contentView.findViewById(R.id.btnAddition);
        mPreviewText = new PreviewText(mEqnPreview);

        btnClear.setOnClickListener(this);
        btnToggleSign.setOnClickListener(this);
        btnPercent.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnDivision.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btnMultiplication.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btnSubtraction.setOnClickListener(this);
        btnPeriod.setOnClickListener(this);
        btn0.setOnClickListener(this);
        btnEqual.setOnClickListener(this);
        btnAddition.setOnClickListener(this);
    }

    public void registerEditText(@NonNull EditText editText) {
        this.mEditText = editText;
    }

    public void unregisterEditText() {
        this.mEditText = null;
    }

    public void show(@NonNull Window window) {
        checkRegisteredEditTextOrThrow();
        View parent = window.getDecorView();
        int gravity = Gravity.BOTTOM|Gravity.FILL_HORIZONTAL;
        showAtLocation(parent,gravity,0,0);
        mCachedInputType = mEditText.getInputType();
        mEditText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        number1 = extractBigDecimal();
    }

    public void hide() {
        checkRegisteredEditTextOrThrow();
        mEditText.setInputType(mCachedInputType);
        dismiss();
        reset();
    }

    @Override
    public void onClick(View v) {
        if (v == btnClear) {
            clearText();
        }
        else if (v == btnToggleSign) {
            toggleSign();
        }
        else if (v == btnPercent) {
            performOperation(PERCENTAGE);
        }
        else if (v == btnDel) {
            deleteText();
        }
        else if (v == btn7) {
            appendText("7");
        }
        else if (v == btn8) {
            appendText("8");
        }
        else if (v == btn9) {
            appendText("9");
        }
        else if (v == btnDivision) {
            performOperation(DIVISION);
        }
        else if (v == btn4) {
            appendText("4");
        }
        else if (v == btn5) {
            appendText("5");
        }
        else if (v == btn6) {
            appendText("6");
        }
        else if (v == btnMultiplication) {
            performOperation(MULTIPLICATION);
        }
        else if (v == btn1) {
            appendText("1");
        }
        else if (v == btn2) {
            appendText("2");
        }
        else if (v == btn3) {
            appendText("3");
        }
        else if (v == btnSubtraction) {
            performOperation(SUBTRACTION);
        }
        else if (v == btnPeriod) {
            appendText(".");
        }
        else if (v == btn0) {
            appendText("0");
        }
        else if (v == btnEqual) {
            calculate();
        }
        else if (v == btnAddition) {
            performOperation(ADDITION);
        }
    }

    private void clearText() {
        checkRegisteredEditTextOrThrow();
        Editable mText = mEditText.getText();
        if (mText.length() > 0) {
            mText.clear();
            return;
        }
        reset();
    }

    private void reset() {
        mPreviewText.clear();
        number1 = null;
        operation = null;
    }

    private void toggleSign() {
        checkRegisteredEditTextOrThrow();
        Editable mText = mEditText.getText();
        if (0 == mText.length()) return;
        char firstChar = mText.charAt(0);
        if ('-' != firstChar) {
            mText.insert(0,"-");
        }
        else {
            mText.delete(0,1);
        }
    }

    private void appendText(String text) {
        checkRegisteredEditTextOrThrow();
        Editable mText = mEditText.getText();
        mText.append(text);
    }

    private void deleteText() {
        checkRegisteredEditTextOrThrow();
        Editable mText = mEditText.getText();
        if (0 == mText.length()) return;
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        if (selectionEnd > selectionStart) {
            mText.delete(selectionStart,selectionEnd);
        }
        else if (selectionStart > 0) {
            mText.delete(selectionStart-1,selectionStart);
        }
    }

    private BigDecimal extractBigDecimal() {
        Editable mText = mEditText.getText();
        String value = mText.toString();
        if (value.isEmpty()) {
            return null;
        }
        return new BigDecimal(value);
    }

    private BigDecimal performOperation(String what) {
        checkRegisteredEditTextOrThrow();
        Editable mText = mEditText.getText();
        BigDecimal number = extractBigDecimal();
        mText.clear();
        if (null == number){
            return number1;
        }
        if (null == operation) {
            number1 = number;
            operation = what;
            mPreviewText.clear().append(number1.toPlainString()).append(operation);
            return null;
        }
        number1 = calculateValue(number1,operation,number);
        operation = what;
        if (EQUAL.equals(what)) {
            mPreviewText.append(number.toPlainString()).append(EQUAL).append(number1.toPlainString());
        }
        else {
            mPreviewText.clear().append(number1.toPlainString()).append(operation);
        }
        return number1;
    }

    private void calculate() {
        checkRegisteredEditTextOrThrow();
        BigDecimal result = performOperation(EQUAL);
        Editable mText = mEditText.getText();
        if (null != result) {
            mText.append(result.toPlainString());
        }
    }

    private BigDecimal calculateValue(BigDecimal left, String operation, BigDecimal right) {
        if (ADDITION.equals(operation)) {
            return left.add(right);
        }
        else if (SUBTRACTION.equals(operation)) {
            return left.subtract(right);
        }
        else if (MULTIPLICATION.equals(operation)) {
            return left.multiply(right);
        }
        else if (DIVISION.equals(operation)) {
            return left.divide(right,MathContext.DECIMAL64);
        }
        else if (PERCENTAGE.equals(operation)) {
            return left.multiply(right).divide(HUNDRED, MathContext.DECIMAL64);
        }
        else if (EQUAL.equals(operation)) {
            return left;
        }
        else {
            // this should never happen
            throw new UnsupportedOperationException("mathematical operation \""+operation+"\" not implemented");
        }
    }

    private void checkRegisteredEditTextOrThrow() {
        if (null == mEditText) {
            throw new IllegalStateException("no EditText is registered via CalculatorKeyboard#registeredEditText method");
        }
    }

    private static class PreviewText implements CharSequence {

        private final TextView mTextView;
        private StringBuilder buff = new StringBuilder();

        public PreviewText(@NonNull TextView textView) {
            mTextView = textView;
        }

        public PreviewText append(CharSequence text) {
            buff.append(text);
            mTextView.setText(this);
            return this;
        }

        @Override
        public int length() {
            return buff.length();
        }

        @Override
        public char charAt(int index) {
            return buff.charAt(index);
        }

        @NonNull
        @Override
        public CharSequence subSequence(int start, int end) {
            return buff.subSequence(start,end);
        }

        public PreviewText clear() {
            buff = new StringBuilder();
            mTextView.setText(null);
            return this;
        }

        @NonNull
        @Override
        public String toString() {
            return buff.toString();
        }
    }
}
