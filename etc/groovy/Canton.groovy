package ec.carper.nomina.model

import javax.persistence.*

import org.openxava.annotations.*
import org.openxava.model.*

// https://www.openxava.org/OpenXavaDoc/docs/three-level-composite-key_en.html
@Entity
@IdClass(CantonId.class)
@View(members=""" provinciaId, cantonId; descripcion; """)
class Canton{
    
    @Id @Column(name="PROVINCIA_ID", length=2)
    String provinciaId
    
    @Id @Column(name="CANTON_ID", length=2)
    String cantonId

    @Column(length=40) @Required
    String descripcion
}

