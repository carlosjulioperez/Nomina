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
        return (horas50 && horas100) ? (horas50*1.5)+(horas100*2): 0
    }
    
    @Depends("calTotalHorasExtras") //Propiedad calculada
    BigDecimal getCalValorHorasExtras(){
        return (calTotalHorasExtras) ? 
        (empleado.sueldo/30/8*calTotalHorasExtras).setScale(2, BigDecimal.ROUND_HALF_UP) : 0
    }

    BigDecimal comision
    
    @Depends("calSueldoGanado, calValorHorasExtras, comision") //Propiedad calculada
    BigDecimal getCalTotalIngresos(){
        BigDecimal valComision = comision ?: 0
        return (calSueldoGanado && calValorHorasExtras ) ? 
        (calSueldoGanado + calValorHorasExtras + valComision) : 0
    }
    
    @Depends("calTotalIngresos")
    BigDecimal getCalAporteIESS(){
        return (calTotalIngresos) ? 
        (calTotalIngresos*9.45/100).setScale(2, BigDecimal.ROUND_HALF_UP) : 0
    }

    BigDecimal prestamosQuirografarios
    
    BigDecimal anticiposPrestamos
    
    @Depends("calAporteIESS")
    BigDecimal getCalTotalDescuentos(){
        
        BigDecimal valPQ = prestamosQuirografarios ?: 0
        BigDecimal valAP = anticiposPrestamos ?: 0

        return (calAporteIESS) ? calAporteIESS + valPQ + valAP: 0
    }
    
    @Depends("calTotalIngresos,calTotalDescuentos")
    BigDecimal getCalLiquidoPagar(){
        return (calTotalIngresos && calTotalDescuentos) ? 
        calTotalIngresos - calTotalDescuentos : 0
    }
}
