% read sink file
[seqNo, send, recv] = textread('output_sink.txt', '%d %d %d');
% number of packets + total size of packet train
%N=5000;
%L=20000;
N=1000;
L=100000;

% LSR for arrival function
arr = polyfit(send/1000,seqNo*L*8/N,1);
% LSR for departure function
dept = polyfit(recv/1000,seqNo*L*8/N,1);

%plotting packet train
figure(1);
plot(send,seqNo*8*L/N,recv,seqNo*8*L/N);
title('Arrival and Departure functions');
ylabel('Transmitted Bits');
xlabel('Time (microseconds)');
% find the max backlog
lines = findobj(gcf, 'type', 'line');
CurveHandle1 = lines(1);
CurveHandle2 = lines(2);
x1 = get(CurveHandle1, 'XData');
y1 = get(CurveHandle1, 'YData');
x2 = get(CurveHandle2, 'XData');
y2 = get(CurveHandle2, 'YData');
projectedy2 = interp1(x2,y2,x1);
[maxdist,maxidx] = max(abs(projectedy2-y2));
[mindist,minidx] = min(abs(projectedy2-y2));
fprintf(1,'Maximum backlog is %g bits at t=%g\n', maxdist, x1(maxidx));
fprintf(1,'Arrival rate is %g kbps\n',arr(1));
fprintf(1,'Departure rate is %g kbps * (t-%g ms)+%d bits\n',dept(1),recv(1)/1000,dept(2)+dept(1)*recv(1)/1000);
% plot legend here to avoid screwing with the backlog code 
% (it would detect > 2 lines)
legend('Arrivals','Departures');

