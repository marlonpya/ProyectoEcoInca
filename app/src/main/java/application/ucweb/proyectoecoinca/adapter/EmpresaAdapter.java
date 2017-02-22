package application.ucweb.proyectoecoinca.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import application.ucweb.proyectoecoinca.MiPerfilEmpresaActivity;
import application.ucweb.proyectoecoinca.R;
import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.aplicacion.Configuracion;
import application.ucweb.proyectoecoinca.model.Empresa;
import application.ucweb.proyectoecoinca.util.ConexionBroadcastReceiver;
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
public class EmpresaAdapter extends RealmBasedRecyclerViewAdapter<Empresa, EmpresaAdapter.ViewHolder> {
    private static final String TAG = EmpresaAdapter.class.getSimpleName();

    public EmpresaAdapter(
            Context context,
            RealmResults<Empresa> realmResults) {
        super(context, realmResults, true, true);
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.row_empresa, viewGroup, false));
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
                .putExtra(Constantes.L_ID_EMPRESA, (int) item.getId())
                .putExtra(Constantes.EXTRA_IS_REAL, true));
            }
        });
        viewHolder.boton_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.app_name)
                        .setMessage(getContext().getString(R.string.eliminar))
                        .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestIgnorar(item.getId_match(), item.getId_server());
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(R.string.cancelar, null)
                        .show();
            }
        });
        switch (item.getTipo_negocio()) {
            case Empresa.N_COMPRADOR: viewHolder.indicador.setColorFilter(viewHolder.comprador);    break;
            case Empresa.N_VENDEDOR: viewHolder.indicador.setColorFilter(viewHolder.vendedor);      break;
            case Empresa.N_AMBOS: viewHolder.indicador.setColorFilter(viewHolder.ambos);            break;
        }
    }

    public class ViewHolder extends RealmViewHolder {
        @BindView(R.id.row_iv_imagen_empresa) ImageView imagen;
        @BindView(R.id.row_tv_titulo_empresa) TextView titulo;
        @BindView(R.id.row_tv_btn_ver_pefil) TextView boton;
        @BindView(R.id.row_tv_btn_ignorar) TextView boton_eliminar;
        @BindView(R.id.row_iv_empresa_indicador) ImageView indicador;
        @BindColor(R.color.comprador) int comprador;
        @BindColor(R.color.vendedor) int vendedor;
        @BindColor(R.color.ambos) int ambos;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void requestIgnorar(final int id_match, final int id_empresa) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(R.string.app_name);
        progressDialog.setMessage(getContext().getString(R.string.m_actualizando));

        if (ConexionBroadcastReceiver.isConnected()) {
            progressDialog.show();
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    Constantes.URL_IGNORAR_EMPRESA,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Log.d(TAG, s);
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                //JSONObject jData = jsonObject.getJSONObject("data");
                                if (jsonObject.getBoolean("status")) {
                                    Empresa.eliminarEmpresa(id_empresa);
                                    notifyDataSetChanged();
                                    new AlertDialog.Builder(getContext())
                                            .setMessage(R.string.app_name)
                                            .setMessage(getContext().getString(R.string.m_ignorado_ok))
                                            .setPositiveButton(R.string.aceptar, null)
                                            .show();
                                }
                                progressDialog.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            VolleyLog.d(volleyError.toString());
                            progressDialog.dismiss();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("idmatch", String.valueOf(id_match));
                    return params;
                }
            };
            Configuracion.getInstance().addToRequestQueue(request, TAG);
        }
    }
}
