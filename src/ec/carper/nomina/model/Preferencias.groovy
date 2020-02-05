package ec.carper.nomina.model

import javax.persistence.*
import org.openxava.annotations.*
import org.openxava.model.*

@Entity
class Preferencias extends Identifiable{

    BigDecimal salarioBasicoUnificado

    BigDecimal valorCargaSubsidioFamiliar
    
    BigDecimal porcentajeFondosReserva
    
    BigDecimal aporteIESSPersonal //9.45
    
    BigDecimal aporteIESSPatronal //11.15

}

