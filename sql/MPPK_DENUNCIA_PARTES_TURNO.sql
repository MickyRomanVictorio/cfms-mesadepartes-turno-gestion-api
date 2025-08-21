CREATE OR REPLACE PACKAGE SISMPA.MPPK_DENUNCIA_PARTES_TURNO
AS

/**********************************************************************************************
* Nombre        : SISMPA.MPPK_DENUNCIA_PARTES_TURNO.MPSP_LISTAR_TIPOS_PARTE_SUJETO
* Autor         : Manuel Cruz Daza
* Versi?n       : 1.0
* Descripci?n   : Listar tipos parte
* Fecha creaci?n: 29.11.2023
***********************************************************************************************/
PROCEDURE MPSP_LISTAR_TIPOS_PARTE_SUJETO(
    PO_CR_TIPO_PARTES   OUT SYS_REFCURSOR,
    PO_V_ERR_COD        OUT VARCHAR2,
	PO_V_ERR_MSG        OUT VARCHAR2
);

/**********************************************************************************************
* Nombre        : SISMPA.MPPK_DENUNCIA_PARTES_TURNO.MPSP_LISTAR_PERSONA_TIPO_PARTE_SUJETO
* Autor         : Manuel Cruz Daza
* Versi?n       : 1.0
* Descripci?n   : Listar los tipos persona de acuerdo al tipo parte sujeto
* Fecha creaci?n: 29.11.2023
***********************************************************************************************/
PROCEDURE MPSP_LISTAR_PERSONA_TIPO_PARTE_SUJETO(
    PI_N_ID_TIPO_PARTE_SUJETO  IN NUMBER,
    PO_CR_TIPO_PARTES       OUT SYS_REFCURSOR,
    PO_V_ERR_COD            OUT VARCHAR2,
	PO_V_ERR_MSG            OUT VARCHAR2
);


END MPPK_DENUNCIA_PARTES_TURNO;

CREATE OR REPLACE PACKAGE BODY SISMPA.MPPK_DENUNCIA_PARTES_TURNO
AS

/**********************************************************************************************
* Nombre        : SISMPA.MPPK_DENUNCIA_PARTES_TURNO.MPSP_LISTAR_TIPOS_PARTE_SUJETO
* Autor         : Manuel Cruz Daza
* Versi?n       : 1.0
* Descripci?n   : Listar tipos parte
* Fecha creaci?n: 29.11.2023
***********************************************************************************************/
PROCEDURE MPSP_LISTAR_TIPOS_PARTE_SUJETO(
    PO_CR_TIPO_PARTES   OUT SYS_REFCURSOR,
    PO_V_ERR_COD        OUT VARCHAR2,
	PO_V_ERR_MSG        OUT VARCHAR2
)
AS
BEGIN
OPEN PO_CR_TIPO_PARTES FOR
SELECT TPS.ID_N_TIPO_PARTE_SUJETO  ,
       TPS.NO_V_TIPO_PARTE_SUJETO
FROM SISMAEST.CFTM_PARTE_SUJETO_ORIGEN O
         JOIN SISMAEST.CFTM_TIPO_PARTE_SUJETO TPS
              ON TPS.ID_N_TIPO_PARTE_SUJETO = O.ID_N_TIPO_PARTE_SUJETO
         JOIN SISMAEST.CFTM_TIPO_ORIGEN TOR
              ON O.ID_N_TIPO_ORIGEN = TOR.ID_N_TIPO_ORIGEN
WHERE O.ES_C_PARTE_SUJETO_ORIGEN = '1' AND
    TPS.ES_C_TIPO_PARTE_SUJETO = '1' AND
    TOR.ES_C_TIPO_ORIGEN = '1' AND
    O.ID_N_TIPO_ORIGEN = 4
ORDER BY 1;

PO_V_ERR_COD:='0';
    PO_V_ERR_MSG:='';

EXCEPTION
    WHEN OTHERS THEN
        PO_V_ERR_COD := '1';
        PO_V_ERR_MSG := 'ERROR AL INTENTAR LISTAR LOS TIPOS DE PARTES: ' || to_char(SQLCODE) || ' - ' ||SQLERRM;
END;

/**********************************************************************************************
* Nombre        : SISMPA.MPPK_DENUNCIA_PARTES_TURNO.MPSP_LISTAR_PERSONA_TIPO_PARTE_SUJETO
* Autor         : Manuel Cruz Daza
* Versi?n       : 1.0
* Descripci?n   : Listar los tipos persona de acuerdo al tipo parte sujeto
* Fecha creaci?n: 29.11.2023
***********************************************************************************************/
PROCEDURE MPSP_LISTAR_PERSONA_TIPO_PARTE_SUJETO(
    PI_N_ID_TIPO_PARTE_SUJETO  IN NUMBER,
    PO_CR_TIPO_PARTES       OUT SYS_REFCURSOR,
    PO_V_ERR_COD            OUT VARCHAR2,
	PO_V_ERR_MSG            OUT VARCHAR2
)
AS
BEGIN
OPEN PO_CR_TIPO_PARTES FOR
SELECT PERPARSUJ.ID_N_PERSONA_PARTE_SUJETO,
       TIPER.NO_V_TIPO_PERSONA
FROM SISMAEST.CFTM_PERSONA_PARTE_SUJETO PERPARSUJ
         JOIN SISMAEST.CFTM_TIPO_PERSONA TIPER
              ON PERPARSUJ.ID_N_TIPO_PERSONA = TIPER.ID_N_TIPO_PERSONA
WHERE PERPARSUJ.ID_N_TIPO_PARTE_SUJETO = PI_N_ID_TIPO_PARTE_SUJETO
ORDER BY PERPARSUJ.ID_N_TIPO_PARTE_SUJETO;
PO_V_ERR_COD:='0';
    PO_V_ERR_MSG:='';

EXCEPTION
    WHEN OTHERS THEN
        PO_V_ERR_COD := '1';
        PO_V_ERR_MSG := 'ERROR AL INTENTAR LISTAR LOS TIPOS DE PARTES: ' || to_char(SQLCODE) || ' - ' ||SQLERRM;

END;

END MPPK_DENUNCIA_PARTES_TURNO;