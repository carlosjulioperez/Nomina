package ec.carper.nomina.model

import java.math.BigDecimal;
import java.util.List;

import ec.carper.nomina.model.Ciudad
import ec.carper.nomina.model.Provincia
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.Digits
import org.openxava.annotations.*
import org.openxava.calculators.*
import org.openxava.model.*

@Entity
@Tab(properties="""cedula,apellidos,nombres,email""")
@View(members=  """
    tabDatosPersonales{
        cedula, fechaNacimiento;
        apellidos,nombres;
        email;
        provincia,ciudad;
        direccion;
        telefono;
        estadoCivil
    }
    tabSueldo{
        departamento;
        sueldo
    }
""")
class Empleado extends Identifiable{

    @Column(length=10) @Required
    String cedula

    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    @Required
    LocalDate fechaNacimiento

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
    
    @ManyToOne(fetch=FetchType.LAZY)
    @DescriptionsList
    EstadoCivil estadoCivil
    
    @ManyToOne(fetch=FetchType.LAZY)
    @DescriptionsList
    Departamento departamento

    //@Digits(integer=4,fraction=2) 
    @Stereotype("MONEY") @Required
    BigDecimal sueldo

}
