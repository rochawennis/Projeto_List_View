package com.example.projetolistview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

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

        btAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaPessoas.add(
                        new Pessoa(
                                nome.getText().toString(),
                                sobrenome.getText().toString(),
                                curso.getText().toString())
                );

                // Ordenando a listView dinamicamente

                nome.setText("");
                sobrenome.setText("");
                curso.setText("");
                nome.requestFocus();
            }
        });

        // clicando na lista e visualizando os dados com o Toast
    }
}