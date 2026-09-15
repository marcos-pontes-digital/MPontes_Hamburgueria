# 🍔 MPontes Hamburgueria

Sistema **PDV (Ponto de Venda)** desenvolvido para gerenciamento de vendas, produtos e pedidos em uma hamburgueria.

O projeto foi desenvolvido com foco em **simplicidade, organização, desempenho e facilidade de manutenção**, utilizando tecnologias modernas do ecossistema Java.

## 🚀 Tecnologias

* ☕ **Java 21**
* 🎨 **JavaFX**
* 🗄️ **SQLite**
* 📦 **Maven**
* 🖨️ **Epson TM-T20X**
* 🐧 **Linux**
* 🪟 **Windows**

## 📋 Funcionalidades

* Cadastro e gerenciamento de produtos
* Controle de produtos ativos/inativos
* Registro de pedidos
* Carrinho de produtos
* Controle de quantidade
* Cálculo automático de subtotais e total
* Finalização de vendas
* Impressão de pedidos
* Impressão de comandas para cozinha
* Integração com banco de dados SQLite
* Interface gráfica desenvolvida com JavaFX

## 🖨️ Impressão

O sistema está sendo preparado para trabalhar com duas impressoras térmicas **Epson TM-T20X**:

### Caixa

Utilizada para:

* Pedidos
* Notas/comprovantes
* Operação do caixa

### Cozinha

Utilizada para:

* Comandas
* Pedidos de produção
* Comunicação com a cozinha através da rede

## 🗄️ Banco de dados

O sistema utiliza **SQLite**, proporcionando uma solução leve e prática para armazenamento local dos dados.

Principais entidades previstas:

```text
configuracoes
produtos
pedidos
itens_pedido
```

## 📁 Estrutura do projeto

```text
MPontes_Hamburgueria/
├── database/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── .gitignore
├── pom.xml
└── README.MD
```

## ▶️ Executando o projeto

Clone o repositório:

```bash
git clone https://github.com/marcos-pontes-digital/MPontes_Hamburgueria.git
```

Entre no diretório:

```bash
cd MPontes_Hamburgueria
```

Compile o projeto:

```bash
mvn clean compile
```

Execute conforme a configuração JavaFX do projeto:

```bash
mvn javafx:run
```

## 🔧 Desenvolvimento

O projeto está em desenvolvimento e novas funcionalidades serão adicionadas progressivamente.

### Roadmap

* [x] Configuração inicial do projeto
* [x] Maven
* [x] Java
* [x] SQLite
* [x] Estrutura inicial do banco de dados
* [ ] Interface principal do PDV
* [ ] Cadastro completo de produtos
* [ ] Tela de pedidos
* [ ] Carrinho de compras
* [ ] Finalização da venda
* [ ] Impressão no caixa
* [ ] Impressão de comandas na cozinha
* [ ] Controle de usuários
* [ ] Relatórios de vendas
* [ ] Backup do banco de dados

## 👨‍💻 Desenvolvedor

**Marcos Pontes**

Software Developer | Full Stack | Python | Java | Linux | Infrastructure

### 📌 Projeto

**MPontes Hamburgueria — Sistema PDV**

Desenvolvido com **Java + JavaFX + SQLite + Maven**.

---

⭐ Projeto desenvolvido para estudos, evolução profissional e aplicação prática de desenvolvimento de software.
# Meu Novo Projeto

Projeto desenvolvido por Marcos Pontes.
