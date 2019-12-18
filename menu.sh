#!/bin/bash
# Bash Menu Script Example
# http://askubuntu.com/questions/1705/how-can-i-create-a-select-menu-in-a-shell-script

PS3='Input your option: '
options=("compile" "updateSchema" "sql-query" "sql-update" "test" "deployWar" "zip" "version" "Quit")

select opt in "${options[@]}"
do

case $opt in
        "compile")
            ant compile
            ;;
		"updateSchema")
			ant updateSchema
            ;;
		"deployWar")
            ant deployWar
			;;
		"test")
			ant test 
			;;
		"zip")
			ant zip
			;;
		"sql-query")
			ant sql-query
			;;
		"sql-update")
			ant sql-update
			;;
        "version")
			echo "OpenXava 6.2"
            ;;
		"Quit")
            break
            ;;
        *) echo invalid option;;
    esac
done

