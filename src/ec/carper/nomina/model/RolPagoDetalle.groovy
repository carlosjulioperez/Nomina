package ec.carper.nomina.model

import ec.carper.nomina.util.Util

import java.time.LocalDate
import javax.persistence.*
import org.openxava.annotations.*
import org.openxava.calculators.*
import org.openxava.model.*

@Entity
@View(members="""#
    empleado;
    jornada[
        diasTrabajados, horas50, horas100, calTotalHorasExtras
    ];
    ingresos[
        calSueldoGanado; calValorHorasExtras; comision;
        calSubsidioFamiliar;
        calDecimoTercero;
        calDecimoCuarto;
        calVacaciones;
        calFondosReserva;
        calTotalIngresos;
    ]
    egresos[
        calAporteIESS;
        prestamosQuirografarios;
        anticiposPrestamos;
        calTotalDescuentos;
    ]
    calLiquidoPagar
""")
class RolPagoDetalle extends Identifiable {
        
    @ManyToOne //Sin lazy fetching porque falla al quitar un detalle desde el padre
    RolPago rolPago

    // @ManyToOne(fetch=FetchType.LAZY)
    // @DescriptionsList(descriptionProperties="apellidos,nombres")
    @ManyToOne
    @ReferenceView("simple") @NoCreate @NoModify 
    Empleado empleado

    @DisplaySize(5) @Required
    Integer diasTrabajados 

    @Depends("diasTrabajados") //Propiedad calculada
    @DisplaySize(10)
    BigDecimal getCalSueldoGanado(){
        return (diasTrabajados) ? (empleado.sueldo/30*diasTrabajados).setScale(2, BigDecimal.ROUND_HALF_UP): 0
    }
    
    @DisplaySize(5)
    Integer horas50

    @DisplaySize(5)
    Integer horas100

    @DisplaySize(10)
    @Depends("horas50,horas100") //Propiedad calculada
    BigDecimal getCalTotalHorasExtras(){
        BigDecimal val50 = horas50 ?:0
        BigDecimal val100 = horas100 ?:0
        return (val50 * 1.5 + val100 * 2).setScale(2, BigDecimal.ROUND_HALF_UP)
    }
    
    @Depends("calTotalHorasExtras") //Propiedad calculada
    @DisplaySize(10)
    BigDecimal getCalValorHorasExtras(){
        return (empleado.sueldo/30/8*calTotalHorasExtras).setScale(2, BigDecimal.ROUND_HALF_UP)
    }

    @DisplaySize(10)
    BigDecimal comision
    
    @Depends("empleado") //Propiedad calculada
    @DisplaySize(10)
    BigDecimal getCalSubsidioFamiliar(){
        Integer valCargaFamiliar = empleado.cargaFamiliar ?: 0
        Preferencias p = Util.instance.getPreferencias()
        return (valCargaFamiliar * p.valorCargaSubsidioFamiliar).setScale(2, BigDecimal.ROUND_HALF_UP)
    }

    private BigDecimal getCalIngresosAntesBeneficiosSociales(){
        BigDecimal valComision = comision ?: 0
        return (calSueldoGanado + calValorHorasExtras + valComision + calSubsidioFamiliar)
    }

    @Depends("calSueldoGanado, calValorHorasExtras, comision") //Propiedad calculada
    @DisplaySize(10)
    BigDecimal getCalDecimoTercero(){
        return (calIngresosAntesBeneficiosSociales / 12 ).setScale(2, BigDecimal.ROUND_HALF_UP)
    }

    @DisplaySize(10)
    BigDecimal getCalDecimoCuarto(){
        Preferencias p = Util.instance.getPreferencias()
        return (p.salarioBasicoUnificado / 12).setScale(2, BigDecimal.ROUND_HALF_UP)
    }
    
    @Depends("calSueldoGanado, calValorHorasExtras, comision") //Propiedad calculada
    @DisplaySize(10)
    BigDecimal getCalVacaciones(){
        return (calIngresosAntesBeneficiosSociales*12/24/12).setScale(2, BigDecimal.ROUND_HALF_UP)
    }

    @Depends("calSueldoGanado, calValorHorasExtras, comision") //Propiedad calculada
    @DisplaySize(10)
    BigDecimal getCalFondosReserva(){
        Preferencias p = Util.instance.getPreferencias()
        return (calIngresosAntesBeneficiosSociales*p.porcentajeFondosReserva/100).setScale(2, BigDecimal.ROUND_HALF_UP)
    }

    @Depends("calSueldoGanado, calValorHorasExtras, comision") //Propiedad calculada
    @DisplaySize(10)
    BigDecimal getCalTotalIngresos(){
        return calIngresosAntesBeneficiosSociales + calDecimoTercero + calDecimoCuarto + calVacaciones + calFondosReserva
    }
    
    @Depends("calTotalIngresos")
    @DisplaySize(10)
    BigDecimal getCalAporteIESS(){
        Preferencias p = Util.instance.getPreferencias()
        return (calTotalIngresos*p.aporteIESSPersonal/100).setScale(2, BigDecimal.ROUND_HALF_UP)
    }

    @DisplaySize(10)
    BigDecimal prestamosQuirografarios
    
    @DisplaySize(10)
    BigDecimal anticiposPrestamos
    
    @Depends("calAporteIESS,prestamosQuirografarios,anticiposPrestamos")
    @DisplaySize(10)
    BigDecimal getCalTotalDescuentos(){
        BigDecimal valPQ = prestamosQuirografarios ?: 0
        BigDecimal valAP = anticiposPrestamos ?: 0
        return (calAporteIESS + valPQ + valAP)
    }
    
    @Depends("calTotalIngresos,calTotalDescuentos")
    @DisplaySize(10)
    BigDecimal getCalLiquidoPagar(){
        return (calTotalIngresos - calTotalDescuentos)
    }

    //TODO Grabar calSubsidioFamiliar
}
