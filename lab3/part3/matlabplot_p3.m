clc;clear all;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Reading data from a file
%Note that time is in micro seconds and packetsize is in Bytes
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
close all;
clearvars;
size = 4000;

%............. sink output1
[packet_no_p, packetsize_p, arrival_time] = textread('output_sink3.txt', '%f %f %f');

%plot number of packet transmissions on a time scale of 10 ms per data
%point
%for number of packet transmissions, we don't care about the packet size
%just the fact that a packet comes in at arrival_time(i)
figure(1);
subplot(2,1,1);

time = arrival_time(1:size);
packet = packet_no_p (1:size);
plot(cumsum(time), packet);
title('Packet Tranmissions for N=2');
xlabel('time (in microseconds)');
ylabel('number of packets');
%plot number of transmitted bytes on a time scale of 10 ms per data point
subplot(2,1,2);
packet2 = packetsize_p(1:size);
plot(cumsum(time), cumsum(packet2));
title('Number of Bytes Transmitted for N=2');
ylabel('number of bytes');
xlabel('time (in microseconds)');

