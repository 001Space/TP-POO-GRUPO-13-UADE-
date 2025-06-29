// Source code is decompiled from a .class file using FernFlower decompiler.
import java.io.Serializable;

public class Reserva implements Serializable {
   public Usuario usuario;
   public Entrada entrada;

   public Reserva(Usuario usuario, Entrada entrada) {
      this.usuario = usuario;
      this.entrada = entrada;
   }

   public void confirmarReserva() {
      System.out.println("\n Reserva confirmada para " + this.usuario.getNombre());
      this.entrada.mostrarDetalle();
   }
}
