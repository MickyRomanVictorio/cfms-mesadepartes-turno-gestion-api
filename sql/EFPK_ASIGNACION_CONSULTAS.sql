CREATE OR REPLACE PACKAGE SISEFE.EFPK_ASIGNACION_CONSULTAS AS
/***************************************************************************************************************
* Nombre              : SISEFE.EFPK_ASIGNACION_CONSULTAS
* Autor               : Dany Daniel Chuquipoma
* Versión             : 1.0
* Descripción         : Funcionalidad de consulta Asignación de Casos
* Fecha de Creación	: 28/11/2023

------------------------------------------------------------------------
Fecha       Modificador por                 DBA pase producción            Descripción del Cambio
=========   ===============                 ================               ======================
11/01/2024  Marco Nina Aguilar                   -                              Adecuaciones en base a los ultimos cambios en la BD. 
'********************************************************************************************************************/
                                                  
     ----STORED PROCEDURES PARA LISTAR LOS CASOS ASIGNADOS A LOS FISCALES
    PROCEDURE EFSP_CASOS_ASIGNADOS_FISCALES (
                                            PI_V_DESPACHO         IN     VARCHAR2,
                                           
                                            PO_V_ERR_COD          OUT    VARCHAR2,
                                            PO_V_ERR_MSG          OUT    VARCHAR2,
                                            PO_CR_LISTA_FISCALES  OUT    SYS_REFCURSOR);                                              
                                            
                                            
    PROCEDURE EFSP_LISTAR_ASIGNACION_CASOS (
                                          
                                            P_V_TIEMPO             IN     VARCHAR2,
                                            P_V_DEPENDENCIA        IN     VARCHAR2,
                                            P_V_USUARIO            IN     VARCHAR2,
                                            PO_V_ERR_COD           OUT    VARCHAR2,
                                            PO_V_ERR_MSG           OUT    VARCHAR2,                                        
                                            P_CR_LISTAR_ASIG_CASOS OUT    SYS_REFCURSOR);
                                            
    
END EFPK_ASIGNACION_CONSULTAS;
/
CREATE OR REPLACE PACKAGE BODY SISEFE.EFPK_ASIGNACION_CONSULTAS AS
/***************************************************************************************************************
* Nombre              : SISEFE.EFPK_ASIGNACION_CONSULTAS
* Autor               : Dany Daniel Chuquipoma
* Versión             : 1.0
* Descripción         : Funcionalidad de consulta Asignación de Casos
* Fecha de Creación	: 28/11/2023

------------------------------------------------------------------------
Fecha       Modificador por         DBA pase producción            Descripción del Cambio
=========   ===============         ================               ======================
11/01/2024  Marco Nina Aguilar      -                              Adecuaciones en base a los ultimos cambios en la BD. 
********************************************************************************************************************/
    EX_CONTROLADO EXCEPTION;
    V_V_ERR_COD VARCHAR2(255);
    V_V_ERR_MSG VARCHAR2(255);

   	----STORED PROCEDURES PARA LISTAR LOS CASOS ASIGNADOS A LOS FISCALES
    PROCEDURE EFSP_CASOS_ASIGNADOS_FISCALES (
                                            PI_V_DESPACHO         IN     VARCHAR2,
                                            PO_V_ERR_COD          OUT    VARCHAR2,
                                            PO_V_ERR_MSG          OUT    VARCHAR2,
                                            PO_CR_LISTA_FISCALES  OUT    SYS_REFCURSOR) AS     
         V_V_COD_OK    VARCHAR2(1)  := '0';
         V_V_MSG_OK    VARCHAR2(100):= 'LA OPERACIÓN SE REALIZÓ SATISFACTORIAMENTE';
                                                                                            
    BEGIN
    
        IF PI_V_DESPACHO IS NULL OR PI_V_DESPACHO = '' THEN
            PO_V_ERR_COD := '42201026';
            SISCFE.CFPK_MENSAJE_VALIDACION.CFSP_OBTENER_MENSAJE_VALIDACION(PO_V_ERR_COD, PO_V_ERR_MSG, V_V_ERR_COD, V_V_ERR_MSG);
            RAISE EX_CONTROLADO;
        END IF;
           
        OPEN PO_CR_LISTA_FISCALES FOR 
            SELECT 
        SU.ID_V_USUARIO ID_FISCAL,
        CP.NU_V_DOCUMENTO || ' '|| TRIM(PN.NO_V_CIUDADANO || ' ' || PN.AP_V_PATERNO || ' ' || NVL(PN.AP_V_MATERNO, ' ')) NOMBRE,
          NVL(COUNT(1),0) CASOS_ASIGNADOS
       FROM SISSAD.SATV_USUARIO SU
       LEFT JOIN SISEFE.EFTV_FISCAL_ASIGNADO EFA
               ON EFA.ID_V_USUARIO = SU.ID_V_USUARIO
              AND EFA.ES_C_FISCAL_ASIGNADO = '1'
       INNER JOIN SISSAD.SATV_DEPENDENCIA_USUARIO SDU 
               ON SU.ID_V_USUARIO = SDU.ID_V_USUARIO 
       INNER JOIN SISCFE.CFTV_PERSONA CP
               ON CP.ID_V_PERSONA = SU.ID_V_PERSONA
       INNER JOIN SISCFE.CFTV_PERSONA_NATURAL PN
               ON CP.ID_V_PERSONA = PN.ID_V_PERSONA
            WHERE SDU.CO_V_DESPACHO = PI_V_DESPACHO
         GROUP BY SU.ID_V_USUARIO, CP.NU_V_DOCUMENTO, PN.NO_V_CIUDADANO, PN.AP_V_PATERNO, PN.AP_V_MATERNO
         ORDER BY PN.NO_V_CIUDADANO, PN.AP_V_PATERNO, PN.AP_V_MATERNO;
  
        PO_V_ERR_COD := V_V_COD_OK;
        PO_V_ERR_MSG := V_V_MSG_OK;
        
    EXCEPTION
        WHEN EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : '|| PO_V_ERR_COD || ' - ' || PO_V_ERR_MSG);
        WHEN OTHERS THEN
            PO_V_ERR_COD := '1';
            PO_V_ERR_MSG := 'EFPK_ASIGNACION_CONSULTAS.EFSP_CASOS_ASIGNADOS_FISCALES - ERROR: '||SQLERRM;
    END EFSP_CASOS_ASIGNADOS_FISCALES; 
    
    PROCEDURE EFSP_LISTAR_ASIGNACION_CASOS (
                                            P_V_TIEMPO             IN     VARCHAR2,
                                            P_V_DEPENDENCIA        IN     VARCHAR2,
                                            P_V_USUARIO            IN     VARCHAR2,
                                            PO_V_ERR_COD           OUT    VARCHAR2,
                                            PO_V_ERR_MSG           OUT    VARCHAR2,                                        
                                            P_CR_LISTAR_ASIG_CASOS OUT    SYS_REFCURSOR) AS 
                                            
        V_V_CAMPOS                    VARCHAR2(7000);
        V_V_FROM                      VARCHAR2(7000);       
        V_V_SQL                       VARCHAR2(14000);
        V_V_SQL_DELITOS               VARCHAR2(7000);
        V_V_SQL_NRO_PARTES            VARCHAR2(2000);
        V_V_SQL_PARTES_PROCESALES     VARCHAR2(2000);
        V_V_SQL_DELITOS_DENUNCIA      VARCHAR2(2000);
        V_V_SQL_NOT_CLASIFICACION     VARCHAR2(2000);
        V_V_SQL_PLAZO_HRS             VARCHAR2(2000);
        V_V_OPERADOR                  VARCHAR2(10);
        P_V_WHERE                     VARCHAR2(7000);
        V_V_LEIDO                     VARCHAR2(500);
        V_V_SEMAFORO_NRO              VARCHAR2(500);
        V_V_SEMAFORO_COLOR            VARCHAR2(500);
    BEGIN
    
        OPEN P_CR_LISTAR_ASIG_CASOS FOR 
           select 
                caso.ID_V_CASO as ideCaso
                , caso.CO_V_CASO as nroCaso
                , origen.no_v_tipo_origen as origen
                , TIPO_SUJETO.NO_V_TIPO_SUJETO as tipoperfil
                , CASE WHEN  persona.DE_V_RAZON_SOCIAL IS NULL then PERSONA.NOMBRESCOMPLETOS  ELSE persona.DE_V_RAZON_SOCIAL END AS nombrePersona 
                , cast( nvl(remi.NU_V_TELEFONO,0) as varchar2(50)) as celular
                , nvl(remi.DE_V_CORREO, '-' ) as correo
                ,nvl(remi.NU_V_TELEFONO,'-' ) as telefono
                , to_char(caso.FE_D_INGRESO_COMPUTABLE,    'dd/mm/yyyy'  ) AS fechaIngreso
                , to_char(caso.FE_D_INGRESO_COMPUTABLE,   'hh24:mi'   ) AS hora
                , CASE WHEN semaforo.semaforo_plazo = 1 THEN 'Dentro del plazo'
                    WHEN semaforo.semaforo_plazo = 2 THEN 'Plazo por vencer' 
                    WHEN semaforo.semaforo_plazo = 3 THEN 'Plazo vencido'
                    ELSE NULL
                    END as plazo
                ,semaforo.semaforo_plazo as SEMAFORO_NRO
                ,semaforo.semaforo_color as SEMAFORO_COLOR
                ,semaforo.semaforo_tiempo as SEMAFORO_TIEMPO
                ,semaforo.FE_D_FINCALCULADA as SEMAFOROFECHA --AND ID_V_USUARIO=P_V_USUARIO
                ,(SELECT COUNT(1) FROM sisefe.EFTV_CASO_LECTURA 
                        WHERE ID_V_CASO =caso.ID_V_CASO   AND ID_N_ESTADO_CASO='1') as leido
                ,(select LISTAGG(MDELITO.NO_V_DELITO, ' |' )  from SISMPA.MPTV_DELITO_DENUNCIA DELITO                          
                                            INNER JOIN SISMAEST.CFTM_DELITO MDELITO ON (MDELITO.ID_N_DELITO=DELITO.ID_N_DELITO)
                                            WHERE DELITO.ID_V_DENUNCIA=caso.ID_V_DENUNCIA) DELITOS
                ,(select COUNT(1) from SISMPA.MPTV_DENUNCIA DENUNCIA
                                            INNER JOIN SISCFE.CFTV_SUJETO_CASO SUJETO_CASO ON (
                                            SUJETO_CASO.ID_V_DENUNCIA =DENUNCIA.ID_V_DENUNCIA and 
                                            SUJETO_CASO.ID_V_CASO=DENUNCIA.ID_V_CASO
                                            ) WHERE DENUNCIA.ID_V_CASO = caso.ID_V_CASO AND DENUNCIA.ID_V_DENUNCIA=caso.ID_V_DENUNCIA) cantidadPartes
                ,(select  LISTAGG( nvl(PN.nombresCompletos,PJ.nombresCompletos), ' |' )  from SISMPA.MPTV_DENUNCIA DENUNCIA
                        INNER JOIN SISCFE.CFTV_SUJETO_CASO SUJETO_CASO ON (
                        SUJETO_CASO.ID_V_DENUNCIA =DENUNCIA.ID_V_DENUNCIA and 
                        SUJETO_CASO.ID_V_CASO=DENUNCIA.ID_V_CASO
                        ) 
                         LEFT join (
                          select  
                          p.ID_V_PERSONA ID_V_PERSONA, 
                          pn.NO_V_CIUDADANO ||  ' ' || pn.AP_V_PATERNO  ||  ' ' ||pn.AP_V_MATERNO nombresCompletos 
                          from SISCFE.CFTV_PERSONA p
                          inner join SISCFE.CFTV_PERSONA_NATURAl pn on (p.ID_V_PERSONA=pn.ID_V_PERSONA)
                        ) PN ON (PN.ID_V_PERSONA=SUJETO_CASO.ID_V_PERSONA)
                        LEFT join (
                          select 
                          p.ID_V_PERSONA ID_V_PERSONA, 
                          pj.DE_V_RAZON_SOCIAL nombresCompletos  
                          from SISCFE.CFTV_PERSONA p
                          inner join SISCFE.CFTV_PERSONA_JURIDICA pj on (pj.ID_V_PERSONA=p.ID_V_PERSONA)
                        ) PJ ON (PJ.ID_V_PERSONA=SUJETO_CASO.ID_V_PERSONA)
                         WHERE DENUNCIA.ID_V_CASO = caso.ID_V_CASO AND DENUNCIA.ID_V_DENUNCIA=caso.ID_V_DENUNCIA) partesProcesales
                ,( select LISTAGG(DELITOS.NO_V_DELITO, '|'  )  from SISMPA.MPTV_DELITO_DENUNCIA DELITO_DENUNCIA
                                inner join (
                                select DELITO.ID_N_DELITO,
                                DELITO_ESPECIFICO.ID_N_ESPECIFICO,
                                DELITO_SUBGENERICO.ID_N_SUBGENERICO,
                                (DELITO.NO_V_DELITO ||   '/' ||  DELITO_ESPECIFICO.DE_V_DETALLE_DELITO || '/' || DELITO_SUBGENERICO.NO_V_SUBGENERICO) NO_V_DELITO
                                from SISMAEST.CFTM_DELITO DELITO
                                INNER JOIN SISMAEST.CFTM_DELITO_ESPECIFICO DELITO_ESPECIFICO ON (DELITO.ID_N_DELITO=DELITO_ESPECIFICO.ID_N_DELITO)
                                INNER JOIN SISMAEST.CFTM_DELITO_SUBGENERICO DELITO_SUBGENERICO ON (DELITO_ESPECIFICO.ID_N_SUBGENERICO=DELITO_SUBGENERICO.ID_N_SUBGENERICO)
                                ) DELITOS ON (
                                    DELITOS.ID_N_DELITO=DELITO_DENUNCIA.ID_N_DELITO AND
                                    DELITOS.ID_N_ESPECIFICO=DELITO_DENUNCIA.ID_N_ESPECIFICO AND 
                                    DELITOS.ID_N_SUBGENERICO=DELITO_DENUNCIA.ID_N_SUBGENERICO
                                )       
                                WHERE DELITO_DENUNCIA.ID_V_DENUNCIA=caso.ID_V_DENUNCIA) delitosDenuncia
                ,(select LISTAGG(DE_V_NOTA_CLASIFICACION, '|| ')  from
                                                        SISEFE.EFTD_NOTA_CLASIFICACION 
                                                         WHERE  ES_C_NOTA_CLASIFICACION=1   AND  ID_V_CASO=caso.ID_V_CASO
                                                        ) notaClasificaion 

				from SISEFE.EFTV_CASO caso
                     inner join  SISMPA.MPTV_DENUNCIA MPTV_DENUNCIA on (MPTV_DENUNCIA.ID_V_DENUNCIA = caso.ID_V_DENUNCIA and MPTV_DENUNCIA.id_n_estado_registro = 364)
                     inner join  SISMAEST.CFTM_TIPO_ORIGEN origen on (origen.id_n_tipo_origen=caso.iD_N_TIPO_ORIGEN)
                     left join  SISCFE.CFTV_SUJETO_CASO sujeto on (sujeto.ID_V_SUJETO_CASO  =caso.ID_V_REMITENTE)
                     left join SISMPA.MPTV_DENUNCIA_REMITENTE remi on (remi.ID_V_DENUNCIA_REMITENTE =caso.ID_V_REMITENTE)
                     left  join SISCFE.CFVM_PERSONA persona ON (persona.ID_V_PERSONA=remi.ID_V_PERSONA)
					 left join  SISMAEST.CFTM_TIPO_SUJETO TIPO_SUJETO on  ( TIPO_SUJETO.ID_N_TIPO_SUJETO  =remi.ID_N_TIPO_SUJETO)
                     left join SISEFE.EFVX_PLAZOS_SEMAFORO semaforo on (semaforo.ID_V_CASO=caso.ID_V_CASO)
				WHERE caso.FL_C_ASIGNADO='0' and caso.ID_V_FISCAL_ASIGNADO is null and caso.FE_D_ULT_ASIGNACION is null
					and (caso.FL_C_ANULADO IS NULL OR caso.FL_C_ANULADO='0') 
  					AND caso.CO_V_DESPACHO =P_V_DEPENDENCIA;
                    
               
        PO_V_ERR_COD := '0';
        PO_V_ERR_MSG := 'LOS DATOS FUERON OBTENIDOS EXITOSAMENTE ' ;
    EXCEPTION
        WHEN OTHERS
        THEN
            PO_V_ERR_COD := '1';
            PO_V_ERR_MSG := 'ERROR EN LISTADO: ' || TO_CHAR (SQLCODE) || ' - ' || SQLERRM;
    END EFSP_LISTAR_ASIGNACION_CASOS;


END EFPK_ASIGNACION_CONSULTAS;
/
