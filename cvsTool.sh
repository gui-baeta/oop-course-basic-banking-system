#!/bin/bash

regex='^[0-9]+$'
RT=$'\n'
NL=$'\r'

printMenu() {
    case $1 in
        'Start menu' )
            echo
            echo 'vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv'
            echo 'PlEASE, FOR THE LOVE OF TRIBOLET!!! REAAAAAAAAD THISSSSSS'
            echo
            echo 'Please make sure this executable (cvsTool.sh) is right outside of the cvs repository'
            echo 'Example:'
            echo 'If the cvs repository starts with the folder "project/" and that same folder'
            echo 'is inside parentProjectDir/ ( So, it would be like parentProjectDir/project/ ), put cvsTool.sh inside parentProjectDir/' '^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^'
            echo
            ;;
        'Main menu' )
            echo ----------------------------------------------------------------------
            echo 'What do you want to do!?'
            echo
            echo '0 - I want to exit from this tool'
            echo '1 - stage every single file added ( like git add --all )'
            echo '2 - commit everything to the holy DM, David Matos ( like git commit )'
            echo '3 - check current status ( like git status )'
            echo '4 - update local stuff ( like git pull )'
            echo '... - I dunno, give me ideas damned!!'
            echo ----------------------------------------------------------------------
            echo
            ;;
    esac
}

handleOptions() {

    if ! [[ $1 =~ $regex ]]; then
        echo
        echo '  You never learn do you...'
        echo '  There are digits ( like 3 )'
        echo '  And there are letters ( like c )'
        echo
        echo '  YOU NEED TO TYPE A DIGIIIIIITTTTT!!!!!!!!!!!!'
        echo
    else

        case $1 in
            0 )
                echo
                echo 'Ok... :( bye!'
                echo
                exit 0
                ;;
            1 )
                stageStuff
                ;;
            2 )
                commitStuff
                ;;
            3 )
                checkStuff
                ;;
            4 )
                updateStuff
                ;;
            *)
                echo
                echo 'Either the developer is a dumbass or you need glasses...'
                echo
                sleep 2
                ;;
        esac
    fi
}

stageStuff() {

    echo 'One seccc...'
    ./expect.sh $secretPass project status "" &> temp.txt ; cat temp.txt | grep '? ' | sed 's/? //' > temp2.txt
    sed '/.jar/d;/.class/d;/.DS_Store/d;/testes/d;/MANIFEST/d;/.import/d;/.txt/d;/.out/d' temp2.txt > to_add.txt
    rm -f temp.txt

    if ! [[ -s to_add.txt ]]; then
        echo
        echo "Everything's added!!"
        echo
        rm -f to_add.txt
    else
        echo 'List of tracked paths, including, possibly, irrelevant information:'
        echo
        cat temp2.txt
        echo
        echo '\\\\\\\\\\\\\\\\\\\\\\\\\\\'
        echo 'Stuff to be added:'
        echo
        cat to_add.txt
        echo
        echo '///////////////////////////'
        while read object; do
            object=${object%$RT}
            object=${object%$NL}
            echo '---'
            echo $object
            echo '---'
            ./expect.sh $secretPass project add "$object"
            echo '\\\\\\\\\\\\\\\\\\\\\\\\\\\'
            echo 'Files/Paths to be added:'
            echo
            cat to_add.txt
            echo
            echo '///////////////////////////'
            echo
        done <to_add.txt

        rm to_add.txt
        rm temp2.txt

        echo
        echo "Going for another run, probably found a few folders to add first..."
        echo

        stageStuff
    fi
}

commitStuff() {
    read -p 'Commit message: ' commitmessage
    cvs commit "-m $commitmessage"
    echo
}

checkStuff() {
    ./expect.sh $secretPass project status "" &> temp.txt ; cat temp.txt | grep '? ' | sed 's/? //' > temp2.txt
    sed '/.jar/d;/.class/d;/.DS_Store/d;/testes/d;/MANIFEST/d;/.import/d;/.txt/d;/.out/d' temp2.txt > to_add.txt

    if ! [[ -s to_add.txt ]]; then
        echo
        echo "Everything is added!!"
        echo
    else
        echo 'List of tracked paths, including, possibly, irrelevant information:'
        echo
        cat temp2.txt
        echo
        echo '\\\\\\\\\\\\\\\\\\\\\\\\\\\'
        echo 'Stuff to be added:'
        echo
        cat to_add.txt
        echo
        echo '///////////////////////////'
        echo
        echo 'Suggestion: Choose option 1 to add above stuff!'
        echo
    fi

    rm -f to_add.txt
    rm -f temp2.txt
    rm -f temp.txt
}

updateStuff() {
    ./expect.sh $secretPass project update ""
    echo
}

infoNow() {
    pwd > startdir.txt
    startdir=$(head -n 1 startdir.txt)
    rm -f startdir.txt

    echo
    echo "Password for the script to auto write it every time there is a cvs command."
    echo
    echo "P.S.: The password will only be used inside expect.sh"
    echo "P.S.: You need to make sure you have the package 'expect' installed"
    echo

    asking=1
    while [[ $asking == 1 ]]; do
        echo 'SHHHHHhhhhhhhh!!!!!!'
        read -sp 'Very secret password: ' secretPass
        echo
        asking=0
    done

}

printMenu 'Start menu'

infoNow

while [[ 1 ]]; do

    printMenu 'Main menu'

    read -p '>>> ' option

    handleOptions $option

done
