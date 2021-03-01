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


