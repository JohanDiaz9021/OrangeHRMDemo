# language: es
@HU-02 @acceso
Característica: Acceso a PetStay
  Como dueño de una mascota
  quiero iniciar sesión con mi usuario y contraseña
  para usar los servicios del hotel

  @CA-ACC-1 @positivo @alto @smoketest @Issue-1
  Escenario: 1 - Ingreso con credenciales válidas
    Dado que estoy en la pantalla de inicio de sesión
    Cuando ingreso el usuario "dueno" y la contraseña "huellitas123"
    Entonces veo la pantalla principal del hotel

  @CA-ACC-2 @negativo @alto @Issue-2
  Escenario: 2 - Ingreso con contraseña incorrecta
    Dado que estoy en la pantalla de inicio de sesión
    Cuando ingreso el usuario "dueno" y la contraseña "huellitas999"
    Entonces veo el mensaje "Usuario o contraseña incorrectos"
    Y sigo en la pantalla de inicio de sesión
