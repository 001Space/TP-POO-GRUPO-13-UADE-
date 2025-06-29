import java.io.Serializable;
import java.time.LocalDate;

public class Administrador extends Usuario implements Serializable {
   private String contraseña = "admin123";

   public Administrador(String nombre, String email) {
      super(nombre, email);
   }

   public boolean autenticar(String email, String contraseña) {
      return this.email.equals(email) && this.contraseña.equals(contraseña);
   }

   public void setContraseña(String nueva) {
      this.contraseña = nueva;
   }

   public Evento crearEvento(String nombre, Zona zona, double precio, LocalDate fecha) {
      return new Evento(nombre, zona, precio, fecha);
   }
}
