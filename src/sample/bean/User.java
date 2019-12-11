package sample.bean;

/**
 * @author baikunlong
 * @date 2019/12/9 21:09
 */
public class User {
    /**
     * 用户名
     */
    private String uName;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 角色：0-管理员、1-普通用户
     */
    private int role;

    @Override
    public String toString() {
        return "User{" +
                "uName='" + uName + '\'' +
                ", pwd='" + pwd + '\'' +
                ", role=" + role +
                '}';
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public User() {
    }

    public User(String uName, String pwd, int role) {
        this.uName = uName;
        this.pwd = pwd;
        this.role = role;
    }
}
