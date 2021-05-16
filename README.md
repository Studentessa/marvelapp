# Marvel App
Implement an app that connects to Marvel API: https://developer.marvel.com/docs​

Feature 1: Show a list with the characters 
Feature 2: Show the detail of a character when is selected on the previous list 


## Solution description
*  Clean Architecture patron has been implemented using MVVM → the view (fragment) observe a StateFlow object which feeds states to the view that reacts to them and update the UI, using the pattern <<Single View State>> 

* I had implemented multi modular app, dividing the data and view layers within feature modules, list_fetaure and detail_feature. 

* For networking calls has been used `okHttp` and `retrofit` libraries. As well `coroutines` have been used to make the asynchronous request. Repository pattern and caseUse pattern are used. 

* It has been use Hilt as dependency injection library

* Viewbinding is used to generate code for interacts with views and avoid boilerplate code regarding to `findViewById`

* `Coil` was the library chosen as image loading library, I have worked with other famous libraries like `Picasso` and `glide` but for sometime I’ve been using Coil  because it is developed in `Kotlin` and used coroutines, the performance it’s quite good. 

* UnitTest have been developed.

Future features:

*Implement data persistence with Room library 
*Implement some filters, such as name search, series or comic appearance. 
