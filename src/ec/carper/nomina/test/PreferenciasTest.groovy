package ec.carper.nomina.test

import ec.carper.nomina.util.*; // En el paquete 'util'
import org.openxava.tests.*

class PreferenciasTest extends ModuleTestBase {
    
    PreferenciasTest (String testName) {
        super(testName, "Nomina", "Preferencias")
    }
 
    // void testPreferencias(){
    //     Preferencias preferencias = new Preferencias().getPreferencias()
    //     println preferencias.valorCargaSubsidioFamiliar
    // }

    void testPreferenciasNomina(){
        println PreferenciasNomina.getSalarioBasicoUnificado()
        println PreferenciasNomina.getValorCargaSubsidioFamiliar()
        println PreferenciasNomina.getPorcentajeFondosReserva()
        println PreferenciasNomina.getAporteIessPersonal()
        println PreferenciasNomina.getAporteIessPatronal()
    }
}

