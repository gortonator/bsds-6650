# CS6650 Summer 2021  Assignment 1

## Text Analysis Server - Building the Client

## Overview
We're going to build a high performance, scalable text analysis system over a series of three assignments

## Assignment 1: Building the Client
For the first assignment, you'll build a client that sends lines of text to a server for analysis. 
The server will simply accept and validate requests, and send an HTTP 200 response. 
In Assignment 2, we'll add the processing to the server. 
In Assignment 3, we'll get a little crazy, so make sure you lay solid code and design foundations in the first two assignments!

## Implement the Server API 

The initial server API is specified using [Swagger](https://app.swaggerhub.com/apis/gortonator/TextProcessor/1.0.0#)

In this assignment you need to implement this API using a server technology of your choice. The course labs guide you through how to do this with Java servlets.
From SwaggerHub however, you can generate a server stub for application servers in Java, Python, Go, Scala from following menu option.

*Export-Server Stub*

So - you choose!! You'll ge best instructor/TA support for Java/servlets but we'll do our best to help whichever way you go.

The API implementation for this assignment should simply:

1. Accept the parameters for the post operation as per the specification
1. Do basic parameter validation, and return a 4XX response code and error message if invalid values/formats supplied
1. If the request is valid, return a 200 response code and a response body with any valid integer

This should be a pretty simple task. Lab 2 shows how to implement a servlet and there's lots of information on servlet programming on the Interwebs. 
[Here's one example](https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaServlets.html). If you are not using servlets, Google is your friend!

Test the API locally with [POSTMAN](https://www.getpostman.com/downloads/) or an equivalent HTTP testing tools.

You then need to deploy the server on an AWS free tier instance you have created and configured in lab 1 and call the APIs successfully.

## Build the Client (Part 1)

This is the major part of this assignment. We want a multithreaded Java client that posts lines of text and exerts various loads on the server.

First you need to get a Java client to call your server APIs. You can generate a client API from the Swagger specification. Look at:

*Export-Client SDK-Java*

Unzip the client and follow the instructions in the README to incorporate the generated code in your client project.

The generated code contains thread-safe methods for calling the server APIs. 
Write a simple test that calls the API before proceeding, to establish that you have connectivity. 
The example in the README is your friend ;). You just need to look at the ApiClient methods to figure out how to point the example at your server. 

If you don't want to figure out the Swagger client, you can use the [Apache Java HTTP API](https://hc.apache.org/index.html).

Once you have your client calling the API, it's time to build the client fun time!!

Your client should accept 2 arguments
1. an input file 
1. number of threads to run - maxThreads

Based on these values, your client will start up and 
1. process and validate the input parameters
1. Create a thread for every store (1..maxStores). Each store thread will, for every hour they are open, send *numPurchases* POST requests to the server. Each POST will have the default number of items to purchase in the request body.
1. Each request needs to randomly select a custID and itemIDs for the order. For custIDs, generate a value between (storeIDx1000) and (storeIDx1000)+number of customers/store. 
1. In the request body, generate the default number of items purchased (randomly select itemID) and set amount to 1. 

