package com.example.revature.project1.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@Component
public class CartIdentification implements Serializable {
    @Column(name = "cart_id")
    int cartId;
    @Column(name = "user_id")
    int userId;
    @Column(name = "item_id")
    int item_id;
}
