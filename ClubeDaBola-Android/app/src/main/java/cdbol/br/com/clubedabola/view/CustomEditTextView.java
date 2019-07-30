package cdbol.br.com.clubedabola.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.concrete.canarinho.validator.Validador;
import br.com.concrete.canarinho.watcher.MascaraNumericaTextWatcher;
import br.com.concrete.canarinho.watcher.evento.EventoDeValidacao;
import cdbol.br.com.clubedabola.R;

public class CustomEditTextView /*extends RelativeLayout*/ {

    /*String attrHintText;
    private String mask;

    private ArrayList<TextWatcher> mListener;

    private boolean openHint = false;

    TextView customTextHint;

    EditText customEditText;


    View customViewIndication;


    LinearLayout linearLayoutCustomView;

    TextView textError;

    ImageView imgIndication;


    ValidationEditText validationEditText = new ValidationEditText();

    public CustomEditTextView(Context context) {
        super(context);
        initView(context);
    }

    public CustomEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        parseAttrs(context, attrs);
    }

    public CustomEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        parseAttrs(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomEditTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
        parseAttrs(context, attrs);
    }

    private void initView(Context context) {

        final View root = LayoutInflater.from(context).inflate(R.layout.view_custom_edit_text, this);

        customTextHint = (TextView) root.findViewById(R.id.custom_text_hint);

        customEditText = (EditText) root.findViewById(R.id.custom_edit_text);

        customViewIndication = (View) root.findViewById(R.id.custom_view_indication);

        linearLayoutCustomView = (LinearLayout) root.findViewById(R.id.custom_linear_lsyout_indication);

        textError = (TextView) root.findViewById(R.id.text_error);
        imgIndication = (ImageView) root.findViewById(R.id.img_indication);

        customEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    customViewIndication.setBackgroundColor(getResources().getColor(R.color.orange_FF8C00));
                    linearLayoutCustomView.setVisibility(GONE);
                    imgIndication.setVisibility(GONE);
                    customTextHint.setVisibility(VISIBLE);
                } else {
                    customViewIndication.setBackgroundColor(getResources().getColor(R.color.gray_A9A9A9));
                    customTextHint.setVisibility(GONE);

                }
            }
        });


        customEditText.addTextChangedListener(new TextWatcher() {
          @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() > 0 && !openHint){

                    customTextHint.setVisibility(VISIBLE);
                    openHint = true;
                } else if (s.toString().length() == 0){
                    customTextHint.setVisibility(GONE);

                }
            }
        });


        configView();

    }





    private void configView() {
        if (attrHintText != null) {
            customTextHint.setText(attrHintText);
        }
    }

    public class ValidationEditText implements EventoDeValidacao {

        @Override
        public void invalido(String valorAtual, String mensagem) {

            setError();
        }

        @Override
        public void parcialmenteValido(String valorAtual) {
            setError();
        }

        @Override
        public void totalmenteValido(String valorAtual) {
            setSucess();
        }
    }


    public void setError() {
        customViewIndication.setBackgroundColor(getResources().getColor(R.color.default_error));
    }

    public void setSucess() {
        customViewIndication.setBackgroundColor(getResources().getColor(R.color.default_sucess));
    }

    private void parseAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.CustomEditTextView, 0, 0);
        attrHintText = typedArray.getString(R.styleable.CustomEditTextView_edt_hintText);
    }

    public void addTextMask(String mask, TextWatcher textWatcher, Validador validador) {
        this.mask = mask;

        addTextChangeListener(new MascaraNumericaTextWatcher.Builder()
                .paraMascara(mask)
                .comCallbackDeValidacao(validationEditText)
                .comValidador(validador)
                .build());

        addTextChangeListener(textWatcher);
    }

    public void addTextChangeListener(TextWatcher textWatcher) {

        if (mListener == null) {
            mListener = new ArrayList<>();
        }

        mListener.add(textWatcher);
        customEditText.addTextChangedListener(textWatcher);
    }

    public void setErrorView(String text) {
        textError.setError(text);
        linearLayoutCustomView.setVisibility(VISIBLE);
        imgIndication.setVisibility(VISIBLE);
    }
*/

}
