clc;clear all;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Reading data from a file
%Note that time is in micro seconds and packetsize is in Bytes
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
[packet_no_p, time_p, packetsize_p] = textread('poisson3.data', '%f %f %f');

%PLOT1
figure(1);

time_array = zeros(1,50000);
cumulative_arrival = zeros(1,50000);


time_array(1) = time_p(1);
cumulative_arrival(1) = packetsize_p(1);
i=2
while i<=50000
    time_array(i) = time_p(i);
    cumulative_arrival(i) = cumulative_arrival(i-1) + packetsize_p(i);
    i=i+1;
end
    

subplot(2,1,1);
plot(time_array,cumulative_arrival);
title('Trace file (poisson3.data)');
xlabel('time (in microseconds)');
ylabel('culmulative arrival (in bytes)');



[packet_no_p2, packetsize_p2, arrival_time] = textread('TrafficSinkOutput.txt', '%f %f %f');

time_array2 = zeros(1,50000);
cumulative_arrival2 = zeros(1,50000);


time_array2(1) = arrival_time(1);
cumulative_arrival2(1) = packetsize_p2(1);
i=2
while i<=50000
    time_array2(i) = time_array2(i-1) + arrival_time(i);
    cumulative_arrival2(i) = cumulative_arrival2(i-1) + packetsize_p2(i);
    i=i+1;
end

subplot(2,1,2);
plot(time_array2,cumulative_arrival2);
title('Traffic Sink (TrafficSinkOutput.txt)');
xlabel('time (in microseconds)');
ylabel('culmulative arrival (in bytes)');

