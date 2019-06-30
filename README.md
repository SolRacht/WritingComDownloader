# Save your favorite interactives

This utility uses your writing.com login credentials to save your favorite writing.com interactive stories. It scrapes the website and records chapter information in a database.

This utility does _not_ include a way to view or browse the saved stories. Its only purpose is to scrape the site and download stories. You will need to find another program that lets you read the stories in a convenient way, or you could make one yourself.

This utility requires SBT (https://www.scala-sbt.org/) and Scala 2.12 (https://scala-lang.org/).


## Usage

### Configuration

Configuration can be found in `src/main/resources/applicaton.conf`. See that file for instructions.

### Execution

From the root directory of this repository, Run `sbt scraper/run getnew`


### Technical overview


