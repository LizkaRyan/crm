package site.easy.to.build.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReservationDTO {
    public String reg;
    public String espace;
    public String client;
    public LocalDate date;
    public LocalTime heureDebut;
    public int duree;
    public List<String> option;
}
