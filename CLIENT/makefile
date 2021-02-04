CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude
LDFLAGS:=-lboost_system -pthread -lboost_thread

# All Targets
all: bin/BGRSclient

bin/BGRSclient: bin/connectionHandler.o bin/BGRSclient.o bin/Communicator.o bin/InputHandler.o
	@echo 'Building target: BGRSclient'
	@echo 'Invoking: C++ Linker'
	g++ -Wall -Weffc++ -o bin/BGRSclient bin/connectionHandler.o bin/BGRSclient.o bin/InputHandler.o bin/Communicator.o $(LDFLAGS)
	@echo 'Finished building target: BGRSclient'
	@echo ' '

# the main class of the client
bin/BGRSclient.o: src/BGRSclient.cpp
	g++ $(CFLAGS) -o bin/BGRSclient.o src/BGRSclient.cpp
	
bin/connectionHandler.o: src/connectionHandler.cpp
	g++ $(CFLAGS) -o bin/connectionHandler.o src/connectionHandler.cpp $(LDFLAGS)

bin/Communicator.o: src/Communicator.cpp
	g++ $(CFLAGS) -o bin/Communicator.o src/Communicator.cpp $(LDFLAGS)

bin/InputHandler.o: src/InputHandler.cpp
	g++ $(CFLAGS) -o bin/InputHandler.o src/InputHandler.cpp $(LDFLAGS)
	

#Clean the build directory	
.PHONY: clean
clean:
	@echo 'Cleaning'
	rm -f bin/*

