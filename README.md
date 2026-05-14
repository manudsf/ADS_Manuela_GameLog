# GameLog - Game Inventory Manager
> Projeto academico desenvolvido com Java, JSF/PrimeFaces e arquitetura em camadas para gestão de coleções de jogos.

##  Sobre o Projeto

O GameLog é um sistema web criado para auxiliar colecionadores e jogadores a organizarem as suas bibliotecas de videojogos de forma prática e intuitiva.

A aplicação permite:

* Gerir jogos da coleção
* Controlar backlog e progresso
* Registar empréstimos
* Criar wishlist personalizada
* Visualizar estatísticas rápidas

O projeto foi desenvolvido seguindo os conceitos de:

* Arquitetura em camadas
* Separação de responsabilidades
* Persistência com JPA/Hibernate
* Interfacecom PrimeFaces

---

## Tecnologias Utilizadas

| Tecnologia         | Descrição                    |
| ------------------ | ---------------------------- |
| Java               | Backend da aplicação         |
| JSF                | Framework web MVC            |
| PrimeFaces         | Componentes visuais          |
| Manhattan Template | Interface responsiva         |
| JPA / Hibernate    | Persistência de dados        |
| PostgreSQL         | Base de dados                |
| Maven              | Gestão de dependências       |

---

##Estrutura do Projeto

---

```text
📂 SaveState/
├── 📂 src/main/java/br/upf/savestate/
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
│
└── pom.xml
```

---

## Funcionalidades

### Gestão de Jogos
* Cadastro de jogos
* Plataforma
* Género
* Formato físico/digital
* Avaliação pessoal

###  Sistema de Backlog
Estados disponíveis:
* Por jogar
* A jogar
* Concluído
* Platinado

### Gestão de Empréstimos
* Controlo de jogos emprestados
* Data de empréstimo
* Previsão de devolução
* Histórico de empréstimos

### Wishlist
* Lista de jogos desejados
* Sistema de prioridade
* Observações personalizadas

## Modelo de Entidades

Principais entidades do sistema:
*  GameEntity
* UserEntity
* LoanEntity
* WishlistEntity
* BacklogEntity

---

## Como Executar o Projeto

### Clonar o Repositório

```bash
git clone https://github.com/seu-usuario/savestate.git
```

---

### Configurar a Base de Dados

Criar uma base de dados chamada:

```sql
DB_GAMELOG
```

Executar o script:

```text
/database/init.sql
```

---

### Abrir na IDE

Importar o projeto em:

* NetBeans

---

### Configurar o Servidor

Compatível com:
* GlassFish 7
---

### Executar o Projeto
Inicie o servidor e abra o navegador:

```text
http://localhost:8080/savestate
```

---


## Objetivos Académicos

Este projeto teve como foco:

* Aplicação de arquitetura em camadas
* Desenvolvimento com JSF e PrimeFaces
* Persistência com JPA/Hibernate
* Organização de código empresarial
* Criação de interface responsiva

---

## Autor

| Informação  | Dados                                  |
| ----------- | ----------------------------------     |
| Nome        | Manuela Fortes                         |
| Curso       | Analise e Desenvolvimento de Sistemas  |
| Instituição | Universidade de Passo Fundo            |

---

Projeto desenvolvido exclusivamente para fins academicos.

---

