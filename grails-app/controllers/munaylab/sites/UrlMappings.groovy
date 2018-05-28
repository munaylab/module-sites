package munaylab.sites

class UrlMappings {

    static mappings = {
        "/org/$nombreURL"(controller: 'sites', action: 'index')
        "/org/$nombreOrganizacion/blog"(controller: 'sites', action: 'blog')
        "/org/$nombreOrganizacion/blog/$nombreArticulo"(controller: 'sites', action: 'articulo')
        "/org/$nombreOrganizacion/planning"(controller: 'sites', action: 'planning')
        "/org/$nombreOrganizacion/programa/$id"(controller: 'sites', action: 'programa')
        "/org/$nombreOrganizacion/proyecto/$id"(controller: 'sites', action: 'proyecto')
        "/org/$nombreOrganizacion/actividad/$id"(controller: 'sites', action: 'actividad')

        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
