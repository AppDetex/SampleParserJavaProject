#!/usr/bin/env bash

./scrape test/urls.txt | jq '.'

VAL=$(./scrape test/urls.txt | jq '.' | awk '/"Carcassonne"/ { print $0 }')
echo "Got: $VAL"

if [ -z "$VAL" ]; then
    echo "FAIL - Missing title"
    exit 3
fi

VAL=$(./scrape file-does-not-exist)
if [ $? -ne 0 ]; then
    echo "FAIL - Missing file not handled gracefully"
    exit 4
fi

VAL=$(./scrape "https://play.google.com/store/apps/details?id=com.exozet.android.catan")
VAL=$(echo $VAL | jq '.' | awk '/"Catan"/ { print $0 }')
echo "Got: $VAL"
if [ -z "$VAL" ]; then
    echo "FAIL - URL parameter"
    exit 5
fi

VAL=$(./scrape https://google.com)
if [ $? -ne 1 ]; then
    echo "FAIL - Unparseable page should error"
    exit 6
fi

echo "Tests ok"
