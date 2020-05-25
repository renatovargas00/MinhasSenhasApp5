package br.usjt.devmobile.minhassenhasapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Senha implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "nome")
    private String nome;
    @ColumnInfo(name = "usuario")
    private String usuario;
    @ColumnInfo(name = "senha")
    private String senha;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "observacao")
    private String observacao;

    public Senha(String nome, String usuario, String senha, String url, String observacao) {
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.url = url;
        this.observacao = observacao;
    }

    public Senha() {
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return nome;
    }
}
