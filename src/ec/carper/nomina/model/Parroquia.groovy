package ec.carper.nomina.model

import javax.persistence.*
import org.openxava.annotations.*
import org.openxava.model.*

@Entity
class Parroquia{
    @Id @Column(length=6) @Required
    String id

    @Column(length=100) @Required
    String descripcion 
    
    @ManyToOne(fetch=FetchType.LAZY)
    @DescriptionsList @NoCreate @NoModify 
    Canton canton
}


