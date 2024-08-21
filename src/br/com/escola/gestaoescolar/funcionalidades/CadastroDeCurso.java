package br.com.escola.gestaoescolar.funcionalidades;

import br.com.escola.gestaoescolar.dao.CursoDao;
import br.com.escola.gestaoescolar.dominio.Curso;

import java.util.List;

public class CadastroDeCurso {

    private CursoDao cursoDao = new CursoDao();

    public void cadastrar(Curso curso) {
        //validacoes
        if (curso.getNome().isBlank()) {
            throw new IllegalArgumentException("Campo nome é obrigatório!");
        }

        if (curso.getCodigo().isBlank()) {
            throw new IllegalArgumentException("Campo código é obrigatório!");
        }

        if (curso.getCargaHoraria() <= 0) {
            throw new IllegalArgumentException("Carga horária deve ser maior do que zero!");
        }

        if (curso.getNivel() == null) {
            throw new IllegalArgumentException("Campo nível é obrigatório!");
        }

        var cursosCadastrados = listar();
        if (cursosCadastrados.contains(curso)) {
            throw new IllegalArgumentException("Código já cadastrado!");
        }

        cursoDao.salvar(curso);
    }

    public List<Curso> listar() {
        return cursoDao.listar();
    }

    public Curso carregarCursoPeloCodigo(String codigoCurso) {
       return cursoDao.buscarPorCodigo(codigoCurso);
    }

    public void excluir(String codigo) {
        cursoDao.excluir(codigo);
    }

    public void atualizar(String codigoCurso, Curso curso) {
        cursoDao.atualizar(codigoCurso, curso);
    }

}
