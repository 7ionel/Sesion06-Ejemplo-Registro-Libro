package com.example.semana06;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.semana06.entity.categoria;
import com.example.semana06.entity.libro;
import com.example.semana06.entity.pais;
import com.example.semana06.service.ServiceCategoria;
import com.example.semana06.service.ServiceLibro;
import com.example.semana06.service.ServicePais;
import com.example.semana06.util.ConnectionRest;
import com.example.semana06.util.FunctionUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    //Pais
    Spinner spnPais;
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> paises = new ArrayList<>();

    //Categoria
    Spinner spnCategoria;
    ArrayAdapter<String> adaptadorCategoria;
    ArrayList<String> categorias = new ArrayList<>();

    //Servicio
    ServiceLibro serviceLibro;
    ServicePais servicePais;
    ServiceCategoria serviceCategoriaLibro;

    Button btnRegistra;

    EditText txtTitulo, txtAnio, txtSerie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        adaptadorPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paises);
        spnPais = findViewById(R.id.spnRegLibPais);
        spnPais.setAdapter(adaptadorPais);

        adaptadorCategoria = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categorias);
        spnCategoria = findViewById(R.id.spnRegLibCategoria);
        spnCategoria.setAdapter(adaptadorCategoria);

        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceCategoriaLibro = ConnectionRest.getConnection().create(ServiceCategoria.class);
        serviceLibro = ConnectionRest.getConnection().create(ServiceLibro.class);

        cargaPais();
        cargaCategoria();

        txtTitulo = findViewById(R.id.txtRegLibTitulo);
        txtAnio = findViewById(R.id.txtRegLibAnio);
        txtSerie = findViewById(R.id.txtRegLibSerie);


        btnRegistra = findViewById(R.id.btnRegLibEnviar);
        btnRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = txtTitulo.getText().toString();
                String anio = txtAnio.getText().toString();
                String serie= txtSerie.getText().toString();
                String idPais = spnPais.getSelectedItem().toString().split(":")[0];
                String idCategoria = spnCategoria.getSelectedItem().toString().split(":")[0];

                pais objPais = new pais();
                objPais.setIdPais(Integer.parseInt(idPais.trim()));

                categoria objCategoria = new categoria();
                objCategoria.setIdCategoria(Integer.parseInt(idCategoria.trim()));

                libro objLibro = new libro();
                objLibro.setTitulo(titulo);
                objLibro.setAnio(Integer.parseInt(anio));
                objLibro.setSerie(serie);
                objLibro.setPais(objPais);
                objLibro.setCategoria(objCategoria);
                objLibro.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                objLibro.setEstado(1);

                registra(objLibro);
            }
        });


    }

    public void cargaPais() {
        Call<List<pais>> call = servicePais.listaTodos();
        call.enqueue(new Callback<List<pais>>() {
            @Override
            public void onResponse(Call<List<pais>> call, Response<List<pais>> response) {
                if (response.isSuccessful()){
                    List<pais> lst =  response.body();
                    for(pais obj: lst){
                        paises.add(obj.getIdPais() +":"+ obj.getNombre());
                    }
                    adaptadorPais.notifyDataSetChanged();
                }else{
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }

            @Override
            public void onFailure(Call<List<pais>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }
    public void cargaCategoria(){
        Call<List<categoria>> call = serviceCategoriaLibro.listaTodos();
        call.enqueue(new Callback<List<categoria>>() {
            @Override
            public void onResponse(Call<List<categoria>> call, Response<List<categoria>> response) {
                if (response.isSuccessful()){
                    List<categoria> lst =  response.body();
                    for(categoria obj: lst){
                        categorias.add(obj.getIdCategoria() +":"+obj.getDescripcion());
                    }
                    adaptadorCategoria.notifyDataSetChanged();
                }else{
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }
            @Override
            public void onFailure(Call<List<categoria>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> "+ t.getMessage());
            }
        });
    }

    void registra(libro obj){
        Call<libro> call = serviceLibro.registraLibro(obj);
        call.enqueue(new Callback<libro>() {
            @Override
            public void onResponse(Call<libro> call, Response<libro> response) {
                if (response.isSuccessful()){
                    libro objSalida = response.body();
                    mensajeAlert(" Registro de Libro exitoso:  "
                            + " \n >>>> ID >> " + objSalida.getIdLibro()
                            + " \n >>> Título >>> " +  objSalida.getTitulo());
                }
            }

            @Override
            public void onFailure(Call<libro> call, Throwable t) {

            }
        });
    }



    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }


    void mensajeToast(String mensaje){
        Toast toast1 =  Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }
}