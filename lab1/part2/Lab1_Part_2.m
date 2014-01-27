clc;clear all;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Reading data from the file
%Note: - time is in miliseconds and framesize is in Bytes
%      - file is sorted in transmit sequence
%  Column 1:   index of frame (in display sequence)
%  Column 2:   time of frame in ms (in display sequence)
%  Column 3:   type of frame (I, P, B)
%  Column 4:   size of frame (in Bytes)
%  Column 5-7: not used
%
% Since we are interested in the transmit sequence we ignore Columns 1 and
% 2. So, we are only interested in the following columns: 
%       Column 3:  assigned to type_f
%       Column 4:   assigned to framesize_f
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
[index, time, type_f, framesize_f, dummy1, dymmy2, dymmy3 ] = textread('movietrace.data', '%f %f %c %f %f %f %f');

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
%   CODE FOR EXERCISE 2.2   (version: Spring 2007)
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Extracting the I,P,B frmes characteristics from the source file
%frame size of I frames  : framesize_I
%frame size of P frames  : framesize_p 
%frame size of B frames  : framesize_B
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

a=0;
b=0;
c=0;
for i=1:length(index)
    if type_f(i)=='I'
        a=a+1;
        framesize_I(a)=framesize_f(i);
    end
     if type_f(i)=='B'
         b=b+1;
         framesize_B(b)=framesize_f(i);
         end
     if type_f(i)=='P'
         c=c+1;
         framesize_P(c)=framesize_f(i);
     end

end

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Hint1: You may use the MATLAB functions 'length()','mean()','max()','min()'.
%       which calculate the length,mean,max,min of a
%       vector (for example max(framesize_P) will give you the size of
%       largest P frame
%Hint2: Use the 'plot' function to graph the framesize as a function of the frame
%       sequence number. 
%Hint3: Use the function 'hist' to show the distribution of the frames. Before 
%       that function type 'figure(2);' to indicate your figure number.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%number of frames and total number of bytes
num_of_frames = length(index);
S=sprintf('number of frames = %d ',num_of_frames); disp(S);

total_number_of_bytes = sum(framesize_f);
S=sprintf('total number of bytes = %d ',total_number_of_bytes); disp(S);

%size of the smallest frame, size of the largest frame, mean frame size
smallest_frame = min(framesize_f);
S=sprintf('smallest frame size = %d bytes',smallest_frame); disp(S);
largest_frame = max(framesize_f);
S=sprintf('largest frame size = %d bytes',largest_frame); disp(S);
mean_frame_size = sum(framesize_f)/num_of_frames;
S=sprintf('mean frame size = %d bytes',mean_frame_size); disp(S);

%size of the smallest, largest, and mean I, P, and B frame
smallest_frame_I = min(framesize_I);
S=sprintf('smallest I frame = %d bytes',smallest_frame_I); disp(S);
largest_frame_I = max(framesize_I);
S=sprintf('largest I frame = %d bytes',largest_frame_I); disp(S);
mean_frame_I = sum(framesize_I)/length(framesize_I);
S=sprintf('mean I frame = %d bytes',mean_frame_I); disp(S);

smallest_frame_P = min(framesize_P);
S=sprintf('smallest P frame = %d bytes',smallest_frame_P); disp(S);
largest_frame_P = max(framesize_P);
S=sprintf('largest P frame = %d bytes',largest_frame_P); disp(S);
mean_frame_P = sum(framesize_P)/length(framesize_P);
S=sprintf('mean P frame = %d bytes',mean_frame_P); disp(S);

smallest_frame_B = min(framesize_B);
S=sprintf('smallest B frame = %d bytes',smallest_frame_B); disp(S);
largest_frame_B = max(framesize_B);
S=sprintf('largest B frame = %d bytes',largest_frame_B); disp(S);
mean_frame_B = sum(framesize_B)/length(framesize_B);
S=sprintf('mean B frame = %d bytes',mean_frame_B); disp(S);

%mean bit rate (mean frame size / frame duration)
mean_bit_rate = mean_frame_size / (1/30);
S=sprintf('mean bit rate = %d Bps',mean_bit_rate); disp(S);

%peak bit rate (max frame size / frame duration)
peak_bit_rate = largest_frame/ (1/30);
S=sprintf('peak bit rate = %d Bps',peak_bit_rate); disp(S);

%ratio of peak rate and average rate. 
ratio_peak_to_average = peak_bit_rate / mean_bit_rate;
S=sprintf('ratio of peak rate and average rate = %d ',ratio_peak_to_average); disp(S);



% shows the frame size as a function of the frame sequence number
figure(1);
subplot(2,1,1);
plot(index,framesize_f);
xlabel('index of frame');
ylabel('frame size');


subplot(2,1,2);

xlabel('frame size');
ylabel('relative frequency');

figure (2);
subplot(3,1,1);
[n,x] = hist(framesize_I,10);
bar(x,n./sum(n),'hist');
title('Distribution of I frames');
xlabel('frame size');
ylabel('relative frequency');

subplot(3,1,2);
[n,x] = hist(framesize_P,10);
bar(x,n./sum(n),'hist');
title('Distribution of P frames');
xlabel('frame size');
ylabel('relative frequency');

subplot(3,1,3);
[n,x] = hist(framesize_B,10);
bar(x,n./sum(n),'hist');
title('Distribution of B frames');
xlabel('frame size');
ylabel('relative frequency');
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
%   CODE FOR EXERCISE 2.3   (version: Spring 2007)
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%The following code will generates Plot 1. You generate Plot2 , Plot3 on
%your own. 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% The next line assigns a label (figure number) to the figure 
%figure(3);

initial_point=1;
ag_frame=500;
jj=initial_point;
i=1;
bytes_f=zeros(1,100);
while i<=100
while ((jj-initial_point)<=ag_frame*i && jj<length(framesize_f))
bytes_f(i)=bytes_f(i)+framesize_f(jj);
jj=jj+1;
end
i=i+1;
end
figure(3);
subplot(3,1,1);
bar(bytes_f);
title('Plot 1');
xlabel('elements of 500 frames each');
ylabel('relative frequency');


%2
initial_point=randi([0 53995],1,1);
ag_frame=50;
jj=initial_point;
i=1;
bytes_f_2=zeros(1,100);
while i<=100
while ((jj-initial_point)<=ag_frame*i && jj<length(framesize_f))
bytes_f_2(i)=bytes_f_2(i)+framesize_f(jj);
jj=jj+1;
end
i=i+1;
end
subplot(3,1,2);
bar(bytes_f_2);
title('Plot 2');
xlabel('elements of 50 frames each');
ylabel('relative frequency');




%3
initial_point=randi([0 53995],1,1);
ag_frame=5;
jj=initial_point;
i=1;
bytes_f_3=zeros(1,100);
while i<=100
while ((jj-initial_point)<=ag_frame*i && jj<length(framesize_f))
bytes_f_3(i)=bytes_f_3(i)+framesize_f(jj);
jj=jj+1;
end
i=i+1;
end
subplot(3,1,3);
bar(bytes_f_3);
title('Plot 3');
xlabel('elements of 5 frames each');
ylabel('relative frequency');


