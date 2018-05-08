package munaylab.sites

import org.munaylab.factory.SiteBuilder as Builder
import org.munaylab.contacto.Contacto
import org.munaylab.contacto.TipoContacto
import org.munaylab.contenido.Articulo
import org.munaylab.contenido.TipoArticulo
import org.munaylab.user.User
import org.munaylab.categoria.TipoUsuario
import org.munaylab.osc.Organizacion
import org.munaylab.osc.EstadoOrganizacion
import org.munaylab.osc.TipoOrganizacion
import org.munaylab.osc.UserOrganizacion
import org.munaylab.balance.Asiento
import org.munaylab.balance.Categoria
import org.munaylab.balance.TipoAsiento
import org.munaylab.planificacion.Actividad
import org.munaylab.planificacion.Proyecto
import org.munaylab.planificacion.Programa
import org.munaylab.security.Role
import org.munaylab.security.UserRole

class BootStrap {

    def init = { servletContext ->
        log.info "initializing..."

        crearRoles()
        environments {
            development {
                crearOrganizacionParaPruebas()
            }
        }
    }

    def crearRoles() {
        Role oscUser = Role.findByAuthority('ROLE_OSC_USER')
                ?: new Role(authority: 'ROLE_OSC_USER').save()
        Role oscContador = Role.findByAuthority('ROLE_OSC_CONTADOR')
                ?: new Role(authority: 'ROLE_OSC_CONTADOR').save()
        Role oscEscritor = Role.findByAuthority('ROLE_OSC_ESCRITOR')
                ?: new Role(authority: 'ROLE_OSC_ESCRITOR').save()
        Role oscAdmin = Role.findByAuthority('ROLE_OSC_ADMIN')
                ?: new Role(authority: 'ROLE_OSC_ADMIN').save()
        Role user = Role.findByAuthority('ROLE_USER')
                ?: new Role(authority: 'ROLE_USER').save()
        [oscContador, oscEscritor, oscAdmin, user]
    }

    void crearOrganizacionParaPruebas() {
        def admin = new TipoUsuario(nombre: 'ADMINISTRADOR').save(failOnError: true)
        def user = Builder.user
                .conUsername('mcaligares@gmail.com')
                .conNombre('Augusto')
                .conApellido('Caligares')
                .conPassword('Pass1234!')
                .crear
        def org = Builder.organizacion
                .conNombre('MunayLab')
                .conNombreUrl('munaylab')
                .deTipo(TipoOrganizacion.FUNDACION)
                .conEstado(EstadoOrganizacion.REGISTRADA)
                .conObjeto('Brindar herramientas innovadoras a las organizaciones de la sociedad civil.')
                .crear
        def userOrg = Builder.organizacion.userOrganizacion
                .conUser(user)
                .conOrganizacion(org)
                .deTipo(admin)
                .crear
        org.addToAdmins(userOrg)
        org.save(failOnError: true)

        Role adminRole = Role.findByAuthority('ROLE_OSC_ADMIN')
        UserRole userRole = new UserRole(user: user, role: adminRole).save(failOnError: true)

        crearAsientos(org)
        crearPlanificacion(org)
        agregarContactos(org)
        agregarArticulos(org, user)
    }

    void crearAsientos(org) {
        def ingresoBienes = Builder.asiento.crearCategoriaIngreso('bienes').save()
        def ingresoServicios = Builder.asiento.crearCategoriaIngreso('servicios').save()
        def egresoVarios = Builder.asiento.crearCategoriaEgreso('varios').save()
        def egresoSueldos = Builder.asiento.crearCategoriaEgreso('sueldos').save()

        def egresoBuilder = Builder.asiento.conOrganizacion(org)
                .conDetalle('egreso').deTipo(TipoAsiento.EGRESO)
        def ingresoBuilder = Builder.asiento.conOrganizacion(org)
                .conDetalle('ingreso').deTipo(TipoAsiento.INGRESO)
        [new Date() -90, new Date() -60, new Date() -30, new Date()].each {
            egresoBuilder.conFecha(it).conMonto(100.0).conCategoria(egresoVarios).crear.save()
            egresoBuilder.conFecha(it).conMonto(150.0).conCategoria(egresoSueldos).crear.save()
            ingresoBuilder.conFecha(it).conMonto(200.0).conCategoria(ingresoBienes).crear.save()
            ingresoBuilder.conFecha(it).conMonto(110.0).conCategoria(ingresoServicios).crear.save()
        }
    }

    void crearPlanificacion(Organizacion org) {
        Actividad actividad = new Actividad(nombre: 'Presentacion Innovacion', imagen: 'proyecto/actividad/innovacion',
            descripcion: 'Presentacion de innovaciones realizadas en el taller', publicado: true)
        Proyecto proyecto = new Proyecto(nombre: 'Taller de Innovaciones', imagen: 'proyecto/programa/innovacion',
            descripcion: 'Taller donde se exponen innovaciones para la sociedad', publicado: true)
        Programa programa = new Programa(nombre: 'Innovaciones Sociales', imagen: 'programa/innovacion',
            descripcion: 'Brindar innovaciones a las osc.', publicado: true)

        proyecto.addToActividades(actividad)
        programa.addToProyectos(proyecto)
        org.addToProgramas(programa)
        org.save()
    }

    void agregarContactos(Organizacion org) {
        org.addToContactos(Builder.contacto.conWeb('munaylab.org').crear)
        org.addToContactos(Builder.contacto.conEmail('contacto@munaylab.org').crear)
        org.addToContactos(Builder.contacto.conTelefono('(0388) 1234567').crear)
        org.addToContactos(Builder.contacto.conCelular('(0388) 1234567').crear)
        org.save(failOnError: true)
    }

    void agregarArticulos(Organizacion org, User user) {
        def articuloBuilder = Builder.articulo.conAutor(user).conOrganizacion(org)
                .conImagen('http://placehold.it/900x800').publicado()

        articuloBuilder.conTitulo('Mision').conPalabrasClaves('mision').conDescripcion('descripcion mision')
                .conContenido('<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p> <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>')
                .crear.save()
        articuloBuilder.conTitulo('Nosotros').conPalabrasClaves('nosotros').conDescripcion('descripcion nosotros')
                .conContenido('<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p> <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>')
                .crear.save()
        articuloBuilder.conTitulo('Programas').conPalabrasClaves('programas').conDescripcion('descripcion programas')
                .conContenido('<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p> <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>')
                .crear.save()
        articuloBuilder.conTitulo('Contacto').conPalabrasClaves('contacto').conDescripcion('descripcion contacto')
                .conContenido('<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p> <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>')
                .crear.save()
        articuloBuilder.conTitulo('Contribuir').conPalabrasClaves('contribuir').conDescripcion('descripcion contribuir')
                .conContenido('<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p> <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>')
                .crear.save()
    }

    def destroy = {
    }
}
