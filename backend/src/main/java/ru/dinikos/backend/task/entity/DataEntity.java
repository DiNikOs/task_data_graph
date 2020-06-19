/**
 * @author Ostrovskiy Dmitriy
 * @created 09.06.2020
 * Data
 * @version v1.0
 */

package ru.dinikos.backend.task.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
//@Data "java.lang.StackOverflowError" with this annotation - changed to getter setter
@Getter
@Setter
@NoArgsConstructor
@Table(name = "data_test")
public class DataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_data")
    private String name;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created")
    private Date created;

    @Column(name = "count_data")
    private Integer counts;

}
