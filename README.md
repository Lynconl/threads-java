# Sistema de Produtor-Consumidor com Threads em Java

Este projeto implementa um sistema de produtor-consumidor utilizando threads em Java. O sistema envolve a criação de produtores e consumidores que trabalham em paralelo para gerenciar objetos de diferentes tipos de canais de atendimento.

## Requisitos

- Java 17
- Maven

## Configuração

1. Clone o repositório para sua máquina local.
2. Certifique-se de ter o Java 17 e o Maven instalados.
3. Configure as propriedades do projeto no arquivo `src/main/resources/sistema.properties`.

## Execução

1. Compile o projeto usando o Maven:
   ```
   mvn compile
   ```

2. Execute o programa principal:
   ```
   mvn exec:java -Dexec.mainClass="br.com.teste.weon.Main"
   ```

## Estrutura do Projeto

- `src/main/java/br/com/teste/weon`: Contém o código-fonte do projeto.
    - `factory`: Contém a fábrica de produtores.
    - `produtores`: Contém as classes dos diferentes tipos de produtores.
    - `consumidores`: Contém a classe do consumidor.
    - `dao`: Contém os Data Access Objects para persistência.
    - `model`: Contém as classes de modelo.
    - `queue`: Contém a implementação da fila de objetos.
    - `runnables`: Contém as classes Runnable para as threads de produtores e consumidores.
    - `util`: Contém utilidades, como a configuração de propriedades e o gerenciador de persistência.

## Funcionamento

O sistema cria threads de produtores que geram objetos de diferentes tipos de canais de atendimento (Voz, Email e Chat) e threads de consumidores que consomem esses objetos. As threads de produtores e consumidores são configuradas com um tempo de execução específico e são gerenciadas através de um `CountDownLatch` para garantir sincronização.

Após a execução, o programa exibirá a quantidade total de objetos produzidos e consumidos, bem como verificará se ainda há objetos na fila.

## Notas

- Certifique-se de configurar corretamente o arquivo `sistema.properties` para ajustar a quantidade de produtores, consumidores e duração das threads.
- Este README é um exemplo e pode ser adaptado conforme suas necessidades.

## Autor

Lynconl Felipe Assunção
