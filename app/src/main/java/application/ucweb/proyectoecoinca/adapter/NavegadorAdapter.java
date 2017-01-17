package application.ucweb.proyectoecoinca.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Collections;
import java.util.List;

import application.ucweb.proyectoecoinca.R;
import application.ucweb.proyectoecoinca.model.ItemNavegador;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ucweb02 on 03/10/2016.
 */
public class NavegadorAdapter extends RecyclerView.Adapter<NavegadorAdapter.ViewHolder>{
    private List<ItemNavegador> lista = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public NavegadorAdapter(List<ItemNavegador> lista, Context context) {
        this.lista = lista;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.row_navegador, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ItemNavegador item = lista.get(position);
        holder.texto.setText(item.getTitulo());
        Glide.with(this.context)
                .load(item.getIcono())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .centerCrop()
                .into(holder.icono);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.row_iv_icono_navegador) ImageView icono;
        @BindView(R.id.row_tv_texto_navegador) TextView texto;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
