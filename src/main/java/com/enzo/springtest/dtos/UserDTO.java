package com.enzo.springtest.dtos;

import java.util.Objects;

public class UserDTO {

    //surname + name
    private String name;

    private String address;

    public UserDTO() {}


    public UserDTO(String name, String address) {
        this.name = name;
        this.address = address;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return name.equals(userDTO.name) &&
                Objects.equals(address, userDTO.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }
}
