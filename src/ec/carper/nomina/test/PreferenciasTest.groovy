package ec.carper.nomina.test

import ec.carper.nomina.model.Preferencias
import org.openxava.tests.*

class PreferenciasTest extends ModuleTestBase {
    
    PreferenciasTest (String testName) {
        super(testName, "Nomina", "Preferencias")
    }
 
    void testPreferencias(){
        Preferencias preferencias = new Preferencias().getPreferencias()
        println preferencias.valorCargaSubsidioFamiliar
    }
}

