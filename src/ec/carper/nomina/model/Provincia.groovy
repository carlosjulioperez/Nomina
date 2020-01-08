package ec.carper.nomina.model

import javax.persistence.*
import org.openxava.annotations.*
import org.openxava.model.*

@Entity
class Provincia{
    @Id @Column(length=2) @Required
    String id

    @Column(length=30) @Required
    String descripcion 
}


