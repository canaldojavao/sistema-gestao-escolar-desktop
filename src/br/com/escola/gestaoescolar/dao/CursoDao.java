package br.com.escola.gestaoescolar.dao;

import br.com.escola.gestaoescolar.dominio.Curso;
import br.com.escola.gestaoescolar.dominio.Nivel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursoDao {

    public void salvar(Curso curso) {
        var sql = "insert into cursos(nome, codigo, duracao, nivel) values(?, ?, ?, ?)";

        try (var connection = ConnectionFactory.criaConnection();
             var ps = connection.prepareStatement(sql);) {

            ps.setString(1, curso.getNome());
            ps.setString(2, curso.getCodigo());
            ps.setInt(3, curso.getCargaHoraria());
            ps.setString(4, curso.getNivel().toString());

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Curso> listar() {
        var cursos = new ArrayList<Curso>();

        var sql = "select * from cursos";
        try (var connection = ConnectionFactory.criaConnection();
             var ps = connection.prepareStatement(sql)) {
            var rs = ps.executeQuery();

            while (rs.next()) {
                var id = rs.getLong("id");
                var nome = rs.getString("nome");
                var codigo = rs.getString("codigo");
                var cargaHoraria = rs.getInt("duracao");
                var nivel = rs.getString("nivel");

                var curso = new Curso(id, codigo, nome, cargaHoraria, Nivel.valueOf(nivel));
                cursos.add(curso);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cursos;
    }

    public void atualizar(String codigoCurso, Curso curso) {
        var sql = "update cursos set nome = ?, codigo = ?, duracao = ?, nivel = ? where codigo = ?";
        try (var connection = ConnectionFactory.criaConnection();
             var ps = connection.prepareStatement(sql)) {

            ps.setString(1, curso.getNome());
            ps.setString(2, curso.getCodigo());
            ps.setInt(3, curso.getCargaHoraria());
            ps.setString(4, curso.getNivel().toString());
            ps.setString(5, codigoCurso);

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void excluir(String codigo) {
        var sql = "delete from cursos where codigo = ?";
        try (var connection = ConnectionFactory.criaConnection();
             var ps = connection.prepareStatement(sql)) {
            ps.setString(1, codigo);

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Curso buscarPorCodigo(String codigoCurso) {
        var sql = "select * from cursos where codigo = ?";
        try (var connection = ConnectionFactory.criaConnection();
             var ps = connection.prepareStatement(sql)) {
            ps.setString(1, codigoCurso);

            var rs = ps.executeQuery();
            var cursoEncontrado = rs.next();
            Curso curso = null;
            if (cursoEncontrado) {
                var id = rs.getLong("id");
                var nome = rs.getString("nome");
                var codigo = rs.getString("codigo");
                var cargaHoraria = rs.getInt("duracao");
                var nivel = rs.getString("nivel");

                curso = new Curso(id, codigo, nome, cargaHoraria, Nivel.valueOf(nivel));
            }

            return curso;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Curso buscarPorId(Long id) {
        var sql = "select * from cursos where id = ?";
        try (var connection = ConnectionFactory.criaConnection();
             var ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);

            var rs = ps.executeQuery();
            var cursoEncontrado = rs.next();
            Curso curso = null;
            if (cursoEncontrado) {
                var nome = rs.getString("nome");
                var codigo = rs.getString("codigo");
                var cargaHoraria = rs.getInt("duracao");
                var nivel = rs.getString("nivel");

                curso = new Curso(id, codigo, nome, cargaHoraria, Nivel.valueOf(nivel));
            }

            return curso;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
