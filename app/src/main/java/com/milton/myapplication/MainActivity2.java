package com.milton.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import models.persona;

public class MainActivity2 extends AppCompatActivity {

    private List<persona> listpersona = new ArrayList<persona>();
    ArrayAdapter<persona>arrayAdapterP;

    EditText nom , ape , cor , celu ,dirre;
    ListView list;


     FirebaseDatabase basedatos;
     DatabaseReference referenciadatos;
     FirebaseAuth salir;
     persona seleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        nom = findViewById(R.id.txtnombre);
        ape = findViewById(R.id.txtapellido);
        cor = findViewById(R.id.txtcorreo);
        celu = findViewById(R.id.txtcelular);
        dirre = findViewById(R.id.txtdireccion);
        list = findViewById(R.id.listview);

        inicializar();
        listardatos();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                seleccionado = (persona) parent.getItemAtPosition(i);
                nom.setText(seleccionado.getNombre());
                ape.setText(seleccionado.getApellido());
                cor.setText(seleccionado.getCorreo());
                celu.setText(seleccionado.getCelular());
                dirre.setText(seleccionado.getDirrecion());
            }
        });

    }

    private void listardatos()
    {
        referenciadatos.child("persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
             listpersona.clear();
             for (DataSnapshot objSnap : snapshot.getChildren())
             {
                 persona p = objSnap.getValue(persona.class);
                 listpersona.add(p);

                 arrayAdapterP = new ArrayAdapter<persona>(MainActivity2.this, android.R.layout.simple_list_item_1, listpersona);
                 list.setAdapter(arrayAdapterP);
             }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    public void inicializar()
    {
        FirebaseApp.initializeApp(this);
        basedatos = FirebaseDatabase.getInstance();
        // basedatos.setPersistenceEnabled(true);
        referenciadatos = basedatos.getReference();
        salir = FirebaseAuth.getInstance();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nav,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String nombre = nom.getText().toString();
        String apellido = ape.getText().toString();
        String direccion = dirre.getText().toString();
        String celular = celu.getText().toString();
        String correo = cor.getText().toString();

        switch(item.getItemId())
        {

            case R.id.icon_add:

                if(nombre.equals(""))
                {
                    nom.setError("El nombre es requerrido");
                }else if(apellido.equals(""))
                {
                    ape.setError("El apellido es requerrido");
                }
                else if(direccion.equals(""))
                {
                    dirre.setError("El dirrecion es requerrido");
                }else if(celular.equals(""))
                {
                    celu.setError("El celular es requerrido");
                }else if(correo.equals(""))
                {
                    cor.setError("El correo es requerrido");
                }else
                {
                    persona p = new persona();
                    p.setId(UUID.randomUUID().toString());
                    p.setNombre(nombre);
                    p.setApellido(apellido);
                    p.setCorreo(correo);
                    p.setDirrecion(direccion);
                    p.setCelular(celular);
                    referenciadatos.child("persona").child(p.getId()).setValue(p);
                    limpiarcajas();
                    Toast.makeText(getBaseContext(), "Añadir", Toast.LENGTH_SHORT).show();

                }

                break;

            case R.id.icon_save: {
                persona p = new persona();
                p.setId(seleccionado.getId());
                p.setNombre(nom.getText().toString().trim());
                p.setApellido(ape.getText().toString().trim());
                p.setCorreo(cor.getText().toString().trim());
                p.setDirrecion(dirre.getText().toString().trim());
                p.setCelular(celu.getText().toString().trim());
                referenciadatos.child("persona").child(p.getId()).setValue(p);


                Toast.makeText(getBaseContext(), "Se ha editado", Toast.LENGTH_SHORT).show();
            }
                break;

            case R.id.icon_delete: {
                persona p = new persona();
                p.setId(seleccionado.getId());
                referenciadatos.child("persona").child(p.getId()).removeValue();
                Toast.makeText(getBaseContext(), "Borrado", Toast.LENGTH_SHORT).show();
                limpiarcajas();
            }
                    break;
            case R.id.icon_singout:

                salir.signOut();
                Intent i = new Intent(MainActivity2.this,MainActivity.class);
                        startActivity(i);
                Toast.makeText(getBaseContext(), "Salio de sección", Toast.LENGTH_SHORT).show();

                break;
        }
        return true;
    }

    public void limpiarcajas()
    {
        nom.setText("");
        cor.setText("");
        ape.setText("");
        celu.setText("");
        dirre.setText("");
    }

}