# A2_Concurrent Programming makefile
# Tafadzwa Nyazenga
# 24 August 2022


JAVA=/usr/bin/java
JAVAC=/usr/bin/javac
JAVADOC=/usr/bin/javadoc
.SUFFIXES: .java .class
SRCDIR=src/typingTutor
BINDIR=bin
DOCDIR=doc

#.PHONY: default doc clean

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES= WordDictionary.class Score.class ScoreUpdater.class FallingWord.class CatchWord.class WordMover.class GamePanel.class TypingTutorApp.class
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

### Documentation
doc:
		$(JAVADOC) -d $(DOCDIR) $(SRCDIR)/*

clean:
	$(RM) $(BINDIR)/*.class


run: $(CLASS_FILES)
		$(JAVA) -cp $(BINDIR) typingTutor.TypingTutorApp $(totalWords) $(noWords) $(dictFile)


clean-doc:
		rm -r $(DOCDIR)/*
