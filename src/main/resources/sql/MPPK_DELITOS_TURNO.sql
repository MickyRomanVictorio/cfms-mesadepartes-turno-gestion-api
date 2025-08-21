CREATE OR REPLACE PACKAGE SISMPA.MPPK_DELITOS_TURNO AS

    /******************************************************************
	* Nombre        : SISMPA.MPPK_DELITOS_TURNO.MPSP_REGISTRAR_DELITOS
	* Autor         : Pedro Yarleque Linares
	* Versión       : 1.0
	* Descripción   : Registrar delitos de denuncia en Mesa de Turno.
	* Fecha creación: 21.07.2025
	******************************************************************/

    PROCEDURE MPSP_REGISTRAR_DELITOS (
	    PI_V_ID_DENUNCIA     IN SISMPA.MPTV_DENUNCIA.ID_V_DENUNCIA%TYPE,
	    PI_V_ID_CASO         IN SISEFE.EFTV_CASO.ID_V_CASO%TYPE,
	    PI_CR_DELITOS        IN SISMPA.MPPK_REGISTRA_DENUNCIA_TURNO.CR_DELITOS,
	    PI_CR_DETENIDOS      IN SISMPA.MPPK_REGISTRA_DENUNCIA_TURNO.CR_DETENIDOS,
	    PI_V_CO_US_CREACION  IN SISMPA.MPTV_DENUNCIA.CO_V_US_CREACION%TYPE,
	    PO_V_ERR_COD         OUT VARCHAR2,
	    PO_V_ERR_MSG         OUT VARCHAR2
	);

    /******************************************************************************************
	* Nombre        : SISMPA.MPPK_DELITOS_TURNO.MPFN_OBTENER_O_INSERTAR_DELITO_SUJETO
	* Autor         : Pedro Yarleque Linares
	* Versión       : 1.0
	* Descripción   : Función auxiliar para obtener ID_V_DELITO_SUJETO o insertar si no existe
	* Fecha creación: 21.07.2025
	******************************************************************************************/

    FUNCTION MPFN_OBTENER_O_INSERTAR_DELITO_SUJETO(
        PI_V_ID_SUJETO_CASO    SISEFE.EFTV_DELITO_SUJETO.ID_V_SUJETO_CASO%TYPE,
        PI_N_ID_DELITO         SISEFE.EFTV_DELITO_SUJETO.ID_N_DELITO%TYPE,
        PI_N_ID_SUBGENERICO    SISEFE.EFTV_DELITO_SUJETO.ID_N_SUBGENERICO%TYPE,
        PI_N_ID_ESPECIFICO     SISEFE.EFTV_DELITO_SUJETO.ID_N_ESPECIFICO%TYPE
    ) RETURN VARCHAR2;

    /*********************************************************************
	* Nombre        : SISMPA.MPPK_DELITOS_PARTES_TURNO.MPSP_LISTAR_DELITOS
	* Autor         : Pedro Yarleque Linares
	* Versi?n       : 1.0
	* Descripci?n   : Listar los delitos de la denuncia en Mesa de Turno
	* Fecha creaci?n: 21.07.2025
	**********************************************************************/

    PROCEDURE MPSP_LISTAR_DELITOS(
	    PI_V_ID_CASO   IN  SISEFE.EFTV_CASO.ID_V_CASO%TYPE,
	    PO_CR_DELITOS  OUT SYS_REFCURSOR,
	    PO_V_ERR_COD   OUT VARCHAR2,
	    PO_V_ERR_MSG   OUT VARCHAR2
	);

END MPPK_DELITOS_TURNO;

/

CREATE OR REPLACE PACKAGE BODY SISMPA.MPPK_DELITOS_TURNO AS

  	V_V_COD_OK VARCHAR2(40) := '0';
    V_V_MSG_OK VARCHAR2(200) := 'LA OPERACION SE REALIZÓ SATISFACTORIAMENTE';

    V_V_COD_ERR VARCHAR2(40) := '0';
    V_V_MSG_ERR VARCHAR2(200) := '';

    EX_CONTROLADO EXCEPTION;

    /******************************************************************
	* Nombre        : SISMPA.MPPK_DELITOS_TURNO.MPSP_REGISTRAR_DELITOS
	* Autor         : Pedro Yarleque Linares
	* Versión       : 1.0
	* Descripción   : Registrar delitos de denuncia en Mesa de Turno.
	* Fecha creación: 21.07.2025
	******************************************************************/

    PROCEDURE MPSP_REGISTRAR_DELITOS (
	    PI_V_ID_DENUNCIA     IN SISMPA.MPTV_DENUNCIA.ID_V_DENUNCIA%TYPE,
	    PI_V_ID_CASO         IN SISEFE.EFTV_CASO.ID_V_CASO%TYPE,
	    PI_CR_DELITOS        IN SISMPA.MPPK_REGISTRA_DENUNCIA_TURNO.CR_DELITOS,
	    PI_CR_DETENIDOS      IN SISMPA.MPPK_REGISTRA_DENUNCIA_TURNO.CR_DETENIDOS,
	    PI_V_CO_US_CREACION  IN SISMPA.MPTV_DENUNCIA.CO_V_US_CREACION%TYPE,
	    PO_V_ERR_COD         OUT VARCHAR2,
	    PO_V_ERR_MSG         OUT VARCHAR2
	) AS

	    V_N_ID_TIPO_COND_SUJETO  SISMPA.MPTV_SUJETO_CONDICION.ID_N_TIPO_COND_SUJETO%TYPE;
	    V_N_ID_TIPO_DOC_IDENT    SISCFE.CFTV_PERSONA.ID_N_TIPO_DOC_IDENT%TYPE;
	    V_V_NU_DOCUMENTO         SISCFE.CFTV_PERSONA.NU_V_DOCUMENTO%TYPE;
	    V_V_ID_DELITO_SUJETO     SISEFE.EFTV_DELITO_SUJETO.ID_V_DELITO_SUJETO%TYPE;

	    C_C_TIPO_SUJETO_CONDICION_DETENIDO CONSTANT NUMBER := 3;
	    C_C_TIPO_PARTE_SUJETO_DENUNCIADO   CONSTANT NUMBER := 2;

CURSOR CR_SUJETOS_CASO IS
SELECT ID_V_SUJETO_CASO, ID_V_PERSONA
FROM SISCFE.CFTV_SUJETO_CASO
WHERE ID_V_DENUNCIA = PI_V_ID_DENUNCIA
  AND ID_V_CASO = PI_V_ID_CASO
  AND ID_N_TIPO_PARTE_SUJETO IN ( C_C_TIPO_PARTE_SUJETO_DENUNCIADO )
  AND ID_V_PERSONA IS NOT NULL;

BEGIN
	    -- Registrar delitos en MPTV_DELITO_DENUNCIA
FOR I IN 1..PI_CR_DELITOS.COUNT LOOP
	        INSERT INTO SISMPA.MPTV_DELITO_DENUNCIA (
	            ID_V_DELITO_DENUNCIA,
	            ID_V_DENUNCIA,
	            ID_N_DELITO,
	            ID_N_SUBGENERICO,
	            ID_N_ESPECIFICO,
	            ID_N_TIPO_GRADO_DELITO,
	            FE_D_CREACION,
	            CO_V_US_CREACION,
	            ES_C_DELITO_DENUNCIA,
	            ID_N_ORIGEN_TURNO
	        ) VALUES (
	            SYS_GUID(),
	            PI_V_ID_DENUNCIA,
	            PI_CR_DELITOS(I).ID_N_DELITO,
	            PI_CR_DELITOS(I).ID_N_SUBGENERICO,
	            PI_CR_DELITOS(I).ID_N_ESPECIFICO,
	            PI_CR_DELITOS(I).ID_N_TIPO_GRADO_DELITO,
	            SYSDATE,
	            PI_V_CO_US_CREACION,
	            '1',
	            PI_CR_DELITOS(I).ID_N_ORIGEN_TURNO
	        );
END LOOP;

	    -- Procesar sujetos del caso
FOR SUJETO IN CR_SUJETOS_CASO LOOP

	        V_N_ID_TIPO_COND_SUJETO := NULL;

BEGIN
SELECT ID_N_TIPO_COND_SUJETO
INTO V_N_ID_TIPO_COND_SUJETO
FROM SISMPA.MPTV_SUJETO_CONDICION
WHERE ID_V_SUJETO_CASO = SUJETO.ID_V_SUJETO_CASO
  AND ES_C_SUJETO_CONDICION = '1'
    FETCH FIRST 1 ROWS ONLY;
EXCEPTION
	            WHEN NO_DATA_FOUND THEN
	                V_N_ID_TIPO_COND_SUJETO := NULL;
END;

	        -- Obtener documento
SELECT ID_N_TIPO_DOC_IDENT, NU_V_DOCUMENTO
INTO V_N_ID_TIPO_DOC_IDENT, V_V_NU_DOCUMENTO
FROM SISCFE.CFTV_PERSONA
WHERE ID_V_PERSONA = SUJETO.ID_V_PERSONA;

-- Buscar si sujeto está en lista de detenidos
FOR I IN 1..PI_CR_DETENIDOS.COUNT LOOP

	            IF PI_CR_DETENIDOS(I).ID_N_TIPO_DOC_IDENT = V_N_ID_TIPO_DOC_IDENT
	               AND PI_CR_DETENIDOS(I).NU_V_DOCUMENTO = V_V_NU_DOCUMENTO
	               AND PI_CR_DETENIDOS(I).ID_N_TIPO_PARTE_SUJETO = V_N_ID_TIPO_COND_SUJETO THEN

	                -- Asociar delitos del detenido
	                FOR J IN 1..PI_CR_DETENIDOS(I).CR_DELITOS_ASOCIADOS.COUNT LOOP

	                    V_V_ID_DELITO_SUJETO := MPFN_OBTENER_O_INSERTAR_DELITO_SUJETO(
	                        SUJETO.ID_V_SUJETO_CASO,
	                        PI_CR_DETENIDOS(I).CR_DELITOS_ASOCIADOS(J).ID_N_DELITO,
	                        PI_CR_DETENIDOS(I).CR_DELITOS_ASOCIADOS(J).ID_N_SUBGENERICO,
	                        PI_CR_DETENIDOS(I).CR_DELITOS_ASOCIADOS(J).ID_N_ESPECIFICO
	                    );

INSERT INTO SISCFE.CFTV_DELITO_DETENIDO (
    ID_V_DELITO_DETENIDO,
    ID_V_DELITO_SUJETO,
    ID_N_TIPO_GRADO_DELITO,
    ID_N_ORIGEN_TURNO,
    ES_C_DELITO_DETENIDO,
    CO_V_US_CREACION,
    FE_D_CREACION
) VALUES (
             SYS_GUID(),
             V_V_ID_DELITO_SUJETO,
             PI_CR_DETENIDOS(I).CR_DELITOS_ASOCIADOS(J).ID_N_TIPO_GRADO_DELITO,
             PI_CR_DETENIDOS(I).CR_DELITOS_ASOCIADOS(J).ID_N_ORIGEN_TURNO,
             '1',
             PI_V_CO_US_CREACION,
             SYSDATE
         );

END LOOP;

	                EXIT; -- ya procesado este sujeto
END IF;
END LOOP;

	        -- Si no se encontró en detenidos pero aún debe asociarse
	        IF V_N_ID_TIPO_COND_SUJETO = C_C_TIPO_SUJETO_CONDICION_DETENIDO THEN -- Detenido
	            FOR I IN 1..PI_CR_DELITOS.COUNT LOOP
	                V_V_ID_DELITO_SUJETO := MPFN_OBTENER_O_INSERTAR_DELITO_SUJETO(
	                    SUJETO.ID_V_SUJETO_CASO,
	                    PI_CR_DELITOS(I).ID_N_DELITO,
	                    PI_CR_DELITOS(I).ID_N_SUBGENERICO,
	                    PI_CR_DELITOS(I).ID_N_ESPECIFICO
	                );

INSERT INTO SISCFE.CFTV_DELITO_DETENIDO (
    ID_V_DELITO_DETENIDO,
    ID_V_DELITO_SUJETO,
    ID_N_TIPO_GRADO_DELITO,
    ID_N_ORIGEN_TURNO,
    ES_C_DELITO_DETENIDO,
    CO_V_US_CREACION,
    FE_D_CREACION
) VALUES (
             SYS_GUID(),
             V_V_ID_DELITO_SUJETO,
             PI_CR_DELITOS(I).ID_N_TIPO_GRADO_DELITO,
             PI_CR_DELITOS(I).ID_N_ORIGEN_TURNO,
             '1',
             PI_V_CO_US_CREACION,
             SYSDATE
         );
END LOOP;
END IF;
END LOOP;

	    PO_V_ERR_COD := V_V_COD_OK;
	    PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
	    WHEN OTHERS THEN
	        PO_V_ERR_COD := '1';
	        PO_V_ERR_MSG := 'REGISTRAR_DELITOS - ERROR : ' || SQLCODE || ' - ' || SQLERRM || ' - ' || dbms_utility.format_error_backtrace();
END MPSP_REGISTRAR_DELITOS;


   /******************************************************************************************
	* Nombre        : SISMPA.MPPK_DELITOS_TURNO.MPFN_OBTENER_O_INSERTAR_DELITO_SUJETO
	* Autor         : Pedro Yarleque Linares
	* Versión       : 1.0
	* Descripción   : Función auxiliar para obtener ID_V_DELITO_SUJETO o insertar si no existe
	* Fecha creación: 21.07.2025
	******************************************************************************************/

   FUNCTION MPFN_OBTENER_O_INSERTAR_DELITO_SUJETO(
        PI_V_ID_SUJETO_CASO    SISEFE.EFTV_DELITO_SUJETO.ID_V_SUJETO_CASO%TYPE,
        PI_N_ID_DELITO         SISEFE.EFTV_DELITO_SUJETO.ID_N_DELITO%TYPE,
        PI_N_ID_SUBGENERICO    SISEFE.EFTV_DELITO_SUJETO.ID_N_SUBGENERICO%TYPE,
        PI_N_ID_ESPECIFICO     SISEFE.EFTV_DELITO_SUJETO.ID_N_ESPECIFICO%TYPE
    ) RETURN VARCHAR2 IS
        V_ID_DELITO_SUJETO SISEFE.EFTV_DELITO_SUJETO.ID_V_DELITO_SUJETO%TYPE;
BEGIN
BEGIN
SELECT ID_V_DELITO_SUJETO
INTO V_ID_DELITO_SUJETO
FROM SISEFE.EFTV_DELITO_SUJETO
WHERE ID_V_SUJETO_CASO = PI_V_ID_SUJETO_CASO
  AND ID_N_DELITO = PI_N_ID_DELITO
  AND ID_N_SUBGENERICO = PI_N_ID_SUBGENERICO
  AND ID_N_ESPECIFICO = PI_N_ID_ESPECIFICO
  AND ES_C_DELITO_SUJETO = '1'
    FETCH FIRST 1 ROWS ONLY;
EXCEPTION
            WHEN NO_DATA_FOUND THEN
                INSERT INTO SISEFE.EFTV_DELITO_SUJETO (
                    ID_V_DELITO_SUJETO,
                    ID_V_SUJETO_CASO,
                    ID_N_DELITO,
                    ID_N_SUBGENERICO,
                    ID_N_ESPECIFICO,
                    FE_D_CREACION,
                    ES_C_DELITO_SUJETO
                ) VALUES (
                    SYS_GUID(),
                    PI_V_ID_SUJETO_CASO,
                    PI_N_ID_DELITO,
                    PI_N_ID_SUBGENERICO,
                    PI_N_ID_ESPECIFICO,
                    SYSDATE,
                    '1'
                ) RETURNING ID_V_DELITO_SUJETO INTO V_ID_DELITO_SUJETO;
END;
RETURN V_ID_DELITO_SUJETO;
END;


    /*********************************************************************
	* Nombre        : SISMPA.MPPK_DELITOS_PARTES_TURNO.MPSP_LISTAR_DELITOS
	* Autor         : Pedro Yarleque Linares
	* Versi?n       : 1.0
	* Descripci?n   : Listar los delitos de la denuncia en Mesa de Turno
	* Fecha creaci?n: 21.07.2025
	**********************************************************************/

    PROCEDURE MPSP_LISTAR_DELITOS(
	    PI_V_ID_CASO   IN  SISEFE.EFTV_CASO.ID_V_CASO%TYPE,
	    PO_CR_DELITOS  OUT SYS_REFCURSOR,
	    PO_V_ERR_COD   OUT VARCHAR2,
	    PO_V_ERR_MSG   OUT VARCHAR2
	) AS
BEGIN

OPEN PO_CR_DELITOS FOR
SELECT DISTINCT
    NVL(cde.DE_V_ARTICULO, '-') AS NU_V_ARTICULO,
    cd.NO_V_DELITO || ' / ' || cds.NO_V_SUBGENERICO || ' / ' || cde.NO_V_ESPECIFICO AS NO_V_DELITO
FROM SISEFE.EFTV_CASO ec
         JOIN SISMPA.MPTV_DENUNCIA md
              ON ec.ID_V_CASO = md.ID_V_CASO
         JOIN SISMPA.MPTV_DELITO_DENUNCIA mdd
              ON md.ID_V_DENUNCIA = mdd.ID_V_DENUNCIA
         JOIN SISMAEST.CFTM_DELITO cd
              ON mdd.ID_N_DELITO = cd.ID_N_DELITO
         JOIN SISMAEST.CFTM_DELITO_SUBGENERICO cds
              ON mdd.ID_N_SUBGENERICO = cds.ID_N_SUBGENERICO
         JOIN SISMAEST.CFTM_DELITO_ESPECIFICO cde
              ON mdd.ID_N_ESPECIFICO = cde.ID_N_ESPECIFICO
WHERE ec.ID_V_CASO = PI_V_ID_CASO
  AND mdd.ES_C_DELITO_DENUNCIA = '1'
ORDER BY NO_V_DELITO;

PO_V_ERR_COD := V_V_COD_OK;
	    PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
	    WHEN OTHERS THEN
	        PO_V_ERR_COD := '1';
	        PO_V_ERR_MSG := 'ERROR AL INTENTAR LISTAR LOS DELITOS DE UNA DENUNCIA: ' || SQLCODE || ' - ' || SQLERRM;
END MPSP_LISTAR_DELITOS;

END MPPK_DELITOS_TURNO;

/