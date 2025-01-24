package br.com.wealth.core.enums;


public enum RolesTypeEnum {

    TENANT_ADMIN(1),
    TEAM_ADMIN(2),
    FINANCIAL_ADVISOR(3);

    long roleId;

    RolesTypeEnum(long roleId) {
        this.roleId = roleId;
    }

    public long getRoleId() {
        return roleId;
    }

}
