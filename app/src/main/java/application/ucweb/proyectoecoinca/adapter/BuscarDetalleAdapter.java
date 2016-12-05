package application.ucweb.proyectoecoinca.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import application.ucweb.proyectoecoinca.R;
import application.ucweb.proyectoecoinca.model.BuscarDetalle;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by ucweb02 on 13/10/2016.
 */
public class BuscarDetalleAdapter extends RealmBasedRecyclerViewAdapter<BuscarDetalle, BuscarDetalleAdapter.ViewHolder> {
    public static final String TAG = BuscarDetalleAdapter.class.getSimpleName();
    public BuscarDetalleAdapter(
            Context context,
            RealmResults<BuscarDetalle> realmResults,
            boolean automaticUpdate,
            boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.row_sector_industrial, viewGroup, false));
    }

    @Override
    public void onBindRealmViewHolder(final ViewHolder viewHolder, int i) {
        final BuscarDetalle item = realmResults.get(i);
        viewHolder.titulo.setText(item.getDescripcion());
        if (item.isSeleccionado()) {
            viewHolder.check.setChecked(true);
        } else {
            viewHolder.check.setChecked(false);
        }
        viewHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                item.setId(item.getId());
                if (viewHolder.check.isChecked()) {
                    item.setSeleccionado(true);
                } else {
                    item.setSeleccionado(false);
                }
                realm.commitTransaction();
                Log.d(TAG, item.toString());
            }
        });
    }

    public class ViewHolder extends RealmViewHolder {
        @BindView(R.id.cb_sector_industrial) CheckBox check;
        @BindView(R.id.row_tv_titulo_sector_industrial) TextView titulo;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
