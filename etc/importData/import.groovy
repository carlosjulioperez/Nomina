// JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:bin/java::")

// http://docs.groovy-lang.org/latest/html/documentation/grape.html
// https://www.lovholm.net/2017/06/load-database-from-csv-with-columns-and-tables-read-from-file/
// http://groovy-lang.org/databases.html
// Los jars se descargan en: .groovy/grapes/

// Formato fecha para csv
// https://xkcd.com/1179/

// @Grab('org.hsqldb:hsqldb:2.3.2')
@Grab('org.postgresql:postgresql:42.2.5.jre7')
@GrabConfig(systemClassLoader=true)
@Grab('com.opencsv:opencsv:3.9')
//@Grab(group='com.opencsv', module='opencsv', version='3.9')

import groovy.sql.*
import com.opencsv.*
import static groovy.io.FileType.FILES

//def path = new File("to_digest")
def path = new File("csv")

// Defino el orden para importar los archivos csv
def lista = [
    "PREFERENCIAS",
    "DEPARTAMENTO",
    "ESTADOCIVIL",
    "FORMULA",
    "PROVINCIA",
    "CANTON",
    "PARROQUIA",
    "RUBRO",
    "EMPLEADO",
    "FORMULACION",
    "FORMULACION_DETALLES",
    "ROLPAGO",
    "ROLPAGODETALLE"
]
lista.each{
    def archivo = "csv/"+ it +".csv"
    //println archivo
    List entries = digestFile(archivo)
    // println "entries: {$entries}"
    insertFile(entries.subList(0,1)[0], entries.subList(1,entries.size()), it)
}

/*
path.traverse(type : FILES, nameFilter: ~/\w*\.csv/) { it ->
    println "it: {$it}"

    List entries = digestFile(it)
    //println "entries: {$entries}"
    insertFile(entries.subList(0,1)[0],entries.subList(1,entries.size()),it.name.take(it.name.lastIndexOf('.')))
}
*/

private List digestFile(def path){
    CSVReader reader = new CSVReader(new FileReader(path),(char)',')
    List myEntries = reader.readAll()
    return myEntries
}

private void insertFile(def header, def data, def name){
    Properties properties = new Properties()
    File propertiesFile = new File('configuration.properties')
    propertiesFile.withInputStream {
        properties.load(it)
    }
    //println name
    String prefix = ""
    if ( properties.db_schema!=null )
        prefix = properties.db_schema + "."

    def table = prefix + name
    println "Procesando ${table}..."

    Sql conn = Sql.newInstance(properties.db_url,properties.db_user,properties.db_password)
    data.each { rows ->
        String columns = String.join(",", header)
        String values = rows.collect {"'$it'"}.join(",")
        //println values
        
        //String query = "INSERT INTO ${name} (${columns}) VALUES (${values})"
    
        def query = "INSERT INTO " + table + " (${columns}) VALUES (${values})"
        //println query
        conn.execute(query)
    }
}
