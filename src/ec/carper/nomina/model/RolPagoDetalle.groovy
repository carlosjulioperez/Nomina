package ec.carper.nomina.model

import java.time.LocalDate
import javax.persistence.*
import org.openxava.annotations.*
import org.openxava.calculators.*
import org.openxava.model.*

@Entity
@View(members="""#
    empleado;
    diasTrabajados; 
    horas50, horas100; 
    calTotalHorasExtras;
    calSueldoGanado,        calAporteIESSPersonal;
    calValorHorasExtras,    prestamosQuirografarios;
    comision,               anticiposPrestamos;
    calSubsidioFamiliar,    calTotalDescuentos;
    calDecimoTercero,       calLiquidoPagar;
    calDecimoCuarto,        calAporteIESSPatronal;
    calVacaciones;
    calFondosReserva;
    calTotalIngresos;
""")
class RolPagoDetalle extends Identifiable {
        
    @ManyToOne //Sin lazy fetching porque falla al quitar un detalle desde el padre
    RolPago rolPago

    // @ManyToOne(fetch=FetchType.LAZY)
    // @DescriptionsList(descriptionProperties="apellidos,nombres")
    @ManyToOne
    @ReferenceView("simple") @NoCreate @NoModify 
    Empleado empleado

    @Column(length=2) @Required
    int diasTrabajados 
    
    @Depends("empledo,diasTrabajados") //Propiedad calculada
    @Stereotype("MONEY")
    BigDecimal getCalSueldoGanado() {
        return diasTrabajados ? (empleado.sueldo/30*diasTrabajados).setScale(2, BigDecimal.ROUND_HALF_UP) : 0
    }

    @Column(length=2)  
    int horas50
    
    @Column(length=2) 
    int horas100

    @Depends("horas50,horas100") //Propiedad calculada
    BigDecimal getCalTotalHorasExtras(){
        BigDecimal val50 = horas50 ?:0
        BigDecimal val100 = horas100 ?:0
        return (val50 * 1.5 + val100 * 2).setScale(2, BigDecimal.ROUND_HALF_UP)
    }
    
    @Depends("calTotalHorasExtras") //Propiedad calculada
    @Stereotype("MONEY")
    BigDecimal getCalValorHorasExtras(){
        return (empleado.sueldo/30/8*calTotalHorasExtras).setScale(2, BigDecimal.ROUND_HALF_UP)
    }

    @Stereotype("MONEY")
    BigDecimal comision
    
    @Depends("calSueldoGanado") //Propiedad calculada
    @Stereotype("MONEY")
    BigDecimal getCalSubsidioFamiliar(){
        return empleado.cargaFamiliar ? (empleado.cargaFamiliar * 20.00).setScale(2, BigDecimal.ROUND_HALF_UP) : 0
    }

    private BigDecimal getCalIngresosAntesBeneficiosSociales(){
        BigDecimal valComision = comision ?: 0
        return (calSueldoGanado + calValorHorasExtras + valComision + calSubsidioFamiliar)
    }

    @Depends("calSueldoGanado, calValorHorasExtras, comision") //Propiedad calculada
    @Stereotype("MONEY")
    BigDecimal getCalDecimoTercero(){
        return (calIngresosAntesBeneficiosSociales / 12 ).setScale(2, BigDecimal.ROUND_HALF_UP)
    }

    @Depends("calSueldoGanado") //Propiedad calculada
    @Stereotype("MONEY")
    BigDecimal getCalDecimoCuarto(){
        // return (p.salarioBasicoUnificado / 12).setScale(2, BigDecimal.ROUND_HALF_UP)
        return ( 394 / 12).setScale(2, BigDecimal.ROUND_HALF_UP)
    }
    
    @Depends("calSueldoGanado, calValorHorasExtras, comision") //Propiedad calculada
    @Stereotype("MONEY")
    BigDecimal getCalVacaciones(){
        return (calIngresosAntesBeneficiosSociales/24).setScale(2, BigDecimal.ROUND_HALF_UP)
    }

    @Depends("calSueldoGanado, calValorHorasExtras, comision") //Propiedad calculada
    @Stereotype("MONEY")
    BigDecimal getCalFondosReserva(){
        // Preferencias p = new Preferencias().getPreferencias()
        // return (calIngresosAntesBeneficiosSociales*p.porcentajeFondosReserva/100).setScale(2, BigDecimal.ROUND_HALF_UP)
        return (calIngresosAntesBeneficiosSociales*8.33/100).setScale(2, BigDecimal.ROUND_HALF_UP)
    }

    @Depends("calSueldoGanado, calValorHorasExtras, comision") //Propiedad calculada
    @Stereotype("MONEY")
    BigDecimal getCalTotalIngresos(){
        return calIngresosAntesBeneficiosSociales + calDecimoTercero + calDecimoCuarto + calVacaciones + calFondosReserva
    }
    
    @Depends("calTotalIngresos")
    @Stereotype("MONEY")
    BigDecimal getCalAporteIESSPersonal(){
        // Preferencias p = new Preferencias().getPreferencias()
        // return (calTotalIngresos*p.aporteIESSPersonal/100).setScale(2, BigDecimal.ROUND_HALF_UP)
        return (calTotalIngresos*9.45/100).setScale(2, BigDecimal.ROUND_HALF_UP)
    }

    @Stereotype("MONEY")
    BigDecimal prestamosQuirografarios
    
    @Stereotype("MONEY")
    BigDecimal anticiposPrestamos
    
    @Depends("calAporteIESSPersonal,prestamosQuirografarios,anticiposPrestamos")
    @Stereotype("MONEY")
    BigDecimal getCalTotalDescuentos(){
        BigDecimal valPQ = prestamosQuirografarios ?: 0
        BigDecimal valAP = anticiposPrestamos ?: 0
        return (calAporteIESSPersonal + valPQ + valAP)
    }
    
    @Depends("calTotalIngresos,calTotalDescuentos")
    @Stereotype("MONEY")
    BigDecimal getCalLiquidoPagar(){
        return (calTotalIngresos - calTotalDescuentos)
    }

    @Depends("calSueldoGanado, calValorHorasExtras, comision") //Propiedad calculada
    @Stereotype("MONEY")
    BigDecimal getCalAporteIESSPatronal(){
        // Preferencias p = new Preferencias().getPreferencias()
        // BigDecimal valor = calIngresosAntesBeneficiosSociales*p.aporteIESSPatronal/100
        // return (valor).setScale(2, BigDecimal.ROUND_HALF_UP)
        return (calIngresosAntesBeneficiosSociales*11.15/100).setScale(2, BigDecimal.ROUND_HALF_UP)
    }

    BigDecimal sueldoGanado
    BigDecimal totalHorasExtras
    BigDecimal valorHorasExtras
    BigDecimal subsidioFamiliar
    BigDecimal decimoTercero
    BigDecimal decimoCuarto
    BigDecimal vacaciones
    BigDecimal fondosReserva
    BigDecimal totalIngresos
    BigDecimal totalDescuentos
    BigDecimal liquidoPagar
    BigDecimal valorAporteIESSPersonal
    BigDecimal valorAporteIESSPatronal

    //TODO Grabar calSubsidioFamiliar
    @PrePersist // Ejecutado justo antes de grabar el objeto por primera vez
    private void preGrabar() throws Exception {
        sincronizarDetalles()
    }

    @PreUpdate
    void sincronizarDetalles(){
        sueldoGanado            = calSueldoGanado
        totalHorasExtras        = calTotalHorasExtras
        valorHorasExtras        = calValorHorasExtras
        subsidioFamiliar        = calSubsidioFamiliar
        decimoTercero           = calDecimoTercero
        decimoCuarto            = calDecimoCuarto
        vacaciones              = calVacaciones
        fondosReserva           = calFondosReserva
        totalIngresos           = calTotalIngresos
        totalDescuentos         = calTotalDescuentos
        liquidoPagar            = calLiquidoPagar
        valorAporteIESSPersonal = calAporteIESSPersonal
        valorAporteIESSPatronal = calAporteIESSPatronal
    }
}
