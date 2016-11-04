package com.example.hp.navigation.activity;
public class DataProviderdesc {
    private String name;

    int id=0;

    public DataProviderdesc(String name, int id)
    {
        this.name =name;

        this.id=id;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
