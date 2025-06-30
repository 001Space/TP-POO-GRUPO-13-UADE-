# TP-POO-GRUPO-13-UADE-
TP de Paradigma Orientado a objetos (Grupo 13)
Sistema de Gestión de Eventos - Grupo 13 (UADE)
Este proyecto es una aplicación de consola desarrollada en Java que permite gestionar eventos, registrar ventas de entradas y facilitar reservas tanto para administradores como para clientes.
Descripción general
La aplicación permite a un administrador crear, modificar y eliminar eventos, así como visualizar las ventas realizadas. Por otro lado, los clientes pueden consultar los eventos disponibles y reservar entradas seleccionando la cantidad deseada y el método de pago. El sistema guarda y carga la información utilizando archivos binarios para mantener los datos persistentes entre ejecuciones.
Tecnologías utilizadas
Lenguaje de programación: Java (versión 8 o superior)
 Paradigma aplicado: Programación Orientada a Objetos
 Persistencia de datos: Serialización de objetos en archivos .dat
 Interfaz de usuario: Consola (modo texto)
Estructura del proyecto
El sistema está compuesto por las siguientes clases principales:
Main: contiene el flujo de ejecución y los menús de interacción.


Evento: representa un evento con nombre, fecha, precio y ubicación.


Zona: gestiona la capacidad de cada evento.


Usuario: representa a los clientes.


Administrador: permite validar el acceso de administración y crear eventos.


Entrada: representa una entrada vendida.


Reserva: enlaza un usuario con una entrada reservada.


Además, se utiliza el archivo eventos.dat para guardar y recuperar la lista de eventos


Roles del sistema
Administrador: accede al sistema con el email admin@eventos.com y la contraseña admin. Puede crear nuevos eventos, modificarlos, eliminarlos y consultar estadísticas de ventas.
Cliente: ingresa con su nombre y correo electrónico. Puede ver la lista de eventos disponibles, seleccionar entradas y confirmar una compra indicando método de pago.
Persistencia de datos
El archivo eventos.dat almacena de forma automática todos los eventos y sus ventas. Si el archivo existe al iniciar el programa, los datos se cargarán automáticamente. Cualquier modificación o compra actualiza este archivo.
