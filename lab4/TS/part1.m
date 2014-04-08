% read sink file
[seqNo, send, recv] = textread('sink.txt', '%d %d %d');
% plot the packet train
% value of r
r = 10000;
figure(1);
plot(seqNo,send,seqNo,recv);
title(sprintf('Send and Receive Times (N=100,L=400,r=%i)', r));
xlabel('Sequence Numbers');
ylabel('Time (microseconds)');
legend('Send','Receive');