docker pull mongo:4.4.14

docker run --name todd-mongo -v /Users/todd/mongodata:/data/db -p 27017:27017 -d mongo:4.4.14

docker container start 87d01d5bf3d5e0064ee080b4e95a5bfa82eae604764a8a199e63a847e34673fd



