# Boletera - Aplicação Java Web para Geração de Boletos

A aplicação Boletera é uma aplicação Java Web desenvolvida com o framework Spring Boot que permite a geração de boletos/carnês utilizando o JasperReports. Esta aplicação recebe parâmetros via requisição HTTP e retorna o boleto gerado em formato PDF como resposta.

## Como Funciona

### Requisição

A geração do boleto é acionada através de uma requisição HTTP GET para o endpoint `/generate`. Os parâmetros necessários para a geração do boleto devem ser fornecidos como parâmetros de consulta na URL da requisição.

Exemplo de requisição:

```http
GET /generate?valor_total=1000.00&referencia=123456&emissor=EmpresaX&total_parcelas=12&data_inicio=2023-01-01
```

### Parâmetros

A tabela a seguir lista os parâmetros necessários para a geração do boleto:

| Parâmetro      | Descrição                                         | Exemplo              |
| -------------- | ------------------------------------------------- | -------------------- |
| valor_total    | Valor total do boleto                             | 1000.00              |
| referencia     | Referência do boleto (identificador único)        | 123456               |
| emissor        | Nome do emissor do boleto                         | EmpresaX             |
| total_parcelas | Número total de parcelas do boleto                | 12                   |
| data_inicio    | Data de início do boleto (no formato "yyyy-MM-dd")| 2023-01-01           |

**Observação:** Todos os parâmetros são obrigatórios. A ausência de qualquer um deles resultará em um erro.

### Resposta

A resposta da requisição é o boleto gerado em formato PDF. O PDF é retornado no corpo da resposta, e o navegador deve renderizá-lo automaticamente.

## Exemplo de Uso

Aqui está um exemplo de como gerar um boleto através de uma requisição cURL:

```bash
curl -o boleto.pdf "http://localhost:7575/boletera/generate?valor_total=1000.00&referencia=123456&emissor=EmpresaX&total_parcelas=12&data_inicio=2023-01-01"
```

O arquivo `boleto.pdf` será gerado no diretório atual.

## Configuração do Projeto

O projeto utiliza o [Spring Boot](https://spring.io/projects/spring-boot) para a construção da aplicação web e [JasperReports](https://community.jaspersoft.com/project/jasperreports-library) para a geração de relatórios em PDF.

java 17 e gradle nem esquenta que o wapper da conta

As dependências principais incluem:

- `spring-boot-starter-web`: Fornece suporte para o desenvolvimento de aplicativos web Spring Boot.
- `openpdf`: Biblioteca para manipulação de PDFs.
- `jasperreports`: Biblioteca para a criação de relatórios.
- `commons-lang3`: Biblioteca de utilitários para manipulação de strings, arrays, etc.

## Executando a Aplicação

Para executar a aplicação, utilize o seguinte comando:

```bash
./gradlew bootRun
```

A aplicação estará disponível em `http://localhost:7575/boletera`.

Lembre-se de substituir os exemplos de parâmetros pelos valores reais desejados ao realizar as requisições.

**Observação:** O projeto utiliza o Gradle como sistema de build. Certifique-se de ter o Gradle instalado para executar os comandos.