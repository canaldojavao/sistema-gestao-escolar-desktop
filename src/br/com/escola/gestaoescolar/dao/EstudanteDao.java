package br.com.escola.gestaoescolar.dao;

import br.com.escola.gestaoescolar.dominio.Estudante;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudanteDao {

    public void salvar(Estudante estudante) {
        var sql = "insert into estudantes(nome, telefone, endereco, cpf, email) values(?, ?, ?, ?, ?)";

        try (var connection = ConnectionFactory.criaConnection();
             var ps = connection.prepareStatement(sql);) {

            ps.setString(1, estudante.getNome());
            ps.setString(2, estudante.getTelefone());
            ps.setString(3, estudante.getEndereco());
            ps.setString(4, estudante.getCpf());
            ps.setString(5, estudante.getEmail());

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Estudante> listar() {
        var estudantes = new ArrayList<Estudante>();

        var sql = "select * from estudantes";
        try (var connection = ConnectionFactory.criaConnection();
             var ps = connection.prepareStatement(sql)) {
            var rs = ps.executeQuery();

            while (rs.next()) {
                var id = rs.getLong("id");
                var nome = rs.getString("nome");
                var telefone = rs.getString("telefone");
                var endereco = rs.getString("endereco");
                var cpf = rs.getString("cpf");
                var email = rs.getString("email");

                var estudante = new Estudante(id, nome, telefone, endereco, cpf, email);
                estudantes.add(estudante);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return estudantes;
    }

    public void atualizar(String cpfEstudante, Estudante estudante) {
        var sql = "update estudantes set nome = ?, telefone = ?, endereco = ?, cpf = ?, email = ? where cpf = ?";
        try (var connection = ConnectionFactory.criaConnection();
             var ps = connection.prepareStatement(sql)) {

            ps.setString(1, estudante.getNome());
            ps.setString(2, estudante.getTelefone());
            ps.setString(3, estudante.getEndereco());
            ps.setString(4, estudante.getCpf());
            ps.setString(5, estudante.getEmail());
            ps.setString(6, cpfEstudante);

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void excluir(String cpf) {
        var sql = "delete from estudantes where cpf = ?";
        try (var connection = ConnectionFactory.criaConnection();
             var ps = connection.prepareStatement(sql)) {
            ps.setString(1, cpf);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Estudante buscarPorCpf(String cpfEstudante) {
        var sql = "select * from estudantes where cpf = ?";
        try (var connection = ConnectionFactory.criaConnection();
             var ps = connection.prepareStatement(sql)) {
            ps.setString(1, cpfEstudante);

            var rs = ps.executeQuery();
            var estudanteEncontrado = rs.next();
            Estudante estudante = null;
            if (estudanteEncontrado) {
                var id = rs.getLong("id");
                var nome = rs.getString("nome");
                var telefone = rs.getString("telefone");
                var endereco = rs.getString("endereco");
                var cpf = rs.getString("cpf");
                var email = rs.getString("email");

                estudante = new Estudante(id, nome, telefone, endereco, cpf, email);
            }

            return estudante;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
