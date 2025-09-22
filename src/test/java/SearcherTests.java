import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.Searcher;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearcherTests {

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


    @DisplayName("searchExactPhrase")
    class SearchExactPhraseTests {

        @Test
        @DisplayName("Devuelve true si la frase es exactamente el primer elemento (control)")
        void encuentraFraseEnPrimeraPosicion() {
            assertTrue(searcher.searchExactPhrase("hola mundo", frases));
        }

        @Test
        @DisplayName("Devuelve true aunque la frase esté en otra posición (revela bug actual)")
        void encuentraFraseEnCualquierPosicion() {
            assertTrue(
                    searcher.searchExactPhrase("adios mundo cruel", frases),
                    "Debe devolver true aunque la coincidencia no esté en la primera posición"
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
