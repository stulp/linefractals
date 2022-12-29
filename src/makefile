APPLICATION = FractalApplication

BIN_DIR = ./
SOURCES := $(shell find ./ -name "*.java")
TARGETS += $(subst .java,.class,${SOURCES})
TARGETS := $(addprefix ${BIN_DIR}/,${TARGETS})

VERSION = 11

all: ${BIN_DIR} ${TARGETS}

run:${TARGETS}
	java ${APPLICATION}

jar: ${TARGETS}
	jar cfe ${APPLICATION}.jar ${APPLICATION}  *.class

clean:
	rm -f ${BIN_DIR}/*.class
	rm -f ${BIN_DIR}/*.jar

${BIN_DIR}/%.class: %.java
	 javac -d ${BIN_DIR} -source ${VERSION} -target ${VERSION} -Xlint $<

${BIN_DIR}:
	mkdir ${BIN_DIR}