CREATE OR REPLACE PACKAGE SISMPA.MPPK_DOCUMENTO_CASO_TURNO IS

	/*******************************************************************************
	* Nombre        : SISMPA.MPPK_DOCUMENTO_CASO_TURNO.MPSP_LISTAR_ACTAS_REGISTRADAS
	* Autor         : Pedro Yarleque Linares
	* Versi�n       : 1.0
	* Descripci�n   : Listar actas registradas en denuncia de Mesa de Turno.
	* Fecha creaci�n: 21.12.2023
	********************************************************************************/

	PROCEDURE MPSP_LISTAR_ACTAS_REGISTRADAS(
		PI_V_ID_CASO            IN SISEFE.EFTV_CASO.ID_V_CASO%TYPE,
		PO_CR_ACTAS_REGISTRADAS OUT SYS_REFCURSOR,
		PO_V_ERR_COD            OUT VARCHAR2,
		PO_V_ERR_MSG            OUT VARCHAR2
	);

	/*******************************************************************************
	* Nombre        : SISMPA.MPPK_DOCUMENTO_CASO_TURNO.MPSP_REGISTRAR_MOVIMIENTO
	* Autor         : Pedro Yarleque Linares
	* Versi�n       : 1.0
	* Descripci�n   : Registrar movimiento al registrar acta en Mesa de Turno.
	* Fecha creaci�n: 02.01.2024
	********************************************************************************/

	PROCEDURE MPSP_REGISTRAR_MOVIMIENTO(
		PI_V_ID_CASO            IN SISEFE.EFTV_MOVIMIENTO.ID_V_CASO%TYPE,
		PI_V_CO_US_CREACION     IN SISEFE.EFTV_MOVIMIENTO.CO_V_US_CREACION%TYPE,
		PO_V_ID_MOVIMIENTO      OUT VARCHAR2,
		PO_V_ERR_COD            OUT VARCHAR2,
		PO_V_ERR_MSG            OUT VARCHAR2
	);

END MPPK_DOCUMENTO_CASO_TURNO;

CREATE OR REPLACE PACKAGE BODY SISMPA.MPPK_DOCUMENTO_CASO_TURNO IS

	V_V_COD_OK VARCHAR2(40) := '0';
	V_V_MSG_OK VARCHAR2(200) := 'LA OPERACI�N SE REALIZ� SATISFACTORIAMENTE';

	V_V_COD_ERR VARCHAR2(40) := '0';
	V_V_MSG_ERR VARCHAR2(200) := '';

	EX_CONTROLADO EXCEPTION;

    /*******************************************************************************
	* Nombre        : SISMPA.MPPK_DOCUMENTO_CASO_TURNO.MPSP_LISTAR_ACTAS_REGISTRADAS
	* Autor         : Pedro Yarleque Linares
	* Versi�n       : 1.0
	* Descripci�n   : Listar actas registradas en denuncia de Mesa de Turno.
	* Fecha creaci�n: 21.12.2023
	********************************************************************************/

	PROCEDURE MPSP_LISTAR_ACTAS_REGISTRADAS(
		PI_V_ID_CASO            IN SISEFE.EFTV_CASO.ID_V_CASO%TYPE,
		PO_CR_ACTAS_REGISTRADAS OUT SYS_REFCURSOR,
		PO_V_ERR_COD            OUT VARCHAR2,
		PO_V_ERR_MSG            OUT VARCHAR2
	) AS

BEGIN

OPEN PO_CR_ACTAS_REGISTRADAS FOR
SELECT
    cd.ID_V_DOCUMENTO,
    cd.CO_V_DOCUMENTO,
    'pdf' NO_V_EXTENSION_ARCHIVO,
    tc.NO_V_DESCRIPCION NO_V_TIPO_COPIA
FROM SISCFE.CFTV_DOCUMENTO cd
         INNER JOIN SISMAEST.CFTM_CATALOGO tc
                    ON tc.ID_N_CATALOGO = cd.ID_N_TIPO_COPIA
                        AND tc.NO_V_GRUPO = 'ID_N_TIPO_COPIA'
WHERE cd.ID_V_CASO = PI_V_ID_CASO
  AND cd.ID_N_TIPO_ACTA IS NOT NULL
  AND cd.ID_N_TIPO_ACTA IN (341,342,343,344,345,346)
ORDER BY cd.FE_D_CREACION ASC;

PO_V_ERR_COD := V_V_COD_OK;
		PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
	    WHEN OTHERS THEN
	        PO_V_ERR_COD := '1';
	        PO_V_ERR_MSG := 'ERROR AL INTENTAR LISTAR LAS ACTAS REGISTRADAS: ' || to_char(SQLCODE) || ' - ' ||SQLERRM;
END;

	/*******************************************************************************
	* Nombre        : SISMPA.MPPK_DOCUMENTO_CASO_TURNO.MPSP_REGISTRAR_MOVIMIENTO
	* Autor         : Pedro Yarleque Linares
	* Versi�n       : 1.0
	* Descripci�n   : Registrar movimiento al registrar acta en Mesa de Turno.
	* Fecha creaci�n: 02.01.2024
	********************************************************************************/

	PROCEDURE MPSP_REGISTRAR_MOVIMIENTO(
		PI_V_ID_CASO            IN SISEFE.EFTV_MOVIMIENTO.ID_V_CASO%TYPE,
		PI_V_CO_US_CREACION     IN SISEFE.EFTV_MOVIMIENTO.CO_V_US_CREACION%TYPE,
		PO_V_ID_MOVIMIENTO      OUT VARCHAR2,
		PO_V_ERR_COD            OUT VARCHAR2,
		PO_V_ERR_MSG            OUT VARCHAR2
	) AS

		V_N_CONTADOR NUMBER;

BEGIN

		-- Validar si el caso existe

SELECT COUNT(1)
INTO V_N_CONTADOR
FROM SISEFE.EFTV_CASO ec
WHERE ec.ID_V_CASO = PI_V_ID_CASO
  AND ec.ES_C_CASO = '1';

IF V_N_CONTADOR = 0 THEN
			PO_V_ERR_COD := '42202004';
			SISCFE.CFPK_MENSAJE_VALIDACION.CFSP_OBTENER_MENSAJE_VALIDACION(PO_V_ERR_COD, PO_V_ERR_MSG, V_V_COD_ERR, V_V_MSG_ERR);
		    RAISE EX_CONTROLADO;
END IF;

		-- Registrar movimiento de acta

INSERT INTO SISEFE.EFTV_MOVIMIENTO (
    ID_V_CASO,
    FE_D_CREACION,
    CO_V_US_CREACION,
    ES_C_MOVIMIENTO,
    FL_C_NIVEL,
    ID_N_ACCION_ESTADO
) VALUES (
             PI_V_ID_CASO,
             SYSDATE,
             PI_V_CO_US_CREACION,
             '1',
             'D',
             43 -- Registrar acta
         )
    RETURNING ID_V_MOVIMIENTO INTO PO_V_ID_MOVIMIENTO;

PO_V_ERR_COD := V_V_COD_OK;
		PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
		WHEN EX_CONTROLADO THEN
	        DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : '|| PO_V_ERR_COD || ' - ' || PO_V_ERR_MSG);
WHEN OTHERS THEN
	        PO_V_ERR_COD := '1';
	        PO_V_ERR_MSG := 'ERROR AL INTENTAR LISTAR LAS ACTAS REGISTRADAS: ' || to_char(SQLCODE) || ' - ' ||SQLERRM;
END;

END MPPK_DOCUMENTO_CASO_TURNO;