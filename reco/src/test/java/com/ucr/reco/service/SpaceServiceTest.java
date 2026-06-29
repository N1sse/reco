package com.ucr.reco.service;

import com.ucr.reco.model.Space;
import com.ucr.reco.repository.SpaceJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //para indicarle a la prueba que usaremos mockito en JUnit 5
class SpaceServiceTest {

    // @Mock permite crear un objeto simulado y permitiendo las pruebas del service como lo pide el enunciado
    @Mock
    private SpaceJpaRepository repository;

    // @InjectMocks inyecta automáticamente el mock al objeto que esta simulando
    @InjectMocks
    private SpaceService service;

    //Este metodo verifica que un espacio válido se guarde correctamente
    @Test
    void addSpaceExpected() {

        //En esta parte compara el espacio simulado que se creo con la información del espacio que se espera.
        //versión todavía no guardada
        Space space = new Space(null, "Garrets Diner's", "San José", "Restaurante", 100.0);
        //versión esperada
        Space expectedSpace = new Space(1, "Garrets Diner's", "San José", "Restaurante", 100.0);

        // Simula que no existe otro espacio con el mismo nombre.
        Mockito.when(repository.getByName("Garrets Diner's")).thenReturn(null); //aqui lo que al objeto simulado es que cuando x cosa pase retornaras este valor
        //Por si no se desea llamar algún espacio con parametros especificos, se puede usar Mockito.anyString()
        //Ejemplo: Mockito.when(repository.getByName(Mockito.anyString())).thenReturn(null);

        //guarda el espacio
        Mockito.when(repository.save(space)).thenReturn(expectedSpace);

        Space result = service.add(space);

        //Asegura que espacios no sean Null y tenga la información esperada
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Garrets Diner's", result.getName());

        //verifica fuera ejecutado
        Mockito.verify(repository, times(1)).getByName("Garrets Diner's");
        Mockito.verify(repository, times(1)).save(space);
    }


    // Esta prueba verifica que no se guarde un espacio si el nombre ya existe.
    @Test
    void addNameAlreadyExists() {
        //Aqui simula crear un nuevo espacio y el que ya existe:

        //version nueva que se intenta agregar
        Space space = new Space(null, "Garrets Diner's", "San José", "Restaurante", 100.0);
        //version que ya esta guardada con el mismo nombre
        Space existingSpace = new Space(1, "Garrets Diner's", "San José", "Restaurante", 100.0);

        //------------------------
        // En esta parte simula una excepción cuando el repositorio intenta
        // guardar un espacio nuevo con el nombre de uno ya existente.
        Mockito.when(repository.getByName("Garrets Diner's")).thenReturn(existingSpace);

        Space result = service.add(space);
        //como el nombre ya existe el resultado debe ser null
        assertNull(result);

        //verificaciones para buscar y guardar el espacio
        Mockito.verify(repository, times(1)).getByName("Garrets Diner's");
        Mockito.verify(repository, never()).save(any(Space.class));
    }

    // Esta prueba verifica que no haya campos obligatorios vacíos.
    @Test
    void addReturnNullWhenRequiredFieldsAreMissing() {
        //crea un espacio con el nombre en null para probar que falta un campo obligatorio
        Space space = new Space(null, null, "San José", "Restaurante", 100.0);

        //ejecuta el metodo del service con el dato incompleto
        Space result = service.add(space);

        Assertions.assertNull(result);   //como falta el campo del nombre debe de devolver null

        //verifica que nunca se intento guardar en el repositorio
        Mockito.verify(repository, never()).save(any(Space.class));

    }


    // Esta prueba verifica que el servicio pueda listar los espacios correctamente.
    @Test
    //@Test anotacion para realizar la prueba
    void findAll() {
        //crea dos espacios que simulan estar guardados
        Space space1 = new Space(1, "Garrets Diner's", "San José", "Restaurante", 100.0);
        Space space2 = new Space(2, "John's Venue", "Heredia", "Country Estate", 150.0);

        //simula que el repositorio devuelve una lista con esos dos espacios
        Mockito.when(repository.findAll()).thenReturn(List.of(space1, space2));

        List<Space> result = service.findAll();

        //asegura que la lista tenga 2 espacios y esten en el orden correcto
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Garrets Diner's", result.get(0).getName());
        Assertions.assertEquals("John's Venue", result.get(1).getName());

        //verifica que findAll se llamo 1 vez
        Mockito.verify(repository, times(1)).findAll();
    }

    // Esta prueba valida el comportamiento cuando el objeto simulado falla al listar.
    @Test
    void findAllThrowExceptionWhenRepositoryFails() {
        //simula que el repositorio lanza(thenThrow) un error al listar
        Mockito.when(repository.findAll()).thenThrow(new RuntimeException("Database error"));

        //verifica que al ejecutar findAll se lance la excepcion esperada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.findAll();
        });
        assertEquals("Database error", exception.getMessage());

        Mockito.verify(repository, times(1)).findAll(); //verifica que se llamo 1 vez
    }

    // Esta prueba verifica que el servicio retorne un espacio específico por su id.
    @Test
    void getSpaceById() {

        Space spaceCreated = new Space(1, "Garrets Diner's", "San José", "Restaurante", 100.0);

        //simula que al buscar por id 1 el repositorio devuelve ese espacio
        Mockito.when(repository.getById(1)).thenReturn(spaceCreated);

        Space result = service.getById(1);

        //asegura que el resultado no sea null y que el nombre coincida
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Garrets Diner's", result.getName());

        Mockito.verify(repository, times(1)).getById(1); //aqui verifica el metodo se haya invocado bien
    }

    // Esta prueba verifica que se pueda actualizar un espacio existente.
    @Test
    void updateSpaceWhenSpaceExists() {
        //Crea un espacio ya existente y los datos para actualizar
        Space existingSpace = new Space(1, "Garrets Diner's", "San José", "Restaurante", 100.0);
        Space updatedData = new Space(null, "John's Venue", "Heredia", "Country Estate", 150.0);

        //simula que al buscar por id 1 encuentra el espacio existente y guarda el existente
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(existingSpace));
        Mockito.when(repository.save(existingSpace)).thenReturn(existingSpace);

        //ejecuta la actualizacion
        Space result = service.update(1, updatedData);

        //En esta parte se asegura que el resultado no sea null
        // y que todos los campos se actualizaron
        assertNotNull(result);
        assertEquals("John's Venue", result.getName());
        assertEquals("Heredia", result.getLocation());
        assertEquals("Country Estate", result.getType());
        assertEquals(150.0, result.getPrice());

        //verifica que busco por id 1 vez y luego guardo 1 vez
        Mockito.verify(repository, times(1)).findById(1);
        Mockito.verify(repository, times(1)).save(existingSpace);
    }

    // Esta prueba verifica el caso en el que se intenta actualizar un espacio inexistente.
    @Test
    void updateReturnNullWhenSpaceDoesNotExist() {
        //simula crear un espacio con el id vacio
        Space updatedData = new Space(null, "John's Venue", "Heredia", "Country Estate", 150.0);

        //simula que no se encuentra el espacio con ese id (optional vacio)
        Mockito.when(repository.findById(14)).thenReturn(Optional.empty());

        //ejecuta la actualizacion y como es vacio entonces devuelve null
        Space result = service.update(14, updatedData);
        Assertions.assertNull(result);

        //Esta parte verifica que se ejecute al menos una vez el findId, pero nunca lo guardo
        Mockito.verify(repository, times(1)).findById(14); //por ese el times
        Mockito.verify(repository, never()).save(any(Space.class)); //por eso el never
    }

    // Esta prueba verifica que se pueda eliminar un espacio existente.
    @Test
    void deleteDeleteSpaceWhenSpaceExists() {
        //Simula crear un espacio
        Space space = new Space(1, "Garrets Diner's", "San José", "Restaurante", 100.0);

        //simula que al buscar por id 1 encuentra el espacio
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(space));

        Space result = service.delete(1);

        //asegura que el resultado no sea null y que sea el espacio eliminado
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Garrets Diner's", result.getName());

        //En esta parte se asegura en ejecutar al menos una vez el encontrar el id
        // y al menos una vez el eliminar el id
        Mockito.verify(repository, times(1)).findById(1);
        Mockito.verify(repository, times(1)).deleteById(1);
    }

    // Esta prueba verifica que no se elimine nada si el espacio no existe.
    @Test
    void deleteReturnNullWhenSpaceDoesNotExist() {
        //simula que no se encuentra el espacio con ese id (optional vacio)
        Mockito.when(repository.findById(14)).thenReturn(Optional.empty());

        Space result = service.delete(14);

        //asegura que el resultado sea null
        Assertions.assertNull(result);

        //En esta parte se asegura en ejecutar al menos una vez el encontrar el id
        // y se asegura de nunca ejecutar el metodo de eliminar
        Mockito.verify(repository, times(1)).findById(14);
        Mockito.verify(repository, never()).deleteById(anyInt());
    }

    // Esta prueba verifica que se pueda cambiar el precio de un espacio existente devuelve null
    @Test
    void changePricePriceWhenSpaceExists() {

        //crea un espacio simulado con el precio inicial de $100 para poder alquilarlo
        Space space = new Space(1, "Garrets Diner's", "San José", "Restaurante", 100.0);

        //simula que al buscar por id 1 encuentra el espacio
        //y simula que al guardar el espacio con el precio cambiado lo devuelve
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(space));
        Mockito.when(repository.save(space)).thenReturn(space);

        Space result = service.changePrice(1, 200.0);

        //asegura que el resultado no sea null y que el precio se actualizo
        Assertions.assertNotNull(result);
        Assertions.assertEquals(200.0, result.getPrice());

        //En esta parte se asegura en ejecutar al menos una vez el encontrar el id y luego salvar los cambios
        Mockito.verify(repository, times(1)).findById(1);
        Mockito.verify(repository, times(1)).save(space);
    }

    // Esta prueba verifica que no se cambie el precio si el espacio no existe.
    @Test
    void changePriceReturnNullWhenSpaceDoesNotExist() {
        //simula que no se encuentra el espacio con ese id
        Mockito.when(repository.findById(14)).thenReturn(Optional.empty());

        Space result = service.changePrice(14, 200.0);

        //como no existe el resultado debe ser null
        assertNull(result);

        //verifica que en ejecuto al menos una vez el encontrar el id y que nunca guardo
        Mockito.verify(repository, times(1)).findById(14);
        Mockito.verify(repository, never()).save(any(Space.class));
    }

}//Fin de las Pruebas