## PR2

## Author
- name: Amador Narbona Sánchez
- e-mail: anarbonas@uoc.edu

## 
## Overview

- Aquí tenemos que indicar lo siguiente:
- Alcance de la entrega.
- Modificaciones y/o actualizaciones realizadas sobre el diseño inicial propuesto (solución oficial de la PEC1) con su justificación en el caso de que haya sido necesaria alguna estructura de datos adicional.
- Indicaciones sobre los nuevos juegos de pruebas.
- Problemas.
- Comentarios adicionales.

## Notas
- Añado una nueva excepción que se lanzará cuando se quiera añadir un nuevo lector al array pero no quepan más. La he llamado MaxNumReadersException.
- En la clase CatalogedBook añado un nuevo atributo llamado idWorker que contiene el id del trabajador que catalogó por primera vez el libro.

## Tests Adicionales

### BookWareHouseTest
- catalogingEmptyWareHouseTest -> esta prueba valida que no se permita catalogar un libro si la cola está vacía. Si la cola está vacía, el método getBookPendingCataloging() devuelve NULL
- duplicateBookStoreTest -> esta prueba valida que se permitan añadir libros repetidos a la cola.
- catalogingOrderTest -> esta prueba valida que se siga el orden de catalogacion esperado:
  - FIFO para sacar pilas de la cola.
  - LIFO para sacar libros de la pila.
- massStorageTest -> esta prueba valida que el sistema permite encolar libros de manera masiva.
- deleteOfStackIfEmpty --> esta prueba valida que cuando se extrae el último libro de una pila, la pila se saca de la cola.


