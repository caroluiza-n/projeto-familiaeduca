/** package com.projeto.familiaeduca.infrastructure.configuration;

import com.projeto.familiaeduca.domain.models.Disciplina;
import com.projeto.familiaeduca.infrastructure.repository.DisciplinaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataSeeder implements CommandLineRunner {
    private final DisciplinaRepository disciplinaRepository;

    public DataSeeder(DisciplinaRepository disciplinaRepository)
    {
        this.disciplinaRepository = disciplinaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedDisciplinasPadrao();
    }

    private void seedDisciplinasPadrao() {
        List<String> disciplinas = Arrays.asList(
                "O Eu, o Outro e o Nós",
                "Corpo, Gestos e Movimentos",
                "Traços, Sons, Cores e Formas",
                "Escuta, Fala, Pensamento e Imaginação",
                "Espaços, Tempos, Quantidades, Relações e Transformações"
        );

        List<Disciplina> disciplinasExistentes = disciplinaRepository.findByNomeIn(disciplinas);

        List<String> nomesExistentes = disciplinasExistentes.stream()
                .map(Disciplina::getNome)
                .toList();

        for (String nome : disciplinas) {
            if (!nomesExistentes.contains(nome)) {
                Disciplina novaDisciplina = new Disciplina();
                novaDisciplina.setNome(nome);
                novaDisciplina.setPadrao(true);
                disciplinaRepository.save(novaDisciplina);
            }
        }
    }
} **/
