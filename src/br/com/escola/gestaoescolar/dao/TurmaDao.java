package br.com.escola.gestaoescolar.dao;

import br.com.escola.gestaoescolar.dominio.Periodo;
import br.com.escola.gestaoescolar.dominio.Turma;

import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class TurmaDao {

    private CursoDao cursoDao = new CursoDao();

    public void salvar(Turma turma) {
        var sql = "insert into turmas(codigo, data_inicio, data_fim, periodo, curso_id) values(?, ?, ?, ?, ?)";

        try (var connection = ConnectionFactory.criaConnection();
             var ps = connection.prepareStatement(sql);) {

            ps.setString(1, turma.getCodigo());
            ps.setDate(2, Date.valueOf(turma.getDataInicio()));
            ps.setDate(3, Date.valueOf(turma.getDataFim()));
            ps.setString(4, turma.getPeriodo().name());
            ps.setLong(5, turma.getCurso().getId());

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Turma> listar() {
        var turmas = new ArrayList<Turma>();

        var sql = "select * from turmas";
        try (var connection = ConnectionFactory.criaConnection();
             var ps = connection.prepareStatement(sql)) {
            var rs = ps.executeQuery();

            while (rs.next()) {
                var id = rs.getLong("id");
                var codigo = rs.getString("codigo");
                var dataInicio = Instant.ofEpochMilli(rs.getDate("data_inicio").getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                var dataFim = Instant.ofEpochMilli(rs.getDate("data_fim").getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                var periodo = Periodo.valueOf(rs.getString("periodo"));
                var curso = cursoDao.buscarPorId(rs.getLong("curso_id"));

                var turma = new Turma(id, codigo, curso, dataInicio, dataFim, periodo);
                turmas.add(turma);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return turmas;
    }

    public void atualizar(String codigoTurma, Turma turma) {
        var sql = "update turmas set codigo = ?, data_inicio = ?, data_fim = ?, periodo = ?, curso_id = ? where codigo = ?";
        try (var connection = ConnectionFactory.criaConnection();
             var ps = connection.prepareStatement(sql)) {

            ps.setString(1, turma.getCodigo());
            ps.setDate(2, Date.valueOf(turma.getDataInicio()));
            ps.setDate(3, Date.valueOf(turma.getDataFim()));
            ps.setString(4, turma.getPeriodo().name());
            ps.setLong(5, turma.getCurso().getId());
            ps.setString(6, codigoTurma);

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void excluir(String codigo) {
        var sql = "delete from turmas where codigo = ?";
        try (var connection = ConnectionFactory.criaConnection();
             var ps = connection.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Turma buscarPorCodigo(String codigoTurma) {
        var sql = "select * from turmas where codigo = ?";
        try (var connection = ConnectionFactory.criaConnection();
             var ps = connection.prepareStatement(sql)) {
            ps.setString(1, codigoTurma);

            var rs = ps.executeQuery();
            var turmaEncontrada = rs.next();
            Turma turma = null;
            if (turmaEncontrada) {
                var id = rs.getLong("id");
                var codigo = rs.getString("codigo");
                var dataInicio = Instant.ofEpochMilli(rs.getDate("data_inicio").getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                var dataFim = Instant.ofEpochMilli(rs.getDate("data_fim").getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                var periodo = Periodo.valueOf(rs.getString("periodo"));
                var curso = cursoDao.buscarPorId(rs.getLong("curso_id"));

                turma = new Turma(id, codigo, curso, dataInicio, dataFim, periodo);
            }

            return turma;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
