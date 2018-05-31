package org.munaylab

import org.munaylab.components.PanelEventos
import org.munaylab.components.PanelProgramas
import org.munaylab.components.PanelProyectos
import org.munaylab.components.PanelActividades
import org.munaylab.direccion.Domicilio
import org.munaylab.osc.Organizacion
import org.munaylab.planificacion.Actividad
import org.munaylab.planificacion.ActividadCommand
import org.munaylab.planificacion.Evento
import org.munaylab.planificacion.EventoCommand
import org.munaylab.planificacion.Programa
import org.munaylab.planificacion.ProgramaCommand
import org.munaylab.planificacion.Proyecto
import org.munaylab.planificacion.ProyectoCommand
import org.munaylab.planificacion.PlanificacionCommand
import org.munaylab.utils.Respuesta

import grails.gorm.transactions.Transactional

@Transactional
class PlanificacionService {

    Programa getPrograma(Long id, Organizacion org) {
        Programa programa = Programa.get(id)
        if (!programa) return null

        if (programa.organizacion != org) return null

        programa
    }

    List<Programa> getProgramas(Organizacion org) {
        Programa.findAllByOrganizacion(org)
    }

    Programa actualizarPrograma(ProgramaCommand command, Organizacion org) {
        if (!command.validate()) return null

        if (command.orgId != org.id) {
            Programa programa = new Programa()
            programa.errors.rejectValue('organizacion', 'error.invalid.token')
            return programa
        }

        Programa programa = command.id ? Programa.get(command.id) : null
        if (programa) {
            programa.nombre = command.nombre
            programa.descripcion = command.descripcion
            programa.contenido = command.contenido
            programa.save()
        } else {
            programa = new Programa(command.properties)
            programa.organizacion = org
            programa.save()
        }
        return programa
    }

    void eliminarPrograma(Long id) {
        eliminarPrograma(Programa.get(id))
    }

    void eliminarPrograma(Programa programa) {
        if (!programa) return
        programa.delete()
    }

    Proyecto getProyecto(Long id, Organizacion org) {
        Proyecto proyecto = Proyecto.get(id)
        if (!proyecto) return null

        if (proyecto.programa.organizacion != org) return null

        proyecto
    }

    List<Proyecto> getProyectos(Organizacion org) {
        def programas = getProgramas(org)
        programas*.proyectos.flatten()
    }

    Proyecto actualizarProyecto(ProyectoCommand command, Organizacion org) {
        if (!command.validate()) return null

        if (command.orgId != org.id) {
            Proyecto proyecto = new Proyecto()
            proyecto.errors.reject('error.invalid.token')
            return proyecto
        }

        Proyecto proyecto = command.id ? Proyecto.get(command.id) : null
        if (proyecto) {
            proyecto.nombre = command.nombre
            proyecto.descripcion = command.descripcion
            proyecto.contenido = command.contenido
            if (command.programaId != proyecto?.programa.id) {
                proyecto.programa = getPrograma(command.programaId, org)
            }
            proyecto.save()
        } else {
            Programa programa = Programa.get(command.programaId)
            if (programa) {
                proyecto = new Proyecto(command.properties)
                proyecto.programa = programa
                proyecto.save()
            } else {
                proyecto = new Proyecto()
                proyecto.errors.rejectValue('programa', 'programa.not.found')
            }
        }
        return proyecto
    }

    void eliminarProyecto(Long id) {
        eliminarProyecto(Proyecto.get(id))
    }

    void eliminarProyecto(Proyecto proyecto) {
        if (!proyecto || !proyecto.programa) return

        Programa programa = proyecto.programa
        programa.removeFromProyectos(proyecto)
        proyecto.delete()
        programa.proyectos.clear()
    }

    Actividad getActividad(Long id, Organizacion org) {
        Actividad actividad = Actividad.get(id)
        if (!actividad) return null

        if (actividad.proyecto.programa.organizacion != org) return null

        actividad
    }

    Actividad actualizarActividad(ActividadCommand command, Organizacion org) {
        if (!command.validate()) return null

        if (command.orgId != org.id) {
            Actividad actividad = new Actividad()
            actividad.errors.reject('error.invalid.token')
            return actividad
        }

        Actividad actividad = command.id ? Actividad.get(command.id) : null
        if (actividad) {
            actividad.nombre = command.nombre
            actividad.descripcion = command.descripcion
            actividad.contenido = command.contenido
            if (command.proyectoId != actividad?.proyecto?.id) {
                actividad.proyecto = getProyecto(command.proyectoId, org)
            }
            actividad.save()
        } else {
            Proyecto proyecto = getProyecto(command.proyectoId, org)
            if (proyecto) {
                actividad = new Actividad(command.properties)
                actividad.proyecto = proyecto
                actividad.save()
            } else {
                actividad = new Actividad()
                actividad.errors.rejectValue('proyecto', 'proyecto.not.found')
            }
        }
        return actividad
    }

    void eliminarActividad(Long id) {
        eliminarActividad(Actividad.get(id))
    }

    void eliminarActividad(Actividad actividad) {
        if (!actividad || !actividad.proyecto) return

        Proyecto proyecto = actividad.proyecto
        proyecto.removeFromActividades(actividad)
        actividad.delete()
        proyecto.actividades.clear()
    }

    Evento getEvento(Long id, Organizacion org) {
        Evento evento = Evento.get(id)
        if (!evento) return null

        if (evento.organizacion != org) return null

        evento
    }

    Evento actualizarEvento(EventoCommand command, Organizacion org) {
        if (!command.validate()) return null

        if (command.orgId != org.id) {
            Evento evento = new Evento()
            evento.errors.reject('error.invalid.token')
            return evento
        }

        Evento evento = command.id ? Evento.get(command.id) : null
        command.fechaIni = command.componerFechaInicial()
        command.fechaFin = command.componerFechaFinal()
        if (command.fechaDifusion)
            command.fechaDifusion = command.componerFechaDifusion()
        if (evento) {
            evento.imagen = command.imagen
            evento.nombre = command.nombre
            evento.descripcion = command.descripcion
            evento.fechaIni = command.fechaIni
            evento.fechaFin = command.fechaFin
            evento.fechaDifusion = command.fechaDifusion
            if (command.direccion) {
                if (!evento.direccion) evento.direccion = new Domicilio()
                evento.direccion.calle = command.direccion.calle
                evento.direccion.numero = command.direccion.numero
                evento.direccion.barrio = command.direccion.barrio
                evento.direccion.distrito = command.direccion.distrito
                evento.direccion.localidad = command.direccion.localidad
                evento.direccion.provincia = command.direccion.provincia
            }
            evento.save()
        } else {
            evento = new Evento(nombre: command.nombre, descripcion: command.descripcion, imagen: command.imagen)
            evento.organizacion = org
            evento.fechaIni = command.fechaIni
            evento.fechaFin = command.fechaFin
            evento.fechaDifusion = command.fechaDifusion
            if (command.direccion)
                evento.direccion = new Domicilio(command.direccion.properties)
            evento.save()
        }
        return evento
    }

    void eliminarEvento(Long id) {
        eliminarEvento(Evento.get(id))
    }

    void eliminarEvento(Evento evento) {
        if (!evento) return
        evento.delete()
    }

    def getProximosEventos(Organizacion org) {
        Evento.findAllByOrganizacionAndFechaIniGreaterThanEquals(org, new Date())
    }

    def getPlanificaciones(Organizacion org) {
        def eventos = getProximosEventos(org)
        def programas = getProgramas(org)
        def proyectos = programas*.proyectos.flatten()
        def actividades = proyectos*.actividades.flatten()
        [programas: programas, proyectos: proyectos, actividades: actividades, eventos: eventos]
    }

    def getResumen(Organizacion org) {
        int totalEventos = Evento.countByPublicadoAndOrganizacion(true, org)
        int totalProgramas = Programa.countByPublicadoAndOrganizacion(true, org)
        int totalProyectos = Proyecto.countByPublicadoAndOrganizacion(true, org)
        int totalActividades = Actividad.countByPublicadoAndOrganizacion(true, org)

        return [eventos: totalEventos, programas: totalProgramas,
            proyectos: totalProyectos, actividades: totalActividades]
    }

}
