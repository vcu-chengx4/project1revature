package com.example.revature.project1.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Component
@Entity
@Table(name="User",schema="project1")

public class User implements Serializable {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(name="email")
    private String userEmail;
    @Column(name="password")
    private String userPassword;
    @OneToMany(mappedBy = "userId")
    @Column(name="carts")
    @JsonIgnore
    private Set<Cart> itemList;

    public static int loggedAs=0;
    public void setItemList() {
        this.itemList = itemList;
    }

    public static void setLoggedAs(int loggedAs) {
        User.loggedAs = loggedAs;
    }

    public static int getLoggedAs() {
        return loggedAs;
    }
}
