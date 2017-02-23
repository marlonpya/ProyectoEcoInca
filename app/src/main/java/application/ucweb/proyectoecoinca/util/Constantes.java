package application.ucweb.proyectoecoinca.util;

/**
 * Created by ucweb02 on 12/10/2016.
 */
public class Constantes {
    public static final String CODIGO_COMERCIO          = "test_hW7o8bZRWzFp";

    public static final String URL_REGISTRAR_USUARIO    = "http://uc-web.mobi/LIAISON/api-rest/registrarUsuario";
    public static final String URL_INICIAR_SESION       = "http://uc-web.mobi/LIAISON/api-rest/iniciarSesion";
    public static final String URL_INICIAR_SESION_2     = "http://uc-web.mobi/LIAISON/api-rest/RedSocial";
    public static final String URL_ACTUALIZAR_EMPRESA   = "http://uc-web.mobi/LIAISON/api-rest/actualizarUsuario";
    public static final String URL_BUSQUEDA_SIMPLE      = "http://uc-web.mobi/LIAISON/api-rest/buscar";
    public static final String URL_VAMOS_AL_NEGOCIO     = "http://uc-web.mobi/LIAISON/api-rest/negociosmatch";
    public static final String URL_PAISES               = "http://uc-web.mobi/LIAISON/api-rest/Paises";
    public static final String URL_DEPARTAMENTOS        = "http://uc-web.mobi/LIAISON/api-rest/Regions";

    public static final String URL_EDITARIMG            = "http://uc-web.mobi/LIAISON/api-rest/editarImg";

    public static final String URL_CONTACTOS            = "http://uc-web.mobi/LIAISON/api-rest/ListadoContactos";

    public static final String URL_MIS_SEGUIDORES       = "http://uc-web.mobi/LIAISON/api-rest/negocios";
    public static final String URL_ACTUALIZAR_TOKEN     = "http://uc-web.mobi/LIAISON/api-rest/updatetoke";
    public static final String URL_ACEPTAR_NEGOCIO      = "http://uc-web.mobi/LIAISON/api-rest/AceptarNotificacion";
    public static final String URL_PAGO_PLUS            = "http://uc-web.mobi/LIAISON/api-rest/pago";

    public static final String URL_IGNORAR_EMPRESA      = "http://uc-web.mobi/LIAISON/api-rest/Eliminarmatch";
    public static final String URL_EMPRESA_X_ID         = "http://uc-web.mobi/LIAISON/api-rest/getEmpresa";

    public static final String B_DESACTIVAR_HACER_NEGOCIO = "B_DESACTIVAR_HACER_NEGOCIO";

    public static final String POSICION_I_DETALLE_BUSCAR = "POSICION_I_DETALLE_BUSCAR";

    public static final String L_ID_EMPRESA             = "L_ID_EMPRESA";
    public static final String I_TIPO_PLUS              = "I_TIPO_PLUS";
    public static final String B_RED_SOCIAL_INICIAR_SESION = "B_RED_SOCIAL_INICIAR_SESION";
    public static final String S_EMAIL_INICIAR_SESION   = "S_EMAIL_INICIAR_SESION";
    public static final String S_NOMBRE_INICIAR_SESION  = "S_NOMBRE_INICIAR_SESION";
    public static final String S_APE_INICIAR_SESION     = "S_APE_INICIAR_SESION";
    public static final String EXTRA_IS_REAL            = "EXTRA_IS_REAL";
    public static final String EXTRA_NOT_MATCH          = "EXTRA_NOT_MATCH";

    public static final String FACEBOOK = "facebook";
    public static final String LINKEDIN = "linkedin";

    //LINKEDIN
    public static final String HOST = "api.linkedin.com";
    public static final String FETCH_BASIC_INFO = "https://" + HOST + "/v1/people/~:(id,first-name,last-name,headline,location,industry)";
    public static final String FETCH_CONTACT = "https://" + HOST + "/v1/people/~:(num-connections,email-address,phone-numbers,main-address)";
    public static final String FETCH_PROFILE_PIC = "https://" + HOST + "/v1/people/~:(picture-urls::(original))";
    public static final String SHARE_URL = "https://" + HOST + "/v1/people/~/shares";
    public static final String FETCH_ALL = "https://" + HOST + "/v1/people/~:(id,first-name,last-name,public-profile-url,picture-url,email-address,picture-urls::(original))";
    public static final String EXTRA_SERIALIZABLE_BUSQUEDA = "EXTRA_SERIALIZABLE_BUSQUEDA";

}
