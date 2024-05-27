<!-- 
    README.md in branch C12-Customer
 -->

# Aufgabe C12: *Customer - Class*

The assignment implements the *Customer.java* class.

Content:

 1. [Setup](#1-setup)

 2. [Building the Application](#2-building-the-application)

 3. [Running the Application](#3-running-the-application)

 4. [Running JUnit Tests](#4-running-junit-tests)

 5. [Generating Javadoc](#5-generating-javadoc)

 6. [Packaging the Application](#6-packaging-the-application)

 7. [Running the Packaged Application](#7-running-the-packaged-application)


&nbsp;
## 1. Setup

Fetch branch `C12-Customer` into project `se1.bestellsystem` from the remote repository:

```sh
cd se1.bestellsystem            # change into project directory

[ -d .git ] || git init         # initialize git, if project is not yet under git control

# add link named 'se1-origin' pointing to the remote distribution repository
git remote add se1-origin https://github.com/sgra64/se1.bestellsystem.git

# fetch branch D12-Datamodel from the remote repository
git fetch se1-origin C12-Customer:C12-Customer

# merge content of fetched branch into current branch
git merge --allow-unrelated-histories --strategy-option theirs C12-Customer
```
```
git fetch se1-origin C12-Customer:C12-Customer
remote: Enumerating objects: 64, done.
remote: Counting objects: 100% (64/64), done.
remote: Compressing objects: 100% (33/33), done.
remote: Total 53 (delta 26), reused 47 (delta 20), pack-reused 0
Unpacking objects: 100% (53/53), 21.68 KiB | 60.00 KiB/s, done.
From https://github.com/sgra64/se1.bestellsystem
 * [new branch]      C12-Customer -> C12-Customer
 * [new branch]      C12-Customer -> se1-origin/C12-Customer

git merge --allow-unrelated-histories --strategy-option theirs C12-Customer
Merge made by the 'ort' strategy.
 README.md                                          | 432 ++++------
 src/application/Application_C1.java                | 273 +++++++
 src/application/Runtime.java                       |   6 +-
 src/application/package-info.java                  |   2 +-
 src/datamodel/Customer.java                        |  60 ++
 src/datamodel/Customer.mdj                         | 894 +++++++++++++++++++++
 src/datamodel/package-info.java                    |  12 +
 src/module-info.java                               |   1 +
 .../datamodel/Customer_100_Constructor_Tests.java  |  93 +++
 tests/datamodel/Customer_200_SetId_Tests.java      | 102 +++
 tests/datamodel/Customer_300_SetName_Tests.java    | 161 ++++
 tests/datamodel/Customer_400_Contacts_Tests.java   | 214 +++++
 .../Customer_500_SetNameExtended_Tests.java        | 182 +++++
 13 files changed, 2156 insertions(+), 276 deletions(-)
 create mode 100644 src/application/Application_C1.java
 create mode 100644 src/datamodel/Customer.java
 create mode 100644 src/datamodel/Customer.mdj
 create mode 100644 src/datamodel/package-info.java
 create mode 100644 tests/datamodel/Customer_100_Constructor_Tests.java
 create mode 100644 tests/datamodel/Customer_200_SetId_Tests.java
 create mode 100644 tests/datamodel/Customer_300_SetName_Tests.java
 create mode 100644 tests/datamodel/Customer_400_Contacts_Tests.java
 create mode 100644 tests/datamodel/Customer_500_SetNameExtended_Tests.java
```
<!-- 
`FETCH_HEAD` points to the fetched branch. Next is to create a local branch
(of same name) that has the same content.


```sh
git checkout FETCH_HEAD -b C12-Customer     # create local branch C12-Customer
git branch -avv
```
```
Switched to a new branch 'C12-Customer'
* C12-Customer                 39a322b initial src, test for C12-Customer Customer
  main                2c438ae [origin/main] commit initial project files and sources
  remotes/origin/HEAD -> origin/main
  remotes/origin/main 2c438ae commit initial project files and sources
```

We are now on a new local branch `C12-Customer` (*) with the fetched content. -->

```sh
find src tests                  # show content of src and test
```
```
src
src/application
src/application/Application.java
src/application/Application_C1.java     <-- new driver
src/application/package-info.java
src/application/Runtime.java
src/datamodel                           <-- new package
src/datamodel/Customer.java             <-- preliminary Customer class
src/datamodel/Customer.mdj              <-- UML class diagram (StarUML)
src/datamodel/package-info.java
src/module-info.java
tests
tests/application
tests/application/Application_0_always_pass_Tests.java
tests/datamodel                         <-- new JUnit tests
tests/datamodel/Customer_100_Constructor_Tests.java
tests/datamodel/Customer_200_SetId_Tests.java
tests/datamodel/Customer_300_SetName_Tests.java
tests/datamodel/Customer_400_Contacts_Tests.java
tests/datamodel/Customer_500_SetNameExtended_Tests.java
```

&nbsp;

Source the project:

```sh
source .env/setenv.sh
```


&nbsp;

## 2. Building the Application

Compile code with:

```sh
mk clean compile compile-tests
```

The result is in the `bin` directory:

```sh
find bin
```

Output

```
bin
bin/classes
bin/classes/application
bin/classes/application/Application.class
bin/classes/application/Application_C1$TableFormatter.class
bin/classes/application/Application_C1.class
bin/classes/application/package_info.class
bin/classes/application/Runtime$1.class
bin/classes/application/Runtime$SupplierWithExceptions.class
bin/classes/application/Runtime.class
bin/classes/datamodel
bin/classes/datamodel/Customer.class
bin/classes/module-info.class
bin/resources
bin/resources/application.properties
bin/resources/logging.properties
bin/test-classes
bin/test-classes/application
bin/test-classes/application/Application_0_always_pass_Tests.class
bin/test-classes/datamodel
bin/test-classes/datamodel/Customer_100_Constructor_Tests.class
bin/test-classes/datamodel/Customer_200_SetId_Tests.class
bin/test-classes/datamodel/Customer_300_SetName_Tests.class
bin/test-classes/datamodel/Customer_400_Contacts_Tests.class
bin/test-classes/datamodel/Customer_500_SetNameExtended_Tests.class
```


&nbsp;
## 3. Running the Application

[Application_C1.java](https://github.com/sgra64/se1.bestellsystem/blob/C12-Customer/src/application/Application_C1.java)
creates *Customer* objects:

```java
final Customer eric = new Customer("Eric Meyer")
    .setId(892474L)     // set id, first time
    .setId(947L)        // ignored, since id can only be set once
    .addContact("eric98@yahoo.com")
    .addContact("eric98@yahoo.com") // ignore duplicate contact
    .addContact("(030) 3945-642298");

final Customer anne = new Customer("Bayer, Anne")
    .setId(643270L)
    .addContact("anne24@yahoo.de")
    .addContact("(030) 3481-23352")
    .addContact("fax: (030)23451356");
...
```

and prints a table with *Customer* information:

```sh
mk run
java application.Application
```

Output (table is initially empty):

```
+------+---------------------------------+----------------------------------+
|ID    | NAME                            | CONTACTS                         |
+------+---------------------------------+----------------------------------+
|892474| Meyer, Eric                     | eric98@yahoo.com, (+1 contacts)  |
|643270| Bayer, Anne                     | anne24@yahoo.de, (+2 contacts)   |
|286516| Schulz-Mueller, Tim             | tim2346@gmx.de                   |
|412396| Blumenfeld, Nadine-Ulla         | +49 152-92454                    |
|456454| Abdelalim, Khaled Saad Mohamed  | +49 1524-12948210                |
+------+---------------------------------+----------------------------------+
```


&nbsp;
## 4. Running JUnit Tests

Implement methods in
[Customer.java](https://github.com/sgra64/se1.bestellsystem/blob/C12-Customer/src/application/Customer.java)
starting with Constructors.

Add Tests one-after-another as you progress with implementations.

Complete one test (showing the correct implementation) before continuing with the next test.

```sh
mk compile-tests
```

Complete constructor tests first:

```sh
java -jar branches/libs/libs/junit-platform-console-standalone-1.9.2.jar \
    $(eval echo $JUNIT_OPTIONS) \
    -c application.Application_0_always_pass_Tests \
    -c datamodel.Customer_100_Constructor_Tests
```

Add more tests as you progress with the implementation:

```
    -c datamodel.Customer_100_Constructor_Tests \
    -c datamodel.Customer_100_Constructor_Tests \
    -c datamodel.Customer_200_SetId_Tests \
    -c datamodel.Customer_300_SetName_Tests \
    -c datamodel.Customer_400_Contacts_Tests \
    -c datamodel.Customer_500_SetNameExtended_Tests
```

The full test-suite of *Customer* tests runs

```sh
mk run-tests
```

Output:

```
├─ JUnit Jupiter ✔
│  └─ Application_0_always_pass_Tests ✔
│     ├─ test_001_always_pass() ✔
│     └─ test_002_always_pass() ✔
|  ...
|
├─ JUnit Vintage ✔
└─ JUnit Platform Suite ✔

Test run finished after 142 ms
[        55 tests successful      ]   <-- 55 tests successful
[         0 tests failed          ]   <--  0 tests failed
done.
```

Run JUnit-Tests also in your IDE.


&nbsp;
## 5. Generating Javadoc

Build the javadoc for the project.

```sh
mk javadoc
```

Output:

```
Loading source files for package application...
Loading source files for package datamodel...
Constructing Javadoc information...
Creating destination directory: "docs\"
Building index for all the packages and classes...
Standard Doclet version 21+35-LTS-2513
Building tree for all the packages and classes...
Generating docs\se1.bestellsystem\application\Application.html...
Generating docs\se1.bestellsystem\application\Application_C1.html...
Generating docs\se1.bestellsystem\datamodel\Customer.html...
Generating docs\se1.bestellsystem\application\Runtime.html...
Generating docs\se1.bestellsystem\application\package-summary.html...
Generating docs\se1.bestellsystem\application\package-tree.html...
Generating docs\se1.bestellsystem\datamodel\package-summary.html...
Generating docs\se1.bestellsystem\datamodel\package-tree.html...
Generating docs\se1.bestellsystem\module-summary.html...
Generating docs\overview-tree.html...
Building index for all classes...
Generating docs\allclasses-index.html...
Generating docs\allpackages-index.html...
Generating docs\index-all.html...
Generating docs\search.html...
Generating docs\index.html...
Generating docs\help-doc.html...
done.
```

Open `docs/index.html` in a browser.


&nbsp;
## 6. Packaging the Application

The packaged application will be `bin/application-1.0.0-SNAPSHOT.jar`.

```sh
mk package                  # run packaging
ls -la bin                  # show result in bin directory
```

Output:

```
total 16
drwxr-xr-x 1     0 May  5 23:18 ./
drwxr-xr-x 1     0 May  5 23:17 ../
-rw-r--r-- 1 20742 May  5 23:18 application-1.0.0-SNAPSHOT.jar
drwxr-xr-x 1     0 May  5 23:17 classes/
drwxr-xr-x 1     0 May  5 23:17 resources/
drwxr-xr-x 1     0 May  5 23:17 test-classes/
```


&nbsp;
## 7. Running the Packaged Application

Run the packaged jar-file with:

```sh
mk run-jar
java -jar bin/application-1.0.0-SNAPSHOT.jar
```

Output:

```
Hello, Application_C1
+------+---------------------------------+----------------------------------+
|ID    | NAME                            | CONTACTS                         |
+------+---------------------------------+----------------------------------+
|892474| Meyer, Eric                     | eric98@yahoo.com, (+1 contacts)  |
|643270| Bayer, Anne                     | anne24@yahoo.de, (+2 contacts)   |
|286516| Schulz-Mueller, Tim             | tim2346@gmx.de                   |
|412396| Blumenfeld, Nadine-Ulla         | +49 152-92454                    |
|456454| Abdelalim, Khaled Saad Mohamed  | +49 1524-12948210                |
+------+---------------------------------+----------------------------------+
```

The packaged .jar file can now be distributed.
