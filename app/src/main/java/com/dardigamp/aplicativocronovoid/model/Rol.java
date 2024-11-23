package com.dardigamp.aplicativocronovoid.model;
import java.util.HashSet;
import java.util.Set;

public class Rol {
    private int id;
    private RoleEnum roleEnum;
    private Set<Permiso> permisos = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleEnum getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }

    public Set<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(Set<Permiso> permisos) {
        this.permisos = permisos;
    }

    public Rol() {
    }

    public Rol(int id, RoleEnum roleEnum, Set<Permiso> permisos) {
        this.id = id;
        this.roleEnum = roleEnum;
        this.permisos = permisos;
    }
}
