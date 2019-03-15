# `Desafio GreenMile`

Repositório destinado a resolução do desafio proposto pela GreenMile.

### Permição de Usuários da API

- `*/api/users`  - via método POST nescessita está logado
- `*/api/users` - via método GET sem restrição
- `*/login` - sem restrição
- `*/api/users/registerTimes` - nescessita está logado
- `*/api/users/{idUser}/registerTimes` - sem restrição

### Endpoints da API

**POST**

- `/login` - Realiza o login e retornar um token de autenticação.


```javascript 
curl -d "{\"username\":"someUsername",\"password\":\"somePassword\"}" -H "Content-Type: application/json" http://localhost:8080/api/users
                 
```

**POST**

- `/api/users` - Adiciona um novo usuário.

```javascript 
curl -d "{\"name\":"someName",\"email\":"someEmail",\"password\":\"somePassword\"}" -H "authToken: <YOUR TOKEN ACESS>" "Content-Type: application/json" http://localhost:8080/api/users            
```

**GET**

- `/api/users` Retorna todas os usuários cadastrados.

```javascript 
curl  http://localhost:8080/api/users              
```

**POST**

- `/api/users/{idUser}/registerTimes`  Adiciona um novo registro de horas do usuário logado.

```javascript 
curl -d "{\"initTime\":"some String with format : dd/MM/YYYY HH:mm",\"endTime\":"some String with format : dd/MM/YYYY HH:mm"}" -H "authToken: <YOUR TOKEN ACESS>" "Content-Type: application/json" http://localhost:8080/api/users/{idUser}/registerTimes           
```

**GET**

- `/api/users/{idUser}/registerTimes` Retorna os registro de horas caso pertença a um usuário logado.

```javascript 
curl  http://localhost:8080/api/users/{idUser}/registerTimes               
```

