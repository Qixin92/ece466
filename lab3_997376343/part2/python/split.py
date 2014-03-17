# open file
f = open("output_scheduler.txt")
lines = f.readlines()
f.close()

f1 = open("output_scheduler1.txt",'a')
f2 = open("output_scheduler2.txt",'a')
# go through lines, check class
for i in range(len(lines)):
	# split each line into an array
	info = lines[i].split('\t')
	# class is the last element on the line
	c = info[4]
	if c[0]=='1':
		f1.write(' '.join(info))
	elif c[0]=='2':
		f2.write(' '.join(info))

print "done with",
print len(lines),
print "lines"

# close files
f1.close()
f2.close()
