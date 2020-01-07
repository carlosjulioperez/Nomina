package ec.carper.nomina.model

import javax.persistence.*

import org.openxava.annotations.*
import org.openxava.model.*

// https://www.openxava.org/OpenXavaDoc/docs/three-level-composite-key_en.html

@Tab(properties="provincia.provinciaNombre,cantonNombre")
//     defaultOrder="${provincia.descripcion},${descripcion}")

@Entity
@IdClass(CantonId.class)
class Canton{
    
    @Id
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="PROVINCIA_ID",referencedColumnName="PROVINCIA_ID",nullable=false,unique=false,insertable=true,updatable=true)
    @DescriptionsList(descriptionProperties="provinciaNombre")
    @NoCreate @NoModify 
    Provincia provincia

    @Id @Column(name="CANTON_ID",length=2)
    String cantonId

    @Column(name="CANTON_NOMBRE",length=40,nullable=false) @Required
    String cantonNombre
}
