# Copyright (c) 2017-present, Facebook, Inc.
# All rights reserved.
#
# This source code is licensed under the license found in the
# LICENSE file in the root directory of this source tree.
#

help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "%-15s%s\n", $$1, $$2}'

run: compile ## Run benchmarks from the paper, printing results to stdout
	@command -v java >/dev/null 2>&1 || { echo >&2 "I require java but it's not installed.  Aborting."; exit 1; }
	java -Xms10g -Xmx10g -jar AsynchronousRatchetingTree/target/uber-AsynchronousRatchetingTree-1.0-SNAPSHOT.jar

compile: ## Compile benchmarks
	@command -v mvn >/dev/null 2>&1 || { echo >&2 "I require maven but it's not installed.  Aborting."; exit 1; }
	mvn --file AsynchronousRatchetingTree/pom.xml package

clean: ## Remove Java artifacts
	@command -v mvn >/dev/null 2>&1 || { echo >&2 "I require maven but it's not installed.  Aborting."; exit 1; }
	mvn --file AsynchronousRatchetingTree/pom.xml clean

