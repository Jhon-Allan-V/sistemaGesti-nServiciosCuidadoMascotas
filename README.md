# Sistema de Gestión de Servicios de Cuidado de Mascotas (SIA) - Arquitectura v2

Este repositorio contiene la implementación del **Sistema de Información (SIA)** diseñado para la gestión integral de un centro de cuidado de mascotas (veterinaria, peluquería y hotel canino). El software ha sido estructurado bajo los principios de la Programación Orientada a Objetos (POO), aplicando patrones de diseño de software avanzados para lograr una arquitectura limpia, modular, altamente desacoplada y compatible con **JDK 8 (Java 1.8)**.

---

## 📂 Estructura de Directorios y Paquetes

La arquitectura de este sistema de información se divide en capas con responsabilidades bien delimitadas, garantizando un código ordenado y escalable:

```text
src/
└── cl/
    └── pucv/
        └── mascotas/
            ├── Main.java                                 # Punto de entrada mínimo de la aplicación
            ├── Aplicacion.java                           # Composition Root (Orquestador e iniciador global)
            │
            ├── config/
            │   └── InformacionLocal.java                # Atributos del local físico (dirección, fono, etc.)
            │
            ├── ui/
            │   ├── InterfazUsuario.java                 # Interfaz polimórfica común para motores de UI
            │   ├── ConsolaTerminal.java                 # Implementación para interfaz de consola (Scanner)
            │   ├── PantallaInterfaz.java                 # Implementación para interfaz gráfica (GUI Swing)
            │   └── Eleccion.java                        # Lógica de decisión del modo de ejecución de UI
            │
            ├── facade/
            │   └── SistemaFacade.java                   # Patrón Facade: Orquestador y simplificador del sistema
            │
            ├── controller/
            │   ├── GestorClientes.java                  # Lógica de negocio y CRUD del mapa de Clientes
            │   ├── GestorMascotas.java                  # Lógica de negocio y CRUD de Mascotas asociadas
            │   └── GestorServicios.java                 # Gestión de reservas, citas e historial de servicios
            │
            ├── model/
            │   ├── Cliente.java                         # Entidad de dominio Cliente (atributos privados)
            │   ├── Mascota.java                         # Entidad de dominio Mascota (id, rutDueno)
            │   ├── Reserva.java                         # Entidad relacional para el agendamiento de citas
            │   ├── Servicio.java                        # Clase abstracta base de servicios del local
            │   ├── Peluqueria.java                      # Subclase con tarifas y servicios de estética
            │   └── Veterinaria.java                     # Subclase con lógica de atención médica
            │
            ├── repository/
            │   ├── Repositorio.java                     # Interfaz genérica <T, ID> para persistencia
            │   ├── RepositorioClienteCSV.java           # Persistencia de clientes en archivos CSV
            │   ├── RepositorioMascotaCSV.java           # Persistencia de mascotas en archivos CSV
            │   └── RepositorioServicioCSV.java          # Persistencia de reservas en archivos CSV
            │
            └── exception/
                ├── ClienteNoEncontradoException.java    # Excepción personalizada para el flujo de clientes
                └── PersistenciaException.java           # Excepción personalizada para fallos de archivos
```

---

## 🏛️ Descripción de Paquetes y Responsabilidades

### 1. Paquete Raíz (`cl.pucv.mascotas`)
Este paquete contiene los activadores globales del sistema:
*   **`Main.java`:** Mantiene un punto de entrada limpio y enfocado, delegando la ejecución inmediatamente a la clase orquestadora.
*   **`Aplicacion.java`:** Actúa como el **Composition Root**. Se encarga de instanciar e inyectar las dependencias del sistema (repositorios CSV, controladores y fachada) antes de iniciar la interfaz de usuario elegida.

### 2. Configuración (`cl.pucv.mascotas.config`)
*   **`InformacionLocal.java`:** Centraliza y encapsula los datos generales del establecimiento (nombre, dirección, fono, correo). Esto desacopla la información corporativa de la lógica gráfica o de terminal.

### 3. Interfaz de Usuario (`cl.pucv.mascotas.ui`)
Abstrae la capa de presentación aplicando polimorfismo puro:
*   **`InterfazUsuario` (Interface):** Obliga a que cualquier interfaz cuente con un ciclo de vida definido (`iniciar`, `mostrarMenu`, `finalizar`).
*   **`ConsolaTerminal` y `PantallaInterfaz`:** Hacen uso exclusivo de la fachada del sistema para interactuar con la lógica del negocio. Ninguna de estas vistas modifica colecciones ni escribe archivos directamente.
*   **`Eleccion`:** Clase que decide dinámicamente qué interfaz inicializar basándose en la configuración o selección del usuario.

### 4. Fachada (`cl.pucv.mascotas.facade`)
*   **`SistemaFacade`:** Implementa el **Patrón de Diseño Facade (Fachada)**. Funciona como una interfaz simplificada y único punto de contacto entre la UI y los gestores de negocio complejos. Evita que la interfaz de usuario se acople directamente con múltiples controladores.

### 5. Controladores (`cl.pucv.mascotas.controller`)
Contiene las clases de control operacional:
*   **`GestorClientes`, `GestorMascotas` y `GestorServicios`:** Manejan las colecciones de datos en memoria e implementan las operaciones CRUD (crear, leer, actualizar, eliminar) aplicando las reglas operativas del negocio.

### 6. Modelo (`cl.pucv.mascotas.model`)
Representa el dominio puro del negocio (Entidades/POJOs):
*   **`Cliente`, `Mascota` y `Reserva`:** Clases con variables privadas de datos, constructores, getters y setters.
*   **`Servicio` (Clase Base Abstracta), `Peluqueria` y `Veterinaria` (Subclases):** Implementan el polimorfismo por sobreescritura del método `calcularPrecio()`, el cual se adapta de forma especializada según las tarifas, insumos y cargos de cada tipo de atención.

### 7. Persistencia (`cl.pucv.mascotas.repository`)
Desacopla el almacenamiento físico de la aplicación mediante el **Patrón Repository**:
*   **`Repositorio<T, ID>` (Interfaz Genérica):** Define los métodos estándar de persistencia (`guardar`, `eliminar`, `buscarPorId`, `listarTodos`).
*   **Implementaciones CSV:** Leen y escriben archivos de texto plano utilizando codificación estándar compatible con cargas masivas batch al inicio y cierres automáticos al apagar el software.

### 8. Excepciones (`cl.pucv.mascotas.exception`)
*   **`ClienteNoEncontradoException` y `PersistenciaException`:** Excepciones personalizadas que heredan de `Exception` para capturar e informar de manera segura fallos específicos de entrada/salida o de búsqueda de datos, evitando cierres inesperados de la aplicación.

---

## 🛠️ Alineación con la Rúbrica de Evaluación (SIA)

*   **Modularización y Encapsulamiento (SIA-3):** Estructura modular dividida en 8 paquetes. Atributos de todas las clases declarados como `private` con acceso exclusivo por *getters* y *setters*.
*   **Colecciones Estructuradas (SIA-4):** Uso de la interfaz `Map` y su implementación `HashMap` en las capas controladoras. El enlace entre entidades se realiza de forma relacional (mediante llaves foráneas como `rutDueno` o `idMascota`) evitando acoplamientos rígidos en memoria.
*   **Polimorfismo por Sobreescritura (SIA-6):** La jerarquía de `Servicio` utiliza herencia para sobreescribir el método de cálculo de precios en sus clases hijas.
*   **Persistencia Batch en Texto (SIA-11):** Carga inicial de datos desde archivos CSV al arrancar y exportación automática al salir del sistema.
*   **Interfaz Dual Consola/Ventanas (SIA-10):** Soporte dinámico para interactuar a través de consola o ventanas con Swing usando una interfaz de UI común.
*   **Excepciones Propias (SIA-12):** Implementación de al menos 2 excepciones personalizadas controladas mediante bloques `try-catch`.
