# Checkout transparente

Essa é uma implementação do backend de um processo de  checkout transparente.

## Tecnologias utilizadas

* Java 11
* Spring Boot
* Postgresql
* Docker (Opicional)

## Como executar

O projeto necessita de algumas variáveis de ambiente para funcionar corretamente. Seguem as configurações necessárias.

```bash
export DB_URL=jdbc:postgresql://localhost/checkout
export DB_USERNAME=postgres # seu usuário
export DD_PASSWORD=postgres # sua senha
```

Para fazer o build do projeto execute

```bash
cd webservice
./mvnw clean install
```

Execute normalmente com java -jar

```bash
java -jar target/*.jar
```

### Executando com docker

Para executar com docker utilize os seguintes comandos:

```bash

# criação de uma rede docker
docker network create checkout-net

# criação do serviço de banco de dados
docker run \
    -d \
    --name checkout-db \
    -p 5432:5432 \
    -e POSTGRES_USER=checkout \
    -e POSTGRES_PASSWORD=checkout \
    -e POSTGRES_DATABASE=checkout \
    -v checkout_db:/var/lib/postgresql/data \
    --net checkout-net
    postgres:12-alpine

# build da imagem docker
docker build . -t lopesluisjorge/checkout-transparente

docker run \
    --rm \
    -p 8080:8080 \
    -e DB_URL=jdbc:postgresql://checkout-db/checkout \
    -e DB_USERNAME=checkout \
    -e DB_PASSWORD=checkout \
    --network checkout-net \
    lopesluisjorge/checkout-transparente
```