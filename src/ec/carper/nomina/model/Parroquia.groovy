package ec.carper.nomina.model

import javax.persistence.*
import org.openxava.annotations.*
import org.openxava.model.*

@Entity
class Parroquia extends Identifiable{

    @Column(length=130) @Required
    String descripcion
}


