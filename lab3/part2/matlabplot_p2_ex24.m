clc;clear all;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Reading data from a file
%Note that time is in micro seconds and packetsize is in Bytes
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
close all;
clearvars;
size = 5000;


%............. sink output1
[packet_no_p, packetsize_p, arrival_time] = textread('output_sink1.txt', '%f %f %f');
sum(packetsize_p) 
max(cumsum(arrival_time))
avg = sum(packetsize_p) / max(cumsum(arrival_time));
avg = avg*8;
avg = avg*10e6

[packet_no_p2, packetsize_p2, arrival_time2] = textread('output_sink2.txt', '%f %f %f');
sum(packetsize_p2) 
max(cumsum(arrival_time2))
avg2 = sum(packetsize_p2) / max(cumsum(arrival_time2));
avg2 = avg2*8;
avg2 = avg2*10e6

