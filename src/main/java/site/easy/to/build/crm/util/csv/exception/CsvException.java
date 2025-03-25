package site.easy.to.build.crm.util.csv.exception;

import java.util.List;

public class CsvException extends Exception{
    List<String> errors;

    public CsvException(List<String> errors){
        super("Il y a une ou des erreurs dans les ficiers csv");
        this.errors=errors;
    }
}
