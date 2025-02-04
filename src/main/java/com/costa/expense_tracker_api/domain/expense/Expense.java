package com.costa.expense_tracker_api.domain.expense;

import com.costa.expense_tracker_api.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Table(name = "expense")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Expense {

    @Id
    @GeneratedValue
    private UUID id;
    private Float value;
    private Date date;
    private String category;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
