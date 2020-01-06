package ec.carper.nomina.model

import javax.persistence.*

import org.openxava.annotations.*
import org.openxava.model.*

// https://www.openxava.org/OpenXavaDoc/docs/three-level-composite-key_en.html

@Tab(properties="provincia.descripcion,descripcion")

// @Tab(properties="provincia.descripcion,descripcion",
//     defaultOrder="${provincia.descripcion},${descripcion}")

@Entity
@IdClass(CantonId.class)
// @View(members=""" provinciaId, cantonId; descripcion; """)
// @Table(
//     uniqueConstraints={
//         @UniqueConstraint(columnNames={"ID","DESCRIPCION"})
//     }
// )
class Canton{
    
    // @Id @Column(name="PROVINCIA_ID", length=2)
    // String provinciaId
    @Id
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="PROVINCIA_ID",referencedColumnName="ID",nullable=false,unique=false,insertable=true,updatable=true)
    @DescriptionsList(descriptionProperties="DESCRIPCION")
    Provincia provincia

    @Id @Column(length=2)
    String cantonId

    @Column(length=40) @Required
    String descripcion
}
