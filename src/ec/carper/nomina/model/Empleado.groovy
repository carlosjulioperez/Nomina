package ec.carper.nomina.model

import javax.persistence.*
import org.openxava.annotations.*
import org.openxava.model.*

@Entity
class Empleado extends Identifiable{

    @Column(length=10) @Required
    String cedula

    @Column(length=40) @Required
    String apellidos

    @Column(length=40) @Required
    String nombres

    @Column(length=100) @Required
    String direccion

    @Column(length=20) @Required
    String telefono

}
