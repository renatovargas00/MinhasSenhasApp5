package br.usjt.devmobile.minhassenhasapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DetalhesSenhaActivity extends AppCompatActivity {

    public Senha senha;
    private TextView textViewNome;
    private TextView textViewUsuario;
    private TextView textViewSenha1;
    private TextView textViewUrl;
    private TextView textViewObservacao;
    private AppDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_senha);
        db = Room.databaseBuilder(this.getApplicationContext(),
                AppDatabase.class, "database-name").build();
        Intent intent = getIntent();
        senha = (Senha)intent.getSerializableExtra("senha");
        textViewNome = findViewById(R.id.textViewNomeValue);
        textViewUsuario = findViewById(R.id.textViewUsuarioValue);
        textViewSenha1 = findViewById(R.id.textViewSenha1Value);
        textViewUrl = findViewById(R.id.textViewUrlValue);
        textViewObservacao = findViewById(R.id.textViewObsValue);
        textViewNome.setText(senha.getNome());
        textViewUsuario.setText(senha.getUsuario());
        textViewSenha1.setText(senha.getSenha());
        textViewUrl.setText(senha.getUrl());
        textViewObservacao.setText(senha.getObservacao());

    }

    public void fecharActivity(View view) {
        finish();
    }

    private class DeletarSenhaAsyncTask extends AsyncTask<Senha, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(Senha... senha) {
            db.senhaDao().delete(senha[0]);
            return true;
        }

    }

    public void alterarSenha(View view) {

        senha.setNome(textViewNome.getText().toString());
        senha.setUsuario(textViewUsuario.getText().toString());
        senha.setSenha(textViewSenha1.getText().toString());
        senha.setUrl(textViewUrl.getText().toString());
        senha.setObservacao(textViewObservacao.getText().toString());

        Intent intent = new Intent(this, CadastroSenhaActivity.class);
        intent.putExtra("senha",senha);
        startActivity(intent);
        finish();
    }

    public void deletarSenha(View view) {
        // Mostrar aqui um dialog e confirmar a deleção da senha
        AlertDialog builder = new AlertDialog.Builder(this)
                .setTitle("Confirmar exclusão")
                .setMessage("Tem certeza que deseja deletar esse curso?")
                .setPositiveButton("sim", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        new DeletarSenhaAsyncTask().execute(senha);
                        finish();

                    } }).setNegativeButton("não", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        finish();

                    } }) .show();

    }

}
