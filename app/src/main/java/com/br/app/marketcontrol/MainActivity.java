package com.br.app.marketcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText campoNome, campoValor, campoQuantidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void botaoSalvarProduto(View view) {

        metodoSalvarProdutos();

    }


    public void inicializarComponentes() {

        campoNome = findViewById(R.id.txtNomeProduto);
        campoValor = findViewById(R.id.txtValorProduto);
        campoQuantidade = findViewById(R.id.txtQuantidadeProduto);

    }


    public void metodoSalvarProdutos() {

        /*Instanciar os valores em  uma string pra pegar dados dos campos de texto*/

        final String nome = campoNome.getText().toString().trim();
        final String valor = campoValor.getText().toString().trim();
        final String quantidade = campoQuantidade.getText().toString().trim();
        /*--------------------------------FIM-------------------------------------*/

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando...");

        /*------------------------------------------------------------------------*/

        if (nome.isEmpty()) {
            Toast.makeText(this, "Insira nome do produto", Toast.LENGTH_LONG).show();
            return;
        } else if (valor.isEmpty()) {
            Toast.makeText(this, "Insira o valor do produto", Toast.LENGTH_LONG).show();
            return;
        } else if (quantidade.isEmpty()) {
            Toast.makeText(this, "Insira uma quantidade para o produto", Toast.LENGTH_LONG);
            return;
        }
        /*------------------------------------------------------------------------*/

        /*Caso nenhum dos camopos estejam vazios execulte a regra abaixo*/

        else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "https://miniproje.000webhostapp.com/insert.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equalsIgnoreCase("Produto inserido com sucesso")) {

                                Toast.makeText(MainActivity.this, "Produto inserido com sucesso", Toast.LENGTH_LONG).show();

                                progressDialog.dismiss();
                                /*ProgressDialog encerra aqui*/
                            } else {
                                Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();

                                progressDialog.dismiss();
                                /*ProgressDialog encerra aqui*/
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    progressDialog.dismiss();
                    /*ProgressDialog encerra aqui*/

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> parametroDadosSalvar = new HashMap<String, String>();

                    parametroDadosSalvar.put("nome",nome);
                    parametroDadosSalvar.put("valor",valor);
                    parametroDadosSalvar.put("quantidade",quantidade);



                    return parametroDadosSalvar;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(request);
            /*Commit*/

        }

    }

}
