package org.scalatra
package tests

class HttpMethodsApp extends ScalatraApp {
  
  get("/hello/:param") {
    params('param)
  }
  
  get("/query") {
    params('val1) + " " + params('val2)
  }
  
  post("/urlencode") {
    params('first) + " " + params('last)   
  }
  
  post("/multipart") {
    params('first) + " " + params('last)
  }
  
  post("/upload") {
    "uploaded"
  }
  
  put("/update") {
    params("first") + " " + params("last")
  }
  
  put("/mulitpart-update") {
    params("first") + " " + params("last")
  }
  
  put("/update_upload") {
    "uploaded too"
  }
  
  delete("/delete/:id") {
    params("id")
  }
  
  get("/some") { // get also does HEAD
    "head success"
  }
  
  options("/options") {
    "options success"
  }
  
  patch("/patching") {
    params("first") + " " + params("last")
  }
  
}

class HttpMethodsSpec extends ScalatraSpec {
  
  mount(new HttpMethodsApp)
  
  def is = 
    "The HttpMethod support should" ^ 
      "allow get requests with path params" ! getHelloParam ^
      "allow get requests with query params" ! getQuery ^
      "allow head requests" ! getHead ^
      "allow options requests" ! getOptions ^
      "allow delete requests" ! deleteRequest ^
      "allow url encoded posts" ! formEncodedPosts ^
      "allow url encoded puts" ! formEncodedPuts ^
      "allow url encoded patches" ! formEncodedPatches ^
    end
  
  def formEncodedPosts = {
    post("/urlencode", Map("first" -> "hello", "last" -> "world")) {
      response.body must_== "hello world"
    }
  }

  def formEncodedPuts = {
    put("/update", Map("first" -> "hello", "last" -> "world")) {
      response.body must_== "hello world"
    }
  }

  def formEncodedPatches = {
    patch("/patching", Map("first" -> "hello", "last" -> "world")) {
      response.body must_== "hello world"
    }
  }

  def getHelloParam = {
    get("/hello/world") {
      response.statusCode must_== 200
      response.body must_== "world"
    }
  }

  def getQuery = {
    get("/query", Map("val1" -> "hello", "val2" -> "world")) {
      response.statusCode must_== 200
      response.body must_== "hello world"
    }
  }
  
  def getHead = {
    head("/some") {
      response.statusCode must_== 200
    }
  }
  
  def getOptions = {
    options("/options") {
      response.body must_== "options success"
    }
  }
  
  def deleteRequest = {
    deleteReq("/delete/blah") {
      response.body must_== "blah"
    }
  }
}