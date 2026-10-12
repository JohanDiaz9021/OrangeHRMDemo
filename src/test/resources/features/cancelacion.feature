# language: es
@HU-02 @cancelacion
Característica: Mis reservas y cancelación
  Como dueño de una mascota
  quiero ver y cancelar mis reservas
  para recuperar mi dinero según la anticipación

  Antecedentes:
    Dado que inicié sesión como "dueno"
    Y tengo registrado el perro "Toby" de 8 kg (Pequeño)

  @CA-CAN-1 @CA-CAN-2 @CA-CAN-3 @CA-CAN-4 @positivo @critico @borde @depende-fecha @smoketest
  Esquema del escenario: 13 - El reembolso depende de los días que faltan para la entrada
    Dado que "Toby" tiene una reserva Confirmada de 2 noches por $80.000 con entrada el día +<dias>
    Cuando cancelo la reserva y acepto la confirmación
    Entonces la reserva queda en estado "Cancelada"
    Y la lista muestra el código, "Toby", las fechas, "$80.000", "Cancelada" y reembolso "<reembolso>"

    Ejemplos:
      | dias | reembolso | borde                   |
      | 3    | $80.000   | primer día con 100%     |
      | 2    | $40.000   | último día con 50%      |
      | 1    | $40.000   | primer día con 50%      |
      | 0    | $0        | mismo día de la entrada |

  @CA-CAN-2 @positivo @medio
  Escenario: 14 - Si no confirmo la cancelación, la reserva se mantiene
    Dado que "Toby" tiene una reserva Confirmada de 2 noches por $80.000 con entrada el día +10
    Cuando cancelo la reserva y rechazo la confirmación
    Entonces la reserva sigue en estado "Confirmada"
    Y la lista no muestra reembolso para esa reserva

  @CA-CAN-4 @negativo @alto
  Escenario: 15 - Una reserva cancelada no se puede volver a cancelar
    Dado que "Toby" tiene una reserva en estado "Cancelada" con reembolso de $80.000
    Entonces la opción de cancelar no está disponible para esa reserva
    Y el reembolso mostrado sigue siendo "$80.000"
