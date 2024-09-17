#!/bin/bash

regex='^[0-9]+$'

read -p 'Your istID number (like 91234): ' ISTN

if ! [[ $ISTN =~ $regex ]]; then
    echo
    echo '/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/'
    echo The istID needs to be a number you dummy :P !!!
    echo '/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/'
    exit 1
fi

read -p "Your group number (like 012 or 123): " GROUPN

if ! [[ $GROUPN =~ $regex ]]; then
    echo
    echo '/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\'
    echo The group number needs to be a number you dummy :P !!!
    echo '/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\'
    exit 1
fi

echo Thx!!
echo Doing stuff for you now!!...
echo
echo export CVSROOT=:ext:ist1"$ISTN"@sigma.ist.utl.pt:/afs/ist.utl.pt/groups/leic-po/po19/cvs/"$GROUPN"
export CVSROOT=:ext:ist1"$ISTN"@sigma.ist.utl.pt:/afs/ist.utl.pt/groups/leic-po/po19/cvs/"$GROUPN"
echo
echo export CVS_RSH=ssh
export CVS_RSH=ssh
echo
echo done!!!
echo 'Next, do: '
echo cvs co project
