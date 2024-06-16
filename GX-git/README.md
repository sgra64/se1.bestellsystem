<!-- 
<img src="https://maven.apache.org/images/maven-logo-black-on-white.png" alt="drawing" width="200"/>

Build GX-main branch:
---------------------
git clone -b main --single-branch git@github.com:sgra64/se1.bestellsystem.git GX-main
cd GX-main
git remote add se1-origin https://github.com/sgra64/se1.bestellsystem.git

git fetch se1-origin C12-Customer:C12-Customer
git fetch se1-origin D12-Datamodel:D12-Datamodel
git fetch se1-origin F12-Refactoring:F12-Refactoring

# merge content of fetched branch into current branch
git merge --allow-unrelated-histories --strategy-option theirs C12-Customer
git merge --allow-unrelated-histories --strategy-option theirs D12-Datamodel
git merge --allow-unrelated-histories --strategy-option theirs F12-Refactoring

# incorporate add-ons:
tar xvf ../D12-Datamodel_complete.tar
tar xvf ../F12-Refactoring_complete.tar

git add .
git commit -m "GX: working version after refactoring"
git tag gx

 -->

# Aufgaben G1234: Source Code Management mit *git*

The assignment will demonstrate the use of the
[*git*](https://www.git-scm.com)
Source Code Management (SCM) system.

Content:
- [G1: Local *git* Repository](#g1-local-git-repository) - (3 pt)

- [G2: Local Commits, Local Branches](#g2-local-commits-local-branches) - (3 pt)

- [G3: Own Remote Repository](#g3-own-remote-repository) - (2 pt)

- [G4: Branch Integration: *feat.730-json-data*](#g4-branch-integration-feat730-json-data) - (4 pt)
<!-- 
- [G5: Structured Branches](#g5-structured-branches) - (3 pt)

- [G6: Feature Branch *feat.728* *LabelPrinter*](#g6-feature-branch-feat728-labelprinter) - (4 pt)

- [G7: Feature Branch *feat.730*: *Customer Addresses*](#g7-feature-branch-feat730-customer-addresses) - (6 pt)

- [G8: Integration (Merge) of Feature Branches](#g8-integration-merge-of-feature-branches) - (6 pt) -->


&nbsp;

---
## G1: Local *git* Repository

Create a new directory and `cd` into it:

```sh
mkdir disney                        # create directory 'disney'
cd disney                           # change into directory
```

Create two files:

 - `tom.txt` with content: *"Tom is a cat."*

 - `jerry.txt` with content: *"Jerry is a dog."*

```sh
ls -ls                              # show files
```
```
-rw-r--r-- 1 svgr2 Kein 15 Jun  9 22:08 jerry.txt
-rw-r--r-- 1 svgr2 Kein 13 Jun  9 22:08 tom.txt
```

No remote repository is neccessary for using *git*.
Create a local `git`-repository:

```sh
git init                            # create local git repository
```

A new local directory `.git` is created in the directory. This directory
contains the local *git* repository.

The current status of this repository is: 

```sh
git status                          # show status of the local repository
```
```
On branch main          <-- we are on the (default) main branch

No commits yet          <-- no commits yet

Untracked files:        <-- two untracked files
  (use "git add <file>..." to include in what will be committed)
        jerry.txt
        tom.txt
```

Untracked (unknown to git) or changed files are shown in red color.

Stage files first and then commmit:

```sh
git add tom.txt jerry.txt           # stage (prepare) files for commit
```

Show changed status:

```sh
git status                          # show status of the local repository
```

Staged files are shown in green color:

```
Changes to be committed:
  (use "git rm --cached <file>..." to unstage)
        new file:   jerry.txt
        new file:   tom.txt
```

Commit:

```sh
git commit -m "add new files: tom.txt, jerry.txt"
```
```
[main (root-commit) ccfeb68] add new files: tom.txt, jerry.txt
 2 files changed, 2 insertions(+)
 create mode 100644 jerry.txt
 create mode 100644 tom.txt
```

Show changed status:

```sh
git status                          # show status of the local repository
```

The status of the (working) directory is now *clean*, which means there are
no differences in all files of the (working) directory to the files in the
last commit:

```
On branch main
nothing to commit, working tree clean
```

Show the commit in the log:

```sh
git log --oneline
```

The log shows one commit. *HEAD* points to the last commit of the *main* branch:

```
ccfeb68 (HEAD -> main) add new files: tom.txt, jerry.txt
```

Create file:

 - `goofy.txt` with content: *"Goofy is an elephant."*

Commit the new file and show the log:

```sh
git log --oneline
```

The log now shows two commits:

```
ab3026a (HEAD -> main) add new file: goofy.txt
ccfeb68 add new files: tom.txt, jerry.txt
```

Correct file `goofy.txt`: *"Goofy is not an elephant, Goofy is a dog."*

There are two ways to fix the error in `goofy.txt`:

1. commit the new state of `goofy.txt`, which leaves the prior (wrong)
    file in the commit history.

1. remove the last commit, fix the error and commit the corrected version.

Commits can only be removed backwards from the end. Commit cannot be removed
from the begin or within the commit chain.

Use the second approach and correct the error. Use commit message: *"correct file goofy.txt added"*.

```sh
git reset HEAD~1                    # remove last commit (HEAD ~ minus 1 back)
```

Commit the correct version and show the commit history:

```sh
git log --oneline
```
```
3fe2477 (HEAD -> main) correct file goofy.txt added     <-- updated commit message
ccfeb68 add new files: tom.txt, jerry.txt
```


&nbsp;

## G2: Local Commits, Local Branches

Starting point of this assignment is the state of the project after
refactoring, see branch
[F12-Refactoring](https://github.com/sgra64/se1.bestellsystem/tree/F12-Refactoring).

Content should be on the local `main` branch:

```sh
git branch -avv                     # show all branches
```

The star (*) shows the current branch.

```
  C12-Customer                      caf113a update application/Runtime.java
  D12-Datamodel                     d415a06 update application/Runtime.java
  F12-Refactoring                   9562dfd update PrinterImpl.java, made tables...
* main                              78751a5 [origin/main: ahead 32] Merge branch 'F12-Refactoring'
  remotes/origin/HEAD               -> origin/main
  remotes/origin/main               86c7459 update application/Runtime.java
  remotes/se1-origin/C12-Customer   caf113a update application/Runtime.java
  remotes/se1-origin/D12-Datamodel  d415a06 update application/Runtime.java
  remotes/se1-origin/F12-Refactoring 9562dfd update PrinterImpl.java, made tables...
```

Show the commit history of the branch (which is mainly inherited from
the merged branch in the Refactoring assignment):

```sh
git log --oneline                       # show commit history
```
```
78751a5 (HEAD -> main) Merge branch 'F12-Refactoring'
7944f63 Merge branch 'D12-Datamodel'
08fbe45 Merge branch 'C12-Customer'
9562dfd (se1-origin/F12-Refactoring, F12-Refactoring) update PrinterImpl.java...
...
a01b457 initial check-in F12-Refactoring
ac485bb update .env/setenv.sh, fixed mk javadoc
...
2c438ae commit initial project files and sources
f5659e4 base commit, .gitignore only
```

Compare the last two commits:

```sh
# compare changes from current commit to base of F12-Refactoring branch
git diff a01b457..HEAD --name-status
```
```
...
M       resources/application.properties        ; M: modified file
A       src/application/Application_F1.java     ; A: added file
A       src/system/Calculator.java
A       src/system/DataBuilder.java
A       src/system/DataFactory.java
...
```

Check to remove none-Java files that are no longer needed:

```sh
find src | grep -v ".java"
```
```
src
src/application
src/datamodel
src/datamodel/Customer.mdj      <-- file to remove
src/datamodel/Datamodel.mdj     <-- file to remove
src/datamodel/Datamodel.png     <-- file to remove
src/system
src/system/impl
```

Remove files (adjust if you have other files):

```sh
rm src/datamodel/Customer.mdj
rm src/datamodel/Datamodel.mdj
rm src/datamodel/Datamodel.png

git status              # removed files show

# stage deleted files
git add src/datamodel/Customer.mdj
git add src/datamodel/Datamodel.mdj
git add src/datamodel/Datamodel.png

# commit
git commit -m "delete obsolete files"
```

Change `Version` in `src/application/package-info.java` to `"1.0.0-SNAPSHOT-GX"`:

```java
class package_info {
    static final String Author = "sgraupner";
    static final String Version = "1.0.0-SNAPSHOT-GX";
}
```

Commit this change.

```sh
git log --oneline
```
```
d87b4f1 (HEAD -> main) update version "1.0.0-SNAPSHOT-GX" application/package-info.java
55a2728 delete obsolete files
7e3f908 (tag: gx) GX: working version after refactoring
```


&nbsp;

---
## G3: Own Remote Repository

Read about
[*Working with Remotes*](https://git-scm.com/book/en/v2/Git-Basics-Working-with-Remotes).

Log into your *GitLab*-account at 
[https://gitlab.bht-berlin.de](https://gitlab.bht-berlin.de).

If not already, store your *public key* in your account (open: *User Settings / SSH Keys* ).
Storing a *public key* in the repository server will prevent the repository server from
asking for passwords for *pull*, *fetch* or *pull* operations.


### G3.a) Create a new repository

Create a new repository: `se1-bestellsystem` in your *GitLab* account.
This will be your *"remote repository"* for this assignment.

Mind that the repository you have received branches from is a different repository
(`https://github.com/sgra64/se1.bestellsystem`) with local name: `se1-origin`.

Your remote repository will be called `origin` (the default name for a remote).


#### G3.b) Store public key in repository account

Git uses two types of *"git-links"* pointing to remote repositories:

- *ssh-Link* -- `git@gitlab.bht-berlin.de:<account>/se1.bestellsystem.git`

    - Uses *ssh* authentication for repository access that is based on *ssh*
    (public, private) key pairs.

    - Find local *ssh* (public, private) key pairs in the `.ssh` directory in your
    home directory.

    - Use tool `ssh-keygen` to generate a (public, private) key pair. Use the public
    key (file: `id_rsa.pub`) to register under SSH keys at the remote repository.


- *HTTP-Link* -- `https://gitlab.bht-berlin.de/<account>/se1.bestellsystem.git`

    - Uses password authentication.


### G3.c) Add new project to your local repository

Add the link to your new remote project to your local repository:

```sh
# add ssh-link to your local repository under name 'origin'
git remote add origin git@gitlab.bht-berlin.de:<account>/se1.bestellsystem.git

git remote -v           # show all remotes
```

Remote *origin* is your remote repository. Remote *se1-origin* is the *"downstream"*
repository where you received branches from in previous assignments.

```
origin  git@gitlab.bht-berlin.de:<account>/se1.bestellsystem.git (fetch)
origin  git@gitlab.bht-berlin.de:<account>/se1.bestellsystem.git (push)
se1-origin      https://github.com/sgra64/se1.bestellsystem.git (fetch)
se1-origin      https://github.com/sgra64/se1.bestellsystem.git (push)
```


### G3.d) Create the connection between local and remote branch

Adding a remote link is not sufficient to push commits to a remote.

A relationship must be establised between a local branch (exist in your local
repository in the `.git` repository) and a branch that exist on the remote
repository server.

Since the `main` branch exists in your local repository and also on the
remote repository server, the connection can be established with:

```sh
git fetch origin main

git branch --set-upstream-to=origin/main main
```
```
branch 'main' set up to track 'origin/main'.
```

The new relationship can be seen:

```sh
git branch -avv
```

Local branch `main` is now *"tracking"* the remote branch `origin/main`
(mind the blue marking).

```
gt br
* main                ec26651 [origin/main: ahead 1, behind 4] first commit
  remotes/origin/main 854bc2b update README.md, add section 5 Workflow with Remotes
```

Pull the remote branch to synchronize the local branch to the state of the
remote branch. Both branches are not yet related, which requires the
`--allow-unrelated-histories` option:

```sh
git pull --allow-unrelated-histories
```

Push commits of the local branch `main` to the remote:

```sh
git push
```

No password should be requested and no *"push conflict"* should occur.

New local branches that do not yet exist on the remote repository server can
simply be pushed:

```sh
git push -u origin main
```


&nbsp;

---
## G4: Branch Integration: *feat.730-json-data*

Branch *feat.730-json-data* enables a new feature to load data-objects (*Customer*, *Article*
and *Orders*) from *JSON files* replacing object creation in
[Application_F1.java](https://github.com/sgra64/se1.bestellsystem/blob/F12-Refactoring/src/application/Application_F1.java)
(before version).

The updated file (after integration of branch *feat.730-json-data*) will no longer
contain object creations:
[Application_F1.java](https://github.com/sgra64/se1-dev/blob/feat.730-json-data/src/application/Application_F1.java)
(after version).

Instead, data will be obtained from JSON files in the new
[data](https://github.com/sgra64/se1-dev/tree/feat.730-json-data/data) directory.

JSON file [data/customers.json](https://github.com/sgra64/se1-dev/blob/feat.730-json-data/data/customers.json)
contains *Customer* data:

```json
[
  {"id": 892474, "name": "Eric Meyer", "contacts": ["eric98@yahoo.com", "(030) 3945-642298"] },
  {"id": 643270, "name": "Bayer, Anne", "contacts": ["anne24@yahoo.de", "(030) 3481-23352", "fax: (030)23451356"] },
  {"id": 286516, "name": "Tim Schulz-Mueller", "contacts": ["tim2346@gmx.de"] },
  {"id": 412396, "name": "Nadine-Ulla Blumenfeld", "contacts": ["+49 152-92454"] },
  {"id": 456454, "name": "Khaled Saad Mohamed Abdelalim", "contacts": ["+49 1524-12948210"] },
  {"id": 651286, "name": "Lena Neumann", "contacts": ["lena228@gmail.com"] }
]
```

Complete the
[steps for the assinment](https://github.com/sgra64/se1-dev/blob/feat.730-json-data/README.md).
