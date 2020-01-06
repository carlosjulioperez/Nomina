package ec.carper.nomina.model

import javax.persistence.*
import org.openxava.annotations.*
import org.openxava.model.*

@Entity
class Canton extends Identifiable{

    @Column(length=40) @Required
    String descripcion
}


