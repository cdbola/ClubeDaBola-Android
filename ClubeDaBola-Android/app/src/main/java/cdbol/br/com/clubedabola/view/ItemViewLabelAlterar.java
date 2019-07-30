package cdbol.br.com.clubedabola.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cdbol.br.com.clubedabola.R;

public class ItemViewLabelAlterar extends LinearLayout {

    View root;
    TextView txtDescricao;
    String attrHintText;
    TextView txtAlterar;
    public ItemViewLabelAlterar(Context context) {
        super(context);
        initView(context);
    }

    public ItemViewLabelAlterar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ItemViewLabelAlterar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(final Context context) {
        root = LayoutInflater.from(context).inflate(R.layout.item_label_alterar, this);
        txtDescricao = root.findViewById(R.id.txtDescricao);
        txtAlterar = root.findViewById(R.id.txtAlterar);
    }

    private void parseAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.ItemViewLabel, 0, 0);
        attrHintText = typedArray.getString(R.styleable.ItemViewLabel_item_hintText);
    }

    private void setHint() {
        if (attrHintText != null) {
            txtDescricao.setText(attrHintText);

        }
    }

    public void build(String valor){
        if(valor.equalsIgnoreCase("G")) {
            txtDescricao.setText("Goleiro");
        }
        if(valor.equalsIgnoreCase("A")) {
            txtDescricao.setText("Árbitro");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            txtDescricao.setTextColor(getContext().getResources().getColor(R.color.black_000000,getContext().getTheme()));
        } else {
            txtDescricao.setTextColor(getContext().getResources().getColor(R.color.black_000000));

        }
    }
}
