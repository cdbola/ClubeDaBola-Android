package cdbol.br.com.clubedabola.view;

import android.content.Context;
import android.media.Image;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import cdbol.br.com.clubedabola.R;
import cdbol.br.com.clubedabola.model.ChooseDataClass;
import cdbol.br.com.clubedabola.model.ItemGoalkeeper;
import cdbol.br.com.clubedabola.utils.CircleTransform;

public class ItemGoleiro extends LinearLayout {

    View root;
    TextView tv_name;
    ImageView img_goalkeeper;

    public ItemGoleiro(Context context) {
        super(context);
        initView(context);
    }

    public ItemGoleiro(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ItemGoleiro(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(final Context context) {
        root = LayoutInflater.from(context).inflate(R.layout.item_view_goleiro, this);
        tv_name = findViewById(R.id.txtDescricao);
        img_goalkeeper = findViewById(R.id.img_goalkeeper);
    }

    public void build(@NotNull ItemGoalkeeper choosed) {
        tv_name.setText(choosed.getNome());
        if (!choosed.getAvatar().isEmpty()){
            Picasso.get().load(choosed.getAvatar()).placeholder(R.drawable.ic_user).transform(new CircleTransform()).into(img_goalkeeper);
        }

    }
}
