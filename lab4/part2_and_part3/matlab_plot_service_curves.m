%2.1 blackbox 1
figure(1);
x = 1:1:1000;


y1 = 1986*(x-4.5);
y2 = 1890*(x-4.4) + 8260;
y3 = 2099*(x-4.5) + 8260;
y4 = 2190*(x-4.8) + 9987;
y5 = 2206*(x-3.1) + 3514;


plot(x,y1, x,y2, x,y3, x,y4, x,y5);

title('Unknown BlackBox');
xlabel('time (in milliseconds)');
ylabel('Data (in bits)');
legend('experiment 1', 'experiment 2', 'experiment 3','experiment 4', 'experiment 5', 'experiment 6');