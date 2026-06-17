# 🎮 GameLog - Game Inventory Manager

Projeto acadêmico desenvolvido com **Java, JSF/PrimeFaces** e arquitetura em camadas para gestão de coleções de jogos.

---

## Sobre o Projeto

O **GameLog** é um sistema web criado para auxiliar colecionadores e jogadores a organizarem as suas bibliotecas de videojogos de forma prática e intuitiva.

### A aplicação permite:
- Gerir jogos da coleção
- Controlar backlog e progresso
- Registar empréstimos
- Criar wishlist personalizada

### O projeto foi desenvolvido seguindo os conceitos de:
- Arquitetura em camadas
- Separação de responsabilidades
- Persistência com JPA/Hibernate
- Interface com PrimeFaces

---

## Tecnologias Utilizadas

| Tecnologia | Descrição |
|---|---|
| Java | Backend da aplicação |
| JSF | Framework web MVC |
| PrimeFaces | Componentes visuais |
| Manhattan Template | Interface responsiva |
| JPA / Hibernate | Persistência de dados |
| PostgreSQL | Base de dados |
| Maven | Gestão de dependências |

---

##  Estrutura do Projeto

```
📂 GameLog/
├── 📂 src/main/java/br/upf/gamelog/
│   ├── 📂 controller/        # Controllers da aplicação
│   ├── 📂 entity/            # Entidades JPA
│   ├── 📂 facade/            # Regras de negócio
│   ├── 📂 enumeration/       # Enumerações
│   └── 📂 resources/         # Configurações
│
├── 📂 src/main/resources/
│   └── 📂 META-INF/
│       └── persistence.xml
│
├── 📂 src/main/webapp/
│   ├── 📂 admin/             # Páginas administrativas
│   ├── 📂 resources/         # Recursos do template
│   └── login.xhtml
│
├── 📂 database/
│   └── init.sql
└── pom.xml
```

---

## Funcionalidades

### Gestão de Jogos
- Cadastro de jogos
- Plataforma
- Género
- Formato físico/digital
- Avaliação pessoal

### Sistema de Backlog
Estados disponíveis:
- Por Jogar
- A Jogar
- Concluído
- Platinado

### Gestão de Empréstimos
- Controlo de jogos emprestados
- Data de empréstimo
- Previsão de devolução
- Histórico de empréstimos

###  Wishlist
- Lista de jogos desejados
- Sistema de prioridade
- Observações personalizadas

---

##  Modelo de Entidades

Principais entidades do sistema:
- `JogoEntity`
- `UsuarioEntity`
- `EmprestimoEntity`
- `WishlistEntity`
- `BacklogEntity`

---

## Como Executar o Projeto

### 1. Clonar o Repositório
```bash
git clone https://github.com/manudsf/ADS_Manuela_GameLog
```

### 2. Configurar a Base de Dados
- Criar uma base de dados chamada: `DB_GAMELOG`
- Executar o script: `/database/init.sql`

### 3. Instalar o Manhattan (dependência local)
```bash
mvn install:install-file -Dfile="manhattan-theme-7.0.0-jakarta.jar" -DgroupId=org.primefaces.themes -DartifactId=manhattan -Dversion=7.0.0 -Dpackaging=jar
```

### 4. Abrir na IDE
- Importar o projeto em: **NetBeans**

### 5. Configurar o Servidor
- Compatível com: **GlassFish 7**

### 6. Executar o Projeto
Inicie o servidor e abra o navegador:
```
http://localhost:8080/gamelog
```

###  Credenciais de acesso
| Campo | Valor |
|---|---|
| Email | manuela@gmail.com |
| Senha | 123 |

---

##  Objetivos Acadêmicos

Este projeto teve como foco:
- Aplicação de arquitetura em camadas
- Desenvolvimento com JSF e PrimeFaces
- Persistência com JPA/Hibernate
- Organização de código empresarial
- Criação de interface responsiva

---

##  Autor

| Informação | Dados |
|---|---|
| Nome | Manuela Fortes |
| Curso | Análise e Desenvolvimento de Sistemas |
| Instituição | Universidade de Passo Fundo |
