package application.ucweb.proyectoecoinca.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import application.ucweb.proyectoecoinca.MiPerfilEmpresaActivity;
import application.ucweb.proyectoecoinca.R;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.model.Empresa;
import application.ucweb.proyectoecoinca.model.EmpresaSerializable;
import application.ucweb.proyectoecoinca.util.Constantes;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ucweb03 on 09/02/2017.
 */

public class EmpresaBusquedaAdapter extends RecyclerView.Adapter<EmpresaBusquedaAdapter.ViewHolder> {
    private ArrayList<EmpresaSerializable> lista;
    private Context context;
    private LayoutInflater inflater;

    public EmpresaBusquedaAdapter(Context context, ArrayList<EmpresaSerializable> lista) {
        this.lista = lista;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.row_lista_buscar, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final EmpresaSerializable item = lista.get(position);
        BaseActivity.usarGlideCircular(context, item.getImagen(), viewHolder.imagen);
        viewHolder.nombre.setText(item.getNombre());
        viewHolder.boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MiPerfilEmpresaActivity.class)
                        .putExtra(Constantes.L_ID_EMPRESA, item.getId_server())
                        .putExtra(Constantes.EXTRA_IS_REAL, false));
            }
        });
        switch (item.getTipo_negocio()) {
            case Empresa.N_COMPRADOR    : viewHolder.icono_tipo_empresa.setColorFilter(viewHolder.comprador);   break;
            case Empresa.N_VENDEDOR     : viewHolder.icono_tipo_empresa.setColorFilter(viewHolder.vendedor);    break;
            case Empresa.N_AMBOS        : viewHolder.icono_tipo_empresa.setColorFilter(viewHolder.ambos);       break;
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.row_iv_imagen_lista_buscar)
        ImageView imagen;
        @BindView(R.id.row_iv_identificador_tipo_empresa) ImageView icono_tipo_empresa;
        @BindView(R.id.row_tv_titulo_lista_buscar)
        TextView nombre;
        @BindView(R.id.row_btn_ll_lista_buscar)
        LinearLayout boton;
        @BindColor(R.color.comprador) int comprador;
        @BindColor(R.color.vendedor) int vendedor;
        @BindColor(R.color.ambos) int ambos;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
