package site.easy.to.build.crm.service.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.javafaker.Faker;
import com.nimbusds.jose.util.Resource;
import lombok.AllArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.dto.csv.CustomerCsv;
import site.easy.to.build.crm.dto.csv.entity.CustomerUser;
import site.easy.to.build.crm.entity.*;
import site.easy.to.build.crm.entity.budget.Expense;
import site.easy.to.build.crm.repository.CustomerRepository;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final JdbcTemplate jdbcTemplate;

    private final Faker faker = new Faker();

    private final PasswordEncoder passwordEncoder;

    private final Path fileStorageLocation;

    public CustomerServiceImpl(CustomerRepository customerRepository, JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public Customer findByCustomerId(int customerId) {
        return customerRepository.findByCustomerId(customerId);
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public List<Customer> findByUserId(int userId) {
        return customerRepository.findByUserId(userId);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    @Override
    public List<Customer> getRecentCustomers(int userId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return customerRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    @Override
    public long countByUserId(int userId) {
        return customerRepository.countByUserId(userId);
    }

    @Override
    public Integer findCountCustomer() {
        return this.customerRepository.findCountCustomer().orElse(0);
    }

    @Override
    public CustomerUser createCustomer(List<CustomerCsv> customerCsvs, Role role) {
        List<Customer> customers = new ArrayList<>();
        List<User> users = new ArrayList<>();
        for (CustomerCsv customerCsv : customerCsvs) {
            Customer customer = customerCsv.createCustomer();
            customer.setCity(faker.address().cityName());
            customer.setCountry(faker.country().name());
            customer.setState(faker.country().capital());
            customer.setDescription(faker.lorem().paragraph());

            User user = customer.getUser();
            user.setRoles(Arrays.asList(role));
            user.setStatus("active");
            user.setPassword(passwordEncoder.encode("itu16"));

            customers.add(customer);
            users.add(user);
        }
        return new CustomerUser(customers, users);
    }

    public List<Customer> saveAll(List<Customer> customers) {
        insertBatch(customers);
        return this.customerRepository.findAll();
    }

    public Customer duplicate(Integer customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId);
        Customer newCustomer = customer.cloneCustomer();
        for (Expense expense : newCustomer.getExpenses()) {
            expense.setIdExpense(null);
            if (expense.getLead() != null) {
                expense.getLead().setLeadId(0);
                expense.getLead().setCustomer(null);
                expense.getLead().setLeadActions(null);
                expense.getLead().setGoogleDriveFiles(null);
                expense.getLead().setFiles(null);
                expense.getLead().setManager(null);
            }
            if (expense.getTicket() != null) {
                expense.getTicket().setCustomer(null);
                expense.getTicket().setTicketId(0);
            }
        }
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.writeValue(new File("C:\\Users\\ryrab\\Desktop\\Ryan\\Etudes\\S6\\Evaluation\\Saison1\\crm\\src\\main\\resources\\mydata.json"), newCustomer);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return newCustomer;
    }

    public UrlResource downloadFile() {
        File dir = new File("C:\\Users\\ryrab\\Desktop\\Ryan\\Etudes\\S6\\Evaluation\\Saison1\\crm\\src\\main\\resources\\mydata.json");
        try{
            if(dir.exists()){
                UrlResource resource = new UrlResource(dir.toURI());
                return resource;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }


    public void insertBatch(List<Customer> customers) {
        String sql = "INSERT INTO customer (name, address, city, state, country, user_id, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, customers, 50, (ps, entity) -> {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getAddress());
            ps.setString(3, entity.getCity());
            ps.setString(4, entity.getState());
            ps.setString(5, entity.getCountry());
            ps.setInt(6, entity.getUser().getId());
            ps.setString(7, entity.getEmail());
        });
    }
}
