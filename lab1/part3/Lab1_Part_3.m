clc;clear all;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Reading the data and putting the first 100000 entries in variables 
%Note that time is in seconds and framesize is in Bytes
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
no_entries=100000;
[time1, framesize1] = textread('Bel.data', '%f %f');
time=time1(1:no_entries);
framesize=framesize1(1:no_entries);
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


num_of_packets = length(framesize1);
S=sprintf('number of packets = %d ',num_of_packets); disp(S);

total_bytes = sum(framesize1);
S=sprintf('total number of bytes = %d ',total_bytes); disp(S);

mean_bit_rate = sum(framesize1)*8/max(time1);
S=sprintf('mean bit rate = %d ',mean_bit_rate); disp(S);

rate = zeros(1,num_of_packets);
rate(1) = framesize1(1)/time1(1);
i = 2;
while i <= num_of_packets
    rate(i) = framesize1(i)*8/(time1(i) - time1(i-1));
    i = i + 1;
end
peak_bit_rate = max(rate);
S=sprintf('peak bit rate = %d ',peak_bit_rate); disp(S);

figure(1);
subplot(2,1,1);plot(time1,framesize1);
xlabel('time in seconds');
ylabel('packet size in bytes');

subplot(2,1,2);
[n,x] = hist(framesize1,8);
bar(x,n./sum(n),'hist');
xlabel('packet size');
ylabel('relative Frequency');
%%%%%%%%%%%%%%%%%%%%%%%%%Exercise %%%3.2%%%%%%%%%%%%%%%%%%%%%%%%%%%
%The following code will generate Plot 1; You generate Plot2 , Plot3.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
figure(2);
jj=1;
i=1;
initial_p=0;
ag_time=1;
bytes_p=zeros(1,100);
while time(jj)<=initial_p
    jj=jj+1;
end
while i<=100
while ((time(jj)-initial_p)<=ag_time*i && jj<no_entries)
bytes_p(i)=bytes_p(i)+framesize(jj);
jj=jj+1;
end
i=i+1;
end
%%%%%%%%
subplot(3,1,1);bar(bytes_p);
title('Plot 1');
xlabel('each element of 1 sec intervals');
ylabel('number of bytes');


%2
jj=1;
i=1;
initial_p=0;
ag_time=0.1;
bytes_p2=zeros(1,100);
while time(jj)<=initial_p
    jj=jj+1;
end
while i<=100
while ((time(jj)-initial_p)<=ag_time*i && jj<no_entries)
bytes_p2(i)=bytes_p2(i)+framesize(jj);
jj=jj+1;
end
i=i+1;
end
%%%%%%%%
subplot(3,1,2);bar(bytes_p2);
title('Plot 2');
xlabel('each element of 100 msec intervals');
ylabel('number of bytes');

%3
jj=1;
i=1;
initial_p=0;
ag_time=0.01;
bytes_p3=zeros(1,100);
while time(jj)<=initial_p
    jj=jj+1;
end
while i<=100
while ((time(jj)-initial_p)<=ag_time*i && jj<no_entries)
bytes_p3(i)=bytes_p3(i)+framesize(jj);
jj=jj+1;
end
i=i+1;
end
%%%%%%%%
subplot(3,1,3);bar(bytes_p3);
title('Plot 3');
xlabel('each element of 10 msec intervals');
ylabel('number of bytes');




