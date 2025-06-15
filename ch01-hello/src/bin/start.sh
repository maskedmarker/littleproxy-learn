#!/bin/sh

nohup java -DlisteningPort=8888 -DroutingStrategy=roundrobin -DredirectAddress=192.168.175.1:3333,192.168.175.2:3333 -jar littleproxy.jar >/dev/null 2>&1 &
