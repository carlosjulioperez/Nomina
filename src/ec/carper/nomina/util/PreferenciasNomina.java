package ec.carper.nomina.util; // En el paquete 'util'
 
import java.io.*;
import java.math.*;
import java.util.*;
import org.apache.commons.logging.*;
import org.openxava.util.*;
 
public class PreferenciasNomina {
 
    private final static String ARCHIVO_PROPIEDADES="nomina.properties";
    private static Log log = LogFactory.getLog(PreferenciasNomina.class);
 
    private static Properties propiedades; // Almacenamos las propiedades aquí
 
    private static Properties getPropiedades() {
        if (propiedades == null) { // Usamos inicialización vaga
            PropertiesReader reader = // PropertiesReader es una clase de OpenXava
                new PropertiesReader(
                    PreferenciasNomina.class, ARCHIVO_PROPIEDADES);
            try {
                propiedades = reader.get();
            }
            catch (IOException ex) {
                log.error(
                    XavaResources.getString( // Para leer un mensaje i18n
                        "properties_file_error",
                        ARCHIVO_PROPIEDADES),
                    ex);
                  propiedades = new Properties();
             }
        }
        return propiedades;
    }
 
    public static BigDecimal getSalarioBasicoUnificado() { 
        return new BigDecimal(getPropiedades().getProperty("salarioBasicoUnificado"));
    }
    
    public static BigDecimal getValorCargaSubsidioFamiliar() {
        return new BigDecimal(getPropiedades().getProperty("valorCargaSubsidioFamiliar"));
    }
    
    public static BigDecimal getPorcentajeFondosReserva() {
        return new BigDecimal(getPropiedades().getProperty("porcentajeFondosReserva"));
    }
    
    public static BigDecimal getAporteIessPersonal() { 
        return new BigDecimal(getPropiedades().getProperty("aporteIessPersonal"));
    }

    public static BigDecimal getAporteIessPatronal() { 
        return new BigDecimal(getPropiedades().getProperty("aporteIessPatronal"));
    }

}
