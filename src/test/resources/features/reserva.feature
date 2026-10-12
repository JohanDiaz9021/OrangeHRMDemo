# language: es
@HU-02 @reserva
Característica: Reserva de estadía
  Como dueño de una mascota
  quiero reservar estadías con servicios adicionales
  para que mi mascota esté cuidada cuando no pueda atenderla

  # Las fechas se expresan como días relativos a hoy (+5 = hoy + 5 días).
  # El step las convierte a DD/MM/AAAA antes de escribirlas en el formulario.

  Antecedentes:
    Dado que inicié sesión como "dueno"
    Y tengo registrado el perro "Toby" de 8 kg (Pequeño)
    Y tengo registrado el perro "Rocky" de 15 kg (Mediano)
    Y tengo registrado el gato "Michi" de 4 kg (Pequeño)

  @CA-RES-4 @CA-RES-5 @CA-RES-7 @CA-RES-8 @positivo @critico @borde
  Esquema del escenario: 7 - El descuento aplica desde la octava noche y hasta el máximo de 30
    Cuando preparo una reserva para "Toby" con entrada el día +5 y <noches> noches, sin servicios
    Entonces el resumen muestra alojamiento "<alojamiento>", descuento "<descuento>" y total "<total>"

    Ejemplos:
      | noches | alojamiento | descuento | total      | borde                 |
      | 7      | $280.000    | $0        | $280.000   | última sin descuento  |
      | 8      | $320.000    | $48.000   | $272.000   | primera con descuento |
      | 30     | $1.200.000  | $180.000  | $1.020.000 | estadía máxima        |

  @CA-RES-6 @CA-RES-7 @CA-RES-8 @CA-RES-10 @positivo @critico @smoke
  Escenario: 8 - El descuento no se aplica a los servicios
    Cuando preparo una reserva para "Rocky" con entrada el día +5 y 10 noches
    Y agrego "Baño y peluquería" y "Paseo diario"
    Entonces el resumen muestra:
      | noches | alojamiento | descuento | servicios | total    |
      | 10     | $550.000    | $82.500   | $125.000  | $592.500 |
    Cuando confirmo la reserva
    Entonces se genera un código con formato "PS-XXXX"
    Y la reserva queda en estado "Confirmada"

  @CA-RES-2 @CA-RES-3 @CA-RES-4 @negativo @alto @borde @depende-fecha
  Esquema del escenario: 9 - Se rechazan fechas inválidas o estadías de más de 30 noches
    Cuando preparo una reserva para "Toby" del día <entrada> al día <salida>
    Entonces la reserva es rechazada
    Y "Toby" no tiene reservas

    Ejemplos:
      | entrada | salida | caso                          |
      | +5      | +36    | 31 noches                     |
      | +5      | +5     | salida igual a la entrada     |
      | +5      | +4     | salida anterior a la entrada  |
      | -1      | +3     | entrada anterior a hoy        |

  @CA-RES-6 @negativo @alto
  Escenario: 10 - El paseo diario no está disponible para gatos
    Cuando preparo una reserva para "Michi" con entrada el día +5 y 3 noches
    Entonces la opción "Paseo diario" no está disponible
    Y el total es "$120.000"

  @CA-RES-9 @positivo @alto @borde
  Escenario: 11 - Una reserva puede empezar el día que termina otra
    Dado que "Toby" tiene una reserva Confirmada del día +10 al día +15
    Cuando reservo para "Toby" del día +15 al día +18
    Entonces la nueva reserva queda en estado "Confirmada"

  @CA-RES-9 @negativo @alto @borde
  Escenario: 12 - Una mascota no puede tener reservas que se crucen
    Dado que "Toby" tiene una reserva Confirmada del día +10 al día +15
    Cuando reservo para "Toby" del día +14 al día +18
    Entonces la reserva es rechazada
    Y "Toby" sigue con una sola reserva activa
