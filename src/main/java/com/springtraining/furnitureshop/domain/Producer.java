package com.springtraining.furnitureshop.domain;


import lombok.NonNull;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@ToString
@Entity
public class Producer {
    @GeneratedValue(generator = "ID_GENERATOR")
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 45)
    private @NonNull String name;

    public Producer(@NonNull String name) {
        this.name = name;
    }

    private Producer() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producer)) return false;

        Producer producer = (Producer) o;

        return name.equals(producer.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
