package br.com.escola.gestaoescolar.funcionalidades;

import br.com.escola.gestaoescolar.dao.TurmaDao;
import br.com.escola.gestaoescolar.dominio.Turma;

import java.util.List;

public class CadastroDeTurma {

    private TurmaDao turmaDao = new TurmaDao();

    public void cadastrar(Turma turma) {
        //validacoes
        if (turma.getCodigo().isBlank()) {
            throw new IllegalArgumentException("Campo código é obrigatório!");
        }

        if (turma.getDataInicio() == null) {
            throw new IllegalArgumentException("Campo data início é obrigatório!");
        }

        if (turma.getDataFim() == null) {
            throw new IllegalArgumentException("Campo data fim é obrigatório!");
        }

        if (turma.getPeriodo() == null) {
            throw new IllegalArgumentException("Campo período é obrigatório!");
        }

        if (turma.getCurso() == null) {
            throw new IllegalArgumentException("Campo curso é obrigatório!");
        }

        var turmasCadastradas = turmaDao.listar();
        if (turmasCadastradas.contains(turma)) {
            throw new IllegalArgumentException("Código já cadastrado!");
        }

        turmaDao.salvar(turma);
    }

    public List<Turma> listar() {
        return turmaDao.listar();
    }

    public void excluir(String codigo) {
        turmaDao.excluir(codigo);
    }

    public void atualizar(String codigoTurma, Turma turma) {
        turmaDao.atualizar(codigoTurma, turma);
    }

}
