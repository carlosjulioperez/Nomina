// JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:bin/java::")

// https://www.lovholm.net/2017/06/load-database-from-csv-with-columns-and-tables-read-from-file/
// http://groovy-lang.org/databases.html

// @GrabResolver(name='nexus', root='http://yourorganizationsnexusinstance.you', m2Compatible = 'true')


// @Grab('org.hsqldb:hsqldb:2.3.2')
@Grab('org.hsqldb:hsqldb:2.4.0')
@GrabConfig(systemClassLoader=true)
@Grab(group='com.opencsv', module='opencsv', version='3.9')

import groovy.sql.*
import com.opencsv.*

import static groovy.io.FileType.FILES

//def path = new File("to_digest")
def path = new File("csv")

path.traverse(type : FILES, nameFilter: ~/\w*\.csv/) { it ->
    List entries = digestFile(it)
    insertFile(entries.subList(0,1)[0],entries.subList(1,entries.size()),it.name.take(it.name.lastIndexOf('.')))

}

private List digestFile(def path){

    CSVReader reader = new CSVReader(new FileReader(path),(char)';')
    List myEntries = reader.readAll()
    return myEntries
}

private void insertFile(def header, def data, def name){
Properties properties = new Properties()
File propertiesFile = new File('configuration.properties')
propertiesFile.withInputStream {
    properties.load(it)
}
    println name
    Sql conn = Sql.newInstance(properties.db_url,properties.db_user,properties.db_password)
    data.each { rows ->
            String columns = String.join(",", header)
            String values = rows.collect {"'$it'"}.join(",")
            String query = "INSERT INTO ${name} (${columns}) VALUES (${values})"
            conn.execute(query)
    }
}
