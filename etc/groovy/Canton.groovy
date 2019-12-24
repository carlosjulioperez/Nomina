package ec.carper.nomina.model

import javax.persistence.*

import org.openxava.annotations.*
import org.openxava.model.*

@Entity
@IdClass(CantonId.class)
class Canton{
    
    @Id @Column(name="PROVINCIA_ID", length=2)
    String provinciaId
    
    @Id @Column(name="CANTON_ID", length=2)
    String cantonId

    @Column(length=30) @Required
    String descripcion
}

