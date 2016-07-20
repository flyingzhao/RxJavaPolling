package com.optimais.pollingtest;

/**
 * Created by optima1 on 2016/7/15.
 */

import java.util.List;

public class UserJsonResult {
    public static class user {
        private String mobile;
        private String name;
        private String roleId;
        private String roleName;
        private String userid;

        public String getMobile() {
            return mobile;
        }

        public String getName() {
            return name;
        }

        public String getUserid() {
            return userid;
        }

        public String getRoleId() {
            return roleId;
        }

        public String getRoleName() {
            return roleName;
        }
    }

    private List<user> list;

    public List<user> getList() {
        return list;
    }
}