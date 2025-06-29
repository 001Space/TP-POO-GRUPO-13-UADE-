import java.io.Serializable;

public class Zona implements Serializable {
   private String ubicacion;
   private int capacidad;
   private int ocupados;

   public Zona(String ubicacion, int capacidad) {
      this.ubicacion = ubicacion;
      this.capacidad = capacidad;
      this.ocupados = 0;
   }

   public String getUbicacion() {
      return this.ubicacion;
   }

   public int getCapacidad() {
      return this.capacidad;
   }

   public int getOcupados() {
      return this.ocupados;
   }

   public boolean hayCapacidad() {
      return this.ocupados < this.capacidad;
   }

   public void ocuparLugar() {
      if (this.hayCapacidad()) {
         ++this.ocupados;
      }

   }

   public void setCapacidad(int nuevaCapacidad) {
      if (nuevaCapacidad >= this.ocupados) {
         this.capacidad = nuevaCapacidad;
      } else {
         System.out.println("La nueva capacidad no puede ser menor a los lugares ya ocupados.");
      }

   }
}