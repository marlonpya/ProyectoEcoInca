package application.ucweb.proyectoecoinca.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import application.ucweb.proyectoecoinca.BuscarDetalleListaActivity;
import application.ucweb.proyectoecoinca.PlusActivity;
import application.ucweb.proyectoecoinca.R;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.model.Buscar;
import application.ucweb.proyectoecoinca.model.BuscarDetalle;
import application.ucweb.proyectoecoinca.model.Usuario;
import application.ucweb.proyectoecoinca.util.Constantes;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ucweb02 on 06/10/2016.
 */
public class BuscarAdapter extends RecyclerView.Adapter<BuscarAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Buscar> lista;
    private LayoutInflater inflater;

    public BuscarAdapter(Context context, ArrayList<Buscar> lista) {
        this.context = context;
        this.lista = lista;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.row_buscar, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Buscar item = lista.get(position);
        holder.titulo.setText(item.getNombre());
        BaseActivity.usarGlide(context, item.getIcono(), holder.imagen);
        BaseActivity.usarGlide(context, R.drawable.icono_buscar_derecho2, holder.flecha_derecha);
        holder.flecha_derecha.setColorFilter(Color.parseColor("#00b2e2"));
        if (position == 0) {
            holder.boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, BuscarDetalleListaActivity.class)
                            .putExtra(Constantes.POSICION_I_DETALLE_BUSCAR, BuscarDetalle.TIPO_EMPRESARIAL));
                }
            });
        } else {
            if (Usuario.getUsuario() != null ) {
                if (Usuario.getUsuario().isPlus()) {
                    new AlertDialog.Builder(context)
                            .setTitle(R.string.app_name)
                            .setMessage(R.string.m_usuario_no_plus)
                            .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    context.startActivity(new Intent(context, PlusActivity.class));
                                }
                            })
                            .setNegativeButton(R.string.cancelar, null)
                            .show();
                } else {
                    context.startActivity(new Intent(context, BuscarDetalleListaActivity.class)
                    .putExtra(Constantes.POSICION_I_DETALLE_BUSCAR, BuscarDetalle.PAIS));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.row_tv_titulo_buscar) TextView titulo;
        @BindView(R.id.row_iv_icono_buscar) ImageView imagen;
        @BindView(R.id.row_btn_ll_buscar) LinearLayout boton;
        @BindView(R.id.iv_imagen_flecha_derecha) ImageView flecha_derecha;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
