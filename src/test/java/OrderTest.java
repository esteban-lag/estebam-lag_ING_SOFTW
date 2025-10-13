import com.example.model.Article;
import com.example.model.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OrderTest")
public class OrderTest {

    @Test
    void gettersSetters() {
        Order o = new Order();
        o.setId("A1");
        List<Article> lista = new ArrayList<>();
        lista.add(new Article("Leche", 2, 1.5, 0.0));
        o.setArticulos(lista);

        assertEquals("A1", o.getId());
        assertEquals(1, o.getArticulos().size());
        assertEquals("Leche", o.getArticulos().get(0).getNombre());
    }
     
    @Test
    @DisplayName("Constructor vacío: lista inicial vacía")
    void ctorVacio() {
        Order o = new Order();
        assertNull(o.getId());
        assertNotNull(o.getArticulos());
        assertTrue(o.getArticulos().isEmpty());
    }
    
    @Test
    @DisplayName("setArticulos(null) deja lista vacía (no null)")
    void setNullList() {
        Order o = new Order("X", List.of(new Article("algo", 1, 1.0, 0.0)));
        o.setArticulos(null);
        assertNotNull(o.getArticulos());
        assertTrue(o.getArticulos().isEmpty());
    }

    @Test
    @DisplayName("getGrossTotal suma brutos")
    void grossTotal() {
        Article a1 = new Article("Pan", 3, 2.0, 10.0); // 6.0
        Article a2 = new Article("Leche", 2, 1.5, 0.0); // 3.0
        Order o = new Order("A1", List.of(a1, a2));
        assertEquals(9.0, o.getGrossTotal(), 1e-6);
    }

    @Test
    @DisplayName("getDiscountedTotal suma con descuentos")
    void discountedTotal() {
        Article a1 = new Article("Pan", 3, 2.0, 10.0); 
        Article a2 = new Article("Leche", 2, 1.5, 0.0);
        Order o = new Order("A1", List.of(a1, a2));
        assertEquals(8.4, o.getDiscountedTotal(), 1e-6);
    }

    @Test
    @DisplayName("pedido vacío => 0")
    void vacio() {
        Order o = new Order("X", new ArrayList<>());
        assertEquals(0.0, o.getGrossTotal(), 1e-6);
        assertEquals(0.0, o.getDiscountedTotal(), 1e-6);
    }
    


public class OrderGetSetTest {

    @Test
    void gettersSetters() {
        Order o = new Order();
        o.setId("A1");
        List<Article> lista = new ArrayList<>();
        lista.add(new Article("Leche", 2, 1.5, 0.0));
        o.setArticulos(lista);

        assertEquals("A1", o.getId());
        assertEquals(1, o.getArticulos().size());
        assertEquals("Leche", o.getArticulos().get(0).getNombre());
    }
}

}
