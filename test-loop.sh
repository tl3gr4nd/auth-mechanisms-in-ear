#!/bin/bash

for i in {1..50}
do
	./redeploy.sh
	./test-one.sh
done
