package com.projeto.familiaeduca.application.mapper;

import com.projeto.familiaeduca.application.responses.TurmaResponse;
import com.projeto.familiaeduca.application.responses.TurmaResumeResponse;
import com.projeto.familiaeduca.domain.models.Turma;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TurmaMapper {
    public TurmaResponse mappingResponse(Turma turma) {
        TurmaResponse response = new TurmaResponse();
        response.setId(turma.getId());
        response.setNome(turma.getNome());

        if(turma.getProfessor() != null) {
            TurmaResponse.ProfessorResumeResponse profResumo = new TurmaResponse.ProfessorResumeResponse();
            profResumo.setId(turma.getProfessor().getId());
            profResumo.setNome(turma.getProfessor().getNome());
            response.setProfessor(profResumo);
        }

        if(turma.getAlunos() != null) {
            List<TurmaResponse.AlunoResumeResponse> alunos = turma.getAlunos().stream().map(aluno -> {
                TurmaResponse.AlunoResumeResponse alunoResumo = new TurmaResponse.AlunoResumeResponse();
                alunoResumo.setMatricula(aluno.getMatricula());
                alunoResumo.setNome(aluno.getNome());
                return alunoResumo;
            }).collect(Collectors.toList());
            response.setAlunos(alunos);
        }

        return response;
    }

    public TurmaResumeResponse mappingResumeResponse(Turma turma) {
        TurmaResumeResponse resumo = new TurmaResumeResponse();
        resumo.setId(turma.getId());
        resumo.setNome(turma.getNome());
        if(turma.getProfessor() != null) {
            resumo.setNomeProfessor(turma.getProfessor().getNome());
        }

        return resumo;
    }
}
