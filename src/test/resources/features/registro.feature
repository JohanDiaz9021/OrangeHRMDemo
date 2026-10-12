# language: es
@HU-02 @registro
Característica: Registro de mascotas
  Como dueño de una mascota
  quiero registrar a mis mascotas
  para poder reservarles estadías

  Antecedentes:
    Dado que inicié sesión como "dueno"
    Y no tengo mascotas registradas

  @CA-REG-2 @negativo @critico @smoketest
  Escenario: 3 - No se registra una mascota sin vacunas al día
    Cuando registro una mascota "Luna", gato, 4 kg, 3 años, vacunas "no"
    Entonces veo el mensaje "No podemos hospedar mascotas sin vacunas al día"
    Y "Luna" no aparece en mi lista de mascotas

  @CA-REG-1 @CA-REG-3 @CA-REG-5 @positivo @alto @borde
  Esquema del escenario: 4 - Se aceptan valores en el borde y el tamaño se asigna según el peso
    Cuando registro una mascota "<nombre>", perro, <peso> kg, <edad> años, vacunas "sí"
    Entonces la lista muestra "<nombre>", "Perro", "<peso>", "<edad>" y tamaño "<tamaño>"

    Ejemplos:
      | nombre                         | peso  | edad | tamaño  | borde                           |
      | Bo                             | 10    | 0    | Pequeño | nombre 2 car., edad 0, 10 kg    |
      | Rocky                          | 10,01 | 5    | Mediano | primer valor mediano            |
      | Bruno                          | 24,99 | 5    | Mediano | último valor mediano            |
      | Thor                           | 25    | 25   | Grande  | primer valor grande, edad 25    |
      | Maximiliano Fernando Gutierrez | 80    | 5    | Grande  | nombre 30 car., peso máximo     |

  @CA-REG-1 @negativo @medio @borde
  Esquema del escenario: 5 - Se rechazan valores fuera de rango
    Cuando registro una mascota "<nombre>", perro, <peso> kg, <edad> años, vacunas "sí"
    Entonces el registro es rechazado
    Y la lista de mascotas sigue vacía

    Ejemplos:
      | nombre                          | peso  | edad | caso              |
      | M                               | 5     | 3    | nombre de 1 car.  |
      | Maximiliano Fernando Gutierrezz | 5     | 3    | nombre de 31 car. |
      | Kira                            | 0     | 3    | peso igual a 0    |
      | Kira                            | 80,01 | 3    | peso mayor a 80   |
      | Kira                            | 5     | 26   | edad mayor a 25   |

  @CA-REG-4 @negativo @medio
  Escenario: 6 - No se permite un nombre repetido sin importar mayúsculas
    Dado que tengo registrada la mascota "Max"
    Cuando registro una mascota "MAX", perro, 8 kg, 2 años, vacunas "sí"
    Entonces el registro es rechazado
    Y la lista tiene una sola mascota llamada "Max"
