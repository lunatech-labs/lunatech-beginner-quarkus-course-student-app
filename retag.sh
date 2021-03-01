#!/bin/bash
set -e

git log --reverse --pretty=oneline|grep 'Solution of Exercise'| \

awk '{ cmd = sprintf("git tag -f exercise-%s-solution %s", $NF, $1);system(cmd); }'

git log --reverse --pretty=oneline|head -n 1|awk '{print $1}' | xargs git tag -f start 

