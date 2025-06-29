import java.io.PrintStream;
import java.io.Serializable;

public class Entrada implements Serializable {
   public Evento evento;
   public Usuario comprador;
   public String metodoPago;

   public Entrada(Evento evento, Usuario comprador, String metodoPago) {
      this.evento = evento;
      this.comprador = comprador;
      this.metodoPago = metodoPago;
   }

   public void mostrarDetalle() {
      System.out.println("\ud83c\udf9f️ Entrada para: " + this.evento.getNombre());
      PrintStream var10000 = System.out;
      String var10001 = this.comprador.getNombre();
      var10000.println("\ud83d\udc64 Comprador: " + var10001 + " (" + this.comprador.getEmail() + ")");
      System.out.println("\ud83d\udccd Ubicación: " + this.evento.getZona().getUbicacion());
      System.out.println("\ud83d\udcb5 Precio: $" + this.evento.getPrecioEntrada());
      System.out.println("\ud83d\udcb3 Método de pago: " + this.metodoPago);
   }
}
