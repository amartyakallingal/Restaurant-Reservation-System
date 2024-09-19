rm -rf *.class
javac Main.java
cat tests/inputs/updated_test_cases.txt | java Main > output.txt
rm -rf *.class