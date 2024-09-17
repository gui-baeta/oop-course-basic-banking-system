all:
	(cd m19-core; make $(MFLAGS) all)
	(cd m19-app; make $(MFLAGS) all)

clean:
	(cd m19-core; make $(MFLAGS) clean)
	(cd m19-app; make $(MFLAGS) clean)

install:
	(cd m19-core; make $(MFLAGS) install)
	(cd m19-app; make $(MFLAGS) install)
