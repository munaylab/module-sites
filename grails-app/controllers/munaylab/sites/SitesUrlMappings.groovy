package munaylab.sites

class SitesUrlMappings {

    static mappings = {
        "/$nombreURL"(controller: 'sites', action: 'index')
        "/$nombreOrganizacion/blog/$nombreArticulo"(controller: 'sites', action: 'articulo')
        "/$nombreOrganizacion/$action/$id?"(controller: 'sites')

        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
