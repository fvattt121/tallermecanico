# Sistema de Gestión para Taller Mecánico ("Hotwheels TAM")

Proyecto en **Java puro** (Swing + JDBC + MySQL) que cumple con todos los requisitos del sistema.

---

## 1. Diagramas del Sistema
Los diagramas requeridos están documentados con detalle en el archivo:
* 📊 **[DIAGRAMAS.md](file:///d:/files/TallerMecanico/TallerMecanico/DIAGRAMAS.md)**: Contiene el **Diagrama de Clases**, **Diagrama de Casos de Uso** y el **Diagrama de Componentes** (en formato Mermaid renderizable).

---

## 2. Estructura del Proyecto

```
TallerMecanico/
 ├─ src/com/taller/          <- Todo el código fuente (.java)
 │   ├─ modelo/               Persona, Cliente, Mecanico, Vehiculo, Usuario,
 │   │                        ItemPresupuesto, ItemRefaccion, ItemManoObra,
 │   │                        OrdenReparacion, Refaccion, RegistroBitacora, enums
 │   ├─ dao/                  ConexionBD + DAOs (acceso a la base de datos MySQL)
 │   ├─ util/                 Sesion (usuario logueado), SeguridadUtil (hash de claves)
 │   ├─ ui/                   Login, Registro, Dashboard y todos los paneles Swing
 │   └─ Main.java              Punto de entrada
 ├─ lib/
 │   ├─ mysql-connector-j.jar  Driver JDBC de MySQL (para XAMPP/MariaDB)
 │   ├─ sqlite-jdbc.jar        Driver JDBC de SQLite (de respaldo)
 │   ├─ slf4j-api.jar          Dependencia de logging
 │   └─ slf4j-nop.jar          Implementación silenciosa de slf4j
 └─ DIAGRAMAS.md               Diagramas requeridos (Clases, Casos de Uso y Componentes)
```

---

## 3. Cómo Compilar y Ejecutar

### Prerrequisitos:
1. Tener **XAMPP** o un servidor **MySQL** local corriendo en el puerto `3306`.
2. Tener el JDK 17 o superior instalado.

### Compilación:
Desde la carpeta raíz del proyecto (`TallerMecanico/`):
```bash
mkdir bin
find src -name "*.java" > sources.txt
javac -encoding UTF-8 -cp "lib/mysql-connector-j.jar;lib/slf4j-api.jar;lib/slf4j-nop.jar" -d bin @sources.txt
```
*(En Linux/macOS, usa `:` en lugar de `;` para separar el classpath).*

### Ejecución:
```bash
java -cp "bin;lib/mysql-connector-j.jar;lib/slf4j-api.jar;lib/slf4j-nop.jar" com.taller.Main
```
*(En Linux/macOS, usa `:` en lugar de `;` para separar el classpath).*

---

## 4. Acceso al Sistema

Al ejecutarse por primera vez:
1. El programa creará automáticamente la base de datos `hotwheels_tam` en tu servidor local de MySQL y creará las 8 tablas con datos de prueba sembrados.
2. Se crea el usuario administrador por defecto:
   - **Usuario (email):** `admin@gmail.com`
   - **Contraseña:** `admin123`

*(También puedes registrar nuevos usuarios tipo **Cliente** desde el botón "Crear cuenta nueva" en la pantalla de inicio de sesión).*

---

## 5. Implementación de los 4 Pilares de la POO

| Pilar | Dónde se aplica en el desarrollo |
|---|---|
| **Abstracción** | Clase abstracta `Persona` (no se puede instanciar directamente, define atributos base y métodos abstractos) y la clase abstracta `ItemPresupuesto`. |
| **Herencia** | `Cliente extends Persona`, `Mecanico extends Persona`, `ItemRefaccion extends ItemPresupuesto` e `ItemManoObra extends ItemPresupuesto`. |
| **Polimorfismo** | 1. El método `resumen()` en `Persona` delega la llamada a `getRolDescriptivo()`, el cual está sobreescrito de manera distinta en `Cliente` y `Mecanico`. <br>2. `OrdenReparacion.calcularTotal()` recorre una lista de tipo `List<ItemPresupuesto>` llamando a `calcularSubtotal()`, que se resuelve dinámicamente en tiempo de ejecución según si el objeto real es un repuesto (`ItemRefaccion`) o servicio (`ItemManoObra`). |
| **Encapsulamiento** | Todos los atributos de los modelos son privados (`private`) y se acceden exclusivamente mediante métodos `getters` y `setters` con validaciones (ej. validar que las placas no estén vacías, o que las contraseñas se almacenen de forma segura usando hash SHA-256). |

---

## 6. Módulo de Auditoría y Bitácora

* **Bitácora Automática:** Cada acción del sistema (inicios/cierres de sesión, creación de clientes, registro de vehículos, cambios en órdenes, actualizaciones de refacciones) llama a `BitacoraDAO.registrar(accion, detalle)` para almacenar una bitácora persistente del usuario que realizó la acción, la fecha/hora y el detalle del cambio.
* **Panel de Auditoría:** Permite a los usuarios con rol `ADMINISTRADOR` visualizar un historial completo de qué pasó en el sistema, a qué hora y quién lo modificó.
* **Panel de Estatus:** Muestra en tiempo real la cantidad de vehículos, clientes, mecánicos y órdenes registradas para supervisar el estatus general del sistema.
