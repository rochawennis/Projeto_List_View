package com.example.projetolistview;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    EditText nome, sobrenome, curso;
    Button btAdicionar;
    ListView listViewPessoas;
    ArrayList<Pessoa> listaPessoas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = findViewById(R.id.editTextNome);
        sobrenome = findViewById(R.id.editTextSobrenome);
        curso = findViewById(R.id.editTextCurso);
        btAdicionar = findViewById(R.id.buttonAdicionar);
        listViewPessoas = findViewById(R.id.listViewPessoas);

        // Configurando a ListView (Lista de Objetos)
        final ArrayAdapter<Pessoa> adaptador = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                listaPessoas);

        listViewPessoas.setAdapter(adaptador);

        btAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaPessoas.add(
                        new Pessoa(
                                nome.getText().toString(),
                                sobrenome.getText().toString(),
                                curso.getText().toString())
                );

                adaptador.notifyDataSetChanged();

                // Ordenando a listView dinamicamente
                Collections.sort(listaPessoas, new OrdenaPorNome());

                nome.setText("");
                sobrenome.setText("");
                curso.setText("");
                nome.requestFocus();

                // clicando na lista e visualizando os dados com o Toast
                listViewPessoas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Pessoa p = (Pessoa) listViewPessoas.getItemAtPosition(position);

                        adaptador.notifyDataSetChanged();

                        Toast.makeText(MainActivity.this, p.getDados(), Toast.LENGTH_SHORT).show();
                    }
                });
                listViewPessoas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {

                        Pessoa p = (Pessoa) listViewPessoas.getItemAtPosition(position);

                        AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                        dlg.setTitle("Remover da Lista?");
                        dlg.setMessage(p.toString());

                        dlg.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listaPessoas.remove((Pessoa) listViewPessoas.getItemAtPosition(position));
                                adaptador.notifyDataSetChanged();
                            }
                        });

                        dlg.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        dlg.setNeutralButton("Verificar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Pessoa p = (Pessoa) listViewPessoas.getItemAtPosition(position);
                                Intent it = new Intent(getApplicationContext(), Resultado.class);
                                it.putExtra("pessoa", p);
                                startActivity(it);
                            }
                        });
                        dlg.create().show();
                        return false;
                    }
                });
            }
        });
    }
}