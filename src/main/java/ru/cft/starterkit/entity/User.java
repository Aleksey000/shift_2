package ru.cft.starterkit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;
import java.util.UUID;

public class User {

    private Long id;

    private String login;

    private String name;

    private String phone;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private UUID baz;

    public User(String login, String password, String name, String phone, UUID baz) {
        this.login      = login;
        this.password   = password;
        this.name       = name;
        this.phone      = phone;
        this.baz = baz;
    }

    // ID
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    // Login
    public String getLogin() { return login;}
    public void setLogin(String login) { this.login = login;}
    // Password
    public String getPassword() { return password;}
    public void setPassword(String password) { this.password = password;}
    // Name
    public String getName() { return name;}
    public void setName(String name) { this.name = name;}
    // Phone
    public String getPhone() { return phone;}
    public void setPhone(String phone) { this.phone = phone;}

    // Baz
    public UUID getBaz() { return baz;}
    public void setBaz(UUID baz) { this.baz = baz;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User entity = (User) o;
        return Objects.equals(id, entity.id) &&
                Objects.equals(login, entity.login) &&
                Objects.equals(name, entity.name) &&
                Objects.equals(phone, entity.phone) &&
                Objects.equals(baz, entity.baz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, name, phone, baz);
    }

    @Override
    public String toString() {
        return "SampleEntity{" +
                "id=" + id +
                ", foo='" + login + '\'' +
                ", name=" + name +
                ", phone=" + phone +
                ", baz=" + baz +
                '}';
    }
}
