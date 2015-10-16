#!/usr/bin/bash
# first parameter is fast, slow, medium
# second parameter is image path
abbyy=/opt/ABBYY/FREngine11/Samples/CommandLineInterface/CommandLineInterface
input=$2
pdf=tmp.pdf

if [ $1 = "fast" ]; then
   mode="--fastmode"
fi

if [ $1 = "slow" ]; then
   mode="--enableAggressiveTextExtraction"
fi

if [ $1 = "medium" ]; then
   mode="--enableTextExtractionMode"
fi

#sometimes can have a problem for pdftotext; have not output for the pdf output
#time $abbyy -ii -ido -ibw $mode -if $input -f PDF -of $pdf

$abbyy -ido -ibw $mode -if $input -f PDF -of $pdf
txt=tmp.txt
#echo $txt
pdftotext -layout $pdf $txt
cat $txt

#delete afterwards
rm $txt
rm $pdf
