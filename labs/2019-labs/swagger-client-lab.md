# CS6650 Fall 2020  

Hey everyone,

Hopefully after yesterday's lab you should (all or at least most) have a servlet running that you can test the two endpoints work ok with POSTMAN. Implementing the other endpoints is pretty straightforward based on these.

Next - you need to create a Java client that can call your servlet instead of POSTMAN. Here's one way to do it:

Go to the API definition in Swagger.
In top right, you'll see an 'export' button. Click on it, go to client SDK option, and choose Java This will download a .zip file with all sorts of goodies!
Extract the .zip to where you want your client project to reside. The README in the root directory contains instructions on how to build with maven, so create a project and get the build working
In the README, there are some simple examples of how to call the APIs. Here's one for /resorts
import io.swagger.client.*;
import io.swagger.client.auth.*;
import io.swagger.client.model.*;
import io.swagger.client.api.ResortsApi;

import java.io.File;
import java.util.*;

public class ResortsApiExample {

    public static void main(String[] args) {
        
        ResortsApi apiInstance = new ResortsApi();
        try {
            List<ResortsList> result = apiInstance.getResorts();
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ResortsApi#getResorts");
            e.printStackTrace();
        }
    }
}
There are two things to note:

1. as you'll see in the README 

All URIs are relative to */*
so we need to set the base path URL for our servers, eg if testing locally this is something like localhost:8080/skiAPI/

This is luckily pretty easy:

ResortsApi apiInstance = new ResortsApi();
ApiClient client = apiInstance.getApiClient();
client.setBasePath(basePath);
So modify one of the examples to point to your servlet URL, and test everything works in a simple single-threaded server. 

#fingerscrossed it does, this is a BIG STEP forward!

The other thing to note is in the generated docs for the client SDK, it says at the bottom of the README:

## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.
So when you start to create a multithreaded client, you need to create a local instance of the ApiClient(s) to use in each thread, and point these local instances at  the base path for the servlet URL

Clear as mud? Give it a go and shout if you have any hassles. I'll add this to the assignment write up on the repo soon.

And for anyone who is bored and wants to go 'old school' (like me!), check out the Apache HttpClient project. It's tonnes of fun! And you'll learn a lot. 

[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)