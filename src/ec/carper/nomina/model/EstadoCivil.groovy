package ec.carper.nomina.model

import javax.persistence.*
import org.openxava.annotations.*
import org.openxava.model.*

@Entity
class Departamento extends Identifiable{

    @Column(length=30) @Required
    String descripcion
}

