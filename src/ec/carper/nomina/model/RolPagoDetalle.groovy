package ec.carper.nomina.model

import java.time.LocalDate
import javax.persistence.*
import org.openxava.annotations.*
import org.openxava.calculators.*
import org.openxava.model.*

@Entity
@View(members="""
    empleado;
    diasTrabajados
""")
class RolPagoDetalle extends Identifiable {

    @ManyToOne //Sin lazy fetching porque falla al quitar un detalle desde el padre
    RolPago rolPago

    // @ManyToOne(fetch=FetchType.LAZY)
    // @DescriptionsList(descriptionProperties="apellidos,nombres")
    @ManyToOne
    @ReferenceView("simple")
    Empleado empleado

    @Required
    Integer diasTrabajados 
}
