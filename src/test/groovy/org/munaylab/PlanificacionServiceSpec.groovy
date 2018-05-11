package org.munaylab

import org.munaylab.osc.Organizacion
import org.munaylab.direccion.Domicilio
import org.munaylab.direccion.DomicilioCommand
import org.munaylab.planificacion.Actividad
import org.munaylab.planificacion.ActividadCommand
import org.munaylab.planificacion.Evento
import org.munaylab.planificacion.EventoCommand
import org.munaylab.planificacion.Programa
import org.munaylab.planificacion.ProgramaCommand
import org.munaylab.planificacion.Proyecto
import org.munaylab.planificacion.ProyectoCommand
import org.munaylab.user.User

import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class PlanificacionServiceSpec extends Specification
        implements ServiceUnitTest<PlanificacionService>, DataTest, UnitTestBase {

    void setupSpec() {
        mockDomains Organizacion, Programa, Evento
    }

    void "agregar programa"() {
        given:
        def org = new Organizacion(DATOS_ORG_VERIFICADA).save(flush: true)
        def command = new ProgramaCommand(DATOS_PROGRAMA_VALIDOS)
        when:
        def programa = service.actualizarPrograma(command, org)
        then:
        programa != null && Programa.count() == 1
    }
    void "agregar programa invalido"() {
        given:
        def org = new Organizacion(DATOS_ORG_VERIFICADA).save(flush: true)
        def command = new ProgramaCommand(DATOS_PROGRAMA_INVALIDOS)
        when:
        def programa = service.actualizarPrograma(command, org)
        then:
        programa == null && Programa.count() == 0
    }
    void "agregar programa con organizacion invalida"() {
        given:
        def org = new Organizacion(DATOS_ORG_VERIFICADA).save(flush: true)
        def command = new ProgramaCommand(DATOS_PROGRAMA_VALIDOS)
        command.orgId = 2
        when:
        def programa = service.actualizarPrograma(command, org)
        then:
        programa.hasErrors() && Programa.count() == 0
    }
    void "modificar programa"() {
        given:
        def programa = crearPrograma(DATOS_PROGRAMA)
        def command = new ProgramaCommand(DATOS_PROGRAMA_VALIDOS << [id: 1])
        when:
        programa = service.actualizarPrograma(command, programa.organizacion)
        then:
        programa != null && Programa.count() == 1
        comprobarDatosProgramaActualizados(programa, command)
    }
    void "modificar programa invalido"() {
        given:
        def programa = crearPrograma(DATOS_PROGRAMA)
        def command = new ProgramaCommand(DATOS_PROGRAMA_INVALIDOS << [id: programa.id])
        when:
        programa = service.actualizarPrograma(command, programa.organizacion)
        then:
        programa == null && Programa.count() == 1
    }
    void "eliminar programa"() {
        given:
        def programa = crearPrograma(DATOS_PROGRAMA)
        when:
        service.eliminarPrograma(programa)
        then:
        Programa.all.isEmpty()
    }
    private Programa crearPrograma(datos) {
        def org = new Organizacion(DATOS_ORG_VERIFICADA).save(flush: true)
        new Programa(datos << [organizacion: org]).save(flush: true)
    }
    void comprobarDatosProgramaActualizados(Programa programa, ProgramaCommand command) {
        assert (programa.imagen == command.imagen && programa.nombre == command.nombre
                && programa.descripcion == command.descripcion)
        assert Programa.get(programa.id).imagen == command.imagen
        assert Programa.get(programa.id).nombre == command.nombre
        assert Programa.get(programa.id).descripcion == command.descripcion
    }
    void "agregar proyecto"() {
        given:
        def programa = crearPrograma(DATOS_PROGRAMA)
        def command = new ProyectoCommand(DATOS_PROYECTO_VALIDOS)
        when:
        def proyecto = service.actualizarProyecto(command, programa.organizacion)
        then:
        proyecto != null && Proyecto.count() == 1
    }
    void "agregar proyecto invalido"() {
        given:
        def programa = crearPrograma(DATOS_PROGRAMA)
        def command = new ProyectoCommand(DATOS_PROYECTO_INVALIDOS)
        when:
        def proyecto = service.actualizarProyecto(command, programa.organizacion)
        then:
        proyecto == null && Proyecto.count() == 0
    }
    void "agregar proyecto con organizacion invalida"() {
        given:
        def programa = crearPrograma(DATOS_PROGRAMA)
        def command = new ProyectoCommand(DATOS_PROYECTO_VALIDOS)
        command.orgId = 2
        when:
        def proyecto = service.actualizarProyecto(command, programa.organizacion)
        then:
        proyecto.hasErrors() && Proyecto.count() == 0
    }
    void "modificar proyecto"() {
        given:
        def proyecto = crearProyecto(DATOS_PROYECTO)
        def command = new ProyectoCommand(DATOS_PROYECTO_VALIDOS << [id: proyecto.id])
        when:
        proyecto = service.actualizarProyecto(command, proyecto.programa.organizacion)
        then:
        proyecto != null && Proyecto.count() == 1
        comprobarDatosProyectoActualizados(proyecto, command)
    }
    void "modificar proyecto invalido"() {
        given:
        def proyecto = crearProyecto(DATOS_PROYECTO)
        def command = new ProyectoCommand(DATOS_PROYECTO_INVALIDOS << [id: proyecto.id])
        when:
        proyecto = service.actualizarProyecto(command, proyecto.programa.organizacion)
        then:
        proyecto == null && Proyecto.count() == 1
    }
    void "eliminar proyecto"() {
        given:
        def proyecto = crearProyecto(DATOS_PROYECTO)
        when:
        service.eliminarProyecto(proyecto)
        then:
        Proyecto.count() == 0 && Programa.count() == 1
    }
    private Proyecto crearProyecto(datos) {
        new Proyecto(datos << [programa: crearPrograma(DATOS_PROGRAMA)]).save(flush: true)
    }
    void comprobarDatosProyectoActualizados(Proyecto proyecto, ProyectoCommand command) {
        assert (proyecto.imagen == command.imagen && proyecto.nombre == command.nombre
                && proyecto.descripcion == command.descripcion)
        assert Proyecto.get(proyecto.id).imagen == command.imagen
        assert Proyecto.get(proyecto.id).nombre == command.nombre
        assert Proyecto.get(proyecto.id).descripcion == command.descripcion
    }
    void "agregar actividad"() {
        given:
        def proyecto = crearProyecto(DATOS_PROYECTO)
        def command = new ActividadCommand(DATOS_ACTIVIDAD_VALIDOS << [proyectoId: proyecto.id])
        when:
        def actividad = service.actualizarActividad(command, proyecto.programa.organizacion)
        then:
        !actividad.hasErrors() && Actividad.count() == 1
    }
    void "agregar actividad invalida"() {
        given:
        def proyecto = crearProyecto(DATOS_PROYECTO)
        def command = new ActividadCommand(DATOS_ACTIVIDAD_INVALIDOS << [proyectoId: proyecto.id])
        when:
        def actividad = service.actualizarActividad(command, proyecto.programa.organizacion)
        then:
        actividad == null && Actividad.count() == 0
    }
    void "modificar actividad"() {
        given:
        def actividad = crearActividad(DATOS_ACTIVIDAD)
        def command = new ActividadCommand(DATOS_ACTIVIDAD_VALIDOS)
        command.id = actividad.id
        command.proyectoId = actividad.proyecto.id
        when:
        actividad = service.actualizarActividad(command, actividad.proyecto.programa.organizacion)
        then:
        !actividad.hasErrors() && Actividad.count() == 1
        comprobarDatosActividadActualizados(actividad, command)
    }
    void "modificar actividad invalida"() {
        given:
        def actividad = crearActividad(DATOS_ACTIVIDAD)
        def command = new ActividadCommand(DATOS_ACTIVIDAD_INVALIDOS)
        command.id = actividad.id
        command.proyectoId = actividad.proyecto.id
        when:
        actividad = service.actualizarActividad(command, actividad.proyecto.programa.organizacion)
        then:
        actividad == null && Actividad.count() == 1
    }
    void "eliminar actividad"() {
        given:
        def actividad = crearActividad(DATOS_ACTIVIDAD)
        when:
        service.eliminarActividad(actividad)
        then:
        Actividad.count() == 0 && Proyecto.count() == 1
    }
    private Actividad crearActividad(datos) {
        new Actividad(datos << [proyecto: crearProyecto(DATOS_PROYECTO)]).save(flush: true)
    }
    void comprobarDatosActividadActualizados(Actividad actividad, ActividadCommand command) {
        assert (actividad.imagen == command.imagen && actividad.nombre == command.nombre
                && actividad.descripcion == command.descripcion)
        assert Actividad.get(actividad.id).imagen == command.imagen
        assert Actividad.get(actividad.id).nombre == command.nombre
        assert Actividad.get(actividad.id).descripcion == command.descripcion
    }
    void "agregar evento"() {
        given:
        def org = new Organizacion(DATOS_ORG_VERIFICADA).save(flush: true)
        def command = new EventoCommand(DATOS_EVENTO_VALIDOS)
        when:
        def evento = service.actualizarEvento(command, org)
        then:
        !evento.hasErrors() && Evento.count() == 1
    }
    void "agregar evento invalido"() {
        given:
        def org = new Organizacion(DATOS_ORG_VERIFICADA).save(flush: true)
        def command = new EventoCommand(DATOS_EVENTO_INVALIDOS)
        when:
        def evento = service.actualizarEvento(command, org)
        then:
        evento == null && Evento.count() == 0
    }
    void "agregar evento con direccion"() {
        given:
        def org = new Organizacion(DATOS_ORG_VERIFICADA).save(flush: true)
        def command = new EventoCommand(DATOS_EVENTO_CON_DIRECCION_VALIDOS)
        command.direccion = new DomicilioCommand(DATOS_DOMICILIO_VALIDOS)
        when:
        def evento = service.actualizarEvento(command, org)
        then:
        !evento.hasErrors() && Evento.count() == 1
    }
    void "modificar evento"() {
        given:
        def evento = crearEvento(DATOS_EVENTO)
        def command = new EventoCommand(DATOS_EVENTO_VALIDOS << [id: evento.id])
        when:
        evento = service.actualizarEvento(command, evento.organizacion)
        then:
        !evento.hasErrors() && Evento.count() == 1
        comprobarDatosEventoActualizados(evento, command)
    }
    void "modificar evento invalido"() {
        given:
        def evento = crearEvento(DATOS_EVENTO)
        def command = new EventoCommand(DATOS_EVENTO_INVALIDOS << [id: evento.id])
        when:
        evento = service.actualizarEvento(command, evento.organizacion)
        then:
        evento == null && Evento.count() == 1
    }
    void "eliminar evento"() {
        given:
        def evento = crearEvento(DATOS_EVENTO)
        when:
        service.eliminarEvento(evento)
        then:
        Evento.count() == 0
    }
    private Evento crearEvento(datos) {
        def org = new Organizacion(DATOS_ORG_VERIFICADA).save(flush: true)
        new Evento(datos << [organizacion: org]).save(flush: true, failOnError: true)
    }
    void comprobarDatosEventoActualizados(Evento evento, EventoCommand command) {
        assert (evento.imagen == command.imagen && evento.nombre == command.nombre
                && evento.descripcion == command.descripcion)
        assert (Evento.get(1).imagen == command.imagen && Evento.get(1).nombre == command.nombre
                && Evento.get(1).descripcion == command.descripcion)
    }

    void "obtener resumen"() {
        given:
        def org = new Organizacion(DATOS_ORG_VERIFICADA).save(flush: true)
        def evento = new Evento(DATOS_EVENTO << [organizacion: org]).save(flush: true)
        def programa = new Programa(DATOS_PROGRAMA << [organizacion: org]).save(flush: true)
        def proyecto = new Proyecto(DATOS_PROYECTO << [organizacion: org, programa: programa]).save(flush: true)
        new Actividad(DATOS_ACTIVIDAD << [organizacion: org, proyecto: proyecto]).save(flush: true)
        expect:
        service.getResumen(org) == [eventos: 1, programas: 1, proyectos: 1, actividades: 1]
    }
}
