package ec.carper.nomina.model

import java.time.LocalDate
import javax.persistence.*
import org.openxava.annotations.*
import org.openxava.calculators.*
import org.openxava.model.*

@Entity
@View(members="""
    empleado;
""")
class RolPagoDetalle extends Identifiable {

    @ManyToOne //Sin lazy fetching porque falla al quitar un detalle desde el padre
    RolPago rolPago

    Empleado empleado

    @Required
    int diasTrabajados 
}
