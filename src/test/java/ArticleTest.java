import com.example.model.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ArticleTest")
public class ArticleTest {

    @Test
    @DisplayName("Constructor con params setea todos los campos")
    void ctorConParams() {
            Article a = new Article("Pan", 3, 2.5, 10.0);
            assertEquals("Pan", a.getNombre());
            assertEquals(3, a.getCantidad());
            assertEquals(2.5, a.getPrecio(), 1e-6);
            assertEquals(10.0, a.getDescuento(), 1e-6);
        }

    @Test
    void gettersSetters() {
        Article a = new Article();
        a.setNombre("Pan");
        a.setCantidad(3);
        a.setPrecio(2.5);
        a.setDescuento(10.0);

        assertEquals("Pan", a.getNombre());
        assertEquals(3, a.getCantidad());
        assertEquals(2.5, a.getPrecio(), 1e-6);
        assertEquals(10.0, a.getDescuento(), 1e-6);
    }



    @Test
    @DisplayName("getGrossAmount = cantidad * precio")
    void grossBasico() {
        Article a = new Article("Pan", 3, 2.0, 0.0);
        assertEquals(6.0, a.getGrossAmount(), 1e-6);
    }

    @Test
    @DisplayName("getDiscountedAmount aplica %")
    void conDescuento() {
        Article a = new Article("Pan", 3, 2.0, 10.0); // 6 -> 5.4
        assertEquals(5.4, a.getDiscountedAmount(), 1e-6);
    }

    @Test
    @DisplayName("cantidad 0 => totales 0")
    void cantidadCero() {
        Article a = new Article("Agua", 0, 7.5, 50.0);
        assertEquals(0.0, a.getGrossAmount(), 1e-6);
        assertEquals(0.0, a.getDiscountedAmount(), 1e-6);       
    
    } 
}


    


