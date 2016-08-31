package ar.com.gitmo.androidpruebas.models;

public class Test {

    private String name;
    private String description;


    private String activity;

    public Test(String n, String d,String a){
        name=n;
        description=d;
        activity=a;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getActivity() {

        return activity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
