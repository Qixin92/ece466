[seqNo, send, receive] = textread('output_sink_15_1.txt', '%d %d %d');

figure(1);
plot(seqNo,send,seqNo,receive);
title('Packet train: N=100, L=400, r=10');
xlabel('Sequence Numbers');
ylabel('Time (in microseconds)');
legend('Send','Receive');