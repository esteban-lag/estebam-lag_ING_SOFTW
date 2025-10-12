import com.example.model.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ArticleTest")
public class ArticleTest {

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
