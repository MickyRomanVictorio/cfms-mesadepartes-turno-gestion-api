CREATE OR REPLACE PACKAGE SISMPA.MPPK_DELITOS_PARTES_TURNO AS

	/*********************************************************************
	* Nombre        : SISMPA.MPPK_DELITOS_PARTES_TURNO.MPSP_LISTAR_DELITOS
	* Autor         : Pedro Yarleque Linares
	* Versi?n       : 1.0
	* Descripci?n   : Listar los delitos de la denuncia en Mesa de Turno
	* Fecha creaci?n: 29.11.2023
	**********************************************************************/

	PROCEDURE MPSP_LISTAR_DELITOS(
	              PI_V_ID_CASO  IN SISEFE.EFTV_CASO.ID_V_CASO%TYPE,
	              PO_CR_DELITOS OUT SYS_REFCURSOR,
			      PO_V_ERR_COD  OUT VARCHAR2,
				  PO_V_ERR_MSG  OUT VARCHAR2
	          );

	/********************************************************************************
	* Nombre        : SISMPA.MPPK_DELITOS_PARTES_TURNO.MPSP_LISTAR_PERSONAS_NATURALES
	* Autor         : Pedro Yarleque Linares
	* Versi?n       : 1.0
	* Descripci?n   : Listar las partes involucradas de tipo Persona Natural de una
	*                 denuncia en Mesa de Turno.
	* Fecha creaci?n: 29.11.2023
	********************************************************************************/

	PROCEDURE MPSP_LISTAR_PERSONAS_NATURALES(
	              PI_V_ID_CASO             IN SISEFE.EFTV_CASO.ID_V_CASO%TYPE,
	              PO_CR_PERSONAS_NATURALES OUT SYS_REFCURSOR,
			      PO_V_ERR_COD             OUT VARCHAR2,
				  PO_V_ERR_MSG             OUT VARCHAR2
	          );

	/********************************************************************************
	* Nombre        : SISMPA.MPPK_DELITOS_PARTES_TURNO.MPSP_LISTAR_PERSONAS_JURIDICAS
	* Autor         : Pedro Yarleque Linares
	* Versi?n       : 1.0
	* Descripci?n   : Listar las partes involucradas de tipo Persona Juridica de una
	*                 denuncia en Mesa de Turno.
	* Fecha creaci?n: 29.11.2023
	*********************************************************************************/

	PROCEDURE MPSP_LISTAR_PERSONAS_JURIDICAS(
	              PI_V_ID_CASO             IN SISEFE.EFTV_CASO.ID_V_CASO%TYPE,
	              PO_CR_PERSONAS_JURIDICAS OUT SYS_REFCURSOR,
			      PO_V_ERR_COD             OUT VARCHAR2,
				  PO_V_ERR_MSG             OUT VARCHAR2
	          );

	/***************************************************************************
	* Nombre        : SISMPA.MPPK_DENUNCIA_TURNO.MPSP_IMPUTADOS_RENADESPPLE
	* Autor         : Pedro Yarleque Linares
	* Versi?n       : 1.0
	* Descripci?n   : Lista los imputados de un caso en la opci?n de Renadespple
	* Fecha creaci?n: 30.11.2023
	****************************************************************************/

	PROCEDURE MPSP_IMPUTADOS_RENADESPPLE(
	              PI_V_ID_CASO      IN SISEFE.EFTV_CASO.ID_V_CASO%TYPE,
	              PO_CR_IMPUTADOS   OUT SYS_REFCURSOR,
			      PO_V_ERR_COD      OUT VARCHAR2,
				  PO_V_ERR_MSG      OUT VARCHAR2
	          );

END MPPK_DELITOS_PARTES_TURNO;

CREATE OR REPLACE PACKAGE BODY SISMPA.MPPK_DELITOS_PARTES_TURNO AS

	V_V_COD_OK VARCHAR2(1) := '0';
    V_V_MSG_OK VARCHAR2(100) := 'LA OPERACI?N SE REALIZ? SATISFACTORIAMENTE';

	EX_CONTROLADO EXCEPTION;

	/*********************************************************************
	* Nombre        : SISMPA.MPPK_DELITOS_PARTES_TURNO.MPSP_LISTAR_DELITOS
	* Autor         : Pedro Yarleque Linares
	* Versi?n       : 1.0
	* Descripci?n   : Listar los delitos de la denuncia en Mesa de Turno
	* Fecha creaci?n: 29.11.2023
	**********************************************************************/

	PROCEDURE MPSP_LISTAR_DELITOS(
	              PI_V_ID_CASO  IN SISEFE.EFTV_CASO.ID_V_CASO%TYPE,
	              PO_CR_DELITOS OUT SYS_REFCURSOR,
			      PO_V_ERR_COD  OUT VARCHAR2,
				  PO_V_ERR_MSG  OUT VARCHAR2
	          ) AS
BEGIN

OPEN PO_CR_DELITOS FOR
--		    SELECT
--		    	'189' NU_V_ARTICULO,
--		    	'CONTRA EL PATRIMONIO / ROBO / ROBO AGRAVADO (A MANO ARMADA)' NO_V_DELITO
--		    FROM DUAL
--		    UNION ALL
SELECT DISTINCT
    NVL(cde.NU_V_ARTICULO,'-') NU_V_ARTICULO,
    (cd.NO_V_DELITO || ' / ' || cds.NO_V_SUBGENERICO || ' / ' || cde.NO_V_ESPECIFICO) NO_V_DELITO
FROM SISEFE.EFTV_CASO ec
         INNER JOIN SISMPA.MPTV_DENUNCIA md
                    ON ec.ID_V_CASO = md.ID_V_CASO
         INNER JOIN SISMPA.MPTV_DELITO_DENUNCIA mdd
                    ON md.ID_V_DENUNCIA = mdd.ID_V_DENUNCIA
         INNER JOIN SISMAEST.CFTM_DELITO cd
                    ON mdd.ID_N_DELITO = cd.ID_N_DELITO
         INNER JOIN SISMAEST.CFTM_DELITO_SUBGENERICO cds
                    ON mdd.ID_N_SUBGENERICO = cds.ID_N_SUBGENERICO
         INNER JOIN SISMAEST.CFTM_DELITO_ESPECIFICO cde
                    ON mdd.ID_N_ESPECIFICO = cde.ID_N_ESPECIFICO
WHERE ec.ID_V_CASO = PI_V_ID_CASO
  AND mdd.ES_C_DELITO_DENUNCIA = '1'
ORDER BY NO_V_DELITO;

PO_V_ERR_COD := V_V_COD_OK;
		PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
		WHEN EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : '|| PO_V_ERR_COD || ' - ' || PO_V_ERR_MSG);
WHEN OTHERS THEN
	        PO_V_ERR_COD := '1';
	        PO_V_ERR_MSG := 'ERROR AL INTENTAR LISTAR LOS DELITOS DE UNA DENUNCIA: ' || to_char(SQLCODE) || ' - ' ||SQLERRM;
END;

	/********************************************************************************
	* Nombre        : SISMPA.MPPK_DELITOS_PARTES_TURNO.MPSP_LISTAR_PERSONAS_NATURALES
	* Autor         : Pedro Yarleque Linares
	* Versi?n       : 1.0
	* Descripci?n   : Listar las partes involucradas de tipo Persona Natural de una
	*                 denuncia en Mesa de Turno.
	* Fecha creaci?n: 29.11.2023
	********************************************************************************/

	PROCEDURE MPSP_LISTAR_PERSONAS_NATURALES(
	              PI_V_ID_CASO             IN SISEFE.EFTV_CASO.ID_V_CASO%TYPE,
	              PO_CR_PERSONAS_NATURALES OUT SYS_REFCURSOR,
			      PO_V_ERR_COD             OUT VARCHAR2,
				  PO_V_ERR_MSG             OUT VARCHAR2
	          ) AS
BEGIN

OPEN PO_CR_PERSONAS_NATURALES FOR
--		    SELECT
--		    	'DENUNCIANTE' NO_V_TIPO_PARTE_SUJETO,
--		    	'DNI' NO_V_TIPO_DOC_IDENT,
--		    	'40752166' NU_V_DOCUMENTO,
--		    	'COELLO' AP_V_PATERNO,
--		    	'SOLANO' AP_V_MATERNO,
--		    	'ELOY IVAN' NO_V_CIUDADANO,
--		    	'M' TI_C_SEXO,
--		    	32 AS NU_N_EDAD,
--		    	'eisc@gmail.com' NO_V_CORREO,
--		    	'912345678' NO_V_CELULAR
--		    FROM DUAL
--		    UNION ALL
SELECT
    ctps.NO_V_TIPO_PARTE_SUJETO,
    ctdi.NO_V_TIPO_DOC_IDENT,
    cp.NU_V_DOCUMENTO,
    cscd.AP_V_PATERNO,
    cscd.AP_V_MATERNO,
    cscd.NO_V_CIUDADANO,
    DECODE(cscd.TI_C_SEXO, '1', 'M', '2', 'F', '-') TI_C_SEXO,
    cpn.NU_N_EDAD,
    (
        SELECT con.NO_V_DATOS_CONTACTO
        FROM SISCFE.CFTV_SUJETO_CONTACTO con
                 INNER JOIN SISMAEST.MPTM_TIPO_CONTACTO mtc
                            ON con.ID_N_TIPO_CONTACTO = mtc.ID_N_TIPO_CONTACTO
        WHERE con.ID_V_SUJETO_CASO = csc.ID_V_SUJETO_CASO
          AND con.ID_N_TIPO_CONTACTO = 1
          AND con.FL_C_CONTACTO_SECUNDARIO = '0'
          AND con.ES_C_SUJETO_CONTACTO = '1'
    ) NO_V_CORREO,
    (
        SELECT con.NO_V_DATOS_CONTACTO
        FROM SISCFE.CFTV_SUJETO_CONTACTO con
                 INNER JOIN SISMAEST.MPTM_TIPO_CONTACTO mtc
                            ON con.ID_N_TIPO_CONTACTO = mtc.ID_N_TIPO_CONTACTO
        WHERE con.ID_V_SUJETO_CASO = csc.ID_V_SUJETO_CASO
          AND con.ID_N_TIPO_CONTACTO = 2
          AND con.FL_C_CONTACTO_SECUNDARIO = '0'
          AND con.ES_C_SUJETO_CONTACTO = '1'
    ) NO_V_CELULAR
FROM SISCFE.CFTV_SUJETO_CASO csc
         INNER JOIN SISCFE.CFTV_SUJETO_CASO_DETALLE cscd
                    ON csc.ID_V_SUJETO_CASO = cscd.ID_V_SUJETO_CASO
         INNER JOIN SISMAEST.CFTM_TIPO_PARTE_SUJETO ctps
                    ON csc.ID_N_TIPO_PARTE_SUJETO = ctps.ID_N_TIPO_PARTE_SUJETO
         INNER JOIN SISCFE.CFTV_PERSONA cp
                    ON csc.ID_V_PERSONA = cp.ID_V_PERSONA
         INNER JOIN SISMAEST.CFTM_TIPO_DOC_IDENT ctdi
                    ON cp.ID_N_TIPO_DOC_IDENT = ctdi.ID_N_TIPO_DOC_IDENT
         INNER JOIN SISCFE.CFTV_PERSONA_NATURAL cpn
                    ON cpn.ID_V_PERSONA = cp.ID_V_PERSONA
WHERE csc.ID_V_CASO = PI_V_ID_CASO
  AND cp.ID_N_TIPO_DOC_IDENT <> 2
  AND csc.ES_C_SUJETO_CASO = '1'
ORDER BY ctps.NO_V_TIPO_PARTE_SUJETO, cscd.AP_V_PATERNO, cscd.AP_V_MATERNO, cscd.NO_V_CIUDADANO;

PO_V_ERR_COD := V_V_COD_OK;
		PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
		WHEN EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : '|| PO_V_ERR_COD || ' - ' || PO_V_ERR_MSG);
WHEN OTHERS THEN
	        PO_V_ERR_COD := '1';
	        PO_V_ERR_MSG := 'ERROR AL INTENTAR LISTAR LAS PERSONAS NATURALES DE UNA DENUNCIA: ' || to_char(SQLCODE) || ' - ' ||SQLERRM;
END;

	/********************************************************************************
	* Nombre        : SISMPA.MPPK_DELITOS_PARTES_TURNO.MPSP_LISTAR_PERSONAS_JURIDICAS
	* Autor         : Pedro Yarleque Linares
	* Versi?n       : 1.0
	* Descripci?n   : Listar las partes involucradas de tipo Persona Juridica de una
	*                 denuncia en Mesa de Turno.
	* Fecha creaci?n: 29.11.2023
	*********************************************************************************/

	PROCEDURE MPSP_LISTAR_PERSONAS_JURIDICAS(
	              PI_V_ID_CASO             IN SISEFE.EFTV_CASO.ID_V_CASO%TYPE,
	              PO_CR_PERSONAS_JURIDICAS OUT SYS_REFCURSOR,
			      PO_V_ERR_COD             OUT VARCHAR2,
				  PO_V_ERR_MSG             OUT VARCHAR2
	          ) AS
BEGIN

OPEN PO_CR_PERSONAS_JURIDICAS FOR
--	    	SELECT
--		    	'DENUNCIANTE' NO_V_TIPO_PARTE_SUJETO,
--		    	'RUC' NO_V_TIPO_DOC_IDENT,
--		    	'10727507626' NU_V_DOCUMENTO,
--		    	'TRANSPORTES EUROTRUCK SIMULATOR S.A.C' DE_V_RAZON_SOCIAL,
--		    	'eisc@gmail.com' NO_V_CORREO,
--		    	'912345678' NO_V_CELULAR
--		    FROM DUAL
--		    UNION ALL
SELECT
    NO_V_TIPO_PARTE_SUJETO,
    NO_V_TIPO_DOC_IDENT,
    NU_V_DOCUMENTO,
    DE_V_RAZON_SOCIAL,
    NO_V_CORREO,
    NO_V_CELULAR
FROM (
         SELECT
             ctps.NO_V_TIPO_PARTE_SUJETO,
             ctdi.NO_V_TIPO_DOC_IDENT,
             cp.NU_V_DOCUMENTO,
             cpj.DE_V_RAZON_SOCIAL,
             (
                 SELECT con.NO_V_DATOS_CONTACTO
                 FROM SISCFE.CFTV_SUJETO_CONTACTO con
                          INNER JOIN SISMAEST.MPTM_TIPO_CONTACTO mtc
                                     ON con.ID_N_TIPO_CONTACTO = mtc.ID_N_TIPO_CONTACTO
                 WHERE con.ID_V_SUJETO_CASO = csc.ID_V_SUJETO_CASO
                   AND con.ID_N_TIPO_CONTACTO = 1
                   AND con.FL_C_CONTACTO_SECUNDARIO = '0'
                   AND con.ES_C_SUJETO_CONTACTO = '1'
             ) NO_V_CORREO,
             (
                 SELECT con.NO_V_DATOS_CONTACTO
                 FROM SISCFE.CFTV_SUJETO_CONTACTO con
                          INNER JOIN SISMAEST.MPTM_TIPO_CONTACTO mtc
                                     ON con.ID_N_TIPO_CONTACTO = mtc.ID_N_TIPO_CONTACTO
                 WHERE con.ID_V_SUJETO_CASO = csc.ID_V_SUJETO_CASO
                   AND con.ID_N_TIPO_CONTACTO = 2
                   AND con.FL_C_CONTACTO_SECUNDARIO = '0'
                   AND con.ES_C_SUJETO_CONTACTO = '1'
             ) NO_V_CELULAR
         FROM SISCFE.CFTV_SUJETO_CASO csc
                  INNER JOIN SISMAEST.CFTM_TIPO_PARTE_SUJETO ctps
                             ON csc.ID_N_TIPO_PARTE_SUJETO = ctps.ID_N_TIPO_PARTE_SUJETO
                  INNER JOIN SISCFE.CFTV_PERSONA cp
                             ON csc.ID_V_PERSONA = cp.ID_V_PERSONA
                  INNER JOIN SISMAEST.CFTM_TIPO_DOC_IDENT ctdi
                             ON cp.ID_N_TIPO_DOC_IDENT = ctdi.ID_N_TIPO_DOC_IDENT
                  INNER JOIN SISCFE.CFTV_PERSONA_JURIDICA cpj
                             ON cpj.ID_V_PERSONA = cp.ID_V_PERSONA
         WHERE csc.ID_V_CASO = PI_V_ID_CASO
           AND cp.ID_N_TIPO_DOC_IDENT = 2
           AND csc.ES_C_SUJETO_CASO = '1'
         UNION ALL
         SELECT
             ctps.NO_V_TIPO_PARTE_SUJETO,
             '-' NO_V_TIPO_DOC_IDENT,
             '-' NU_V_DOCUMENTO,
             (CASE WHEN csc.FL_C_AGRAVIADO = 'E' THEN 'EL ESTADO'
                   WHEN csc.FL_C_AGRAVIADO = 'S' THEN 'LA SOCIEDAD'
                   ELSE '-'
                 END) DE_V_RAZON_SOCIAL,
             (
                 SELECT con.NO_V_DATOS_CONTACTO
                 FROM SISCFE.CFTV_SUJETO_CONTACTO con
                          INNER JOIN SISMAEST.MPTM_TIPO_CONTACTO mtc
                                     ON con.ID_N_TIPO_CONTACTO = mtc.ID_N_TIPO_CONTACTO
                 WHERE con.ID_V_SUJETO_CASO = csc.ID_V_SUJETO_CASO
                   AND con.ID_N_TIPO_CONTACTO = 1
                   AND con.FL_C_CONTACTO_SECUNDARIO = '0'
                   AND con.ES_C_SUJETO_CONTACTO = '1'
             ) NO_V_CORREO,
             (
                 SELECT con.NO_V_DATOS_CONTACTO
                 FROM SISCFE.CFTV_SUJETO_CONTACTO con
                          INNER JOIN SISMAEST.MPTM_TIPO_CONTACTO mtc
                                     ON con.ID_N_TIPO_CONTACTO = mtc.ID_N_TIPO_CONTACTO
                 WHERE con.ID_V_SUJETO_CASO = csc.ID_V_SUJETO_CASO
                   AND con.ID_N_TIPO_CONTACTO = 2
                   AND con.FL_C_CONTACTO_SECUNDARIO = '0'
                   AND con.ES_C_SUJETO_CONTACTO = '1'
             ) NO_V_CELULAR
         FROM SISCFE.CFTV_SUJETO_CASO csc
                  INNER JOIN SISMAEST.CFTM_TIPO_PARTE_SUJETO ctps
                             ON csc.ID_N_TIPO_PARTE_SUJETO = ctps.ID_N_TIPO_PARTE_SUJETO
         WHERE csc.ID_V_CASO = PI_V_ID_CASO
           AND csc.FL_C_AGRAVIADO IN ('E','S')
           AND csc.ES_C_SUJETO_CASO = '1'
     ) ORDER BY NO_V_TIPO_PARTE_SUJETO, DE_V_RAZON_SOCIAL;


PO_V_ERR_COD := V_V_COD_OK;
		PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
		WHEN EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : '|| PO_V_ERR_COD || ' - ' || PO_V_ERR_MSG);
WHEN OTHERS THEN
	        PO_V_ERR_COD := '1';
	        PO_V_ERR_MSG := 'ERROR AL INTENTAR LISTAR LAS PERSONAS JURIDICAS DE UNA DENUNCIA: ' || to_char(SQLCODE) || ' - ' ||SQLERRM;
END;

	/***************************************************************************
	* Nombre        : SISMPA.MPPK_DENUNCIA_TURNO.MPSP_IMPUTADOS_RENADESPPLE
	* Autor         : Pedro Yarleque Linares
	* Versi?n       : 1.0
	* Descripci?n   : Lista los imputados de un caso en la opci?n de Renadespple
	* Fecha creaci?n: 30.11.2023
	****************************************************************************/

	PROCEDURE MPSP_IMPUTADOS_RENADESPPLE(
	              PI_V_ID_CASO      IN SISEFE.EFTV_CASO.ID_V_CASO%TYPE,
	              PO_CR_IMPUTADOS   OUT SYS_REFCURSOR,
			      PO_V_ERR_COD      OUT VARCHAR2,
				  PO_V_ERR_MSG      OUT VARCHAR2
	          ) AS
BEGIN

OPEN PO_CR_IMPUTADOS FOR

SELECT
    csc.ID_V_SUJETO_CASO,
    --'07C386582A2B8285E0650250569D508A' AS ID_V_SUJETO_CASO,
    ctps.NO_V_TIPO_PARTE_SUJETO,
    ctcs.NO_N_TIPO_COND_SUJETO,
    ctdi.NO_V_TIPO_DOC_IDENT,
    cp.NU_V_DOCUMENTO,
    --'40291777' AS NU_V_DOCUMENTO,
    TRIM(cpn.NO_V_CIUDADANO || ' ' || cpn.AP_V_PATERNO || ' ' || NVL(cpn.AP_V_MATERNO, ' ')) NO_V_IMPUTADO,
    cc.NO_V_DESCRIPCION NO_V_NACIONALIDAD,
    DECODE(mfr.FL_C_AVANCE,null,'0%','0','0%','1','25%','2','50%','3','75%','4','100%') FL_C_AVANCE
FROM SISMPA.MPTV_DENUNCIA md
         INNER JOIN SISEFE.EFTV_CASO ec
                    ON md.ID_V_CASO = ec.ID_V_CASO
         INNER JOIN SISCFE.CFTV_SUJETO_CASO csc
                    ON csc.ID_V_CASO = ec.ID_V_CASO
                        AND csc.ID_V_DENUNCIA = md.ID_V_DENUNCIA
         LEFT JOIN SISMPA.MPTX_FICHA_RENADESPPLE mfr
                   ON mfr.ID_V_SUJETO_CASO = csc.ID_V_SUJETO_CASO
                       AND mfr.ID_V_CASO = csc.ID_V_CASO
         INNER JOIN SISMAEST.CFTM_TIPO_PARTE_SUJETO ctps
                    ON csc.ID_N_TIPO_PARTE_SUJETO = ctps.ID_N_TIPO_PARTE_SUJETO
         INNER JOIN SISMPA.MPTV_SUJETO_CONDICION msc
                    ON msc.ID_V_SUJETO_CASO = csc.ID_V_SUJETO_CASO
         INNER JOIN SISMAEST.CFTM_TIPO_COND_SUJETO ctcs
                    ON ctcs.ID_N_TIPO_COND_SUJETO  = msc.ID_N_TIPO_COND_SUJETO
         INNER JOIN SISCFE.CFTV_PERSONA cp
                    ON csc.ID_V_PERSONA = cp.ID_V_PERSONA
         INNER JOIN SISMAEST.CFTM_TIPO_DOC_IDENT ctdi
                    ON cp.ID_N_TIPO_DOC_IDENT = ctdi.ID_N_TIPO_DOC_IDENT
         INNER JOIN SISCFE.CFTV_PERSONA_NATURAL cpn
                    ON cpn.ID_V_PERSONA = cp.ID_V_PERSONA
         INNER JOIN SISMAEST.CFTM_CATALOGO cc
                    ON cc.NO_V_GRUPO = 'ID_N_NACIONALIDAD'
                        AND cc.ID_N_CATALOGO = cpn.ID_N_NACIONALIDAD
WHERE csc.ID_N_TIPO_PARTE_SUJETO = 4
  AND ec.ID_V_CASO = PI_V_ID_CASO; -- Imputado

PO_V_ERR_COD := V_V_COD_OK;
		PO_V_ERR_MSG := V_V_MSG_OK;

EXCEPTION
		WHEN EX_CONTROLADO THEN
            DBMS_OUTPUT.PUT_LINE('ERROR CONTROLADO : '|| PO_V_ERR_COD || ' - ' || PO_V_ERR_MSG);
WHEN OTHERS THEN
	        PO_V_ERR_COD := '1';
	        PO_V_ERR_MSG := 'ERROR AL INTENTAR LISTAR LOS IMPUTADOS RENADESPPLE: ' || to_char(SQLCODE) || ' - ' ||SQLERRM;
END;


END MPPK_DELITOS_PARTES_TURNO;