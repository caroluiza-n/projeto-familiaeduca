# Projeto FamiliaEduca

API Rest para gestão e comunicação do ambiente escolar, projetado para fortalecer a conexão entre a instituição, os professores e os responsáveis pelos alunos.

* [Funcionalidades Principais](#-funcionalidades-principais)
* [Tecnologias Utilizadas](#%EF%B8%8F-tecnologias-utilizadas)
* [Arquitetura](#%EF%B8%8F-arquitetura)
* [Como rodar o projeto](#-como-rodar-o-projeto)

---

## ✨ Funcionalidades Principais

* **Gestão de Usuários:** CRUD completo com diferentes perfis (Diretor, Professor, Responsável), utilizando herança no modelo de dados.
* **Segurança Robusta:** Autenticação e autorização baseada em papéis (Roles) com Spring Security. Endpoints são protegidos para garantir que apenas usuários autorizados possam realizar ações críticas.
* **Gestão de Turmas:** CRUD para criação e gerenciamento de turmas, com regras de negócio.
* **Gestão de Alunos:** CRUD completo para o cadastro e gerenciamento de alunos, associando-os a turmas e responsáveis.
* **Módulos de Comunicação:**
    * **Avisos, Cardápios e Eventos:** Funcionalidades para o Diretor enviar comunicados para grupos de Professores e Responsáveis.
* **Registros Diários e Acadêmicos:**
    * Lançamento de **Notas** e controle de **Frequência**.
    * Administração de **Medicação**.
    * Envio de **Justificativas** de falta pelos responsáveis.
    * Preenchimento de **Checklists** diários.
* **Fluxos Administrativos:**
    * Geração de **Boletins**.
    * Solicitação de **Renovação de Matrícula**.

## 🛠️ Tecnologias Utilizadas

* **Backend:** Java 17, Spring Boot 3
* **Persistência:** Spring Data JPA, Hibernate
* **Banco de Dados:** MySQL 8 (gerenciado via Docker)
* **Segurança:** Spring Security
* **Build & Dependências:** Maven
* **Utilitários:** Lombok

## 🏛️ Arquitetura

A API é construída seguindo os princípios da **Arquitetura Hexagonal**, separando claramente as responsabilidades em quatro camadas principais:

* **Domain:** O núcleo da aplicação, contendo a lógica de negócio mais pura. Esta camada não tem conhecimento sobre o banco de dados, a web ou qualquer outro framework.

* **Application:** Esta camada contém os casos de uso específicos da aplicação, orquestrando os objetos do domínio para executar as tarefas.
  
* **Infrastructure:** A camada mais externa, responsável pelas implementações técnicas e pela integração com ferramentas e frameworks. Ela implementa as "portas" definidas pelo domínio.
  
* **Api:** Atua como o principal **"Adaptador de Entrada"** (*Driving Adapter*). É a porta de entrada da aplicação.

## 🚀 Como rodar o projeto
1. **Abra a IDE de sua preferência e clone o repositório**
   
   ```bash
    git init
    git clone https://github.com/caroluiza-n/projeto-familiaeduca.git
    cd projeto-familiaeduca
    ```
2. **Inicie o banco de dados**
- Se não possuir Docker Desktop instalado:
  
  *No powershell como administrador, execute:*
  ```bash
    wsl --install
  ```
  *Faça o download do Docker Desktop e inicie*
  - [Docker Desktop (Windows)](https://docs.docker.com/desktop/setup/install/windows-install/)  
    
- **Rode o container do banco**
  ```bash
    docker compose up -d
  ```
3. **Executando o projeto**
   
   Abra a classe `FamiliaeducaApplication.java` e clique no ícone "Play" (▶️) para iniciar.
   
   A API estará disponível em `http:localhost:8080`
