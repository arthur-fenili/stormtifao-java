# StormTifão - Global Solution 🌪️

Bem-vindo ao StormTifão, uma solução web desenvolvida para auxiliar o gerenciamento de pessoas desaparecidas e análise de risco climático (tempestades e furacões) em situações de desastre natural. Este projeto foi realizado como parte do desafio Global Solution, focando em inovação, colaboração e integração de tecnologias modernas.

## 🚀 Descrição do Projeto

O StormTifão é um sistema web que permite:
- **Cadastro e listagem de pessoas desaparecidas**, com upload de fotos e todos os dados relevantes.
- **Análise de risco de tempestade/furacão com IA**, consumindo dados reais da OpenWeather e gerando uma análise automática.
- **Internacionalização** (português/inglês) para alcance global.
- **Autenticação via Google OAuth2**, garantindo segurança e privacidade.
- **Integração assíncrona via RabbitMQ**: todo cadastro de pessoa desaparecida publica a mensagem correspondente na fila.
- **Interface moderna e responsiva**, pensada para facilitar o uso em situações críticas.

---

## 🛠️ Tecnologias Utilizadas

- **Java 21 + Spring Boot 3.5.0**
- Thymeleaf
- Spring Data JPA (H2 para testes)
- Spring Security + OAuth2 Client (login Google)
- Spring AMQP (RabbitMQ)
- Docker (RabbitMQ container)
- OpenWeather API (dados climáticos)
- Cohere API (análise IA dos dados do tempo)

---

## ⚡ Como Executar o Projeto

### 1. **Pré-requisitos**

- **Java 21** instalado
- **Gradle** instalado
- **Docker** instalado (para subir o RabbitMQ)
- **Conta Google** (para autenticação)
- **Chave da API OpenWeather** (configure no seu `application.properties`)
- **Chave da API Cohere**
- **Secrets do Google para OAuth**

### 2. **Clone o repositório**

```sh
git clone https://github.com/seu-usuario/stormtifao.git
cd stormtifao
```
## 3. Suba o RabbitMQ com Docker

**É OBRIGATÓRIO executar este comando antes de rodar a aplicação:**

```sh
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

A interface do RabbitMQ estará disponível em `http://localhost:15672` com usuário `guest` e senha `guest`.

### 4. **Configuração do Ambiente**

Para você, professor, enviarei um arquivo .txt com as configurações necessárias do `application.properties`.

### 5. **Compilar e Executar**

```sh
./gradlew clean build
./gradlew bootRun
```

---

## **Documentação dos Endpoints**

## Rotas Principais

| Rota                              | Método | Protegido | Descrição                                              |
|------------------------------------|--------|-----------|--------------------------------------------------------|
| `/`                               | GET    | Sim       | Página inicial (sobre o projeto)                       |
| `/pessoas-perdidas`                | GET    | Sim       | Lista de pessoas desaparecidas                         |
| `/pessoas-perdidas/nova`           | GET    | Sim       | Formulário para novo cadastro                          |
| `/pessoas-perdidas`                | POST   | Sim       | Cadastra uma nova pessoa e publica no RabbitMQ         |
| `/pessoas-perdidas/editar/{id}`    | GET    | Sim       | Editar pessoa                                          |
| `/pessoas-perdidas/deletar/{id}`   | GET    | Sim       | Exclui pessoa                                          |
| `/dashboard/weather`               | GET    | Sim       | Dashboard de risco climático                           |
| `/login`                           | GET    | Não       | Autenticação via Google                                |
| `/logout`                          | GET    | Sim       | Logout                                                 |

> **Todas as telas protegidas só são acessíveis mediante login com Google.**  
> **Internacionalização disponível (🇧🇷/🇺🇸) no header.**

---

## **Integração RabbitMQ**

- **Necessário Docker!**
- Toda vez que uma pessoa é cadastrada, os dados (exceto a foto em base64) são enviados como mensagem JSON para a fila `pessoa-perdida`.
- O consumer está implementado na aplicação, pronto para consumir e logar/processar as mensagens.

---

## **Testes**

- Testes unitários e de integração utilizando **JUnit 5** e **Spring Boot Test**.

### Como rodar os testes

```sh
./gradlew test
```
---

## **Notas finais e lembretes**
- Lembre-se de subir o RabbitMQ com Docker antes de rodar o sistema.
- O projeto utiliza banco de dados em memória (H2) para facilitar testes locais, porém basta comentar a configuração do H2 e descomentar a do Oracle no build.gradle para trocar, assim como adicionar as credenciais.

---

## **Integrantes**
- **Arthur Fenili** - 552752
- **Enzo Antunes Oliveira** - 553185
- **Vinicio Raphael Santana** - 553813

---
