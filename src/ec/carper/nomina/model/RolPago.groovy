package ec.carper.nomina.model

import java.time.LocalDate
import javax.persistence.*
import org.openxava.annotations.*
import org.openxava.calculators.*
import org.openxava.model.*

@Entity
@Tab(properties="""
    fecha,descripcion
""")
@View(members=  """fecha;descripcion;detalles""")
class RolPago extends Identifiable{
    
    @Version
    private Integer version;

    @DefaultValueCalculator(CurrentLocalDateCalculator.class) // Fecha actual
    @Required
    LocalDate fecha

    @Column(length=100) @Required
    String descripcion 

    @OneToMany (mappedBy="rolPago", cascade=CascadeType.ALL)
    @ListProperties("""
        empleado.apellidos,empleado.nombres,
        diasTrabajados,empleado.sueldo,calSueldoGanado,horas50,horas100,calTotalHorasExtras,calValorHorasExtras,comision,calSubsidioFamiliar,calDecimoTercero,calDecimoCuarto,calVacaciones,calFondosReserva,calTotalIngresos,calAporteIESSPersonal,prestamosQuirografarios,anticiposPrestamos,calTotalDescuentos,calLiquidoPagar,calAporteIESSPatronal
    """)
    Collection<RolPagoDetalle>detalles

    @PrePersist // Ejecutado justo antes de grabar el objeto por primera vez
    private void preGrabar() throws Exception {
        sincronizarPropiedadesPersistentes()
    }
    
    @PreUpdate
    void sincronizarPropiedadesPersistentes(){
    }
}

