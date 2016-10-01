package com.example.eodwan.cookingrecipe;
public class DataProvider {
    private String name;
    private String calory;
    int id=0;

    public DataProvider(String name,String calory, int id)
    {
        this.name =name;
        this.calory =calory;
this.id=id;

    }

    public String getCalory() {

        return calory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCalory(String calory) {
        this.calory = calory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
      //  id=getId();
      //  id++;
      //  setId( id);

        this.name = name;
    }

}
