#!/usr/bin/env bash


usage() { echo "Usage: $0 [-u unit tests only] [-i integration tests only] [-h help]" 1>&2; exit 1; }

while getopts "uih" option; do
    case "${option}" in
        u)
            ./mvnw clean
            ./mvnw test -Dgroups="com.example.fabrikam.TodoDemo.FastTests" -e
            exit 0
            ;;
        i)
            ./mvnw clean
            ./mvnw test -Dgroups="com.example.fabrikam.TodoDemo.SlowTests" -e
            exit 0
            ;;
        h)
            usage
            ;;
        *)
            usage
            ;;
    esac
done
./mvnw clean
./mvnw test -e