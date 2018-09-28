# TMDBProject-MVP-Clean-Architecture

This **android app** does the same functions that the [TMDBProject](https://github.com/fvaldiviadev/TMDBProject) of this repository, but it is developed with **Model View Presenter** ([MVP](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter)) arquitecture and using **Clean Architecture** concepts.

With the intention of show the progress and the improves of each feature of the project, you can find two branches:
 - **MVP_Basic** branch: I use contracts to define the functions that presenters and view implements. The view doesn´t use data, just show it. The presenter has all business logic. 
 - **MVP_Clean_Arquitecture** branch: I´ve added three layers to the initial approach, so the project is distribute like next:
![schema](https://github.com/fvaldiviadev/TMDBProject-MVP/blob/MVP_Clean_arquitecture/img2_readem.jpg?raw=true)
    - View: only elements of the list, it will implement methods that are dedicated to modify this. It includes the Adapter.
    - Presenter: Implements methods that call the view, such as actions by pressing buttons etc. Each of these methods call to their Interactor. The presenter does not handle data.
    - Interactor: Implements the methods called by the presenter. Manage where you are going to collect the data, by calling one repository or another. Any other task that does not involve obtaining data from a source (repository), goes here.
    - Repository: handles all data related to a collection (a repository for movies, another for user, etc) and calls different data sources (DAO)
    - DAO: methods to obtain the data. There will be a DAO for each origin (Network, local database, internal memory, xml of project resources ...) 

The folder structure is this:

![structure](https://github.com/fvaldiviadev/TMDBProject-MVP/blob/MVP_Clean_arquitecture/img1_readme.png?raw=true)

I distribute the code in four main folders, two for each screen of the app, a Data folder where you can find the repositories and data sources, and a Util folder for constants class or any class of global access.


This project has been developed in **Java**, using **Android Studio** and implementing the libraries **Retrofit and Glide**.


![Infinitelist](https://github.com/fvaldiviadev/TMDBProject-MVP/blob/MVP_Clean_arquitecture/gif1_readme.gif) ![Search while typing](https://github.com/fvaldiviadev/TMDBProject-MVP/blob/MVP_Clean_arquitecture/gif2_readme.gif)
