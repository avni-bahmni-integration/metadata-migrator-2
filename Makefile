build-server: ## Builds the jar file
	./gradlew clean build -x test

tunnel-to-avni-staging:
	ssh -L 4321:stagingdb.openchs.org:5432 avni-server-staging