package ec.carper.nomina.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
// https://www.tothenew.com/blog/groovy-annotations-for-tostring-and-equalsandhashcode/
// http://docs.groovy-lang.org/latest/html/api/groovy/transform/ToString.html
class CantonId implements java.io.Serializable {

    String provinciaId
    String cantonId
}
