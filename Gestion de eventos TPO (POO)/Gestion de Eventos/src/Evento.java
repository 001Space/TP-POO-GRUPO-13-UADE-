import java.io.Serializable;
import java.time.LocalDate;

public class Evento implements Serializable {
   private String nombre;
   private Zona zona;
   private double precioEntrada;
   private LocalDate fecha;

   public Evento(String nombre, Zona zona, double precioEntrada, LocalDate fecha) {
      this.nombre = nombre;
      this.zona = zona;
      this.precioEntrada = precioEntrada;
      this.fecha = fecha;
   }

   public String getNombre() {
      return this.nombre;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   public Zona getZona() {
      return this.zona;
   }

   public void setZona(Zona zona) {
      this.zona = zona;
   }

   public double getPrecioEntrada() {
      return this.precioEntrada;
   }

   public void setPrecioEntrada(double precioEntrada) {
      this.precioEntrada = precioEntrada;
   }

   public LocalDate getFecha() {
      return this.fecha;
   }

   public void setFecha(LocalDate fecha) {
      this.fecha = fecha;
   }
}
