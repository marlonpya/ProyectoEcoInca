package application.ucweb.proyectoecoinca;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import application.ucweb.proyectoecoinca.aplicacion.BaseActivity;
import application.ucweb.proyectoecoinca.util.Constantes;
import application.ucweb.proyectoecoinca.util.Preferencia;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import me.originqiu.library.EditTag;
import me.originqiu.library.MEditText;

public class RegistroActivity extends BaseActivity {
    public static final String TAG = RegistroActivity.class.getSimpleName();
    @BindView(R.id.include) Toolbar toolbar;
    @BindView(R.id.ivRegistroImagenSubir) ImageView imagen_subir;
    @BindView(R.id.icono) ImageView icono;
    @BindView(R.id.ll_btn__pais) LinearLayout btn_pais;
    @BindView(R.id.ll_btn_ciudad) LinearLayout btn_ciudad;

    @BindView(R.id.et_nombre_registro) EditText et_nombre;
    @BindView(R.id.et_apellido_registro) EditText et_apellido;
    @BindView(R.id.et_pais_registro) EditText et_pais;
    @BindView(R.id.et_ciudad_registro) EditText et_ciudad;
    @BindView(R.id.et_email_registro) EditText et_email;
    @BindView(R.id.et_anio_fundacion_registro) EditText et_anio_f;
    @BindView(R.id.et_descripcion_emp_registro) EditText et_descripcion_e;
    @BindView(R.id.et_sector_industrial_registro) EditText et_sec_industrial;
    @BindView(R.id.et_sector_producto_registro) EditTag et_producto;
    @BindView(R.id.et_sector_servicio_registro) EditText et_servicio;
    @BindView(R.id.et_certificado_registro) EditText et_certificado;
    @BindView(R.id.tv_titulo_producto) TextView titulo_producto;
    @BindView(R.id.met_editag) MEditText mEditText;
    @BindColor(R.color.celeste) int CELESTE;
    private static int VALOR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        iniciarLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        et_sec_industrial.setText(Preferencia.getIndustria(this));
        //et_producto.setText(Preferencia.getProducto(this));
        et_servicio.setText(Preferencia.getServicio(this));
        et_certificado.setText(Preferencia.getCertificado(this));
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (mEditText.getText().length() > 0) { titulo_producto.setTextColor(CELESTE); }
                else { titulo_producto.setTextColor(Color.parseColor("#FF808080")); }
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.ACTION_DOWN ||et_producto.hasFocus() || keyCode == KeyEvent.KEYCODE_DEL) {
            List<String> arraystr = et_producto.getTagList();
            Log.d(TAG, arraystr.toString());
            Log.d(TAG, "some");
        }
        return super.onKeyUp(keyCode, event);
    }

    @OnClick(R.id.btnSiguienteRegistro)
    public void siguienteRegistro() { startActivity(new Intent(this, PrincipalActivity.class)); }

    @OnClick(R.id.fabRegistroAgregarImagen)
    public void agregarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, VALOR);
    }

    @OnClick(R.id.btnProducto)
    public void irADetalleProducto() {
        //startActivity(new Intent(this, RegistroDetalleListaActivity.class).putExtra(Constantes.POSICION_I_DETALLE_BUSCAR, 1));
    }

    @OnClick(R.id.btnServicio)
    public void irADetalleServicio() {
        startActivity(new Intent(this, RegistroDetalleListaActivity.class).putExtra(Constantes.POSICION_I_DETALLE_BUSCAR, 2));
    }

    @OnClick(R.id.btnSectorIndustrial)
    public void irADetalleSectorIndustrial() {
        startActivity(new Intent(this, RegistroDetalleListaActivity.class).putExtra(Constantes.POSICION_I_DETALLE_BUSCAR, 4));
    }

    @OnClick(R.id.btnCertificado)
    public void irADetalleCertificado() {
        startActivity(new Intent(this, RegistroDetalleListaActivity.class).putExtra(Constantes.POSICION_I_DETALLE_BUSCAR, 6));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VALOR && resultCode == RESULT_OK && data !=null) {
            Uri image = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(image, filePath, null, null, null);
            cursor.moveToFirst();

            int index = cursor.getColumnIndex(filePath[0]);
            String picturePath = cursor.getString(index);
            cursor.close();
            imagen_subir.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    @OnClick(R.id.ll_btn__pais)
    public void dialogoPais() {
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(Constantes.getPaises(), -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        et_pais.setText(Constantes.getPaises()[which]);
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    @OnClick(R.id.ll_btn_ciudad)
    public void dialogoCiudad() {
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(Constantes.ARRAY_DEPARTAMENTOS, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        et_ciudad.setText(Constantes.ARRAY_DEPARTAMENTOS[which]);
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void iniciarLayout() {
        setToolbarSon(toolbar, this, getString(R.string.registrarse_min));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
