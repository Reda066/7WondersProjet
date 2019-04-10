package jeu.test;

import jeu.Materiaux;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MateriauxTest {

    Materiaux materiaux;

    @BeforeEach
    void setUp() {
        materiaux = new Materiaux(2, 1, 0, 0, 1, 0, 0, 0);
    }

    @Test
    void testmateriaux() {
        int or = 2;
        assert materiaux.getListeMateriaux().get(0).equals(or);

        int pierre = 1;
        assert materiaux.getListeMateriaux().get(1).equals(pierre);

        int minerai = 1;
        assert materiaux.getListeMateriaux().get(4).equals(minerai);

        System.out.println("------------------TEST MATERIAUX------------------");
        System.out.println("L'or est  bien attribué et vaut : " + materiaux.getListeMateriaux().get(0));
        System.out.println("La pierre est  bien attribuée et vaut : " + materiaux.getListeMateriaux().get(1));
        System.out.println("Le minerai est  bien attribué et vaut : " + materiaux.getListeMateriaux().get(4));
    }

}