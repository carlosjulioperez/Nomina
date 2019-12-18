package ec.carper.nomina.model

import ec.carper.nomina.model.Ciudad
import ec.carper.nomina.model.Provincia
import javax.persistence.*
import org.openxava.annotations.*
import org.openxava.model.*

@Entity
@Tab(properties="""cedula,apellidos,nombres,email""")
@View(members=  """
    tabDatosPersonales{
        cedula;
        apellidos,nombres;
        email;
        provincia,ciudad;
        direccion,
        telefono
    }
""")
class Empleado extends Identifiable{

    @Column(length=10) @Required
    String cedula

    @Column(length=40) @Required
    String apellidos

    @Column(length=40) @Required
    String nombres

    @Stereotype("EMAIL") @Required
    String email 

    @ManyToOne(fetch=FetchType.LAZY)
    @DescriptionsList
    Provincia provincia
    
    @ManyToOne(fetch=FetchType.LAZY)
    @DescriptionsList
    Ciudad ciudad
    
    @Column(length=100) @Required
    String direccion

    @Column(length=20) @Required
    String telefono
}
