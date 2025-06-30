package org.code;

public class MyService {
    private final ExternalApi externalApi;

    public MyService(ExternalApi api){
        this.externalApi = api;
    }

    public String fetchData(){
        return externalApi.getData();
    }
}
