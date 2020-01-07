package ec.carper.nomina.test

import org.openxava.tests.*

class ProvinciaTest extends ModuleTestBase {
    ProvinciaTest (String testName) {
        super(testName, "Nomina", "Provincia")
    }
 
    void testCreateReadUpdateDelete() throws Exception {
        login("admin", "admin")
 
        String valor = "99"
        String provinciaNombre = "JUNIT"
        String provinciaNombre2 = "JUNI2"
        
        // Create
        execute("CRUD.new")
        setValue("provinciaId", valor)
        setValue("provinciaNombre", provinciaNombre)
        execute("CRUD.save")
        assertNoErrors()
        assertValue("provinciaId", "")
        assertValue("provinciaNombre", "")
 
        // Read
        setValue("provinciaId", valor)
        execute("CRUD.refresh")
        assertValue("provinciaId", valor)
        assertValue("provinciaNombre", provinciaNombre)
 
        // Update
        setValue("provinciaNombre", provinciaNombre2)
        execute("CRUD.save")
        assertNoErrors()
        assertValue("provinciaNombre", "")
 
        // Verify if modified
        setValue("provinciaId", valor)
        execute("CRUD.refresh")
        assertValue("provinciaNombre", provinciaNombre2)
 
        // Delete
        execute("CRUD.delete")
        assertMessage("Province deleted successfully")
    }
 
}

