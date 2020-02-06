package ec.carper.nomina.model

import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.Digits
import org.openxava.annotations.*
import org.openxava.calculators.*
import org.openxava.model.*

@Entity
@Tab(properties="""cedula,apellidos,nombres,email""")
@Views([
    @View(members=  """
        tabDatosPersonales{
            cedula, fechaNacimiento;
            apellidos;nombres;
            email;
            parroquia;
            direccion;
            telefono;
            estadoCivil
        }
        tabSueldo{
            departamento;
            sueldo;
            cargaFamiliar;
        }
    """),
    @View(name="simple", members="apellidos,nombres,cargaFamiliar,sueldo")
])
class Empleado extends Identifiable{

    @Column(length=10) @Required
    String cedula

    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    @Required
    LocalDate fechaNacimiento

    @Column(length=40) @DisplaySize(20) @Required
    String apellidos

    @Column(length=40) @DisplaySize(20) @Required
    String nombres

    @Stereotype("EMAIL") @Required
    String email 

    @ManyToOne(fetch=FetchType.LAZY) @NoCreate @NoModify 
    @DescriptionsList(descriptionProperties="canton.provincia.descripcion,canton.descripcion,descripcion")
    Parroquia parroquia
    
    @Column(length=100) @Required
    String direccion

    @Column(length=20) @Required
    String telefono
    
    @ManyToOne(fetch=FetchType.LAZY)
    @DescriptionsList @NoCreate @NoModify 
    EstadoCivil estadoCivil
    
    @ManyToOne(fetch=FetchType.LAZY)
    @DescriptionsList @NoCreate @NoModify 
    Departamento departamento

    //@Digits(integer=4,fraction=2) 
    @Stereotype("MONEY") @Required
    BigDecimal sueldo

    @Column(length=2) @Required
    int cargaFamiliar

    Boolean pagoMensual13

}
