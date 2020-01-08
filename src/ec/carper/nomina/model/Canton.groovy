package ec.carper.nomina.model

import javax.persistence.*
import org.openxava.annotations.*
import org.openxava.model.*

@Entity
class Canton{
    @Id @Column(length=4) @Required
    String id

    @Column(length=40) @Required
    String descripcion 
    
    @ManyToOne(fetch=FetchType.LAZY)
    @DescriptionsList @NoCreate @NoModify 
    Provincia provincia
}


