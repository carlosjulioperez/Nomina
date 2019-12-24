package ec.carper.nomina.model

import javax.persistence.*
import org.openxava.annotations.*
import org.openxava.model.*

@Embeddable
class FormulacionDetalle{
    
    @ManyToOne(fetch=FetchType.LAZY)
    @DescriptionsList
    Rubro rubro

    @ManyToOne(fetch=FetchType.LAZY)
    @DescriptionsList
    Formula formula
}
