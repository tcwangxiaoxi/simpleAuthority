package simple.authority.entity;

import lombok.Data;
import simple.authority.entity.base.AbstractEntity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
@Data
public class User extends AbstractEntity {

    private String username, password;

    @ManyToMany
    private Set<Role> roles = new HashSet<>();
}
