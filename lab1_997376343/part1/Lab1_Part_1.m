clc;clear all;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Reading data from a file
%Note that time is in micro seconds and packetsize is in Bytes
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%[packet_no_p, time_p, packetsize_p] = textread('poisson1.data', '%f %f %f');
[packet_no_p, time_p, packetsize_p] = textread('poisson3.data', '%f %f %f');
%%%%%%%%%%%%%%%%%%%%%%%%%Exercise 1.2%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%The following code will generate Plot 1; You generate Plot2 , Plot3.
%Hint1: For Plot2 and Plot3, you only need to change 'initial_p', the
%       initial time in microseconds, and 'ag_frame', the time period of
%       aggregation
%Hint2: After adding Plot2 and Plot3 to this part, you can use 'subplot(3,1,2);'
%       and 'subplot(3,1,3);' respectively to show 3 plots in the same figure.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%PLOT1
figure(1);
jj=1;
i=1;
initial_p=0;
ag_time=1000000; %1 mill microsecs = 1 second
bytes_p=zeros(1,100); %vector of 100 elements
while time_p(jj)<=initial_p
    jj=jj+1;
end
while i<=100
    while ((time_p(jj)-initial_p)<=ag_time*i && jj<length(packetsize_p))
        bytes_p(i)=bytes_p(i)+packetsize_p(jj);
        jj=jj+1;
    end
    i=i+1;
end
subplot(3,1,1);
bar(bytes_p);
title('Plot1');
xlabel('Elements (1 second each)');
ylabel('Number of Bytes');

x = sum(bytes_p)/100;

%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%PLOT2
figure(1);
jj=1;
i=1;
initial_p=30; %random starting point, eg. 30 s
ag_time=100000; %100 milliseconds = 100 000 us
bytes_p2=zeros(1,100); %vector of 100 elements
while time_p(jj)<=initial_p
    jj=jj+1;
end
while i<=100
    while ((time_p(jj)-initial_p)<=ag_time*i && jj<length(packetsize_p))
        bytes_p2(i)=bytes_p2(i)+packetsize_p(jj);
        jj=jj+1;
    end
    i=i+1;
end
subplot(3,1,2);
bar(bytes_p2);
title('Plot2');
xlabel('Elements (100 milliseconds each)');
ylabel('Number of Bytes');
%%%%%%%%
%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%PLOT3
figure(1);
jj=1;
i=1;
initial_p=50.2; %random starting point, eg. 52.2 s
ag_time=10000; %10 milliseconds = 100 00 us
bytes_p3=zeros(1,100); %vector of 100 elements
while time_p(jj)<=initial_p
    jj=jj+1;
end
while i<=100
    while ((time_p(jj)-initial_p)<=ag_time*i && jj<length(packetsize_p))
        bytes_p3(i)=bytes_p3(i)+packetsize_p(jj);
        jj=jj+1;
    end
    i=i+1;
end
subplot(3,1,3);
bar(bytes_p3);
title('Plot3');
xlabel('Elements (10 milliseconds each)');
ylabel('Number of Bytes');
%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Note: Run the same MATLAB code for Exercise 1.3 and 1.4 but change the
%second line of the code in order to read the files 'poisson2.data' and
%'poisson3.data' respectively.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

