package cdbol.br.com.clubedabola.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cdbol.br.com.clubedabola.R;

public class ItemViewLabel extends LinearLayout {

    View root;
    TextView txtDescricao;
    String attrHintText;


    public ItemViewLabel(Context context) {
        super(context);
        initView(context);
    }

    public ItemViewLabel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        parseAttrs(context, attrs);
        setHint();

    }

    public ItemViewLabel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        parseAttrs(context, attrs);
        setHint();

    }

    public void build(String valor){
        txtDescricao.setText(valor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            txtDescricao.setTextColor(getContext().getResources().getColor(R.color.black_000000,getContext().getTheme()));
        }else{
            txtDescricao.setTextColor(getContext().getResources().getColor(R.color.black_000000));
        }
    }

    public void initView(final Context context) {
        root = LayoutInflater.from(context).inflate(R.layout.item_view_label, this);
        txtDescricao = root.findViewById(R.id.txtDescricao);
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
}