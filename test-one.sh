#!/bin/bash

curl -w "%{http_code}" "$@" -s "http://localhost:48080/mywebapp1/api/h/i?user=hans&group=admin"
echo
