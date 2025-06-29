import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
   static Scanner sc;
   static Random random;
   static List<Evento> eventos;
   static Administrador admin;
   static final String ARCHIVO_EVENTOS = "eventos.dat";

   public Main() {
   }

   public static void main(String[] args) {
      cargarEventosDesdeArchivo();

      while(true) {
         while(true) {
            System.out.println("\n\ud83c\udfaf Bienvenido al Sistema de Gestión de Eventos");
            System.out.print("Ingresar como administrador (A)\nIngresar como cliente (B)\nSalir del programa (S)\nTu opción: ");
            String rol = sc.nextLine().trim().toUpperCase();
            if (rol.equals("A")) {
               boolean autenticado = false;

               for(int intentos = 1; intentos <= 3; ++intentos) {
                  System.out.print("\ud83d\udce8 Ingresá tu email de admin: ");
                  String emailAdmin = sc.nextLine();
                  System.out.print("\ud83d\udd11 Ingresá tu contraseña: ");
                  String passAdmin = sc.nextLine();
                  if (admin.autenticar(emailAdmin, passAdmin)) {
                     System.out.println("\ud83d\udd13 Acceso concedido. Bienvenido administrador.");
                     menuAdministrador();
                     autenticado = true;
                     break;
                  }

                  System.out.println("Datos incorrectos. Intento " + intentos + "/3");
               }

               if (!autenticado) {
                  System.out.println("\ud83d\udeab Se superaron los intentos permitidos. Volviendo al menú principal.\n");
               }
            } else if (rol.equals("B")) {
               flujoCliente();
            } else if (rol.equals("S")) {
               System.out.print("¿Estás seguro que querés cerrar el programa? (s/n): ");
               String salir = sc.nextLine().trim().toLowerCase();
               if (salir.equals("s")) {
                  guardarEventosEnArchivo();
                  System.out.println("\ud83d\udc4b Cerrando programa. Hasta luego.");
                  return;
               }

               System.out.println("\ud83d\udd19 Cancelado. Volviendo al menú principal.");
            } else {
               System.out.println("Opción inválida. Volvé a intentarlo.");
            }
         }
      }
   }

   public static void guardarEventosEnArchivo() {
      try {
         ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("eventos.dat"));

         try {
            out.writeObject(eventos);
            System.out.println("\ud83d\udcbe Eventos guardados correctamente.");
         } catch (Throwable var4) {
            try {
               out.close();
            } catch (Throwable var3) {
               var4.addSuppressed(var3);
            }

            throw var4;
         }

         out.close();
      } catch (IOException var5) {
         System.out.println("Error al guardar los eventos: " + var5.getMessage());
      }

   }

   public static void cargarEventosDesdeArchivo() {
      File archivo = new File("eventos.dat");
      if (archivo.exists()) {
         try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo));

            try {
               eventos = (List)in.readObject();
               System.out.println("\ud83d\udcc2 Eventos cargados desde archivo.");
            } catch (Throwable var5) {
               try {
                  in.close();
               } catch (Throwable var4) {
                  var5.addSuppressed(var4);
               }

               throw var5;
            }

            in.close();
         } catch (ClassNotFoundException | IOException var6) {
            System.out.println("No se pudieron cargar los eventos: " + var6.getMessage());
         }

      }
   }

   public static void menuAdministrador() {
      while(true) {
         System.out.println("\n\ud83d\udd27 MENÚ ADMINISTRADOR");
         System.out.println("1. Agregar evento");
         System.out.println("2. Modificar evento existente");
         System.out.println("3. Ver eventos cargados");
         System.out.println("4. Ver ventas totales por evento");
         System.out.println("5. Eliminar evento");
         System.out.println("0. Volver al menú principal");
         System.out.print("Elegí una opción: ");
         switch (sc.nextLine()) {
            case "1":
               agregarEvento();
               break;
            case "2":
               modificarEvento();
               break;
            case "3":
               mostrarEventos();
               break;
            case "4":
               verVentasTotales();
               break;
            case "5":
               eliminarEvento();
               break;
            case "0":
               System.out.println("\ud83d\udd19 Volviendo al menú principal...");
               return;
            default:
               System.out.println("Opción inválida.");
         }
      }
   }

   public static void agregarEvento() {
      System.out.print("\ud83d\udcdb Nombre del evento (0 para cancelar): ");
      String nombre = sc.nextLine();
      if (nombre.equals("0")) {
         System.out.println("\ud83d\udd19 Cancelando creación de evento.");
      } else {
         System.out.print("\ud83d\udccd Ubicación: ");
         String ubicacion = sc.nextLine();
         System.out.print("\ud83e\ude91 Capacidad: ");
         int capacidad = Integer.parseInt(sc.nextLine());
         System.out.print("\ud83d\udcb5 Precio entrada: ");
         double precio = Double.parseDouble(sc.nextLine());

         LocalDate fecha;
         while(true) {
            System.out.print("\ud83d\udcc5 Ingresá la fecha del evento (dd/MM/yyyy): ");
            String fechaStr = sc.nextLine();

            try {
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
               fecha = LocalDate.parse(fechaStr, formatter);
               if (!fecha.isBefore(LocalDate.now())) {
                  break;
               }

               System.out.println("La fecha no puede ser en el pasado.");
            } catch (DateTimeParseException var8) {
               System.out.println("Formato de fecha inválido.");
            }
         }

         Zona zona = new Zona(ubicacion, capacidad);
         Evento nuevoEvento = admin.crearEvento(nombre, zona, precio, fecha);
         eventos.add(nuevoEvento);
         guardarEventosEnArchivo();
         System.out.println("Evento agregado exitosamente para el " + fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
      }
   }

   public static void modificarEvento() {
      if (eventos.isEmpty()) {
         System.out.println("No hay eventos cargados.");
      } else {
         mostrarEventos();
         System.out.print("\ud83d\udd22 Ingresá el número del evento que querés modificar (0 para volver): ");
         String input = sc.nextLine();
         if (input.equals("0")) {
            System.out.println("\ud83d\udd19 Volviendo al menú administrador...");
         } else {
            int indice;
            try {
               indice = Integer.parseInt(input) - 1;
            } catch (NumberFormatException var9) {
               System.out.println("Entrada inválida");
               return;
            }

            if (indice >= 0 && indice < eventos.size()) {
               Evento e = (Evento)eventos.get(indice);
               System.out.println("Modificá los valores. Dejá vacío para mantener el actual.");
               System.out.print("Nuevo nombre (" + e.getNombre() + ") o 0 para cancelar: ");
               String nuevoNombre = sc.nextLine();
               if (nuevoNombre.equals("0")) {
                  System.out.println("\ud83d\udd19 Modificación cancelada.");
               } else {
                  if (!nuevoNombre.isEmpty()) {
                     e.setNombre(nuevoNombre);
                  }

                  System.out.print("Nueva capacidad (" + e.getZona().getCapacidad() + ") o 0 para cancelar: ");
                  String nuevaCapacidad = sc.nextLine();
                  if (nuevaCapacidad.equals("0")) {
                     System.out.println("\ud83d\udd19 Modificación cancelada.");
                  } else {
                     if (!nuevaCapacidad.isEmpty()) {
                        e.getZona().setCapacidad(Integer.parseInt(nuevaCapacidad));
                     }

                     System.out.print("Nuevo precio (" + e.getPrecioEntrada() + ") o 0 para cancelar: ");
                     String nuevoPrecio = sc.nextLine();
                     if (nuevoPrecio.equals("0")) {
                        System.out.println("\ud83d\udd19 Modificación cancelada.");
                     } else {
                        if (!nuevoPrecio.isEmpty()) {
                           e.setPrecioEntrada(Double.parseDouble(nuevoPrecio));
                        }

                        PrintStream var10000 = System.out;
                        LocalDate var10001 = e.getFecha();
                        var10000.print("Nueva fecha (" + var10001.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ") o enter para mantener: ");
                        String nuevaFecha = sc.nextLine();
                        if (!nuevaFecha.isEmpty()) {
                           try {
                              LocalDate fecha = LocalDate.parse(nuevaFecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                              e.setFecha(fecha);
                           } catch (DateTimeParseException var8) {
                              System.out.println("Fecha inválida. No se modificó.");
                           }
                        }

                        guardarEventosEnArchivo();
                        System.out.println("Evento modificado.");
                     }
                  }
               }
            } else {
               System.out.println("Evento inválido.");
            }
         }
      }
   }

   public static void eliminarEvento() {
      if (eventos.isEmpty()) {
         System.out.println("\ud83d\udced No hay eventos para eliminar.");
      } else {
         mostrarEventos();
         System.out.print("Ingresá el número del evento a eliminar (0 para volver): ");
         String input = sc.nextLine();
         if (input.equals("0")) {
            System.out.println("\ud83d\udd19 Cancelando eliminación.");
         } else {
            int index;
            try {
               index = Integer.parseInt(input) - 1;
            } catch (NumberFormatException var5) {
               System.out.println("Entrada inválida.");
               return;
            }

            if (index >= 0 && index < eventos.size()) {
               Evento elegido = (Evento)eventos.get(index);
               System.out.print("¿Estás seguro que querés eliminar '" + elegido.getNombre() + "'? (s/n): ");
               String confirmar = sc.nextLine().trim().toLowerCase();
               if (!confirmar.equals("s")) {
                  System.out.println("Eliminación cancelada.");
               } else {
                  Evento eliminado = (Evento)eventos.remove(index);
                  guardarEventosEnArchivo();
                  System.out.println("Evento '" + eliminado.getNombre() + "' eliminado correctamente.");
               }
            } else {
               System.out.println("Número inválido.");
            }
         }
      }
   }

   public static void mostrarEventos() {
      if (eventos.isEmpty()) {
         System.out.println("\ud83d\udced No hay eventos disponibles.");
      } else {
         System.out.println("\n\ud83d\udccb EVENTOS CARGADOS:");

         for(int i = 0; i < eventos.size(); ++i) {
            Evento e = (Evento)eventos.get(i);
            int libres = e.getZona().getCapacidad() - e.getZona().getOcupados();
            System.out.printf("%d. %s - %s - \ud83d\udcc5 %s - $%.2f - Capacidad: %d (libres: %d)\n", i + 1, e.getNombre(), e.getZona().getUbicacion(), e.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), e.getPrecioEntrada(), e.getZona().getCapacidad(), libres);
         }

      }
   }

   public static void verVentasTotales() {
      if (eventos.isEmpty()) {
         System.out.println("\ud83d\udced No hay eventos.");
      } else {
         System.out.println("\n\ud83d\udcca VENTAS TOTALES:");
         Iterator var0 = eventos.iterator();

         while(var0.hasNext()) {
            Evento e = (Evento)var0.next();
            int vendidas = e.getZona().getOcupados();
            double ingresos = (double)vendidas * e.getPrecioEntrada();
            System.out.printf("- %s: %d entradas vendidas, ingresos: $%.2f\n", e.getNombre(), vendidas, ingresos);
         }

      }
   }

   public static void flujoCliente() {
      System.out.print("\ud83d\udce7 Ingresá tu nombre: ");
      String nombre = sc.nextLine();
      System.out.print("\ud83d\udce8 Ingresá tu email: ");
      String email = sc.nextLine();
      Usuario usuario = new Usuario(nombre, email);
      System.out.println("\nBienvenido, " + usuario.getNombre() + "!");
      List<Reserva> reservasUsuario = new ArrayList();

      int eleccion;
      while(true) {
         if (eventos.isEmpty()) {
            System.out.println("\ud83d\udced No hay eventos disponibles.");
            break;
         }

         System.out.println("\n\ud83c\udfab Eventos disponibles:");
         boolean hayDisponibles = false;

         for(int i = 0; i < eventos.size(); ++i) {
            Evento ev = (Evento)eventos.get(i);
            int libres = ev.getZona().getCapacidad() - ev.getZona().getOcupados();
            if (libres > 0) {
               hayDisponibles = true;
               System.out.printf("%d. %s - %s - Fecha: %s - $%.2f - Capacidad restante: %d/%d\n", i + 1, ev.getNombre(), ev.getZona().getUbicacion(), ev.getFecha(), ev.getPrecioEntrada(), libres, ev.getZona().getCapacidad());
            }
         }

         if (!hayDisponibles) {
            System.out.println("\ud83d\udeab Todos los eventos están completos.");
            break;
         }

         System.out.print("\n\ud83d\udcdd Elegí el número del evento que querés reservar (0 para volver): ");
         String input = sc.nextLine();
         if (input.equals("0")) {
            System.out.println("\ud83d\udd19 Cancelando compra. Volviendo al menú principal...");
            break;
         }

         try {
            eleccion = Integer.parseInt(input);
         } catch (NumberFormatException var18) {
            System.out.println("Entrada inválida.");
            continue;
         }

         if (eleccion >= 1 && eleccion <= eventos.size()) {
            Evento elegido = (Evento)eventos.get(eleccion - 1);
            int disponibles = elegido.getZona().getCapacidad() - elegido.getZona().getOcupados();
            if (disponibles == 0) {
               System.out.println("\ud83d\udeab No hay más lugares disponibles para este evento.");
            } else {
               System.out.print("\ud83c\udf9f️ ¿Cuántas entradas querés comprar? (Máx " + disponibles + "): ");

               int cantidad;
               try {
                  cantidad = Integer.parseInt(sc.nextLine());
               } catch (NumberFormatException var17) {
                  System.out.println("Número inválido.");
                  continue;
               }

               if (cantidad >= 1 && cantidad <= disponibles) {
                  double subtotal = (double)cantidad * elegido.getPrecioEntrada();
                  System.out.printf("\ud83d\udcb5 Total a pagar por %d entradas: $%.2f\n", cantidad, subtotal);
                  System.out.print("\ud83d\udcb3 ¿Cómo vas a pagar? (efectivo/tarjeta o 0 para cancelar): ");
                  String metodoPago = sc.nextLine().trim().toLowerCase();
                  if (metodoPago.equals("0")) {
                     System.out.println("Compra cancelada.");
                  } else if (!metodoPago.equals("efectivo") && !metodoPago.equals("tarjeta")) {
                     System.out.println("Método de pago inválido.");
                  } else {
                     System.out.print("✅ ¿Confirmás la compra de " + cantidad + " entradas por $" + subtotal + "? (s/n): ");
                     String confirmacion = sc.nextLine().trim().toLowerCase();
                     if (!confirmacion.equals("s")) {
                        System.out.println("Compra cancelada.");
                     } else {
                        for(int i = 0; i < cantidad; ++i) {
                           elegido.getZona().ocuparLugar();
                           Entrada entrada = new Entrada(elegido, usuario, metodoPago);
                           Reserva reserva = new Reserva(usuario, entrada);
                           reserva.confirmarReserva();
                           reservasUsuario.add(reserva);
                        }

                        System.out.println("Se reservaron " + cantidad + " entradas para el evento: " + elegido.getNombre());
                     }
                  }
               } else {
                  System.out.println("Cantidad fuera del rango disponible.");
               }
            }
         } else {
            System.out.println("Opción inválida.");
         }
      }

      System.out.println("\n\ud83d\udccb RESUMEN FINAL DE COMPRAS");
      if (reservasUsuario.isEmpty()) {
         System.out.println("No realizaste ninguna reserva.");
      } else {
         double total = 0.0;

         for(eleccion = 0; eleccion < reservasUsuario.size(); ++eleccion) {
            Entrada e = ((Reserva)reservasUsuario.get(eleccion)).entrada;
            System.out.printf("\n%d. \ud83c\udf9f️ %s - %s - Fecha: %s\n", eleccion + 1, e.evento.getNombre(), e.evento.getZona().getUbicacion(), e.evento.getFecha());
            System.out.println("\ud83d\udcb5 Precio: $" + e.evento.getPrecioEntrada());
            System.out.println("\ud83d\udcb3 Método de pago: " + e.metodoPago);
            total += e.evento.getPrecioEntrada();
         }

         System.out.printf("\n\ud83c\udf9f️ TOTAL DE ENTRADAS COMPRADAS: %d\n", reservasUsuario.size());
         System.out.printf("\ud83d\udcb0 TOTAL GASTADO: $%.2f\n", total);
      }

      System.out.println("\n\ud83d\udc4b Gracias por tu compra, " + usuario.getNombre() + "!");
   }

   static {
      sc = new Scanner(System.in);
      random = new Random();
      eventos = new ArrayList();
      admin = new Administrador("admin", "admin@eventos.com");
   }
}