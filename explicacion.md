# Explicación del proyecto PetStay – Serenity BDD con Screenplay

Este proyecto automatiza la historia de usuario **HU-02** de la aplicación web **PetStay**, un hotel para mascotas (https://petstay-aphn.onrender.com).

- **Tecnologías:** Serenity BDD 5.3.11, patrón Screenplay, Cucumber, JUnit 5, Selenium, Gradle y Java 17.
- **Pruebas:** 4 features escritos en Gherkin en español, con 15 escenarios.
- **Ejecución:** en local y en un pipeline de GitHub Actions.

---

## 1. ¿Qué es Serenity BDD?

**Serenity BDD** es un framework de Java para automatizar pruebas de aceptación y generar reportes que documentan lo que hace el sistema.

Funciona sobre herramientas conocidas:

| Herramienta | Qué hace |
|---|---|
| **Selenium** | Maneja el navegador |
| **Cucumber** | Lee los features escritos en Gherkin |
| **JUnit 5** | Ejecuta las pruebas |

Serenity agrega encima tres cosas:

1. **El patrón Screenplay.** Da la estructura de actor, habilidades, tareas, interacciones y preguntas. Las clases `Actor`, `Task`, `Question`, `Target` y `Ensure` vienen de Serenity.
2. **Reportes en HTML.** Muestran cada escenario paso a paso, con capturas y su relación con las historias y los criterios de aceptación.
3. **Configuración centralizada del navegador** en `serenity.conf`: qué navegador se usa, la URL, los tiempos de espera y si corre en modo headless.

### Dónde se ve Serenity en el proyecto

| Archivo | Qué contiene |
|---|---|
| [build.gradle](build.gradle) | El plugin `serenity-gradle-plugin` y las dependencias `serenity-core`, `serenity-cucumber`, `serenity-screenplay` y `serenity-ensure`. La línea `finalizedBy 'aggregate'` genera el reporte aunque fallen escenarios. |
| [serenity.conf](src/test/resources/serenity.conf) | El nombre del proyecto, Chrome headless (línea 15), la URL de PetStay (línea 18) y capturas solo cuando hay fallos (línea 8). |
| [CucumberTestSuite.java](src/test/java/com/petstay/runners/CucumberTestSuite.java) | El plugin `SerenityReporterParallel`, que conecta Cucumber con el reporte de Serenity (línea 16). |

El reporte se genera en `target/site/serenity/index.html`.

> **Cómo explicarlo:** "Serenity BDD es un framework de Java para pruebas de aceptación automatizadas. Integra Selenium, Cucumber y JUnit; me da el patrón Screenplay para organizar el código con actores, tareas y preguntas; y genera un reporte en HTML con cada escenario paso a paso y su evidencia. Lo agrego en el `build.gradle` y lo configuro en `serenity.conf`."

---

## 2. Estructura del proyecto

```
src/test/
├── resources/
│   ├── features/            → Los 4 features en Gherkin
│   └── serenity.conf        → Configuración del navegador y de Serenity
└── java/com/petstay/
    ├── hooks/               → Crea el escenario (Stage) y el actor
    ├── stepdefinitions/     → Conectan el Gherkin con el actor
    ├── tasks/               → Tareas: acciones de negocio
    ├── interactions/        → Interacciones propias de bajo nivel
    ├── ui/                  → Targets: ubicación de los elementos
    ├── questions/           → Preguntas: leen el estado de la pantalla
    ├── models/              → Datos de prueba y formatos
    └── runners/             → Ejecuta la suite de Cucumber
```

---

## 3. Los features

Los features están en la carpeta **`src/test/resources/features`**:

| Archivo | Qué cubre |
|---|---|
| [acceso.feature](src/test/resources/features/acceso.feature) | Inicio de sesión |
| [registro.feature](src/test/resources/features/registro.feature) | Registro de mascotas |
| [reserva.feature](src/test/resources/features/reserva.feature) | Reserva de estadías, descuentos y servicios |
| [cancelacion.feature](src/test/resources/features/cancelacion.feature) | Cancelación y reembolso |

### Partes de un feature (ejemplo: acceso.feature)

| Línea | Qué es |
|---|---|
| 1 | `# language: es`: el Gherkin está en español (*Característica*, *Escenario*, *Dado*, *Cuando*, *Entonces*) |
| 2 | `@HU-02`: la historia de usuario |
| 3-6 | La característica con su narrativa: *Como* dueño, *quiero* iniciar sesión, *para* usar el hotel |
| 8 | Las etiquetas del escenario: criterio, tipo, prioridad, smoke test e issue |
| 9-12 | El escenario con sus pasos Dado / Cuando / Entonces |

> **Cómo explicarlo:** "Los features están en `src/test/resources/features`. Cada uno cubre una parte de la historia HU-02. En la línea 1 indico que el Gherkin está en español; arriba está la narrativa *como / quiero / para*; y cada escenario tiene etiquetas con su criterio de aceptación, su tipo, su prioridad y el issue de GitHub."

### Etiquetas usadas

| Etiqueta | Significado |
|---|---|
| `@positivo` / `@negativo` | Tipo de prueba |
| `@critico` / `@alto` / `@medio` | Prioridad |
| `@borde` | Prueba valores límite |
| `@CA-XXX-N` | Criterio de aceptación que cubre |
| `@Issue-N` | Issue de GitHub vinculado (trazabilidad) |
| `@smoketest` | Subconjunto mínimo para una validación rápida |
| `@depende-fecha` | El resultado depende de la fecha actual |

---

## 4. Escenarios positivos y negativos

- **Escenario positivo** (*happy path*): con datos válidos, el sistema hace lo que debe.
- **Escenario negativo**: se ingresan a propósito datos inválidos, o se intenta una acción prohibida, y el sistema debe **rechazarlo**.

"Negativo" no quiere decir que la prueba falle. La prueba **pasa** cuando la aplicación sabe decir "no".

### Ejemplo principal: acceso.feature

- **Línea 9, escenario 1 (positivo):** ingreso con `dueno` / `huellitas123` y veo la pantalla principal.
- **Línea 15, escenario 2 (negativo):** ingreso con la clave incorrecta `huellitas999`.
  - En la **línea 18** se valida el mensaje *"Usuario o contraseña incorrectos"*.
  - En la **línea 19** se valida que el usuario siga en el login.

> **Cómo explicarlo:** "Este escenario es negativo porque meto una clave incorrecta a propósito. El sistema no debe dejarme entrar: valido que salga el mensaje de error y que siga en el login. La prueba pasa si el sistema me rechaza."

### Dónde se valida el rechazo en el código

- El paso "el registro es rechazado" está en [PetStepDefinitions.java](src/test/java/com/petstay/stepdefinitions/PetStepDefinitions.java), en el método `registrationIsRejected`.
- Ese paso usa la pregunta `TheMessage.isAnError()` ([TheMessage.java:17](src/test/java/com/petstay/questions/TheMessage.java#L17)), que revisa que el mensaje tenga la clase CSS `error`.

### Detalle de todos los escenarios

#### acceso.feature

| # | Línea | Tipo | Explicación |
|---|---|---|---|
| 1 | 9 | Positivo | Con credenciales válidas, el usuario entra a "Mis mascotas". |
| 2 | 15 | **Negativo** | Con una contraseña incorrecta, la app debe rechazar el ingreso y mostrar el mensaje de error. |

#### registro.feature

| # | Línea | Tipo | Explicación |
|---|---|---|---|
| 3 | 13 | **Negativo** | No se registra una mascota sin vacunas al día. Se valida el mensaje y que "Luna" no aparezca en la lista. |
| 4 | 19 | Positivo + borde | Se aceptan los valores justo en el límite: nombre de 2 y de 30 caracteres, edad 0 y 25, peso 10 / 10,01 / 24,99 / 25 / 80. También verifica el tamaño según el peso. |
| 5 | 32 | **Negativo** + borde | Se rechazan los valores justo fuera del límite: nombre de 1 y de 31 caracteres, peso 0, peso 80,01 y edad 26. |
| 6 | 46 | **Negativo** | No se permite un nombre repetido aunque cambien las mayúsculas ("Max" y "MAX"). |

Los escenarios 4 y 5 forman un **análisis de valores límite**. El 4 prueba el último valor válido y el 5, el primer valor inválido.

#### reserva.feature

| # | Línea | Tipo | Explicación |
|---|---|---|---|
| 7 | 18 | Positivo + borde | El descuento empieza en la octava noche y la estadía máxima es de 30 noches. |
| 8 | 29 | Positivo | El descuento no se aplica a los servicios. Al confirmar se genera un código `PS-XXXX`. |
| 9 | 40 | **Negativo** + borde | Se rechazan las estadías de 31 noches, la salida igual o anterior a la entrada y la entrada en el pasado. |
| 10 | 53 | **Negativo** | El servicio "Paseo diario" no está disponible para gatos. |
| 11 | 59 | Positivo + borde | Una reserva puede empezar el mismo día en que termina otra. |
| 12 | 65 | **Negativo** + borde | Dos reservas de la misma mascota no se pueden cruzar. |

#### cancelacion.feature

| # | Línea | Tipo | Explicación |
|---|---|---|---|
| 13 | 13 | Positivo + borde | El reembolso depende de la anticipación: 3 días o más, 100 %; 1 o 2 días, 50 %; el mismo día, 0 %. |
| 14 | 27 | Positivo | Si el usuario elige "No, volver", la reserva se mantiene. |
| 15 | 34 | **Negativo** | Una reserva cancelada no se puede volver a cancelar. |

### Fechas relativas

Las fechas se escriben como días relativos a hoy: `+5` es hoy más 5 días y `-1` es ayer.

- La conversión está en [ScreenplayHooks.java:21](src/test/java/com/petstay/hooks/ScreenplayHooks.java#L21), en el `@ParameterType dia`.
- Ese método las convierte en `LocalDate.now().plusDays(n)`, así que las pruebas no se vuelven obsoletas con el tiempo.

---

## 5. El patrón Screenplay

La idea central del patrón es esta: **un Actor, con unas Habilidades, realiza Tareas compuestas de Interacciones sobre Targets, y hace Preguntas para verificar el resultado.**

```
Gherkin → StepDefinition → Actor.attemptsTo( Task → Interaction → Target )
                                 Ensure.that( Question ) ← lee la UI
```

### 5.1 Actor

**Dónde está:** [ScreenplayHooks.java](src/test/java/com/petstay/hooks/ScreenplayHooks.java), paquete `com.petstay.hooks`.

| Línea | Código | Qué hace |
|---|---|---|
| 13 | `@Before` | Se ejecuta antes de cada escenario |
| 15 | `OnStage.setTheStage(new OnlineCast())` | Monta el escenario y prepara los actores |
| 17 | `OnStage.theActorCalled("Dueño")` | Crea el actor "Dueño" |

> **Cómo explicarlo:** "El actor se crea en `ScreenplayHooks`, línea 17. Se llama Dueño y es el 'yo' de los escenarios, que están en primera persona. Como el método tiene `@Before`, cada escenario empieza con un actor nuevo y un navegador limpio."

### 5.2 Habilidad (Ability)

La única habilidad es **`BrowseTheWeb`**: la capacidad de usar un navegador.

| Dónde | Qué pasa |
|---|---|
| [ScreenplayHooks.java:15](src/test/java/com/petstay/hooks/ScreenplayHooks.java#L15) | `new OnlineCast()` le da la habilidad al actor automáticamente |
| [serenity.conf:15-18](src/test/resources/serenity.conf#L15-L18) | Configura el navegador: Chrome headless y la URL de PetStay |
| [WaitForLoading.java:24](src/test/java/com/petstay/interactions/WaitForLoading.java#L24) | `BrowseTheWeb.as(actor).getDriver()` usa la habilidad de forma explícita |

> **Cómo explicarlo:** "La habilidad es `BrowseTheWeb`. No la asigno a mano: `OnlineCast`, en la línea 15 de `ScreenplayHooks`, se la da al actor con el navegador configurado en `serenity.conf`. Donde se ve usada es en `WaitForLoading`, línea 24, que toma el navegador del actor."

### 5.3 Step definitions

**Dónde están:** paquete `com.petstay.stepdefinitions`.

Ejemplo: [AccessStepDefinitions.java:22-25](src/test/java/com/petstay/stepdefinitions/AccessStepDefinitions.java#L22-L25).

```java
@Cuando("ingreso el usuario {string} y la contraseña {string}")
public void logsIn(String username, String password) {
    theActorInTheSpotlight().attemptsTo(Login.withCredentials(username, password));
}
```

> **Cómo explicarlo:** "El step conecta la frase del feature con el código mediante la anotación `@Cuando`. No tiene selectores ni clics: solo dice qué hace el actor. El *qué* está aquí; el *cómo* está en la tarea."

### 5.4 Tareas (Tasks): el *qué*, en lenguaje de negocio

**Dónde están:** paquete `com.petstay.tasks`. Cada tarea se crea con `Task.where("{0} hace X", ...)`, y ese texto aparece en el reporte.

| Tarea | Archivo | Qué hace |
|---|---|---|
| `Login.withCredentials` / `as` | [Login.java](src/test/java/com/petstay/tasks/Login.java) | Escribe el usuario y la clave, da clic y espera la carga |
| `RegisterPet.with` | [RegisterPet.java](src/test/java/com/petstay/tasks/RegisterPet.java) | Llena el formulario de la mascota y lo envía |
| `PrepareBooking.of` | [PrepareBooking.java](src/test/java/com/petstay/tasks/PrepareBooking.java) | Llena el formulario de reserva y calcula el total, sin confirmar |
| `AddServices.named` | [AddServices.java](src/test/java/com/petstay/tasks/AddServices.java) | Marca los servicios adicionales y recalcula |
| `ConfirmBooking.now` | [ConfirmBooking.java](src/test/java/com/petstay/tasks/ConfirmBooking.java) | Confirma la reserva y guarda el código generado |
| `Book.forPet` | [Book.java](src/test/java/com/petstay/tasks/Book.java) | Tarea compuesta: prepara la reserva y la confirma |
| `CancelBooking.accepting` / `declining` | [CancelBooking.java](src/test/java/com/petstay/tasks/CancelBooking.java) | Cancela y acepta la confirmación, o se arrepiente |
| `NavigateTo.*` | [NavigateTo.java](src/test/java/com/petstay/tasks/NavigateTo.java) | Navega por el menú y espera el título de la pantalla |
| `ResetData.ofTheHotel` | [ResetData.java](src/test/java/com/petstay/tasks/ResetData.java) | Borra las mascotas y reservas guardadas |

**Ejemplo:** [Login.java:16-24](src/test/java/com/petstay/tasks/Login.java#L16-L24).

```java
return Task.where("{0} inicia sesión con el usuario " + username,
        Enter.theValue(username).into(USERNAME),
        Enter.theValue(password).into(PASSWORD),
        Click.on(LOGIN_BUTTON),
        WaitForLoading.toFinish()
);
```

**Composición de tareas:**
- En [Login.java:26-31](src/test/java/com/petstay/tasks/Login.java#L26-L31), el método `as` navega al login y luego reutiliza `withCredentials`.
- En [Book.java:18-25](src/test/java/com/petstay/tasks/Book.java#L18-L25), `Book` usa `PrepareBooking` y `ConfirmBooking`.

> **Cómo explicarlo:** "Las tareas están en el paquete `tasks`. `Login`, en la línea 16, es una acción de negocio: escribe el usuario en `USERNAME`, la clave en `PASSWORD`, da clic en `LOGIN_BUTTON` y espera a que cargue. Las tareas se componen: `Login.as` reutiliza `withCredentials`, y `Book` usa `PrepareBooking` y `ConfirmBooking`."

### 5.5 Interacciones (Interactions): el *cómo*

- La mayoría son interacciones de Serenity: `Click`, `Enter`, `SetCheckbox`, `SelectFromOptions`, `Open` y `WaitUntil`.
- Hay una **interacción propia**: [WaitForLoading.java](src/test/java/com/petstay/interactions/WaitForLoading.java), en el paquete `com.petstay.interactions`.

| Línea | Qué hace |
|---|---|
| 19-20 | Script de JavaScript que revisa si la capa `#capa-cargando` ya se ocultó |
| 23 | `Interaction.where(...)` crea la interacción |
| 24 | Toma el navegador del actor con la habilidad `BrowseTheWeb` |
| 26-27 | Espera hasta 15 segundos y reintenta si la página está cambiando |

> **Cómo explicarlo:** "Las interacciones son las acciones básicas. Uso las de Serenity, como Click o Enter, y creé una propia: `WaitForLoading`. PetStay muestra una capa de 'cargando' con tiempos aleatorios y la espera normal de Serenity no la detectaba bien, así que esta interacción le pregunta al navegador, con JavaScript, si la capa ya desapareció."

### 5.6 Targets (UI): el *dónde*

**Dónde están:** paquete `com.petstay.ui`, con una clase por pantalla.

| Clase | Pantalla |
|---|---|
| [LoginPage.java](src/test/java/com/petstay/ui/LoginPage.java) | Inicio de sesión |
| [PetsPage.java](src/test/java/com/petstay/ui/PetsPage.java) | Mis mascotas |
| [BookingPage.java](src/test/java/com/petstay/ui/BookingPage.java) | Reservar |
| [MyBookingsPage.java](src/test/java/com/petstay/ui/MyBookingsPage.java) | Mis reservas |
| [Header.java](src/test/java/com/petstay/ui/Header.java) | Menú superior |
| [PageMessage.java](src/test/java/com/petstay/ui/PageMessage.java) | Mensajes, título y capa de carga, comunes a todas las pantallas |

**Ejemplo:** [LoginPage.java:7-9](src/test/java/com/petstay/ui/LoginPage.java#L7-L9).

```java
public static final Target USERNAME = Target.the("campo usuario").locatedBy("[name='usuario']");
```

**Target parametrizado:** [MyBookingsPage.java:12](src/test/java/com/petstay/ui/MyBookingsPage.java#L12). `CANCEL_BUTTON` tiene `{0}`, y con `.of(codigo)` se ubica el botón cancelar de una reserva específica.

> **Cómo explicarlo:** "Los targets están en el paquete `ui`, con una clase por pantalla. Cada uno tiene un nombre legible, que sale en el reporte, y su selector. Si cambia la página, solo cambio esta clase. Algunos son parametrizados con `{0}`, como el botón cancelar, que busco por el código de la reserva."

### 5.7 Preguntas (Questions): leen el estado para verificar

**Dónde están:** paquete `com.petstay.questions`.

| Pregunta | Archivo | Qué responde |
|---|---|---|
| `TheMessage` | [TheMessage.java](src/test/java/com/petstay/questions/TheMessage.java) | El texto del mensaje (línea 11) y si es un error (línea 17) |
| `ThePetList` | [ThePetList.java](src/test/java/com/petstay/questions/ThePetList.java) | Los nombres, el detalle de una mascota y si la lista está vacía |
| `TheBookingSummary` | [TheBookingSummary.java](src/test/java/com/petstay/questions/TheBookingSummary.java) | El resumen de la reserva, traducido al lenguaje del negocio |
| `TheBookings` | [TheBookings.java](src/test/java/com/petstay/questions/TheBookings.java) | El estado, el reembolso, el detalle y si la reserva se puede cancelar |

`TheBookingSummary` traduce lo que muestra la pantalla a como lo describe la historia de usuario:
- si una fila está oculta, devuelve `$0`;
- el descuento que aparece como `-$48.000` lo devuelve como `$48.000` (líneas 42-45).

**Verificación con `Ensure`:** en [AccessStepDefinitions.java:34](src/test/java/com/petstay/stepdefinitions/AccessStepDefinitions.java#L34).

```java
theActorInTheSpotlight().attemptsTo(Ensure.that(TheMessage.shown()).isEqualTo(message));
```

> **Cómo explicarlo:** "Las preguntas están en el paquete `questions` y leen la pantalla. `TheMessage.shown()` devuelve el texto del mensaje. En el step, con `Ensure.that`, comparo esa respuesta con lo que dice el feature. Esa es la aserción y queda en el reporte."

### 5.8 Memoria del actor (`remember` / `recall`)

| Dónde | Qué hace |
|---|---|
| [ConfirmBooking.java:24](src/test/java/com/petstay/tasks/ConfirmBooking.java#L24) | `actor.remember(...)` guarda el código de la reserva confirmada |
| [PrepareBooking.java:27-30](src/test/java/com/petstay/tasks/PrepareBooking.java#L27-L30) | Guarda las fechas y olvida el código anterior con `forget` |
| [CancellationStepDefinitions.java:61-63](src/test/java/com/petstay/stepdefinitions/CancellationStepDefinitions.java#L61-L63) | `actor.recall(...)` recupera el código y las fechas para buscar esa reserva |

> **Cómo explicarlo:** "Cuando confirmo una reserva, el actor guarda el código con `remember`. Después, en los pasos *Entonces*, lo recupera con `recall` para buscar esa reserva exacta en la tabla."

### 5.9 Piezas de apoyo

| Pieza | Dónde | Qué hace |
|---|---|---|
| `HotelUser` | [models/HotelUser.java](src/test/java/com/petstay/models/HotelUser.java) | Enum con los usuarios de prueba (`dueno` / `huellitas123`) |
| `HotelFormats` | [models/HotelFormats.java](src/test/java/com/petstay/models/HotelFormats.java) | Formato de las fechas (`dd/MM/yyyy`) y de los pesos (`$1.200.000`) |
| Runner | [CucumberTestSuite.java](src/test/java/com/petstay/runners/CucumberTestSuite.java) | Ver la tabla siguiente |

Partes del runner:

| Línea | Qué indica |
|---|---|
| 14 | `@SelectClasspathResource("features")`: dónde están los features |
| 15 | *Glue* `com.petstay`: dónde está el código |
| 16 | El reporte de Serenity |

---

## 6. Recorrido sugerido para la exposición

Sigue un solo escenario de punta a punta. Así se ve todo el patrón conectado:

1. **Feature:** [acceso.feature:15](src/test/resources/features/acceso.feature#L15), el escenario negativo de login.
2. **Actor y habilidad:** [ScreenplayHooks.java:15-17](src/test/java/com/petstay/hooks/ScreenplayHooks.java#L15-L17).
3. **Step:** [AccessStepDefinitions.java:22](src/test/java/com/petstay/stepdefinitions/AccessStepDefinitions.java#L22).
4. **Tarea:** [Login.java:16](src/test/java/com/petstay/tasks/Login.java#L16).
5. **Interacción propia:** [WaitForLoading.java:23](src/test/java/com/petstay/interactions/WaitForLoading.java#L23).
6. **Targets:** [LoginPage.java:7](src/test/java/com/petstay/ui/LoginPage.java#L7).
7. **Pregunta y verificación:** [AccessStepDefinitions.java:34](src/test/java/com/petstay/stepdefinitions/AccessStepDefinitions.java#L34) y [TheMessage.java](src/test/java/com/petstay/questions/TheMessage.java).
8. **Reporte:** `target/site/serenity/index.html`.

---

## 7. Resumen

| Elemento | En este proyecto | Dónde |
|---|---|---|
| **Actor** | El Dueño | `hooks/ScreenplayHooks` |
| **Habilidad** | Navegar la web (`BrowseTheWeb`) | `OnlineCast` + `serenity.conf` |
| **Tareas** | Iniciar sesión, registrar mascota, reservar, cancelar | `tasks/` |
| **Interacciones** | Clic, escribir, esperar la carga | Serenity + `interactions/WaitForLoading` |
| **Targets** | Dónde está cada elemento en la pantalla | `ui/` |
| **Preguntas** | Qué muestra la pantalla | `questions/` |
| **Ensure** | Compara lo que se ve con lo que pide el criterio de aceptación | `stepdefinitions/` |

> **Cierre:** "El feature, en `resources/features`, dice el **qué** en lenguaje de negocio. El step, en `stepdefinitions`, se lo pasa al **actor**, creado en `hooks`. El actor tiene la **habilidad** de navegar y ejecuta **tareas**, en `tasks`. Las tareas usan **interacciones** sobre **targets**, en `ui`. Al final verifico con **preguntas**, en `questions`, usando `Ensure`. Así los step definitions quedan limpios y el código es reutilizable."
