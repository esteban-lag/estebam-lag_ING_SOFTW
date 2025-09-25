
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.Calculator;

public class CalulatorTests {

    private Calculator calculator = new Calculator();

    @DisplayName("Test básico suma")
    @Test
    void sumaTest(){
        double result = calculator.sum(2, 3);
        assertEquals(result, 5);
    }

    @DisplayName("Test básico suma negativos")
    @Test
    void sumaTestNegativos(){
        double result = calculator.sum(-2, -3);
        assertEquals(result, -5);        


    }
    
    @DisplayName("Test básico multiplicación")
    @Test
    void multiplicaciónTest(){

        int result = calculator.multiply(2, 3);
        assertEquals(result, 6);

    }

   @DisplayName("Test básico multiplicación")
    @Test
    void multiplicaciónTestCero(){

        int result = calculator.multiply(2, 0);
        assertEquals(result, 0);
    }

    @DisplayName("Test multiplicación 0")
    @Test
    void multiplicaciónTest0(){

        int result = calculator.multiply(2, 0);
        assertEquals(result, 0);
    }

    @DisplayName("Test multiplicación Negativos")
    @Test
    void multiplicaciónTestNegativo(){

        int result = calculator.multiply(2, -3);
        assertEquals(result, -6);
    }

    @DisplayName("Test concat")
    @Test
    void concatTest(){

        String result =calculator.concat("hi", "he");
        assertEquals(result,"hihe");

    }
    
    @DisplayName("Test concat null")
    @Test
    void concatTestNull(){

        String result =calculator.concat(null, "he");
        assertEquals(result,"empty");
    }
    
    @DisplayName("Test porcentage")
    @Test
    void discountTest(){
        
        Double result = calculator.discount(80, 50.5);
        assertEquals(result, 39.6);

    }

    
    @DisplayName("Test porcentage of 0 and 100")
    @Test
    void discountTest0(){
        
        Double result = calculator.discount(80, 0);
        assertEquals(result, 80);

    }
    @Test
    void discountTest100(){
        
        Double result = calculator.discount(80, 100);
        assertEquals(result, 0);

    }
    
    @Test
    void discountTestInvalid100(){
        
        assertThrows(IllegalArgumentException.class,() -> calculator.discount(80, 120));

    }
      
    @Test
    void discountTestInvalid0(){
        
        assertThrows(IllegalArgumentException.class,() -> calculator.discount(80, -10));

    }

    @Test
     void calculateTotal_listaVaciaDevuelveCero() {
        List<Double> importes = new ArrayList<>();
        double resultado = calculator.calculateTotal(importes);
        assertEquals(0.0, resultado, 0.0001);
    }
    
    


}




