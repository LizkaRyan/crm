package site.easy.to.build.crm.util.csv;

import site.easy.to.build.crm.util.csv.exception.CSVException;
import site.easy.to.build.crm.util.csv.parameter.CellCSV;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ConstraintCSV {

    public static final LongPositive LONG_POSITIVE = new LongPositive();

    public static final IntPositive INT_POSITIVE = new IntPositive();

    public static final DoublePositive DOUBLE_POSITIVE = new DoublePositive();

    public static final LocalDateConstraint LOCALDATE = new LocalDateConstraint();

    public static final ListForeign LIST_FOREIGN = new ListForeign();

    public static final TimeConstraint LOCAL_TIME = new TimeConstraint();

    public static final LocalDateTimeConstraint LOCALDATE_TIME = new LocalDateTimeConstraint();

    public static final StatusConstraint STATUS_CONSTRAINT = new StatusConstraint();

    public static final TypeConstraint TYPE_CONSTRAINT = new TypeConstraint();

    private static class LongPositive implements CellCSV<Long> {

        @Override
        public Long getValue(String cell, int line) throws CSVException {
            try {
                Long value = Long.parseLong(cell);
                if (value < 0) {
                    throw new CSVException("Valeur négative sur la ligne " + line);
                }
                return value;
            } catch (NumberFormatException ex) {
                throw new CSVException(ex.getMessage() + " sur la ligne " + line);
            }
        }
    }

    private static class StatusConstraint implements CellCSV<String> {

        private static List<String> statusPossible;

        static {
            statusPossible = Arrays.asList("meeting-to-schedule", "archived", "success", "open", "assigned",
                    "on-hold","in-progress", "resolved", "closed", "reopened", "pending-customer-response",
                    "escalated", "archived");
        }

        @Override
        public String getValue(String cell, int line) throws CSVException {
            if (statusPossible.contains(cell)){
                return cell;
            }
            throw new CSVException("Status: " + cell + " not found in line " + line);
        }
    }

    private static class TypeConstraint implements CellCSV<String> {
        @Override
        public String getValue(String cell, int line) throws CSVException {
            if (cell.equals("lead") || cell.equals("ticket")) {
                return cell;
            }
            throw new CSVException("Type: " + cell + " not found in line " + line);
        }
    }

    private static class IntPositive implements CellCSV<Integer> {

        @Override
        public Integer getValue(String cell, int line) throws CSVException {
            try {
                Integer value = Integer.parseInt(cell);
                if (value < 0) {
                    throw new CSVException("Valeur négative sur la ligne " + line);
                }
                return value;
            } catch (NumberFormatException ex) {
                throw new CSVException(ex.getMessage() + " sur la ligne " + line);
            }
        }
    }

    private static class TimeConstraint implements CellCSV<LocalTime> {

        @Override
        public LocalTime getValue(String cell, int line) throws CSVException {
            try {
                LocalTime value = LocalTime.parse(cell);
                return value;
            } catch (Exception ex) {
                throw new CSVException(ex.getMessage() + " sur la ligne " + line);
            }
        }
    }

    private static class LocalDateTimeConstraint implements CellCSV<LocalDateTime> {

        @Override
        public LocalDateTime getValue(String cell, int line) throws CSVException {
            try {
                LocalDateTime value = LocalDateTime.parse(cell);
                return value;
            } catch (Exception ex) {
                throw new CSVException(ex.getMessage() + " sur la ligne " + line);
            }
        }
    }

    private static class DoublePositive implements CellCSV<Double> {

        @Override
        public Double getValue(String cell, int line) throws CSVException {
            try {
                Double value = Double.parseDouble(cell.replace(",", "."));
                if (value < 0) {
                    throw new CSVException("Valeur négative sur la ligne " + line);
                }
                return value;
            } catch (NumberFormatException ex) {
                throw new CSVException(ex.getMessage() + " sur la ligne " + line);
            }
        }
    }

    private static class LocalDateConstraint implements CellCSV<LocalDate> {

        @Override
        public LocalDate getValue(String cell, int line) throws CSVException {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate value = LocalDate.parse(cell, formatter);
                return value;
            } catch (Exception ex) {
                throw new CSVException(ex.getMessage() + " sur la ligne " + line);
            }
        }
    }

    private static class ListForeign implements CellCSV<List<String>> {

        @Override
        public List<String> getValue(String cell, int line) throws CSVException {
            try {
                List<String> value = new ArrayList<>();
                String[] foreignKeySplitted = cell.split(";");
                for (String foreignKey : foreignKeySplitted) {
                    value.add(foreignKey.replace(" ", ""));
                }
                return value;
            } catch (DateTimeException ex) {
                throw new CSVException(ex.getMessage() + " sur la ligne " + line);
            }
        }
    }
}
