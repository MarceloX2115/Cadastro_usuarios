# 📚 DataLink: CRUD MongoDB Atlas com Cache Redis Cloud

## 🧩 Descrição do Projeto
Este projeto é uma aplicação de **console em Java** que implementa as operações básicas de **CRUD (Create, Read, Update, Delete)** em um banco de dados NoSQL **MongoDB Atlas**.  
O diferencial do projeto é a utilização do **Redis Cloud** para implementar o padrão de cache **Cache Aside** no método de listagem (**READ**), demonstrando uma otimização de performance.

---

### 📘 Informações Acadêmicas
- **Disciplina:** Banco de Dados Avançados  
- **Professor(a):** [Nome do Professor(a)]  
- **Aluno(a):** [Seu Nome Completo]

---

## 💻 Tecnologias Utilizadas

| Tecnologia | Função |
|-------------|--------|
| **Java (JDK 8 ou superior)** | Linguagem de programação principal |
| **Apache Maven** | Gerenciador de dependências e build |
| **MongoDB Atlas** | Banco de dados NoSQL em nuvem (Persistência de dados) |
| **Redis Cloud (Jedis)** | Banco de dados NoSQL In-Memory (Camada de cache) |
| **MongoDB Java Driver** | Biblioteca para conexão e operações no MongoDB |
| **Jedis (UnifiedJedis)** | Cliente Java para o Redis |

---

## ⚙️ Configuração e Dependências

Para que a aplicação funcione corretamente, é necessário configurar as credenciais de acesso ao **MongoDB Atlas** e ao **Redis Cloud**.

### 1️⃣ Configuração do MongoDB (`MongoConnection.java`)
Certifique-se de que a string de conexão do seu cluster MongoDB Atlas esteja configurada na classe:

```java
datalink.MongoConnection.java
```

- **Usuário de acesso:** `marcelosampaio0987_db_user` (configurado no MongoDB Atlas)  
- **Acesso à rede:** o IP do ambiente de execução deve estar liberado no painel do Atlas.

---

### 2️⃣ Configuração do Redis (`RedisConnection.java`)
As informações de conexão com o **Redis Cloud** (Host, Porta e Senha) devem ser configuradas na classe:

```java
datalink.RedisConnection.java
```

A aplicação utiliza o cliente **UnifiedJedis** para garantir a compatibilidade com a conexão segura do Redis Cloud.

---

## ▶️ Como Executar o Projeto

Este projeto utiliza o **Maven** para gerenciar a compilação e execução.

### 🔧 Pré-requisitos

- Java JDK (8 ou superior) instalado e configurado (variável `JAVA_HOME`)
- Apache Maven instalado

---

### 🪜 Passos de Execução

#### 1. Clonar o Repositório
```bash
git clone [LINK_DO_SEU_REPOSITORIO]
cd mongodb-redis-crud
```

#### 2. Compilar e Empacotar o Projeto
```bash
mvn clean install
```

#### 3. Executar a Aplicação
```bash
mvn exec:java -Dexec.mainClass="datalink.App"
```

> 💬 **Observação:** o menu de console interativo será iniciado automaticamente.

---

## 💡 Demonstração da Lógica de Cache (Cache Aside)

A principal funcionalidade avançada está no método de **listagem (Opção 2)**.  
O método `ListarUsuarios()` segue o padrão **Cache Aside**:

---

### 🐢 Cache Miss (Acesso Lento)

**Ação:** Escolher a opção 2 (LISTAR TODOS) pela primeira vez.  
**Resultado esperado:**

```
Consultando o MONGODB (Cache Miss)
Cache de listagem criado no Redis.
```

> A aplicação consulta o MongoDB e, em seguida, armazena a lista no Redis com um **TTL de 60 segundos**.

---

### ⚡ Cache Hit (Acesso Rápido)

**Ação:** Escolher novamente a opção 2 (LISTAR TODOS), sem executar operações de escrita (1, 3 ou 4) entre as consultas.  
**Resultado esperado:**

```
✅ Dados vindos do REDIS (Cache Hit)
```

> A aplicação retorna os dados do Redis instantaneamente, provando a otimização de performance.

---

### 🔁 Invalidação do Cache
Qualquer operação de escrita (**CREATE**, **UPDATE** ou **DELETE** — Opções 1, 3 ou 4) remove a chave `usuarios` do Redis, forçando um novo **Cache Miss** na próxima listagem.  
Isso garante que os dados estejam sempre atualizados.

---

## 🎬 Link do Vídeo Explicativo
[INSERIR AQUI O LINK DO SEU VÍDEO 🎥 (YouTube, Google Drive, etc.)]

O vídeo deve demonstrar:
- A execução e navegação pelo menu interativo  
- O funcionamento completo do CRUD (Cadastro, Listagem, Alteração, Exclusão)  
- A prova da **Cache Miss** e **Cache Hit**, mostrando a consulta ao MongoDB e ao Redis  

---

## 🗂️ Estrutura do Projeto

| Arquivo | Descrição |
|----------|------------|
| `App.java` | Classe principal com o menu de console (método `main`) |
| `CRUD.java` | Classe de serviço que contém toda a lógica CRUD e de cache |
| `MongoConnection.java` | Classe estática para a conexão com o MongoDB |
| `RedisConnection.java` | Classe estática para a conexão com o Redis |

---

## 🙏 Agradecimentos
Obrigado pela avaliação e pelo interesse no projeto! 🚀  
Sinta-se à vontade para contribuir ou enviar sugestões. 😄
