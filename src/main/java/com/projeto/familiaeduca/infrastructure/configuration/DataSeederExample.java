package com.projeto.familiaeduca.infrastructure.configuration;

import com.projeto.familiaeduca.domain.models.*;
import com.projeto.familiaeduca.infrastructure.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataSeederExample implements CommandLineRunner {

    private final DiretorRepository diretorRepository;
    private final ProfessorRepository professorRepository;
    private final ResponsavelRepository responsavelRepository;
    private final TurmaRepository turmaRepository;
    private final AlunoRepository alunoRepository;
    private final AvisoRepository avisoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        semearDisciplinas();
        if (precisaSemearUsuarios()) {
            semearUsuariosETurmas();
        }

        System.out.println(">>> DATA SEEDER FINALIZADO COM SUCESSO! <<<");
    }

    private boolean precisaSemearUsuarios() {
        // Só semeia se não houver diretores, para evitar duplicatas a cada restart
        return diretorRepository.count() == 0;
    }

    private void semearDisciplinas() {
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

    private void semearUsuariosETurmas() {
        // 1. CRIAR DIRETOR
        Diretor diretor = new Diretor();
        diretor.setNome("Ana Diretora");
        diretor.setEmail("diretor@escola.com");
        diretor.setSenha(passwordEncoder.encode("123456"));
        diretor.setTelefone("(11) 91111-1111");
        diretorRepository.save(diretor);
        System.out.println("Diretor criado: " + diretor.getNome());

        // 2. CRIAR PROFESSORES (Necessário para as Turmas)
        Professor prof1 = new Professor();
        prof1.setNome("Carla Pedagoga");
        prof1.setEmail("prof1@escola.com");
        prof1.setSenha(passwordEncoder.encode("123456"));
        prof1.setTelefone("(11) 92222-2222");
        professorRepository.save(prof1);

        Professor prof2 = new Professor();
        prof2.setNome("João da Música");
        prof2.setEmail("prof2@escola.com");
        prof2.setSenha(passwordEncoder.encode("123456"));
        prof2.setTelefone("(11) 93333-3333");
        professorRepository.save(prof2);
        System.out.println("Professores criados.");

        // 3. CRIAR TURMAS
        Turma turma1 = new Turma();
        turma1.setNome("Maternal I - Manhã");
        turma1.setProfessor(prof1);
        turma1.setDisciplinas(new HashSet<>(disciplinaRepository.findAll())); // Adiciona todas as disciplinas
        turmaRepository.save(turma1);

        Turma turma2 = new Turma();
        turma2.setNome("Jardim II - Tarde");
        turma2.setProfessor(prof2);
        turma2.setDisciplinas(new HashSet<>(disciplinaRepository.findAll()));
        turmaRepository.save(turma2);
        System.out.println("Turmas criadas.");

        // 4. CRIAR RESPONSÁVEIS
        Responsavel resp1 = new Responsavel();
        resp1.setNome("Marcos Pai");
        resp1.setEmail("pai1@email.com");
        resp1.setSenha(passwordEncoder.encode("123456"));
        resp1.setTelefone("(11) 94444-4444");
        resp1.setEndereco("Rua A, 123");
        responsavelRepository.save(resp1);

        Responsavel resp2 = new Responsavel();
        resp2.setNome("Julia Mãe");
        resp2.setEmail("mae2@email.com");
        resp2.setSenha(passwordEncoder.encode("123456"));
        resp2.setTelefone("(11) 95555-5555");
        resp2.setEndereco("Av B, 500");
        responsavelRepository.save(resp2);

        Responsavel resp3 = new Responsavel();
        resp3.setNome("Avó Maria");
        resp3.setEmail("avo3@email.com");
        resp3.setSenha(passwordEncoder.encode("123456"));
        resp3.setTelefone("(11) 96666-6666");
        resp3.setEndereco("Praça C, 10");
        responsavelRepository.save(resp3);
        System.out.println("Responsáveis criados.");

        // 5. CRIAR ALUNOS (6 crianças)
        criarAluno("Pedro", LocalDate.of(2022, 5, 10), turma1, resp1);
        criarAluno("Sofia", LocalDate.of(2022, 8, 20), turma1, resp1); // Irmãos
        criarAluno("Lucas", LocalDate.of(2021, 1, 15), turma2, resp2);
        criarAluno("Isabela", LocalDate.of(2021, 3, 30), turma2, resp2); // Irmãos
        criarAluno("Miguel", LocalDate.of(2022, 12, 5), turma1, resp3);
        criarAluno("Laura", LocalDate.of(2021, 7, 7), turma2, resp3); // Irmãos (netos da avó)
        System.out.println("Alunos criados.");

        // 6. CRIAR AVISOS
        Aviso aviso1 = new Aviso();
        aviso1.setTitulo("Reunião de Pais");
        aviso1.setMensagem("Prezados, teremos reunião na próxima sexta às 18h.");
        aviso1.setDataPublicacao(LocalDate.now());
        aviso1.setDiretor(diretor);
        // Envia para todos os responsáveis
        aviso1.setAvisosResponsaveis(new HashSet<>(Arrays.asList(resp1, resp2, resp3)));
        avisoRepository.save(aviso1);

        Aviso aviso2 = new Aviso();
        aviso2.setTitulo("Feriado Escolar");
        aviso2.setMensagem("Não haverá aula na segunda-feira devido ao feriado.");
        aviso2.setDataPublicacao(LocalDate.now().minusDays(2)); // Aviso antigo
        aviso2.setDiretor(diretor);
        // Envia para professores e responsáveis
        aviso2.setAvisosProfessores(new HashSet<>(Arrays.asList(prof1, prof2)));
        aviso2.setAvisosResponsaveis(new HashSet<>(Arrays.asList(resp1, resp2)));
        avisoRepository.save(aviso2);
        System.out.println("Avisos criados.");
    }

    // Método auxiliar para não repetir código na criação de aluno
    private void criarAluno(String nome, LocalDate dtNasc, Turma turma, Responsavel resp) {
        Aluno aluno = new Aluno();
        // Como a matrícula é int e não auto-gerada, vamos criar uma lógica simples aqui
        // Gera um número aleatório entre 1000 e 9999 para garantir que não repita fácil no teste
        aluno.setMatricula((int) (Math.random() * 9000) + 1000);
        aluno.setNome(nome);
        aluno.setDataNascimento(dtNasc);
        aluno.setTurma(turma);
        aluno.setResponsavel(resp);
        alunoRepository.save(aluno);
    }
}