package ec.carper.nomina.model

import java.time.LocalDate
import javax.persistence.*
import org.openxava.annotations.*
import org.openxava.calculators.*
import org.openxava.model.*

@Entity
@View(members="""#
    empleado;
    diasTrabajados, calSueldoGanado;
    horas50, horas100;
    calTotalHorasExtras, calValorHorasExtras;
    comision, calTotalIngresos;
    calAporteIESS,prestamosQuirografarios;
    anticiposPrestamos,calTotalDescuentos;
    calLiquidoPagar
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

    @Depends("diasTrabajados") //Propiedad calculada
    BigDecimal getCalSueldoGanado(){
        return (diasTrabajados) ? empleado.sueldo/30*diasTrabajados: 0
    }
    
    Integer horas50

    Integer horas100

    // TODO VALIDAR HORAS EXTRAS
    @Depends("horas50,horas100") //Propiedad calculada
    BigDecimal getCalTotalHorasExtras(){
        BigDecimal val50 = horas50 ?:0
        BigDecimal val100 = horas100 ?:0
        return val50 * 1.5 + val100 * 2
    }
    
    @Depends("calTotalHorasExtras") //Propiedad calculada
    BigDecimal getCalValorHorasExtras(){
        return (empleado.sueldo/30/8*calTotalHorasExtras).setScale(2, BigDecimal.ROUND_HALF_UP)
    }

    BigDecimal comision
    
    @Depends("calSueldoGanado, calValorHorasExtras, comision") //Propiedad calculada
    BigDecimal getCalTotalIngresos(){
        BigDecimal valComision = comision ?: 0
        return calSueldoGanado + calValorHorasExtras + valComision
    }
    
    @Depends("calTotalIngresos")
    BigDecimal getCalAporteIESS(){
        return (calTotalIngresos*9.45/100).setScale(2, BigDecimal.ROUND_HALF_UP)
    }

    BigDecimal prestamosQuirografarios
    
    BigDecimal anticiposPrestamos
    
    @Depends("calAporteIESS,prestamosQuirografarios,anticiposPrestamos")
    BigDecimal getCalTotalDescuentos(){
        
        BigDecimal valPQ = prestamosQuirografarios ?: 0
        BigDecimal valAP = anticiposPrestamos ?: 0

        return (calAporteIESS + valPQ + valAP)
    }
    
    @Depends("calTotalIngresos,calTotalDescuentos")
    BigDecimal getCalLiquidoPagar(){
        return (calTotalIngresos - calTotalDescuentos)
    }
}
