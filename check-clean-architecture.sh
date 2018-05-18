#!/usr/bin/env bash

# ###################################################

#   1) Looks for a "Domain" package
#       1.1) Loops on import lines in .java files
#       1.2) If imports are from java.* or *.Domain* : OK (KO Otherwise)

#   1) Looks for a "UseCases" package
#       1.1) Loops on import lines in .java files
#       1.2) If imports are from java.* or *.Domain* or *.UseCases* : OK (KO Otherwise)

# ###################################################


# ###################################################

#                     COLORS

# ###################################################
BLACK=$(tput setaf 0)
RED=$(tput setaf 1)
GREEN=$(tput setaf 2)
YELLOW=$(tput setaf 3)
LIME_YELLOW=$(tput setaf 190)
POWDER_BLUE=$(tput setaf 153)
BLUE=$(tput setaf 4)
MAGENTA=$(tput setaf 5)
CYAN=$(tput setaf 6)
WHITE=$(tput setaf 7)
BRIGHT=$(tput bold)
NORMAL=$(tput sgr0)
BLINK=$(tput blink)
REVERSE=$(tput smso)
UNDERLINE=$(tput smul)

# ###################################################

#            DIRECTORIES AND PACKAGE

# ###################################################
sourceDir="./src/main/java/com/example/fabrikam/TodoDemo"
domainDir=${sourceDir}"/Domain"
useCasesDir=${sourceDir}"/UseCases"
sourcePackageForRegex="com\.example\.fabrikam\.TodoDemo"
files=${domainDir}"/*.java"

# ###################################################

#                     REGEX

# ###################################################
regexAllImports="import.*"
regexCommonJavaImports="import[[:space:]]+java\..*"
regexDomainImports="import[[:space:]]+"${sourcePackageForRegex}"\.Domain\..*"
regexUseCasesImports="import[[:space:]]+"${sourcePackageForRegex}"\.UseCases\..*"
regexAuthorizedForDomain=${regexCommonJavaImports}"|"${regexDomainImports}
regexAuthorizedForUseCases=${regexCommonJavaImports}"|"${regexDomainImports}"|"${regexUseCasesImports}


# ###################################################

#                 UTILITY FUNCTIONS

# ###################################################

successToConsole() {
    echo ""
    echo ""
    echo "-----------------------------------------------------------------------------------"
    echo "Congratulations, your project is ${GREEN}Clean-Architecture® compliant${NORMAL}! 👍"
    echo "-----------------------------------------------------------------------------------"
}


failureToConsole() {
    echo ""
    echo ""
    echo "----------------------------------------------------------------------------------------------"
    echo "Too bad, your project is ${YELLOW}not Clean-Architecture® compliant yet${NORMAL}... ¯\_(ツ)_/¯"
    echo "----------------------------------------------------------------------------------------------"
}

positiveToConsole() {
    messageToLog=$1
    indentationIndex=$2
    indentationPrefix=""

    for (( i=0; i<$indentationIndex; i++ ))
    do
        indentationPrefix=${indentationPrefix}"   "
    done

    echo "${GREEN}${indentationPrefix}✅  > ${messageToLog}${NORMAL}"
}

warningToConsole() {
    messageToLog=$1
    indentationIndex=$2
    indentationPrefix=""

    for (( i=0; i<$indentationIndex; i++ ))
    do
        indentationPrefix=${indentationPrefix}"   "
    done

    echo "${YELLOW}${indentationPrefix}⚠️  > ${messageToLog}${NORMAL}"
}

negativeToConsole() {
    messageToLog=$1
    indentationIndex=$2
    indentationPrefix=""

    for (( i=0; i<$indentationIndex; i++ ))
    do
        indentationPrefix=${indentationPrefix}"   "
    done

    echo "${RED}${indentationPrefix}❌  > ${messageToLog}${NORMAL}"
}
infoToConsole() {
    messageToLog=$1
    indentationIndex=$2
    indentationPrefix=""

    for (( i=0; i<$indentationIndex; i++ ))
    do
        indentationPrefix=${indentationPrefix}"   "
    done

    echo "${indentationPrefix}> ${messageToLog}"
}


# ###################################################

#                 PARSING FUNCTIONS

# ###################################################
checkLine() {
    line=$1
    regexAuthorizedImportForDirectory=$2

    if [[ $1 =~ $regexAllImports ]]
    then
        message="Found import: \""$1"\""
        if [[ $line =~ $regexAuthorizedImportForDirectory ]]
        then
           message="Authorized import : \"$1\""
           positiveToConsole "$message" 2
           return 1
        else
            message="Unauthorized import : \"$1\""
            negativeToConsole "$message" 2
            return 0
        fi
    else
       return 1
    fi
}

checkDirectory() {
    files=$1"/*.java"
    regexAuthorizedForDirectory=$2
    isCleanArchi=1
    for file in ${files}
    do
        infoToConsole "Checking file \"$file\"" 1
        while read line
        do
            checkLine "${line}" ${regexAuthorizedForDirectory}
            if [ $? -eq 0 ]
            then
                isCleanArchi=0
            fi
        done < ${file}
    done
    return ${isCleanArchi}
}

# ###################################################

#                        SCRIPT

# ###################################################
projectIsClean=1
infoToConsole "[DOMAIN]" 0
if [ -d ${domainDir} ]; then
   checkDirectory ${domainDir} ${regexAuthorizedForDomain}
   if [ $? -eq 0 ]
   then
       projectIsClean=0
   fi
else
    projectIsClean=0
    warningToConsole "\"Domain\" package not found in $domainDir" 1
fi

infoToConsole "[USECASES]" 0
if [ -d ${useCasesDir} ]; then
   checkDirectory ${useCasesDir} ${regexAuthorizedForUseCases}
   if [ $? -eq 0 ]
   then
       projectIsClean=0
   fi
else
    projectIsClean=0
    warningToConsole "\"UseCases\" package not found in $useCasesDir" 1
fi

if [ $projectIsClean -eq 1 ]
then
    successToConsole
    exit 0
else
    failureToConsole
    exit 1
fi

