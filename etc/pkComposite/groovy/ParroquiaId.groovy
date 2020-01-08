package ec.carper.nomina.model

import javax.persistence.*

import org.openxava.annotations.*

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
// https://www.tothenew.com/blog/groovy-annotations-for-tostring-and-equalsandhashcode/
// http://docs.groovy-lang.org/latest/html/api/groovy/transform/ToString.html
class ParroquiaId implements java.io.Serializable {
    
    private static final long serialVersionUID = 4L;
  
    @Id
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumns([ 
        @JoinColumn(name="CANTON_ID", referencedColumnName="CANTON_ID",nullable=false,unique=false,insertable=true,updatable=true),
        @JoinColumn(name="PROVINCIA_ID", referencedColumnName="PROVINCIA_ID",nullable=false,unique=false,insertable=true,updatable=true)
    ])
    @DescriptionsList(descriptionProperties="provincia.provinciaNombre,cantonNombre")
    @NoCreate @NoModify 
    Canton canton

    @Id @Column(name="PARROQUIA_ID",length=2)
    String parroquiaId
}
