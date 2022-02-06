# Http

## A full and easy Java library for make POST and GET requests, and some other usefull tasks.

## Tutorial:

### 1) Import the Http.jar onto your Java Project

### 2) Read the class documentation

### 3) Example of Usage:

public class Test {
    public static void main(String[] args) {
        Http http = Http.buildRequest();

        ArrayList<ArrayList> paramsBuilder = new ArrayList<>();
        ArrayList<String> params = new ArrayList<>(); //first param
        params.add("your_name"); //key
        params.add("eduardo_programador"); //value
        paramsBuilder.add(params);

        ArrayList<ArrayList> headersBuilder = new ArrayList<>();
        ArrayList<String> headers = new ArrayList<>(); //first header
        headers.add("User-Agent"); //key
        headers.add("Mozilla"); //value
        headersBuilder.add(headers);

        http.post("example.com",false,80,"/webservice.asp",paramsBuilder,headersBuilder);
    }
}

Copyright 2022. Eduardo Programador