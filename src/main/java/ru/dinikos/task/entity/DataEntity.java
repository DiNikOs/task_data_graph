/**
 * @author Ostrovskiy Dmitriy
 * @created 09.06.2020
 * Data
 * @version v1.0
 */

package ru.dinikos.task.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "name_data", unique = true)
    private String name_data;

    @Column(name = "count_data")
    private Integer count_data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataEntity that = (DataEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(created, that.created) &&
                Objects.equals(name_data, that.name_data) &&
                Objects.equals(count_data, that.count_data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, name_data, count_data);
    }
}
