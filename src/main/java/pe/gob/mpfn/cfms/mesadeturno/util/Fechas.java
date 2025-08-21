package pe.gob.mpfn.cfms.mesadeturno.util;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Fechas {

    /**
     * Convierte una fecha del formato "dd-MMM-yy" al formato "dd/MM/yyyy"
     * Ejemplo: "01-AUG-25" -> "01/08/2025"
     *
     * @param fechaOriginal String con la fecha en formato "dd-MMM-yy"
     * @return String con la fecha en formato "dd/MM/yyyy"
     * @throws ParseException si la fecha no puede ser parseada
     */
    public static String convertirFormatoFecha(String fechaOriginal) throws ParseException {
        if (fechaOriginal == null || fechaOriginal.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha no puede ser null o vacía");
        }

        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy");

        Date fecha = formatoEntrada.parse(fechaOriginal.trim());
        return formatoSalida.format(fecha);
    }


    /**
     * Convierte una fecha con hora del formato "dd/MM/yyyy hh:mm AM/PM" al formato "dd/MM/yyyy hh:mm hrs."
     * Ejemplo: "01/08/2025 10:42 AM" -> "01/08/2025 10:42 hrs."
     *
     * @param fechaHoraOriginal String con la fecha y hora en formato "dd/MM/yyyy hh:mm AM/PM"
     * @return String con la fecha y hora en formato "dd/MM/yyyy hh:mm hrs."
     * @throws ParseException si la fecha no puede ser parseada
     */
    public static String convertirFormatoFechaHora(String fechaHoraOriginal) throws ParseException {
        if (fechaHoraOriginal == null || fechaHoraOriginal.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha con hora no puede ser null o vacía");
        }

        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH);
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Date fechaHora = formatoEntrada.parse(fechaHoraOriginal.trim());
        return formatoSalida.format(fechaHora) + " hrs.";
    }


}
