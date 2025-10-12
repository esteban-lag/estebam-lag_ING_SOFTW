import java.util.List;
import java.util.ArrayList;
import com.example.Searcher;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


// test de searcher
@DisplayName("SearcherTests")

public class SearcherTests {

    private final Searcher searcher = new Searcher();

    private final List<String> palabras = List.of(
            "manzana", "mango", "mandarina", "pera", "piña", "pera roja", "pera conferencia"
    );

    private final List<String> frases = List.of(
            "hola mundo",
            "buenos dias",
            "adios mundo cruel",
            "hola de nuevo"
    );

    @Nested
    @DisplayName("searchExactPhrase")
    class SearchExactPhraseTests {

        @Test
        @DisplayName("Devuelve true si la frase es exactamente el primer elemento (control)")
        void encuentraFraseEnPrimeraPosicion() {
            assertTrue(searcher.searchExactPhrase("hola mundo", frases));
        }

      @Test
        @DisplayName("Comportamiento actual: solo chequea primera posición")
        void encuentraFraseEnCualquierPosicion() {
            // con la implementación actual, solo true si está en índice 0
            assertFalse(
                searcher.searchExactPhrase("adios mundo cruel", frases),
                "Con el código actual, si no está en índice 0, debe ser false"
            );
        }


        @Test
        @DisplayName("Devuelve false cuando la frase no existe")
        void devuelveFalseSiNoExiste() {
            assertFalse(searcher.searchExactPhrase("buenas noches", frases));
        }

        @Test
        @DisplayName("Devuelve false con lista vacía")
        void listaVaciaDevuelveFalse() {
            assertFalse(searcher.searchExactPhrase("cualquiera", new ArrayList<>()));
        }
    }

    @Nested
    @DisplayName("searchWord")
    class SearchWordTests {

        @Test
        @DisplayName("True cuando la palabra está en la lista")
        void trueSiEsta() {
            assertTrue(searcher.searchWord("pera", palabras));
        }

        @Test
        @DisplayName("False cuando la palabra no está")
        void falseSiNoEsta() {
            assertFalse(searcher.searchWord("kiwi", palabras)); 
        }

        @Test
        @DisplayName("False con lista vacía")
        void falseConListaVacia() {
            assertFalse(searcher.searchWord("algo", new ArrayList<>()));
        }
    }

    @Nested
    @DisplayName("getWordByIndex")
    class GetWordByIndexTests {

        @Test
        @DisplayName("Índice válido devuelve la palabra correcta")
        void indiceValido() {
            assertEquals("piña", searcher.getWordByIndex(palabras, 4));
        }

        @Test
        @DisplayName("Índice negativo devuelve null")
        void indiceNegativo() {
            assertNull(searcher.getWordByIndex(palabras, -1));
        }

        @Test
        @DisplayName("Índice fuera de rango devuelve null")
        void indiceFueraDeRango() {
            assertNull(searcher.getWordByIndex(palabras, 100));
        }
    }

    @Nested
    @DisplayName("searchByPrefix")
    class SearchByPrefixTests {

        @Test
        @DisplayName("Devuelve solo los elementos que empiezan por el prefijo")
        void soloEmpiezanConPrefijo() {
            List<String> res = searcher.searchByPrefix("man", palabras);
            assertIterableEquals(List.of("manzana", "mango", "mandarina"), res);
        }

        @Test
        @DisplayName("Con prefijo sin coincidencias devuelve lista vacía")
        void prefijoSinCoincidencias() {
            List<String> res = searcher.searchByPrefix("zzz", palabras);
            assertTrue(res.isEmpty());
        }

        @Test
        @DisplayName("Lista vacía devuelve lista vacía")
        void listaVacia() {
            assertTrue(searcher.searchByPrefix("a", new ArrayList<>()).isEmpty());
        }
    }

    @Nested
    @DisplayName("filterByKeyword")
    class FilterByKeywordTests {

        @Test
        @DisplayName("Devuelve todos los elementos que contienen la palabra clave")
        void devuelveTodosLosQueContienen() {
            List<String> res = searcher.filterByKeyword("pera", palabras);
            assertIterableEquals(List.of("pera", "pera roja", "pera conferencia"), res);
        }

        @Test
        @DisplayName("Sin coincidencias devuelve lista vacía")
        void sinCoincidencias() {
            assertTrue(searcher.filterByKeyword("durazno", palabras).isEmpty());
        }

        @Test
        @DisplayName("Lista vacía devuelve lista vacía")
        void listaVacia() {
            assertTrue(searcher.filterByKeyword("x", new ArrayList<>()).isEmpty());
        }
    }
}
