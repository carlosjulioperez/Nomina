package ec.carper.nomina.util

import static org.openxava.jpa.XPersistence.*
import ec.carper.nomina.model.Preferencias
import javax.persistence.Query
import java.util.List

@Singleton
class Util{
    
    Preferencias getPreferencias(){
        Query query = getManager().createQuery("SELECT p FROM Preferencias p")
        List records = query.getResultList()
        Preferencias p = records ? (Preferencias)records[0]: null
        return p
    }

}
