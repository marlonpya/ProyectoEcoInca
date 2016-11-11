package application.ucweb.proyectoecoinca.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import application.ucweb.proyectoecoinca.BuscarResultadoListaActivity;
import application.ucweb.proyectoecoinca.R;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.model.BuscarDetalle;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by ucweb02 on 12/10/2016.
 */
public class BuscarDetalleAdapter extends RealmBasedRecyclerViewAdapter<BuscarDetalle, BuscarDetalleAdapter.ViewHolder> {
    public BuscarDetalleAdapter(
            Context context,
            RealmResults<BuscarDetalle> realmResults,
            boolean automaticUpdate,
            boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
    }

    @Override
    public BuscarDetalleAdapter.ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.row_sector_industrial, viewGroup, false));
    }

    @Override
    public void onBindRealmViewHolder(BuscarDetalleAdapter.ViewHolder viewHolder, int i) {
        final BuscarDetalle item = realmResults.get(i);
        viewHolder.titulo.setText(item.getDescripcion());
        viewHolder.boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getContext().startActivity(new Intent(getContext(), BuscarResultadoListaActivity.class));
            }
        });
    }

    public class ViewHolder extends RealmViewHolder {
        @BindView(R.id.row_tv_titulo_sector_industrial) TextView titulo;
        @BindView(R.id.row_btn_ll_buscar_detalle) LinearLayout boton;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
