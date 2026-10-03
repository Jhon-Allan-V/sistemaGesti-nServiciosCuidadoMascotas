# Sistema de Gestión de Servicios de Cuidado de Mascotas

Este repositorio contiene la implementación de un sistema para la gestión de servicios de cuidado de mascotas.

El sistema permite administrar clientes, mascotas, servicios de peluquería y veterinaria, además de realizar y cancelar reservas.

El proyecto fue desarrollado en Java aplicando conceptos de Programación Orientada a Objetos, colecciones, herencia, sobrecarga, sobreescritura, manejo de excepciones y persistencia de datos mediante archivos CSV.

El programa puede utilizarse mediante consola o mediante una interfaz gráfica.

---

## 💻 Requisitos

- JDK 8 o superior.
- Visual Studio Code, NetBeans, Eclipse o cualquier IDE compatible con Java.
- Git, en caso de querer clonar el repositorio.

---

## 🛠️ Instalación

Clonar el repositorio:

```bash
git clone https://github.com/Jhon-Allan-V/sistemaGesti-nServiciosCuidadoMascotas.git
```

Luego abrir la carpeta del proyecto en el IDE de preferencia.

---

## ⚡ Ejecución

Ejecutar la clase:

```text
src/cl/pucv/mascotas/Main.java
```

Al iniciar el programa, el usuario puede elegir el modo de ejecución:

```text
1. Modo Consola o Terminal
2. Modo Ventana o Interfaz
```

Ambas opciones permiten utilizar las principales funciones del sistema.

---

## 📝 Funcionalidades principales

El sistema permite:

- Registrar, listar, buscar, modificar y eliminar clientes.
- Registrar, listar, buscar, modificar y eliminar mascotas.
- Asociar mascotas a sus respectivos dueños.
- Registrar servicios de peluquería.
- Registrar servicios de veterinaria.
- Listar servicios disponibles.
- Realizar reservas de servicios.
- Cancelar reservas.
- Listar reservas.
- Calcular el total de reservas activas de un cliente.
- Guardar y cargar información mediante archivos CSV.
- Utilizar el sistema mediante consola o interfaz gráfica.

---

## 📂 Estructura del proyecto

El proyecto está organizado en paquetes según la responsabilidad de cada clase.

```text
src/
└── cl/
    └── pucv/
        └── mascotas/
            ├── Main.java
            ├── Aplicacion.java
            │
            ├── config/
            │   └── InformacionLocal.java
            │
            ├── ui/
            │   ├── InterfazUsuario.java
            │   ├── ConsolaTerminal.java
            │   ├── PantallaInterfaz.java
            │   └── Eleccion.java
            │
            ├── facade/
            │   └── SistemaFacade.java
            │
            ├── controller/
            │   ├── GestorClientes.java
            │   ├── GestorMascotas.java
            │   └── GestorServicios.java
            │
            ├── model/
            │   ├── Cliente.java
            │   ├── Mascota.java
            │   ├── Reserva.java
            │   ├── Servicio.java
            │   ├── Peluqueria.java
            │   └── Veterinaria.java
            │
            ├── repository/
            │   ├── Repositorio.java
            │   ├── RepositorioClienteCSV.java
            │   ├── RepositorioMascotaCSV.java
            │   └── RepositorioServicioCSV.java
            │
            └── exception/
                ├── ClienteNoEncontradoException.java
                └── PersistenciaException.java
```

---

## 📦 Paquetes principales

### `cl.pucv.mascotas`

Contiene las clases principales para iniciar el programa.

- `Main.java`: punto de entrada de la aplicación.
- `Aplicacion.java`: crea los objetos necesarios e inicia el sistema.

### `cl.pucv.mascotas.ui`

Contiene las dos formas de utilizar el programa.

- `InterfazUsuario`: define las operaciones básicas que debe tener una interfaz.
- `ConsolaTerminal`: permite utilizar el sistema mediante consola.
- `PantallaInterfaz`: contiene la interfaz gráfica.
- `Eleccion`: permite seleccionar el modo de ejecución.

### `cl.pucv.mascotas.facade`

- `SistemaFacade`: centraliza las operaciones utilizadas por las interfaces y comunica la interfaz con los gestores.

### `cl.pucv.mascotas.controller`

Contiene las clases encargadas de manejar las operaciones principales del sistema.

- `GestorClientes`
- `GestorMascotas`
- `GestorServicios`

Estas clases administran los datos en memoria y realizan las operaciones principales del sistema.

### `cl.pucv.mascotas.model`

Contiene las clases que representan los datos principales del sistema.

- `Cliente`
- `Mascota`
- `Reserva`
- `Servicio`
- `Peluqueria`
- `Veterinaria`

`Servicio` es una clase abstracta y `Peluqueria` y `Veterinaria` sobrescriben el método `calcularPrecioServicio()`.

### `cl.pucv.mascotas.repository`

Contiene las clases encargadas de leer y guardar la información en archivos CSV.

- `Repositorio`
- `RepositorioClienteCSV`
- `RepositorioMascotaCSV`
- `RepositorioServicioCSV`

### `cl.pucv.mascotas.exception`

Contiene las excepciones personalizadas utilizadas por el sistema.

- `ClienteNoEncontradoException`
- `PersistenciaException`

---

## 🧩 Conceptos implementados

### Colecciones

El proyecto utiliza colecciones del Java Collections Framework.

En `GestorClientes` se utiliza un `Map` para almacenar los clientes utilizando el RUT como clave.

Además, cada objeto `Cliente` mantiene internamente otra colección:

```java
Map<String, Mascota>
```

De esta forma, cada cliente puede mantener asociadas sus propias mascotas.

### Sobrecarga de métodos

En `GestorMascotas` existen dos versiones del método `agregarMascota()`.

Una permite registrar los datos básicos de una mascota y la otra permite agregar también información de trato especial.

En `SistemaFacade` se utiliza el mismo principio mediante dos versiones de `registrarMascota()`.

Esto permite registrar mascotas con o sin información adicional.

### Sobreescritura de métodos

La clase abstracta `Servicio` define el método `calcularPrecioServicio()`.

Las clases `Peluqueria` y `Veterinaria` sobrescriben este método.

Esto permite utilizar la misma operación para distintos tipos de servicio y aplicar el cálculo correspondiente según el objeto.

### Funcionalidad propia del negocio

El sistema permite calcular el total de reservas activas de un cliente.

Para realizar el cálculo:

1. Se revisan las reservas registradas.
2. Se seleccionan las que pertenecen al cliente indicado.
3. Se consideran solamente las reservas con estado `ACTIVA`.
4. Se obtiene el precio de los servicios asociados.
5. Se calcula el total.

Esta función permite conocer el valor de las atenciones que el cliente todavía mantiene vigentes.

### Consola e interfaz gráfica

El programa puede utilizarse de dos formas:

- **Consola:** mediante menús y opciones numéricas.
- **Interfaz gráfica:** mediante ventanas y botones.

Ambos modos permiten acceder a las funciones principales del sistema.

---

## 💾 Persistencia de datos

La información se almacena en la carpeta `data` mediante archivos CSV:

```text
data/
├── clientes.csv
├── mascotas.csv
├── servicios.csv
└── reservas.csv
```

Los datos se cargan cuando se inicia el programa y se guardan nuevamente cuando el usuario sale del sistema.

---

## 🔄 Control de versiones

Durante el desarrollo se utilizó Git y GitHub para mantener un historial de los cambios realizados.

Los commits se utilizaron para registrar:

- Correcciones.
- Nuevas funcionalidades.
- Mejoras en la interfaz.
- Persistencia de datos.
- Gestión de servicios.
- Gestión de mascotas.
- Cambios en el cálculo de precios.

---

## ⚙️ Tecnologías utilizadas

- Java
- JDK 8
- Java Swing
- Java Collections Framework
- Git
- GitHub

---

## 👥 Integrantes

- Roberto Osses Espinosa
- Jhon Veliz Hansen
- Diego Rojas Cartagena