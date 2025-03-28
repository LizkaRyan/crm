package site.easy.to.build.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.groups.Default;
import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.customValidations.customer.UniqueEmail;
import site.easy.to.build.crm.entity.budget.Budget;
import site.easy.to.build.crm.entity.budget.Expense;
import site.easy.to.build.crm.util.POV;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@JsonView(POV.Public.class)
public class Customer {
    public interface CustomerUpdateValidationGroupInclusion {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    @JsonView(POV.Public.class)
    private Integer customerId;

    @Column(name = "name")
    @NotBlank(message = "Name is required", groups = {Default.class, CustomerUpdateValidationGroupInclusion.class})
    @JsonView(POV.Public.class)
    private String name;

    @Column(name = "email")
    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email format")
    @UniqueEmail
    private String email;

    @Column(name = "position")
    private String position;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    @JsonView(POV.Public.class)
    private String address;

    @Column(name = "city")
    @JsonView(POV.Public.class)
    private String city;

    @Column(name = "state")
    @JsonView(POV.Public.class)
    private String state;

    @Column(name = "country")
    @JsonView(POV.Public.class)
    @NotBlank(message = "Country is required", groups = {Default.class, CustomerUpdateValidationGroupInclusion.class})
    private String country;

    @Column(name = "description")
    private String description;

    @Column(name = "twitter")
    private String twitter;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "youtube")
    private String youtube;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable=false)
    @JsonIgnoreProperties("customer")
    private User user;

    @OneToOne
    @JoinColumn(name = "profile_id")
    @JsonIgnore
    private CustomerLoginInfo customerLoginInfo;

    @Column(name = "created_at")
    @JsonView(POV.Full.class)
    private LocalDateTime createdAt;

    @Setter
    @Getter
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "customer")
    private List<Budget> budgets=new ArrayList<>();

    @Setter
    @Getter
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "customer")
    private List<Expense> expenses=new ArrayList<>();

    public Customer() {
    }

    public Customer cloneCustomer(){
        Customer customer=new Customer();
        customer.setName(this.getName());
        customer.setCity(this.getCity());
        customer.setDescription(this.getDescription());
        customer.setState(this.getState());
        customer.setCountry(this.getCountry());
        customer.setEmail(this.getEmail());
        customer.setBudgets(this.budgets);
        customer.setExpenses(this.expenses);
        customer.user=null;
        for (Budget budget:this.budgets){
            budget.setCustomer(null);
        }
        for (Expense expense:this.expenses){
            expense.setCustomer(null);
        }
        return customer;
    }

    public String getCsv(){
        //customer_email,subject_or_name,type,status,expense
        String csv="";
        for (Expense expense:this.getExpenses()){
            if(expense.getLead()!=null){
                csv += this.getEmail()+","+expense.getLead().getName()+",lead,"+expense.getLead().getStatus()+","+expense.getAmount()+"\n";
            }
            if(expense.getTicket()!=null){
                csv += this.getEmail()+","+expense.getTicket().getSubject()+",ticket,"+expense.getTicket().getStatus()+","+expense.getAmount()+"\n";
            }
        }
        return csv;
    }

    public Customer(String name, String email, String position, String phone, String address, String city, String state, String country,
                    String description, String twitter, String facebook, String youtube, User user, CustomerLoginInfo customerLoginInfo,
                    LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.position = position;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.description = description;
        this.twitter = twitter;
        this.facebook = facebook;
        this.youtube = youtube;
        this.user = user;
        this.customerLoginInfo = customerLoginInfo;
        this.createdAt = createdAt;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setUser(List<User> users) {
        for(User user:users){
            if(user.getEmail().equals(this.getEmail())){
                this.user=user;
                break;
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CustomerLoginInfo getCustomerLoginInfo() {
        return customerLoginInfo;
    }

    public void setCustomerLoginInfo(CustomerLoginInfo customerLoginInfo) {
        this.customerLoginInfo = customerLoginInfo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

//    public List<Ticket> getTickets() {
//        return tickets;
//    }
//
//    public void addTicket(Ticket ticket) {
//        this.tickets.add(ticket);
//    }
//    public void deleteTicket(Ticket ticket) {
//        this.tickets.remove(ticket);
//    }
//    public void setTickets(List<Ticket> tickets) {
//        this.tickets = tickets;
//    }
}
