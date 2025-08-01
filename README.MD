# COMO RODAR O PROJETO

### Após clonar o repositório crie na pasta raiz um arquivo chamado .env que contenha as seguintes variáveis de ambiente:
```bash
    SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/tasks_db
    SPRING_DATASOURCE_USERNAME=postgres
    SPRING_DATASOURCE_PASSWORD=postgres
```
Ou se preferir, altere o nome do arquivo `.env.exemple` para `.env`.

### Na pasta raiz execute o comando:
```bash
  docker-compose up --build
```
O servidor deverá iniciar em http://localhost:8080/.

Além disso, o banco banco de dados PostgreSql dentro do container ficará acessível na porta `5433` para acessos externos.

### Toda a documentação dos endpoints pode ser acessada em:
http://localhost:8080/swagger-ui/index.html

### Alguns pontos do desenvolvimento:

- Foi criado um endpoint genérico para atualização completa tanto de tarefas quando sub-tarefas,
porém, como apenas o status deveria ser atualizado, no controller de cada entidade foi exposto um método que utiliza a classe genérica para atualizar apenas o status;
- Os filtros da listagem de tarefas está documentado no endpoint específico no swagger;
- Para os filtros foi utilizado a dependência `Specification` para gerar filtros personalizados para cada atributo de entidade;
- As listagens estão paginadas;
- Exceções personalizadas estão no path `(src/main/java/com/totvs/core/exceptions)` e o tratamento de retorno de exceções no path `(src/main/java/com/totvs/core/controller/ApiExceptionHandler.java)`;
- Foi criado um DTO específico para validação de cada entrada e retorno dos endpoints e um mapper para mapear cada objeto de entidade para o objeto esperado pelos DTOs;
- Alguns testes para os services estão na pasta de testes dentro de `src`;