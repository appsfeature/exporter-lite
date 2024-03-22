package com.exporter.sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExportSample {

    public static class User {

        private String id;
        private String name;

        User(String id, String name) {
            this.id = id;
            this.name = name;
        }

        String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public static List<List<String>> getExporterList() {
        List<User> yourList = new ArrayList<>();
        yourList.add(new User("01","Alpha"));
        yourList.add(new User("02","Bita"));

        //add header in your exporting list
        yourList.add(0, new User("Id","Name"));

        //make exporter list
        List<List<String>> exporterList = new ArrayList<>();
        for (User item : yourList){
            exporterList.add(Arrays.asList(item.getId(),item.getName()));
        }
        return exporterList;
    }

    private static List<User> getSampleUsersList() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            users.add(new User("U-"+i,"User"+i));
        }
        return users;
    }

}
