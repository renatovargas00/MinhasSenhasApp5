package br.usjt.devmobile.minhassenhasapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SenhaDao {
    @Query("SELECT * FROM senha")
    List<Senha> getAll();

    @Query("SELECT * FROM senha ORDER BY nome")
    List<Senha> getAllOrdemCrescente();

    @Query("SELECT * FROM senha ORDER BY nome DESC")
    List<Senha> getAllOrdemDecrescente();

    @Query("SELECT * FROM senha WHERE nome LIKE :nomePesquisado")
    List<Senha> getPesquisaNome(String nomePesquisado);

    @Query("SELECT * FROM senha WHERE uid IN (:senhaIds)")
    List<Senha> loadAllByIds(int[] senhaIds);

    @Query("SELECT * FROM senha WHERE nome LIKE :nome AND " +
            "usuario LIKE :usuario LIMIT 1")
    Senha findByName(String nome, String usuario);

    @Update
    void updateSenha(Senha senha);

    @Insert
    void insertAll(Senha... senhas);

    @Delete
    void delete(Senha senha);
}