<h1 align="center"> 
  Microsserviços JAVA com mensageria - Sistema de avaliação de crédito pessoal.
</h1>

<h1 align="center">
  <a href="https://github.com/andreycostaalves">
    <img src="https://aux.iconspalace.com/uploads/1421321576980686818.png" width=50">
  </a>
   BEM VINDO! 
</h1>

<div align="center">
  
  ![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
  ![](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
  ![](https://img.shields.io/badge/EUREKA-SERVER-6DB33F?style=for-the-badge&color=red)
  ![](https://img.shields.io/badge/SPRING%20SECURITY-6DB33F?style=for-the-badge&logo=white)
  ![](https://img.shields.io/badge/RABBITMQ-ED8B00?style=for-the-badge&logo=java&logoColor=white)
  ![](https://img.shields.io/badge/DOCKER-0062ed?style=for-the-badge&logo=java&logoColor=white)
  
</div>
 
 ## Sobre o projeto
Projeto em java, microsserviços - avaliação de crédito pessoal, consiste em microsserviços responsaveis por cadastrar novos clientes, receber requisição de pedido de crédito, avalia se o crédito pode ser consedido e qual o valor, finalizando com as opções de cartões crédito disponiveis.
Feito o pedido, é gerado um protocolo e a requisição vai para um serviço de mensageria - fila onde aguarda o microsserviço receber e finalizar a liberação do cartão.

## Desenho simples da aquiterura
<div align="row">
  <img src="https://user-images.githubusercontent.com/47609519/228678052-27006c10-132c-4971-b00d-9920299e02cd.jpg" width="500" height="300" />
  <img src="https://user-images.githubusercontent.com/47609519/228678899-c3683a49-0763-4365-9e66-3b61c438336d.jpg" width="500" height="300"/>
</div>

Microsservições de clientes - cartões - avaliador de crédito - api gateway e Discovery server.
Todos cadastrados no Eureka server, administrando as requisições via api gateway, atraves de endereços randômicos.


## TECNOLOGIAS
* Java versão 11.
* Spring Boot
* Spring Boot Actuator
* Spring Security
* Spring Cloud Eureka
* Spring Cloud Gateway
* keycloak
* Spring Cloud Feign
* Open API - Swagger
* RabbitMQ 
* Docker
* H2 DATABASE



<a href="https://www.linkedin.com/in/andrey-costa-927458164/" target="_blank"><img src="https://img.shields.io/badge/-LinkedIn-%230077B5?style=for-the-badge&logo=linkedin&logoColor=white" target="_blank"></a>
 <a href = "andreyalvescontato@gmail.com"><img src="https://img.shields.io/badge/-Gmail-%23333?style=for-the-badge&logo=gmail&logoColor=white" target="_blank"></a>
##

<p  align="right">Made with ❤️ by <a href="https://github.com/andreycostaalves">Andrey Alves</a>.</p>
