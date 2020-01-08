package ec.carper.nomina.model

import javax.persistence.*

import org.openxava.annotations.*
import org.openxava.model.*

// https://www.openxava.org/OpenXavaDoc/docs/three-level-composite-key_en.html

@Tab(properties="canton.provincia.provinciaNombre,canton.cantonNombre,parroquiaNombre")

@Entity
@IdClass(ParroquiaId.class)
@Table(uniqueConstraints=@UniqueConstraint(columnNames=["PROVINCIA_ID","CANTON_ID","PARROQUIA_NOMBRE"]))
class Parroquia {
    
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

    @Column(name="PARROQUIA_NOMBRE",length=100,nullable=false) @Required
    String parroquiaNombre
}
