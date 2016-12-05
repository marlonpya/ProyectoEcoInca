package application.ucweb.proyectoecoinca.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
 * Created by ucweb02 on 06/10/2016.
 */
public class EmpresaResultadoAdapter extends RealmBasedRecyclerViewAdapter<Empresa, EmpresaResultadoAdapter.ViewHolder> {

    public EmpresaResultadoAdapter(
            Context context,
            RealmResults<Empresa> realmResults,
            boolean automaticUpdate,
            boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.row_lista_buscar, viewGroup, false));
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int i) {
        final Empresa item = realmResults.get(i);
        BaseActivity.usarGlideCircular(getContext(), item.getImagen(), viewHolder.imagen);
        viewHolder.nombre.setText(item.getNombre());
        viewHolder.boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), MiPerfilEmpresaActivity.class)
                .putExtra(Constantes.L_ID_EMPRESA, item.getId()));
            }
        });
        switch (item.getTipo_negocio()) {
            case Empresa.N_COMPRADOR    : viewHolder.icono_tipo_empresa.setColorFilter(viewHolder.comprador);   break;
            case Empresa.N_VENDEDOR     : viewHolder.icono_tipo_empresa.setColorFilter(viewHolder.vendedor);    break;
            case Empresa.N_AMBOS        : viewHolder.icono_tipo_empresa.setColorFilter(viewHolder.ambos);       break;
        }

    }

    public class ViewHolder extends RealmViewHolder {
        @BindView(R.id.row_iv_imagen_lista_buscar) ImageView imagen;
        @BindView(R.id.row_iv_identificador_tipo_empresa) ImageView icono_tipo_empresa;
        @BindView(R.id.row_tv_titulo_lista_buscar) TextView nombre;
        @BindView(R.id.row_btn_ll_lista_buscar) LinearLayout boton;
        @BindColor(R.color.comprador) int comprador;
        @BindColor(R.color.vendedor) int vendedor;
        @BindColor(R.color.ambos) int ambos;
        ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
