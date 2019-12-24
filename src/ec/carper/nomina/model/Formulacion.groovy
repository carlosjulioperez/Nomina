package ec.carper.nomina.model

import java.time.LocalDate
import javax.persistence.*
import org.openxava.annotations.*
import org.openxava.calculators.*
import org.openxava.model.*

@Entity
class Formulacion extends Identifiable{
    
    @DefaultValueCalculator(CurrentLocalDateCalculator.class) // Fecha actual
    @Required
    LocalDate fecha
    
    @Column(length=100) @Required
    String descripcion 

    @ElementCollection
    @ListProperties(""" rubro, formula """)
    Collection<FormulacionDetalle>detalles
}

