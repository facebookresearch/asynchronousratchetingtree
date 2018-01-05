# Copyright (c) 2017-present, Facebook, Inc.
# All rights reserved.
#
# This source code is licensed under the license found in the
# LICENSE file in the root directory of this source tree.
#

run: compile
	@command -v java >/dev/null 2>&1 || { echo >&2 "I require java but it's not installed.  Aborting."; exit 1; }
	java -Xms10g -Xmx10g -jar AsynchronousRatchetingTree/target/uber-AsynchronousRatchetingTree-1.0-SNAPSHOT.jar

compile:
	@command -v mvn >/dev/null 2>&1 || { echo >&2 "I require maven but it's not installed.  Aborting."; exit 1; }
	mvn --file AsynchronousRatchetingTree/pom.xml package

clean:
	@command -v mvn >/dev/null 2>&1 || { echo >&2 "I require maven but it's not installed.  Aborting."; exit 1; }
	mvn --file AsynchronousRatchetingTree/pom.xml clean
