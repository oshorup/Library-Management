package DATA;

import java.io.Serializable;

public class UserInformation implements Serializable {

    String name="",email="",CollegeID_number="";

    public UserInformation(String name,String email,String collegeID_number) {
        this.name=name;
        this.email = email;
        this.CollegeID_number=collegeID_number;
    }

    public UserInformation() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCollegeID_number() {
        return CollegeID_number;
    }

    public void setCollegeID_number(String collegeID_number) {
        CollegeID_number = collegeID_number;
    }
}
