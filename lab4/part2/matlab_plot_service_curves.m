%2.1 blackbox 1
figure(1);
x = 1:1:1000;
y = 10000*(x-20) + 11840;

y1 = 170*(x-100) + 944;
y2 = 170*(x-100) + 1032;
y3 = 170*(x-100) + 1232;



%plot(x,y, x,y1, x,y2, x,y3, x,y4, x,y5, x,y6);
plot( x,y1, x,y2, x,y3);
title('Unknown BlackBox');
xlabel('time (in milliseconds)');
ylabel('Data (in bits)');
legend('experiment 1', 'experiment 2', 'experiment 3','experiment 4', 'experiment 5', 'experiment 6');