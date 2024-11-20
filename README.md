## PR2

## Author
- name: Amador Narbona Sánchez
- e-mail: anarbonas@uoc.edu

## Alcance de la entrega

## Modificaciones y/o actualizaciones realizadas sobre el diseño inicial propuesto

### Interfaz Library

#### Clase LibraryPR2Impl
- He añadido algunos métodos privados para encapsular ciertas funciones y/o comportamientos de los métodos públicos. Están al final de la clase y con un comentario de bloque que marca la separación.

#### Clase BookWareHose
- He añadido algunos métodos privados para encapsular ciertas funciones y/o comportamientos de los métodos públicos. Están al final de la clase y con un comentario de bloque que marca la separación.

### Clase de pruebas LibraryPR2Test

#### Método timeToBeCatalogedTest
- Modifico el Assert.assertEquals(244, t) a Assert.assertEquals(256, t). Como hemos comentando en el foro, 244 minutos serían hasta el libro, pero no lo incluirían.

### Clases de excepciones

- Añado una nueva excepción que se lanzará cuando se quiera añadir un nuevo lector al array pero no quepan más. La he llamado **MaxNumReadersException**.

### Modificaciones al modelo

#### CataloguedBook

- Añado un nuevo atributo llamado **idWorker** que contiene el id del trabajador que catalogó por primera vez el libro.

## Indicaciones sobre los nuevos juegos de pruebas

### BookWareHouseANSTest
- **catalogingEmptyWareHouseTest** -> esta prueba valida que no se permita catalogar un libro si la cola está vacía. Si la cola está vacía, el método getBookPendingCataloging() devuelve NULL.

- **duplicateBookStoreTest** -> esta prueba valida que se permitan añadir libros repetidos a la cola.

- **catalogingOrderTest** -> esta prueba valida que se siga el orden de catalogacion esperado:
  - FIFO para sacar pilas de la cola.
  - LIFO para sacar libros de la pila.

- **massStorageTest** -> esta prueba valida que el sistema permite encolar libros de manera masiva.

- **deleteOfStackIfEmpty** --> esta prueba valida que cuando se extrae el último libro de una pila, la pila se saca de la cola.

### LibraryPR2ANSTest
- **massStorageTest** -> esta prueba valida que el sistema permite encolar libros de manera masiva. Es la misma que he añadido en BookWareHouseTest, pero probando los métodos de LibraryPR2Impl.

- **noBooksRemainingTest** -> esta prueba valida que cuando se hayan catalogado todos los libros que hay en la cola, si se trata de catalogar uno más, salte la excepción NoBookException.

- **bookLoanAvailabilityTest** -> esta prueba valida que cuando se hayan catalogado todos los libros que hay en la cola, si se trata de catalogar.

- **completeBookLoanCycleTest** -> esta prueba valida que el flujo completo de préstamo y devolución de los libros

## Problemas

### LibraryPR2Test

#### timeToBeCatalogedTest
- En esté método, al final, encontramos el siguiente código:

  >     /**
  >     	* position = bookWareHouse.getPosition("HP4a");
  >     	* Assert.assertEquals(2, position.getNumStack());
  >     	* Assert.assertEquals(9, position.getNum());
  >     */
  >     int t = theLibrary.timeToBeCataloged("HP4a", 8, 12);  
  >       
  >     Assert.assertEquals(244, t);

- Lo he comentado con el profesor, con Ángel, porque en este punto el libro **HP4a** está en la segunda pila de la cola, pero las pilas van numeradas de 0 a N, por lo que **position.getNumStack()** debe dar 1.
- Si el libro está en la cola 1 posición 9, hay que procesar la pila anterior totalmente, junto con sus 10 libros, y luego hay que procesar la segunda cola, dónde está **HP4a**, que como entró el primero en la cola, está abajo del todo, y hay que procesar también 10 libros. El tiempo de 244 minutos no es correcto, debe dar **255** minutos.

## Comentarios adicionales  
