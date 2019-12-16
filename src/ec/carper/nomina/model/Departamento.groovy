package ec.carper.nomina.model

import javax.persistence.*
import org.openxava.annotations.*
import org.openxava.model.*

@Entity
class EstadoCivil extends Identifiable{

    @Column(length=20) @Required
    String descripcion
}

