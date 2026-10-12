# language: es
Característica: Login en OrangeHRM
  Como usuario de OrangeHRM
  Quiero iniciar sesion
  Para acceder al Dashboard

  Escenario: Login exitoso con credenciales validas
    Dado que el usuario abre la pagina de OrangeHRM
    Cuando inicia sesion con usuario "Admin" y contrasena "admin123"
    Entonces deberia ver el Dashboard
