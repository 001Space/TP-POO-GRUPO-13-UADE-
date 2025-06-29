// Source code is decompiled from a .class file using FernFlower decompiler.
import java.io.Serializable;

public class Usuario implements Serializable {
   protected String nombre;
   protected String email;

   public Usuario(String nombre, String email) {
      this.nombre = nombre;
      this.email = email;
   }

   public String getNombre() {
      return this.nombre;
   }

   public String getEmail() {
      return this.email;
   }
}
