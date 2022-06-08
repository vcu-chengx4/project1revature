package com.example.revature.project1.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Component
@Entity
@Table(name="Cart",schema="project1")

public class Cart implements Serializable {
    @Getter
    @EmbeddedId
    private CartIdentification cartId;
    @ManyToOne()
    @MapsId("userId")
    @JoinColumn(name="userId")
    @JsonIgnore
    private User userId;
    @ManyToOne()
    @JsonIgnore
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    private Item itemId;
    @Column(name="item_qoh")
    private int cartQoh;
}