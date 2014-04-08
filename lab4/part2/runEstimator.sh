echo "TrafficEstimator localhost 4444 N L R"
echo "BlackBox at 4444"
echo " N - number of packets"
echo " L - packet size of the train (bytes)"
echo " r - average bit rate of the train (kbps)"

java TrafficEstimator localhost 4444 $1 $2 $3
