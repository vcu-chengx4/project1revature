package com.example.revature.project1.model;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Component
@Entity
@Table(name="Item",schema="project1")
public class Item implements Serializable {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;
    private String itemName;
    private int qoh;
    private double price;

    public String displayMessage(){
        return "items message activated";
    }
}
