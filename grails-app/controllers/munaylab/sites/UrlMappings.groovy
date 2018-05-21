package munaylab.sites

class UrlMappings {

    static mappings = {
        "/sites/$nombreURL"(controller: 'sites', action: 'index')
        "/org/$nombreOrganizacion/$nombreArticulo"(controller: 'sites', action: 'articulo')

        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
