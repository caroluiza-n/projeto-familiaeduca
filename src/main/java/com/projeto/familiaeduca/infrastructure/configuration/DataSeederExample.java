package com.projeto.familiaeduca.infrastructure.configuration;

import com.projeto.familiaeduca.domain.models.*;
import com.projeto.familiaeduca.domain.models.enums.StatusReuniao;
import com.projeto.familiaeduca.infrastructure.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
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
    private final DisciplinaRepository disciplinaRepository;
    private final AvisoRepository avisoRepository;
    private final BoletimRepository boletimRepository;
    private final NotaRepository notaRepository;
    private final FrequenciaRepository frequenciaRepository;
    private final ReuniaoRepository reuniaoRepository;
    private final ChecklistProfessorRepository checklistProfessorRepository;
    private final ChecklistResponsavelRepository checklistResponsavelRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        semearDisciplinas();

        if (precisaSemearUsuarios()) {
            semearCenarioCompleto();
        }
    }

    private boolean precisaSemearUsuarios() {
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
        List<String> existentes = disciplinaRepository.findByNomeIn(disciplinas).stream().map(Disciplina::getNome).toList();
        for (String nome : disciplinas) {
            if (!existentes.contains(nome)) {
                Disciplina d = new Disciplina(); d.setNome(nome); d.setPadrao(true);
                disciplinaRepository.save(d);
            }
        }
    }

    private void semearCenarioCompleto() {
        Diretor diretor = criarDiretor("Ana Diretora", "diretor@escola.com", "(11) 91111-1111");
        Professor prof1 = criarProfessor("Carla Pedagoga", "prof1@escola.com", "(11) 92222-2222");
        Professor prof2 = criarProfessor("João da Música", "prof2@escola.com", "(11) 93333-3333");
        Responsavel resp1 = criarResponsavel("Marcos Pai", "pai1@email.com", "(11) 94444-4444");
        Responsavel resp2 = criarResponsavel("Julia Mãe", "mae2@email.com", "(11) 95555-5555");

        Turma turma1 = criarTurma("Maternal I - Manhã", prof1);
        Turma turma2 = criarTurma("Jardim II - Tarde", prof2);

        Aluno pedro = criarAluno("Pedro", LocalDate.of(2022, 5, 10), turma1, resp1);
        Aluno sofia = criarAluno("Sofia", LocalDate.of(2022, 8, 20), turma1, resp1);
        Aluno lucas = criarAluno("Lucas", LocalDate.of(2021, 1, 15), turma2, resp2);


        criarAviso("Feriado", "Escola fechada sexta-feira.", diretor, Set.of(resp1, resp2), Set.of(prof1, prof2));

        LocalDate hoje = LocalDate.now();
        criarFrequencia(pedro, turma1, hoje.minusDays(4), true);
        criarFrequencia(pedro, turma1, hoje.minusDays(3), true);
        criarFrequencia(pedro, turma1, hoje.minusDays(2), false); // Faltou!
        criarFrequencia(pedro, turma1, hoje.minusDays(1), true);
        criarFrequencia(pedro, turma1, hoje, true);

        criarChecklistResp(lucas, resp2, LocalDateTime.now().minusHours(4), "Mochila OK, Lancheira OK, Sem febre.");
        criarChecklistProf(lucas, prof2, LocalDateTime.now(), "Almoçou tudo, dormiu 1h, participou bem.");

        criarReuniao(resp1, LocalDateTime.now().plusDays(5), "Alinhamento pedagógico", StatusReuniao.AGENDADA);
        criarReuniao(resp2, LocalDateTime.now().minusMonths(1), "Adaptação escolar", StatusReuniao.REALIZADA);

        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        Disciplina discMovimento = disciplinas.stream().filter(d -> d.getNome().contains("Movimentos")).findFirst().orElseThrow();
        Disciplina discSons = disciplinas.stream().filter(d -> d.getNome().contains("Sons")).findFirst().orElseThrow();

        Boletim bolPedro = criarBoletim(pedro, diretor, "1º Bimestre", Year.now(), "Ótimo desenvolvimento.");
        criarNota(bolPedro, discMovimento, "10", LocalDate.now().minusDays(10));
        criarNota(bolPedro, discSons, "8", LocalDate.now().minusDays(5));

        Boletim bolSofia = criarBoletim(sofia, diretor, "1º Bimestre", Year.now(), "Aguardando fechamento.");
        criarNota(bolSofia, discMovimento, "7", LocalDate.now());

    }

    private Diretor criarDiretor(String nome, String email, String fone) {
        Diretor d = new Diretor(); d.setNome(nome); d.setEmail(email); d.setSenha(passwordEncoder.encode("123456")); d.setTelefone(fone);
        return diretorRepository.save(d);
    }
    private Professor criarProfessor(String nome, String email, String fone) {
        Professor p = new Professor(); p.setNome(nome); p.setEmail(email); p.setSenha(passwordEncoder.encode("123456")); p.setTelefone(fone);
        return professorRepository.save(p);
    }
    private Responsavel criarResponsavel(String nome, String email, String fone) {
        Responsavel r = new Responsavel(); r.setNome(nome); r.setEmail(email); r.setSenha(passwordEncoder.encode("123456")); r.setTelefone(fone); r.setEndereco("Rua Exemplo, 123");
        return responsavelRepository.save(r);
    }
    private Turma criarTurma(String nome, Professor prof) {
        Turma t = new Turma(); t.setNome(nome); t.setProfessor(prof); t.setDisciplinas(new HashSet<>(disciplinaRepository.findAll()));
        return turmaRepository.save(t);
    }
    private Aluno criarAluno(String nome, LocalDate nasc, Turma turma, Responsavel resp) {
        Aluno a = new Aluno(); a.setMatricula((int) (Math.random() * 9000) + 1000); a.setNome(nome); a.setDataNascimento(nasc); a.setTurma(turma); a.setResponsavel(resp);
        return alunoRepository.save(a);
    }
    private void criarAviso(String titulo, String msg, Diretor dir, Set<Responsavel> resps, Set<Professor> profs) {
        Aviso a = new Aviso(); a.setTitulo(titulo); a.setMensagem(msg); a.setDataPublicacao(LocalDate.now()); a.setDiretor(dir); a.setAvisosResponsaveis(resps); a.setAvisosProfessores(profs);
        avisoRepository.save(a);
    }
    private void criarFrequencia(Aluno aluno, Turma turma, LocalDate data, boolean presente) {
        Frequencia f = new Frequencia(); f.setAluno(aluno); f.setTurma(turma); f.setData(data); f.setPresente(presente);
        frequenciaRepository.save(f);
    }
    private void criarChecklistProf(Aluno aluno, Professor prof, LocalDateTime data, String obs) {
        ChecklistProfessor cp = new ChecklistProfessor(); cp.setAluno(aluno); cp.setProfessor(prof); cp.setDataChecklist(LocalDate.from(data)); cp.setObservacoes(obs);
        checklistProfessorRepository.save(cp);
    }
    private void criarChecklistResp(Aluno aluno, Responsavel resp, LocalDateTime data, String itens) {
        ChecklistResponsavel cr = new ChecklistResponsavel(); cr.setAluno(aluno); cr.setResponsavel(resp); cr.setDataChecklist(LocalDate.from(data)); cr.setItensVerificados(itens);
        checklistResponsavelRepository.save(cr);
    }
    private void criarReuniao(Responsavel resp, LocalDateTime data, String motivo, StatusReuniao status) {
        Reuniao r = new Reuniao(); r.setResponsavel(resp); r.setData(LocalDate.from(data)); r.setMotivo(motivo); r.setStatus(status);
        reuniaoRepository.save(r);
    }
    private Boletim criarBoletim(Aluno aluno, Diretor diretor, String bimestre, Year ano, String obs) {
        Boletim b = new Boletim(); b.setAluno(aluno); b.setDiretor(diretor); b.setBimestre(bimestre); b.setAno(ano); b.setObservacoes(obs);
        return boletimRepository.save(b);
    }
    private void criarNota(Boletim boletim, Disciplina disciplina, String nota, LocalDate data) {
        Nota n = new Nota(); n.setBoletim(boletim); n.setDisciplina(disciplina); n.setNota(nota); n.setDataAvaliacao(data);
        n.setAluno(boletim.getAluno());
        if (boletim.getAluno().getTurma() != null) n.setTurma(boletim.getAluno().getTurma());
        notaRepository.save(n);
    }
}