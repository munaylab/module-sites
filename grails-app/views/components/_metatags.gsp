
<%-- URL canónica de tu página. Los Me gusta y las veces que se compartió esta URL se agruparán en esta URL --%>
<meta property="og:url" content="${url}" />
<%-- Título del artículo sin identificación de marca --%>
<meta property="og:title" content="${titulo}" />
<%-- Breve descripción del contenido (normalmente entre dos y cuatro oraciones) --%>
<g:if test="${descripcion}">
  <meta name="description" content="${descripcion}">
  <meta property="og:description" content="${descripcion}" />
</g:if>
<%-- URL de la imagen que aparece cuando alguien comparte el contenido en Facebook --%>
<g:if test="${imagen}">
  <meta property="og:image" content="${imagen}" />
</g:if>
<%-- Tipo de elementos multimedia del contenido --%>
<meta property="og:type" content="article" />
<%-- The card type, which will be one of 'summary', 'summary_large_image', 'app', or 'player' --%>
<meta name="twitter:card" content="summary" />
<%-- @username for the website used in the card footer. --%>
<meta name="twitter:site" content="@munaylab" />

<g:if test="${keywords}">
  <meta name="keywords" content="${keywords}">
</g:if>
