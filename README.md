# Prueba Tecnica Java - Back

Este repositorio contiene la solución a la prueba técnica de Java...   
solo contiene el back, el front por **consola** se encuentra en [otro repositorio](https://github.com/cris6h16/prueba-tecnica-java-front).

<hr>

[Planteamiento](#Requerimientos-Principales)   

[Acerca del proyecto](#Proyecto)   

[Ejecuta la app](#Ejecuta-la-app)

<hr>

## Requerimientos Principales

- Que sea Web API (Java)
- El Front sera por Consola
- Contenga sus respectivas pruebas unitarias
- Que se aplique POO

### Se Valorara

- Aplicar conocimientos de SOLID
- Arquitetura Hexagonal

### Generales

![img.png](Docs/1(1)(1)(1).png)
![img.png](Docs/2(1)(1)(1).png)

<hr>   

# Proyecto

## Gerarquia final (Arquitectura Hexagonal)

```
.
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── example
    │   │           ├── SpringBootMain.java
    │   │           ├── Domain
    │   │           │   ├── Model
    │   │           │   │   ├── UserModel.java
    │   │           │   │   └── PostModel.java
    │   │           │   ├── Exceptions
    │   │           │   │   ├── AbstractDomainException.java
    │   │           │   │   └── Impls
    │   │           │   │       ├── UserModelException.java
    │   │           │   │       └── PostModelException.java
    │   │           │   └── Repositories
    │   │           │       ├── PostDomainRepository.java
    │   │           │       └── UserDomainRepository.java
    │   │           ├── Application
    │   │           │   ├── Commands
    │   │           │   │   ├── CreatePostCommand.java
    │   │           │   │   ├── FindByUsernameCommand.java
    │   │           │   │   ├── FindByUsernameFollowingEagerCommand.java
    │   │           │   │   └── FollowUserCommand.java
    │   │           │   ├── DTOs
    │   │           │   │   ├── CreatePostDTO.java
    │   │           │   │   ├── PostDTO.java
    │   │           │   │   └── UserDTO.java
    │   │           │   ├── Exceptions
    │   │           │   │   ├── AbstractApplicationException.java
    │   │           │   │   └── Impls 
    │   │           │   │           ├── AlreadyFollowingException.java 
    │   │           │   │           ├── UsernameIsNullOrBlankException.java 
    │   │           │   │           └── UserNotFoundException.java 
    │   │           │   ├── Handlers
    │   │           │   │   ├── CreatePostCommandHandler.java
    │   │           │   │   ├── FindByUsernameCommandHandler.java
    │   │           │   │   ├── FindByUsernameFollowingEagerCommandHandler.java
    │   │           │   │   └── FollowUserCommandHandler.java
    │   │           │   └── Services
    │   │           │       ├── UserApplicationServiceImpl
    │   │           │       ├── PostApplicationServiceImpl.java
    │   │           │       └── Interfaces
    │   │           │           ├── PostApplicationService.java
    │   │           │           └── UserApplicationService.java
    │   │           │   
    │   │           └── Infrastructure
    │   │               ├── Adapter
    │   │               │   ├── Input
    │   │               │   |   └── REST
    │   │               │   |       ├── PostController.java
    │   │               │   |       ├── UserController.java
    │   │               │   |       └── DTOs
    │   │               │   |           ├── CreatePostRequest.java
    │   │               │   |           └── FollowUserRequest.java
    │   │               │   └── Output
    │   │               │       ├── InMemoryPostRepository.java
    │   │               │       └── InMemoryUserRepository.java
    │   │               ├── Config
    │   │               │   └── SpringBoot
    │   │               │       ├── Beans.java
    │   │               │       └── CustomExceptionHandler.java
    │   │               └── Exceptions
    │   │                   ├── AbstractInfrastructureException.java
    │   │                   └── REST.java
    │   │                       └── MyResponseStatusException.java
    │   └── resources
    │       └── application.yml
    └── test
        └── java
            └── com
                └── example
                    ├── Suites.java
                    ├── Domain
                    │   └── Model
                    │       ├── PostModelTest.java
                    │       └── UserModelTest.java
                    ├── Application
                    |   ├── DTOs
                    |   |   ├── CreatePostDTOTest.java
                    |   |   ├── PostDTOTest.java
                    |   |   └── UserDTOTest.java
                    |   ├── Handlers
                    |   |   ├── CreatePostCommandHandlerTest.java
                    |   |   ├── FindByUsernameCommandHandlerTest.java
                    |   |   ├── FindByUsernameFollowingEagerCommandHandlerTest.java
                    |   |   └── FollowUserCommandHandlerTest.java
                    |   └── Services
                    |       ├── PostApplicationServiceImplTest.java
                    |       └── UserApplicationServiceImplTest.java
                    ├── Domain
                    |   └── Model
                    |       ├── PostModelTest.java
                    |       └── UserModelTest.java
                    └── Infrastructure
                        └── Adapter
                            ├── Input
                            |   └── REST
                            |       ├── PostControllerTest.java
                            |       └── UserControllerTest.java
                            ├── Output
                            |   ├── InMemoryPostRepositoryTest.java
                            |   └── InMemoryUserRepositoryTest.java
                            └── Config
                                └── SpringBoot
                                    ├── BeansTest.java
                                    └── CustomExceptionHandlerTest.java
```

Uno de los principales requerimientos era que sea Web API, esto lo hago con Spring Boot...

## Puntos Fuertes a Destacar
1. **Arquitectura Hexagonal**
2. **SOLID**
3. Pruebas **Unitarias** con **100%** de **Cobertura** ( +100 tests )
4. Exception Handling (Excepciones limitadas son las que se exponen)
5. **Beans** de Spring son declarados en `Infrastucture`
5. Etc

![img_1.png](Docs/img_1.png)   

## Workflow

1. un `Adapter.INPUT` recibe la petición del cliente
2. el `Adapter.INPUT` se crea un `Command` muy especifico dependiendo de la petición (dentro de los metodos)
3. el `Adapter.INPUT` le pasa el `Command` creado a su respectivo `CommandHandler`
4. el `CommandHandler` se encarga de llamar al `Service` de `/Application` correspondiente
5. el `Service` de `/Application` se encarga de llamar a su respectivo `Repository` de `/Domain` (depemde de una abstracción (es una interfaz))
6. el `Repository` llamado sera una implementación de un "puerto" o "interfaz de salida" que se encuentra en `/Domain`, esta implementación se encuentra en `/Infrastructure`

PD:
- `CommandHandler` es un wrapper de un caso de uso, es decir este llama a un metodo especifico de un `Service`
- un `Service` de `Application` es un caso de uso, este tiene interaccion con los `Adapter.INPUT` atravez de `CommandHandler`, no directamente.

**TODO**: Agregar diagrama de flujo


## Ejecuta la app

1. Clone el repositorio
2. Abre la ubicación del proyecto en tu terminal
3. Ejecuta (puedes cambiar el puerto): 
   - `mvn clean`
   - `mvn package`
    - `java -jar target/demo-1.0.jar --server.port=8000`
- Ya tienes el back ejecutandose!!
4. Ahora continuar con el [front (Consola)](https://github.com/cris6h16/prueba-tecnica-java-front)

## Testea la app [usando Postman](Docs/prueba-tecnica.postman_collection.json) !!
![img.png](Docs/img.png)   

