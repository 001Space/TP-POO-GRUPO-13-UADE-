// Source code is decompiled from a .class file using FernFlower decompiler.
import java.io.Serializable;

public class Proveedor implements Serializable {
   private String tipo;
   private String nombreEmpresa;

   public Proveedor(String tipo, String nombreEmpresa) {
      this.tipo = tipo;
      this.nombreEmpresa = nombreEmpresa;
   }

   public String getTipo() {
      return this.tipo;
   }

   public String getNombreEmpresa() {
      return this.nombreEmpresa;
   }
}