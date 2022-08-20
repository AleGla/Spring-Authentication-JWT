# Spring-Authentication-JWT
Obtención de JWT para tener acceso y  poder consumir un servicio REST

1° Primero se obtiene JSON WEB TOKEN (JWT) con la siguiente Request de tipo JSON: 

ENDPOINT= http://localhost:8080/token

{
    "user" : "alejandro",
    "pass" : "testing"
}

2° Se hace el llamado al servicio REST (Pokemon). En el Header de la Request debe ir el token obtenido anteriormente y en el body se pasa el nombre del Pokemon a consultar
con el campo "name":

ENDPOINT= http://localhost:8080/pokemon/search

Header: Authentication = "ACA VA EL TOKEN QUE SE RECIBIO ANTERIORMENTE"

{
    "name" : "pikachu"
}
