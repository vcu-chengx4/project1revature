package com.example.revature.project1.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Component
@Entity
@Table(name="Order",schema="project1")

public class Order implements Serializable {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private int cartId; //will show cart id instead of item ids
    private int userId;
    private int countOfUniqueItems;
    private double price;
    @JsonIgnore
    @OneToMany(mappedBy = "itemId")
    private List<Item> itemList = new ArrayList<>();
}