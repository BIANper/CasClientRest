package com.yuyu.first.config.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cas.client")
public class CasClientProperties {

    private String prefix;
    private String login;
    private String logout;
    private String loginRelative;
    private String logoutRelative;


    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogout() {
        return logout;
    }

    public void setLogout(String logout) {
        this.logout = logout;
    }

    public String getLoginRelative() {
        return loginRelative;
    }

    public void setLoginRelative(String loginRelative) {
        this.loginRelative = loginRelative;
    }

    public String getLogoutRelative() {
        return logoutRelative;
    }

    public void setLogoutRelative(String logoutRelative) {
        this.logoutRelative = logoutRelative;
    }
}
