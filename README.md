<!-- 
    README.md in branch D12-Datamodel
 -->

# Aufgabe F12: *Refactoring*

[Refactoring](https://refactoring.guru/refactoring)
is the process of changing (improving) the structure of
the codebase without changing its function.

The program will perform the same function after the refactoring
compared to what it performed before the refactoring.

The [datamodel](src/datamodel) has already been well-structured.

A problem is that all functions of the program were included in
a single file [Application_D1.java](src/application/Application_D1.java)

Goal of the Refactoring is a **Component architecture** that introduces
component objects with interfaces.

<img src="https://raw.githubusercontent.com/sgra64/se1.bestellsystem/markup/F12-Refactoring/F1_component_diagram.png" alt="drawing" width="800"/>


Content:

- [Setup](#1-setup) - Fetching Branches and Sourcing the Project
    - branch: [C12-Customer](https://github.com/sgra64/se1.bestellsystem/tree/C12-Customer),
    - branch: [D12-Datamodel](https://github.com/sgra64/se1.bestellsystem/tree/D12-Datamodel),
    - branch: [F12-Refactoring](https://github.com/sgra64/se1.bestellsystem/tree/F12-Refactoring).

- [F1: Building Components](#d1-building-components)
    - Build component that implements the [system.Calculator](src/system/Calculator.java) interface
    - Build component that implements the [system.Formatter](src/system/Formatter.java) interface

- [F2: Repository](#d2-repository)
    - Replace *mock*-component [system.impl.MockRepository.java](src/system/impl/MockRepository.java)

- [Release Preparation](#release-preparation)
    - Packaging the Application
    - Run the packaged Application


&nbsp;

## 1. Setup

Merge files from the distribution branch
[D12-Datamodel](https://github.com/sgra64/se1.bestellsystem/tree/D12-Datamodel)
into project `se1.bestellsystem`.

Setup assumes the project is under `git` control. Initialize `git`
if this is not yet the case (skip step: `git init` otherwise).

```sh
cd se1.bestellsystem            # change into project directory

[ -d .git ] || git init         # initialize git, if project is not yet under git control

# add link named 'se1-origin' pointing to the remote distribution repository
git remote add se1-origin https://github.com/sgra64/se1.bestellsystem.git

# fetch branches from the remote repository (if not yet fetched)
git fetch se1-origin C12-Customer:C12-Customer
git fetch se1-origin D12-Datamodel:D12-Datamodel
git fetch se1-origin F12-Datamodel:F12-Datamodel

# merge content of fetched branch into current branch
git merge --allow-unrelated-histories --strategy-option theirs C12-Customer
git merge --allow-unrelated-histories --strategy-option theirs D12-Datamodel
git merge --allow-unrelated-histories --strategy-option theirs F12-Datamodel
```

Files that came with the merge of the branch:

```sh
find src
```

```
src
src/application
src/application/Application.java
src/application/Application_C1.java
src/application/Application_D1.java
src/application/Application_F1.java     <-- new driver class
src/application/package-info.java
src/application/Runtime.java
src/datamodel                       <-- complete datamodel classes
src/datamodel/Article.java
src/datamodel/Currency.java
src/datamodel/Customer.java
src/datamodel/Order.java
src/datamodel/OrderItem.java
src/datamodel/package-info.java
src/datamodel/TAX.java
src/module-info.java
src/system                          <-- new package with public component interfaces
src/system/Calculator.java
src/system/DataBuilder.java
src/system/DataFactory.java
src/system/DataStore.java
src/system/Formatter.java
src/system/impl                     <-- implementation classes of system interfaces
src/system/impl/DataStoreImpl.java
src/system/impl/IoC_Impl.java
src/system/impl/MockRepositoryImpl.java
src/system/impl/PrinterImpl.java
src/system/impl/TableFormatter.java
src/system/IoC.java
src/system/package-info.java
src/system/Printer.java
src/system/Repository.java
```

The project can now be sourced to set the project environment:

```sh
source .env/setenv.sh           # source project
```


&nbsp;

## F1: Building Components

<!-- - D1.a - [Datamodel Generation and Completion](#d1a-datamodel-generation-and-completion)

- D1.b - [Building and Running the Application](#d1b-building-and-running-the-application)

- D1.c - [Running JUnit Tests](#d1c-running-junit-tests)

- D1.d - [Code Coverage Report](#d1d-code-coverage-report)

- D1.e - [Javadoc for Complete Datamodel](#d1e-javadoc-for-complete-datamodel) -->


<!-- &nbsp;

### D1.a Datamodel Generation and Completion -->


&nbsp;

## F2: Repository

The [Repository](src/system/Repository.java) component mirrors
Spring Boot's `CrudRepository`
([javadoc](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html)).

A **`Repository<T, ID>`** stores a collection of objects of type `T` that use
an identifier id of type `ID`.
Datamodel objects:
[Customer](src/datamodel/Customer.java),
[Article](src/datamodel/Article.java) and
[Order](src/datamodel/Order.java)
objects are examples of type `T`.
`Customer` uses `Long` as `ID`-type, the other two classes use `String` as `ID`-type.

<!-- - D2.a - [New Articles, Customers and Orders](#d2a-new-articles-customers-and-orders)

- D2.b - [*find()* method](#d2b-find-method)

- D2.c - [*printOrder()* method](#d2c-printorder-method)

- D2.d - [Order Value and Tax Calculations](#d2d-order-value-and-tax-calculations) -->



&nbsp;

## Release Preparation

### a.) Packaging the Application

The packaged application will be `bin/application-1.0.0-SNAPSHOT.jar`.

```sh
mk package                  # run packaging
ls -la bin                  # show result in bin directory
```

Output:

```
total 16
drwxr-xr-x 1      0 May  5 23:18 ./
drwxr-xr-x 1      0 May  5 23:17 ../
-rw-r--r-- 1 173615 May  5 23:18 application-1.0.0-SNAPSHOT.jar
drwxr-xr-x 1      0 May  5 23:17 classes/
drwxr-xr-x 1      0 May  5 23:17 resources/
drwxr-xr-x 1      0 May  5 23:17 test-classes/
```


### b.) Running the packaged Application

Run the packaged jar-file with:

```sh
find src/system

mk run-jar
java -jar bin/application-1.0.0-SNAPSHOT.jar
```

Final output of correct order table:

```
java application.Application
Hello, Application_F1
(6) Customer objects built.
(9) Article objects built.
(7) Order objects built.
---
Kunden:
+----------+----------------------------+--------------------------------------+
| Kund.-ID | Name                       | Kontakt                              |
+----------+----------------------------+--------------------------------------+
|   892474 | Meyer, Eric                | eric98@yahoo.com, (+1 contacts)      |
|   643270 | Bayer, Anne                | anne24@yahoo.de, (+2 contacts)       |
|   286516 | Schulz-Mueller, Tim        | tim2346@gmx.de                       |
|   412396 | Blumenfeld, Nadine-Ulla    | +49 152-92454                        |
|   456454 | Abdelalim, Khaled Saad Moha| +49 1524-12948210                    |
|   651286 | Neumann, Lena              | lena228@gmail.com                    |
+----------+----------------------------+--------------------------------------+

Artikel:
+----------+----------------------------+---------------+----------------------+
|Artikel-ID| Beschreibung               |      Preis CUR|  Mehrwertsteuersatz  |
+----------+----------------------------+---------------+----------------------+
|SKU-458362| Tasse                      |       2.99 EUR|  19.0% GER_VAT       |
|SKU-693856| Becher                     |       1.49 EUR|  19.0% GER_VAT       |
|SKU-518957| Kanne                      |      19.99 EUR|  19.0% GER_VAT       |
|SKU-638035| Teller                     |       6.49 EUR|  19.0% GER_VAT       |
|SKU-278530| Buch "Java"                |      49.90 EUR|   7.0% GER_VAT_REDU  |
|SKU-425378| Buch "OOP"                 |      79.95 EUR|   7.0% GER_VAT_REDU  |
|SKU-300926| Pfanne                     |      49.99 EUR|  19.0% GER_VAT       |
|SKU-663942| Fahrradhelm                |     169.00 EUR|  19.0% GER_VAT       |
|SKU-583978| Fahrradkarte               |       6.95 EUR|   7.0% GER_VAT_REDU  |
+----------+----------------------------+---------------+----------------------+

Bestellungen:
+----------+--------------------------------------------+----------------------+
|Bestell-ID| Bestellungen             MwSt*        Preis|     MwSt       Gesamt|
+----------+--------------------------------------------+----------------------+
|8592356245| Eric's Bestellung:                         |                      |
|          |  - 4 Teller, 4x 6.49     4.14     25.96 EUR|                      |
|          |  - 8 Becher, 8x 1.49     1.90     11.92 EUR|                      |
|          |  - 1 Buch "OOP"          5.23*    79.95 EUR|                      |
|          |  - 4 Tasse, 4x 2.99      1.91     11.96 EUR|    13.18   129.79 EUR|
+----------+--------------------------------------------+----------------------+
|3563561357| Anne's Bestellung:                         |                      |
|          |  - 2 Teller, 2x 6.49     2.07     12.98 EUR|                      |
|          |  - 2 Tasse, 2x 2.99      0.95      5.98 EUR|     3.02    18.96 EUR|
+----------+--------------------------------------------+----------------------+
|5234968294| Eric's Bestellung:                         |                      |
|          |  - 1 Kanne               3.19     19.99 EUR|     3.19    19.99 EUR|
+----------+--------------------------------------------+----------------------+
|6135735635| Nadine-Ulla's Bestel                       |                      |
|          |  - 12 Teller, 12x 6.    12.43     77.88 EUR|                      |
|          |  - 1 Buch "Java"         3.26*    49.90 EUR|                      |
|          |  - 1 Buch "OOP"          5.23*    79.95 EUR|    20.92   207.73 EUR|
+----------+--------------------------------------------+----------------------+
|6173043537| Lena's Bestellung:                         |                      |
|          |  - 1 Buch "Java"         3.26*    49.90 EUR|                      |
|          |  - 1 Fahrradkarte        0.45*     6.95 EUR|     3.71    56.85 EUR|
+----------+--------------------------------------------+----------------------+
|7372561535| Eric's Bestellung:                         |                      |
|          |  - 1 Fahrradhelm        26.98    169.00 EUR|                      |
|          |  - 1 Fahrradkarte        0.45*     6.95 EUR|    27.43   175.95 EUR|
+----------+--------------------------------------------+----------------------+
|4450305661| Eric's Bestellung:                         |                      |
|          |  - 3 Tasse, 3x 2.99      1.43      8.97 EUR|                      |
|          |  - 3 Becher, 3x 1.49     0.71      4.47 EUR|                      |
|          |  - 1 Kanne               3.19     19.99 EUR|     5.33    33.43 EUR|
+----------+--------------------------------------------+----------------------+
                                                 Gesamt:|    76.78   642.70 EUR|
                                                        +======================+
done.
```

The packaged `.jar`-file: `application-1.0.0-SNAPSHOT.jar` can now be distributed.

