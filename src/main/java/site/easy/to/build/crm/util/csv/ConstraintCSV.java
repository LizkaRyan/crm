package site.easy.to.build.crm.util.csv;

import site.easy.to.build.crm.util.csv.exception.CSVException;
import site.easy.to.build.crm.util.csv.parameter.CellCSV;

import java.time.DateTimeException;
import java.time.LocalDate;

public final class ConstraintCSV {

    public static final LongPositive LONGPOSITIVE=new LongPositive();

    public static final IntPositive INTPOSITIVE=new IntPositive();

    public static final DoublePositive DOUBLEPOSITIVE=new DoublePositive();

    public static final LocalDateConstraint LOCALDATE=new LocalDateConstraint();

    public static class LongPositive implements CellCSV<Long>{

        @Override
        public Long getValue(String cell,int line) throws CSVException {
            try{
                Long value=Long.parseLong(cell);
                if(value<0){
                    throw new CSVException("Valeur négative sur la ligne "+line);
                }
                return value;
            }
            catch (NumberFormatException ex){
                throw new CSVException(ex.getMessage()+" sur la ligne "+line);
            }
        }
    }

    public static class IntPositive implements CellCSV<Integer>{

        @Override
        public Integer getValue(String cell,int line) throws CSVException {
            try{
                Integer value=Integer.parseInt(cell);
                if(value<0){
                    throw new CSVException("Valeur négative sur la ligne "+line);
                }
                return value;
            }
            catch (NumberFormatException ex){
                throw new CSVException(ex.getMessage()+" sur la ligne "+line);
            }
        }
    }

    public static class DoublePositive implements CellCSV<Double>{

        @Override
        public Double getValue(String cell,int line) throws CSVException {
            try{
                Double value=Double.parseDouble(cell);
                if(value<0){
                    throw new CSVException("Valeur négative sur la ligne "+line);
                }
                return value;
            }
            catch (NumberFormatException ex){
                throw new CSVException(ex.getMessage()+" sur la ligne "+line);
            }
        }
    }

    public static class LocalDateConstraint implements CellCSV<LocalDate>{

        @Override
        public LocalDate getValue(String cell,int line) throws CSVException {
            try{
                LocalDate value=LocalDate.parse(cell);
                return value;
            }
            catch (DateTimeException ex){
                throw new CSVException(ex.getMessage()+" sur la ligne "+line);
            }
        }
    }
}
