package site.easy.to.build.crm.util.csv;

import java.util.ArrayList;
import java.util.List;

public class CollectionCsvFile extends ArrayList<CSVFile<?>> {

    public CollectionCsvFile(List<CSVFile<?>> csvFiles){
        super(csvFiles);
    }

    public void read(){
        for (CSVFile<?> csvFile:this){
            csvFile.read();
        }
    }

    public boolean hasErorrs(){
        for (CSVFile<?> csvFile:this){
            if(csvFile.hasError()){
                return true;
            }
        }
        return false;
    }
}
