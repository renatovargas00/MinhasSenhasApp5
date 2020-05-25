package br.usjt.devmobile.minhassenhasapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.room.Room;

import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import java.util.List;
import java.util.concurrent.ExecutionException;
import androidx.appcompat.widget.Toolbar;

public class ListasSenhasActivity extends AppCompatActivity {

    private static final String TAG = "ListaSenhasActivity";
    private List<Senha> listaSenhas;
    private ListView senhasListView;
    private ArrayAdapter <Senha> adapter;
    private AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_senhas);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = Room.databaseBuilder(this.getApplicationContext(),
                AppDatabase.class, "database-name").build();
        listaSenhas = geraListaSenhas();
        senhasListView = findViewById(R.id.senhasListView);

        adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaSenhas);
        senhasListView.setAdapter(adapter);
        senhasListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Senha senhaSelecionada = listaSenhas.get(position);
                Toast.makeText(ListasSenhasActivity.this,"Senha selecionada: "
                        +senhaSelecionada.getNome(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ListasSenhasActivity.this, DetalhesSenhaActivity.class);
                intent.putExtra("senha",senhaSelecionada);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista_senhas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_Ordem_Crescente:
                geraListaSenhasOrdemCrescente();
                break;
            case R.id.action_Ordem_Decrescente:
                geraListaSenhasOrdemDecrescente();
                break;
            case R.id.action_Ordem_Original:
                onResume();
                break;
            case R.id.action_busca:
                Intent intent = getIntent();
                if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                    String query = intent.getStringExtra(SearchManager.QUERY);
                    Log.d(TAG, query);
                }
                break;
            default:
                break;
        }

        return true;
    }


    @Override
    protected void onResume(){
        super.onResume();
        listaSenhas = geraListaSenhas();
        if(adapter != null) {
            adapter.clear();
            adapter.addAll(listaSenhas);
            adapter.notifyDataSetChanged();
        }
    }


    private void geraListaSenhasOrdemCrescente(){
        listaSenhas = geraListaSenhasCrescente();
        if(adapter != null) {
            adapter.clear();
            adapter.addAll(listaSenhas);
            adapter.notifyDataSetChanged();
        }
    }

    private void geraListaSenhasOrdemDecrescente(){
        listaSenhas = geraListaSenhasDecrescente();
        if(adapter != null) {
            adapter.clear();
            adapter.addAll(listaSenhas);
            adapter.notifyDataSetChanged();
        }
    }

    private void geraListaSenhasPesquisaPorNome(){
        listaSenhas = geraListaSenhasDecrescente();
        if(adapter != null) {
            adapter.clear();
            adapter.addAll(listaSenhas);
            adapter.notifyDataSetChanged();
        }
    }


    public void adicionarSenha(View v){
        Intent intent = new Intent(this,CadastroSenhaActivity.class);
        startActivity(intent);
    }



    public List<Senha> geraListaSenhas()  {

        List<Senha> lista = null;
        try {
            lista = new GetSenhasAsyncTask().execute().get();
        }catch (ExecutionException e1){
            e1.printStackTrace();
        }catch (InterruptedException e2){
            e2.printStackTrace();
        }
        return lista;
    }

    public List<Senha> geraListaSenhasCrescente()  {

        List<Senha> lista = null;
        try {
            lista = new GetSenhasAsyncTaskCrescente().execute().get();
        }catch (ExecutionException e1){
            e1.printStackTrace();
        }catch (InterruptedException e2){
            e2.printStackTrace();
        }
        return lista;
    }

    public List<Senha> geraListaSenhasDecrescente()  {

        List<Senha> lista = null;
        try {
            lista = new GetSenhasAsyncTaskDecrescente().execute().get();
        }catch (ExecutionException e1){
            e1.printStackTrace();
        }catch (InterruptedException e2){
            e2.printStackTrace();
        }
        return lista;
    }

    public List<Senha> geraListaPorNomePesquisado()  {

        List<Senha> lista = null;
        try {
            lista = new GetSenhasAsyncTaskDecrescente().execute().get();
        }catch (ExecutionException e1){
            e1.printStackTrace();
        }catch (InterruptedException e2){
            e2.printStackTrace();
        }
        return lista;
    }

    private class GetSenhasAsyncTask extends AsyncTask<Void, Void,List<Senha>> {
        @Override
        protected List<Senha> doInBackground(Void... url) {
            return db.senhaDao().getAll();
        }
    }


    private class GetSenhasAsyncTaskCrescente extends AsyncTask<Void, Void,List<Senha>> {
        @Override
        protected List<Senha> doInBackground(Void... url) {
            return db.senhaDao().getAllOrdemCrescente();
        }
    }

    private class GetSenhasAsyncTaskDecrescente extends AsyncTask<Void, Void,List<Senha>> {
        @Override
        protected List<Senha> doInBackground(Void... url) {
            return db.senhaDao().getAllOrdemDecrescente();
        }
    }

    private class GetSenhasAsyncTaskPesquisaNome extends AsyncTask<Void, Void,List<Senha>> {
        @Override
        protected List<Senha> doInBackground(Void... url) {
            return db.senhaDao().getAllOrdemDecrescente();
        }
    }


}