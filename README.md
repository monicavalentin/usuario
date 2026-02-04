## 🏛️ 1. Visão Geral do Projeto

API desenvolvida em **Java Spring Boot** com foco em alta coesão e baixo acoplamento. O sistema atua como o motor de regras de negócio para gestão de usuários, servindo diretamente a uma camada de **BFF (Backend for Frontend)**.

### 🛠️ Core Stack

* **Spring Data JPA** | 🗄️ Persistência de dados.
* **Lombok** | ⚡ Produtividade e código limpo (Boilerplate-free).
* **Spring Security + JWT** | 🔐 Segurança stateless e autorização.
* **Gradle** | 🐘 Build automatizado e gestão de dependências moderna.

---

## 📂 2. Estrutura do Projeto (Modelo de Service)

O projeto aplica **Clean Architecture Pragmática**, garantindo que as regras de negócio sejam independentes da infraestrutura.

### 🔌 Pacote: `controller`

* **Responsabilidade:** Ponto de entrada da API.
* **Papel:** Recebe requisições do BFF, valida o contrato de entrada e delega a execução para a camada Business.

### 🧠 Pacote: `business`

* **`dto`** | Objetos de transferência de dados para proteção da camada de domínio.
* **`converter`** | Lógica de mapeamento entre Entidades e DTOs.
* **`services`** | Orquestração central da lógica de negócio.

#### 📊 Métodos da UsuarioService

| Operação | Método | Objetivo Técnico |
| --- | --- | --- |
| **Criação** | `salvaUsuario` | Persiste um novo usuário e seus vínculos. |
| **Validação** | `verificarEmailExiste` | Regra de integridade para e-mails duplicados. |
| **Busca** | `buscaUsuarioEmail` | Recuperação de perfil completo via identificador único. |
| **Atualização** | `atualizaDados` | Gerenciamento de alterações em Dados/Endereço/Telefone. |
| **Exclusão** | `deletaUsuarioByEmail` | Remoção segura do registro no ecossistema. |

### 🏗️ Pacote: `infrastructure`

* **`entity`** | Modelagem JPA/Hibernate.
* **`repository`** | Interfaces de acesso ao banco de dados.
* **`exceptions`** | Gestão de erros com `GlobalExceptionHandler`.
* **`security`** | Filtros JWT e configurações de contexto de segurança.
---

## 🔄 3. Fluxo de Dados (Workflow)

O fluxo abaixo ilustra a jornada de uma requisição desde o BFF até o banco de dados:

1. **BFF** dispara `POST /usuario` ➡️
2. **Controller** intercepta e valida o `UsuarioRequestDTO`.
3. **Service** executa a regra de negócio (`verificarEmailExiste`).
4. **Converter** traduz `DTO` 🔄 `Entity`.
5. **Repository** realiza a persistência no Database.
6. **Controller** devolve o `UsuarioResponseDTO` com Status `201 Created`.

---

### 🏗️ Débito Técnico: `melhorias futuras:`

### 🛠️ Refatoração para Clean Architecture Purista


[ ] Criar o pacote business.usecases para isolar as regras de negócio.

[ ] Migrar lógica da UsuarioService para classes específicas (ex: SalvarUsuarioUseCase, CadastrarEnderecoUseCase).

[ ] Implementar verificarEmailExiste como regra de validação interna do UseCase de salvamento.

[ ] Adaptar o UsuarioController para injetar UseCases específicos em vez da Service genérica.

### 🛠️ Fase 2: Infraestrutura & Qualidade
[ ] Integrar Swagger/OpenAPI para documentação interativa dos endpoints.

[ ] Implementar testes unitários com JUnit 5 e Mockito para os UseCases.