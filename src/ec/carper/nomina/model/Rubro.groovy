package ec.carper.nomina.model

import javax.persistence.*
import org.openxava.annotations.*
import org.openxava.model.*

@Entity
class Rubro extends Identifiable{

    @Column(length=30) @Required
    String descripcion

    @Required
    boolean suma
}
