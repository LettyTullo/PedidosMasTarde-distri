# Sistema 2: Empresa de Delivery "PedidosMasTarde"

Integrantes: Camila Martinez, Letizia Tullo, Franco Espinola

## Servicio de eBye que consume (implementado)

### Consultar existencia de productos (UDP)

PedidosMasTarde actúa como **cliente**: envía un datagrama a eBye y espera la respuesta (timeout de 3 segundos).

```json
{
  "tipo": "GET_STOCK",
  "requestId": "req-1",
  "idproducto": 305
}
```

Respuesta de eBye:

```json
{
  "ok": true,
  "requestId": "req-1",
  "stock_disponible": 15,
  "timestamp": "2026-10-10T15:30:00-03:00"
}
```

El contrato completo (incluidos los errores) está en el `docs/eBye.md` del proyecto eBye.

## Servicios propios (pendientes, según la documentación del grupo)

- Servicio 1: Solicitar transporte (TCP)
- Servicio 2: Cotizar envío (UDP)
- Servicio 3: Cancelar o reprogramar envío (TCP)

Las tablas `conductor`, `ruta` y `envio` de `schema.sql` están preparadas para estos servicios.
