package ec.carper.nomina.actions

import javax.persistence.Query;
import org.openxava.actions.*
import org.openxava.jpa.*
import ec.carper.nomina.model.Preferencias
import static org.openxava.jpa.XPersistence.*;

class NuevaPreferencia extends ViewBaseAction implements IChainAction{

    private String nextAction = null // Para guardar la siguiente acción a ejecutar

    void execute() throws Exception{
        //TODO Ejemplo para ejecutar queries desde Groovy
        Query query = getManager().createQuery("select count(*) from Preferencias")
        def numero = (Integer)query.getSingleResult()
        //System.out.println(numero)

        if ( numero >0 ){
            addMessage ("msgSoloUnRegistro")
            
            resetDescriptionsCache()
            getView().clear()
        }else 
            nextAction = "CRUD.new"
    }
    
    // Obligatorio por causa de 'IChainAction'
    String getNextAction() throws Exception {
        return nextAction // Si es nulo no se encadena con ninguna acción
    }
}

