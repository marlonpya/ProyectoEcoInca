package application.ucweb.proyectoecoinca.util;

/**
 * Created by ucweb02 on 12/10/2016.
 */
public class Constantes {

    public static final String URL_REGISTRAR_USUARIO    = "http://uc-web.mobi/LIAISON/api-rest/registrarUsuario";
    public static final String URL_INICIAR_SESION       = "http://uc-web.mobi/LIAISON/api-rest/iniciarSesion";
    public static final String URL_ACTUALIZAR_EMPRESA   = "http://uc-web.mobi/LIAISON/api-rest/actualizarUsuario";
    public static final String URL_BUSQUEDA_SIMPLE      = "http://uc-web.mobi/LIAISON/api-rest/buscar";
    public static final String URL_VAMOS_AL_NEGOCIO     = "http://uc-web.mobi/LIAISON/api-rest/negociosmatch";

    public static final String URL_CONTACTOS            = "";
    public static final String URL_MIS_SEGUIDORES       = "http://uc-web.mobi/LIAISON/api-rest/negocios";
    public static final String URL_ACTUALIZAR_TOKEN     = "http://uc-web.mobi/LIAISON/api-rest/updatetoke";
    public static final String CULQUI_KEY               = "test_GaLSmy33nYBB";
    public static final String URL_PAISES_DEPARTAMENTOS = "";
    public static final String URL_ACEPTAR_NEGOCIO      = "http://uc-web.mobi/LIAISON/api-rest/AceptarNotificacion";

    public static final String URL_IGNORAR_EMPRESA      = "http://uc-web.mobi/LIAISON/api-rest/Eliminarmatch";
    public static final String B_DESACTIVAR_HACER_NEGOCIO = "B_DESACTIVAR_HACER_NEGOCIO";

    public static final String POSICION_I_DETALLE_BUSCAR = "POSICION_I_DETALLE_BUSCAR";

    public static final String S_SEC_INDUSTRIAL = "S_SEC_INDUSTRIAL";
    public static final String S_PRODUCTO_REGISTRO = "S_PRODUCTO_REGISTRO";
    public static final String S_SERVICIO_REGISTRO = "S_SERVICIO_REGISTRO";
    public static final String S_CERTIFICADO_REGISTRO = "S_CERTIFICADO_REGISTRO";
    public static final String L_ID_EMPRESA = "L_ID_EMPRESA";

    public static final String ARRAY_DEPARTAMENTOS[] = {
            "Amazonas",
            "Ancash" ,
            "Apurimac" ,
            "Arequipa" ,
            "Ayacucho" ,
            "Cajamarca" ,
            "Callao",
            "Cusco",
            "Huancavelica" ,
            "Huanuco" ,
            "Ica" ,
            "Junin" ,
            "La Libertad" ,
            "Lambayeque" ,
            "Lima" ,
            "Loreto" ,
            "Madre De Dios",
            "Moquegua" ,
            "Pasco" ,
            "Piura" ,
            "Puno" ,
            "San Martin" ,
            "Tacna" ,
            "Tumbes" ,
            "Ucayali"};

    public static final String[] getPaises() {
        final String ARRAY_PAISES[] = {"Afganistán" ,
                "Albania" ,
                "Alemania" ,
                "Andorra" ,
                "Angola" ,
                "Antigua y Barbuda" ,
                "Arabia Saudita" ,
                "Argelia" ,
                "Argentina" ,
                "Armenia" ,
                "Australia" ,
                "Austria" ,
                "Azerbaiyán" ,
                "Bahamas" ,
                "Bangladés" ,
                "Barbados" ,
                "Baréin" ,
                "Bélgica" ,
                "Belice" ,
                "Benín" ,
                "Bielorrusia" ,
                "Birmania" ,
                "Bolivia" ,
                "Bosnia y Herzegovina" ,
                "Botsuana" ,
                "Brasil" ,
                "Brunéi" ,
                "Bulgaria" ,
                "Burkina Faso" ,
                "Burundi" ,
                "Bután" ,
                "Cabo Verde" ,
                "Camboya" ,
                "Camerún" ,
                "Canadá" ,
                "Catar" ,
                "Chad" ,
                "Chile" ,
                "China" ,
                "Chipre" ,
                "Ciudad del Vaticano" ,
                "Colombia" ,
                "Comoras" ,
                "Corea del Norte" ,
                "Corea del Sur" ,
                "Costa de Marfil" ,
                "Costa Rica" ,
                "Croacia" ,
                "Cuba" ,
                "Dinamarca" ,
                "Dominica" ,
                "Ecuador" ,
                "Egipto" ,
                "El Salvador" ,
                "Emiratos Árabes Unidos" ,
                "Eritrea" ,
                "Eslovaquia" ,
                "Eslovenia" ,
                "España" ,
                "Estados Unidos" ,
                "Estonia" ,
                "Etiopía" ,
                "Filipinas" ,
                "Finlandia" ,
                "Fiyi" ,
                "Francia" ,
                "Gabón" ,
                "Gambia" ,
                "Georgia" ,
                "Ghana" ,
                "Granada" ,
                "Grecia" ,
                "Guatemala" ,
                "Guyana" ,
                "Guinea" ,
                "Guinea ecuatorial" ,
                "Guinea-Bisáu" ,
                "Haití" ,
                "Honduras" ,
                "Hungría" ,
                "India" ,
                "Indonesia" ,
                "Irak" ,
                "Irán" ,
                "Irlanda" ,
                "Islandia" ,
                "Islas Marshall" ,
                "Islas Salomón" ,
                "Israel" ,
                "Italia" ,
                "Jamaica" ,
                "Japón" ,
                "Jordania" ,
                "Kazajistán" ,
                "Kenia" ,
                "Kirguistán" ,
                "Kiribati" ,
                "Kuwait" ,
                "Laos" ,
                "Lesoto" ,
                "Letonia" ,
                "Líbano" ,
                "Liberia" ,
                "Libia" ,
                "Liechtenstein" ,
                "Lituania" ,
                "Luxemburgo" ,
                "Madagascar" ,
                "Malasia" ,
                "Malaui" ,
                "Maldivas" ,
                "Malí" ,
                "Malta" ,
                "Marruecos" ,
                "Mauricio" ,
                "Mauritania" ,
                "México" ,
                "Micronesia" ,
                "Moldavia" ,
                "Mónaco" ,
                "Mongolia" ,
                "Montenegro" ,
                "Mozambique" ,
                "Namibia" ,
                "Nauru" ,
                "Nepal" ,
                "Nicaragua" ,
                "Níger" ,
                "Nigeria" ,
                "Noruega" ,
                "Nueva Zelanda" ,
                "Omán" ,
                "Países Bajos" ,
                "Pakistán" ,
                "Palaos" ,
                "Panamá" ,
                "Papúa Nueva Guinea" ,
                "Paraguay" ,
                "Perú" ,
                "Polonia" ,
                "Portugal" ,
                "Reino Unido" ,
                "República Centroafricana" ,
                "República Checa" ,
                "República de Macedonia" ,
                "República del Congo" ,
                "República Democrática del Congo" ,
                "República Dominicana" ,
                "República Sudafricana" ,
                "Ruanda" ,
                "Rumanía" ,
                "Rusia" ,
                "Samoa" ,
                "San Cristóbal y Nieves" ,
                "San Marino" ,
                "San Vicente y las Granadinas" ,
                "Santa Lucía" ,
                "Santo Tomé y Príncipe" ,
                "Senegal" ,
                "Serbia" ,
                "Seychelles" ,
                "Sierra Leona" ,
                "Singapur" ,
                "Siria" ,
                "Somalia" ,
                "Sri Lanka" ,
                "Suazilandia" ,
                "Sudán" ,
                "Sudán del Sur" ,
                "Suecia" ,
                "Suiza" ,
                "Surinam" ,
                "Tailandia" ,
                "Tanzania" ,
                "Tayikistán" ,
                "Timor Oriental" ,
                "Togo" ,
                "Tonga" ,
                "Trinidad y Tobago" ,
                "Túnez" ,
                "Turkmenistán" ,
                "Turquía" ,
                "Tuvalu" ,
                "Ucrania" ,
                "Uganda" ,
                "Uruguay" ,
                "Uzbekistán" ,
                "Vanuatu" ,
                "Venezuela" ,
                "Vietnam" ,
                "Yemen" ,
                "Yibuti" ,
                "Zambia" ,
                "Zimbabue" };
        /*String[] nuevo = new String[ARRAY_PAISES.length];
        for (int i = 0; i < ARRAY_PAISES.length; i++) {

            for (int j = 0; j < ARRAY_PAISES[i].length(); j ++){
                if (ARRAY_PAISES[i].equals("'\'")){

                }
            }
            String limpio =
            nuevo[i] = ARRAY_PAISES[i];
        }*/
        return ARRAY_PAISES;
    }

    //LINKEDIN
    public static final String HOST = "api.linkedin.com";
    public static final String FETCH_BASIC_INFO = "https://" + HOST + "/v1/people/~:(id,first-name,last-name,headline,location,industry)";
    public static final String FETCH_CONTACT = "https://" + HOST + "/v1/people/~:(num-connections,email-address,phone-numbers,main-address)";
    public static final String FETCH_PROFILE_PIC = "https://" + HOST + "/v1/people/~:(picture-urls::(original))";
    public static final String SHARE_URL = "https://" + HOST + "/v1/people/~/shares";
}
