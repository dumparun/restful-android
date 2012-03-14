# Contents

* [Overview](#overview)
  * [The problem](#theproblem)
  * [The solution](#thesolution)
  * [More information](#moreinformation)
* [How to contribute](#howtocontribute)
* [Branches](#branches)
* [Third-party software](#thirdpartysoftware)

<a name="overview"></a>
# Overview

RESTful Android is an implementation of the RESTful Android app architecture as described in [Developing Android REST client applications](http://www.google.com/events/io/2010/sessions/developing-RESTful-android-apps.html), a talk by [Virgil Dobjanschi](https://plus.google.com/101870761930221849874) at Google I/O 2010.

<a name="theproblem"></a>
## The problem

[TODO]

<a name="thesolution"></a>
## The solution

[TODO]

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
A future edition of Programming Android may address the other two approaches, as well as document more details of this Google presentation. After you finish reading this chapter, we suggest that you view Google’s talk.

### Code

* **iosched** - Google I/O App for Android    
[http://code.google.com/p/iosched/](http://code.google.com/p/iosched/)

<a name="howtocontribute"></a>
# How to contribute

[TODO]

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
