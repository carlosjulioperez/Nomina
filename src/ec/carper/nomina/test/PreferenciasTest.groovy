package ec.carper.nomina.test

import ec.carper.nomina.model.Preferencias
import ec.carper.nomina.util.Util
import org.openxava.tests.*

class PreferenciasTest extends ModuleTestBase {
    
    PreferenciasTest (String testName) {
        super(testName, "Nomina", "Preferencias")
    }
 
    void testPreferencias(){
        Preferencias p = Util.instance.getPreferencias()
        println p.valorCargaSubsidioFamiliar
    }
}

