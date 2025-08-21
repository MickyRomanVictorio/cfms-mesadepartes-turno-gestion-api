CREATE OR REPLACE PACKAGE SISMPA.MPPK_CASO_TURNO AS

    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TUNO.MPSP_CREAR_CASO_DENUNCIA
    * Autor         : Manuel Cruz Daza
    * Versi?n       : 1.0
    * Descripci?n   : Crear el caso asociado a la denuncia
    * Fecha creaci?n: 30.11.2023
    ***********************************************************************************************/
   PROCEDURE MPSP_CREAR_CASO_DENUNCIA(
        PI_V_CO_DPND_FISCAL 					IN VARCHAR2,
        PI_V_CO_DESPACHO 						IN SISEFE.EFTV_CASO.CO_V_DESPACHO%TYPE,
        PI_V_ID_DENUNCIA 						IN VARCHAR2,
        PI_N_ID_DPND_POLICIAL 					IN NUMBER,
        PI_V_ID_DENUNCIA_REMITENTE 				IN VARCHAR2,
        PI_V_ID_ESPECIALIDAD 					IN SISEFE.EFTV_CASO.ID_V_ESPECIALIDAD%TYPE,
        PI_N_ID_TIPO_SUJETO 					IN NUMBER,
        PI_V_USUARIO 							IN VARCHAR2,
        PO_V_ID_CASO 							OUT VARCHAR2,
        PO_V_CO_CASO 							OUT VARCHAR2,
        PO_V_ERR_COD 							OUT VARCHAR2,
        PO_V_ERR_MSG 							OUT VARCHAR2
    );

    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_BUSCAR_DELITOS
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Registrar los hechos
    * Fecha creaci?n: 06.12.2023
    ***********************************************************************************************/
    PROCEDURE MPSP_BUSCAR_DELITOS(PI_DELITO IN VARCHAR2,
                                  PI_N_PAGINA IN NUMBER,
                                  PI_N_POR_PAGINA IN NUMBER,
                                  PO_N_TOTAL_PAGINAS OUT NUMBER,
                                  PO_N_TOTAL_REGISTROS OUT NUMBER,
                                  PO_CR_DELITOS OUT SYS_REFCURSOR,
                                  PO_V_ERR_COD OUT VARCHAR2,
                                  PO_V_ERR_MSG OUT VARCHAR2
    );

    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_LISTAR_TIPOS_GRADO_DELITO
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Registrar los hechos
    * Fecha creaci?n: 06.12.2023
    ***********************************************************************************************/
    PROCEDURE MPSP_LISTAR_TIPOS_GRADO_DELITO(PO_CR_TIPOS_GRADO_DELITO OUT SYS_REFCURSOR,
                                             PO_V_ERR_COD OUT VARCHAR2,
                                             PO_V_ERR_MSG OUT VARCHAR2
    );

    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_REGISTRAR_DELITO
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Registrar los hechos
    * Fecha creaci?n: 07.12.2023
    ***********************************************************************************************/
    PROCEDURE MPSP_REGISTRAR_DELITO(PI_ID_V_DENUNCIA IN VARCHAR2,
                                    PI_ID_N_DELITO IN NUMBER,
                                    PI_ID_N_SUBGENERICO IN NUMBER,
                                    PI_ID_N_ESPECIFICO IN NUMBER,
                                    PI_ID_N_TIPO_GRADO_DELITO IN NUMBER,
                                    PI_CO_V_US_CREACION IN VARCHAR2,
                                    PI_ID_N_ORIGEN_TURNO IN NUMBER,
                                    PO_V_ERR_COD OUT VARCHAR2,
                                    PO_V_ERR_MSG OUT VARCHAR2);

    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_REGISTRAR_DETENIDO_DELITO
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Registrar los hechos
    * Fecha creaci?n: 06.12.2023
    ***********************************************************************************************/
    PROCEDURE MPSP_REGISTRAR_DETENIDO_DELITO(PI_ID_V_SUJETO_CASO IN NUMBER,
                                             PI_ID_N_DELITO IN NUMBER,
                                             PI_ID_N_SUBGENERICO IN VARCHAR2,
                                             PI_ID_N_ESPECIFICO IN VARCHAR2,
                                             PI_ID_N_TIPO_GRADO_DELITO IN NUMBER,
                                             PI_ID_N_ORIGEN_TURNO IN NUMBER,
                                             PI_CO_V_US_CREACION IN VARCHAR2,
                                             PO_V_ERR_COD OUT VARCHAR2,
                                             PO_V_ERR_MSG OUT VARCHAR2);

    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_VALIDAR_DUPLICIDAD
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Registrar los hechos
    * Fecha creaci?n: 06.12.2023
    ***********************************************************************************************/
    PROCEDURE MPSP_VALIDAR_DUPLICIDAD(PI_JSON_DELITOS IN CLOB DEFAULT NULL,
                                      PI_JSON_DENUNCIANTES IN CLOB DEFAULT NULL,
                                      PI_JSON_AGRAVIADOS IN CLOB DEFAULT NULL,
                                      PI_JSON_DENUNCIADOS IN CLOB DEFAULT NULL,
                                      PI_JSON_IMPUTADOS IN CLOB DEFAULT NULL,
                                      PO_CR_ID_CASOS OUT SYS_REFCURSOR,
                                      PO_V_ERR_COD OUT VARCHAR2,
                                      PO_V_ERR_MSG OUT VARCHAR2);

    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_REGISTRAR_DELITO
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Listar Oficinas Renadespple
    * Fecha creaci?n: 18.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_LISTAR_OFICINAS_RENADESPPLE(PO_CR_OFICINAS_RENADESPPLE OUT SYS_REFCURSOR,
                                               PO_V_ERR_COD OUT VARCHAR2,
                                               PO_V_ERR_MSG OUT VARCHAR2);

    /**********************************************************************************************
    * Nombre        : SISMPA.MPSP_REGISTRAR_ACTUALIZAR_FICHA_RENADESPPLE
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Registrar/Actualizar Ficha Renadespple
    * Fecha creaci?n: 22.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_REGISTRAR_ACTUALIZAR_FICHA_RENADESPPLE(PI_ID_V_FICHA_RENADESPPLE IN VARCHAR2,
                                                          PI_ID_V_CASO IN VARCHAR2,
                                                          PI_ID_V_SUJETO_CASO IN VARCHAR2,
                                                          PI_ID_N_OFICINA IN VARCHAR2,
                                                          PI_FL_C_AFROPERUANO IN CHAR,
                                                          PI_FL_C_DISCAPACIDAD IN CHAR,
                                                          PI_FL_C_PRIV_LIBERTAD IN CHAR,
                                                          PI_FL_C_VIH_TBC IN CHAR,
                                                          PI_FL_C_TRAB_HOGAR IN CHAR,
                                                          PI_FL_C_LGTBI IN CHAR,
                                                          PI_FL_C_MIGRANTE IN CHAR,
                                                          PI_FL_C_VICTIM_VIOLE_8020 IN CHAR,
                                                          PI_FL_C_DEFENSOR IN CHAR,
                                                          PI_FL_C_FUNC_PUBLICO IN CHAR,
                                                          PI_ID_N_TIPO_PUEBLO IN NUMBER,
                                                          PI_ID_N_TIPO_LENGUA IN NUMBER,
                                                          PI_FL_C_AVANCE IN CHAR,
                                                          PI_N_STEP IN NUMBER,
                                                          PI_N_OPERACION IN NUMBER,
                                                          PI_FL_C_TENTATIVA IN CHAR,
                                                          PI_ID_N_MOTIVO_DETENCION IN NUMBER,
                                                          PI_NU_V_INFORME_POL IN VARCHAR2,
                                                          PI_FE_D_DENUNCIA_POLI IN VARCHAR2,
                                                          PI_ID_N_SITUACION_JURIDICA IN NUMBER,
                                                          PI_FE_D_LIBERTAD IN VARCHAR2,
                                                          PI_V_ALIAS IN VARCHAR2,
                                                          PO_V_ERR_COD OUT VARCHAR2,
                                                          PO_V_ERR_MSG OUT VARCHAR2);

    /**********************************************************************************************
    * Nombre        : SISMPA.MPSP_LISTAR_DIRECCIONES_INFO
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Registrar/Actualizar Ficha Renadespple
    * Fecha creaci?n: 22.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_LISTAR_DIRECCIONES_INFO(PI_ID_V_SUJETO_CASO IN VARCHAR2,
                                           PO_CR_DIRECCIONES OUT SYS_REFCURSOR,
                                           PO_V_ERR_COD OUT VARCHAR2,
                                           PO_V_ERR_MSG OUT VARCHAR2);

    /**********************************************************************************************
    * Nombre        : SISMPA.MPSP_ELIMINAR_DIRECCION_INFO
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Eliminar direccion
    * Fecha creaci?n: 23.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_ELIMINAR_DIRECCION_INFO(PI_ID_V_DIRECCION IN VARCHAR2,
                                           PO_V_ERR_COD OUT VARCHAR2,
                                           PO_V_ERR_MSG OUT VARCHAR2);

    /**********************************************************************************************
    * Nombre        : SISMPA.MPSP_OPERACION_DIRECCIONES_INFO
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Registrar direccion
    * Fecha creaci?n: 23.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_REGISTRAR_DIRECCION_INFO(PI_ID_V_DIRECCION IN VARCHAR2,
                                            PI_ID_V_SUJETO_CASO IN VARCHAR2,
                                            PI_ID_N_TIPO_DOMICILIO IN NUMBER,
                                            PI_ID_N_PAIS IN NUMBER,
                                            PI_ID_V_UBIGEO IN VARCHAR2,
                                            PI_ID_N_TIPO_VIA IN NUMBER,
                                            PI_DI_V_RESIDENCIA IN VARCHAR2,
                                            PI_NU_N_RESIDENCIA IN NUMBER,
                                            PI_ID_N_TIPO_PREF_URB IN NUMBER,
                                            PI_DE_V_URBANIZACION IN VARCHAR2,
                                            PI_DE_V_BLOCK_DIRECCION IN VARCHAR2,
                                            PI_DE_V_INTERIOR IN VARCHAR2,
                                            PI_DE_V_ETAPA IN VARCHAR2,
                                            PI_DE_V_MANZANA IN VARCHAR2,
                                            PI_DE_V_LOTE IN VARCHAR2,
                                            PI_DE_V_REFERENCIA IN VARCHAR2,
                                            PO_V_ERR_COD OUT VARCHAR2,
                                            PO_V_ERR_MSG OUT VARCHAR2);


    /**********************************************************************************************
    * Nombre        : SISMPA.MPSP_OPERACION_DIRECCIONES_INFO
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Actualizar direccion
    * Fecha creaci?n: 23.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_ACTUALIZAR_DIRECCION_INFO(PI_ID_V_DIRECCION IN VARCHAR2,
                                             PI_ID_V_SUJETO_CASO IN VARCHAR2,
                                             PI_ID_N_TIPO_DOMICILIO IN NUMBER,
                                             PI_ID_N_PAIS IN NUMBER,
                                             PI_ID_V_UBIGEO IN VARCHAR2,
                                             PI_ID_N_TIPO_VIA IN NUMBER,
                                             PI_DI_V_RESIDENCIA IN VARCHAR2,
                                             PI_NU_N_RESIDENCIA IN NUMBER,
                                             PI_ID_N_TIPO_PREF_URB IN NUMBER,
                                             PI_DE_V_URBANIZACION IN VARCHAR2,
                                             PI_DE_V_BLOCK_DIRECCION IN VARCHAR2,
                                             PI_DE_V_INTERIOR IN VARCHAR2,
                                             PI_DE_V_ETAPA IN VARCHAR2,
                                             PI_DE_V_MANZANA IN VARCHAR2,
                                             PI_DE_V_LOTE IN VARCHAR2,
                                             PI_DE_V_REFERENCIA IN VARCHAR2,
                                             PO_V_ERR_COD OUT VARCHAR2,
                                             PO_V_ERR_MSG OUT VARCHAR2);

    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_LISTAR_MOTIVOS_DETENCION
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Listar Motivos Detencion
    * Fecha creaci?n: 23.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_LISTAR_MOTIVOS_DETENCION(PO_CR_MOTIVOS_DETENCION OUT SYS_REFCURSOR,
                                            PO_V_ERR_COD OUT VARCHAR2,
                                            PO_V_ERR_MSG OUT VARCHAR2);

    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_OBTENER_MOMENTO_DETENCION
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Obtener Motivos Detencion
    * Fecha creaci?n: 23.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_OBTENER_MOMENTO_DETENCION(PI_ID_V_CASO IN VARCHAR2,
                                             PO_CR_MOMENTO_DETENCION OUT SYS_REFCURSOR,
                                             PO_V_ERR_COD OUT VARCHAR2,
                                             PO_V_ERR_MSG OUT VARCHAR2);

    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_OBTENER_FICHA_RENADESPPLE
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Obtener Ficha Renadespple
    * Fecha creaci?n: 23.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_OBTENER_FICHA_RENADESPPLE(PI_ID_V_CASO IN VARCHAR2,
                                             PI_ID_V_SUJETO_CASO IN VARCHAR2,
                                             PO_CR_FICHAS_RENADESPPLE OUT SYS_REFCURSOR,
                                             PO_V_ERR_COD OUT VARCHAR2,
                                             PO_V_ERR_MSG OUT VARCHAR2);

    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_LISTAR_SITUACIONES_JURIDICAS
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Listar Situaciones Juridicas
    * Fecha creaci?n: 23.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_LISTAR_SITUACIONES_JURIDICAS(PO_CR_SITUACIONES_JURIDICAS OUT SYS_REFCURSOR,
                                                PO_V_ERR_COD OUT VARCHAR2,
                                                PO_V_ERR_MSG OUT VARCHAR2);


    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_OBTENER_INVESTIGACION_POLICIAL
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Obtener Investigacion Policial
    * Fecha creaci?n: 23.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_OBTENER_INVESTIGACION_POLICIAL(PI_ID_V_SUJETO_CASO IN VARCHAR2,
                                                  PO_CR_INVESTIGACION_POLICIAL OUT SYS_REFCURSOR,
                                                  PO_V_ERR_COD OUT VARCHAR2,
                                                  PO_V_ERR_MSG OUT VARCHAR2);

    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_CONSULTAR_PERSONA
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Obtener Investigacion Policial
    * Fecha creaci?n: 23.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_CONSULTAR_PERSONA(PI_ID_V_SUJETO_CASO IN VARCHAR2,
                                     PO_CR_PERSONA OUT SYS_REFCURSOR,
                                     PO_V_ERR_COD OUT VARCHAR2,
                                     PO_V_ERR_MSG OUT VARCHAR2);
END MPPK_CASO_TURNO;

CREATE OR REPLACE PACKAGE BODY SISMPA.MPPK_CASO_TURNO AS

    V_V_COD_OK VARCHAR2(1) := '0';
    V_V_MSG_OK VARCHAR2(100) := 'LA OPERACION SE REALIZ? SATISFACTORIAMENTE';

/**********************************************************************************************
* Nombre        : SISMPA.MPPK_CASO_TUNO.MPSP_CREAR_CASO_DENUNCIA
* Autor         : Manuel Cruz Daza
* Versi?n       : 1.0
* Descripci?n   : Crear el caso asociado a la denuncia
* Fecha creaci?n: 30.11.2023
***********************************************************************************************/
    PROCEDURE MPSP_CREAR_CASO_DENUNCIA(
        --PI_N_ID_DPND_FISCAL 					IN NUMBER,
        PI_V_CO_DPND_FISCAL 					IN VARCHAR2,
        --PI_N_ID_DESPACHO 						IN SISEFE.EFTV_CASO.ID_N_DESPACHO%TYPE,
        PI_V_CO_DESPACHO 						IN SISEFE.EFTV_CASO.CO_V_DESPACHO%TYPE,
        PI_V_ID_DENUNCIA 						IN VARCHAR2,
        PI_N_ID_DPND_POLICIAL 					IN NUMBER,
        PI_V_ID_DENUNCIA_REMITENTE 				IN VARCHAR2,
        PI_V_ID_ESPECIALIDAD 					IN SISEFE.EFTV_CASO.ID_V_ESPECIALIDAD%TYPE,
        PI_N_ID_TIPO_SUJETO 					IN NUMBER,
        PI_V_USUARIO 							IN VARCHAR2,
        PO_V_ID_CASO 							OUT VARCHAR2,
        PO_V_CO_CASO 							OUT VARCHAR2,
        PO_V_ERR_COD 							OUT VARCHAR2,
        PO_V_ERR_MSG 							OUT VARCHAR2
    ) AS
        V_N_ID_DPND_FISCAL NUMBER;
        V_N_MAX_CASO       SISEFE.EFTV_CASO.NU_N_CASO%TYPE;
        V_N_CUADERNO       SISEFE.EFTV_CASO.NU_N_SEC%TYPE := 0;
        V_N_ANIO           SISEFE.EFTV_CASO.NU_N_ANIO%TYPE;
        V_V_ID_MOVIMIENTO  SISEFE.EFTV_MOVIMIENTO.ID_V_MOVIMIENTO%TYPE;
BEGIN

        V_N_ANIO := EXTRACT(YEAR FROM SYSDATE);

SELECT NVL(MAX(NU_N_CASO), 0) + 1
INTO V_N_MAX_CASO
FROM SISEFE.EFTV_CASO
WHERE CO_V_ENTIDAD = PI_V_CO_DPND_FISCAL;
PO_V_CO_CASO := PI_V_CO_DPND_FISCAL || '-' || V_N_ANIO || '-' || V_N_MAX_CASO || '-' || V_N_CUADERNO;


INSERT INTO SISEFE.EFTV_CASO (CO_V_CASO,
                              NU_N_ANIO,
                              NU_N_CASO,
                              NU_N_SEC,
                              ID_N_DPND_POLICIAL,
                              ID_V_DENUNCIA,
                              FE_D_INGRESO_COMPUTABLE,
                              CO_V_ENTIDAD,
                              ID_N_TIPO_SUJETO,
                              ID_V_REMITENTE,
                              ES_C_REGISTRO,
    --ID_N_DESPACHO,
                              CO_V_DESPACHO,
                              ID_N_TIPO_ORIGEN,
                              ID_N_TIPO_COMPLEJIDAD, -- 2- SIMPLE
                              ID_V_ESPECIALIDAD,
                              ID_V_PROCESO_ETAPA,
                              ID_V_SUBTIPO_PROCESO,
                              ID_V_ETAPA,
                              ID_N_CLASIFICADOR_EXPEDIENTE,
                              ID_V_TIPO_CLASIFICADOR_EXPEDIENTE,
                              ID_N_ACCION_ESTADO

)
VALUES (PO_V_CO_CASO,
        V_N_ANIO,
        V_N_MAX_CASO,
        V_N_CUADERNO,
        PI_N_ID_DPND_POLICIAL,
        PI_V_ID_DENUNCIA,
        SYSDATE,
        PI_V_CO_DPND_FISCAL,
        PI_N_ID_TIPO_SUJETO,
        PI_V_ID_DENUNCIA_REMITENTE,
        1,
           --PI_N_ID_DESPACHO,
        PI_V_CO_DESPACHO,
        4, -- TURNO
        2, -- simple,
        PI_V_ID_ESPECIALIDAD,
        '10101',
        '01',
        '01',
        0,
        '000', --PRINCIPAL,
        3 -- REGISTRAR DENUNCIA POR MESA DE TURNO
       )
    RETURNING ID_V_CASO INTO PO_V_ID_CASO;
--TURNO

--- MOVIMIENTO

INSERT INTO SISEFE.EFTV_MOVIMIENTO(ID_V_CASO,
                                   ID_N_ACCION_ESTADO,
                                   ES_C_MOVIMIENTO)
VALUES (PO_V_ID_CASO,
        3, -- ACCI?N TURNO
        '1')
    RETURNING ID_V_MOVIMIENTO INTO V_V_ID_MOVIMIENTO;

INSERT INTO SISEFE.EFTV_MOVIMIENTO_DETALLE(ID_V_MOVIMIENTO,
                                           CO_V_US_CREACION)
VALUES (V_V_ID_MOVIMIENTO,
        PI_V_USUARIO);

PO_V_ERR_COD := '0';
        PO_V_ERR_MSG := '';

EXCEPTION
        WHEN OTHERS THEN
            PO_V_ERR_COD := '1';
            PO_V_ERR_MSG := 'ERROR AL INTENTAR CREAR EL CASO: ' || SQLCODE || ' - ' || SQLERRM;

END MPSP_CREAR_CASO_DENUNCIA;

    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_BUSCAR_DELITOS
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Registrar los hechos
    * Fecha creaci?n: 06.12.2023
    ***********************************************************************************************/

    PROCEDURE MPSP_BUSCAR_DELITOS(PI_DELITO IN VARCHAR2,
                                  PI_N_PAGINA IN NUMBER,
                                  PI_N_POR_PAGINA IN NUMBER,
                                  PO_N_TOTAL_PAGINAS OUT NUMBER,
                                  PO_N_TOTAL_REGISTROS OUT NUMBER,
                                  PO_CR_DELITOS OUT SYS_REFCURSOR,
                                  PO_V_ERR_COD OUT VARCHAR2,
                                  PO_V_ERR_MSG OUT VARCHAR2
    ) IS
        EX_CONTROLADO EXCEPTION;
BEGIN

SELECT COUNT(*)
INTO PO_N_TOTAL_REGISTROS
FROM (SELECT     D.NO_V_DELITO                                                        AS DELITO_GENERICO,
                 S.NO_V_SUBGENERICO                                                   AS DELITO_SUBGENERICO,
                 E.NO_V_ESPECIFICO                                                    AS DELITO_ESPECIFICO,
                 D.ID_N_DELITO                                                        AS DELITO_GENERICO_ID,
                 S.ID_N_SUBGENERICO                                                   AS DELITO_SUBGENERICO_ID,
                 E.ID_N_ESPECIFICO                                                    AS DELITO_ESPECIFICO_ID
      FROM SISMAEST.CFTM_DELITO_ESPECIFICO E
               JOIN SISMAEST.CFTM_DELITO D ON D.ID_N_DELITO = E.ID_N_DELITO
               JOIN SISMAEST.CFTM_DELITO_SUBGENERICO S ON E.ID_N_SUBGENERICO = S.ID_N_SUBGENERICO
          AND S.ID_N_DELITO = D.ID_N_DELITO
      WHERE D.NO_V_DELITO LIKE '%' || PI_DELITO || '%'
         OR S.NO_V_SUBGENERICO LIKE '%' || PI_DELITO || '%'
         OR E.NO_V_ESPECIFICO LIKE '%' || PI_DELITO || '%'
          FETCH FIRST 50 ROWS ONLY
     ) X;

IF PO_N_TOTAL_REGISTROS = 0 THEN
            PO_V_ERR_COD := '422002';
            PO_V_ERR_MSG := 'MPSP_BUSCAR_DELITOS - No se encontraron registros.';
            RAISE EX_CONTROLADO;
END IF;

OPEN PO_CR_DELITOS FOR
SELECT ROWNUM AS ID,
       X.ARTICULO,
       X.DELITO_GENERICO,
       X.DELITO_SUBGENERICO,
       X.DELITO_ESPECIFICO,
       X.DELITO_GENERICO_ID,
       X.DELITO_SUBGENERICO_ID,
       X.DELITO_ESPECIFICO_ID
FROM (SELECT E.NU_V_ARTICULO || ' - ' || E.NU_V_INCISO || ' - ' || E.NU_V_PARRAFO AS ARTICULO,
             D.NO_V_DELITO                                                        AS DELITO_GENERICO,
             S.NO_V_SUBGENERICO                                                   AS DELITO_SUBGENERICO,
             E.NO_V_ESPECIFICO                                                    AS DELITO_ESPECIFICO,
             D.ID_N_DELITO                                                        AS DELITO_GENERICO_ID,
             S.ID_N_SUBGENERICO                                                   AS DELITO_SUBGENERICO_ID,
             E.ID_N_ESPECIFICO                                                    AS DELITO_ESPECIFICO_ID
      FROM SISMAEST.CFTM_DELITO_ESPECIFICO E
               JOIN SISMAEST.CFTM_DELITO D ON D.ID_N_DELITO = E.ID_N_DELITO
               JOIN SISMAEST.CFTM_DELITO_SUBGENERICO S ON E.ID_N_SUBGENERICO = S.ID_N_SUBGENERICO
          AND S.ID_N_DELITO = D.ID_N_DELITO
      WHERE D.NO_V_DELITO LIKE '%' || PI_DELITO || '%'
         OR S.NO_V_SUBGENERICO LIKE '%' || PI_DELITO || '%'
         OR E.NO_V_ESPECIFICO LIKE '%' || PI_DELITO || '%'
      ORDER BY 2, 3, 4 ASC
          FETCH FIRST 50 ROWS ONLY
     ) X
OFFSET(PI_N_PAGINA - 1) * PI_N_POR_PAGINA ROWS FETCH NEXT PI_N_POR_PAGINA ROWS ONLY;

PO_N_TOTAL_PAGINAS := CEIL(PO_N_TOTAL_REGISTROS / PI_N_POR_PAGINA);

        PO_V_ERR_COD := V_V_COD_OK;
        PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
        WHEN
            EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : ' ||
                                 PO_V_ERR_COD || ' - ' ||
                                 PO_V_ERR_MSG);
WHEN
            OTHERS THEN
            PO_V_ERR_COD := '1';
            PO_V_ERR_MSG := 'MPSP_BUSCAR_DELITOS - ERROR : ' || SQLERRM;

END MPSP_BUSCAR_DELITOS;

    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_LISTAR_TIPOS_GRADO_DELITO
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Registrar los hechos
    * Fecha creaci?n: 06.12.2023
    ***********************************************************************************************/
    PROCEDURE MPSP_LISTAR_TIPOS_GRADO_DELITO(PO_CR_TIPOS_GRADO_DELITO OUT SYS_REFCURSOR,
                                             PO_V_ERR_COD OUT VARCHAR2,
                                             PO_V_ERR_MSG OUT VARCHAR2
    ) IS
        EX_CONTROLADO EXCEPTION;
BEGIN

OPEN PO_CR_TIPOS_GRADO_DELITO FOR
SELECT ID_N_TIPO_GRADO_DELITO AS ID,
       NO_V_GRADO_DELITO      AS VALOR
FROM SISMAEST.CFTM_TIPO_GRADO_DELITO;

PO_V_ERR_COD := V_V_COD_OK;
        PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
        WHEN
            EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : ' ||
                                 PO_V_ERR_COD || ' - ' ||
                                 PO_V_ERR_MSG);
WHEN
            OTHERS THEN
            PO_V_ERR_COD := '1';
            PO_V_ERR_MSG := 'MPSP_LISTAR_TIPOS_GRADO_DELITO - ERROR : ' || SQLERRM;

END MPSP_LISTAR_TIPOS_GRADO_DELITO;

    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_REGISTRAR_DELITO
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Registrar los hechos
    * Fecha creaci?n: 07.12.2023
    ***********************************************************************************************/
    PROCEDURE MPSP_REGISTRAR_DELITO(PI_ID_V_DENUNCIA IN VARCHAR2,
                                    PI_ID_N_DELITO IN NUMBER,
                                    PI_ID_N_SUBGENERICO IN NUMBER,
                                    PI_ID_N_ESPECIFICO IN NUMBER,
                                    PI_ID_N_TIPO_GRADO_DELITO IN NUMBER,
                                    PI_CO_V_US_CREACION IN VARCHAR2,
                                    PI_ID_N_ORIGEN_TURNO IN NUMBER,
                                    PO_V_ERR_COD OUT VARCHAR2,
                                    PO_V_ERR_MSG OUT VARCHAR2)
IS
        EX_CONTROLADO EXCEPTION;
BEGIN

        PO_V_ERR_COD := V_V_COD_OK;
        PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
        WHEN
            EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : ' ||
                                 PO_V_ERR_COD || ' - ' ||
                                 PO_V_ERR_MSG);
WHEN
            OTHERS THEN
            PO_V_ERR_COD := '1';
            PO_V_ERR_MSG := 'MPSP_REGISTRAR_DELITO - ERROR : ' || SQLERRM;

END MPSP_REGISTRAR_DELITO;

    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_REGISTRAR_DETENIDO_DELITO
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Registrar los hechos
    * Fecha creaci?n: 06.12.2023
    ***********************************************************************************************/
    PROCEDURE MPSP_REGISTRAR_DETENIDO_DELITO(PI_ID_V_SUJETO_CASO IN NUMBER,
                                             PI_ID_N_DELITO IN NUMBER,
                                             PI_ID_N_SUBGENERICO IN VARCHAR2,
                                             PI_ID_N_ESPECIFICO IN VARCHAR2,
                                             PI_ID_N_TIPO_GRADO_DELITO IN NUMBER,
                                             PI_ID_N_ORIGEN_TURNO IN NUMBER,
                                             PI_CO_V_US_CREACION IN VARCHAR2,
                                             PO_V_ERR_COD OUT VARCHAR2,
                                             PO_V_ERR_MSG OUT VARCHAR2)
IS
        V_ID_V_DELITO_SUJETO VARCHAR2(40) := NULL;
        EX_CONTROLADO EXCEPTION;
BEGIN

INSERT INTO SISEFE.EFTV_DELITO_SUJETO(ID_V_SUJETO_CASO,
                                      ID_N_DELITO,
                                      ID_N_SUBGENERICO,
                                      ID_N_ESPECIFICO,
                                      FE_D_CREACION)
VALUES (PI_ID_V_SUJETO_CASO,
        PI_ID_N_DELITO,
        PI_ID_N_SUBGENERICO,
        PI_ID_N_ESPECIFICO,
        DEFAULT)
    RETURNING ID_V_DELITO_SUJETO INTO V_ID_V_DELITO_SUJETO;

INSERT INTO SISCFE.CFTV_DELITO_DETENIDO(ID_V_DELITO_SUJETO,
                                        ID_N_TIPO_GRADO_DELITO,
                                        ID_N_ORIGEN_TURNO,
                                        CO_V_US_CREACION,
                                        FE_D_CREACION)
VALUES (V_ID_V_DELITO_SUJETO,
        PI_ID_N_TIPO_GRADO_DELITO,
        PI_ID_N_ORIGEN_TURNO,
        PI_CO_V_US_CREACION,
        DEFAULT);

PO_V_ERR_COD := V_V_COD_OK;
        PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
        WHEN
            EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : ' ||
                                 PO_V_ERR_COD || ' - ' ||
                                 PO_V_ERR_MSG);
WHEN
            OTHERS THEN
            PO_V_ERR_COD := '1';
            PO_V_ERR_MSG := 'MPSP_REGISTRAR_DETENIDO_DELITO - ERROR : ' || SQLERRM;

END MPSP_REGISTRAR_DETENIDO_DELITO;

    PROCEDURE MPSP_VALIDAR_DUPLICIDAD(PI_JSON_DELITOS IN CLOB DEFAULT NULL,
                                      PI_JSON_DENUNCIANTES IN CLOB DEFAULT NULL,
                                      PI_JSON_AGRAVIADOS IN CLOB DEFAULT NULL,
                                      PI_JSON_DENUNCIADOS IN CLOB DEFAULT NULL,
                                      PI_JSON_IMPUTADOS IN CLOB DEFAULT NULL,
                                      PO_CR_ID_CASOS OUT SYS_REFCURSOR,
                                      PO_V_ERR_COD OUT VARCHAR2,
                                      PO_V_ERR_MSG OUT VARCHAR2)
IS
        EX_CONTROLADO EXCEPTION;
        TOTAL_REGISTROS NUMBER;
BEGIN

SELECT COUNT(SC.ID_V_CASO)
INTO TOTAL_REGISTROS
FROM SISCFE.CFTV_SUJETO_CASO SC
WHERE SC.ID_V_CASO IN (SELECT SC.ID_V_CASO
                       FROM SISCFE.CFTV_SUJETO_CASO SC
                                INNER JOIN SISEFE.EFTV_DELITO_SUJETO DS
                                           ON SC.ID_V_SUJETO_CASO = DS.ID_V_SUJETO_CASO
                                CROSS JOIN JSON_TABLE(PI_JSON_DELITOS, '$[*]' COLUMNS (
                                   delitoGenerico VARCHAR2(100) PATH '$.delitoGenerico',
                                   idDelitoGenerico NUMBER PATH '$.idDelitoGenerico',
                                   delitoSubGenerico VARCHAR2(100) PATH '$.delitoSubGenerico',
                                   idDelitoSubGenerico VARCHAR2(20) PATH '$.idDelitoSubGenerico',
                                   delitoEspecifico NUMBER PATH '$.delitoEspecifico',
                                   idDelitoEspecifico VARCHAR2(100) PATH '$.idDelitoEspecifico'
                                   )) J
                       WHERE DS.ID_N_DELITO = J.idDelitoGenerico
                       GROUP BY SC.ID_V_CASO)
  -- DENUNCIANTES
  AND SC.ID_V_CASO IN (SELECT SC.ID_V_CASO
                       FROM SISCFE.CFTV_SUJETO_CASO SC
                                INNER JOIN SISCFE.CFTV_PERSONA P ON SC.ID_V_PERSONA = P.ID_V_PERSONA
                                INNER JOIN SISEFE.EFTV_DELITO_SUJETO DS
                                           ON SC.ID_V_SUJETO_CASO = DS.ID_V_SUJETO_CASO
                                CROSS JOIN JSON_TABLE(PI_JSON_DENUNCIANTES, '$[*]' COLUMNS (
                                   nombreCompleto VARCHAR2(100) PATH '$.nombreCompleto',
                                   idTipoDocumento NUMBER PATH '$.idTipoDocumento',
                                   tipoDocumento VARCHAR2(100) PATH '$.tipoDocumento',
                                   numeroDocumento VARCHAR2(20) PATH '$.numeroDocumento',
                                   idTipoParte NUMBER PATH '$.idTipoParte',
                                   tipoParte VARCHAR2(100) PATH '$.tipoParte',
                                   idCondicion VARCHAR2(100) PATH '$.idCondicion',
                                   condicion VARCHAR2(100) PATH '$.condicion'
                                   )) J
                       WHERE P.NU_V_DOCUMENTO = J.numeroDocumento
                         AND SC.ID_N_TIPO_PARTE_SUJETO = J.idTipoParte
                       GROUP BY SC.ID_V_CASO)
  -- AGRAVIADOS
  AND SC.ID_V_CASO IN (SELECT SC.ID_V_CASO
                       FROM SISCFE.CFTV_SUJETO_CASO SC
                                INNER JOIN SISCFE.CFTV_PERSONA P ON SC.ID_V_PERSONA = P.ID_V_PERSONA
                                INNER JOIN SISEFE.EFTV_DELITO_SUJETO DS
                                           ON SC.ID_V_SUJETO_CASO = DS.ID_V_SUJETO_CASO
                                CROSS JOIN JSON_TABLE(PI_JSON_AGRAVIADOS, '$[*]' COLUMNS (
                                   nombreCompleto VARCHAR2(100) PATH '$.nombreCompleto',
                                   idTipoDocumento NUMBER PATH '$.idTipoDocumento',
                                   tipoDocumento VARCHAR2(100) PATH '$.tipoDocumento',
                                   numeroDocumento VARCHAR2(20) PATH '$.numeroDocumento',
                                   idTipoParte NUMBER PATH '$.idTipoParte',
                                   tipoParte VARCHAR2(100) PATH '$.tipoParte',
                                   idCondicion VARCHAR2(100) PATH '$.idCondicion',
                                   condicion VARCHAR2(100) PATH '$.condicion'
                                   )) J
                       WHERE P.NU_V_DOCUMENTO = J.numeroDocumento
                         AND SC.ID_N_TIPO_PARTE_SUJETO = J.idTipoParte
                       GROUP BY SC.ID_V_CASO)
  -- DENUNCIADOS
  AND SC.ID_V_CASO IN (SELECT SC.ID_V_CASO
                       FROM SISCFE.CFTV_SUJETO_CASO SC
                                INNER JOIN SISCFE.CFTV_PERSONA P ON SC.ID_V_PERSONA = P.ID_V_PERSONA
                                INNER JOIN SISEFE.EFTV_DELITO_SUJETO DS
                                           ON SC.ID_V_SUJETO_CASO = DS.ID_V_SUJETO_CASO
                                INNER JOIN SISMPA.MPTV_SUJETO_CONDICION MSC
                                           ON SC.ID_V_SUJETO_CASO = MSC.ID_V_SUJETO_CASO
                                CROSS JOIN JSON_TABLE(PI_JSON_DENUNCIADOS, '$[*]' COLUMNS (
                                   nombreCompleto VARCHAR2(100) PATH '$.nombreCompleto',
                                   idTipoDocumento NUMBER PATH '$.idTipoDocumento',
                                   tipoDocumento VARCHAR2(100) PATH '$.tipoDocumento',
                                   numeroDocumento VARCHAR2(20) PATH '$.numeroDocumento',
                                   idTipoParte NUMBER PATH '$.idTipoParte',
                                   tipoParte VARCHAR2(100) PATH '$.tipoParte',
                                   idCondicion VARCHAR2(100) PATH '$.idCondicion',
                                   condicion VARCHAR2(100) PATH '$.condicion'
                                   )) J
                       WHERE P.NU_V_DOCUMENTO = J.numeroDocumento
                         AND SC.ID_N_TIPO_PARTE_SUJETO = J.idTipoParte
                         AND MSC.ID_V_SUJETO_CONDICION = J.idCondicion
                       GROUP BY SC.ID_V_CASO)
  -- IMPUTADOS
  AND SC.ID_V_CASO IN (SELECT SC.ID_V_CASO
                       FROM SISCFE.CFTV_SUJETO_CASO SC
                                INNER JOIN SISCFE.CFTV_PERSONA P ON SC.ID_V_PERSONA = P.ID_V_PERSONA
                                INNER JOIN SISEFE.EFTV_DELITO_SUJETO DS
                                           ON SC.ID_V_SUJETO_CASO = DS.ID_V_SUJETO_CASO
                                INNER JOIN SISMPA.MPTV_SUJETO_CONDICION MSC
                                           ON SC.ID_V_SUJETO_CASO = MSC.ID_V_SUJETO_CASO
                                CROSS JOIN JSON_TABLE(PI_JSON_IMPUTADOS, '$[*]' COLUMNS (
                                   nombreCompleto VARCHAR2(100) PATH '$.nombreCompleto',
                                   idTipoDocumento NUMBER PATH '$.idTipoDocumento',
                                   tipoDocumento VARCHAR2(100) PATH '$.tipoDocumento',
                                   numeroDocumento VARCHAR2(20) PATH '$.numeroDocumento',
                                   idTipoParte NUMBER PATH '$.idTipoParte',
                                   tipoParte VARCHAR2(100) PATH '$.tipoParte',
                                   idCondicion VARCHAR2(100) PATH '$.idCondicion',
                                   condicion VARCHAR2(100) PATH '$.condicion'
                                   )) J
                       WHERE P.NU_V_DOCUMENTO = J.numeroDocumento
                         AND SC.ID_N_TIPO_PARTE_SUJETO = J.idTipoParte
                         AND MSC.ID_V_SUJETO_CONDICION = J.idCondicion
                       GROUP BY SC.ID_V_CASO);

IF TOTAL_REGISTROS = 0 THEN
            PO_V_ERR_COD := '422002';
            PO_V_ERR_MSG := 'MPSP_VALIDAR_DUPLICIDAD - No se encontraron casos duplicados.';
            RAISE EX_CONTROLADO;
END IF;

OPEN PO_CR_ID_CASOS FOR
SELECT SC.ID_V_CASO AS idCaso, EC.CO_V_CASO AS numeroCaso
FROM SISCFE.CFTV_SUJETO_CASO SC
         INNER JOIN SISEFE.EFTV_CASO EC ON SC.ID_V_CASO = EC.ID_V_CASO
WHERE SC.ID_V_CASO IN (SELECT SC.ID_V_CASO
                       FROM SISCFE.CFTV_SUJETO_CASO SC
                                INNER JOIN SISEFE.EFTV_DELITO_SUJETO DS
                                           ON SC.ID_V_SUJETO_CASO = DS.ID_V_SUJETO_CASO
                                CROSS JOIN JSON_TABLE(PI_JSON_DELITOS, '$[*]' COLUMNS (
                                       delitoGenerico VARCHAR2(100) PATH '$.delitoGenerico',
                                       idDelitoGenerico NUMBER PATH '$.idDelitoGenerico',
                                       delitoSubGenerico VARCHAR2(100) PATH '$.delitoSubGenerico',
                                       idDelitoSubGenerico VARCHAR2(20) PATH '$.idDelitoSubGenerico',
                                       delitoEspecifico NUMBER PATH '$.delitoEspecifico',
                                       idDelitoEspecifico VARCHAR2(100) PATH '$.idDelitoEspecifico'
                                       )) J
                       WHERE DS.ID_N_DELITO = J.idDelitoGenerico
                       GROUP BY SC.ID_V_CASO)
  -- DENUNCIANTES
  AND SC.ID_V_CASO IN (SELECT SC.ID_V_CASO
                       FROM SISCFE.CFTV_SUJETO_CASO SC
                                INNER JOIN SISCFE.CFTV_PERSONA P ON SC.ID_V_PERSONA = P.ID_V_PERSONA
                                INNER JOIN SISEFE.EFTV_DELITO_SUJETO DS
                                           ON SC.ID_V_SUJETO_CASO = DS.ID_V_SUJETO_CASO
                                CROSS JOIN JSON_TABLE(PI_JSON_DENUNCIANTES, '$[*]' COLUMNS (
                                       nombreCompleto VARCHAR2(100) PATH '$.nombreCompleto',
                                       idTipoDocumento NUMBER PATH '$.idTipoDocumento',
                                       tipoDocumento VARCHAR2(100) PATH '$.tipoDocumento',
                                       numeroDocumento VARCHAR2(20) PATH '$.numeroDocumento',
                                       idTipoParte NUMBER PATH '$.idTipoParte',
                                       tipoParte VARCHAR2(100) PATH '$.tipoParte',
                                       idCondicion VARCHAR2(100) PATH '$.idCondicion',
                                       condicion VARCHAR2(100) PATH '$.condicion'
                                       )) J
                       WHERE P.NU_V_DOCUMENTO = J.numeroDocumento
                         AND SC.ID_N_TIPO_PARTE_SUJETO = J.idTipoParte
                       GROUP BY SC.ID_V_CASO)
  -- AGRAVIADOS
  AND SC.ID_V_CASO IN (SELECT SC.ID_V_CASO
                       FROM SISCFE.CFTV_SUJETO_CASO SC
                                INNER JOIN SISCFE.CFTV_PERSONA P ON SC.ID_V_PERSONA = P.ID_V_PERSONA
                                INNER JOIN SISEFE.EFTV_DELITO_SUJETO DS
                                           ON SC.ID_V_SUJETO_CASO = DS.ID_V_SUJETO_CASO
                                CROSS JOIN JSON_TABLE(PI_JSON_AGRAVIADOS, '$[*]' COLUMNS (
                                       nombreCompleto VARCHAR2(100) PATH '$.nombreCompleto',
                                       idTipoDocumento NUMBER PATH '$.idTipoDocumento',
                                       tipoDocumento VARCHAR2(100) PATH '$.tipoDocumento',
                                       numeroDocumento VARCHAR2(20) PATH '$.numeroDocumento',
                                       idTipoParte NUMBER PATH '$.idTipoParte',
                                       tipoParte VARCHAR2(100) PATH '$.tipoParte',
                                       idCondicion VARCHAR2(100) PATH '$.idCondicion',
                                       condicion VARCHAR2(100) PATH '$.condicion'
                                       )) J
                       WHERE P.NU_V_DOCUMENTO = J.numeroDocumento
                         AND SC.ID_N_TIPO_PARTE_SUJETO = J.idTipoParte
                       GROUP BY SC.ID_V_CASO)
  -- DENUNCIADOS
  AND SC.ID_V_CASO IN (SELECT SC.ID_V_CASO
                       FROM SISCFE.CFTV_SUJETO_CASO SC
                                INNER JOIN SISCFE.CFTV_PERSONA P ON SC.ID_V_PERSONA = P.ID_V_PERSONA
                                INNER JOIN SISEFE.EFTV_DELITO_SUJETO DS
                                           ON SC.ID_V_SUJETO_CASO = DS.ID_V_SUJETO_CASO
                                INNER JOIN SISMPA.MPTV_SUJETO_CONDICION MSC
                                           ON SC.ID_V_SUJETO_CASO = MSC.ID_V_SUJETO_CASO
                                CROSS JOIN JSON_TABLE(PI_JSON_DENUNCIADOS, '$[*]' COLUMNS (
                                       nombreCompleto VARCHAR2(100) PATH '$.nombreCompleto',
                                       idTipoDocumento NUMBER PATH '$.idTipoDocumento',
                                       tipoDocumento VARCHAR2(100) PATH '$.tipoDocumento',
                                       numeroDocumento VARCHAR2(20) PATH '$.numeroDocumento',
                                       idTipoParte NUMBER PATH '$.idTipoParte',
                                       tipoParte VARCHAR2(100) PATH '$.tipoParte',
                                       idCondicion VARCHAR2(100) PATH '$.idCondicion',
                                       condicion VARCHAR2(100) PATH '$.condicion'
                                       )) J
                       WHERE P.NU_V_DOCUMENTO = J.numeroDocumento
                         AND SC.ID_N_TIPO_PARTE_SUJETO = J.idTipoParte
                         AND MSC.ID_V_SUJETO_CONDICION = J.idCondicion
                       GROUP BY SC.ID_V_CASO)
  -- IMPUTADOS
  AND SC.ID_V_CASO IN (SELECT SC.ID_V_CASO
                       FROM SISCFE.CFTV_SUJETO_CASO SC
                                INNER JOIN SISCFE.CFTV_PERSONA P ON SC.ID_V_PERSONA = P.ID_V_PERSONA
                                INNER JOIN SISEFE.EFTV_DELITO_SUJETO DS
                                           ON SC.ID_V_SUJETO_CASO = DS.ID_V_SUJETO_CASO
                                INNER JOIN SISMPA.MPTV_SUJETO_CONDICION MSC
                                           ON SC.ID_V_SUJETO_CASO = MSC.ID_V_SUJETO_CASO
                                CROSS JOIN JSON_TABLE(PI_JSON_IMPUTADOS, '$[*]' COLUMNS (
                                       nombreCompleto VARCHAR2(100) PATH '$.nombreCompleto',
                                       idTipoDocumento NUMBER PATH '$.idTipoDocumento',
                                       tipoDocumento VARCHAR2(100) PATH '$.tipoDocumento',
                                       numeroDocumento VARCHAR2(20) PATH '$.numeroDocumento',
                                       idTipoParte NUMBER PATH '$.idTipoParte',
                                       tipoParte VARCHAR2(100) PATH '$.tipoParte',
                                       idCondicion VARCHAR2(100) PATH '$.idCondicion',
                                       condicion VARCHAR2(100) PATH '$.condicion'
                                       )) J
                       WHERE P.NU_V_DOCUMENTO = J.numeroDocumento
                         AND SC.ID_N_TIPO_PARTE_SUJETO = J.idTipoParte
                         AND MSC.ID_V_SUJETO_CONDICION = J.idCondicion
                       GROUP BY SC.ID_V_CASO);

PO_V_ERR_COD := V_V_COD_OK;
        PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
        WHEN
            EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : ' ||
                                 PO_V_ERR_COD || ' - ' ||
                                 PO_V_ERR_MSG);
WHEN
            OTHERS THEN
            PO_V_ERR_COD := '1';
            PO_V_ERR_MSG := 'MPSP_VALIDAR_DUPLICIDAD - ERROR : ' || SQLERRM;

END MPSP_VALIDAR_DUPLICIDAD;

    PROCEDURE MPSP_LISTAR_OFICINAS_RENADESPPLE(PO_CR_OFICINAS_RENADESPPLE OUT SYS_REFCURSOR,
                                               PO_V_ERR_COD OUT VARCHAR2,
                                               PO_V_ERR_MSG OUT VARCHAR2)
IS
        EX_CONTROLADO EXCEPTION;

BEGIN
OPEN PO_CR_OFICINAS_RENADESPPLE FOR
SELECT ID_N_CATALOGO    AS ID,
       NO_V_DESCRIPCION AS VALOR
FROM SISMAEST.CFTM_CATALOGO
WHERE NO_V_GRUPO = 'ID_N_OFICINA_RENA';

PO_V_ERR_COD := V_V_COD_OK;
        PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
        WHEN
            EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : ' ||
                                 PO_V_ERR_COD || ' - ' ||
                                 PO_V_ERR_MSG);
WHEN
            OTHERS THEN
            PO_V_ERR_COD := '1';
            PO_V_ERR_MSG := 'MPSP_LISTAR_OFICINAS_RENADESPPLE - ERROR : ' || SQLERRM;

END MPSP_LISTAR_OFICINAS_RENADESPPLE;

    /**********************************************************************************************
    * Nombre        : SISMPA.MPSP_REGISTRAR_ACTUALIZAR_FICHA_RENADESPPLE
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Registrar/Actualizar Ficha Renadespple
    * Fecha creaci?n: 22.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_REGISTRAR_ACTUALIZAR_FICHA_RENADESPPLE(PI_ID_V_FICHA_RENADESPPLE IN VARCHAR2,
                                                          PI_ID_V_CASO IN VARCHAR2,
                                                          PI_ID_V_SUJETO_CASO IN VARCHAR2,
                                                          PI_ID_N_OFICINA IN VARCHAR2,
                                                          PI_FL_C_AFROPERUANO IN CHAR,
                                                          PI_FL_C_DISCAPACIDAD IN CHAR,
                                                          PI_FL_C_PRIV_LIBERTAD IN CHAR,
                                                          PI_FL_C_VIH_TBC IN CHAR,
                                                          PI_FL_C_TRAB_HOGAR IN CHAR,
                                                          PI_FL_C_LGTBI IN CHAR,
                                                          PI_FL_C_MIGRANTE IN CHAR,
                                                          PI_FL_C_VICTIM_VIOLE_8020 IN CHAR,
                                                          PI_FL_C_DEFENSOR IN CHAR,
                                                          PI_FL_C_FUNC_PUBLICO IN CHAR,
                                                          PI_ID_N_TIPO_PUEBLO IN NUMBER,
                                                          PI_ID_N_TIPO_LENGUA IN NUMBER,
                                                          PI_FL_C_AVANCE IN CHAR,
                                                          PI_N_STEP IN NUMBER,
                                                          PI_N_OPERACION IN NUMBER,
                                                          PI_FL_C_TENTATIVA IN CHAR,
                                                          PI_ID_N_MOTIVO_DETENCION IN NUMBER,
                                                          PI_NU_V_INFORME_POL IN VARCHAR2,
                                                          PI_FE_D_DENUNCIA_POLI IN VARCHAR2,
                                                          PI_ID_N_SITUACION_JURIDICA IN NUMBER,
                                                          PI_FE_D_LIBERTAD IN VARCHAR2,
                                                          PI_V_ALIAS IN VARCHAR2,
                                                          PO_V_ERR_COD OUT VARCHAR2,
                                                          PO_V_ERR_MSG OUT VARCHAR2)
IS
        V_ID_V_PERSONA   VARCHAR2(200);
        V_EXISTE   NUMBER := 0;
        V_FECHA_DENUNCIA DATE;
        V_FECHA_LIBERTAD DATE;
        EX_CONTROLADO EXCEPTION;
BEGIN
        -- Step 1 - Informaci?n General
        IF PI_N_STEP = 1 THEN
            -- Step 1 - Registrar
            IF PI_N_OPERACION = 0 THEN
                INSERT INTO SISMPA.MPTX_FICHA_RENADESPPLE (ID_V_CASO, ID_V_SUJETO_CASO,
                                                           ID_N_OFICINA, FL_C_AVANCE)
                VALUES (PI_ID_V_CASO, PI_ID_V_SUJETO_CASO, PI_ID_N_OFICINA, PI_FL_C_AVANCE);
END IF;

            -- Step 1 - Actualizar
            IF PI_N_OPERACION = 1 THEN
UPDATE SISMPA.MPTX_FICHA_RENADESPPLE
SET ID_N_OFICINA = PI_ID_N_OFICINA,
    FL_C_AVANCE  = PI_FL_C_AVANCE
WHERE ID_V_FICHA_RENADESPPLE = PI_ID_V_FICHA_RENADESPPLE;
END IF;

SELECT COUNT(*) INTO V_EXISTE
FROM SISCFE.CFTV_ALIAS_SUJETO where ID_V_SUJETO_CASO = PI_ID_V_SUJETO_CASO;

IF V_EXISTE > 0 THEN
UPDATE SISCFE.CFTV_ALIAS_SUJETO SET NO_V_ALIAS_SUJETO = PI_V_ALIAS
WHERE ID_V_SUJETO_CASO = PI_ID_V_SUJETO_CASO;
ELSE
              INSERT INTO SISCFE.CFTV_ALIAS_SUJETO(
                  ID_V_ALIAS_SUJETO,
                  ID_V_SUJETO_CASO,
                  NO_V_ALIAS_SUJETO,
                  FE_D_CREACION,
                  CO_V_US_CREACION,
                  ES_C_ALIAS_SUJETO
              )
              VALUES(
                  SYS_GUID(),
                  PI_ID_V_SUJETO_CASO,
                  PI_V_ALIAS,
                  SYSDATE,
                  'SISMPA',
                  '1'
              );
END IF;

END IF;

        -- Step 1 - Informaci?n Datos adicionales
        IF PI_N_STEP = 5 THEN
            -- Step 4 - Actualizar
            IF PI_N_OPERACION = 1 THEN
SELECT SC.ID_V_PERSONA
INTO V_ID_V_PERSONA
FROM SISCFE.CFTV_SUJETO_CASO SC
WHERE SC.ID_V_SUJETO_CASO = PI_ID_V_SUJETO_CASO
  AND ROWNUM = 1;

UPDATE SISCFE.CFTV_PERSONA_NATURAL
SET FL_C_AFROPERUANO       = PI_FL_C_AFROPERUANO,
    FL_C_DISCAPACIDAD      = PI_FL_C_DISCAPACIDAD,
    FL_C_PRIV_LIBERTAD     = PI_FL_C_PRIV_LIBERTAD,
    FL_C_VIH_TBC           = PI_FL_C_VIH_TBC,
    FL_C_TRAB_HOGAR        = PI_FL_C_TRAB_HOGAR,
    FL_C_LGTBI             = PI_FL_C_LGTBI,
    FL_C_MIGRANTE          = PI_FL_C_MIGRANTE,
    FL_C_VICTIM_VIOLE_8020 = PI_FL_C_VICTIM_VIOLE_8020,
    ID_N_TIPO_PUEBLO       = PI_ID_N_TIPO_LENGUA,
    ID_N_TIPO_LENGUA       = PI_ID_N_TIPO_PUEBLO
WHERE ID_V_PERSONA = V_ID_V_PERSONA;

UPDATE SISCFE.CFTV_SUJETO_CASO_DETALLE
SET FL_C_DEFENSOR    = PI_FL_C_DEFENSOR,
    FL_C_FUNC_PUBLICO= PI_FL_C_FUNC_PUBLICO
WHERE ID_V_SUJETO_CASO = PI_ID_V_SUJETO_CASO;
END IF;
END IF;

        -- Step 2
        IF PI_N_STEP = 2 THEN
            --             -- Step 2 - Registrar
--             IF PI_N_OPERACION = 0 THEN
--             END IF;

            -- Step 2 - Actualizar
            IF PI_N_OPERACION = 1 THEN
UPDATE SISCFE.CFTV_SUJETO_CASO_DETALLE
SET FL_C_TENTATIVA        = PI_FL_C_TENTATIVA,
    ID_N_MOTIVO_DETENCION = PI_ID_N_MOTIVO_DETENCION
WHERE ID_V_SUJETO_CASO = PI_ID_V_SUJETO_CASO;

UPDATE SISMPA.MPTX_FICHA_RENADESPPLE
SET FL_C_AVANCE = PI_FL_C_AVANCE
WHERE ID_V_FICHA_RENADESPPLE = PI_ID_V_FICHA_RENADESPPLE;
END IF;
END IF;

        -- Step 3
        IF PI_N_STEP = 3 THEN
            --             -- Step 3 - Registrar
--             IF PI_N_OPERACION = 0 THEN
--             END IF;

            IF PI_FE_D_DENUNCIA_POLI IS NOT NULL THEN
                V_FECHA_DENUNCIA := TO_DATE(PI_FE_D_DENUNCIA_POLI, 'DD/MM/YYYY');
END IF;

            IF PI_FE_D_LIBERTAD IS NOT NULL THEN
                V_FECHA_LIBERTAD := TO_DATE(PI_FE_D_LIBERTAD, 'DD/MM/YYYY');
END IF;

            -- Step 3 - Actualizar
            IF PI_N_OPERACION = 1 THEN
UPDATE SISCFE.CFTV_SUJETO_CASO_DETALLE
SET ID_N_SITUACION_JURIDICA = PI_ID_N_SITUACION_JURIDICA,
    FE_D_LIBERTAD           = V_FECHA_LIBERTAD
WHERE ID_V_SUJETO_CASO = PI_ID_V_SUJETO_CASO;

UPDATE SISMPA.MPTV_DENUNCIA_DETALLE
SET NU_V_INFORME_POL   = PI_NU_V_INFORME_POL,
    FE_D_DENUNCIA_POLI = V_FECHA_DENUNCIA
WHERE ID_V_SUJETO_CASO = PI_ID_V_SUJETO_CASO;

UPDATE SISMPA.MPTX_FICHA_RENADESPPLE
SET FL_C_AVANCE = PI_FL_C_AVANCE
WHERE ID_V_FICHA_RENADESPPLE = PI_ID_V_FICHA_RENADESPPLE;
END IF;
END IF;

        IF PI_N_STEP = 4 THEN
            -- Step 4 - Actualizar
            IF PI_N_OPERACION = 1 THEN
UPDATE SISMPA.MPTX_FICHA_RENADESPPLE
SET FL_C_AVANCE = PI_FL_C_AVANCE
WHERE ID_V_FICHA_RENADESPPLE = PI_ID_V_FICHA_RENADESPPLE;
END IF;
END IF;

        PO_V_ERR_COD := V_V_COD_OK;
        PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
        WHEN
            EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : ' ||
                                 PO_V_ERR_COD || ' - ' ||
                                 PO_V_ERR_MSG);
WHEN
            OTHERS THEN
            PO_V_ERR_COD := '1';
            PO_V_ERR_MSG := 'MPSP_REGISTRAR_ACTUALIZAR_FICHA_RENADESPPLE - ERROR : ' || SQLERRM;

END MPSP_REGISTRAR_ACTUALIZAR_FICHA_RENADESPPLE;

    /**********************************************************************************************
    * Nombre        : SISMPA.MPSP_LISTAR_DIRECCIONES_INFO
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Registrar/Actualizar Ficha Renadespple
    * Fecha creaci?n: 22.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_LISTAR_DIRECCIONES_INFO(PI_ID_V_SUJETO_CASO IN VARCHAR2,
                                           PO_CR_DIRECCIONES OUT SYS_REFCURSOR,
                                           PO_V_ERR_COD OUT VARCHAR2,
                                           PO_V_ERR_MSG OUT VARCHAR2)
IS
        EX_CONTROLADO EXCEPTION;
BEGIN
OPEN PO_CR_DIRECCIONES FOR
SELECT D.*,
       TD.NO_V_TIPO_DOMICILIO    AS NO_V_TIPO_DOMICILIO,
       TV.NO_V_TIPO_VIA          AS NO_V_TIPO_VIA,
       DPTO.CO_V_DPTO_GEOGRAFICA AS CO_V_DPTO_GEOGRAFICA,
       DPTO.NO_V_DPTO_GEOGRAFICA AS NO_V_DPTO_GEOGRAFICA,
       PROV.CO_V_PROV_GEOGRAFICA AS CO_V_PROV_GEOGRAFICA,
       PROV.NO_V_PROV_GEOGRAFICA AS NO_V_PROV_GEOGRAFICA,
       DIST.CO_V_DIST_GEOGRAFICA AS CO_V_DIST_GEOGRAFICA,
       DIST.NO_V_DIST_GEOGRAFICA AS NO_V_DIST_GEOGRAFICA
FROM SISCFE.CFTV_DIRECCION D
         LEFT JOIN SISMAEST.CFTM_TIPO_DOMICILIO TD ON D.ID_N_TIPO_DOMICILIO = TD.ID_N_TIPO_DOMICILIO
         LEFT JOIN SISMAEST.CFTM_TIPO_VIA TV ON TV.ID_N_TIPO_VIA = D.ID_N_TIPO_VIA
         LEFT JOIN SISMAEST.CFTM_DIST_GEOGRAFICA DIST ON D.ID_N_UBIGEO = DIST.ID_N_UBIGEO
         LEFT JOIN SISMAEST.CFTM_DPTO_GEOGRAFICA DPTO
                   ON DIST.CO_V_DPTO_GEOGRAFICA = DPTO.CO_V_DPTO_GEOGRAFICA
         LEFT JOIN SISMAEST.CFTM_PROV_GEOGRAFICA PROV
                   ON DIST.CO_V_PROV_GEOGRAFICA = PROV.CO_V_PROV_GEOGRAFICA AND
                      DIST.CO_V_DPTO_GEOGRAFICA = PROV.CO_V_DPTO_GEOGRAFICA
WHERE D.ID_V_SUJETO_CASO = PI_ID_V_SUJETO_CASO
  AND D.ES_C_DIRECCION = '1';

PO_V_ERR_COD := V_V_COD_OK;
        PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
        WHEN
            EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : ' ||
                                 PO_V_ERR_COD || ' - ' ||
                                 PO_V_ERR_MSG);
WHEN
            OTHERS THEN
            PO_V_ERR_COD := '1';
            PO_V_ERR_MSG := 'MPSP_LISTAR_DIRECCIONES_INFO - ERROR : ' || SQLERRM;

END MPSP_LISTAR_DIRECCIONES_INFO;

    /**********************************************************************************************
    * Nombre        : SISMPA.MPSP_ELIMINAR_DIRECCION_INFO
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Eliminar direccion
    * Fecha creaci?n: 23.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_ELIMINAR_DIRECCION_INFO(PI_ID_V_DIRECCION IN VARCHAR2,
                                           PO_V_ERR_COD OUT VARCHAR2,
                                           PO_V_ERR_MSG OUT VARCHAR2)
IS
        EX_CONTROLADO EXCEPTION;
BEGIN

UPDATE SISCFE.CFTV_DIRECCION
SET ES_C_DIRECCION = '0'
WHERE ID_V_DIRECCION = PI_ID_V_DIRECCION;

PO_V_ERR_COD := V_V_COD_OK;
        PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
        WHEN
            EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : ' ||
                                 PO_V_ERR_COD || ' - ' ||
                                 PO_V_ERR_MSG);
WHEN
            OTHERS THEN
            PO_V_ERR_COD := '1';
            PO_V_ERR_MSG := 'MPSP_ELIMINAR_DIRECCION_INFO - ERROR : ' || SQLERRM;

END MPSP_ELIMINAR_DIRECCION_INFO;

    /**********************************************************************************************
    * Nombre        : SISMPA.MPSP_REGISTRAR_DIRECCION_INFO
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Registrar direccion
    * Fecha creaci?n: 23.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_REGISTRAR_DIRECCION_INFO(PI_ID_V_DIRECCION IN VARCHAR2,
                                            PI_ID_V_SUJETO_CASO IN VARCHAR2,
                                            PI_ID_N_TIPO_DOMICILIO IN NUMBER,
                                            PI_ID_N_PAIS IN NUMBER,
                                            PI_ID_V_UBIGEO IN VARCHAR2,
                                            PI_ID_N_TIPO_VIA IN NUMBER,
                                            PI_DI_V_RESIDENCIA IN VARCHAR2,
                                            PI_NU_N_RESIDENCIA IN NUMBER,
                                            PI_ID_N_TIPO_PREF_URB IN NUMBER,
                                            PI_DE_V_URBANIZACION IN VARCHAR2,
                                            PI_DE_V_BLOCK_DIRECCION IN VARCHAR2,
                                            PI_DE_V_INTERIOR IN VARCHAR2,
                                            PI_DE_V_ETAPA IN VARCHAR2,
                                            PI_DE_V_MANZANA IN VARCHAR2,
                                            PI_DE_V_LOTE IN VARCHAR2,
                                            PI_DE_V_REFERENCIA IN VARCHAR2,
                                            PO_V_ERR_COD OUT VARCHAR2,
                                            PO_V_ERR_MSG OUT VARCHAR2)
IS
        EX_CONTROLADO EXCEPTION;
        PI_ID_N_UBIGEO NUMBER(15);
        P_EXIST NUMBER(1) := 0;
BEGIN

SELECT COUNT(*) INTO P_EXIST
FROM SISMAEST.CFTM_DIST_GEOGRAFICA t
WHERE co_v_ubigeo_reniec = PI_ID_V_UBIGEO;

IF P_EXIST > 0 THEN
SELECT id_n_ubigeo INTO PI_ID_N_UBIGEO
FROM SISMAEST.CFTM_DIST_GEOGRAFICA
WHERE co_v_ubigeo_reniec = PI_ID_V_UBIGEO;
END IF;

        P_EXIST :=0;

SELECT COUNT(*) INTO P_EXIST
FROM SISMAEST.CFTM_DIST_GEOGRAFICA t
WHERE co_v_ubigeo_inei = PI_ID_V_UBIGEO;

IF P_EXIST > 0 THEN
SELECT id_n_ubigeo INTO PI_ID_N_UBIGEO
FROM SISMAEST.CFTM_DIST_GEOGRAFICA
WHERE co_v_ubigeo_inei = PI_ID_V_UBIGEO;
END IF;

INSERT INTO SISCFE.CFTV_DIRECCION (ID_V_SUJETO_CASO,
                                   ID_N_TIPO_DOMICILIO,
                                   ID_N_PAIS,
                                   ID_N_UBIGEO,
                                   ID_N_TIPO_VIA,
                                   DI_V_RESIDENCIA,
                                   NU_N_RESIDENCIA,
                                   ID_N_TIPO_PREF_URB,
                                   DE_V_URBANIZACION,
                                   DE_V_BLOCK_DIRECCION,
                                   DE_V_INTERIOR,
                                   DE_V_ETAPA,
                                   DE_V_MANZANA,
                                   DE_V_LOTE,
                                   DE_V_REFERENCIA)
VALUES (PI_ID_V_SUJETO_CASO,
        PI_ID_N_TIPO_DOMICILIO,
        PI_ID_N_PAIS,
        PI_ID_N_UBIGEO,
        PI_ID_N_TIPO_VIA,
        PI_DI_V_RESIDENCIA,
        PI_NU_N_RESIDENCIA,
        PI_ID_N_TIPO_PREF_URB,
        PI_DE_V_URBANIZACION,
        PI_DE_V_BLOCK_DIRECCION,
        PI_DE_V_INTERIOR,
        PI_DE_V_ETAPA,
        PI_DE_V_MANZANA,
        PI_DE_V_LOTE,
        PI_DE_V_REFERENCIA);

PO_V_ERR_COD := V_V_COD_OK;
        PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
        WHEN
            EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : ' ||
                                 PO_V_ERR_COD || ' - ' ||
                                 PO_V_ERR_MSG);
WHEN
            OTHERS THEN
            PO_V_ERR_COD := '1';
            PO_V_ERR_MSG := 'MPSP_REGISTRAR_DIRECCION_INFO - ERROR : ' || SQLERRM;

END MPSP_REGISTRAR_DIRECCION_INFO;

    /**********************************************************************************************
    * Nombre        : SISMPA.MPSP_ACTUALIZAR_DIRECCION_INFO
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Actualizar direccion
    * Fecha creaci?n: 23.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_ACTUALIZAR_DIRECCION_INFO(PI_ID_V_DIRECCION IN VARCHAR2,
                                             PI_ID_V_SUJETO_CASO IN VARCHAR2,
                                             PI_ID_N_TIPO_DOMICILIO IN NUMBER,
                                             PI_ID_N_PAIS IN NUMBER,
                                             PI_ID_V_UBIGEO IN VARCHAR2,
                                             PI_ID_N_TIPO_VIA IN NUMBER,
                                             PI_DI_V_RESIDENCIA IN VARCHAR2,
                                             PI_NU_N_RESIDENCIA IN NUMBER,
                                             PI_ID_N_TIPO_PREF_URB IN NUMBER,
                                             PI_DE_V_URBANIZACION IN VARCHAR2,
                                             PI_DE_V_BLOCK_DIRECCION IN VARCHAR2,
                                             PI_DE_V_INTERIOR IN VARCHAR2,
                                             PI_DE_V_ETAPA IN VARCHAR2,
                                             PI_DE_V_MANZANA IN VARCHAR2,
                                             PI_DE_V_LOTE IN VARCHAR2,
                                             PI_DE_V_REFERENCIA IN VARCHAR2,
                                             PO_V_ERR_COD OUT VARCHAR2,
                                             PO_V_ERR_MSG OUT VARCHAR2)
IS
        EX_CONTROLADO EXCEPTION;
        PI_ID_N_UBIGEO NUMBER(15);
        P_EXIST NUMBER(1) := 0;
BEGIN

SELECT COUNT(*) INTO P_EXIST
FROM SISMAEST.CFTM_DIST_GEOGRAFICA t
WHERE co_v_ubigeo_reniec = PI_ID_V_UBIGEO;

IF P_EXIST > 0 THEN
SELECT id_n_ubigeo INTO PI_ID_N_UBIGEO
FROM SISMAEST.CFTM_DIST_GEOGRAFICA
WHERE co_v_ubigeo_reniec = PI_ID_V_UBIGEO;
END IF;

        P_EXIST :=0;

SELECT COUNT(*) INTO P_EXIST
FROM SISMAEST.CFTM_DIST_GEOGRAFICA t
WHERE co_v_ubigeo_inei = PI_ID_V_UBIGEO;

IF P_EXIST > 0 THEN
SELECT id_n_ubigeo INTO PI_ID_N_UBIGEO
FROM SISMAEST.CFTM_DIST_GEOGRAFICA
WHERE co_v_ubigeo_inei = PI_ID_V_UBIGEO;
END IF;

UPDATE SISCFE.CFTV_DIRECCION
SET ID_N_TIPO_DOMICILIO  = PI_ID_N_TIPO_DOMICILIO,
    ID_N_PAIS            = PI_ID_N_PAIS,
    ID_N_UBIGEO          = PI_ID_N_UBIGEO,
    ID_N_TIPO_VIA        = CASE WHEN PI_ID_N_TIPO_VIA = 0 THEN NULL ELSE PI_ID_N_TIPO_VIA END,
    DI_V_RESIDENCIA      = PI_DI_V_RESIDENCIA,
    NU_N_RESIDENCIA      = PI_NU_N_RESIDENCIA,
    ID_N_TIPO_PREF_URB   = PI_ID_N_TIPO_PREF_URB,
    DE_V_URBANIZACION    = PI_DE_V_URBANIZACION,
    DE_V_BLOCK_DIRECCION = PI_DE_V_BLOCK_DIRECCION,
    DE_V_INTERIOR        = PI_DE_V_INTERIOR,
    DE_V_ETAPA           = PI_DE_V_ETAPA,
    DE_V_MANZANA         = PI_DE_V_MANZANA,
    DE_V_LOTE            = PI_DE_V_LOTE,
    DE_V_REFERENCIA      = PI_DE_V_REFERENCIA
WHERE ID_V_DIRECCION = PI_ID_V_DIRECCION;

PO_V_ERR_COD := V_V_COD_OK;
        PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
        WHEN
            EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : ' ||
                                 PO_V_ERR_COD || ' - ' ||
                                 PO_V_ERR_MSG);
WHEN
            OTHERS THEN
            PO_V_ERR_COD := '1';
            PO_V_ERR_MSG := 'MPSP_ACTUALIZAR_DIRECCION_INFO - ERROR : ' || SQLERRM;

END MPSP_ACTUALIZAR_DIRECCION_INFO;

    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_LISTAR_MOTIVOS_DETENCION
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Listar Motivos Detencion
    * Fecha creaci?n: 23.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_LISTAR_MOTIVOS_DETENCION(PO_CR_MOTIVOS_DETENCION OUT SYS_REFCURSOR,
                                            PO_V_ERR_COD OUT VARCHAR2,
                                            PO_V_ERR_MSG OUT VARCHAR2)
IS
        EX_CONTROLADO EXCEPTION;

BEGIN
OPEN PO_CR_MOTIVOS_DETENCION FOR
SELECT *
FROM SISMAEST.CFTM_CATALOGO
WHERE NO_V_GRUPO = 'ID_N_MOTIVO_DETENCION';

PO_V_ERR_COD := V_V_COD_OK;
        PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
        WHEN
            EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : ' ||
                                 PO_V_ERR_COD || ' - ' ||
                                 PO_V_ERR_MSG);
WHEN
            OTHERS THEN
            PO_V_ERR_COD := '1';
            PO_V_ERR_MSG := 'MPSP_LISTAR_MOTIVOS_DETENCION - ERROR : ' || SQLERRM;

END MPSP_LISTAR_MOTIVOS_DETENCION;


/**********************************************************************************************
* Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_OBTENER_MOMENTO_DETENCION
* Autor         : Adolfo Villanueva Paravicino
* Versi?n       : 1.0
* Descripci?n   : Obtener Motivos Detencion
* Fecha creaci?n: 23.01.2024
***********************************************************************************************/
    PROCEDURE MPSP_OBTENER_MOMENTO_DETENCION(PI_ID_V_CASO IN VARCHAR2,
                                             PO_CR_MOMENTO_DETENCION OUT SYS_REFCURSOR,
                                             PO_V_ERR_COD OUT VARCHAR2,
                                             PO_V_ERR_MSG OUT VARCHAR2) IS
        EX_CONTROLADO EXCEPTION;
BEGIN

OPEN PO_CR_MOMENTO_DETENCION FOR
SELECT DISTINCT D.ID_V_DENUNCIA           AS ID_V_DENUNCIA,
                D.ID_V_CASO               AS ID_V_CASO,
                DP.ID_N_DPND_POLICIAL     AS ID_N_DPND_POLICIAL,
                DP.NO_V_DPND_POLICIAL     AS NO_V_DPND_POLICIAL,
                DIST.CO_V_UBIGEO_RENIEC   AS ID_V_UBIGEO,
                --DP.ID_N_UBIGEO            AS ID_N_UBIGEO,
                H.FE_D_DETENCION          AS FE_D_DETENCION,
                SCD.ID_N_MOTIVO_DETENCION AS ID_N_MOTIVO_DETENCION,
                SCD.FL_C_TENTATIVA        AS FL_C_TENTATIVA,
                DIST.NO_V_DIST_GEOGRAFICA AS NO_V_DIST_GEOGRAFICA,
                PROV.NO_V_PROV_GEOGRAFICA AS NO_V_PROV_GEOGRAFICA,
                DPTO.NO_V_DPTO_GEOGRAFICA AS NO_V_DPTO_GEOGRAFICA
FROM SISMPA.MPTV_DENUNCIA D
         LEFT JOIN SISMAEST.CFTM_DEPENDENCIA_POLICIAL DP
                   ON DP.ID_N_DPND_POLICIAL = D.ID_N_DPND_POLICIAL
         LEFT JOIN SISMPA.MPTV_HECHO H
                   ON H.ID_V_CASO = D.ID_V_CASO
                       AND H.ID_V_DENUNCIA = D.ID_V_DENUNCIA
         LEFT JOIN SISMAEST.CFTM_DIST_GEOGRAFICA DIST ON DP.ID_N_UBIGEO = DIST.ID_N_UBIGEO
         INNER JOIN SISMAEST.CFTM_DPTO_GEOGRAFICA DPTO
                    ON DIST.CO_V_DPTO_GEOGRAFICA = DPTO.CO_V_DPTO_GEOGRAFICA
         LEFT JOIN SISCFE.CFTV_SUJETO_CASO S
                   ON D.ID_V_CASO = S.ID_V_CASO
                       AND D.ID_V_DENUNCIA = S.ID_V_DENUNCIA
         LEFT JOIN SISMAEST.CFTM_PROV_GEOGRAFICA PROV
                   ON DIST.CO_V_PROV_GEOGRAFICA = PROV.CO_V_PROV_GEOGRAFICA AND
                      DIST.CO_V_DPTO_GEOGRAFICA = PROV.CO_V_DPTO_GEOGRAFICA
         LEFT JOIN SISCFE.CFTV_SUJETO_CASO_DETALLE SCD
                   ON SCD.ID_V_SUJETO_CASO = S.ID_V_SUJETO_CASO
WHERE S.ID_V_CASO = PI_ID_V_CASO;
-- AND SCD.ID_N_MOTIVO_DETENCION IS NOT NULL;

PO_V_ERR_COD := V_V_COD_OK;
        PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
        WHEN EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : ' || PO_V_ERR_COD || ' - ' ||
                                 PO_V_ERR_MSG);
WHEN OTHERS THEN
            PO_V_ERR_COD := '1';
            PO_V_ERR_MSG := 'MPSP_OBTENER_MOMENTO_DETENCION - ERROR : ' || SQLERRM;

END MPSP_OBTENER_MOMENTO_DETENCION;

    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_OBTENER_FICHA_RENADESPPLE
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Obtener Ficha Renadespple
    * Fecha creaci?n: 23.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_OBTENER_FICHA_RENADESPPLE(PI_ID_V_CASO IN VARCHAR2,
                                             PI_ID_V_SUJETO_CASO IN VARCHAR2,
                                             PO_CR_FICHAS_RENADESPPLE OUT SYS_REFCURSOR,
                                             PO_V_ERR_COD OUT VARCHAR2,
                                             PO_V_ERR_MSG OUT VARCHAR2)
IS
        EX_CONTROLADO EXCEPTION;
BEGIN

OPEN PO_CR_FICHAS_RENADESPPLE FOR
SELECT *
FROM SISMPA.MPTX_FICHA_RENADESPPLE
WHERE ID_V_CASO = PI_ID_V_CASO
  AND ID_V_SUJETO_CASO = PI_ID_V_SUJETO_CASO;

PO_V_ERR_COD := V_V_COD_OK;
        PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
        WHEN EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : ' || PO_V_ERR_COD || ' - ' ||
                                 PO_V_ERR_MSG);
WHEN OTHERS THEN
            PO_V_ERR_COD := '1';
            PO_V_ERR_MSG := 'MPSP_OBTENER_FICHA_RENADESPPLE - ERROR : ' || SQLERRM;

END MPSP_OBTENER_FICHA_RENADESPPLE;

    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_LISTAR_SITUACIONES_JURIDICAS
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Listar Situaciones Juridicas
    * Fecha creaci?n: 23.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_LISTAR_SITUACIONES_JURIDICAS(PO_CR_SITUACIONES_JURIDICAS OUT SYS_REFCURSOR,
                                                PO_V_ERR_COD OUT VARCHAR2,
                                                PO_V_ERR_MSG OUT VARCHAR2)
IS
        EX_CONTROLADO EXCEPTION;
BEGIN
OPEN PO_CR_SITUACIONES_JURIDICAS FOR
SELECT *
FROM SISMAEST.CFTM_CATALOGO T
WHERE T.NO_V_GRUPO = 'ID_N_SITUACION_JURIDICA';

PO_V_ERR_COD := V_V_COD_OK;
        PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
        WHEN EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : ' || PO_V_ERR_COD || ' - ' ||
                                 PO_V_ERR_MSG);
WHEN OTHERS THEN
            PO_V_ERR_COD := '1';
            PO_V_ERR_MSG := 'MPSP_LISTAR_SITUACIONES_JURIDICAS - ERROR : ' || SQLERRM;

END MPSP_LISTAR_SITUACIONES_JURIDICAS;

    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_OBTENER_INVESTIGACION_POLICIAL
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Obtener InvestigacionPolicial
    * Fecha creaci?n: 23.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_OBTENER_INVESTIGACION_POLICIAL(PI_ID_V_SUJETO_CASO IN VARCHAR2,
                                                  PO_CR_INVESTIGACION_POLICIAL OUT SYS_REFCURSOR,
                                                  PO_V_ERR_COD OUT VARCHAR2,
                                                  PO_V_ERR_MSG OUT VARCHAR2)
IS
        EX_CONTROLADO EXCEPTION;
BEGIN

OPEN PO_CR_INVESTIGACION_POLICIAL FOR
SELECT DISTINCT SCD.ID_V_SUJETO_CASO,
                SCD.ID_N_SITUACION_JURIDICA,
                SCD.FE_D_LIBERTAD,
                IP.NU_V_INFORME_POL   AS NU_V_INFORME_POLICIAL,
                IP.FE_D_DENUNCIA_POLI AS FE_D_INFORME_POL
FROM SISCFE.CFTV_SUJETO_CASO_DETALLE SCD
         LEFT JOIN SISMPA.MPTV_DENUNCIA_DETALLE IP ON SCD.ID_V_SUJETO_CASO = IP.ID_V_SUJETO_CASO
WHERE SCD.ID_V_SUJETO_CASO = PI_ID_V_SUJETO_CASO;

PO_V_ERR_COD := V_V_COD_OK;
        PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
        WHEN EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : ' || PO_V_ERR_COD || ' - ' ||
                                 PO_V_ERR_MSG);
WHEN OTHERS THEN
            PO_V_ERR_COD := '1';
            PO_V_ERR_MSG := 'MPSP_LISTAR_SITUACIONES_JURIDICAS - ERROR : ' || SQLERRM;

END MPSP_OBTENER_INVESTIGACION_POLICIAL;

    /**********************************************************************************************
    * Nombre        : SISMPA.MPPK_CASO_TURNO.MPSP_CONSULTAR_PERSONA
    * Autor         : Adolfo Villanueva Paravicino
    * Versi?n       : 1.0
    * Descripci?n   : Obtener Investigacion Policial
    * Fecha creaci?n: 23.01.2024
    ***********************************************************************************************/
    PROCEDURE MPSP_CONSULTAR_PERSONA(PI_ID_V_SUJETO_CASO IN VARCHAR2,
                                     PO_CR_PERSONA OUT SYS_REFCURSOR,
                                     PO_V_ERR_COD OUT VARCHAR2,
                                     PO_V_ERR_MSG OUT VARCHAR2)
IS
        EX_CONTROLADO EXCEPTION;
BEGIN

OPEN PO_CR_PERSONA FOR
SELECT
    -- INFORMACI?N GENERAL
    CPN.FL_C_EXTRANJERO                                                  AS FL_C_EXTRANJERO,
    CP.ID_N_TIPO_DOC_IDENT                                               AS ID_N_TIPO_DOC_IDENT,
    TDI.NO_V_TIPO_DOC_IDENT                                              AS NO_V_TIPO_DOC_IDENT,
    CP.NU_V_DOCUMENTO                                                    AS NU_V_DOCUMENTO,
    CPN.FL_C_VERIFICADO                                                  AS FL_C_VERIFICADO, -- si esta en 1 ya esta verificado
    CPN.AP_V_PATERNO                                                     AS AP_V_PATERNO,
    CPN.AP_V_MATERNO                                                     AS AP_V_MATERNO,
    CPN.NO_V_CIUDADANO                                                   AS NO_V_CIUDADANO,
    NVL(TO_CHAR(CPN.FE_D_NACIMIENTO, 'DD/MM/YYYY'), CPN.FE_D_NACIMIENTO) AS FE_D_NACIMIENTO,
    CPN.NU_N_EDAD                                                        AS NU_N_EDAD,
    CPN.TI_C_SEXO                                                        AS TI_C_SEXO,       -- (1) masculino , (0) femenino
    CAS.NO_V_ALIAS_SUJETO                                                AS NO_V_ALIAS_SUJETO,
    CPN.ID_N_EST_CIV                                                     AS ID_N_EST_CIV,
    SCD.ID_N_GRAD_INST                                                   AS ID_N_GRAD_INST,
    SCD.ID_N_TIPO_ACT_LABORAL                                            AS ID_N_TIPO_ACT_LABORAL,
    SCD.NO_V_OTRA_ACT_LABORAL                                            AS NO_V_OTRA_ACT_LABORAL,
    -- DATOS FAMILIARES
    CPN.NO_V_MADRE                                                       AS NO_V_MADRE,
    CPN.NO_V_PADRE                                                       AS NO_V_PADRE,
    -- FOTO
    RB.IN_B_REPOSITORIO                                                  AS FOTO,
    -- INFORMACI?N DETALLADA
    CASE
        WHEN CSC.FL_C_CONTACTO_SECUNDARIO = '0' THEN 'PRINCIPAL'
        WHEN CSC.FL_C_CONTACTO_SECUNDARIO = '1' THEN 'SECUNDARIO'
        END                                                              AS PRIORIDAD_CONTACTO,
    TC.NO_V_TIPO_CONTACTO                                                AS NO_V_TIPO_CONTACTO,
    CSC.NO_V_DATOS_CONTACTO                                              AS NO_V_DATOS_CONTACTO,
    -- OTROS DATOS INTER?S
    CPN.ID_N_TIPO_PUEBLO                                                 AS ID_N_TIPO_PUEBLO,
    CPN.ID_N_TIPO_LENGUA                                                 AS ID_N_TIPO_LENGUA,
    -- RADIO
    COALESCE(CPN.FL_C_AFROPERUANO, '0')                                  AS FL_C_AFROPERUANO,
    COALESCE(CPN.FL_C_DISCAPACIDAD, '0')                                 AS FL_C_DISCAPACIDAD,
    COALESCE(CPN.FL_C_PRIV_LIBERTAD, '0')                                AS FL_C_PRIV_LIBERTAD,
    COALESCE(CPN.FL_C_VIH_TBC, '0')                                      AS FL_C_VIH_TBC,
    COALESCE(CPN.FL_C_TRAB_HOGAR, '0')                                   AS FL_C_TRAB_HOGAR,
    COALESCE(CPN.FL_C_LGTBI, '0')                                        AS FL_C_LGTBI,
    COALESCE(SCD.FL_C_DEFENSOR, '0')                                     AS FL_C_DEFENSOR,
    COALESCE(CPN.FL_C_MIGRANTE, '0')                                     AS FL_C_MIGRANTE,
    COALESCE(CPN.FL_C_VICTIM_VIOLE_8020, '0')                            AS FL_C_VICTIM_VIOLE_8020,
    COALESCE(SCD.FL_C_FUNC_PUBLICO, '0')                                 AS FL_C_FUNC_PUBLICO

FROM SISCFE.CFTV_SUJETO_CASO SC
         INNER JOIN SISCFE.CFTV_SUJETO_CASO_DETALLE SCD ON SC.ID_V_SUJETO_CASO = SCD.ID_V_SUJETO_CASO
         INNER JOIN SISCFE.CFTV_PERSONA CP ON SC.ID_V_PERSONA = CP.ID_V_PERSONA
         INNER JOIN SISCFE.CFTV_PERSONA_NATURAL CPN ON CP.ID_V_PERSONA = CPN.ID_V_PERSONA
         INNER JOIN SISMAEST.CFTM_TIPO_DOC_IDENT TDI ON TDI.ID_N_TIPO_DOC_IDENT = CP.ID_N_TIPO_DOC_IDENT
         LEFT JOIN SISCFE.CFTV_ALIAS_SUJETO CAS ON CAS.ID_V_SUJETO_CASO = SC.ID_V_SUJETO_CASO
         LEFT JOIN SISCFE.CFTV_DATOS_BIOMETRICOS_SUJETOS DBS ON SC.ID_V_SUJETO_CASO = DBS.ID_V_SUJETO_CASO
         LEFT JOIN SISCFE.CFTV_REGISTROS_BIOMETRICOS RB
                   ON RB.ID_V_REGISTROS_BIOMETRICOS = DBS.ID_V_REGISTROS_BIOMETRICOS
         LEFT JOIN SISCFE.CFTV_SUJETO_CONTACTO CSC ON CSC.ID_V_SUJETO_CASO = SC.ID_V_SUJETO_CASO
         LEFT JOIN SISMAEST.MPTM_TIPO_CONTACTO TC ON CSC.ID_N_TIPO_CONTACTO = TC.ID_N_TIPO_CONTACTO
    AND CSC.ES_C_SUJETO_CONTACTO = '1' AND TC.ES_C_ESTADO_TIPO_CONTACTO = '1'
WHERE SC.ID_V_SUJETO_CASO = PI_ID_V_SUJETO_CASO
  AND SC.ID_V_SUJETO_CASO = PI_ID_V_SUJETO_CASO;

PO_V_ERR_COD := V_V_COD_OK;
        PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
        WHEN EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : ' || PO_V_ERR_COD || ' - ' ||
                                 PO_V_ERR_MSG);
WHEN OTHERS THEN
            PO_V_ERR_COD := '1';
            PO_V_ERR_MSG := 'MPSP_CONSULTAR_PERSONA - ERROR : ' || SQLERRM;

END MPSP_CONSULTAR_PERSONA;

END MPPK_CASO_TURNO;