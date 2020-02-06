package ec.carper.nomina.model

import javax.persistence.*
import org.openxava.annotations.*
import org.openxava.model.*
import static org.openxava.jpa.XPersistence.*

@Entity
class Preferencias extends Identifiable{

    BigDecimal salarioBasicoUnificado

    BigDecimal valorCargaSubsidioFamiliar
    
    BigDecimal porcentajeFondosReserva
    
    BigDecimal aporteIESSPersonal //9.45
    
    BigDecimal aporteIESSPatronal //11.15
    
    Preferencias getPreferencias(){
        Query query = getManager().createQuery("SELECT p FROM Preferencias p")
        List records = query.getResultList()
        Preferencias p = records ? (Preferencias)records[0]: null
        return p
    }
}

