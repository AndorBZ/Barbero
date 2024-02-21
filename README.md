# Solución Barbero

## 1) Decisión del Cliente
  - ### ¿Qué factores influyen en esta decisión y cómo se puede implementar esta lógica de manera que sea coherente con el comportamiento esperado?
### __Respuesta__:
#### Cuando el cliente llega a la barbería (__se ejecuta el *.start* del hilo__) comprueba si hay sillas disponibles donde esperar a ser atendido (**en realidad *las sillas* es una linkered list**), habiendo únicamente 5 sillas donde poder sentarse (__el cliente comprobará si la longitud de la linkered list es igual o superior a 5__). 
#### En el mejor de los casos, habŕa al menos una silla donde poder sentarse (__en ese caso se añadirá a la linkered list con *.add*__)
#### En el caso de que no haya sillas disponibles, el cliente se marchará (__se interrumpe la ejecución del hilo con *.interrupt*__)

## 2) Manejo de la Cola de Espera
  - ### ¿Cómo garantizarías que los clientes sean atendidos en el orden correcto, especialmente cuando el barbero se desocupa y está listo para atender al siguiente cliente?
### __Respuesta__:
#### En este caso he utilizado *Queue* para representar la cola de espera porque, según he leído, está más enfocado al sistema FIFO y es más estricto a la hora de implementarlo que *Linkered List*, aunque ambas listas son válidas.

## 3) Concurrencia y Sincronización
  - ### ¿Cómo asegurarías que el barbero no sea despertado por un cliente cuando ya está atendiendo a otro?
  - ### ¿Cómo manejarías las situaciones en las que múltiples clientes llegan al mismo tiempo cuando solo queda una silla de espera disponible?
### __Respuesta__:
#### Cuando el hilo *Cliente* es ejecutado, *.start* comprueba antes que nada la longitud de la lista, si está completamente vacía, llamará al método *.signal* de la condición de bloqueo del hilo *Barbero* para despertarlo.
#### Cada hilo *Cliente* que quiera acceder a la lista bloquerá el flujo de acceso a la misma con un *Lock*. El objetivo es evitar que los hilos accedan de manera simultánea a la lista.

## 4) Justicia y Eficiencia
  - ### ¿Cómo impactan tus decisiones de diseño en este equilibrio?
### __Respuesta__:
#### Para la eficiencia, he pensado en bloquear el flujo de acceso a la lista cuando el *Barbero* elige a su próximo *Cliente*, pero durante el corte (que dura entre 5 y 8 seg.), el flujo se libera para que los hilos *Cliente* puedan acceder a ella. Por último, para eliminar al cliente atendido de la lista, se vuelve a bloquear el flujo momentáneamente.
