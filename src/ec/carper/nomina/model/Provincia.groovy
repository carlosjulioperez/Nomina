package ec.carper.nomina.model

import java.util.Collection;

import javax.persistence.*
import org.openxava.annotations.*
import org.openxava.model.*

@Entity
class Provincia{
    @Id @Column(name="PROVINCIA_ID",length=2) @Required
    String provinciaId

    @Column(name="PROVINCIA_NOMBRE",length=30,unique=true,nullable=false) @Required
    String provinciaNombre 
    
}
