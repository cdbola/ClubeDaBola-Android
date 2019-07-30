package cdbol.br.com.clubedabola.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cdbol.br.com.clubedabola.R;
import cdbol.br.com.clubedabola.utils.MaskUtils;
import cdbol.br.com.clubedabola.utils.ViewUtils;

public class ItemCartao extends LinearLayout {

    TextView txtValorPartida;
    View root;
    View lineCard;

    public ItemCartao(Context context) {
        super(context);
        initView(context);
    }

    public ItemCartao(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ItemCartao(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(final Context context) {
        root = LayoutInflater.from(context).inflate(R.layout.item_informacao_cartao, this);
        txtValorPartida = (TextView) root.findViewById(R.id.txt_valor_partida);
        lineCard = (View) root.findViewById(R.id.line_card);
        lineCard.setVisibility(GONE);
        //build();
    }

    public void build(String value) {
        txtValorPartida.setText(ViewUtils.formatValueCurrency(Double.valueOf(value)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            txtValorPartida.setTextColor(getContext().getResources().getColor(R.color.black_000000, getContext().getTheme()));
        } else {
            txtValorPartida.setTextColor(getContext().getResources().getColor(R.color.black_000000));
        }
    }
}
