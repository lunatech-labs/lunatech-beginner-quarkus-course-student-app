# Lunatech Beginner Quarkus Course Student Repository

The repository is part of Lunatech's _Beginner Quarkus Course_. It contains 

* The skeleton of the application that students build during the course
* Some useful SQL files and templates that students can use while making this application.

The appliction is built during a set of exercises of the course. The exercises themselves *are not* part of this 
repository.

## Getting Started

You should start from the beginning:

    git checkout start -b exercises

And then do the exercises in [EXERCISES.md](EXERCISES.md)

## How it works

You can use this repository for two things:

1. As a source of some useful files, in the `materials` directory. This directory is references several times from the
exercises.
2. As a way to  _catch up_. Most exercises build on the previous exercise. If you are succesful in all exercises, you 
can build the entire application yourself. But if you fall behind, or fail to complete an exercise, you can checkout
   a tag from this repository, and this repository will contain the solution up to there.
   
For example, to throw away what you made, and get yourself back on track with the solution of exercise 5, run:

    git reset --hard exercise-5-solution

will get you into a state after exercise 5 has been completed, and with a code base ready to attack exercise #6.

Or, if you prefer to keep what you made, you can continue working on a new branch:

    git checkout exercise-5-solution -b my-new-branchname

## Update the project

Project solutions are managed with tags.
To update the project correctly I strongly recommend you to make multiple commits rather than a big one.
The reason is that there is a script in the project `retag.sh` that is creating all solutions tags according git log history.  
So to be sure that the modification you have done are in the correct exercise and tags you should follow those steps:

1. Make your changes in a dedicated branch that will be merged into main.
2. Once the branch is merged go on main branch and start an interactive rebase with this command: `git rebase -i --root` 
the `--root` allow you to rebase from the first commit of the project.
3. Place your modification commits where they belong. So for example a modification on exercise instruction should be 
placed after the first commit (it will be squash), and a modification on exercise solution should be placed 
after the commit of the exercise solution (e.g. Solution of Exercise 7).
4. When every commit are in place change the verb at the start of the line from `pick` to `fixup` it will squash 
with the precedent commit and keep the old commit message (to allow retag.sh to do it's job).
5. After this you can save and quit interactive rebase
6. Now your git history is clean and should be as the same as before. You can now execute `retag.sh`.
7. Now you can push your branch and tags by doing `git push -f` and `git push -f --tags `.

If the project has been updated and you want to fetch the new exercises tags : 

1. git fetch --force --tags
2. git pull --force --tags

 or 
1. remove the tags locally: git tag -l | xargs git tag -d
2. fetch the tags : git fetch --tags

Thanks for your contribution !

