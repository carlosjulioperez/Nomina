package ec.carper.nomina.model

import javax.persistence.*

import org.openxava.annotations.*

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
// https://www.tothenew.com/blog/groovy-annotations-for-tostring-and-equalsandhashcode/
// http://docs.groovy-lang.org/latest/html/api/groovy/transform/ToString.html
class CantonId implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="PROVINCIA_ID",referencedColumnName="PROVINCIA_ID",nullable=false,unique=false,insertable=true,updatable=true)
    @DescriptionsList(descriptionProperties="provinciaNombre")
    @NoCreate @NoModify 
    Provincia provincia

    @Id @Column(name="CANTON_ID",length=2)
    String cantonId
}
