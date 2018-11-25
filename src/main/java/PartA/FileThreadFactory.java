package PartA;

import java.util.concurrent.ThreadFactory;

public class FileThreadFactory implements ThreadFactory{
    String name="";


    public FileThreadFactory(String name){
        this.name=name;
    }

    public Thread newThread(Runnable r) {
        return new Thread(r,this.name);
    }
    public void setName(String name) {
        this.name = name;
    }
}

