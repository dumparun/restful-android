# Contents

* [Overview](#overview)
* [Architecture](#architecture)
* [More information](#moreinformation)
* [How to contribute](#howtocontribute)
* [Branches](#branches)
* [Contact Us](#contactus)
* [Third-party software](#thirdpartysoftware)

<a name="overview"></a>
# Overview

RESTful Android is an implementation of the RESTful Android app architecture as described in [Developing Android REST client applications](http://www.google.com/events/io/2010/sessions/developing-RESTful-android-apps.html), a talk by [Virgil Dobjanschi](https://plus.google.com/101870761930221849874) at Google I/O 2010.

<a name="architecture"></a>
# Architecture

![Service API Diagram](https://github.com/aug-mn/restful-android/raw/master/docs/service-api-diagram.png "Service API Pattern")

### Activity
Displays the current state of a resource, or set of resources, by observing the Content Provider that holds the resource, typically via a CursorAdapter.  CRUD requests for a resource are executed asynchronously using the ServiceHelper.  The activity can listen for the result of the requests, which are broadcast by the ServiceHelper, and display any indicators on the UI.  Any update of the actual resource is retrieved via the ContentProvider.

### ServiceHelper
Exposes an asynchronous API for the front-end application that provides access to resources. Translates a resource request from the UI layer into service invocation.  Immediately returns a unique request id to a caller, and later broadcasts the result of the request, once completed by the service layer.  Maintains the state of all pending requests.

### Service
Stateless process that accepts resource requests from the application layer, and invokes the correct processor for that resource in a background thread. Returns the result of the request along with original intent in an asynchronous callback.

### Processor
Maintain the state of a resource and any transactions for that resource using a Content Provider.  Knows how to execute rest operations for a resource. Returns the result of the transaction.

### Rest Method
Understands the semantics of the remote api needed for the resource and handles the actual http requests. Rest Methods are blocking and return the result of the request and optionally a raw representation of the resource.

<a name="moreinformation"></a>
## More information

### Presentations

* **Developing Android REST client applications**
[http://www.google.com/events/io/2010/sessions/developing-RESTful-android-apps.html](http://www.google.com/events/io/2010/sessions/developing-RESTful-android-apps.html)    
The talk that started it all.

### Books

* **Programming Android**    
By Zigurd Mednieks, Laird Dornin, G. Blake Meike, and Masumi Nakamura. Copyright 2011 O’Reilly Media, Inc., 978-1-449-38969-7
[http://shop.oreilly.com/product/0636920010364.do](http://shop.oreilly.com/product/0636920010364.do)<br /><br />
This book has a complete chapter (13. Exploring Content Providers) dedicated to 'Option B: Use the ContentProvider API' from Virgil's Google I/O talk:<br /><br />

    > We are not the only ones who see the benefits of this approach. At the Google I/O conference in May 2010, Virgil Dobjanschi of Google presented a talk that outlined the following three patterns for using content providers to integrate RESTful web services into Android applications...  <br />      
    > In this chapter, we’ll explore the second pattern in detail with our second Finch video example; this strategy will yield a number of important benefits for your applications. Due to the elegance with which this approach integrates network operations into Android MVC, we’ve given it the moniker “Network MVC.”<br /><br />
    > A future edition of Programming Android may address the other two approaches, as well as document more details of this Google presentation. After you finish reading this chapter, we suggest that you view Google’s talk.

### Code

* **iosched** - Google I/O App for Android    
[http://code.google.com/p/iosched/](http://code.google.com/p/iosched/)

<a name="howtocontribute"></a>
# How to contribute

1. Fork the repo
2. Do cool stuff in your repo
3. Issue a [pull request](http://help.github.com/send-pull-requests/) to get your changes into the base project

<a name="branches"></a>
# Branches

This project contains two branches:

* master
* catpictures

## master

The master branch is the main development branch and uses the Twitter API for content.  Contributions to the project should be made here.

## catpictures

The catpictures branch is a simple, no-fluff version for use with the __Developing an Android RESTful Client App__ presentation at [Mobile March](http://mobilemarchtc.com) and [Gluecon](http://gluecon.com).  This one uses the [catpictures subreddit](http://reddit.com/r/catpictures) for its content.

This branch is currently ahead of master in terms of features and maturity.  Following the Mobile March presentation, changes will be brought back to master.

<a name="contactus"></a>
# Contact Us

**Jeremy Haberman** [@jeremyhaberman](http://twitter.com/jeremyhaberman)    
**Brad Armstrong** [@hashbrown1](http://twitter.com/hashbrown1)    
**Peter Pascale** [@peterpascale](http://twitter.com/peterpascale)

<a name="thirdpartysoftware"></a>
# Third-party software

This product in the master branch contains the following library:

scribe-java    
[https://github.com/fernandezpablo85/scribe-java](https://github.com/fernandezpablo85/scribe-java)

The MIT License

Copyright (c) 2010 Pablo Fenandez

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
