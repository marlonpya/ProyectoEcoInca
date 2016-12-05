package application.ucweb.proyectoecoinca.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import application.ucweb.proyectoecoinca.MiPerfilActivity;
import application.ucweb.proyectoecoinca.MiPerfilEmpresaActivity;
import application.ucweb.proyectoecoinca.R;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.model.Empresa;
import application.ucweb.proyectoecoinca.util.Constantes;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by ucweb02 on 24/10/2016.
 */
public class MisContactosAdapter extends RealmBasedRecyclerViewAdapter<Empresa, MisContactosAdapter.ViewHolder> {
    public MisContactosAdapter(
            Context context,
            RealmResults<Empresa> realmResults,
            boolean automaticUpdate,
            boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.row_contacto, viewGroup, false));
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int i) {
        final Empresa item = realmResults.get(i);
        BaseActivity.usarGlideCircular(getContext(), item.getImagen(), viewHolder.imagen);
        viewHolder.titulo.setText(item.getNombre());
        viewHolder.boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), MiPerfilEmpresaActivity.class)
                .putExtra(Constantes.L_ID_EMPRESA, item.getId()));
            }
        });
        switch (item.getTipo_negocio()) {
            case Empresa.N_COMPRADOR    : viewHolder.indicador.setColorFilter(viewHolder.comprador);    break;
            case Empresa.N_VENDEDOR     : viewHolder.indicador.setColorFilter(viewHolder.vendedor);     break;
            case Empresa.N_AMBOS        : viewHolder.indicador.setColorFilter(viewHolder.ambos);        break;
        }
    }

    public class ViewHolder extends RealmViewHolder {
        @BindView(R.id.row_iv_imagen_empresa) ImageView imagen;
        @BindView(R.id.row_tv_titulo_empresa) TextView titulo;
        @BindView(R.id.row_tv_btn_ver_pefil_contacto) TextView boton;
        @BindView(R.id.row_iv_contacto_indicador) ImageView indicador;
        @BindColor(R.color.comprador) int comprador;
        @BindColor(R.color.vendedor) int vendedor;
        @BindColor(R.color.ambos) int ambos;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
