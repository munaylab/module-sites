package munaylab.sites

import org.munaylab.user.User
import org.munaylab.categoria.TipoUsuario
import org.munaylab.contenido.Accion
import org.munaylab.contenido.Articulo
import org.munaylab.contenido.Cabecera
import org.munaylab.contenido.Principal
import org.munaylab.osc.Organizacion
import org.munaylab.osc.EstadoOrganizacion
import org.munaylab.osc.TipoOrganizacion
import org.munaylab.osc.UserOrganizacion
import org.munaylab.planificacion.Actividad
import org.munaylab.planificacion.Proyecto
import org.munaylab.planificacion.Programa
import org.munaylab.security.Role
import org.munaylab.security.UserRole

class BootStrap {

    def init = { servletContext ->
        log.info "initializing..."

        environments {
            development {
                crearOrganizacion()
            }
        }
    }

    void crearOrganizacion() {
        Role oscAdmin = Role.findByAuthority('ROLE_OSC_ADMIN')
                ?: new Role(authority: 'ROLE_OSC_ADMIN').save()
        def admin = TipoUsuario.findByNombre('ADMINISTRADOR')
                ?: new TipoUsuario(nombre: 'ADMINISTRADOR').save()
        def user = User.findByUsername('mcaligares@gmail.com')
        if (!user) {
            user = new User().with {
                username    = 'mcaligares@gmail.com'
                nombre      = 'Augusto'
                apellido    = 'Caligares'
                password    = 'Pass1234!'
                it
            }.save()
            new UserRole(user: user, role: oscAdmin).save()
        }
        def org = Organizacion.findByNombre('MunayLab')
        if (!org) {
            org = new Organizacion().with {
                nombre      = 'MunayLab'
                nombreURL   = 'munaylab'
                tipo        = TipoOrganizacion.FUNDACION
                estado      = EstadoOrganizacion.REGISTRADA
                objeto      = 'Brindar herramientas innovadoras a las organizaciones de la sociedad civil.'
                it
            }
            org.addToAdmins(new UserOrganizacion(user: user, tipo: admin)).save()
        }

        crearPlanificacion(org)
        agregarArticulos(org, user)
    }

    void agregarArticulos(Organizacion org, User user) {
        def landing = new Articulo().with {
            autor           = user
            organizacion    = org
            imagen          = 'http://placehold.it/600x400'
            publicado       = true
            titulo          = 'Principal'
            palabrasClaves  = 'principal'
            descripcion     = 'descripcion principal'
            contenido       = '<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p> <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>'
            it
        }.save()
        def principal = new Principal().with {
            titulo          = 'Titulo Principal'
            imagen          = 'http://www.csrindia.org/images/Banner-Social-Surfing.jpg'
            contenido       = landing
            organizacion    = org
            it
        }
        principal.accionPrincipal = new Accion().with {
            titulo      = 'Titulo Accion'
            link        = 'link'
            principal   = principal
            it
        }
        principal.save(failOnError: true)

        ['Mision', 'Nosotros', 'Programas', 'Contribuir', 'Contacto'].eachWithIndex { nombre, i ->
            def articulo = new Articulo().with {
                titulo          = nombre
                contenido       = '<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p> <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>'
                imagen          = 'http://placehold.it/900x800'
                palabrasClaves  = nombre.toLowerCase()
                descripcion     = "descripcion ${nombre.toLowerCase()}"
                publicado       = true
                autor           = user
                organizacion    = org
                it
            }.save()
            def cabecera = new Cabecera().with {
                organizacion    = org
                contenido       = articulo
                titulo          = articulo.titulo
                nombre          = articulo.titulo.toLowerCase()
                link            = nombre == 'Contacto' ? 'mailto:contacto@munaylab.org' : null
                prioridad       = i
                it
            }.save()
        }
    }

    void crearPlanificacion(Organizacion org) {
        def programa = new Programa().with {
            nombre          = 'Innovaciones Sociales'
            imagen          = 'programa/innovacion'
            descripcion     = 'Brindar innovaciones a las osc.'
            publicado       = true
            organizacion    = org
            it
        }.save()
        def proyecto = new Proyecto().with {
            nombre          = 'Taller de Innovaciones'
            imagen          = 'proyecto/innovacion'
            descripcion     = 'Taller donde se exponen innovaciones para la sociedad.'
            publicado       = true
            organizacion    = org
            programa        = programa
            it
        }.save()
        def actividad = new Actividad().with {
            nombre          = 'Presentacion Innovacion'
            imagen          = 'actividad/innovacion'
            descripcion     = 'Presentacion de innovaciones realizadas en el taller.'
            publicado       = true
            organizacion    = org
            proyecto        = proyecto
            it
        }.save()
    }

    def destroy = {
    }
}
