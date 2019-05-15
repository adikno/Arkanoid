# 209007087
# knobela

compile: bin
	find src -name "*.java" > sources.txt
	javac -d bin -cp biuoop-1.4.jar @sources.txt
	rm sources.txt

bin:
	mkdir bin

run:
	java -jar ass6game.jar

jar:
	jar -cfm ass6game.jar MANIFEST.MF -C bin . -C resources .
