package cdbol.br.com.clubedabola.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cdbol.br.com.clubedabola.R;

public class CustomViewEditText extends ConstraintLayout {

    String attrHintText;
    int colorText;
    int attrType;

    View root;

    TextInputEditText customEditText;

    TextInputLayout customTextInput;

    View linhaIndicaoStatus;

    LinearLayout customLinearLayoutIndication;

    TextView textError;


    public CustomViewEditText(Context context) {
        super(context);
        initView(context);
    }

    public CustomViewEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        parseAttrs(context, attrs);
        setHint();
    }

    public CustomViewEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        parseAttrs(context, attrs);
        setHint();
    }

    private void initView(final Context context) {
        root = LayoutInflater.from(context).inflate(R.layout.custom_edittext_view, this);
        customEditText = (TextInputEditText) root.findViewById(R.id.custom_edit_text);
        linhaIndicaoStatus = (View) root.findViewById(R.id.custom_view_indication);
        customLinearLayoutIndication = (LinearLayout) root.findViewById(R.id.custom_linear_layout_indication);
        textError = (TextView) root.findViewById(R.id.text_error);
        customTextInput = (TextInputLayout) root.findViewById(R.id.text_input_custom);

        customEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    linhaIndicaoStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.gray_B5B5B5));
                    customEditText.setHint(attrHintText);
                } else {
                    linhaIndicaoStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.orange_clear));
                    customEditText.setHint("");

                }
            }
        });
    }

    private void parseAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.CustomViewEditText, 0, 0);
        attrHintText = typedArray.getString(R.styleable.CustomViewEditText_edt_hintText);
        colorText = typedArray.getColor(R.styleable.CustomViewEditText_edt_textColor, ContextCompat.getColor(context, R.color.gray_B5B5B5));
        attrType = typedArray.getInt(R.styleable.CustomViewEditText_android_inputType, 0);
    }

    private void setHint() {
        if (attrHintText != null) {
            customEditText.setHint(attrHintText);
            customTextInput.setHint(attrHintText);

        }

        customEditText.setTextColor(colorText);
        customEditText.setInputType(attrType);
    }

    public String getText() {
        return customEditText.getText().toString();
    }

    public TextInputEditText getCustomEditText(){

        return this.customEditText;
    }


}