# PedidosMasTarde

Empresa de delivery del proyecto de Sistemas Distribuidos (Tarea 3). Gestiona envíos, rutas y conductores. Se comunica con **eBye** por paso de mensajes sobre sockets TCP/UDP.

> Estado actual: PedidosMasTarde funciona como **cliente** de eBye y consulta el stock de un producto por UDP. Sus servicios propios (solicitar transporte, cotizar envío, cancelar/reprogramar) todavía no están implementados.

## Stack

- Java 1.8
- Apache Maven (fat JAR con `maven-shade-plugin`)
- PostgreSQL 12+
- Sockets UDP
- JSON via `json-simple 1.1.1`
- Driver JDBC `postgresql 42.7.4`

## Prerrequisitos

- JDK 1.8 o superior instalado y disponible en `PATH`
- Maven 3.6 o superior
- El servidor de **eBye** corriendo (ver el README de eBye)
- PostgreSQL 12 o superior, solo si querés crear la base de PedidosMasTarde (todavía no la usa el programa)

## Setup

### 1. (Opcional) Crear la base de datos

```sql
CREATE DATABASE pedidosmastarde;
```

```bash
psql -U postgres -d pedidosmastarde -f schema.sql
```

### 2. Compilar y empaquetar

```bash
mvn clean package
```

Genera un fat JAR ejecutable en `target/pedidosmastarde-1.0-SNAPSHOT.jar`.

### 3. Ejecutar

Con el servidor de eBye ya levantado:

```bash
java -jar target/pedidosmastarde-1.0-SNAPSHOT.jar
```

Por defecto consulta el producto `305` en `127.0.0.1:6000`. Los argumentos son `host puerto idproducto`:

```bash
java -jar target/pedidosmastarde-1.0-SNAPSHOT.jar 127.0.0.1 6000 306
```

## Estructura del proyecto

```
PedidosMasTarde-distri/
├── pom.xml                                      Configuración de Maven y dependencias
├── schema.sql                                   Tablas conductor, ruta y envio
├── docs/PedidosMasTarde.md                      Servicios y mensajes
└── src/main/java/fpuna/pedidosmastarde/
    └── Main.java                                Cliente UDP que consulta el stock a eBye
```

## Comunicación distribuida

- **UDP**: consulta de stock a eBye (cliente).
- Pendiente: servicios TCP/UDP propios de PedidosMasTarde.
