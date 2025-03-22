package site.easy.to.build.crm.service.data;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class DataService {
    private final RestTemplate restTemplate;
    private final JdbcTemplate jdbcTemplate;

    public List<String> callApi(String apiUrl) {
        ResponseEntity<List<String>> response = restTemplate.exchange(
                apiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {});
        return response.getBody();
    }

    public String initData(){
        List<String> sqlDatas=this.callApi("http://127.0.0.1:8000/generate/data");
        String sqlFor="";
        try{
            for(String sql:sqlDatas){
                sqlFor=sql;
                jdbcTemplate.update(sql);
            }
        }
        catch (Exception ex){
            System.out.println(sqlFor);
            throw ex;
        }
        return "Insertion réussie";
    }
}
